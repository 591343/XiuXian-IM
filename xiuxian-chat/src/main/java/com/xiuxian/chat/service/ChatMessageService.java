package com.xiuxian.chat.service;



import com.xiuxian.chat.entity.ChatMessageEntity;
import com.xiuxian.chat.po.ChatMessagePO;
import com.xiuxian.chat.vo.chatlist.ChatListItemRelVo;
import com.xiuxian.chat.vo.request.ChatMessageUpdateTotimeVo;

import java.util.List;

/**
 * 聊天消息
 *
 * @author Chenxiao 591343671@qq.com
 */
public interface ChatMessageService {

    List<ChatMessagePO> getChatMessagesByFrom(String fromId, String toId, Integer limit);

    void saveChatMessage(ChatMessageEntity chatMessageEntity);

    void updateChatMessageToTime(ChatMessageUpdateTotimeVo chatMessageUpdateTotimeVo);

    List<ChatMessagePO> getChatMessagesByxiuxianGroupId(String selfXiuxianId, String xiuxianGroupId, Integer limit);

    void deleteChatMessageByChatListItemRel(ChatListItemRelVo chatListItemRelVo);
}
