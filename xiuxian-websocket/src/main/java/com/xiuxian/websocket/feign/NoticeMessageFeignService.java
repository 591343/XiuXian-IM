package com.xiuxian.websocket.feign;

import com.xiuxian.common.utils.Result;
import com.xiuxian.websocket.entity.NoticeMessageEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "xiuxian-chat")
public interface NoticeMessageFeignService {

    @PostMapping("/api/xiuxiannoticemessage/save/noticemessage")
    Result<Long> saveNoticeMessage(@RequestBody NoticeMessageEntity noticeMessageEntity);

}
