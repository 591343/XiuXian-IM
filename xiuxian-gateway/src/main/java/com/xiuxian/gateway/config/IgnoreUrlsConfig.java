package com.xiuxian.gateway.config;
 
import java.util.List;
 
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
 
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author Chen Xiao
 * @Description 请求白名单配置，这里的请求都不需要携带token
 * @Create Created in 2022/12/2
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Component
@ConfigurationProperties(prefix = "secure.ignore")
public class IgnoreUrlsConfig {
	private List<String> urls;
}