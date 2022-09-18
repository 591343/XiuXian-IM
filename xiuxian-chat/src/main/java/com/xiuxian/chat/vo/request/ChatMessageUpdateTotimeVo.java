package com.xiuxian.chat.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 聊天消息更新接收时间Vo
 * @author Chenxiao 591343671@qq.com
 */
@Data
@ApiModel(value = "聊天消息更新接收时间数据")
public class ChatMessageUpdateTotimeVo {
    @ApiModelProperty(value = "发送者id")
    private Long id;


    @ApiModelProperty(value = "接收时间")
    private Long toTime;

    public void setId(String id) {
        this.id = Long.valueOf(id);
    }
}
