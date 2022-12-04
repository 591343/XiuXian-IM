package com.xiuxian.chat.vo.group;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: Chen Xiao
 * @Description: 移出群成员Vo
 * @Date: Created in 2022/11/29

 */
@Data
@ApiModel(value = "移出群成员Vo")
public class RemoveFromGroupVo {
    @ApiModelProperty(value = "发起人修仙号")
    private String xiuxianUserId;
    @ApiModelProperty(value = "群号")
    private String xiuxianGroupId;
    @ApiModelProperty(value = "群成员修仙号列表")
    private List<String> membersList;
}
