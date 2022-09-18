package com.xiuxian.chat.dao;

import com.xiuxian.common.dao.BaseDao;
import com.xiuxian.chat.entity.FriendsEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 朋友关系
 * @author Chenxiao 591343671@qq.com
 */

@Mapper
public interface FriendsDao extends BaseDao<FriendsEntity> {

    List<FriendsEntity> getFriendsRelByXiuxianGroupId(String xiuxianGroupId);
    Integer getXiuxianUserNumberByXiuxianGroupId(String xiuxianGroupId);

    List<FriendsEntity> getFriendsRelByselfXiuxianId(String selfXiuxianId);

    List<FriendsEntity> getGroupsRelByselfXiuxianId(String selfXiuxianId);

    FriendsEntity getFriendRelByselfXiuxianIdAndFriendXiuxianId(String selfXiuxianId,String friendXiuxianId);

}
