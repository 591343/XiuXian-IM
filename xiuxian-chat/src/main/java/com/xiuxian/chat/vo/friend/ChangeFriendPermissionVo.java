package com.xiuxian.chat.vo.friend;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Chen Xiao
 * @Description: 修改好友权限Vo
 * @Date: Created in 2022/10/20
 @ApiModel(value = "修改好友权限Vo")
 */
@Data
public class ChangeFriendPermissionVo {
    @ApiModelProperty(value = "朋友列表项自身id")
    private String selfXiuxianId;
    @ApiModelProperty(value = "朋友列表项朋友id")
    private String friendXiuxianId;
    @ApiModelProperty(value = "好友的权限")
    private Integer permission;
}
