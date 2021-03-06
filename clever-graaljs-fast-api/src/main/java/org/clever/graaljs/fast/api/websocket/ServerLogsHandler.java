package org.clever.graaljs.fast.api.websocket;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.core.utils.RingBuffer;
import org.clever.graaljs.fast.api.utils.WebsocketUtils;
import org.clever.graaljs.spring.logger.GraalJsDebugLogbackAppender;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.concurrent.*;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/09/02 09:58 <br/>
 */
@SuppressWarnings("NullableProblems")
@Component
@Slf4j
public class ServerLogsHandler extends AbstractWebSocketHandler {
    /**
     * 定时调度器
     */
    private static final ScheduledExecutorService SCHEDULED = Executors.newSingleThreadScheduledExecutor();
    /**
     * {@code ConcurrentMap<SessionId, WebSocketSession>}
     */
    private static final ConcurrentMap<String, WebSocketSession> SESSION_MAP = new ConcurrentHashMap<>();
    /**
     * logStartIndex
     */
    private static Long LOG_START_INDEX = null;

    static {
        SCHEDULED.scheduleAtFixedRate(() -> {
            try {
                if (SESSION_MAP.isEmpty()) {
                    return;
                }
                RingBuffer.BufferContent<String> logContent;
                if (LOG_START_INDEX == null) {
                    logContent = GraalJsDebugLogbackAppender.SERVER_LOGS_BUFFER.getBuffer();
                } else {
                    logContent = GraalJsDebugLogbackAppender.SERVER_LOGS_BUFFER.getBuffer(LOG_START_INDEX, 64);
                }
                if (logContent.getContent().isEmpty()) {
                    return;
                }
                LOG_START_INDEX = logContent.getLastIndex() + 1;
                SESSION_MAP.forEach((sid, session) -> WebsocketUtils.sendMessage(session, logContent));
            } catch (Exception e) {
                log.warn("[ApiLogsHandler] 发送日志失败", e);
            }
        }, 0, 1, TimeUnit.MILLISECONDS);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String msg = message.getPayload();
        if (StringUtils.isBlank(msg)) {
            WebsocketUtils.close(session);
            return;
        }
        if ("all".equals(msg)) {
            RingBuffer.BufferContent<String> logContent = GraalJsDebugLogbackAppender.SERVER_LOGS_BUFFER.getBuffer();
            if (!logContent.getContent().isEmpty()) {
                WebsocketUtils.sendMessage(session, logContent);
            }
        }
        SESSION_MAP.put(session.getId(), session);
    }

    /**
     * 消息传输错误处理
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.warn("[ServerLogsHandler] 数据传输错误 | SessionId={}", session.getId(), exception);
    }

    /**
     * 关闭连接后
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String sessionId = session.getId();
        SESSION_MAP.remove(sessionId);
        log.info("[ServerLogsHandler] 关闭连接 | SessionId={}", sessionId);
    }
}
