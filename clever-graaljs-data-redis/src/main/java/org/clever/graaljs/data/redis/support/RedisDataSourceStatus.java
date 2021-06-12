package org.clever.graaljs.data.redis.support;

import lombok.Data;

import java.io.Serializable;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/11/12 14:47 <br/>
 */
@Data
public class RedisDataSourceStatus implements Serializable {
    /**
     * 总连接数
     */
    private int totalConnections = 0;
    /**
     * 活动连接数
     */
    private int activeConnections = 0;
    /**
     * 空闲连接数
     */
    private int idleConnections = 0;
    /**
     * 等待索取连接的线程数
     */
    private int threadsAwaitingConnection = 0;
    /**
     * 最大借用等待时间(毫秒)
     */
    private long maxBorrowWaitTimeMillis;
    /**
     * 平均借用等待时间(毫秒)
     */
    private long meanBorrowWaitTimeMillis;
    /**
     * 平均活动时间(毫秒)
     */
    private Long meanActiveTimeMillis;
    /**
     * 平均空闲时间(毫秒)
     */
    private Long meanIdleTimeMillis;
}
