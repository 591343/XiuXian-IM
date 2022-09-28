package com.xiuxian.websocket.service.impl;


import com.xiuxian.common.utils.Result;
import com.xiuxian.websocket.constant.NoticeMessageConstant;
import com.xiuxian.websocket.entity.NoticeMessageEntity;
import com.xiuxian.websocket.feign.ChatMessageFeignService;
import com.xiuxian.websocket.feign.NoticeMessageFeignService;
import com.xiuxian.websocket.message.NoticeMessage;
import com.xiuxian.websocket.service.WSNoticeMessageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author: Chen Xiao
 * @Description: websocket通知消息接口
 * @Date: Created in 2022/9/21
 */
@Service
public class WSNoticeMessageServiceImpl implements WSNoticeMessageService {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private NoticeMessageFeignService noticeMessageFeignService;


}
