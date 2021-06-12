package org.clever.graaljs.data.redis;

import org.clever.graaljs.core.internal.jackson.JacksonMapperSupport;
import org.clever.graaljs.data.redis.builtin.adapter.RedisDataSource;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/13 00:33 <br/>
 */
public class BaseTest {
    public static RedisProperties newRedisProperties() {
        RedisProperties properties = new RedisProperties();
        properties.setHost("192.168.1.201");
        properties.setPort(12001);
        properties.setPassword("a123456!@#");
        properties.setDatabase(5);
        properties.getLettuce().setPool(new RedisProperties.Pool());
        properties.getLettuce().getPool().setMaxIdle(1);
        properties.getLettuce().getPool().setMaxActive(10);
        return properties;
    }

    public static RedisDataSource newRedisDataSource() {
        return new RedisDataSource(newRedisProperties(), JacksonMapperSupport.getRedisJacksonMapper().getMapper());
    }
}
