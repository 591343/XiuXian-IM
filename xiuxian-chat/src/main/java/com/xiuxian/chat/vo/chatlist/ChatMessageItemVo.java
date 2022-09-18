package com.xiuxian.chat.vo.chatlist;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 聊天项Vo
 *
 * @author Chenxiao 591343671@qq.co
 */
@Data
@ApiModel(value = "聊天项Vo")
public class ChatMessageItemVo {



    /**
     * 消息发送者部分信息
     */
    @ApiModelProperty(value = "消息发送者部分信息")
    ChatUser chatUser;
    /**
     * 消息内容（文本+emoji）
     */
    @ApiModelProperty(value = "消息内容")
    private String content;

    /**
     * 聊天图片Url(存储在阿里云上)
     */
    @ApiModelProperty(value = "聊天图片Url")
    private String remoteMediaUrl;

    /**
     * from->to时 发送时间
     * from<-to时 接收时间
     */
    @ApiModelProperty(value = "聊天时间")
    private Long date;


    /**
     * 消息类型 0:文本 1:图片 后期还会添加
     */
    @ApiModelProperty(value = "消息类型")
    private Integer chatMessageType;

    @ApiModelProperty(value = "是否为发送者发送")
    private Boolean self;


}
