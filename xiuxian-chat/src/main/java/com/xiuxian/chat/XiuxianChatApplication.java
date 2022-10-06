package com.xiuxian.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@ComponentScan(value = "com.xiuxian")
@EnableFeignClients(basePackages = "com.xiuxian.chat.feign")
@EnableDiscoveryClient
@SpringBootApplication
public class XiuxianChatApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(XiuxianChatApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		StringBuilder b=new StringBuilder();
		b.deleteCharAt(b.length()-1);
		return application.sources(XiuxianChatApplication.class);
	}
}
