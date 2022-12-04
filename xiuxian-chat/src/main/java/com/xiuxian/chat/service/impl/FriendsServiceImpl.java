package com.xiuxian.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xiuxian.chat.constant.Constant;
import com.xiuxian.chat.constant.NoticeMessageConstant;
import com.xiuxian.chat.dao.FriendsDao;
import com.xiuxian.chat.dao.NoticeMessageDao;
import com.xiuxian.chat.dao.XiuXianUserDao;
import com.xiuxian.chat.entity.*;
import com.xiuxian.chat.feign.WsFeignService;
import com.xiuxian.chat.service.ChatListService;
import com.xiuxian.chat.service.ChatMessageService;
import com.xiuxian.chat.service.FriendsService;
import com.xiuxian.chat.service.NoticeMessageService;
import com.xiuxian.chat.vo.chatlist.ChatListItemRelVo;
import com.xiuxian.chat.vo.chatlist.ChatUser;
import com.xiuxian.chat.vo.chatlist.FriendListItemRelVo;
import com.xiuxian.chat.vo.friend.AcceptFriendVo;
import com.xiuxian.chat.vo.friend.AddFriendVo;
import com.xiuxian.chat.vo.friend.ChangeFriendPermissionVo;
import com.xiuxian.chat.vo.friend.ChangeFriendRemarkVo;
import com.xiuxian.chat.vo.message.ChatMessage;
import com.xiuxian.chat.vo.message.NoticeMessage;
import com.xiuxian.chat.vo.message.NoticeMessageVo;
import com.xiuxian.common.utils.Result;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Chen Xiao
 * @Description: 好友关系服务
 * @Date: Created in 2022/9/22
 */
@Service
public class FriendsServiceImpl implements FriendsService {
    @Autowired
    FriendsDao friendsDao;

    @Autowired
    XiuXianUserDao xiuXianUserDao;

    @Autowired
    WsFeignService wsFeignService;

    @Autowired
    NoticeMessageService noticeMessageService;

    @Autowired
    ChatMessageService chatMessageService;

    @Autowired
    ChatListService chatListService;




    @Transactional
    @Override
    public void addFriend(AddFriendVo addFriendVo) {

        //1. 添加好友
        FriendsEntity friendsEntity = new FriendsEntity();
        long startTime = new Date().getTime();
        friendsEntity.setSelfXiuxianId(addFriendVo.getNoticeMessageVo().getFromId());
        friendsEntity.setFriendXiuxianId(addFriendVo.getNoticeMessageVo().getToId());

        friendsEntity.setRemark(addFriendVo.getRemark());
        friendsEntity.setPermission(addFriendVo.getPermission());
        friendsEntity.setStartTime(startTime);

        if(!StringUtils.isEmpty(friendsEntity.getRemark())){
            friendsEntity.setInitial(getInitial(friendsEntity.getRemark()));
        }else {
            XiuXianUserEntity xiuXianUserEntity = xiuXianUserDao.selectById(addFriendVo.getNoticeMessageVo().getToId());
            friendsEntity.setInitial(getInitial(xiuXianUserEntity.getNickname()));
        }

        //如果对方没有删除你，则直接加为好友
        if(isFriends(addFriendVo.getNoticeMessageVo().getToId(),addFriendVo.getNoticeMessageVo().getFromId())){
            friendsEntity.setStartTime(startTime);
            friendsEntity.setType(Constant.FRIEND_TYPE);
            friendsDao.insert(friendsEntity);
            NoticeMessageVo noticeMessageVo = addFriendVo.getNoticeMessageVo();
            NoticeMessageEntity noticeMessageEntity = new NoticeMessageEntity();
            BeanUtils.copyProperties(noticeMessageVo,noticeMessageEntity);
            noticeMessageEntity.setFromId(noticeMessageVo.getToId());
            noticeMessageEntity.setToId(noticeMessageVo.getFromId());
            noticeMessageEntity.setContent("");
            noticeMessageEntity.setStatus(NoticeMessageConstant.ADDED_STATUS);
            noticeMessageService.saveNoticeMessage(noticeMessageEntity);

            //保存fromId->toId聊天关系
            ChatListEntity chatListEntity = new ChatListEntity();
            chatListEntity.setSelfXiuxianId(noticeMessageVo.getFromId());
            chatListEntity.setFriendXiuxianId(noticeMessageVo.getToId());
            chatListEntity.setType(Constant.FRIEND_TYPE);
            chatListEntity.setStartTime(startTime);
            chatListService.addChatList(chatListEntity);

            //给对方发送打招呼消息
            ChatMessage chatMessage = new ChatMessage();

            chatMessage.setFromId(noticeMessageVo.getFromId());
            chatMessage.setToId(noticeMessageVo.getToId());
            // 打招呼语句
            chatMessage.setContent(noticeMessageVo.getContent());
            chatMessage.setFromTime(startTime);
            chatMessage.setChatMessageType(Constant.TEXT_CHAT_MESSAGE_TYPE);
            //给朋友发送打招呼消息
            wsFeignService.sendMsgByUser(chatMessage);

        }else {
            friendsEntity.setType(Constant.VALID_TYPE);
            friendsDao.insert(friendsEntity);
            //2. 保存通知消息
            NoticeMessageVo noticeMessageVo = addFriendVo.getNoticeMessageVo();
            NoticeMessageEntity noticeMessageEntity = new NoticeMessageEntity();
            BeanUtils.copyProperties(noticeMessageVo,noticeMessageEntity);
            noticeMessageService.saveNoticeMessage(noticeMessageEntity);

            NoticeMessage noticeMessage = new NoticeMessage();
            BeanUtils.copyProperties(noticeMessageEntity,noticeMessage);
            noticeMessage.setId(String.valueOf(noticeMessageEntity.getId()));
            //3. 发送请求添加好友通知消息
            wsFeignService.sendNoticeMessageToUser(noticeMessage);

            //4. 保存消息验证通知
            noticeMessage.setNoticeMessageType(NoticeMessageConstant.SEND_MESSAGE_NOTICE);
            noticeMessage.setStatus(NoticeMessageConstant.SUCCESS_SEND_STATUS);
            noticeMessageEntity=new NoticeMessageEntity();
            BeanUtils.copyProperties(noticeMessage,noticeMessageEntity);
            noticeMessageService.saveNoticeMessage(noticeMessageEntity);
        }




    }

    @Override
    public void saveFriends(FriendsEntity friendsEntity) {
        friendsDao.insert(friendsEntity);
    }

    /**
     * 是否已添加为好友
     * @param fromId
     * @param toId
     * @return
     */
    @Override
    public Boolean isFriends(String fromId, String toId) {
        FriendsEntity friendsEntity = friendsDao.selectOne(new QueryWrapper<FriendsEntity>()
                .eq("self_xiuxian_id", fromId)
                .eq("friend_xiuxian_id", toId)
                .ne("type",Constant.VALID_TYPE));
        return friendsEntity != null;
    }


    @Transactional
    @Override
    public void acceptFriend(AcceptFriendVo acceptFriendVo) {
        //1.是否相互发送过验证消息
        long startTime = new Date().getTime();
        NoticeMessageVo noticeMessageVo = acceptFriendVo.getNoticeMessageVo();
        if(noticeMessageService.isSendValidMessage(noticeMessageVo.getFromId(), noticeMessageVo.getToId())){
           noticeMessageService.updateNoticeMessageStatus(noticeMessageVo.getFromId(), noticeMessageVo.getToId()
                   ,NoticeMessageConstant.ADD_FRIEND_NOTICE,NoticeMessageConstant.WAITING_FOR_VERIFY_STATUS
                   ,NoticeMessageConstant.ADDED_STATUS);
        }

        //修改发给自己的好友添加请求状态为已添加
        noticeMessageService.updateNoticeMessageStatus(noticeMessageVo.getToId(), noticeMessageVo.getFromId()
                , NoticeMessageConstant.ADD_FRIEND_NOTICE,NoticeMessageConstant.WAITING_FOR_RECEIVE_STATUS
                ,NoticeMessageConstant.ADDED_STATUS);

        //修改Friends关系为单聊
        FriendsEntity friendsEntity = new FriendsEntity();

        friendsEntity.setType(Constant.FRIEND_TYPE);
        friendsDao.update(friendsEntity,new UpdateWrapper<FriendsEntity>()
                .eq("self_xiuxian_id",noticeMessageVo.getToId())
                .eq("friend_xiuxian_id",noticeMessageVo.getFromId()));

        //如果还没添加为朋友先添加，否则不添加
        if(!isFriends(noticeMessageVo.getFromId(),noticeMessageVo.getToId())){
            //增加fromId->toId的friends
            friendsEntity=new FriendsEntity();
            FriendsEntity myFriend = friendsDao.selectOne(new QueryWrapper<FriendsEntity>().select("start_time").eq("self_xiuxian_id", noticeMessageVo.getToId())
                    .eq("friend_xiuxian_id", noticeMessageVo.getFromId()));
            friendsEntity.setSelfXiuxianId(noticeMessageVo.getFromId());
            friendsEntity.setFriendXiuxianId(noticeMessageVo.getToId());
            friendsEntity.setRemark(acceptFriendVo.getRemark());
            friendsEntity.setStartTime(myFriend.getStartTime());
            XiuXianUserEntity xiuXianUserEntity = xiuXianUserDao.getByXiuXianUserId(noticeMessageVo.getToId());
            if(!StringUtils.isEmpty(acceptFriendVo.getRemark())){
                friendsEntity.setInitial(getInitial(acceptFriendVo.getRemark()));
            }else {
                friendsEntity.setInitial(getInitial(xiuXianUserEntity.getNickname()));
            }
            friendsEntity.setType(Constant.FRIEND_TYPE);
            friendsEntity.setPermission(acceptFriendVo.getPermission());
            friendsDao.insert(friendsEntity);

        }



        NoticeMessage noticeMessage = new NoticeMessage();
        noticeMessage.setFromId(noticeMessageVo.getFromId());
        noticeMessage.setToId(noticeMessageVo.getToId());
        noticeMessage.setNoticeMessageType(NoticeMessageConstant.ADD_FRIEND_SUCCESS_NOTICE);
        noticeMessage.setStatus(NoticeMessageConstant.SUCCESS_SEND_STATUS);
        noticeMessage.setNoticeTime(startTime);
        noticeMessage.setContent("");
        NoticeMessageEntity noticeMessageEntity = new NoticeMessageEntity();
        BeanUtils.copyProperties(noticeMessage,noticeMessageEntity);
        noticeMessageService.saveNoticeMessage(noticeMessageEntity);
        noticeMessage.setId(String.valueOf(noticeMessageEntity.getId()));


        ChatMessageEntity chatMessageEntity = new ChatMessageEntity();
        chatMessageEntity.setFromId(noticeMessageVo.getFromId());
        chatMessageEntity.setToId(noticeMessageVo.getToId());
        // 打招呼语句
        chatMessageEntity.setContent(noticeMessageVo.getContent());
        chatMessageEntity.setFromTime(startTime);
        chatMessageEntity.setToTime(startTime);
        chatMessageEntity.setChatMessageType(Constant.TEXT_CHAT_MESSAGE_TYPE);
        chatMessageService.saveChatMessage(chatMessageEntity);


        //保存聊天关系
        ChatListEntity chatListEntity = new ChatListEntity();
        chatListEntity.setSelfXiuxianId(noticeMessageVo.getToId());
        chatListEntity.setFriendXiuxianId(noticeMessageVo.getFromId());
        chatListEntity.setType(Constant.FRIEND_TYPE);
        chatListEntity.setStartTime(startTime);
        chatListService.addChatList(chatListEntity);

        wsFeignService.sendNoticeMessageToUser(noticeMessage);
    }

    /**
     * 删除好友
     * @param friendListItemRelVo
     */
    @Transactional
    @Override
    public void deleteFriend(FriendListItemRelVo friendListItemRelVo,Integer friendType) {

        friendsDao.delete(new QueryWrapper<FriendsEntity>()
                .eq("self_xiuxian_id",friendListItemRelVo.getSelfXiuxianId())
                .eq("friend_xiuxian_id",friendListItemRelVo.getFriendXiuxianId()));

        ChatListItemRelVo chatListItemRelVo = new ChatListItemRelVo();
        chatListItemRelVo.setSelfXiuxianId(friendListItemRelVo.getSelfXiuxianId());
        chatListItemRelVo.setFriendXiuxianId(friendListItemRelVo.getFriendXiuxianId());
        //删除和好友所有通知消息,后期如果遇到特定的通知消息不能删除，再进行讨论
        noticeMessageService.deleteNoticeMessage(friendListItemRelVo.getSelfXiuxianId(),friendListItemRelVo.getFriendXiuxianId());
        if(friendType==Constant.FRIEND_TYPE){
            chatListService.deleteChatListItem(chatListItemRelVo);
        }
    }

    @Override
    public void changeRemark(ChangeFriendRemarkVo changeFriendRemarkVo) {
        FriendsEntity friendsEntity = new FriendsEntity();
        friendsEntity.setRemark(changeFriendRemarkVo.getRemark());
        friendsDao.update(friendsEntity,new UpdateWrapper<FriendsEntity>()
                .eq("self_xiuxian_id",changeFriendRemarkVo.getSelfXiuxianId())
                .eq("friend_xiuxian_id",changeFriendRemarkVo.getFriendXiuxianId()));
    }

    @Override
    public void changePermission(ChangeFriendPermissionVo changeFriendPermissionVo) {
        FriendsEntity friendsEntity = new FriendsEntity();
        friendsEntity.setPermission(changeFriendPermissionVo.getPermission());
        friendsDao.update(friendsEntity,new UpdateWrapper<FriendsEntity>()
                .eq("self_xiuxian_id",changeFriendPermissionVo.getSelfXiuxianId())
                .eq("friend_xiuxian_id",changeFriendPermissionVo.getFriendXiuxianId()));
    }

    @Override
    public String getFriendRemark(String selfXiuxianId, String friendXiuxianId) {
        FriendsEntity friendsEntity = friendsDao.selectOne(new QueryWrapper<FriendsEntity>().select("remark").eq("self_xiuxian_id", selfXiuxianId)
                .eq("friend_xiuxian_id", friendXiuxianId));
        if(friendsEntity!=null){
            return friendsEntity.getRemark();
        }
        return null;
    }

    @Override
    public FriendsEntity getFriend(String selfXiuxianId, String friendXiuxianId) {
        return friendsDao.selectOne(new QueryWrapper<FriendsEntity>().eq("self_xiuxian_id", selfXiuxianId)
                .eq("friend_xiuxian_id", friendXiuxianId));
    }

    @Override
    public void setStartTime(String selfXiuxianId, String friendXiuxianId, Long startTime) {
        FriendsEntity friendsEntity = new FriendsEntity();
        friendsEntity.setStartTime(startTime);
        friendsDao.update(friendsEntity,new UpdateWrapper<FriendsEntity>().eq("self_xiuxian_id", selfXiuxianId)
                .eq("friend_xiuxian_id", friendXiuxianId));
    }


    /**
     * 获取名称的大写首字母
     * @param name
     * @return
     */
    public String getInitial(String name){
        char c = name.charAt(0);
        Pattern compile = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher matcher = compile.matcher(c + "");
        //是汉字
        if(matcher.find()){
            String[] pingYin = PinyinHelper.toHanyuPinyinStringArray(c);
            return  String.valueOf(Character.toUpperCase(pingYin[0].charAt(0)));
        }
        if(Character.isLetter(c)){
            return String.valueOf(Character.toUpperCase(c));
        }
        return "#";
    }

}
