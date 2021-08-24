package org.clever.graaljs.spring.logger;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.encoder.Encoder;
import lombok.Getter;
import lombok.Setter;
import org.clever.graaljs.core.utils.Assert;
import org.clever.graaljs.core.utils.RingBuffer;
import org.slf4j.MDC;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 脚本执行调试Appender
 * 作者：lizw <br/>
 * 创建时间：2021/07/04 20:10 <br/>
 */
public class GraalJsDebugLogbackAppender extends AppenderBase<ILoggingEvent> {
    /**
     * 定时调度器
     */
    private static final ScheduledExecutorService SCHEDULED = Executors.newSingleThreadScheduledExecutor();
    /**
     * 缓存最大存活时间(API DEBUG)
     */
    private static final long API_DEBUG_CACHE_MAX_TIME = 1000 * 60 * 5;
    /**
     * API DEBUG 日志缓存
     */
    public static final String MDC_API_DEBUG_ID = "API_DEBUG";
    /**
     * API DEBUG 日志缓存
     */
    private static final ConcurrentHashMap<String, RingBuffer<String>> API_DEBUG_CACHE = new ConcurrentHashMap<>();
    /**
     * 缓存最大存活时间(直接运行JS脚本)
     */
    private static final long RUN_JS_CACHE_MAX_TIME = 1000 * 60 * 30;
    /**
     * 直接运行JS脚本 日志缓存
     */
    public static final String MDC_RUN_JS_ID = "RUN_JS";
    /**
     * 直接运行JS脚本 日志缓存
     */
    private static final ConcurrentHashMap<String, RingBuffer<String>> RUN_JS_CACHE = new ConcurrentHashMap<>();

    @Setter
    @Getter
    protected Encoder<ILoggingEvent> encoder;

    public GraalJsDebugLogbackAppender() {
        SCHEDULED.scheduleWithFixedDelay(() -> {
            final long now = System.currentTimeMillis();
            cleanCache(API_DEBUG_CACHE, now, API_DEBUG_CACHE_MAX_TIME);
            cleanCache(RUN_JS_CACHE, now, RUN_JS_CACHE_MAX_TIME);
        }, 3, 3, TimeUnit.SECONDS);
    }

    @Override
    protected void append(ILoggingEvent event) {
        // final String logMsg = event.getFormattedMessage();
        final String logMsg = new String(encoder.encode(event));
        // API_DEBUG 日志
        String traceLogId = event.getMDCPropertyMap().get(getKey(MDC_API_DEBUG_ID));
        cacheLog(traceLogId, logMsg, API_DEBUG_CACHE);
        // RUN_JS 日志
        traceLogId = event.getMDCPropertyMap().get(getKey(MDC_RUN_JS_ID));
        cacheLog(traceLogId, logMsg, RUN_JS_CACHE);
    }

    private void cacheLog(String traceLogId, String logMsg, ConcurrentHashMap<String, RingBuffer<String>> map) {
        if (traceLogId == null) {
            return;
        }
        RingBuffer<String> ringBuffer = map.get(traceLogId);
        if (ringBuffer == null) {
            return;
        }
        ringBuffer.add(logMsg);
    }

    private static String getKey(String group) {
        return Thread.currentThread().getId() + "_" + group;
    }

    private static void cleanCache(ConcurrentHashMap<String, RingBuffer<String>> cache, long now, long maxTime) {
        final Set<String> traceLogIds = new HashSet<>();
        cache.forEach((traceLogId, ringBuffer) -> {
            if ((now - ringBuffer.getCreateTime()) >= maxTime) {
                traceLogIds.add(traceLogId);
            }
        });
        traceLogIds.forEach(cache::remove);
    }

    /**
     * 开始调试API
     *
     * @param uniqueId   唯一ID
     * @param bufferSize 缓存大小
     */
    public static void apiDebugStart(String uniqueId, int bufferSize) {
        Assert.isNotBlank(uniqueId, "uniqueId 不能为空");
        API_DEBUG_CACHE.computeIfAbsent(uniqueId, traceLogId -> new RingBuffer<>(bufferSize));
        MDC.put(getKey(MDC_API_DEBUG_ID), uniqueId);
    }

    /**
     * 调试API结束
     *
     * @param uniqueId 唯一ID
     */
    public static RingBuffer.BufferContent<String> apiDebugEnd(String uniqueId) {
        Assert.isNotBlank(uniqueId, "uniqueId 不能为空");
        MDC.remove(getKey(MDC_API_DEBUG_ID));
        RingBuffer<String> ringBuffer = API_DEBUG_CACHE.get(uniqueId);
        if (ringBuffer == null) {
            return new RingBuffer.BufferContent<>(0, 0, Collections.emptyList());
        }
        API_DEBUG_CACHE.remove(uniqueId);
        return ringBuffer.getBuffer();
    }

    /**
     * 开始运行JS
     *
     * @param uniqueId   唯一ID
     * @param bufferSize 缓存大小
     */
    public static RingBuffer<String> runJsStart(String uniqueId, int bufferSize) {
        Assert.isNotBlank(uniqueId, "uniqueId 不能为空");
        RingBuffer<String> ringBuffer = RUN_JS_CACHE.computeIfAbsent(uniqueId, traceLogId -> new RingBuffer<>(bufferSize));
        MDC.put(getKey(MDC_RUN_JS_ID), uniqueId);
        return ringBuffer;
    }

    /**
     * 运行JS结束
     *
     * @param uniqueId 唯一ID
     */
    public static void runJsEnd(String uniqueId) {
        Assert.isNotBlank(uniqueId, "uniqueId 不能为空");
        MDC.remove(getKey(MDC_RUN_JS_ID));
        RUN_JS_CACHE.remove(uniqueId);
    }
}
