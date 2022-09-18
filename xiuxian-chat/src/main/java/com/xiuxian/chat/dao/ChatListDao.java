package com.xiuxian.chat.dao;

import com.xiuxian.common.dao.BaseDao;
import com.xiuxian.chat.entity.ChatListEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 聊天列表
 * @author Chenxiao 591343671@qq.com
 */
@Mapper
public interface ChatListDao extends BaseDao<ChatListEntity> {
    List<ChatListEntity> getChatListBySelfXiuxianId(String selfXiuxianId);
}
