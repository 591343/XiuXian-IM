package com.xiuxian.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xiuxian.chat.constant.Constant;
import com.xiuxian.chat.constant.NoticeMessageConstant;
import com.xiuxian.chat.dao.NoticeMessageDao;
import com.xiuxian.chat.entity.NoticeMessageEntity;
import com.xiuxian.chat.entity.XiuXianUserEntity;
import com.xiuxian.chat.feign.WsFeignService;
import com.xiuxian.chat.po.ValidMessagePO;
import com.xiuxian.chat.service.NoticeMessageService;
import com.xiuxian.chat.service.XiuXianUserService;
import com.xiuxian.chat.vo.message.NewFriendListVo;
import com.xiuxian.chat.vo.message.NewFriendVo;
import com.xiuxian.chat.vo.message.NoticeMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.xiuxian.chat.constant.NoticeMessageConstant.*;

/**
 * @Author: Chen Xiao
 * @Description: 通知消息服务
 * @Date: Created in 2022/9/21
 */
@Service
public class NoticeMessageServiceImpl implements NoticeMessageService {

    @Autowired
    NoticeMessageDao noticeMessageDao;
    @Autowired
    XiuXianUserService xiuXianUserService;

    @Autowired
    WsFeignService wsFeignService;


    @Override
    public void saveNoticeMessage(NoticeMessageEntity noticeMessageEntity) {
        noticeMessageDao.insert(noticeMessageEntity);
    }

    @Override
    @Transactional
    public NewFriendListVo newFriendNoticeMessage(String xiuxianUserId) {

        List<NoticeMessageEntity> noticeMessages = noticeMessageDao.
                selectList(new QueryWrapper<NoticeMessageEntity>().eq("to_id", xiuxianUserId)
                        .eq("notice_message_type", NoticeMessageConstant.ADD_FRIEND_NOTICE));

        Date date = new Date();

        List<NewFriendVo> collect = noticeMessages.stream().map(item -> {
            NewFriendVo newFriendVo = new NewFriendVo();
            String fromId = item.getFromId();
            XiuXianUserEntity xiuXianUser = xiuXianUserService.getXiuXianUser(fromId);
            newFriendVo.setProfile(xiuXianUser.getProfile());
            newFriendVo.setNickname(xiuXianUser.getNickname());
            Long noticeTime = item.getNoticeTime();
            long now = date.getTime();
            //如果添加好友申请超过3天则更改为已过期状态
            if (now - noticeTime > NoticeMessageConstant.ADD_FRIEND_EXPIRED_TIME&&item.getStatus()!= ADDED_STATUS&&item.getStatus()!=EXPIREd_STATUS) {
                NoticeMessageEntity noticeMessageEntity = new NoticeMessageEntity();
                noticeMessageEntity.setId(item.getId());
                noticeMessageEntity.setStatus(NoticeMessageConstant.EXPIREd_STATUS);
                noticeMessageDao.updateById(noticeMessageEntity);
                item.setStatus(NoticeMessageConstant.EXPIREd_STATUS);
            }

            List<ValidMessagePO> validMessages;
            if (item.getStatus() == NoticeMessageConstant.ADDED_STATUS) {
                validMessages = noticeMessageDao.getLatestValidMessages(xiuxianUserId, item.getFromId(), NoticeMessageConstant.SEND_MESSAGE_NOTICE);
            } else {
                validMessages = noticeMessageDao.getValidMessages(xiuxianUserId, item.getFromId(), NoticeMessageConstant.SEND_MESSAGE_NOTICE);
            }
            newFriendVo.setMessages(validMessages);
            BeanUtils.copyProperties(item, newFriendVo);

            return newFriendVo;
        }).collect(Collectors.toList());
        NewFriendListVo newFriendListVo = new NewFriendListVo();
        newFriendListVo.setNewFriendVoList(collect);

        return newFriendListVo;
    }

    @Transactional
    @Override
    public Integer sendValidMessage(NoticeMessage noticeMessage) {
        //查询对方近期添加好友请求是否过期。
        if (isLatestMessageExpired(noticeMessage.getFromId(), noticeMessage.getToId())) {
            return NoticeMessageConstant.EXPIREd_STATUS;
        }
        //查询是否为第一次接收验证回复消息
        List<NoticeMessageEntity> noticeMessageEntities = noticeMessageDao.selectList(new QueryWrapper<NoticeMessageEntity>()
                .eq("from_id", noticeMessage.getFromId())
                .eq("to_id", noticeMessage.getToId())
                .eq("notice_message_type", NoticeMessageConstant.SEND_MESSAGE_NOTICE).last("limit 1"));


        //没有发送过
        if (noticeMessageEntities.size() == 0) {
            //保存等待验证为好友通知消息
            NoticeMessageEntity noticeMessageEntity1 = new NoticeMessageEntity();
            BeanUtils.copyProperties(noticeMessage, noticeMessageEntity1);
            noticeMessageEntity1.setNoticeMessageType(NoticeMessageConstant.ADD_FRIEND_NOTICE);
            noticeMessageEntity1.setStatus(NoticeMessageConstant.WAITING_FOR_VERIFY_STATUS);
            noticeMessageEntity1.setContent(""); //不需要内容
            saveNoticeMessage(noticeMessageEntity1);
        }

        //保存 验证回复消息并用ws发送
        NoticeMessageEntity validMessageEntity = new NoticeMessageEntity();
        BeanUtils.copyProperties(noticeMessage, validMessageEntity);
        saveNoticeMessage(validMessageEntity);
        noticeMessage.setId(String.valueOf(validMessageEntity.getId()));
        wsFeignService.sendNoticeMessageToUser(noticeMessage);

        return SUCCESS_SEND_STATUS;
    }


    /**
     * 判断最新的好友添加消息是否过期
     *
     * @param fromId
     * @param toId
     * @return
     */
    private Boolean isLatestMessageExpired(String fromId, String toId) {
        NoticeMessageEntity noticeMessageEntity = noticeMessageDao.selectOne(new QueryWrapper<NoticeMessageEntity>().select("notice_time")
                .eq("from_id", fromId)
                .eq("to_id", toId)
                .eq("notice_message_type", ADD_FRIEND_NOTICE)
                .orderByDesc("notice_time").last("limit 1"));

        if (noticeMessageEntity == null) return false;
        long nowTimeStamp = new Date().getTime();
        return nowTimeStamp - noticeMessageEntity.getNoticeTime() > NoticeMessageConstant.ADD_FRIEND_EXPIRED_TIME;
    }


    /**
     * 是否发送过验证回复消息
     * @param fromId
     * @param toId
     * @return
     */
    public Boolean  isSendValidMessage(String fromId,String toId){
        List<NoticeMessageEntity> noticeMessageEntities = noticeMessageDao.selectList(new QueryWrapper<NoticeMessageEntity>()
                .eq("from_id", fromId)
                .eq("to_id", toId)
                .eq("notice_message_type", SEND_MESSAGE_NOTICE).last("limit 1"));

        return noticeMessageEntities.size()!=0;
    }

    @Override
    public void updateNoticeMessageStatus(String fromId, String toId, Integer noticeMessageType, Integer status,Integer updateStatus) {
        NoticeMessageEntity noticeMessageEntity = new NoticeMessageEntity();

        noticeMessageEntity.setStatus(updateStatus);

        noticeMessageDao.update(noticeMessageEntity,new UpdateWrapper<NoticeMessageEntity>()
                .eq("from_id",fromId)
                .eq("to_id",toId)
                .eq("notice_message_type",noticeMessageType)
                .eq("status",status));
    }

    /**
     * 只保留对方已添加消息
     * @param fromId
     * @param toId
     */
    @Override
    public void deleteNoticeMessage(String fromId, String toId) {
        noticeMessageDao.delete(new QueryWrapper<NoticeMessageEntity>().eq("from_id", toId)
                .eq("to_id", fromId).ne("notice_message_type", SEND_MESSAGE_NOTICE));
        noticeMessageDao.delete(new QueryWrapper<NoticeMessageEntity>().eq("from_id", fromId)
                .eq("to_id", toId).ne("notice_message_type", ADD_FRIEND_NOTICE).ne("notice_message_type",SEND_MESSAGE_NOTICE));
    }
}
