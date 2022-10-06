package com.xiuxian.chat.vo.chatlist;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Chen Xiao
 * @Description: 朋友列表项关系Vo
 * @Date: Created in 2022/10/4
 */
@Data
@ApiModel(value = "朋友列表项关系Vo")
public class FriendListItemRelVo {
    @ApiModelProperty(value = "朋友列表项自身id")
    private String selfXiuxianId;
    @ApiModelProperty(value = "朋友列表项朋友id")
    private String friendXiuxianId;
}
