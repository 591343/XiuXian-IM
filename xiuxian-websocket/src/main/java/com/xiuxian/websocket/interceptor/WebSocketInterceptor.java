package com.xiuxian.websocket.interceptor;


import com.xiuxian.websocket.entity.TokenEntity;
import com.xiuxian.websocket.feign.TokenFeignService;
import com.xiuxian.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Slf4j
@Component
public class WebSocketInterceptor implements HandshakeInterceptor {
    @Autowired
    private TokenFeignService tokenService;


    public static final String USER_KEY = "userId";

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception{


        log.info("--websocket的http连接握手之前--");
        ServletServerHttpRequest req = (ServletServerHttpRequest) request;


        //获取token认证
        String token = req.getServletRequest().getParameter("token");
        log.debug("token{}",token);
        //解析token获取用户信息
        //鉴权，我的方法是，前端把token传过来，解析token，判断正确与否，return true表示通过，false请求不通过。
        //TODO 鉴权设置用户
        TokenEntity tokenEntity=null;
        if (StringUtils.isNotBlank(token)) {
            Result<TokenEntity> r = tokenService.tokenGetByToken(token);
            tokenEntity =r.getData();

        }else{
            return false;
        }

        //如果token认证失败user为null，返回false拒绝握手
        if(tokenEntity == null || tokenEntity.getExpireDate().getTime() < System.currentTimeMillis()){
            return false;
        }
        //保存认证用户
        attributes.put(USER_KEY, tokenEntity.getUserId());
        return true;


    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        log.debug("完成握手");
    }


}