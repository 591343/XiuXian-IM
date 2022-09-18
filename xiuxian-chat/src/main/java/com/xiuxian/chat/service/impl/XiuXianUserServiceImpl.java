package com.xiuxian.chat.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiuxian.chat.service.XiuXianUserService;

import com.xiuxian.chat.dao.XiuXianUserDao;
import com.xiuxian.chat.dto.XiuXianUserDTO;
import com.xiuxian.chat.entity.XiuXianUserEntity;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class XiuXianUserServiceImpl implements XiuXianUserService {
    @Autowired
    XiuXianUserDao xiuXianUserDao;

    @Override
    public XiuXianUserEntity getXiuXianUser(String xiuxianUserId) {
        return xiuXianUserDao.getByXiuXianUserId(xiuxianUserId);
    }

    @Override
    public XiuXianUserEntity getXiuXianUserByMobile(String mobile) {

        return xiuXianUserDao.getByMobile(mobile);
    }

    @Override
    public void addXiuxianUserInfo(XiuXianUserDTO xiuXianUserDTO) {
        XiuXianUserEntity xiuXianUserEntity = new XiuXianUserEntity();
        BeanUtils.copyProperties(xiuXianUserDTO, xiuXianUserEntity);
        xiuXianUserDao.insert(xiuXianUserEntity);
    }

    @Override
    public Integer isXiuXianUserExist(String xiuxianUserId) {
        XiuXianUserEntity xiuXianUserEntity = xiuXianUserDao.selectOne(new QueryWrapper<XiuXianUserEntity>().eq("xiuxian_user_id", xiuxianUserId));
        if (xiuXianUserEntity != null) {
            return 1;
        }
        return 0;
    }


}
