package org.clever.graaljs.fast.api.websocket;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.core.utils.ExceptionUtils;
import org.clever.graaljs.core.utils.RingBuffer;
import org.clever.graaljs.core.utils.mapper.JacksonMapper;
import org.clever.graaljs.fast.api.dto.request.DebugApiReq;
import org.clever.graaljs.fast.api.dto.response.WebSocketErrorRes;
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
 * 创建时间：2021/09/01 14:06 <br/>
 */
@SuppressWarnings("NullableProblems")
@Component
@Slf4j
public class DebugApiLogsHandler extends AbstractWebSocketHandler {
    private static final String API_DEBUG_ID = "api_debug_id";
    /**
     * 定时调度器
     */
    private static final ScheduledExecutorService SCHEDULED = Executors.newSingleThreadScheduledExecutor();
    /**
     * {@code ConcurrentMap<WebSocketSession, RingBuffer<String>>}
     */
    private static final ConcurrentMap<WebSocketSession, RingBuffer<String>> LOG_RING_BUFFER_MAP = new ConcurrentHashMap<>();
    /**
     * {@code ConcurrentMap<SessionId, logStartIndex>}
     */
    private static final ConcurrentMap<String, Long> LOG_START_INDEX_MAP = new ConcurrentHashMap<>();

    static {
        SCHEDULED.scheduleAtFixedRate(() -> {
            try {
                LOG_RING_BUFFER_MAP.forEach(DebugApiLogsHandler::sendLogs);
            } catch (Exception e) {
                log.warn("[ApiLogsHandler] 发送日志失败", e);
            }
        }, 0, 1, TimeUnit.MILLISECONDS);
    }

    private static void sendLogs(WebSocketSession session, RingBuffer<String> logRingBuffer) {
        if (!session.isOpen()) {
            return;
        }
        String sessionId = session.getId();
        Long logStartIndex = LOG_START_INDEX_MAP.get(sessionId);
        RingBuffer.BufferContent<String> logContent;
        try {
            if (logStartIndex == null) {
                logContent = logRingBuffer.getBuffer();
            } else {
                logContent = logRingBuffer.getBuffer(logStartIndex, 1024);
            }
            if (logContent.getContent().isEmpty()) {
                return;
            }
            LOG_START_INDEX_MAP.put(sessionId, logContent.getLastIndex() + 1);
            if (session.isOpen()) {
                TextMessage textMessage = new TextMessage(JacksonMapper.getInstance().toJson(logContent));
                session.sendMessage(textMessage);
            }
        } catch (Exception e) {
            log.warn("[ApiLogsHandler] 发送日志失败", e);
        }
    }

    private static void apiDebugEnd(WebSocketSession session) {
        Object apiDebugId = session.getAttributes().get(API_DEBUG_ID);
        if (apiDebugId != null && StringUtils.isNotBlank(String.valueOf(apiDebugId))) {
            GraalJsDebugLogbackAppender.apiDebugEnd(String.valueOf(apiDebugId));
        }
        LOG_START_INDEX_MAP.remove(session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String msg = message.getPayload();
        if (StringUtils.isBlank(msg)) {
            WebsocketUtils.close(session);
            return;
        }
        try {
            // 结束上一次的调试
            apiDebugEnd(session);
            // 请求参数验证
            DebugApiReq req = JacksonMapper.getInstance().fromJson(msg, DebugApiReq.class);
            if (req == null) {
                throw new IllegalArgumentException("请求数据不能为空");
            }
            if (StringUtils.isBlank(req.getApiDebugId())) {
                throw new IllegalArgumentException("apiDebugId不能为空");
            }
            // 开始收集调试日志
            session.getAttributes().put(API_DEBUG_ID, req.getApiDebugId());
            RingBuffer<String> ringBuffer = GraalJsDebugLogbackAppender.apiDebugStart(req.getApiDebugId(), 2048);
            LOG_RING_BUFFER_MAP.put(session, ringBuffer);
        } catch (Exception e) {
            WebSocketErrorRes WebSocketErrorRes = new WebSocketErrorRes();
            WebSocketErrorRes.setErrorStackTrace(ExceptionUtils.getStackTraceAsString(e));
            WebsocketUtils.sendMessage(session, WebSocketErrorRes);
            WebsocketUtils.close(session);
            apiDebugEnd(session);
        }
    }

    /**
     * 消息传输错误处理
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.warn("[ApiLogsHandler] 数据传输错误 | SessionId={}", session.getId(), exception);
    }

    /**
     * 关闭连接后
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String sessionId = session.getId();
        LOG_RING_BUFFER_MAP.remove(session);
        LOG_START_INDEX_MAP.remove(sessionId);
        apiDebugEnd(session);
        log.info("[ApiLogsHandler] 关闭连接 | SessionId={}", sessionId);
    }
}
