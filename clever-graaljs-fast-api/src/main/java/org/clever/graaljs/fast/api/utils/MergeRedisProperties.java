package org.clever.graaljs.fast.api.utils;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

import java.util.Objects;

/**
 * 作者： lzw<br/>
 * 创建时间：2019-10-06 22:05 <br/>
 */
public class MergeRedisProperties {
    private static final RedisProperties DEF_CONFIG = new RedisProperties();
    private static final RedisProperties.Pool DEF_CONFIG_2 = new RedisProperties.Pool();

    public static RedisProperties mergeConfig(RedisProperties source, RedisProperties target) {
        if (source == null) {
            return target;
        }
        if (Objects.equals(target.getDatabase(), DEF_CONFIG.getDatabase())) {
            target.setDatabase(source.getDatabase());
        }
        if (Objects.equals(target.getUrl(), DEF_CONFIG.getUrl())) {
            target.setUrl(source.getUrl());
        }
        if (Objects.equals(target.getHost(), DEF_CONFIG.getHost())) {
            target.setHost(source.getHost());
        }
        if (Objects.equals(target.getPassword(), DEF_CONFIG.getPassword())) {
            target.setPassword(source.getPassword());
        }
        if (Objects.equals(target.getPort(), DEF_CONFIG.getPort())) {
            target.setPort(source.getPort());
        }
        if (Objects.equals(target.isSsl(), DEF_CONFIG.isSsl())) {
            target.setSsl(source.isSsl());
        }
        if (Objects.equals(target.getTimeout(), DEF_CONFIG.getTimeout())) {
            target.setTimeout(source.getTimeout());
        }
        if (Objects.equals(target.getSentinel(), DEF_CONFIG.getSentinel())) {
            target.setSentinel(source.getSentinel());
        } else {
            if (Objects.equals(target.getSentinel().getMaster(), DEF_CONFIG.getSentinel().getMaster())) {
                target.getSentinel().setMaster(source.getSentinel().getMaster());
            }
            if (Objects.equals(target.getSentinel().getNodes(), DEF_CONFIG.getSentinel().getNodes())) {
                target.getSentinel().setNodes(source.getSentinel().getNodes());
            }
        }
        if (Objects.equals(target.getCluster(), DEF_CONFIG.getCluster())) {
            target.setCluster(source.getCluster());
        } else {
            if (Objects.equals(target.getCluster().getNodes(), DEF_CONFIG.getCluster().getNodes())) {
                target.getCluster().setNodes(source.getCluster().getNodes());
            }
            if (Objects.equals(target.getCluster().getMaxRedirects(), DEF_CONFIG.getCluster().getMaxRedirects())) {
                target.getCluster().setMaxRedirects(source.getCluster().getMaxRedirects());
            }
        }
        if (target.getJedis() != null && source.getJedis() != null) {
            if (Objects.equals(target.getJedis().getPool(), DEF_CONFIG.getJedis().getPool())) {
                target.getJedis().setPool(new RedisProperties.Pool());
            }
            target.getJedis().setPool(mergePool(source.getJedis().getPool(), target.getJedis().getPool()));
        }
        if (target.getLettuce() != null && source.getLettuce() != null) {
            if (Objects.equals(target.getLettuce().getShutdownTimeout(), DEF_CONFIG.getLettuce().getShutdownTimeout())) {
                target.getLettuce().setShutdownTimeout(source.getLettuce().getShutdownTimeout());
            }
            if (Objects.equals(target.getLettuce().getPool(), DEF_CONFIG.getLettuce().getPool())) {
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
        if (Objects.equals(target.getMaxIdle(), DEF_CONFIG_2.getMaxIdle())) {
            target.setMaxIdle(source.getMaxIdle());
        }
        if (Objects.equals(target.getMinIdle(), DEF_CONFIG_2.getMinIdle())) {
            target.setMinIdle(source.getMinIdle());
        }
        if (Objects.equals(target.getMaxActive(), DEF_CONFIG_2.getMaxActive())) {
            target.setMaxActive(source.getMaxActive());
        }
        if (Objects.equals(target.getMaxWait(), DEF_CONFIG_2.getMaxWait())) {
            target.setMaxWait(source.getMaxWait());
        }
        if (Objects.equals(target.getTimeBetweenEvictionRuns(), DEF_CONFIG_2.getTimeBetweenEvictionRuns())) {
            target.setTimeBetweenEvictionRuns(source.getTimeBetweenEvictionRuns());
        }
        return target;
    }
}
