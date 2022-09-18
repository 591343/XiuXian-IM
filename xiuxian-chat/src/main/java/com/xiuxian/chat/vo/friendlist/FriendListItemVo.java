package com.xiuxian.chat.vo.friendlist;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 朋友列表项信息Vo
 *
 * @author Chenxiao 591343671@qq.co
 */
@Data
@ApiModel(value = "朋友列表项Vo")
public class FriendListItemVo {

    @ApiModelProperty(value = "聊天列表修仙用户id")
    private String friendXiuxianId;

    @ApiModelProperty(value = "头像")
    private String profile ;

    @ApiModelProperty(value = "个性签名")
    private String signature;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "昵称首字母")
    private String initial ;

    @ApiModelProperty(value = "备注")
    private String remark ;

    @ApiModelProperty(value = "地区")
    private String area;

    @ApiModelProperty(value = "朋友类型")
    private Integer type;




}
