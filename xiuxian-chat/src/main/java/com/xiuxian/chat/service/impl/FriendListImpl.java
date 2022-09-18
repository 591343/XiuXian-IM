package com.xiuxian.chat.service.impl;


import com.xiuxian.chat.dao.FriendsDao;
import com.xiuxian.chat.entity.FriendsEntity;
import com.xiuxian.chat.entity.GroupEntity;
import com.xiuxian.chat.entity.XiuXianUserEntity;
import com.xiuxian.chat.service.FriendListService;
import com.xiuxian.chat.service.XiuXianGroupService;
import com.xiuxian.chat.service.XiuXianUserService;
import com.xiuxian.chat.vo.friendlist.FriendListItemVo;
import com.xiuxian.chat.vo.friendlist.FriendListVo;
import com.xiuxian.chat.vo.friendlist.GroupListItemVo;
import com.xiuxian.chat.vo.friendlist.GroupListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendListImpl implements FriendListService {

    @Autowired
    FriendsDao friendsDao;
    @Autowired
    XiuXianUserService xiuXianUserService;

    @Autowired
    XiuXianGroupService xiuXianGroupService;

    @Override
    public FriendListVo getFriendList(String selfXiuxianId) {
        List<FriendsEntity> friendsList=friendsDao.getFriendsRelByselfXiuxianId(selfXiuxianId);

        List<FriendListItemVo> collect = friendsList.stream().map(item -> {
            FriendListItemVo friendListItemVo = new FriendListItemVo();
            XiuXianUserEntity xianUserEntity = xiuXianUserService.getXiuXianUser(item.getFriendXiuxianId());
            BeanUtils.copyProperties(xianUserEntity, friendListItemVo);
            BeanUtils.copyProperties(item, friendListItemVo);
            return friendListItemVo;
        }).collect(Collectors.toList());

        FriendListVo friendListVo = new FriendListVo();
        friendListVo.setFriendList(collect);
        return friendListVo;
    }

    @Override
    public GroupListVo getGroupList(String selfXiuxianId) {
        List<FriendsEntity> groupList=friendsDao.getGroupsRelByselfXiuxianId(selfXiuxianId);

        List<GroupListItemVo> collect = groupList.stream().map(item -> {
            GroupListItemVo groupListItemVo = new GroupListItemVo();
            GroupEntity groupEntity = xiuXianGroupService.getXiuXianGroup(item.getFriendXiuxianId());

            Integer number = friendsDao.getXiuxianUserNumberByXiuxianGroupId(item.getFriendXiuxianId());
            groupListItemVo.setNumber(number);
            BeanUtils.copyProperties(groupEntity, groupListItemVo);
            BeanUtils.copyProperties(item, groupListItemVo);
            return groupListItemVo;
        }).collect(Collectors.toList());

        GroupListVo groupListVo = new GroupListVo();
        groupListVo.setGroupList(collect);
        return groupListVo;
    }

    @Override
    public FriendsEntity getFriendRelByselfXiuxianIdAndFriendXiuxianId(String selfXiuxianId, String friendXiuxianId) {
        return friendsDao.getFriendRelByselfXiuxianIdAndFriendXiuxianId(selfXiuxianId, friendXiuxianId);
    }




}
