package com.xiuxian.chat.controller;

import com.xiuxian.chat.annotation.Login;
import com.xiuxian.chat.vo.friend.ChangeFriendRemarkVo;
import com.xiuxian.chat.vo.group.*;
import com.xiuxian.chat.vo.xiuxianuser.XiuXianUserVo;
import com.xiuxian.common.utils.Result;
import com.xiuxian.chat.entity.GroupEntity;
import com.xiuxian.chat.service.XiuXianGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 群接口
 *
 * @author Chenxiao 591343671@qq.com
 */
@RestController
@RequestMapping("/api")
@Api(tags="修仙用户接口")
public class ApiGroupController {

    @Autowired
    XiuXianGroupService xiuXianGroupService;

    @Login
    @GetMapping("/xiuxiangroup/group")
    @ApiOperation(value="获取群信息", response= GroupEntity.class)
    public Result<GroupEntity> userInfo(@RequestParam("xiuxianGroupId") String xiuxianGroupId){
        GroupEntity group = xiuXianGroupService.getXiuXianGroup(xiuxianGroupId);
        return new Result<GroupEntity>().ok(group);
    }

    @Login
    @GetMapping("/xiuxiangroup/contacts")
    @ApiOperation(value="获取群人员", response= GroupContactsVo.class)
    public Result<GroupContactsVo> groupContacts(@RequestParam("xiuxianUserId") String xiuxianUserId,@RequestParam("xiuxianGroupId") String xiuxianGroupId){
        GroupContactsVo groupContacts = xiuXianGroupService.getGroupContacts(xiuxianUserId,xiuxianGroupId);
        return new Result<GroupContactsVo>().ok(groupContacts);
    }

    @Login
    @GetMapping("/xiuxiangroup/groupInfo")
    @ApiOperation(value="获取搜索时群信息", response= GroupContactsVo.class)
    public Result<GroupInfoVo> groupInfo(@RequestParam("xiuxianGroupId") String xiuxianGroupId){
        GroupInfoVo groupInfoVo = xiuXianGroupService.getGroupInfo(xiuxianGroupId);
        return new Result<GroupInfoVo>().ok(groupInfoVo);
    }


    @Login
    @GetMapping("/xiuxiangroup/groupnumber")
    @ApiOperation(value="获取群人数", response= Integer.class)
    public Result<Integer> groupPersonNumber(@RequestParam("xiuxianGroupId") String xiuxianGroupId){
        Integer number = xiuXianGroupService.getGroupNumber(xiuxianGroupId);
        return new Result<Integer>().ok(number);
    }

    @Login
    @PostMapping("/xiuxiangroup/changegroupname")
    @ApiOperation(value="修改群名")
    public Result changGroupName(@RequestBody ChangGroupNameVo changGroupNameVo){
        xiuXianGroupService.changGroupName(changGroupNameVo);
        return new Result();
    }

    @Login
    @PostMapping("/xiuxiangroup/changegroupremark")
    @ApiOperation(value="修改群备注")
    public Result changGroupRemark(@RequestBody ChangeFriendRemarkVo changeFriendRemarkVo){
        xiuXianGroupService.changGroupRemark(changeFriendRemarkVo);
        return new Result();
    }

    @Login
    @PostMapping("/xiuxiangroup/invite-join-group")
    @ApiOperation(value="邀请加入群聊")
    public Result inviteJoinGroup(@RequestBody InviteJoinGroupVo inviteJoinGroupVo){
        xiuXianGroupService.inviteJoinGroup(inviteJoinGroupVo);
        return new Result();
    }
    @Login
    @PostMapping("/xiuxiangroup/remove-from-group")
    @ApiOperation(value="邀请加入群聊")
    public Result removeFromGroup(@RequestBody RemoveFromGroupVo removeFromGroupVo){
        xiuXianGroupService.removeFromGroup(removeFromGroupVo);
        return new Result();
    }


    @Login
    @GetMapping("/xiuxiangroup/member-name")
    @ApiOperation(value="获取群成员名字 ",response= String.class)
    public Result<String> getMemberName(@RequestParam("fromId") String fromId,@RequestParam("toId") String toId){
        String name=xiuXianGroupService.getMemberName(fromId,toId);
        return new Result<String>().ok(name);
    }

    @Login
    @GetMapping("/xiuxiangroup/new-member")
    @ApiOperation(value="获取新成员信息 ",response= XiuXianUserVo.class)
    public Result<XiuXianUserVo> getNewMember(@RequestParam("selfXiuxianId") String selfXiuxianId, @RequestParam("memberId") String memberId){
        XiuXianUserVo xiuXianUserVo=xiuXianGroupService.getNewMember(selfXiuxianId,memberId);
        return new Result<XiuXianUserVo>().ok(xiuXianUserVo);
    }

}
