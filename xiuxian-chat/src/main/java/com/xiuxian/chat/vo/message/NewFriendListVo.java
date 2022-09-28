package com.xiuxian.chat.vo.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: Chen Xiao
 * @Description: 新的朋友列表Vo
 * @Date: Created in 2022/9/21
 *
 */

@Data
@ApiModel(value = "新的朋友列表Vo")
public class NewFriendListVo {
    @ApiModelProperty("新的朋友列表")
    private List<NewFriendVo> newFriendVoList;
}


