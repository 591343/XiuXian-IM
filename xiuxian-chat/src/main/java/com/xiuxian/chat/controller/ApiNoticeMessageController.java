package com.xiuxian.chat.controller;

import com.xiuxian.chat.annotation.Login;
import com.xiuxian.chat.entity.ChatMessageEntity;
import com.xiuxian.chat.entity.GroupEntity;
import com.xiuxian.chat.entity.NoticeMessageEntity;
import com.xiuxian.chat.service.NoticeMessageService;
import com.xiuxian.chat.vo.message.NewFriendListVo;
import com.xiuxian.chat.vo.message.NoticeMessage;
import com.xiuxian.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Chen Xiao
 * @Description: 通知消息接口
 * @Date: Created in 2022/9/21
 */
@RestController
@RequestMapping("/api")
@Api(tags="通知消息接口")
public class ApiNoticeMessageController {
    @Autowired
    NoticeMessageService noticeMessageService;

    @Login
    @PostMapping("/xiuxiannoticemessage/save/noticemessage")
    @ApiOperation(value="保存通知消息")
    public Result<Long> saveNoticeMessage(@RequestBody NoticeMessageEntity noticeMessageEntity){
        noticeMessageService.saveNoticeMessage(noticeMessageEntity);
        return new Result<Long>().ok(noticeMessageEntity.getId());
    }

    @Login
    @GetMapping("/xiuxiannoticemessage/newfriend")
    @ApiOperation(value="获取新的朋友通知消息",response= NewFriendListVo.class)
    public Result<NewFriendListVo> getNewFriendNoticeMessage(@RequestParam("xiuxianUserId") String xiuxianUserId){
        NewFriendListVo newFriendListVo=noticeMessageService.newFriendNoticeMessage(xiuxianUserId);
        return new Result<NewFriendListVo>().ok(newFriendListVo);
    }

    @Login
    @PostMapping("/xiuxiannoticemessage/send/validmessage")
    @ApiOperation(value="发送验证回复消息")
    public Result<Integer> sendValidMessage(@RequestBody NoticeMessage noticeMessage){
        Integer status = noticeMessageService.sendValidMessage(noticeMessage);
        return new Result<Integer>().ok(status);
    }
}
