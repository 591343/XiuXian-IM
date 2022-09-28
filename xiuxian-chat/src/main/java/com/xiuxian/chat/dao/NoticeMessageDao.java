package com.xiuxian.chat.dao;

import com.xiuxian.chat.entity.GroupEntity;
import com.xiuxian.chat.entity.NoticeMessageEntity;
import com.xiuxian.chat.po.ChatMessagePO;
import com.xiuxian.chat.po.ValidMessagePO;
import com.xiuxian.common.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: Chen Xiao
 * @Description: 通知消息Dao
 * @Date: Created in 2022/9/21
 */
@Mapper
public interface NoticeMessageDao extends BaseDao<NoticeMessageEntity> {
    /**
     * 获取所有的验证消息
     * @param toId
     * @param fromId
     * @param type
     * @return
     */
    List<ValidMessagePO> getValidMessages(String toId, String fromId, Integer type);

    /**
     * 获取最新的验证消息
     * @param toId
     * @param fromId
     * @param type
     * @return
     */
    List<ValidMessagePO> getLatestValidMessages(String toId, String fromId, Integer type);



}
