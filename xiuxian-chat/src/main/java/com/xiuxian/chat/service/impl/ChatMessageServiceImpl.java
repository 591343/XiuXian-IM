package com.xiuxian.chat.service.impl;


import com.xiuxian.chat.dao.ChatMessageDao;
import com.xiuxian.chat.entity.ChatMessageEntity;
import com.xiuxian.chat.po.ChatMessagePO;
import com.xiuxian.chat.service.ChatMessageService;
import com.xiuxian.chat.vo.request.ChatMessageUpdateTotimeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    @Autowired
    ChatMessageDao chatMessageDao;

    @Override
    public List<ChatMessagePO> getChatMessagesByFrom(String fromId, String toId, Integer limit) {
        return chatMessageDao.getChatMessagesByFromId(fromId,toId,limit);
    }

    @Override
    public void saveChatMessage(ChatMessageEntity chatMessageEntity) {
        chatMessageDao.insert(chatMessageEntity);
    }

    @Override
    public void updateChatMessageToTime(ChatMessageUpdateTotimeVo chatMessageUpdateTotimeVo) {
        ChatMessageEntity chatMessageEntity = new ChatMessageEntity();
        chatMessageEntity.setToTime(chatMessageUpdateTotimeVo.getToTime());
        chatMessageEntity.setId(chatMessageUpdateTotimeVo.getId());
        chatMessageDao.updateById(chatMessageEntity);
    }

    @Override
    public List<ChatMessagePO> getChatMessagesByxiuxianGroupId(String selfXiuxianId, String xiuxianGroupId, Integer limit) {
        return  chatMessageDao.getChatMessagesByxiuxianGroupId(selfXiuxianId,xiuxianGroupId,limit);
    }


}
