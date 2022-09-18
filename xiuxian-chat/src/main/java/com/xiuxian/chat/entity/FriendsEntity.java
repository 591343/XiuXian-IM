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
@TableName("tb_friends")
public class FriendsEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type= IdType.AUTO)
    private Long id;

    /**
     * 用户自身修仙id
     */
    private String selfXiuxianId;

    /**
     * 关联的群或其它修仙用户id
     */

    private String friendXiuxianId;

    /**
     * 备注
     */

    private String remark;

    /**
     * 朋友类型 0：朋友 1：群组
     */
    private Integer type;

    /**
     * 分类名称
     */
    private String initial;
}
