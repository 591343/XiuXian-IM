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
@TableName("tb_chat_list")
public class ChatListEntity {
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
     * 朋友类型 0朋友 1群组
     */
    private Integer type;
}
