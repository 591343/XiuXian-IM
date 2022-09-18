package com.xiuxian.chat.dao;

import com.xiuxian.chat.entity.XiuXianUserEntity;
import com.xiuxian.common.dao.BaseDao;

import org.apache.ibatis.annotations.Mapper;

/**
 * 修仙用户
 * @author Chenxiao 591343671@qq.com
 */

@Mapper
public interface XiuXianUserDao extends BaseDao<XiuXianUserEntity> {


    XiuXianUserEntity getByXiuXianUserId(String xiuxianUserId);
    XiuXianUserEntity getByMobile(String mobile);
    XiuXianUserEntity getById(Long id);
}
