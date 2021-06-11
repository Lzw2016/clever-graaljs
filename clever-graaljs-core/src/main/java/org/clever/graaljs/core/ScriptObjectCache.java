package org.clever.graaljs.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.clever.graaljs.core.utils.ExceptionUtils;
import org.clever.graaljs.core.utils.ScriptCodeUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/03 16:00 <br/>
 */
@Slf4j
public class ScriptObjectCache {
    public static final int Default_Initial_Capacity = 2048;
    /**
     * 缓存 {@code Cache<ScriptCode, ScriptObject>}
     */
    @JsonIgnore
    private final Cache<String, ScriptObject> cache;

    /**
     * 创建ScriptObject缓存
     *
     * @param clearTimeInterval 定时清除缓存的时间间隔，单位：秒(小于等于0表示不清除)
     * @param initialCapacity   初始缓存容量
     */
    public ScriptObjectCache(long clearTimeInterval, int initialCapacity) {
        if (initialCapacity < 0) {
            initialCapacity = Default_Initial_Capacity;
        }
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder().initialCapacity(initialCapacity);
        if (clearTimeInterval > 0) {
            cacheBuilder.removalListener(notification -> {
                Object code = notification.getKey();
                log.debug("ModuleCache 移除缓存 -> {} | 原因: {}", ScriptCodeUtils.compressCode(String.valueOf(code), false), notification.getCause());
            }).expireAfterWrite(clearTimeInterval, TimeUnit.SECONDS);
        }
        this.cache = cacheBuilder.build();
    }

    /**
     * 创建ScriptObject缓存(不定时清除缓存，使用默认的缓存容量)
     */
    public ScriptObjectCache() {
        this(-1, Default_Initial_Capacity);
    }

    /**
     * 获取ScriptObject
     *
     * @param code   脚本代码
     * @param loader 根据脚本代码创建ScriptObject
     * @return 返回ScriptObject，不会返回null
     */
    public ScriptObject get(String code, Callable<ScriptObject> loader) {
        try {
            return cache.get(code, loader);
        } catch (ExecutionException e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

    /**
     * 获取ScriptObject
     *
     * @param code 脚本代码
     * @return 返回ScriptObject，不存在就返回null
     */
    public ScriptObject get(String code) {
        return cache.getIfPresent(code);
    }

    /**
     * 缓存ScriptObject
     *
     * @param code         脚本代码
     * @param scriptObject ScriptObject对象
     */
    public void put(String code, ScriptObject scriptObject) {
        cache.put(code, scriptObject);
    }

    /**
     * 清除所有缓存
     */
    public void clear() {
        cache.invalidateAll();
    }

    /**
     * 移除指定的缓存
     *
     * @param code 脚本代码
     */
    public void remove(String code) {
        cache.invalidate(code);
    }
}
