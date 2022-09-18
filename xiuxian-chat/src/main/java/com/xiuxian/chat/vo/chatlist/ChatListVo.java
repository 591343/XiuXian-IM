package com.xiuxian.chat.vo.chatlist;

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
@ApiModel(value = "聊天列表Vo")
public class ChatListVo {
    @ApiModelProperty(value = "聊天列表")
    private List<ChatListItemVo> chatList;
}
