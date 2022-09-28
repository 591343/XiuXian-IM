package com.xiuxian.chat.controller;


import com.xiuxian.chat.annotation.Login;
import com.xiuxian.chat.vo.friendlist.FriendListItemVo;
import com.xiuxian.chat.vo.message.NoticeMessageVo;
import com.xiuxian.common.utils.Result;
import com.xiuxian.chat.service.FriendListService;
import com.xiuxian.chat.vo.friendlist.FriendListVo;
import com.xiuxian.chat.vo.friendlist.GroupListVo;
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
@Api(tags="朋友列表接口")
public class ApiFriendListController {

    @Autowired
    FriendListService friendListService;

    @Login
    @GetMapping("/xiuxianfriendlist/friendlist")
    @ApiOperation(value="获取好友列表信息", response= FriendListVo.class)
    public Result<FriendListVo> getAllFriendList(@RequestParam("selfXiuxianId") String selfXiuxianId){
        FriendListVo friendList = friendListService.getAllFriendList(selfXiuxianId);
        return new Result<FriendListVo>().ok(friendList);
    }

    @Login
    @GetMapping("/xiuxianfriendlist/grouplist")
    @ApiOperation(value="获取好友列表信息", response= GroupListVo.class)
    public Result<GroupListVo> getGroupList(@RequestParam("selfXiuxianId") String selfXiuxianId){
        GroupListVo groupList = friendListService.getGroupList(selfXiuxianId);
        return new Result<GroupListVo>().ok(groupList);
    }

    @Login
    @GetMapping("/xiuxianfriendlist/friendlistitem")
    @ApiOperation(value="获取好友列表项信息", response= FriendListItemVo.class)
    public Result<FriendListItemVo> getFriendListItem(@RequestParam("selfXiuxianId") String selfXiuxianId,@RequestParam("friendXiuxianId") String friendXiuxianId){
        FriendListItemVo friendListItemVo = friendListService.getFriendListItem(selfXiuxianId,friendXiuxianId);
        return new Result<FriendListItemVo>().ok(friendListItemVo);
    }



}
