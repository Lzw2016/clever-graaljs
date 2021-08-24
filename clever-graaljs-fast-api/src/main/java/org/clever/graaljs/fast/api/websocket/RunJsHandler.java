package org.clever.graaljs.fast.api.websocket;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.clever.graaljs.core.ScriptEngineInstance;
import org.clever.graaljs.core.utils.ExceptionUtils;
import org.clever.graaljs.core.utils.RingBuffer;
import org.clever.graaljs.core.utils.mapper.JacksonMapper;
import org.clever.graaljs.fast.api.dto.request.RunJsReq;
import org.clever.graaljs.fast.api.dto.response.RunJsErrorRes;
import org.clever.graaljs.fast.api.entity.FileResource;
import org.clever.graaljs.fast.api.service.FileResourceManageService;
import org.clever.graaljs.spring.logger.GraalJsDebugLogbackAppender;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.*;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/08/24 16:27 <br/>
 */
@SuppressWarnings("NullableProblems")
@Component
@Slf4j
public class RunJsHandler extends AbstractWebSocketHandler {
    /**
     * 定时调度器
     */
    private static final ScheduledExecutorService SCHEDULED = Executors.newSingleThreadScheduledExecutor();
    /**
     * 执行线程池
     */
    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(
            8,
            8,
            1_000,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(8),
            new BasicThreadFactory.Builder().namingPattern("run_js-pool-%d").daemon(false).build()
    );
    /**
     * {@code ConcurrentMap<SessionId, Future<?>>}
     */
    private static final ConcurrentMap<String, Future<?>> FUTURE_MAP = new ConcurrentHashMap<>();
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
                LOG_RING_BUFFER_MAP.forEach(RunJsHandler::sendLogs);
            } catch (Exception e) {
                log.warn("[RunJsHandler] 发送日志失败", e);
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
                logContent = logRingBuffer.getBuffer(logStartIndex, 64);
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
            log.warn("[RunJsHandler] 发送日志失败", e);
        }
    }

    private static void sendMessage(WebSocketSession session, Object object) {
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
                log.warn("[RunJsHandler] 发送数据失败", e);
            }
        }
    }

    private static void close(WebSocketSession session) {
        if (session != null) {
            try {
                session.close();
            } catch (IOException e) {
                log.warn("[RunJsHandler] 关闭连接失败 | SessionId={}", session.getId(), e);
            }
        }
    }

    @Resource
    private ScriptEngineInstance scriptEngineInstance;
    @Resource
    private FileResourceManageService fileResourceManageService;

    private void doRunJs(WebSocketSession session, String msg) {
        RunJsReq runJsReq = JacksonMapper.getInstance().fromJson(msg, RunJsReq.class);
        // 请求参数验证
        if (runJsReq == null) {
            throw new IllegalArgumentException("请求数据不能为空");
        }
        if (runJsReq.getFileResourceId() == null) {
            throw new IllegalArgumentException("请求fileResourceId不能为空");
        }
        // 执行JS
        FileResource fileResource = fileResourceManageService.getFileResource(runJsReq.getFileResourceId());
        if (fileResource == null) {
            throw new IllegalArgumentException("FileResource不存在，fileResourceId=" + runJsReq.getFileResourceId());
        }
        if (!StringUtils.endsWith(StringUtils.lowerCase(fileResource.getName()), ".js")) {
            throw new IllegalArgumentException("FileResource必须是js文件，fileName=" + fileResource.getName());
        }
        String sessionId = session.getId();
        Future<?> future = EXECUTOR.submit(() -> scriptEngineInstance.wrapFunctionAndEval(fileResource.getContent(), scriptObject -> {
            try {
                RingBuffer<String> ringBuffer = GraalJsDebugLogbackAppender.runJsStart(sessionId, 2048);
                LOG_RING_BUFFER_MAP.put(session, ringBuffer);
                scriptObject.executeVoid();
            } finally {
                GraalJsDebugLogbackAppender.runJsEnd(sessionId);
                RingBuffer<String> ringBuffer = LOG_RING_BUFFER_MAP.remove(session);
                if (ringBuffer != null) {
                    Long logStartIndex = LOG_START_INDEX_MAP.get(sessionId);
                    RingBuffer.BufferContent<String> logContent = logStartIndex == null ? ringBuffer.getBuffer() : ringBuffer.getBuffer(logStartIndex, ringBuffer.getBufferSize());
                    if (!logContent.getContent().isEmpty()) {
                        sendMessage(session, logContent);
                    }
                }
                close(session);
            }
        }));
        FUTURE_MAP.put(sessionId, future);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String msg = message.getPayload();
        if (StringUtils.isBlank(msg)) {
            close(session);
            return;
        }
        try {
            doRunJs(session, msg);
        } catch (Exception e) {
            RunJsErrorRes runJsErrorRes = new RunJsErrorRes();
            runJsErrorRes.setErrorStackTrace(ExceptionUtils.getStackTraceAsString(e));
            sendMessage(session, runJsErrorRes);
            close(session);
        }
    }

    /**
     * 消息传输错误处理
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.warn("[RunJsHandler] 数据传输错误 | SessionId={}", session.getId(), exception);
    }

    /**
     * 关闭连接后
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String sessionId = session.getId();
        Future<?> future = FUTURE_MAP.remove(sessionId);
        if (future != null) {
            future.cancel(true);
        }
        LOG_RING_BUFFER_MAP.remove(session);
        LOG_START_INDEX_MAP.remove(sessionId);
        log.info("[RunJsHandler] 关闭连接 | SessionId={}", sessionId);
    }
}
