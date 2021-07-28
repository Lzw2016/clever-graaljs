interface SentinelConfig {
    /** Redis服务器的名称 */
    master?: JString;
    /** 逗号分隔列表 "host:port" 对 */
    nodes?: JList<JString>;
}

interface ClusterConfig {
    /** 群集节点的“初始”列表，要求至少有一个条目 */
    nodes?: JList<JString>;
    /** 跨群集执行命令时要遵循的最大重定向数 */
    maxRedirects?: JInt;
}

interface PoolConfig {
    /** 池中“空闲”连接的最大数量。使用负值表示空闲连接的数量不受限制 */
    maxIdle?: JInt;
    /** 池中要维护的最小空闲连接数的目标。此设置只有在它和逐出运行之间的时间均为正值时才有效 */
    minIdle?: JInt;
    /** 池在给定时间可以分配的最大连接数。使用负值表示无限制 */
    maxActive?: JInt;
    /** 当池耗尽时，连接分配在引发异常之前应阻塞的最长时间。使用负值可无限期阻止 */
    maxWaitMillis?: JLong;
    /** 空闲对象逐出器线程的运行间隔时间。为正值时，空闲对象逐出线程启动，否则不执行空闲对象逐出 */
    timeBetweenEvictionRunsMillis?: JLong;
}

interface JedisConfig {
    /** 连接池配置 */
    pool?: PoolConfig;
}

interface LettuceConfig {
    /** 关机超时 */
    shutdownTimeoutMillis?: JLong;
    /** 连接池配置 */
    pool?: PoolConfig;
}

interface RedisConfig {
    /** 数据库索引 */
    database?: JInt;
    /** Redis服务器主机 */
    host?: JString;
    /** redis服务器登录密码 */
    password?: JString;
    /** Redis服务器端口 */
    port?: JInt;
    /** 是否启用SSL支持 */
    ssl?: JBoolean;
    /** 连接超时(毫秒) */
    timeoutMillis?: JLong;
    /** 哨兵配置 */
    sentinel?: SentinelConfig;
    /** 集群配置 */
    cluster?: ClusterConfig;
    /** jedis 客户端配置 */
    jedis?: JedisConfig;
    /** lettuce 客户端配置 */
    lettuce?: LettuceConfig;
}
