package com.xiuxian.chat.po;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Chen Xiao
 * @Description: 验证消息Po
 * @Date: Created in 2022/9/21
 *
 */
@Data
@ApiModel("验证消息")
public class ValidMessagePO {
    @ApiModelProperty("验证消息内容")
    private String content;
    @ApiModelProperty("发送时间")
    private Long date;
    @ApiModelProperty("是否为本人发送")
    private Boolean self;
}