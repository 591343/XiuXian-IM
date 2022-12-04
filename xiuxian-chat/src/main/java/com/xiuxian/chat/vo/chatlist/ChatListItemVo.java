package com.xiuxian.chat.vo.chatlist;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 聊天列表项信息Vo
 *
 * @author Chenxiao 591343671@qq.co
 */
@Data
@ApiModel(value = "聊天列表项Vo")
public class ChatListItemVo {

    @ApiModelProperty(value = "聊天列表修仙用户id")
    private String friendXiuxianId;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String profile ;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "朋友类型")
    private Integer type;

    @ApiModelProperty(value = "群成员人数")
    private Integer number;

    @ApiModelProperty(value = "建立聊天时的时间戳")
    private Long startTime;
    @ApiModelProperty(value = "聊天消息列表")
    private List<ChatMessageItemVo> messages;


}
