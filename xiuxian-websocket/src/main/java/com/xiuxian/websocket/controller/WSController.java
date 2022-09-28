package com.xiuxian.websocket.controller;

import com.xiuxian.common.utils.Result;
import com.xiuxian.websocket.constant.NoticeMessageConstant;
import com.xiuxian.websocket.entity.ChatMessageEntity;
import com.xiuxian.websocket.entity.NoticeMessageEntity;
import com.xiuxian.websocket.feign.ChatMessageFeignService;
import com.xiuxian.websocket.feign.NoticeMessageFeignService;
import com.xiuxian.websocket.message.ChatMessage;
import com.xiuxian.websocket.message.NoticeMessage;
import com.xiuxian.websocket.message.Ping;
import com.xiuxian.websocket.message.Pong;

import com.xiuxian.websocket.service.WSNoticeMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author: Chen Xiao
 * @Description: WsApi
 * @Date: Created in 2022/9/01
 */
@Slf4j
@RestController
@RequestMapping("/api/ws")
public class WSController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ChatMessageFeignService chatMessageFeignService;

    @Autowired
    private WSNoticeMessageService wsNoticeMessageService;

    @Autowired
    private NoticeMessageFeignService noticeMessageFeignService;

    //用于心跳检测
    @PostMapping("/heartbeatCheck")
    public Result heartbeatCheck(@RequestBody Ping ping) {
        System.out.println(ping);
        Pong pong = new Pong();
        pong.setToId(ping.getFromId());
        simpMessagingTemplate.convertAndSendToUser(ping.getFromId(), "/heartbeat", pong);
        return new Result();
    }


    //单聊 订阅/user/{xiuxianUserId}/chat
    @PostMapping("/sendMsgToUser")
    public Result sendMsgByUser(@RequestBody ChatMessage chatMessage) {

        ChatMessageEntity chatMessageEntity = new ChatMessageEntity();
        BeanUtils.copyProperties(chatMessage, chatMessageEntity);
        Result<Long> result = chatMessageFeignService.saveChatMessage(chatMessageEntity);
        Long messageId = result.getData();
        chatMessage.setId(String.valueOf(messageId));
        simpMessagingTemplate.convertAndSendToUser(chatMessage.getToId(), "/chat", chatMessage);
        return new Result<>();
    }

    //群聊 订阅/topic/info/{xiuxianGroupId}
    @PostMapping("/sendMsgToGroup")
    public Result sendMsgByAll(@RequestBody ChatMessage chatMessage) {
        // /topic/info/{xiuxianGroupId}
        ChatMessageEntity chatMessageEntity = new ChatMessageEntity();
        BeanUtils.copyProperties(chatMessage, chatMessageEntity);
        Result<Long> result = chatMessageFeignService.saveChatMessage(chatMessageEntity);
        Long messageId = result.getData();
        chatMessage.setId(String.valueOf(messageId));
        simpMessagingTemplate.convertAndSend("/topic/info/" + chatMessage.getToId(), chatMessage);
        return new Result<>();
    }

    //单发 订阅/user/{xiuxianUserId}/notice
    @PostMapping("/sendNoticeMessageToUser")
    public Result sendNoticeMessageToUser(@RequestBody NoticeMessage noticeMessage) {
        simpMessagingTemplate.convertAndSendToUser(noticeMessage.getToId(),"/notice",noticeMessage);
        return new Result<>();
    }




}
