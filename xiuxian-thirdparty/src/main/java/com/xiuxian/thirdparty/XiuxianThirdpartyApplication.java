package com.xiuxian.thirdparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.xiuxian.thirdparty.feign")
@SpringBootApplication
public class XiuxianThirdpartyApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(XiuxianThirdpartyApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        StringBuilder b=new StringBuilder();
        b.deleteCharAt(b.length()-1);
        return application.sources(XiuxianThirdpartyApplication.class);
    }
}
