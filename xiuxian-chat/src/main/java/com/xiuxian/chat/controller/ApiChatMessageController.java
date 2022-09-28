package com.xiuxian.chat.controller;


import com.xiuxian.chat.annotation.Login;
import com.xiuxian.chat.entity.ChatMessageEntity;
import com.xiuxian.chat.service.ChatMessageService;
import com.xiuxian.chat.vo.request.ChatMessageUpdateTotimeVo;
import com.xiuxian.common.utils.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 聊天消息接口
 *
 * @author Chenxiao 591343671@qq.com
 */
@RestController
@RequestMapping("/api")
@Api(tags="聊天消息接口")
public class ApiChatMessageController {


    @Autowired
    ChatMessageService chatMessageService;

    @Login
    @PostMapping("/xiuxianchatmessage/chatmessage")
    @ApiOperation(value="更新聊天消息的接收时间戳")
    public Result updateChatMessageToTime(@RequestBody ChatMessageUpdateTotimeVo chatMessageUpdateTotimeVo){
        chatMessageService.updateChatMessageToTime(chatMessageUpdateTotimeVo);
        return new Result();
    }

    @Login
    @PostMapping("/xiuxianchatmessage/save/chatmessage")
    @ApiOperation(value="保存聊天消息")
    public Result<Long> saveChatMessage(@RequestBody ChatMessageEntity chatMessageEntity){
        chatMessageService.saveChatMessage(chatMessageEntity);
        return new Result<Long>().ok(chatMessageEntity.getId());
    }
}
