package com.xiuxian.chat.vo.message;


import com.xiuxian.chat.po.ValidMessagePO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: Chen Xiao
 * @Description: 新的朋友Vo
 * @Date: Created in 2022/9/22
 *
 */
@Data
@ApiModel("新的朋友Vo")
public class NewFriendVo {
    @ApiModelProperty("通知消息id")
    private Long id;

    @ApiModelProperty("消息发送者id")
    private String fromId;

    @ApiModelProperty("消息发送者头像")
    private String profile;

    @ApiModelProperty("消息发送者昵称")
    private String nickname;

    @ApiModelProperty("通知时间戳")
    private Long noticeTime;

    @ApiModelProperty("通知状态")
    private Integer status;

    @ApiModelProperty("验证消息列表")
    private List<ValidMessagePO> messages;
}


