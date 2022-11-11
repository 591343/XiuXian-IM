package com.xiuxian.chat.vo.xiuxianuser;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 修仙用户信息Vo
 *
 * @author Chenxiao 591343671@qq.co
 */
@Data
@ApiModel(value = "修仙用户Vo")

public class XiuXianUserVo {
    @ApiModelProperty(value = "修仙用户id")
    private String xiuxianUserId;
    @ApiModelProperty(value = "修仙用户头像")
    private String profile;
    @ApiModelProperty(value = "修仙用户昵称")
    private String nickname;
}
