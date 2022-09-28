package com.xiuxian.websocket.entity;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Chen Xiao
 * @Description: 通知消息
 * @Date: Created in 2022/9/21
 */
@Data
public class NoticeMessageEntity {
    /**
     * 通知消息id
     */

    private Long id;

    /**
     * 消息发送者id
     */
    private String fromId;

    /**
     * 消息接收者id
     */
    private String toId;
    /**
     * 通知消息类型
     */
    private Integer noticeMessageType;

    /**
     * 通知时间戳
     */
    private Long noticeTime;

    /**
     * 通知状态
     */

    private Integer status;

    /**
     * 通知内容
     *
     */

    private String content;


}
