package com.xiuxian.chat.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 群公告
 * @author Chenxiao 591343671@qq.com
 */
@Data
@TableName("tb_group_announcement")
public class GroupAnnouncementEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type= IdType.AUTO)
    private Long id;

    /**
     * 群id
     */
    private String xiuxianGroupId;

    /**
     * 修仙用户号
     */
    @TableField("xiuxian_user_id")
    private String xiuxianUserId;

    /**
     * 发出公告时间
     */

    private Date publishmentTime;

    /**
     * 公告内容
     */
    private String content;
}
