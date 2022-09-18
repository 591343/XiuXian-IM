package com.xiuxian.chat.vo.friendlist;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 群组列表项信息Vo
 *
 * @author Chenxiao 591343671@qq.co
 */
@Data
@ApiModel(value = "群组列表项Vo")
public class GroupListItemVo {

    /**
     * 群id
     */
    @ApiModelProperty(value = "群组id")

    private String friendXiuxianId;

    /**
     * 群名
     */
    @ApiModelProperty(value = "群名")
    private String groupName;

    /**
     * 群头像
     */
    @ApiModelProperty(value = "群头像")
    private String groupProfile;

    /**
     * 管理员id
     */
    @ApiModelProperty(value = "群管理员id")
    private String managerId;

    @ApiModelProperty(value = "昵称首字母")
    private String initial ;

    @ApiModelProperty(value = "备注")
    private String remark ;

    @ApiModelProperty(value = "朋友类型")
    private Integer type;

    @ApiModelProperty(value = "群人数")
    private Integer number;
}
