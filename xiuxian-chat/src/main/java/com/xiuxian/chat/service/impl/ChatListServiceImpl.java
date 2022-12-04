package com.xiuxian.chat.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xiuxian.chat.constant.Constant;
import com.xiuxian.chat.dao.ChatListDao;
import com.xiuxian.chat.entity.ChatListEntity;
import com.xiuxian.chat.entity.FriendsEntity;
import com.xiuxian.chat.entity.GroupEntity;
import com.xiuxian.chat.entity.XiuXianUserEntity;
import com.xiuxian.chat.po.ChatMessagePO;
import com.xiuxian.chat.service.*;
import com.xiuxian.chat.vo.chatlist.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatListServiceImpl implements ChatListService {
    @Autowired
    ChatListDao chatListDao;

    @Autowired
    XiuXianUserService xiuXianUserService;

    @Autowired
    ChatMessageService chatMessageService;

    @Autowired
    FriendListService friendListService;

    @Autowired
    XiuXianGroupService xiuXianGroupService;

    @Autowired
    FriendsService friendsService;





    @Override
    public ChatListVo getLatestChatList(String selfXiuxianId,String toId,Boolean single) {
        List<ChatListEntity> chatLists;
        if(single){
            chatLists=chatListDao.selectList(new QueryWrapper<ChatListEntity>()
                    .eq("self_xiuxian_id", selfXiuxianId)
                    .eq("friend_xiuxian_id", toId));

        }else {
            chatLists = chatListDao.getChatListBySelfXiuxianId(selfXiuxianId);
        }

        //缓存用于保存查询的用户记录
        Map<String,ChatUser> chatUserMap=new HashMap<>();
        List<ChatListItemVo> collect = chatLists.stream().map(item -> {
            ChatListItemVo chatListItemVo = new ChatListItemVo();
            String friendXiuxianId = item.getFriendXiuxianId();
            Integer chatType = item.getType();
            List<ChatMessagePO> chatMessagePOList;
            chatListItemVo.setNumber(0);
            chatListItemVo.setType(item.getType());
            Long startTime=item.getStartTime();
            if(chatType==Constant.FRIEND_TYPE){
                chatMessagePOList = chatMessageService.getChatMessagesByFrom(selfXiuxianId, friendXiuxianId, Constant.LIMIT,startTime);
            }else{
                chatMessagePOList = chatMessageService.getChatMessagesByxiuxianGroupId(selfXiuxianId,friendXiuxianId,Constant.LIMIT,startTime);
                //查询群成员人数
                Integer groupNumber = xiuXianGroupService.getGroupNumber(friendXiuxianId);
                chatListItemVo.setNumber(groupNumber);
            }
            List<ChatMessageItemVo> messages = chatMessagePOList.stream().map(chatMessagePO -> {
                String fromId = chatMessagePO.getFromId();
                Integer chatMessageType = chatMessagePO.getChatMessageType();
                ChatMessageItemVo chatMessageItemVo = new ChatMessageItemVo();
                ChatUser chatUser = new ChatUser();
                BeanUtils.copyProperties(chatMessagePO, chatMessageItemVo);
                //当为群类型消息时才查询用户信息
                if(chatType==Constant.GROUP_TYPE&&chatMessageType!=Constant.SUB_MESSAGE_TYPE){
                    if(!chatUserMap.containsKey(fromId)){
                        XiuXianUserEntity xiuXianUser = xiuXianUserService.getXiuXianUser(fromId);
                        String remark = friendsService.getFriendRemark(selfXiuxianId, fromId);
                        BeanUtils.copyProperties(xiuXianUser,chatUser);
                        chatUser.setRemark(remark);
                        chatUserMap.put(fromId,chatUser);
                    }else {
                        chatUser = chatUserMap.get(fromId);
                    }
                }
                chatMessageItemVo.setChatUser(chatUser);
                return chatMessageItemVo;
            }).collect(Collectors.toList());

            FriendsEntity friendsEntity = friendListService.getFriendRelByselfXiuxianIdAndFriendXiuxianId(selfXiuxianId, friendXiuxianId);

            BeanUtils.copyProperties(item, chatListItemVo);
            if(friendsEntity!=null){
                chatListItemVo.setRemark(friendsEntity.getRemark());
                chatListItemVo.setType(friendsEntity.getType());
            }

            chatListItemVo.setStartTime(startTime);

            if (chatType == Constant.FRIEND_TYPE) {
                XiuXianUserEntity xianUserEntity = xiuXianUserService.getXiuXianUser(friendXiuxianId);
                BeanUtils.copyProperties(xianUserEntity, chatListItemVo);

            } else {
                GroupEntity groupEntity = xiuXianGroupService.getXiuXianGroup(friendXiuxianId);
                chatListItemVo.setNickname(groupEntity.getGroupName());
                chatListItemVo.setProfile(groupEntity.getGroupProfile());
            }

            chatListItemVo.setMessages(messages);
            return chatListItemVo;
        }).collect(Collectors.toList());
        ChatListVo chatListVo = new ChatListVo();
        chatListVo.setChatList(collect);
        return chatListVo;
    }

    @Override
    public void addChatList(ChatListEntity chatListEntity) {
        chatListDao.insert(chatListEntity);
    }

    @Override
    public ChatListItemVo getChatListItem(String selfXiuxianId, String friendXiuxianId) {
        ChatListVo latestChatList = getLatestChatList(selfXiuxianId, friendXiuxianId,true);

        if(latestChatList.getChatList()!=null&&latestChatList.getChatList().size()!=0){
            return latestChatList.getChatList().get(0);
        }
        return null;
    }

    @Transactional
    @Override
    public void deleteChatListItem(ChatListItemRelVo chatListItemRelVo) {
        //删除聊天列表项(单方面)
        chatListDao.delete(new QueryWrapper<ChatListEntity>()
                .eq("self_xiuxian_id", chatListItemRelVo.getSelfXiuxianId())
                .eq("friend_xiuxian_id", chatListItemRelVo.getFriendXiuxianId()));

        if(!friendsService.isFriends(chatListItemRelVo.getSelfXiuxianId(),chatListItemRelVo.getFriendXiuxianId())){ //如果双方都不是朋友，删除所有聊天记录
            if(!friendsService.isFriends(chatListItemRelVo.getFriendXiuxianId(),chatListItemRelVo.getSelfXiuxianId())){
                //删除自己相关的聊天记录
                chatMessageService.deleteChatMessageByChatListItemRel(chatListItemRelVo);
                ChatListItemRelVo chatListItemRelVo1 = new ChatListItemRelVo();
                chatListItemRelVo1.setSelfXiuxianId(chatListItemRelVo.getFriendXiuxianId());
                chatListItemRelVo1.setFriendXiuxianId(chatListItemRelVo.getSelfXiuxianId());
                //删除对方相关的聊天记录
                chatMessageService.deleteChatMessageByChatListItemRel(chatListItemRelVo1);
            }
        }
    }

    /**
     * 是否为聊天关系
     * @param fromId
     * @param toId
     * @return
     */
    @Override
    public Boolean isChatRel(String fromId, String toId) {
        ChatListEntity chatListEntity = chatListDao.selectOne(new QueryWrapper<ChatListEntity>()
                .eq("self_xiuxian_id", fromId)
                .eq("friend_xiuxian_id", toId));
        return chatListEntity!=null;
    }

    @Override
    public void updateStartTime(String xiuxianUserId, String xiuxianGroupId, long startTime) {
        ChatListEntity chatListEntity = new ChatListEntity();
        chatListEntity.setStartTime(startTime);
        chatListDao.update(chatListEntity,new UpdateWrapper<ChatListEntity>().eq("self_xiuxian_id", xiuxianUserId)
                .eq("friend_xiuxian_id", xiuxianGroupId));
    }


}
