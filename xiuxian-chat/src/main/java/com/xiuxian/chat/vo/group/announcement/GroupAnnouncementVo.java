package com.xiuxian.chat.vo.group.announcement;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Chen Xiao
 * @Description: 群公告Vo
 * @Date: Created in 2022/11/1

 */
@Data
@ApiModel(value = "群公告Vo")
public class GroupAnnouncementVo {

    @ApiModelProperty(value = "群id")
    private String xiuxianGroupId;


    @ApiModelProperty(value = "修仙用户号")
    private String xiuxianUserId;


    @ApiModelProperty(value = "公告内容")
    private String content;

    @ApiModelProperty(value = "发布时间")
    private Date publishmentTime;
}
