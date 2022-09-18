package io.renren.controller;

import io.renren.annotation.Login;
import com.xiuxian.common.utils.Result;
import io.renren.entity.TokenEntity;
import io.renren.entity.UserEntity;
import io.renren.service.TokenService;
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
@Api(tags = "令牌接口")
public class ApiTokenController {

    @Autowired
    TokenService tokenService;

    @Login
    @GetMapping("/token/token")
    @ApiOperation(value = "获取用户信息", response = TokenEntity.class)
    public Result<TokenEntity> tokenGetByToken(@RequestParam("token") String token) {
        TokenEntity userEntity = tokenService.getByToken(token);
        return new Result<TokenEntity>().ok(userEntity);
    }
}
