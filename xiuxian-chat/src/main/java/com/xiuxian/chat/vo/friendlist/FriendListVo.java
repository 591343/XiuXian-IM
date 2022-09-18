package com.xiuxian.chat.vo.friendlist;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 聊天列表信息Vo
 *
 * @author Chenxiao 591343671@qq.co
 */
@Data
@ApiModel(value = "朋友列表Vo")
public class FriendListVo {
    @ApiModelProperty(value = "朋友列表")
    private List<FriendListItemVo> friendList;
}
