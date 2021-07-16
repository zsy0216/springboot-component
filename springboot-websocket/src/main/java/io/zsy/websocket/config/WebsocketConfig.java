package io.zsy.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author zhangshuaiyin
 * @date 2021-07-16 16:19
 */
@Configuration
public class WebsocketConfig {
    /**
     * 配置 ServerEndpointExporter
     * 该Bean会自动注册 @ServerEndpoint 注解声明的Websocket Endpoint
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
