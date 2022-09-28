package com.xiuxian.chat.service;

import com.xiuxian.chat.entity.ChatListEntity;
import com.xiuxian.chat.vo.chatlist.ChatListItemVo;
import com.xiuxian.chat.vo.chatlist.ChatListVo;


/**
 * 聊天列表
 *
 * @author Chenxiao 591343671@qq.com
 */
public interface ChatListService {
    //获取按聊天时间排序后（降序）的聊天列表
    ChatListVo getLatestChatList(String selfXiuxianId,String toId,Boolean single);

    void addChatList(ChatListEntity chatListEntity);

    ChatListItemVo getChatListItem(String selfXiuxianId, String friendXiuxianId);
}
