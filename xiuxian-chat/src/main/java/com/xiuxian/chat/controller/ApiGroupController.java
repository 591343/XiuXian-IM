package com.xiuxian.chat.controller;

import com.xiuxian.chat.annotation.Login;
import com.xiuxian.common.utils.Result;
import com.xiuxian.chat.entity.GroupEntity;
import com.xiuxian.chat.service.XiuXianGroupService;
import com.xiuxian.chat.vo.group.GroupContactsVo;
import com.xiuxian.chat.vo.group.GroupInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public Result<GroupContactsVo> groupContacts(@RequestParam("xiuxianGroupId") String xiuxianGroupId){
        GroupContactsVo groupContacts = xiuXianGroupService.getGroupContacts(xiuxianGroupId);
        return new Result<GroupContactsVo>().ok(groupContacts);
    }

    @Login
    @GetMapping("/xiuxiangroup/groupInfo")
    @ApiOperation(value="获取搜索时群信息", response= GroupContactsVo.class)
    public Result<GroupInfoVo> groupInfo(@RequestParam("xiuxianGroupId") String xiuxianGroupId){
        GroupInfoVo groupInfoVo = xiuXianGroupService.getGroupInfo(xiuxianGroupId);
        return new Result<GroupInfoVo>().ok(groupInfoVo);
    }
}
