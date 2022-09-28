package com.xiuxian.chat.constant;

/**
 * @Author: Chen Xiao
 * @Description: 通知消息常量
 * @Date: Created in 2022/9/21
 */
public interface NoticeMessageConstant {

    /**
     * 添加好友通知
     */
    int ADD_FRIEND_NOTICE=1;

    /**
     * 消息验证通知
     */
    int SEND_MESSAGE_NOTICE=2;

    /**
     * 加群通知
     */
    int ADD_GROUP_NOTICE=3;

    /**
     * 添加好友申请成功通知
     */
    int ADD_FRIEND_SUCCESS_NOTICE=4;

    /**
     * 消息发送成功状态
     */
    int SUCCESS_SEND_STATUS=0;

    /**
     * 等待接受状态
     */
    int WAITING_FOR_RECEIVE_STATUS=1;

    /**
     * 添加好友申请已过期状态
     */
    int EXPIREd_STATUS=2;

    /**
     * 已添加好友状态
     */
    int ADDED_STATUS=3;

    /**
     * 等待验证状态
     */
    int WAITING_FOR_VERIFY_STATUS=4;

    /**
     * 3天
     */
    int ADD_FRIEND_EXPIRED_TIME=1000*60*60*24*3;
}
