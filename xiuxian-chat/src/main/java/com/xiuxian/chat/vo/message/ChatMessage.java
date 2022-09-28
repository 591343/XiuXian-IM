package com.xiuxian.chat.vo.message;

import com.xiuxian.chat.vo.chatlist.ChatUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 聊天消息
 *
 * @author Chenxiao 591343671@qq.com
 */
@Data
@ApiModel(value = "聊天消息")
public class ChatMessage {

    @ApiModelProperty("聊天消息id")
    private String id;

    @ApiModelProperty("聊天用户")
    private ChatUser chatUser;

    @ApiModelProperty("消息发送者Id")
    private String fromId;

    @ApiModelProperty("消息接收者Id")
    private String toId;

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("聊天图片Url")
    private String remoteMediaUrl;

    @ApiModelProperty("发送消息的时间戳")
    private Long fromTime;

    @ApiModelProperty("对方接收到消息的时间戳")
    private Long toTime;

    @ApiModelProperty("消息类型 0:文本 1:图片 后期还会添加")
    private Integer chatMessageType;
}