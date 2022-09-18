package com.xiuxian.chat.dto;


import com.xiuxian.chat.annotation.Sex;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 修仙用户表单
 * @author Chenxiao 591343671@qq.com
 */
@Data
@ApiModel(value = "修仙用户表单")
public class XiuXianUserDTO {

    /**
     * 修仙用户号
     */
    @ApiModelProperty(value = "修仙号")
    @NotBlank(message="修仙号不能为空")
    private String xiuxianUserId;
    /**
     * 用户的头像url
     */
    @ApiModelProperty(value = "修仙用户头像")
    @NotBlank(message="修仙用户头像url不能为空")
    private String profile ;

    /**
     * 用户手机号
     *
     */
    @ApiModelProperty(value = "手机号")
    @NotBlank(message="手机号不能为空")
    @Pattern(regexp = "^1(3\\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$",message = "不是手机号")
    private String mobile;
    /**
     * 用户签名
     */
    @ApiModelProperty(value = "个性签名")
    @NotBlank(message="个性签名不能为空")
    private String signature;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    @NotBlank(message="昵称不能为空")
    private String nickname;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    @Sex
    private Integer sex;

    /**
     * 地区
     */
    @ApiModelProperty(value = "地区")
    @NotBlank(message="地区不能为空")
    private String area;
}
