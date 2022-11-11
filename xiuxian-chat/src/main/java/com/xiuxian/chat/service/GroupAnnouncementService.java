package com.xiuxian.chat.service;

import com.xiuxian.chat.entity.GroupAnnouncementEntity;
import com.xiuxian.chat.vo.group.announcement.GroupAnnouncementVo;

public interface GroupAnnouncementService {
    GroupAnnouncementEntity getGroupAnnouncement(String xiuxianGroupId);

    void insertOrUpdateAnnouncement(GroupAnnouncementVo groupAnnouncementVo);

    Boolean isReceived(String xiuxianGroupId, String xiuxianUserId);
}
