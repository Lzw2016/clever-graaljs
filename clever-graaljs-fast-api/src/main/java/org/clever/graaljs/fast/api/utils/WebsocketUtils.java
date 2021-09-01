package org.clever.graaljs.fast.api.utils;

import lombok.extern.slf4j.Slf4j;
import org.clever.graaljs.core.utils.mapper.JacksonMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/09/01 15:07 <br/>
 */
@Slf4j
public class WebsocketUtils {
    public static void sendMessage(WebSocketSession session, Object object) {
        if (!session.isOpen()) {
            return;
        }
        String msg;
        if (object instanceof CharSequence) {
            msg = String.valueOf(object);
        } else {
            msg = JacksonMapper.getInstance().toJson(object);
        }
        TextMessage textMessage = new TextMessage(msg);
        if (session.isOpen()) {
            try {
                session.sendMessage(textMessage);
            } catch (IOException e) {
                log.warn("[WebsocketUtils] 发送数据失败", e);
            }
        }
    }

    public static void close(WebSocketSession session) {
        if (session != null) {
            try {
                session.close();
            } catch (IOException e) {
                log.warn("[WebsocketUtils] 关闭连接失败 | SessionId={}", session.getId(), e);
            }
        }
    }
}
