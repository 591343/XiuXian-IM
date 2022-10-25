package com.xiuxian.chat.vo.friend;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Chen Xiao
 * @Description: 修改好友备注Vo
 * @Date: Created in 2022/10/13
 @ApiModel(value = "修改好友备注Vo")
 */
@Data
public class ChangeFriendRemarkVo {
    @ApiModelProperty(value = "朋友列表项自身id")
    private String selfXiuxianId;
    @ApiModelProperty(value = "朋友列表项朋友id")
    private String friendXiuxianId;
    @ApiModelProperty(value = "好友的备注")
    private String remark;
}
