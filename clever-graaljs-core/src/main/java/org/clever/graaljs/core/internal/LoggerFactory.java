package org.clever.graaljs.core.internal;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.SneakyThrows;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/26 08:45 <br/>
 */
public class LoggerFactory {
    protected static final Cache<String, Logger> Logger_Cache = CacheBuilder.newBuilder().initialCapacity(32).maximumSize(256).build();

    public static final LoggerFactory Instance = new LoggerFactory();

    protected LoggerFactory() {
    }

    /**
     * 获取日志对象
     *
     * @param name 名称
     */
    @SneakyThrows
    public Logger getLogger(String name) {
        return Logger_Cache.get(name, () -> new Logger(name));
    }
}
