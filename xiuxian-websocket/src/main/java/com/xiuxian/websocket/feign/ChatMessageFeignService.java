package com.xiuxian.websocket.feign;

import com.xiuxian.common.utils.Result;
import com.xiuxian.websocket.entity.ChatMessageEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "xiuxian-chat")
public interface ChatMessageFeignService {
    @PostMapping("/api/xiuxianchatmessage/save/chatmessage")
    Result<Long> saveChatMessage(@RequestBody ChatMessageEntity chatMessageEntity);
}
