package com.xiuxian.chat.controller;


import com.xiuxian.chat.annotation.Login;
import com.xiuxian.chat.entity.ChatListEntity;
import com.xiuxian.chat.service.ChatListService;
import com.xiuxian.chat.vo.chatlist.ChatListItemVo;
import com.xiuxian.chat.vo.chatlist.ChatListVo;
import com.xiuxian.common.utils.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 聊天列表接口
 *
 * @author Chenxiao 591343671@qq.com
 */
@RestController
@RequestMapping("/api")
@Api(tags="聊天列表接口")
public class ApiChatListController {


    @Autowired
    ChatListService chatListService;

    @Login
    @GetMapping("/xiuxianchatlist/chatlist")
    @ApiOperation(value="获取聊天列表信息", response= ChatListVo.class)
    public Result<ChatListVo> getChatList(@RequestParam("selfXiuxianId") String selfXiuxianId){
        ChatListVo chatListVo = chatListService.getLatestChatList(selfXiuxianId,"",false);
        return new Result<ChatListVo>().ok(chatListVo);
    }

    @Login
    @GetMapping("/xiuxianchatlist/chatlistitem")
    @ApiOperation(value="获取单个聊天列表项", response= ChatListItemVo.class)
    public Result<ChatListItemVo> getChatListItem(@RequestParam("selfXiuxianId") String selfXiuxianId,@RequestParam("friendXiuxianId") String friendXiuxianId){
        ChatListItemVo chatListItemVo = chatListService.getChatListItem(selfXiuxianId,friendXiuxianId);
        return new Result<ChatListItemVo>().ok(chatListItemVo);
    }


    @Login
    @PostMapping("/xiuxianchalist/chatlist")
    @ApiOperation(value="添加聊天列表信息")
    public Result addChatList(@RequestBody ChatListEntity chatListEntity){
        chatListService.addChatList(chatListEntity);
        return new Result();
    }
}
