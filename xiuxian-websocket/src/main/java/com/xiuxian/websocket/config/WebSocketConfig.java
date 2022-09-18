package com.xiuxian.websocket.config;


import com.xiuxian.websocket.interceptor.WebSocketInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Bean
    public WebSocketInterceptor getWebSocketInterceptor() {
        return new WebSocketInterceptor();
    }



    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 配置客户端尝试连接地址
        registry
                .addEndpoint("/ws")     // 设置连接节点，前端请求的建立连接的地址就是 http://ip:端口/ws
                .addInterceptors(getWebSocketInterceptor())
                .setAllowedOrigins("*")
                .withSockJS();       // 开启sockJS支持，这里可以对不支持stomp的浏览器进行兼容。
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 消息代理,这里配置自带的消息代理，也可以配置其它的消息代理
        // 一定要注意这里的参数，可以理解为开启通道,后面如果使用了"/XXX"来作为前缀，这里就要配置,同时这里的"/topic"是默认群发消息的前缀,前端在订阅群发地址的时候需要加上"/topic"
        registry.enableSimpleBroker("/user","/topic");
        // 客户端向服务端发送消息需有的前缀,需要什么样的前缀在这里配置,但是不建议使用，这里跟下面首次订阅返回会有点冲突，如果不需要首次订阅返回消息，也可以加上消息前缀
        registry.setApplicationDestinationPrefixes("/app");
        // 配置单发消息的前缀 /user，前端订阅一对一通信的地址需要加上"/user"前缀
        registry.setUserDestinationPrefix("/user");
    }
}
