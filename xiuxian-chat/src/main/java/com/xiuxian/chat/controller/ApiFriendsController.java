package com.xiuxian.chat.controller;

import com.xiuxian.chat.annotation.Login;
import com.xiuxian.chat.service.FriendsService;
import com.xiuxian.chat.service.impl.ChatListServiceImpl;
import com.xiuxian.chat.vo.chatlist.FriendListItemRelVo;
import com.xiuxian.chat.vo.friend.AcceptFriendVo;
import com.xiuxian.chat.vo.friend.AddFriendVo;
import com.xiuxian.chat.vo.friend.ChangeFriendPermissionVo;
import com.xiuxian.chat.vo.friend.ChangeFriendRemarkVo;
import com.xiuxian.chat.vo.friendlist.GroupListVo;
import com.xiuxian.chat.vo.message.NoticeMessage;
import com.xiuxian.chat.vo.message.NoticeMessageVo;
import com.xiuxian.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Chen Xiao
 * @Description: 好友关系接口
 * @Date: Created in 2022/9/22
 */
@RestController
@RequestMapping("/api")
@Api(tags="朋友关系接口")
public class ApiFriendsController {

    @Autowired
    FriendsService friendsService;


    @Login
    @PostMapping("/xiuxianfriend/addfriend")
    @ApiOperation(value="添加好友申请")
    public Result addFriend(@RequestBody AddFriendVo addFriendVo){
        friendsService.addFriend(addFriendVo);
        return new Result();
    }

    @Login
    @GetMapping("/xiuxianfriend/isfriends")
    @ApiOperation(value="查询是否为好友",response= Boolean.class)
    public Result<Boolean> isFriends(@RequestParam("fromId") String fromId,@RequestParam("toId") String toId){
        Boolean isFriends=friendsService.isFriends(fromId,toId);
        return new Result<Boolean>().ok(isFriends);
    }

    @Login
    @PostMapping("/xiuxianfriend/acceptfriend")
    @ApiOperation(value = "接受好友申请")
    public Result acceptFriend(@RequestBody AcceptFriendVo acceptFriendVo){
        friendsService.acceptFriend(acceptFriendVo);
        return new Result();
    }

    @Login
    @PostMapping("/xiuxianfriend/deletefriend")
    @ApiOperation(value = "删除好友")
    public Result deleteFriend(@RequestBody FriendListItemRelVo friendListItemRelVo){
        friendsService.deleteFriend(friendListItemRelVo);
        return new Result();
    }

    @Login
    @PostMapping("/xiuxianfriend/changeremark")
    @ApiOperation(value = "修改好友的备注")
    public Result changeFriendsRemark(@RequestBody ChangeFriendRemarkVo changeFriendRemarkVo){
        friendsService.changeRemark(changeFriendRemarkVo);
        return new Result();
    }

    @Login
    @PostMapping("/xiuxianfriend/changepermission")
    @ApiOperation(value = "修改好友的权限")
    public Result changeFriendsPermission(@RequestBody ChangeFriendPermissionVo changeFriendPermissionVo){
        friendsService.changePermission(changeFriendPermissionVo);
        return new Result();
    }



}
