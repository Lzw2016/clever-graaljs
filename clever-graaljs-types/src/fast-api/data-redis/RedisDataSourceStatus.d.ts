interface RedisDataSourceStatus {
    /** 总连接数 */
    getTotalConnections(): JInt;

    /** 活动连接数 */
    getActiveConnections(): JInt;

    /** 空闲连接数 */
    getIdleConnections(): JInt;

    /** 等待索取连接的线程数 */
    getThreadsAwaitingConnection(): JInt;

    /** 最大借用等待时间(毫秒) */
    getMaxBorrowWaitTimeMillis(): JLong;

    /** 平均借用等待时间(毫秒) */
    getMeanBorrowWaitTimeMillis(): JLong;

    /** 平均活动时间(毫秒) */
    getMeanActiveTimeMillis(): JLong | null;

    /** 平均空闲时间(毫秒) */
    getMeanIdleTimeMillis(): JLong | null;
}