package com.xiuxian.chat.vo.friend;

import com.xiuxian.chat.vo.message.NoticeMessageVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Chen Xiao
 * @Description: 添接受好友Vo
 * @Date: Created in 2022/9/21
 */
@Data
@ApiModel(value = "接受好友Vo")
public class AcceptFriendVo {
    @ApiModelProperty("通知消息")
    private NoticeMessageVo noticeMessageVo;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("朋友权限")
    private Integer permission;
}
