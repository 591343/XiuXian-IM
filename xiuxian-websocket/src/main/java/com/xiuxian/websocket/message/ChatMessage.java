package com.xiuxian.websocket.message;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 聊天消息
 *
 * @author Chenxiao 591343671@qq.com
 */
@Data
@ApiModel(value = "聊天消息")
public class ChatMessage {
    /**
     * 聊天消息id
     */
    private String id;

    private ChatUser chatUser;
    /**
     * 消息发送者id
     */
    private String fromId;

    /**
     * 消息接收者id
     */

    private String toId;

    /**
     * 消息内容（文本+emoji）
     */
    private String content;

    /**
     * 聊天图片Url(存储在阿里云上)
     */

    private String remoteMediaUrl;

    /**
     * 发送消息的时间戳
     */
    private Long fromTime;

    /**
     * 对方接收到消息的时间戳
     */
    private Long toTime;

    /**
     * 消息类型 0:文本 1:图片 后期还会添加
     */
    private Integer chatMessageType;
}
