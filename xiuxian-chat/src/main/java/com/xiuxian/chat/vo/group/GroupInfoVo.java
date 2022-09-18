package com.xiuxian.chat.vo.group;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 群人员信息Vo
 *
 * @author Chenxiao 591343671@qq.co
 */
@Data
@ApiModel(value = "查找时的群信息Vo")
public class GroupInfoVo {
    @ApiModelProperty(value = "群号")
    private String xiuxianGroupId;

    @ApiModelProperty(value = "群名")
    private String groupName;

    @ApiModelProperty(value = "群头像")
    private String groupProfile;

    @ApiModelProperty(value = "管理员Id")
    private String managerId;

    @ApiModelProperty(value = "群人数")
    private Integer number;
}
