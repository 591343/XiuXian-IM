package com.xiuxian.chat.feign;


import com.xiuxian.chat.vo.message.ChatMessage;
import com.xiuxian.chat.vo.message.NoticeMessage;
import com.xiuxian.common.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "xiuxian-websocket")
public interface WsFeignService {
    //单发 订阅/user/{xiuxianUserId}/notice
    @PostMapping("/api/ws/sendNoticeMessageToUser")
    Result sendNoticeMessageToUser(@RequestBody NoticeMessage noticeMessage);


    //单聊 订阅/user/{xiuxianUserId}/chat
    @PostMapping("/api/ws/sendMsgToUser")
    Result sendMsgByUser(@RequestBody ChatMessage chatMessage);

    @PostMapping("/api/ws/sendNoticeMessageToGroup")
    Result sendNoticeMessageToGroup(@RequestBody NoticeMessage noticeMessage);
}
