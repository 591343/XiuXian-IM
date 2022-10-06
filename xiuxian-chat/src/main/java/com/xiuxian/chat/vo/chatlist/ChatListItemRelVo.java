package com.xiuxian.chat.vo.chatlist;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Chen Xiao
 * @Description: 聊天列表项关系Vo
 * @Date: Created in 2022/9/30
 */
@Data
@ApiModel(value = "聊天列表项关系Vo")
public class ChatListItemRelVo {
    @ApiModelProperty(value = "聊天列表项自身id")
    private String selfXiuxianId;
    @ApiModelProperty(value = "聊天列表项朋友id")
    private String friendXiuxianId;
}
