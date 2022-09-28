package com.xiuxian.websocket.message;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Chen Xiao
 * @Description: 通知消息
 * @Date: Created in 2022/9/21
 *
 */
@Data
@ApiModel(value = "通知消息")
public class NoticeMessage {

    @ApiModelProperty("通知消息id")
    private String id;

    @ApiModelProperty("消息发送者id")
    private String fromId;

    @ApiModelProperty("消息接收者id")
    private String toId;

    @ApiModelProperty("通知消息类型")
    private Integer noticeMessageType;

    @ApiModelProperty("通知时间戳")
    private Long noticeTime;

    @ApiModelProperty("通知状态")
    private Integer status;

    @ApiModelProperty("通知内容")
    private String content;


}
