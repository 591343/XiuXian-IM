package com.xiuxian.chat.vo.group;


import com.xiuxian.chat.vo.xiuxianuser.XiuXianUserVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 群人员信息Vo
 *
 * @author Chenxiao 591343671@qq.co
 */
@Data
@ApiModel(value = "群人员Vo")
public class GroupContactsVo {
    @ApiModelProperty(value = "群号")
    private String xiuxianGroupId;

    @ApiModelProperty(value = "群名")
    private String groupName;

    @ApiModelProperty(value = "群头像")
    private String groupProfile;

    @ApiModelProperty(value = "管理员Id")
    private String managerId;

    @ApiModelProperty(value = "群所有人员")
    List<XiuXianUserVo> xiuxianUsers;
}
