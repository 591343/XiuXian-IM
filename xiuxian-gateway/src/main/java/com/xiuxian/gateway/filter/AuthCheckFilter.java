package com.xiuxian.gateway.filter;


import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.xiuxian.common.exception.ErrorCode;
import com.xiuxian.common.exception.RenException;
import com.xiuxian.common.utils.Result;
import com.xiuxian.gateway.config.IgnoreUrlsConfig;
import com.xiuxian.gateway.entity.TokenEntity;
import com.xiuxian.gateway.feign.TokenFeignService;
import com.xiuxian.gateway.utils.LoginTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Author: Chen Xiao
 * @Description: 认证过滤器
 * @Date: Created in 2022/12/2
 *
 */
@Slf4j
@Component
public class AuthCheckFilter implements Ordered, GlobalFilter {
    @Autowired
    IgnoreUrlsConfig ignoreUrlsConfig;

    @Autowired
    TokenFeignService tokenFeignService;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String path = request.getURI().getPath();

        if(isMatch(path)){
            return chain.filter(exchange);
        }

        log.info("url:{},method:{}", path, request.getMethodValue());
        // 1. 获取token
        String token = request.getHeaders().getFirst(LoginTokenUtil.HEADER_TOKEN_NAME);
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        String wsToken = queryParams.getFirst(LoginTokenUtil.WS_TOKEN_NAME);

        //token为空
        if (LoginTokenUtil.validateLoginToken(token)&&LoginTokenUtil.validateLoginToken(wsToken)) {
            RenException renException = new RenException(ErrorCode.TOKEN_NOT_EMPTY);
            DataBuffer buffer = exceptionWriter(renException, response);
            response.getHeaders().set("Content-Type","application/json;charset=UTF-8");
            return response.writeWith(Mono.just(buffer));
        }

        if(!LoginTokenUtil.validateLoginToken(wsToken)){
            token=wsToken;
        }
        //查询token信息
        Result<TokenEntity> r = tokenFeignService.tokenGetByToken(token);
        TokenEntity tokenEntity = r.getData();
        if (tokenEntity == null || tokenEntity.getExpireDate().getTime() < System.currentTimeMillis()) {
            RenException renException = new RenException(ErrorCode.TOKEN_INVALID);
            DataBuffer buffer = exceptionWriter(renException, response);
            response.getHeaders().set("Content-Type","application/json;charset=UTF-8");
            return response.writeWith(Mono.just(buffer));
        }
        //添加用户id
//        request = request.mutate().header(LoginTokenUtil.USER_ID, String.valueOf(tokenEntity.getUserId())).build();
//        chain.filter(exchange.mutate().request(request).build());
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 匹配白名单路径
     * @param path
     * @return
     */
    public boolean isMatch(String path){
        List<String> urls = ignoreUrlsConfig.getUrls();
        for (String url : urls) {
            if(url.equals(path)){
                return true;
            }
        }

        return false;
    }

    public DataBuffer exceptionWriter(RenException renException, ServerHttpResponse response){
        Result result = new Result();
        result.error(renException.getCode(), renException.getMsg());
        String s = JSON.toJSONString(result);
        byte[] bits = s.getBytes(StandardCharsets.UTF_8);
        //把字节数据转换成一个DataBuffer

        return response.bufferFactory().wrap(bits);
    }

}
