package com.xiuxian.chat.service.impl;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xiuxian.chat.dao.FriendsDao;
import com.xiuxian.chat.dao.GroupDao;
import com.xiuxian.chat.dao.XiuXianUserDao;
import com.xiuxian.chat.entity.FriendsEntity;
import com.xiuxian.chat.entity.GroupEntity;
import com.xiuxian.chat.entity.XiuXianUserEntity;
import com.xiuxian.chat.service.FriendsService;
import com.xiuxian.chat.service.XiuXianGroupService;
import com.xiuxian.chat.vo.friend.ChangeFriendRemarkVo;
import com.xiuxian.chat.vo.group.ChangGroupNameVo;
import com.xiuxian.chat.vo.group.GroupContactsVo;
import com.xiuxian.chat.vo.group.GroupInfoVo;
import com.xiuxian.chat.vo.xiuxianuser.XiuXianUserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class XiuXianGroupServiceImpl implements XiuXianGroupService {
    @Autowired
    GroupDao groupDao;
    @Autowired
    FriendsDao friendsDao;

    @Autowired
    XiuXianUserDao xiuXianUserDao;

    @Autowired
    FriendsService friendsService;

    @Override
    public GroupEntity getXiuXianGroup(String xiuxianGroupId) {
        GroupEntity groupEntity = groupDao.getByXiuXianGroup(xiuxianGroupId);
        return groupEntity;
    }

    @Override
    public GroupContactsVo getGroupContacts(String xiuxianGroupId) {
        GroupEntity groupEntity = groupDao.getByXiuXianGroup(xiuxianGroupId);
        List<FriendsEntity> friendsEntities = friendsDao.getFriendsRelByXiuxianGroupId(xiuxianGroupId);
        GroupContactsVo groupContactsVo = new GroupContactsVo();
        if (groupEntity != null) {
            BeanUtils.copyProperties(groupEntity, groupContactsVo);
            List<XiuXianUserVo> collect = friendsEntities.stream().map(item -> {
                XiuXianUserEntity xiuXianUserEntity = xiuXianUserDao.getByXiuXianUserId(item.getSelfXiuxianId());
                XiuXianUserVo xiuXianUserVo = new XiuXianUserVo();
                BeanUtils.copyProperties(xiuXianUserEntity, xiuXianUserVo);
                return xiuXianUserVo;
            }).collect(Collectors.toList());
            groupContactsVo.setXiuxianUsers(collect);
            return groupContactsVo;
        }
        return null;
    }

    @Override
    public GroupInfoVo getGroupInfo(String xiuxianGroupId) {
        GroupEntity groupEntity = groupDao.getByXiuXianGroup(xiuxianGroupId);
        Integer number = friendsDao.getXiuxianUserNumberByXiuxianGroupId(xiuxianGroupId);
        GroupInfoVo groupInfoVo = new GroupInfoVo();
        if (groupEntity != null) {
            BeanUtils.copyProperties(groupEntity, groupInfoVo);
            groupInfoVo.setNumber(number);
            return groupInfoVo;
        }
        return null;
    }

    @Override
    public Integer getGroupNumber(String xiuxianGroupId) {
        return friendsDao.getXiuxianUserNumberByXiuxianGroupId(xiuxianGroupId);
    }

    @Override
    public void changGroupName(ChangGroupNameVo changGroupNameVo) {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setGroupName(changGroupNameVo.getGroupName());

        groupDao.update(groupEntity, new UpdateWrapper<GroupEntity>().eq("xiuxian_group_id", changGroupNameVo.getXiuxianGroupId()));
    }

    @Override
    public void changGroupRemark(ChangeFriendRemarkVo changeFriendRemarkVo) {
        friendsService.changeRemark(changeFriendRemarkVo);
    }


}
