package com.xiuxian.chat.vo.group;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Chen Xiao
 * @Description: 修改群名Vo
 * @Date: Created in 2022/10/18

 */
@Data
@ApiModel(value = "修改群名Vo")
public class ChangGroupNameVo {
    @ApiModelProperty(value = "群号")
    private String xiuxianGroupId;

    @ApiModelProperty(value = "群名")
    private String groupName;
}
