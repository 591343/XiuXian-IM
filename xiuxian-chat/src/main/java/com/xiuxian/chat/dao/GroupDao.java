package com.xiuxian.chat.dao;

import com.xiuxian.common.dao.BaseDao;
import com.xiuxian.chat.entity.GroupEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 群
 * @author Chenxiao 591343671@qq.com
 */

@Mapper
public interface GroupDao extends BaseDao<GroupEntity> {

    GroupEntity getByXiuXianGroup(String xiuxianGroupId);


}
