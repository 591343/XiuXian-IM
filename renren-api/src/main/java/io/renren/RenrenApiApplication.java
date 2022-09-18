package io.renren;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(value = {"com.xiuxian","io.renren"})
@EnableDiscoveryClient
@SpringBootApplication
public class RenrenApiApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(RenrenApiApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        StringBuilder b=new StringBuilder();
        b.deleteCharAt(b.length()-1);
        return application.sources(RenrenApiApplication.class);
    }

}
