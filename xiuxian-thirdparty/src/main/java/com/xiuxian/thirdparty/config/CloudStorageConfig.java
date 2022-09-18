/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.xiuxian.thirdparty.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 云存储配置信息
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@ApiModel(value = "云存储配置信息")
public class CloudStorageConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "阿里云绑定的域名")
    private String aliyunDomain;
    @ApiModelProperty(value = "阿里云路径前缀")
    private String aliyunPrefix;


    @ApiModelProperty(value = "阿里云EndPoint")
    private String aliyunEndPoint;
    @ApiModelProperty(value = "阿里云AccessKeyId")
    private String aliyunAccessKeyId;
    @ApiModelProperty(value = "阿里云AccessKeySecret")
    private String aliyunAccessKeySecret;
    @ApiModelProperty(value = "阿里云BucketName")
    private String aliyunBucketName;



}