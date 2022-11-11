package com.xiuxian.chat.controller;

import com.xiuxian.chat.annotation.Login;
import com.xiuxian.chat.entity.GroupAnnouncementEntity;
import com.xiuxian.chat.service.GroupAnnouncementService;
import com.xiuxian.chat.vo.group.announcement.GroupAnnouncementVo;
import com.xiuxian.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Chen Xiao
 * @Description: 群公告接口
 * @Date: Created in 2022/11/1
 */
@RestController
@RequestMapping("/api")
@Api(tags="群公告接口")
public class ApiGroupAnnouncementController {

    @Autowired
    GroupAnnouncementService groupAnnouncementService;


    @Login
    @GetMapping("/xiuxian-group-announcement/group-announcement")
    @ApiOperation(value="获取群公告", response= GroupAnnouncementEntity.class)
    public Result<GroupAnnouncementEntity> getGroupAnnouncement(@RequestParam("xiuxianGroupId") String xiuxianGroupId){
        GroupAnnouncementEntity groupAnnouncement = groupAnnouncementService.getGroupAnnouncement(xiuxianGroupId);
        return new Result<GroupAnnouncementEntity>().ok(groupAnnouncement);
    }

    @Login
    @PostMapping("/xiuxian-group-announcement/group-announcement")
    @ApiOperation(value="增加或修改群公告")
    public Result addGroupAnnouncement(@RequestBody GroupAnnouncementVo groupAnnouncementVo){
        groupAnnouncementService.insertOrUpdateAnnouncement(groupAnnouncementVo);
        return new Result();
    }

    @Login
    @GetMapping("/xiuxian-group-announcement/group-announcement/received")
    @ApiOperation(value="查询用户是否已经接受了最新的群消息", response= Boolean.class)
    public Result<Boolean> groupAnnouncementReceived(@RequestParam("xiuxianGroupId") String xiuxianGroupId,@RequestParam("xiuxianUserId") String xiuxianUserId){
        Boolean received = groupAnnouncementService.isReceived(xiuxianGroupId,xiuxianUserId);
        return new Result<Boolean>().ok(received);
    }


}
