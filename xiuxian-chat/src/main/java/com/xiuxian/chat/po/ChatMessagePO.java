package com.xiuxian.chat.po;


import lombok.Data;

/**
 * 聊天信息
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
public class ChatMessagePO {



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
     * from->to时 发送时间
     * from<-to时 接收时间
     */
    private Long date;

    /**
     * 消息类型 0:文本 1:图片 后期还会添加
     */
    private Integer chatMessageType;
    /**
     * 是否为发送者发送
     */
    private Boolean self;
}
