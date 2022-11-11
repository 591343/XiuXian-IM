/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.xiuxian.chat.constant;

/**
 * 通用常量
 *
 * @author Chenxiao 591343671@qq.com
 */
public interface Constant {
    /**
     * 聊天消息返回数量限制
     */
    int LIMIT = 30;

    /**
     *  朋友类型
     */
    int FRIEND_TYPE = 0;

    /**
     *  群组类型
     */
    int GROUP_TYPE = 1;

    /**
     *  待验证类型
     */
    int VALID_TYPE = 2;

    /**
     * 0:聊天、朋友圈、修仙运动
     */
    int NOT_ONLY_CHAT = 0;

    /**
     *  1:仅聊天
     */
    int ONLY_CHAT = 1;

    /**
     * 文本消息
     */
    int TEXT_CHAT_MESSAGE_TYPE=0;

    /**
     * 图片消息
     */
    int IMAGE_CHAT_MESSAGE_TYPE=1;


    /**
     * 公群公告消息
     */

    int GROUP_ANNOUNCEMENT_MESSAGE_TYPE=2;

    /**
     * 个人名片推送消息
     */
    int PERSONAL_DELIVERY_MESSAGE_TYPE=3;

}