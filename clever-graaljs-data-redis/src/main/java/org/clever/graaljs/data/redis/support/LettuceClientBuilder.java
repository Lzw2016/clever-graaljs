package org.clever.graaljs.data.redis.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import lombok.Getter;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 参考 org.springframework.boot.autoconfigure.data.redis.LettuceConnectionConfiguration
 * <p>
 * 作者： lzw<br/>
 * 创建时间：2019-10-07 22:16 <br/>
 */
public class LettuceClientBuilder implements DisposableBean {
    /**
     * 默认的JSON序列化反序列化对象
     */
    private static final ObjectMapper Default_Object_Mapper = new ObjectMapper();

    private final RedisProperties properties;
    private final RedisSentinelConfiguration sentinelConfiguration;
    private final RedisClusterConfiguration clusterConfiguration;
    private final ClientResources clientResources;
    private final List<LettuceClientConfigurationBuilderCustomizer> builderCustomizers;

    @Getter
    private final ObjectMapper objectMapper;
    @Getter
    private final RedisConnectionFactory redisConnectionFactory;
    @Getter
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * @param properties            Redis配置
     * @param sentinelConfiguration Redis Sentinel配置
     * @param clusterConfiguration  Redis集群配置
     * @param builderCustomizers    自定义配置
     * @param objectMapper          JSON序列化反序列化对象
     */
    public LettuceClientBuilder(
            RedisProperties properties,
            RedisSentinelConfiguration sentinelConfiguration,
            RedisClusterConfiguration clusterConfiguration,
            List<LettuceClientConfigurationBuilderCustomizer> builderCustomizers,
            ObjectMapper objectMapper) {
        Assert.notNull(properties, "RedisProperties不能为空");
        this.properties = properties;
        this.sentinelConfiguration = sentinelConfiguration;
        this.clusterConfiguration = clusterConfiguration;
        this.builderCustomizers = builderCustomizers;
        this.objectMapper = objectMapper == null ? Default_Object_Mapper : objectMapper;
        this.clientResources = DefaultClientResources.create();
        this.redisConnectionFactory = redisConnectionFactory(this.clientResources);
        this.redisTemplate = initRedisTemplate();
    }

    /**
     * @param properties           Redis配置
     * @param clusterConfiguration Redis集群配置
     * @param builderCustomizers   自定义配置
     * @param objectMapper         JSON序列化反序列化对象
     */
    public LettuceClientBuilder(
            RedisProperties properties,
            RedisClusterConfiguration clusterConfiguration,
            List<LettuceClientConfigurationBuilderCustomizer> builderCustomizers,
            ObjectMapper objectMapper) {
        this(properties, null, clusterConfiguration, builderCustomizers, objectMapper);
    }

    /**
     * @param properties         Redis配置
     * @param builderCustomizers 自定义配置
     * @param objectMapper       JSON序列化反序列化对象
     */
    public LettuceClientBuilder(RedisProperties properties, List<LettuceClientConfigurationBuilderCustomizer> builderCustomizers, ObjectMapper objectMapper) {
        this(properties, null, null, builderCustomizers, objectMapper);
    }

    /**
     * @param properties        Redis配置
     * @param builderCustomizer 自定义配置
     * @param objectMapper      JSON序列化反序列化对象
     */
    public LettuceClientBuilder(RedisProperties properties, LettuceClientConfigurationBuilderCustomizer builderCustomizer, ObjectMapper objectMapper) {
        this(properties, null, null, Collections.singletonList(builderCustomizer), objectMapper);
    }

    /**
     * @param properties   Redis配置
     * @param objectMapper JSON序列化反序列化对象
     */
    public LettuceClientBuilder(RedisProperties properties, ObjectMapper objectMapper) {
        this(properties, null, null, null, objectMapper);
    }

    /**
     * @param properties Redis配置
     */
    public LettuceClientBuilder(RedisProperties properties) {
        this(properties, null, null, null, Default_Object_Mapper);
    }

    /**
     * @param redisConnectionFactory Redis Connection 工厂
     * @param objectMapper           JSON序列化反序列化对象
     */
    public LettuceClientBuilder(RedisConnectionFactory redisConnectionFactory, ObjectMapper objectMapper) {
        Assert.notNull(redisConnectionFactory, "RedisConnectionFactory不能为空");
        this.properties = null;
        this.sentinelConfiguration = null;
        this.clusterConfiguration = null;
        this.builderCustomizers = null;
        this.objectMapper = objectMapper == null ? Default_Object_Mapper : objectMapper;
        this.clientResources = null;
        this.redisConnectionFactory = redisConnectionFactory;
        this.redisTemplate = initRedisTemplate();
    }

    /**
     * @param redisConnectionFactory Redis Connection 工厂
     */
    public LettuceClientBuilder(RedisConnectionFactory redisConnectionFactory) {
        this(redisConnectionFactory, Default_Object_Mapper);
    }

    private RedisTemplate<String, Object> initRedisTemplate() {
        Assert.notNull(redisConnectionFactory, "RedisConnectionFactory不能为空");
        Assert.notNull(objectMapper, "ObjectMapper不能为空");
        // 创建 RedisTemplate
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        // 设置序列化规则
        template.setStringSerializer(RedisSerializer.string());
        template.setDefaultSerializer(RedisSerializer.string());
        template.setEnableDefaultSerializer(true);
        // template.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        serializer.setObjectMapper(objectMapper);
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(serializer);
        template.setHashValueSerializer(serializer);
        template.afterPropertiesSet();
        return template;
    }

    @Override
    public void destroy() {
        if (redisConnectionFactory instanceof LettuceConnectionFactory) {
            LettuceConnectionFactory lettuceConnectionFactory = (LettuceConnectionFactory) redisConnectionFactory;
            lettuceConnectionFactory.destroy();
        } else if (redisConnectionFactory instanceof JedisConnectionFactory) {
            JedisConnectionFactory jedisConnectionFactory = (JedisConnectionFactory) redisConnectionFactory;
            jedisConnectionFactory.destroy();
        } else {
            throw new UnsupportedOperationException("当前RedisConnectionFactory不支持destroy");
        }
        if (clientResources != null) {
            clientResources.shutdown();
        }
    }

    // --------------------------------------------------------------------------------------------------------------------------------------------------------- LettuceConnectionConfiguration

    public LettuceConnectionFactory redisConnectionFactory(ClientResources clientResources) {
        LettuceClientConfiguration clientConfig = getLettuceClientConfiguration(clientResources, this.properties.getLettuce().getPool());
        LettuceConnectionFactory lettuceConnectionFactory = createLettuceConnectionFactory(clientConfig);
        lettuceConnectionFactory.afterPropertiesSet();
        return lettuceConnectionFactory;
    }

    private LettuceConnectionFactory createLettuceConnectionFactory(LettuceClientConfiguration clientConfiguration) {
        if (getSentinelConfig() != null) {
            return new LettuceConnectionFactory(getSentinelConfig(), clientConfiguration);
        }
        if (getClusterConfiguration() != null) {
            return new LettuceConnectionFactory(getClusterConfiguration(), clientConfiguration);
        }
        return new LettuceConnectionFactory(getStandaloneConfig(), clientConfiguration);
    }

    private LettuceClientConfiguration getLettuceClientConfiguration(ClientResources clientResources, RedisProperties.Pool pool) {
        LettuceClientConfiguration.LettuceClientConfigurationBuilder builder = createBuilder(pool);
        applyProperties(builder);
        if (StringUtils.hasText(this.properties.getUrl())) {
            customizeConfigurationFromUrl(builder);
        }
        builder.clientResources(clientResources);
        customize(builder);
        return builder.build();
    }

    private LettuceClientConfiguration.LettuceClientConfigurationBuilder createBuilder(RedisProperties.Pool pool) {
        if (pool == null) {
            return LettuceClientConfiguration.builder();
        }
        return new PoolBuilderFactory().createBuilder(pool);
    }

    @SuppressWarnings("UnusedReturnValue")
    private LettuceClientConfiguration.LettuceClientConfigurationBuilder applyProperties(LettuceClientConfiguration.LettuceClientConfigurationBuilder builder) {
        if (this.properties.isSsl()) {
            builder.useSsl();
        }
        if (this.properties.getTimeout() != null) {
            builder.commandTimeout(this.properties.getTimeout());
        }
        if (this.properties.getLettuce() != null) {
            RedisProperties.Lettuce lettuce = this.properties.getLettuce();
            if (lettuce.getShutdownTimeout() != null && !lettuce.getShutdownTimeout().isZero()) {
                builder.shutdownTimeout(this.properties.getLettuce().getShutdownTimeout());
            }
        }
        return builder;
    }

    private void customizeConfigurationFromUrl(LettuceClientConfiguration.LettuceClientConfigurationBuilder builder) {
        ConnectionInfo connectionInfo = parseUrl(this.properties.getUrl());
        if (connectionInfo.isUseSsl()) {
            builder.useSsl();
        }
    }

    private void customize(LettuceClientConfiguration.LettuceClientConfigurationBuilder builder) {
        if (builderCustomizers == null) {
            return;
        }
        this.builderCustomizers.forEach((customizer) -> customizer.customize(builder));
    }

    /**
     * Inner class to allow optional commons-pool2 dependency.
     */
    private static class PoolBuilderFactory {
        public LettuceClientConfiguration.LettuceClientConfigurationBuilder createBuilder(RedisProperties.Pool properties) {
            return LettucePoolingClientConfiguration.builder().poolConfig(getPoolConfig(properties));
        }

        private GenericObjectPoolConfig<?> getPoolConfig(RedisProperties.Pool properties) {
            GenericObjectPoolConfig<?> config = new GenericObjectPoolConfig<>();
            config.setMaxTotal(properties.getMaxActive());
            config.setMaxIdle(properties.getMaxIdle());
            config.setMinIdle(properties.getMinIdle());
            if (properties.getTimeBetweenEvictionRuns() != null) {
                config.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRuns().toMillis());
            }
            if (properties.getMaxWait() != null) {
                config.setMaxWaitMillis(properties.getMaxWait().toMillis());
            }
            return config;
        }
    }


    // --------------------------------------------------------------------------------------------------------------------------------------------------------- RedisConnectionConfiguration

    protected final RedisStandaloneConfiguration getStandaloneConfig() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        if (StringUtils.hasText(this.properties.getUrl())) {
            ConnectionInfo connectionInfo = parseUrl(this.properties.getUrl());
            config.setHostName(connectionInfo.getHostName());
            config.setPort(connectionInfo.getPort());
            config.setPassword(RedisPassword.of(connectionInfo.getPassword()));
        } else {
            config.setHostName(this.properties.getHost());
            config.setPort(this.properties.getPort());
            config.setPassword(RedisPassword.of(this.properties.getPassword()));
        }
        config.setDatabase(this.properties.getDatabase());
        return config;
    }

    protected final RedisSentinelConfiguration getSentinelConfig() {
        if (this.sentinelConfiguration != null) {
            return this.sentinelConfiguration;
        }
        RedisProperties.Sentinel sentinelProperties = this.properties.getSentinel();
        if (sentinelProperties != null) {
            RedisSentinelConfiguration config = new RedisSentinelConfiguration();
            config.master(sentinelProperties.getMaster());
            config.setSentinels(createSentinels(sentinelProperties));
            if (this.properties.getPassword() != null) {
                config.setPassword(RedisPassword.of(this.properties.getPassword()));
            }
            config.setDatabase(this.properties.getDatabase());
            return config;
        }
        return null;
    }

    /**
     * Create a {@link RedisClusterConfiguration} if necessary.
     *
     * @return {@literal null} if no cluster settings are set.
     */
    protected final RedisClusterConfiguration getClusterConfiguration() {
        if (this.clusterConfiguration != null) {
            return this.clusterConfiguration;
        }
        if (this.properties.getCluster() == null) {
            return null;
        }
        RedisProperties.Cluster clusterProperties = this.properties.getCluster();
        RedisClusterConfiguration config = new RedisClusterConfiguration(clusterProperties.getNodes());
        if (clusterProperties.getMaxRedirects() != null) {
            config.setMaxRedirects(clusterProperties.getMaxRedirects());
        }
        if (this.properties.getPassword() != null) {
            config.setPassword(RedisPassword.of(this.properties.getPassword()));
        }
        return config;
    }

    private List<RedisNode> createSentinels(RedisProperties.Sentinel sentinel) {
        List<RedisNode> nodes = new ArrayList<>();
        for (String node : sentinel.getNodes()) {
            try {
                String[] parts = StringUtils.split(node, ":");
                assert parts != null;
                Assert.state(parts.length == 2, "Must be defined as 'host:port'");
                nodes.add(new RedisNode(parts[0], Integer.parseInt(parts[1])));
            } catch (RuntimeException ex) {
                throw new IllegalStateException("Invalid redis sentinel " + "property '" + node + "'", ex);
            }
        }
        return nodes;
    }

    protected ConnectionInfo parseUrl(String url) {
        try {
            URI uri = new URI(url);
            boolean useSsl = (url.startsWith("rediss://"));
            String password = null;
            if (uri.getUserInfo() != null) {
                password = uri.getUserInfo();
                int index = password.indexOf(':');
                if (index >= 0) {
                    password = password.substring(index + 1);
                }
            }
            return new ConnectionInfo(uri, useSsl, password);
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException("Malformed url '" + url + "'", ex);
        }
    }

    protected static class ConnectionInfo {
        private final URI uri;

        private final boolean useSsl;

        private final String password;

        public ConnectionInfo(URI uri, boolean useSsl, String password) {
            this.uri = uri;
            this.useSsl = useSsl;
            this.password = password;
        }

        public boolean isUseSsl() {
            return this.useSsl;
        }

        public String getHostName() {
            return this.uri.getHost();
        }

        public int getPort() {
            return this.uri.getPort();
        }

        public String getPassword() {
            return this.password;
        }
    }
}
