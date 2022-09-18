package com.xiuxian.chat.vo.chatlist;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 消息发送者部分信息
 */
@Data
@ApiModel(value = "消息发送者部分信息")
public class ChatUser {
        /**
         * 修仙用户号
         */

        private String xiuxianUserId;
        /**
         * 用户的头像url
         */
        private String profile ;

        /**
         * 昵称
         */
        private String nickname;

        /**
         * 备注
         */
        private String remark;
    }