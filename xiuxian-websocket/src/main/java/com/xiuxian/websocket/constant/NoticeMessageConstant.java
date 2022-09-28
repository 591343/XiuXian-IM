package com.xiuxian.websocket.constant;

/**
 * @Author: Chen Xiao
 * @Description: 通知消息常量
 * @Date: Created in 2022/9/21
 */
public interface NoticeMessageConstant {

    //添加好友通知
    int ADD_FRIEND_NOTICE=1;
    //消息验证通知
    int SEND_MESSAGE_NOTICE=2;
    //加群通知
    int ADD_GROUP_NOTICE=3;

    //消息发送成功状态
    int SUCCESS_SEND_STATUS=0;

    //等待接受状态
    int WAITING_FOR_RECEIVE_STATUS=1;
}
