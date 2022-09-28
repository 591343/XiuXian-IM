package com.xiuxian.chat.service;


import com.xiuxian.chat.dao.NoticeMessageDao;
import com.xiuxian.chat.entity.ChatMessageEntity;
import com.xiuxian.chat.entity.NoticeMessageEntity;
import com.xiuxian.chat.vo.message.NewFriendListVo;
import com.xiuxian.chat.vo.message.NoticeMessage;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: Chen Xiao
 * @Description: 通知消息服务接口
 * @Date: Created in 2022/9/21
 */
public interface NoticeMessageService {


    void saveNoticeMessage(NoticeMessageEntity noticeMessageEntity);

    NewFriendListVo newFriendNoticeMessage(String xiuxianUserId);

    Integer sendValidMessage(NoticeMessage noticeMessage);

    Boolean  isSendValidMessage(String fromId,String toId);

    void updateNoticeMessageStatus(String fromId,String toId,Integer NoticeMessageType,Integer status,Integer updateStatus);
}
