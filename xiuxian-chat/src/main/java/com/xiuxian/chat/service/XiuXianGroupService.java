package com.xiuxian.chat.service;

import com.xiuxian.chat.entity.GroupEntity;
import com.xiuxian.chat.vo.group.GroupContactsVo;
import com.xiuxian.chat.vo.group.GroupInfoVo;


/**
 * 修仙群
 *
 * @author Chenxiao 591343671@qq.com
 */
public interface XiuXianGroupService {
    GroupEntity getXiuXianGroup(String xiuxianGroupId);

    GroupContactsVo getGroupContacts(String xiuxianGroupId);

    GroupInfoVo getGroupInfo(String xiuxianGroupId);


}
