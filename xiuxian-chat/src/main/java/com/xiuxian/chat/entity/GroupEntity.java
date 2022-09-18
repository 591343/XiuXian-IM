package com.xiuxian.chat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 群
 *
 * @author Chenxiao 591343671@qq.com
 */
@Data
@TableName("tb_group")
public class GroupEntity {
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
     * 群名
     */

    private String groupName;

    /**
     * 群头像
     */

    private String groupProfile;

    /**
     * 管理员id
     */
    private String managerId;
}
