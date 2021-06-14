package org.clever.graaljs.fast.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.io.Serializable;
import java.time.Duration;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/14 10:17 <br/>
 */
@ConfigurationProperties(prefix = Constant.CONFIG_ROOT)
@Data
public class FileResourcesConfig implements Serializable {
    /**
     * 资源文件存储类型
     */
    private StorageType storageType = StorageType.JdbcDatabase;
    /**
     * 命名空间
     */
    private String namespace;
    /**
     * mybatis mapper.xml文件路径
     */
    private String mapperPath;
    /**
     * 监听资源文件变化配置(JdbcDatabase类型)
     */
    @NestedConfigurationProperty
    private JdbcDatabaseWatcherConfig jdbcDatabaseWatcherConfig;

    @Data
    public static class JdbcDatabaseWatcherConfig implements Serializable {
        /**
         * 文件检查时间间隔(默认3秒)
         */
        private Duration interval = Duration.ofSeconds(3);
    }
}
