package com.xiuxian.chat.feign;

import com.xiuxian.chat.entity.TokenEntity;
import com.xiuxian.common.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "renren-api")
public interface TokenFeignService {
    @GetMapping("/api/token/token")
    Result<TokenEntity> tokenGetByToken(@RequestParam("token") String token);
}
