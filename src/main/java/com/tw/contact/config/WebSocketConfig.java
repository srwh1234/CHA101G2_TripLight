package com.tw.contact.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    // 註冊STOMP協議的節點(endpoint)，並映射指定的URL
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 添加一個服務端點，接受客戶端的WebSocket連接。
        // SockJS是用於在瀏覽器中模擬WebSocket的JavaScript庫。
        // 如果WebSocket不可用，SockJS將使用其他協議來模擬WebSocket的行為。
        registry.addEndpoint("/ws").withSockJS();
    }

    @Override
    // 配置訊息代理(Message Broker)
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 設定伺服器接收訊息的地址的前綴，意即設定一個或多個前綴路徑，這些前綴路徑用於過濾目的地，以便將訊息分發到帶有@MessageMapping註解的方法，而不是到訊息代理。
        registry.setApplicationDestinationPrefixes("/app");
        // 設定訊息代理，由它來處理訊息的轉發，即用戶訂閱主題或者廣播，或者向主題發送訊息等等。
        registry.enableSimpleBroker("/topic");
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}

