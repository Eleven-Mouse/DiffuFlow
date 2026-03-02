package ollamaStar.common.config;

import ollamaStar.common.interceptor.WebscoketUserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker//开启Websocket服务端功能
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer
{
    /**
     * 定义了用户端连接websocket的uri，同时设置运行跨域访问
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .setAllowedOriginPatterns("*");
    }

    /**
     * 定义了用户端订阅消息的前缀为/topic和/user
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic","/user");
    }

    /**
     * 注册刚才定义的拦截器
     * @param registry
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registry)
    {
        registry.interceptors(new WebscoketUserInterceptor());
    }
}
