package com.xiuxian.chat.dao;

import com.xiuxian.chat.entity.ChatMessageEntity;
import com.xiuxian.chat.po.ChatMessagePO;
import com.xiuxian.common.dao.BaseDao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 聊天消息
 * @author Chenxiao 591343671@qq.com
 */
@Mapper
public interface ChatMessageDao extends BaseDao<ChatMessageEntity> {
    List<ChatMessagePO> getChatMessagesByFromId(String fromId, String toId, Integer limit);

    List<ChatMessagePO> getChatMessagesByxiuxianGroupId(String fromId, String xiuxianGroupId, Integer limit);
}
