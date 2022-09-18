package com.xiuxian.websocket.message;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 聊天用户部分信息
 *
 * @author Chenxiao 591343671@qq.com
 */
@Data
@ApiModel(value = "用户部分信息")
public class ChatUser {
    /**
     * 修仙用户号
     */

    private String xiuxianUserId;
    /**
     * 用户的头像url
     */
    private String profile ;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 备注
     */
    private String remark;



}
