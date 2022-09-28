package com.xiuxian.chat.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: Chen Xiao
 * @Description: 通知消息
 * @Date: Created in 2022/9/21
 */
@Data
@TableName("tb_notice_message")
public class NoticeMessageEntity {
    /**
     * 通知消息id
     */
    @TableId
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
     */

    private String content;


}
