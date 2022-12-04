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

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;


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
    private StringRedisTemplate stringRedisTemplate;

    private final String ONLINE_KEY="XIUXIANIM:WS:ONLINE:USER:";  //在线用户键

    //用于心跳检测
    @PostMapping("/heartbeatCheck")
    public Result heartbeatCheck(@RequestBody Ping ping) {
        Pong pong = new Pong();
        String fromId = ping.getFromId();
        pong.setToId(fromId);

        // 正常情况下服务器只需要在新建连接或者断开连接的时候更新一下Redis就好了。但由于服务器可能会出现异常，或者服务器跟Redis之间的网络会出现问题，
        // 此时基于事件的更新就会出现问题，导致用户状态不正确。因此，如果需要用户在线状态准确的话最好通过心跳来更新在线状态。
        ValueOperations<String, String> onlineUserOps = stringRedisTemplate.opsForValue();
        onlineUserOps.set(ONLINE_KEY+fromId,fromId,5, TimeUnit.SECONDS); //设置5s过期

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
    public Result sendMsgToGroup(@RequestBody ChatMessage chatMessage) {
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
    //群发 订阅/top/{xiuxianGroupId}
    @PostMapping("/sendNoticeMessageToGroup")
    public Result sendNoticeMessageToGroup(@RequestBody NoticeMessage noticeMessage) {
        simpMessagingTemplate.convertAndSend("/topic/notice/" + noticeMessage.getToId(), noticeMessage);
        return new Result<>();
    }




}
