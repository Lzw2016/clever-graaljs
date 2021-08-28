package org.clever.graaljs.fast.api.autoconfigure;

import org.clever.graaljs.fast.api.websocket.RunJsHandler;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/08/24 18:52 <br/>
 */
@AutoConfigureAfter({FastApiAutoConfiguration.class})
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
@EnableWebSocket
@Configuration
public class FastApiWebSocketConfigurer implements WebSocketConfigurer {

    private final RunJsHandler runJsHandler;

    public FastApiWebSocketConfigurer(RunJsHandler runJsHandler) {
        this.runJsHandler = runJsHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        String[] allowsOrigins = {"*"};
        //WebSocket通道 withSockJS()表示开启 SockJs, SockJS 所处理的 URL 是 “http://“ 或 “https://“ 模式，而不是 “ws://“ or “wss://“
        registry.addHandler(runJsHandler, "/fast_api/ws/run_js")
                .setAllowedOriginPatterns(allowsOrigins);
    }
}