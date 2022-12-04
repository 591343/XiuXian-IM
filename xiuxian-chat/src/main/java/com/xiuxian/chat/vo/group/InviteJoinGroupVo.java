package com.xiuxian.chat.vo.group;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: Chen Xiao
 * @Description: 邀请加入群聊Vo
 * @Date: Created in 2022/11/19

 */
@Data
@ApiModel(value = "邀请加入群聊Vo")
public class InviteJoinGroupVo {
    @ApiModelProperty(value = "发起人修仙号")
    private String xiuxianUserId;
    @ApiModelProperty(value = "群号")
    private String xiuxianGroupId;
    @ApiModelProperty(value = "群成员修仙号列表")
    private List<String> membersList;
}
