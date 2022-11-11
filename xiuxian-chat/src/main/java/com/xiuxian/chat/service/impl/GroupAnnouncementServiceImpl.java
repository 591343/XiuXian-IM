package com.xiuxian.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xiuxian.chat.constant.NoticeMessageConstant;
import com.xiuxian.chat.dao.GroupAnnouncementDao;
import com.xiuxian.chat.dao.NoticeMessageDao;
import com.xiuxian.chat.entity.GroupAnnouncementEntity;
import com.xiuxian.chat.entity.NoticeMessageEntity;
import com.xiuxian.chat.feign.WsFeignService;
import com.xiuxian.chat.service.GroupAnnouncementService;
import com.xiuxian.chat.vo.group.announcement.GroupAnnouncementVo;
import com.xiuxian.chat.vo.message.NoticeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @Author: Chen Xiao
 * @Description: 通知消息服务
 * @Date: Created in 2022/9/21
 */
@Service
@Slf4j
public class GroupAnnouncementServiceImpl implements GroupAnnouncementService {

    @Autowired
    GroupAnnouncementDao groupAnnouncementDao;

    @Autowired
    WsFeignService wsFeignService;

    @Autowired
    NoticeMessageDao noticeMessageDao;

    @Autowired
    StringRedisTemplate redisTemplate;
    final String GROUPANN_KEY = "XIUXIANIM:CHAT:GROUPANN:RECEIVED:";

    @Override
    public GroupAnnouncementEntity getGroupAnnouncement(String xiuxianGroupId) {
        return groupAnnouncementDao.selectOne(new QueryWrapper<GroupAnnouncementEntity>().eq("xiuxian_group_id", xiuxianGroupId));
    }

    @Transactional
    @Override
    public void insertOrUpdateAnnouncement(GroupAnnouncementVo groupAnnouncementVo) {
        // 如果内容为空就删除公告
        if (StringUtils.isEmpty(groupAnnouncementVo.getContent())) {
            deleteAnnouncement(groupAnnouncementVo.getXiuxianGroupId());
            return;
        }
        GroupAnnouncementEntity groupAnnouncement = getGroupAnnouncement(groupAnnouncementVo.getXiuxianGroupId());
        GroupAnnouncementEntity groupAnnouncementEntity = new GroupAnnouncementEntity();
        groupAnnouncementEntity.setContent(groupAnnouncementVo.getContent());
        groupAnnouncementEntity.setXiuxianUserId(groupAnnouncementVo.getXiuxianUserId());
        groupAnnouncementEntity.setPublishmentTime(groupAnnouncementVo.getPublishmentTime());
        if (groupAnnouncement != null) {
            groupAnnouncementDao.update(groupAnnouncementEntity, new UpdateWrapper<GroupAnnouncementEntity>().eq("xiuxian_group_id", groupAnnouncementVo.getXiuxianGroupId()));
        } else {
            groupAnnouncementEntity.setXiuxianGroupId(groupAnnouncementVo.getXiuxianGroupId());
            groupAnnouncementDao.insert(groupAnnouncementEntity);
        }
        NoticeMessage noticeMessage = new NoticeMessage();
        noticeMessage.setFromId(groupAnnouncementVo.getXiuxianUserId());
        noticeMessage.setToId(groupAnnouncementVo.getXiuxianGroupId());
        noticeMessage.setNoticeMessageType(NoticeMessageConstant.GROUP_ANNOUNCEMENT_NOTICE);
        noticeMessage.setNoticeTime(groupAnnouncementVo.getPublishmentTime().getTime());
        noticeMessage.setStatus(NoticeMessageConstant.SUCCESS_SEND_STATUS);
        NoticeMessageEntity noticeMessageEntity = new NoticeMessageEntity();
        BeanUtils.copyProperties(noticeMessage, noticeMessageEntity);
        noticeMessage.setId(String.valueOf(noticeMessageEntity.getId()));
        noticeMessageDao.insert(noticeMessageEntity);
        try {
            wsFeignService.sendNoticeMessageToGroup(noticeMessage);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        if (redisTemplate.hasKey(GROUPANN_KEY + groupAnnouncementVo.getXiuxianGroupId())) {
            redisTemplate.delete(GROUPANN_KEY + groupAnnouncementVo.getXiuxianGroupId());
        }
        BoundSetOperations<String, String> receivedMembersSet = redisTemplate.boundSetOps(GROUPANN_KEY + groupAnnouncementVo.getXiuxianGroupId());
        receivedMembersSet.add(groupAnnouncementVo.getXiuxianUserId());
    }


    @Override
    public Boolean isReceived(String xiuxianGroupId, String xiuxianUserId) {
        if (!redisTemplate.hasKey(GROUPANN_KEY + xiuxianGroupId)) {
            return true;
        }

        BoundSetOperations<String, String> receivedMembersSet = redisTemplate.boundSetOps(GROUPANN_KEY + xiuxianGroupId);
        Boolean res = receivedMembersSet.isMember(xiuxianUserId);
        if(!res){
            receivedMembersSet.add(xiuxianUserId);
        }
        return res;
    }

    public void deleteAnnouncement(String xiuxianGroupId) {
        groupAnnouncementDao.delete(new QueryWrapper<GroupAnnouncementEntity>().eq("xiuxian_group_id", xiuxianGroupId));

    }
}
