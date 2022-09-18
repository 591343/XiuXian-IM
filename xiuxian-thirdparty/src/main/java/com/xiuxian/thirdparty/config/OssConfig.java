package com.xiuxian.thirdparty.config;


import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class OssConfig {
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
    @Bean
    public CloudStorageConfig cloudStorageConfig(@Value("${aliyun.oss.domain}")String domain,
                                                 @Value("${aliyun.oss.prefix}")String prefix,
                                                 @Value("${aliyun.oss.endPoint}") String endPoint,
                                                 @Value("${aliyun.oss.accessKeyId}") String accessKeyId,
                                                 @Value("${aliyun.oss.accessKeySecret}") String accessKeySecret,
                                                 @Value("${aliyun.oss.bucketName}") String bucketName){
        CloudStorageConfig cloudStorageConfig = new CloudStorageConfig();
        cloudStorageConfig.setAliyunDomain(domain);
        cloudStorageConfig.setAliyunPrefix(prefix);
        cloudStorageConfig.setAliyunEndPoint(endPoint);
        cloudStorageConfig.setAliyunAccessKeyId(accessKeyId);
        cloudStorageConfig.setAliyunAccessKeySecret(accessKeySecret);
        cloudStorageConfig.setAliyunBucketName(bucketName);
        return cloudStorageConfig;
    }
}
