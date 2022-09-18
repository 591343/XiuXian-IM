/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.xiuxian.websocket.interceptor;


import com.xiuxian.websocket.annotation.Login;
import com.xiuxian.websocket.entity.TokenEntity;
import com.xiuxian.websocket.feign.TokenFeignService;
import com.xiuxian.common.exception.ErrorCode;
import com.xiuxian.common.exception.RenException;
import com.xiuxian.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限(Token)验证
 *
 * @author Mark sunlightcs@gmail.com
 */
@Slf4j
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private TokenFeignService tokenService;

    public static final String USER_KEY = "userId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Login annotation;

        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
        } else {
            return true;
        }

        if (annotation == null) {
            return true;
        }

        //从header中获取token
        String token = request.getHeader("Authorization");
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }

        //token为空
        if (StringUtils.isBlank(token)) {
            throw new RenException(ErrorCode.TOKEN_NOT_EMPTY);
        }


        //查询token信息
        Result<TokenEntity> r = tokenService.tokenGetByToken(token);
        System.out.println(r);
        TokenEntity tokenEntity = r.getData();
        if (tokenEntity == null || tokenEntity.getExpireDate().getTime() < System.currentTimeMillis()) {
            throw new RenException(ErrorCode.TOKEN_INVALID);
        }

        //设置userId到request里，后续根据userId，获取用户信息
        request.setAttribute(USER_KEY, tokenEntity.getUserId());

        return true;
    }
}
