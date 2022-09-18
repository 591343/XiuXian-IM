package com.xiuxian.chat.controller;


import com.xiuxian.chat.annotation.Login;
import com.xiuxian.common.utils.Result;
import com.xiuxian.common.validator.ValidatorUtils;
import com.xiuxian.chat.dto.XiuXianUserDTO;
import com.xiuxian.chat.entity.XiuXianUserEntity;
import com.xiuxian.chat.service.XiuXianUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 修仙用户接口
 *
 * @author Chenxiao 591343671@qq.com
 */
@RestController
@RequestMapping("/api")
@Api(tags = "修仙用户接口")
public class ApiXiuXianUserController {

    @Autowired
    XiuXianUserService xiuXianUserService;

    @Login
    @GetMapping("/xiuxianuser/friend")
    @ApiOperation(value = "获取修仙用户信息", response = XiuXianUserEntity.class)
    public Result<XiuXianUserEntity> userInfoByXiuXianUserId(@RequestParam("xiuxianUserId") String xiuxianUserId) {
        XiuXianUserEntity xiuXianUser = xiuXianUserService.getXiuXianUser(xiuxianUserId);
        return new Result<XiuXianUserEntity>().ok(xiuXianUser);
    }

    @Login
    @GetMapping("/xiuxianuser/user")
    @ApiOperation(value = "获取修仙用户信息", response = XiuXianUserEntity.class)
    public Result<XiuXianUserEntity> userInfoByMobile(@RequestParam("mobile") String mobile) {
        XiuXianUserEntity xiuXianUser = xiuXianUserService.getXiuXianUserByMobile(mobile);
        return new Result<XiuXianUserEntity>().ok(xiuXianUser);

    }

    @Login
    @PostMapping("/xiuxianuser/user")
    @ApiOperation(value = "添加修仙用户信息", response = XiuXianUserEntity.class)
    public Result addXiuxianUserInfo(@RequestBody XiuXianUserDTO xiuXianUserDTO) {
        //表单校验
        ValidatorUtils.validateEntity(xiuXianUserDTO);
        xiuXianUserService.addXiuxianUserInfo(xiuXianUserDTO);
        return new Result();

    }

    @Login
    @GetMapping("/xiuxianuser/user/exist")
    @ApiOperation(value = "判断修仙用户是否存在. 0: 不存在、1:存在", response = Integer.class)
    public Result<Map<String,Object>> isXiuXianUserExist(@RequestParam("xiuxianUserId") String xiuxianUserId) {
        Integer resutlt = xiuXianUserService.isXiuXianUserExist(xiuxianUserId);
        Map<String, Object> map = new HashMap<>();
        map.put("exist",resutlt);
        return new Result().ok(map);

    }




}
