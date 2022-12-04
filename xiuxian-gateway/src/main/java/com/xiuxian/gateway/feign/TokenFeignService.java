package com.xiuxian.gateway.feign;


import com.xiuxian.common.utils.Result;
import com.xiuxian.gateway.entity.TokenEntity;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//path为调用服务的context-path
@FeignClient(name="renren-api")
public interface TokenFeignService {
    @GetMapping("/api/token/token")
    Result<TokenEntity> tokenGetByToken(@RequestParam("token") String token);
}
