package com.xiuxian.chat.service;

import com.xiuxian.chat.entity.GroupEntity;
import com.xiuxian.chat.vo.friend.ChangeFriendRemarkVo;
import com.xiuxian.chat.vo.group.*;
import com.xiuxian.chat.vo.xiuxianuser.XiuXianUserVo;


/**
 * 修仙群
 *
 * @author Chenxiao 591343671@qq.com
 */
public interface XiuXianGroupService {
    GroupEntity getXiuXianGroup(String xiuxianGroupId);

    GroupContactsVo getGroupContacts(String xiuxianUserId, String xiuxianGroupId);

    GroupInfoVo getGroupInfo(String xiuxianGroupId);


    Integer getGroupNumber(String xiuxianGroupId);

    void changGroupName(ChangGroupNameVo changGroupNameVo);

    void changGroupRemark(ChangeFriendRemarkVo changeFriendRemarkVo);

    void inviteJoinGroup(InviteJoinGroupVo inviteJoinGroupVo);

    String getMemberName(String fromId, String toId);

    XiuXianUserVo getNewMember(String selfXiuxianId, String memberId);

    void removeFromGroup(RemoveFromGroupVo removeFromGroupVo);
}
