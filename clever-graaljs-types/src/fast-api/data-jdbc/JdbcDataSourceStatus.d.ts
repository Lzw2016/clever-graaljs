interface JdbcDataSourceStatus extends JObject {
    /** 总连接数 */
    getTotalConnections(): JInt;

    /** 活动连接数 */
    getActiveConnections(): JInt;

    /** 空闲连接数 */
    getIdleConnections(): JInt;

    /** 等待索取连接的线程数 */
    getThreadsAwaitingConnection(): JInt;
}
