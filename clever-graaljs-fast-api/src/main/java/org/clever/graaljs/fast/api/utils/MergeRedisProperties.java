package org.clever.graaljs.fast.api.utils;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

import java.time.Duration;

/**
 * 作者： lzw<br/>
 * 创建时间：2019-10-06 22:05 <br/>
 */
public class MergeRedisProperties {

    public static RedisProperties mergeConfig(RedisProperties source, RedisProperties target) {
        if (source == null) {
            return target;
        }
        if (target.getDatabase() == 0) {
            target.setDatabase(source.getDatabase());
        }
        if (target.getUrl() == null) {
            target.setUrl(source.getUrl());
        }
        if ("localhost".equals(target.getHost())) {
            target.setHost(source.getHost());
        }
        if (target.getPassword() == null) {
            target.setPassword(source.getPassword());
        }
        if (target.getPort() == 6379) {
            target.setPort(source.getPort());
        }
        if (!target.isSsl()) {
            target.setSsl(source.isSsl());
        }
        if (target.getTimeout() == null) {
            target.setTimeout(source.getTimeout());
        }
        if (target.getSentinel() == null) {
            target.setSentinel(source.getSentinel());
        } else {
            if (target.getSentinel().getMaster() == null) {
                target.getSentinel().setMaster(source.getSentinel().getMaster());
            }
            if (target.getSentinel().getNodes() == null) {
                target.getSentinel().setNodes(source.getSentinel().getNodes());
            }
        }
        if (target.getCluster() == null) {
            target.setCluster(source.getCluster());
        } else {
            if (target.getCluster().getNodes() == null) {
                target.getCluster().setNodes(source.getCluster().getNodes());
            }
            if (target.getCluster().getMaxRedirects() == null) {
                target.getCluster().setMaxRedirects(source.getCluster().getMaxRedirects());
            }
        }
        if (target.getJedis() != null && source.getJedis() != null) {
            if (target.getJedis().getPool() == null) {
                target.getJedis().setPool(new RedisProperties.Pool());
            }
            target.getJedis().setPool(mergePool(source.getJedis().getPool(), target.getJedis().getPool()));
        }
        if (target.getLettuce() != null && source.getLettuce() != null) {
            if (Duration.ofMillis(100).equals(target.getLettuce().getShutdownTimeout())) {
                target.getLettuce().setShutdownTimeout(source.getLettuce().getShutdownTimeout());
            }
            if (target.getLettuce().getPool() == null) {
                target.getLettuce().setPool(new RedisProperties.Pool());
            }
            target.getLettuce().setPool(mergePool(source.getLettuce().getPool(), target.getLettuce().getPool()));
        }
        return target;
    }

    private static RedisProperties.Pool mergePool(RedisProperties.Pool source, RedisProperties.Pool target) {
        if (source == null) {
            return target;
        }
        if (target.getMaxIdle() == 8) {
            target.setMaxIdle(source.getMaxIdle());
        }
        if (target.getMinIdle() == 0) {
            target.setMinIdle(source.getMinIdle());
        }
        if (target.getMaxActive() == 8) {
            target.setMaxActive(source.getMaxActive());
        }
        if (Duration.ofMillis(-1).equals(target.getMaxWait())) {
            target.setMaxWait(source.getMaxWait());
        }
        if (target.getTimeBetweenEvictionRuns() == null) {
            target.setTimeBetweenEvictionRuns(source.getTimeBetweenEvictionRuns());
        }
        return target;
    }
}
