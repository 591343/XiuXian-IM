package com.xiuxian.chat.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.xiuxian.chat.constant.Constant;
import com.xiuxian.chat.constant.NoticeMessageConstant;
import com.xiuxian.chat.dao.FriendsDao;
import com.xiuxian.chat.dao.GroupDao;
import com.xiuxian.chat.dao.XiuXianUserDao;
import com.xiuxian.chat.entity.*;
import com.xiuxian.chat.feign.WsFeignService;
import com.xiuxian.chat.service.*;
import com.xiuxian.chat.vo.chatlist.FriendListItemRelVo;
import com.xiuxian.chat.vo.friend.ChangeFriendRemarkVo;
import com.xiuxian.chat.vo.group.*;
import com.xiuxian.chat.vo.message.ChatMessage;
import com.xiuxian.chat.vo.message.NoticeMessage;
import com.xiuxian.chat.vo.xiuxianuser.XiuXianUserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.IdGenerator;
import org.springframework.util.StringUtils;

import java.util.Date;
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

    @Autowired
    ChatListService chatListService;

    @Autowired
    WsFeignService wsFeignService;

    @Autowired
    NoticeMessageService noticeMessageService;

    @Autowired
    ChatMessageService chatMessageService;

    @Autowired
    XiuXianUserService xiuXianUserService;

    @Override
    public GroupEntity getXiuXianGroup(String xiuxianGroupId) {
        GroupEntity groupEntity = groupDao.getByXiuXianGroup(xiuxianGroupId);
        return groupEntity;
    }

    @Override
    public GroupContactsVo getGroupContacts(String xiuxianUserId, String xiuxianGroupId) {
        GroupEntity groupEntity = groupDao.getByXiuXianGroup(xiuxianGroupId);
        List<FriendsEntity> friendsEntities = friendsDao.getFriendsRelByXiuxianGroupId(xiuxianGroupId);
        GroupContactsVo groupContactsVo = new GroupContactsVo();
        if (groupEntity != null) {
            BeanUtils.copyProperties(groupEntity, groupContactsVo);
            List<XiuXianUserVo> collect = friendsEntities.stream().map(item -> {
                XiuXianUserEntity xiuXianUserEntity = xiuXianUserDao.getByXiuXianUserId(item.getSelfXiuxianId());
                String remark = friendsService.getFriendRemark(xiuxianUserId, item.getSelfXiuxianId());
                XiuXianUserVo xiuXianUserVo = new XiuXianUserVo();
                xiuXianUserVo.setRemark(remark);
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

    @Transactional
    @Override
    public void inviteJoinGroup(InviteJoinGroupVo inviteJoinGroupVo) {
        //1.先建立聊天和朋友关系
        //2.给每个人发送通知消息
        //3.对于在线人员接收到通知消息后给予通知，并添加群聊和朋友,不在线的上线后自动添加
        List<String> membersList = inviteJoinGroupVo.getMembersList();
        String xiuxianGroupId = inviteJoinGroupVo.getXiuxianGroupId();
        long startTime = new Date().getTime();
        for (String xiuxianUserId : membersList) {
            if (!chatListService.isChatRel(xiuxianUserId, xiuxianGroupId)) {
                ChatListEntity chatListEntity = new ChatListEntity();
                chatListEntity.setType(Constant.GROUP_TYPE);
                chatListEntity.setSelfXiuxianId(xiuxianUserId);
                chatListEntity.setFriendXiuxianId(inviteJoinGroupVo.getXiuxianGroupId());
                chatListEntity.setStartTime(startTime);
                chatListService.addChatList(chatListEntity);
            }

            FriendsEntity friendsEntity = new FriendsEntity();
            friendsEntity.setSelfXiuxianId(xiuxianUserId);
            friendsEntity.setFriendXiuxianId(xiuxianGroupId);
            friendsEntity.setRemark("群聊");
            friendsEntity.setType(Constant.GROUP_TYPE);
            friendsEntity.setPermission(Constant.NOT_ONLY_CHAT);
            friendsEntity.setInitial("群聊");
            friendsEntity.setStartTime(startTime);
            friendsDao.insert(friendsEntity);
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setFromId(inviteJoinGroupVo.getXiuxianUserId());
            chatMessage.setToId(xiuxianGroupId);
            chatMessage.setChatMessageType(Constant.SUB_MESSAGE_TYPE);
            chatMessage.setFromTime(startTime);
            chatMessage.setContent(Constant.SUB_MESSAGE_GROUP_MEMBER_ADD + "-" + inviteJoinGroupVo.getXiuxianUserId() + "-" +
                    xiuxianUserId);
            wsFeignService.sendMsgToGroup(chatMessage);
        }
        Date date = new Date();
        for (String xiuxianUserId : membersList) {
            NoticeMessage noticeMessage = new NoticeMessage();
            noticeMessage.setFromId(xiuxianGroupId);
            noticeMessage.setToId(xiuxianUserId);
            noticeMessage.setNoticeMessageType(NoticeMessageConstant.INVITE_JOIN_GROUP_NOTICE);
            noticeMessage.setStatus(NoticeMessageConstant.SUCCESS_SEND_STATUS);
            noticeMessage.setContent("");
            noticeMessage.setNoticeTime(date.getTime());
            NoticeMessageEntity noticeMessageEntity = new NoticeMessageEntity();
            BeanUtils.copyProperties(noticeMessage, noticeMessageEntity);
            noticeMessage.setId(IdWorker.getIdStr());
            wsFeignService.sendNoticeMessageToUser(noticeMessage);
            //群向所有群成员通知有成员加入群聊.
            NoticeMessage noticeMessage1 = new NoticeMessage();
            noticeMessage1.setFromId(xiuxianUserId);
            noticeMessage1.setToId(xiuxianGroupId);
            noticeMessage1.setNoticeMessageType(NoticeMessageConstant.SOMEONE_JOIN_GROUP_NOTICE);
            noticeMessage1.setStatus(NoticeMessageConstant.SUCCESS_SEND_STATUS);
            //附带上发起邀请成员的人修仙号
            noticeMessage1.setContent(inviteJoinGroupVo.getXiuxianUserId());
            noticeMessage1.setNoticeTime(date.getTime());
            BeanUtils.copyProperties(noticeMessage, noticeMessageEntity);
            noticeMessage1.setId(IdWorker.getIdStr());
            wsFeignService.sendNoticeMessageToGroup(noticeMessage1);
        }
    }

    @Override
    public String getMemberName(String fromId, String toId) {
        String remark = friendsService.getFriendRemark(fromId, toId);

        if (!StringUtils.isEmpty(remark)) {
            return remark;
        }
        return xiuXianUserService.getNickname(toId);
    }

    @Override
    public XiuXianUserVo getNewMember(String selfXiuxianId, String memberId) {
        XiuXianUserVo xiuXianUserVo = new XiuXianUserVo();
        XiuXianUserEntity xiuXianUser = xiuXianUserService.getXiuXianUser(memberId);
        xiuXianUserVo.setXiuxianUserId(memberId);
        xiuXianUserVo.setProfile(xiuXianUser.getProfile());
        xiuXianUserVo.setNickname(xiuXianUser.getNickname());
        String remark = getMemberName(selfXiuxianId, memberId);
        xiuXianUserVo.setRemark(remark);
        return xiuXianUserVo;
    }

    @Transactional
    @Override
    public void removeFromGroup(RemoveFromGroupVo removeFromGroupVo) {
        //1.先建立聊天和朋友关系
        //2.给每个人发送通知消息
        //3.对于在线人员接收到通知消息后给予通知，并添加群聊和朋友,不在线的上线后自动添加
        List<String> membersList = removeFromGroupVo.getMembersList();
        String xiuxianGroupId = removeFromGroupVo.getXiuxianGroupId();
        long startTime = new Date().getTime();
        for (String xiuxianUserId : membersList) {
            FriendListItemRelVo friendListItemRelVo = new FriendListItemRelVo();
            friendListItemRelVo.setSelfXiuxianId(xiuxianUserId);
            friendListItemRelVo.setFriendXiuxianId(xiuxianGroupId);
            friendsService.deleteFriend(friendListItemRelVo,Constant.GROUP_TYPE);

            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setFromId(removeFromGroupVo.getXiuxianUserId());
            chatMessage.setToId(xiuxianGroupId);
            chatMessage.setChatMessageType(Constant.SUB_MESSAGE_TYPE);
            chatMessage.setFromTime(startTime);
            chatMessage.setContent(Constant.SUB_MESSAGE_GROUP_MEMBER_REMOVE + "-" + removeFromGroupVo.getXiuxianUserId() + "-" +
                    xiuxianUserId);
            wsFeignService.sendMsgToGroup(chatMessage);
        }
        Date date = new Date();
        for (String xiuxianUserId : membersList) {
            NoticeMessage noticeMessage = new NoticeMessage();
            noticeMessage.setFromId(xiuxianGroupId);
            noticeMessage.setToId(xiuxianUserId);
            noticeMessage.setNoticeMessageType(NoticeMessageConstant.REMOVE_FROM_GROUP_NOTICE);
            noticeMessage.setStatus(NoticeMessageConstant.SUCCESS_SEND_STATUS);
            noticeMessage.setContent("");
            noticeMessage.setNoticeTime(date.getTime());
            NoticeMessageEntity noticeMessageEntity = new NoticeMessageEntity();
            BeanUtils.copyProperties(noticeMessage, noticeMessageEntity);
            noticeMessage.setId(IdWorker.getIdStr());
            wsFeignService.sendNoticeMessageToUser(noticeMessage);
            //群向所有群成员通知有成员被移出群聊.
            NoticeMessage noticeMessage1 = new NoticeMessage();
            noticeMessage1.setFromId(xiuxianUserId);
            noticeMessage1.setToId(xiuxianGroupId);
            noticeMessage1.setNoticeMessageType(NoticeMessageConstant.SOMEONE_REMOVE_GROUP_NOTICE);
            noticeMessage1.setStatus(NoticeMessageConstant.SUCCESS_SEND_STATUS);
            //附带上发起邀请成员的人修仙号
            noticeMessage1.setContent(removeFromGroupVo.getXiuxianUserId());
            noticeMessage1.setNoticeTime(date.getTime());
            BeanUtils.copyProperties(noticeMessage, noticeMessageEntity);
            noticeMessage1.setId(IdWorker.getIdStr());
            wsFeignService.sendNoticeMessageToGroup(noticeMessage1);
        }
    }


}
