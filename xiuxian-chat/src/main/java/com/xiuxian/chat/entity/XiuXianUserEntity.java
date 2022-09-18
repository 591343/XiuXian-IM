package com.xiuxian.chat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * 修仙用户
 *
 * @author Chenxiao 591343671@qq.com
 */
@Data
@TableName("tb_user")
@ToString
public class XiuXianUserEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type=IdType.AUTO)
    private Long id;
    /**
     * 修仙用户号
     */
    @TableField("xiuxian_user_id")
    private String xiuxianUserId;
    /**
     * 用户的头像url
     */
    private String profile ;

    /**
     * 用户手机号
     *
     */

    private String mobile;
    /**
     * 用户签名
     */
    private String signature;
    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 地区
     */
    private String area;

}
