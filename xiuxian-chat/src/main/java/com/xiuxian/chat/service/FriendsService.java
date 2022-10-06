package com.xiuxian.chat.service;

import com.xiuxian.chat.entity.FriendsEntity;
import com.xiuxian.chat.vo.chatlist.FriendListItemRelVo;
import com.xiuxian.chat.vo.friend.AcceptFriendVo;
import com.xiuxian.chat.vo.friend.AddFriendVo;
import com.xiuxian.chat.vo.message.NoticeMessage;
import com.xiuxian.chat.vo.message.NoticeMessageVo;

/**
 * @Author: Chen Xiao
 * @Description: 好友关系接口
 * @Date: Created in 2022/9/22
 */
public interface FriendsService {

    void addFriend(AddFriendVo addFriendVo);
    void saveFriends(FriendsEntity friendsEntity);

    Boolean isFriends(String fromId, String toId);

    void acceptFriend(AcceptFriendVo acceptFriendVo);

    void deleteFriend(FriendListItemRelVo friendListItemRelVo);
}
