package org.clever.graaljs.fast.api.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.clever.graaljs.core.ScriptEngineInstance;
import org.clever.graaljs.data.jdbc.builtin.wrap.JdbcDatabase;
import org.clever.graaljs.data.jdbc.builtin.wrap.MyBatisJdbcDatabase;
import org.clever.graaljs.data.redis.builtin.wrap.RedisDatabase;
import org.clever.graaljs.fast.api.config.EngineInstanceConfig;
import org.clever.graaljs.meta.data.builtin.wrap.MateDataManage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/14 13:57 <br/>
 */
@Configuration
@EnableConfigurationProperties({EngineInstanceConfig.class})
@Slf4j
public class EngineInstanceAutoConfiguration {
    private final EngineInstanceConfig engineInstanceConfig;

    public EngineInstanceAutoConfiguration(EngineInstanceConfig engineInstanceConfig) {
        this.engineInstanceConfig = engineInstanceConfig;
    }

    @Bean("scriptEngineInstance")
    @ConditionalOnMissingBean
    public ScriptEngineInstance scriptEngineInstance() {
        final Map<String, Object> contextMap = new HashMap<>();
        contextMap.put("MateDataManage", MateDataManage.Instance);
        contextMap.put("JdbcDatabase", JdbcDatabase.Instance);
        contextMap.put("MyBatisJdbcDatabase", MyBatisJdbcDatabase.Instance);
        contextMap.put("RedisDatabase", RedisDatabase.Instance);


        final Map<String, Object> global = new HashMap<>();
        final ScriptEngineInstance scriptEngineInstance = new ScriptEngineInstance(
                engineInstanceConfig.toGenericObjectPoolConfig(),
                null,
                contextMap,
                global
        );
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                log.info("### 关闭MateDataManage,开始...");
                MateDataManage.Instance.delAll();
                log.info("### 关闭MateDataManage,完成!");
            } catch (Exception e) {
                log.warn("### 关闭MateDataManage,失败", e);
            }
            try {
                log.info("### 关闭JdbcDatabase,开始...");
                JdbcDatabase.Instance.delAll();
                log.info("### 关闭JdbcDatabase,完成!");
            } catch (Exception e) {
                log.warn("### 关闭JdbcDatabase,失败", e);
            }
            try {
                log.info("### 关闭MyBatisJdbcDatabase,开始...");
                MyBatisJdbcDatabase.Instance.delAll();
                log.info("### 关闭MyBatisJdbcDatabase,完成!");
            } catch (Exception e) {
                log.warn("### 关闭MyBatisJdbcDatabase,失败", e);
            }
            try {
                log.info("### 关闭RedisDatabase,开始...");
                RedisDatabase.Instance.delAll();
                log.info("### 关闭RedisDatabase,完成!");
            } catch (Exception e) {
                log.warn("### 关闭RedisDatabase,失败", e);
            }
            try {
                log.info("### 关闭脚本引擎,开始...");
                scriptEngineInstance.close();
                log.info("### 关闭脚本引擎,完成!");
            } catch (Exception e) {
                log.warn("### 关闭脚本引擎,失败", e);
            }
        }));
        return scriptEngineInstance;
    }
}
