package com.xiuxian.chat.service.impl;


import com.xiuxian.chat.constant.Constant;
import com.xiuxian.chat.dao.ChatListDao;
import com.xiuxian.chat.entity.ChatListEntity;
import com.xiuxian.chat.entity.FriendsEntity;
import com.xiuxian.chat.entity.GroupEntity;
import com.xiuxian.chat.entity.XiuXianUserEntity;
import com.xiuxian.chat.po.ChatMessagePO;
import com.xiuxian.chat.service.*;
import com.xiuxian.chat.vo.chatlist.ChatListItemVo;
import com.xiuxian.chat.vo.chatlist.ChatListVo;
import com.xiuxian.chat.vo.chatlist.ChatMessageItemVo;
import com.xiuxian.chat.vo.chatlist.ChatUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Override

    public ChatListVo getLatestChatList(String selfXiuxianId) {
        List<ChatListEntity> chatLists = chatListDao.getChatListBySelfXiuxianId(selfXiuxianId);
        //缓存用于保存查询的用户记录
        Map<String,ChatUser> chatUserMap=new HashMap<>();
        List<ChatListItemVo> collect = chatLists.stream().map(item -> {
            ChatListItemVo chatListItemVo = new ChatListItemVo();
            String friendXiuxianId = item.getFriendXiuxianId();
            Integer chatType = item.getType();
            List<ChatMessagePO> chatMessagePOList=new ArrayList<>();
            if(chatType==Constant.Friend_TYPE){
                chatMessagePOList = chatMessageService.getChatMessagesByFrom(selfXiuxianId, friendXiuxianId, Constant.LIMIT);
            }else{
                chatMessagePOList = chatMessageService.getChatMessagesByxiuxianGroupId(selfXiuxianId,friendXiuxianId,Constant.LIMIT);
            }
            List<ChatMessageItemVo> messages = chatMessagePOList.stream().map(chatMessagePO -> {
                String fromId = chatMessagePO.getFromId();
                ChatMessageItemVo chatMessageItemVo = new ChatMessageItemVo();
                ChatUser chatUser = new ChatUser();
                BeanUtils.copyProperties(chatMessagePO, chatMessageItemVo);

                //当为群类型消息时才查询用户信息
                if(chatType==Constant.Group_TYPE){
                    if(!chatUserMap.containsKey(fromId)){
                        XiuXianUserEntity xiuXianUser = xiuXianUserService.getXiuXianUser(fromId);
                        BeanUtils.copyProperties(xiuXianUser,chatUser);
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

            chatListItemVo.setRemark(friendsEntity.getRemark());
            chatListItemVo.setType(friendsEntity.getType());

            if (chatType == Constant.Friend_TYPE) {
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

}
