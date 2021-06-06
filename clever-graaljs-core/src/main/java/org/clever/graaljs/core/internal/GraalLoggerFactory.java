package org.clever.graaljs.core.internal;

import lombok.SneakyThrows;
import org.clever.graaljs.core.internal.support.GraalObjectToString;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/26 15:20 <br/>
 */
public class GraalLoggerFactory extends LoggerFactory {
    public static final GraalLoggerFactory Instance = new GraalLoggerFactory();

    protected GraalLoggerFactory() {
    }

    /**
     * 获取日志对象
     *
     * @param name 名称
     */
    @SneakyThrows
    public Logger getLogger(String name) {
        return Logger_Cache.get(name, () -> {
            Logger logger = new Logger(name);
            logger.setObjectToString(GraalObjectToString.Instance);
            return logger;
        });
    }
}
