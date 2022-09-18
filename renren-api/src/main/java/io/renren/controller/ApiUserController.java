package io.renren.controller;


import io.renren.annotation.Login;
import com.xiuxian.common.utils.Result;
import io.renren.entity.UserEntity;
import io.renren.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户接口
 *
 * @author Chenxiao 591343671@qq.com
 */
@RestController
@RequestMapping("/api")
@Api(tags = "用户接口")
public class ApiUserController {

    @Autowired
    UserService userService;

    @Login
    @GetMapping("/user/user")
    @ApiOperation(value = "获取用户信息", response = UserEntity.class)
    public Result<UserEntity> userInfoByMobile(@RequestParam("mobile") String mobile) {
        UserEntity userEntity = userService.getByMobile(mobile);
        return new Result<UserEntity>().ok(userEntity);
    }


}
