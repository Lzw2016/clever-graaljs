package org.clever.graaljs.fast.api.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.clever.graaljs.core.internal.jackson.JacksonMapperSupport;
import org.clever.graaljs.data.redis.builtin.adapter.RedisDataSource;
import org.clever.graaljs.data.redis.builtin.wrap.RedisDatabase;
import org.clever.graaljs.fast.api.config.FastApiConfig;
import org.clever.graaljs.fast.api.config.MultipleRedisConfig;
import org.clever.graaljs.fast.api.utils.MergeRedisProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/14 14:53 <br/>
 */

@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE - 1)
@EnableConfigurationProperties({FastApiConfig.class})
@ConditionalOnClass({RedisProperties.class, RedisConnectionFactory.class, RedisDataSource.class, RedisDatabase.class})
@Configuration
@Slf4j
public class MultipleRedisAutoConfigure implements CommandLineRunner {
    private final List<RedisConnectionFactory> redisConnectionFactoryList = new ArrayList<>();
    private final MultipleRedisConfig multipleRedis;
    private final ObjectMapper objectMapper;

    protected boolean initialized = false;

    public MultipleRedisAutoConfigure(
            ObjectProvider<RedisConnectionFactory> redisConnectionFactoryList,
            FastApiConfig fastApiConfig) {
        redisConnectionFactoryList.forEach(this.redisConnectionFactoryList::add);
        this.multipleRedis = fastApiConfig.getMultipleRedis() == null ? new MultipleRedisConfig() : fastApiConfig.getMultipleRedis();
        this.objectMapper = JacksonMapperSupport.getRedisJacksonMapper().getMapper();
    }

    @Override
    public synchronized void run(String... args) {
        if (initialized) {
            return;
        }
        initialized = true;
        if (multipleRedis.isDisable()) {
            return;
        }
        // 加入已存在的数据源
        int index = 0;
        for (RedisConnectionFactory redisConnectionFactory : redisConnectionFactoryList) {
            index++;
            RedisDataSource redisDataSource = new RedisDataSource(redisConnectionFactory, objectMapper);
            String name = String.format("autowired-redis-%s", index);
            RedisDatabase.Instance.add(name, redisDataSource);
            log.info("初始化 RedisDataSource: {}", name);
        }
        // 初始化配置的数据源
        final RedisProperties globalConfig = multipleRedis.getGlobal();
        multipleRedis.getRedisMap().forEach((name, redisConfig) -> {
            if (RedisDatabase.Instance.hasDataSource(name)) {
                throw new RuntimeException("redis数据源名称重复: " + name);
            }
            redisConfig = MergeRedisProperties.mergeConfig(globalConfig, redisConfig);
            RedisDataSource redisDataSource = new RedisDataSource(redisConfig, objectMapper);
            RedisDatabase.Instance.add(name, redisDataSource);
            log.info("初始化 RedisDataSource: {}", name);
        });
        RedisDatabase.Instance.setDefault(multipleRedis.getDefaultName());
        log.info("默认的 RedisDataSource: {}", multipleRedis.getDefaultName());
    }
}
