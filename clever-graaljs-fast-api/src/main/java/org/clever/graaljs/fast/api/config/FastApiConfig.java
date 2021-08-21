package org.clever.graaljs.fast.api.config;

import lombok.Data;
import org.clever.graaljs.core.ScriptEngineConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.io.Serializable;
import java.time.Duration;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/14 10:17 <br/>
 */
@ConfigurationProperties(prefix = FastApiConfig.CONFIG_ROOT)
@Data
public class FastApiConfig implements Serializable {
    static final String CONFIG_ROOT = "clever.fast-api";

    /**
     * FileResource 命名空间
     */
    private String namespace = "default";
    /**
     * 文件资源变化检查时间间隔(默认3秒)
     */
    private Duration scanInterval = Duration.ofSeconds(3);
    /**
     * 脚本引擎配置
     */
    @NestedConfigurationProperty
    private ScriptEngineConfig scriptEngine = new ScriptEngineConfig();
    /**
     * MVC配置
     */
    @NestedConfigurationProperty
    private MvcConfig mvc = new MvcConfig();
    /**
     * 多JDBC数据源配置
     */
    @NestedConfigurationProperty
    private MultipleJdbcConfig multipleJdbc = new MultipleJdbcConfig();
    /**
     * 多Redis数据源配置
     */
    @NestedConfigurationProperty
    private MultipleRedisConfig multipleRedis = new MultipleRedisConfig();
    /**
     * 定时任务配置
     */
    @NestedConfigurationProperty
    private TaskConfig taskConfig = new TaskConfig();
}
