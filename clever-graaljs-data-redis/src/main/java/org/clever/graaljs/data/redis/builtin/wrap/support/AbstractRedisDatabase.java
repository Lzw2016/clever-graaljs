package org.clever.graaljs.data.redis.builtin.wrap.support;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/11/11 17:21 <br/>
 */
public abstract class AbstractRedisDatabase {

    protected List<String> toList(Object object) {
        List<String> list = null;
        if (object instanceof Collection) {
            list = new ArrayList<>();
            for (Object node : ((Collection<?>) object)) {
                if (node == null || StringUtils.isBlank(String.valueOf(node))) {
                    continue;
                }
                list.add(String.valueOf(node));
            }
        } else if (object instanceof Object[]) {
            list = new ArrayList<>();
            for (Object node : ((Object[]) object)) {
                if (node == null || StringUtils.isBlank(String.valueOf(node))) {
                    continue;
                }
                list.add(String.valueOf(node));
            }
        }
        return list;
    }

    protected RedisConfig.PoolConfig toPool(Map<?, ?> poolMap) {
        RedisConfig.PoolConfig pool = new RedisConfig.PoolConfig();
        Object maxIdle = poolMap.get("maxIdle");
        if (maxIdle != null) {
            pool.setMaxIdle(NumberUtils.toInt(String.valueOf(maxIdle)));
        }
        Object minIdle = poolMap.get("minIdle");
        if (minIdle != null) {
            pool.setMinIdle(NumberUtils.toInt(String.valueOf(minIdle)));
        }
        Object maxActive = poolMap.get("maxActive");
        if (maxActive != null) {
            pool.setMaxActive(NumberUtils.toInt(String.valueOf(maxActive)));
        }
        Object maxWaitMillis = poolMap.get("maxWaitMillis");
        if (maxWaitMillis != null) {
            pool.setMaxWaitMillis(NumberUtils.toLong(String.valueOf(maxWaitMillis)));
        }
        Object timeBetweenEvictionRunsMillis = poolMap.get("timeBetweenEvictionRunsMillis");
        if (timeBetweenEvictionRunsMillis != null) {
            pool.setTimeBetweenEvictionRunsMillis(NumberUtils.toLong(String.valueOf(timeBetweenEvictionRunsMillis)));
        }
        return pool;
    }

    public RedisConfig getRedisConfig(Map<String, Object> config) {
        RedisConfig redisConfig = new RedisConfig();
        Object database = config.get("database");
        if (database != null) {
            redisConfig.setDatabase(NumberUtils.toInt(String.valueOf(database)));
        }
        redisConfig.setHost((String) config.get("host"));
        redisConfig.setPassword((String) config.get("password"));
        Object port = config.get("port");
        if (port != null) {
            redisConfig.setPort(NumberUtils.toInt(String.valueOf(port)));
        }
        Object ssl = config.get("ssl");
        if (ssl != null) {
            redisConfig.setSsl(Boolean.parseBoolean(String.valueOf(ssl)));
        }
        Object timeoutMillis = config.get("timeoutMillis");
        if (timeoutMillis != null) {
            redisConfig.setTimeoutMillis(NumberUtils.toLong(String.valueOf(timeoutMillis)));
        }
        Object sentinel = config.get("sentinel");
        if (sentinel instanceof Map) {
            Map<?, ?> sentinelMap = (Map<?, ?>) sentinel;
            RedisConfig.SentinelConfig sentinelConfig = new RedisConfig.SentinelConfig();
            redisConfig.setSentinel(sentinelConfig);
            sentinelConfig.setMaster((String) sentinelMap.get("master"));
            List<String> nodes = toList(sentinelMap.get("nodes"));
            sentinelConfig.setNodes(nodes);
        }
        Object cluster = config.get("cluster");
        if (cluster instanceof Map) {
            Map<?, ?> clusterMap = (Map<?, ?>) cluster;
            RedisConfig.ClusterConfig clusterConfig = new RedisConfig.ClusterConfig();
            Object maxRedirects = clusterMap.get("maxRedirects");
            if (maxRedirects != null) {
                clusterConfig.setMaxRedirects(NumberUtils.toInt(String.valueOf(maxRedirects)));
            }
            List<String> nodes = toList(clusterMap.get("nodes"));
            clusterConfig.setNodes(nodes);
        }
        Object jedis = config.get("jedis");
        if (jedis instanceof Map) {
            Map<?, ?> jedisMap = (Map<?, ?>) jedis;
            RedisConfig.JedisConfig jedisConfig = new RedisConfig.JedisConfig();
            Object pool = jedisMap.get("pool");
            if (pool instanceof Map) {
                Map<?, ?> poolMap = (Map<?, ?>) pool;
                jedisConfig.setPool(toPool(poolMap));
            }
            redisConfig.setJedis(jedisConfig);
        }
        Object lettuce = config.get("lettuce");
        if (lettuce instanceof Map) {
            Map<?, ?> lettuceMap = (Map<?, ?>) lettuce;
            RedisConfig.LettuceConfig lettuceConfig = new RedisConfig.LettuceConfig();
            Object shutdownTimeoutMillis = lettuceMap.get("shutdownTimeoutMillis");
            if (shutdownTimeoutMillis != null) {
                lettuceConfig.setShutdownTimeoutMillis(NumberUtils.toLong(String.valueOf(shutdownTimeoutMillis)));
            }
            Object pool = lettuceMap.get("pool");
            if (pool instanceof Map) {
                Map<?, ?> poolMap = (Map<?, ?>) pool;
                lettuceConfig.setPool(toPool(poolMap));
            }
            redisConfig.setLettuce(lettuceConfig);
        }
        return redisConfig;
    }
}
