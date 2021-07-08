package org.clever.graaljs.data.redis.builtin.wrap.support;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

import java.io.Serializable;
import java.time.Duration;
import java.util.List;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/11/11 17:23 <br/>
 */
@Data
public class RedisConfig implements Serializable {
    /**
     * 数据库索引
     */
    private int database = 0;

    /**
     * Redis服务器主机
     */
    private String host = "localhost";

    /**
     * redis服务器登录密码
     */
    private String password;

    /**
     * Redis服务器端口
     */
    private int port = 6379;

    /**
     * 是否启用SSL支持
     */
    private boolean ssl;

    /**
     * 连接超时(毫秒)
     */
    private Long timeoutMillis;

    /**
     * 哨兵配置
     */
    private SentinelConfig sentinel;

    /**
     * 集群配置
     */
    private ClusterConfig cluster;

    /**
     * jedis 客户端配置
     */
    private JedisConfig jedis;

    /**
     * lettuce 客户端配置
     */
    private LettuceConfig lettuce;

    @Data
    public static class SentinelConfig implements Serializable {
        /**
         * Redis服务器的名称
         */
        private String master;

        /**
         * 逗号分隔列表 "host:port" 对
         */
        private List<String> nodes;
    }

    @Data
    public static class ClusterConfig implements Serializable {
        /**
         * 群集节点的“初始”列表，要求至少有一个条目
         */
        private List<String> nodes;

        /**
         * 跨群集执行命令时要遵循的最大重定向数
         */
        private Integer maxRedirects;
    }

    @Data
    public static class PoolConfig implements Serializable {

        /**
         * 池中“空闲”连接的最大数量。使用负值表示空闲连接的数量不受限制
         */
        private int maxIdle = 8;

        /**
         * 池中要维护的最小空闲连接数的目标。此设置只有在它和逐出运行之间的时间均为正值时才有效
         */
        private int minIdle = 0;

        /**
         * 池在给定时间可以分配的最大连接数。使用负值表示无限制
         */
        private int maxActive = 8;

        /**
         * 当池耗尽时，连接分配在引发异常之前应阻塞的最长时间。使用负值可无限期阻止
         */
        private Long maxWaitMillis = -1L;

        /**
         * 空闲对象逐出器线程的运行间隔时间。为正值时，空闲对象逐出线程启动，否则不执行空闲对象逐出
         */
        private Long timeBetweenEvictionRunsMillis;
    }

    @Data
    public static class JedisConfig implements Serializable {
        /**
         * 连接池配置
         */
        private PoolConfig pool;
    }

    @Data
    public static class LettuceConfig implements Serializable {
        /**
         * 关机超时
         */
        private long shutdownTimeoutMillis = 100L;
        /**
         * 连接池配置
         */
        private PoolConfig pool;
    }

    @JsonIgnore
    protected RedisProperties.Pool getPool(PoolConfig poolConfig) {
        if (poolConfig == null) {
            return null;
        }
        RedisProperties.Pool pool = new RedisProperties.Pool();
        pool.setMaxIdle(poolConfig.getMaxIdle());
        pool.setMinIdle(poolConfig.getMinIdle());
        pool.setMaxActive(poolConfig.getMaxActive());
        if (poolConfig.getMaxWaitMillis() != null) {
            pool.setMaxWait(Duration.ofMillis(poolConfig.getMaxWaitMillis()));
        }
        if (poolConfig.getTimeBetweenEvictionRunsMillis() != null) {
            pool.setTimeBetweenEvictionRuns(Duration.ofMillis(poolConfig.getTimeBetweenEvictionRunsMillis()));
        }
        return pool;
    }

    @JsonIgnore
    public RedisProperties getRedisProperties() {
        RedisProperties redisProperties = new RedisProperties();
        redisProperties.setDatabase(database);
        redisProperties.setHost(host);
        redisProperties.setPassword(password);
        redisProperties.setPort(port);
        redisProperties.setSsl(ssl);
        if (timeoutMillis != null) {
            redisProperties.setTimeout(Duration.ofMillis(timeoutMillis));
        }
        if (sentinel != null) {
            RedisProperties.Sentinel sentinelTmp = new RedisProperties.Sentinel();
            redisProperties.setSentinel(sentinelTmp);
            sentinelTmp.setMaster(sentinel.getMaster());
            sentinelTmp.setNodes(sentinel.getNodes());
        }
        if (cluster != null) {
            RedisProperties.Cluster clusterTmp = new RedisProperties.Cluster();
            redisProperties.setCluster(clusterTmp);
            clusterTmp.setMaxRedirects(cluster.getMaxRedirects());
            clusterTmp.setNodes(cluster.getNodes());
        }
        if (jedis != null) {
            RedisProperties.Jedis jedisTmp = redisProperties.getJedis();
            jedisTmp.setPool(getPool(jedis.getPool()));
        }
        if (lettuce != null) {
            RedisProperties.Lettuce lettuceTmp = redisProperties.getLettuce();
            lettuceTmp.setShutdownTimeout(Duration.ofMillis(lettuce.getShutdownTimeoutMillis()));
            lettuceTmp.setPool(getPool(lettuce.getPool()));
        }
        return redisProperties;
    }
}
