package com.xiuxian.chat.service;


import com.xiuxian.chat.dto.XiuXianUserDTO;
import com.xiuxian.chat.entity.XiuXianUserEntity;

/**
 * 修仙用户
 *
 * @author Chenxiao 591343671@qq.com
 */
public interface XiuXianUserService {
    XiuXianUserEntity getXiuXianUser(String xiuxianUserId);
    XiuXianUserEntity getXiuXianUserByMobile(String mobile);
    void addXiuxianUserInfo(XiuXianUserDTO xiuXianUserDTO);

    Integer isXiuXianUserExist(String xiuxianUserId);

    String getNickname(String xiuxianUserId);
}
