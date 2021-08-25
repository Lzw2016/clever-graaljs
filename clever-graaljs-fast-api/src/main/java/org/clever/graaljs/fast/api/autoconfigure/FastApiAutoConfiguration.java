package org.clever.graaljs.fast.api.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.clever.graaljs.core.ScriptEngineConfig;
import org.clever.graaljs.core.ScriptEngineInstance;
import org.clever.graaljs.data.jdbc.DataJdbcEngineGlobalUtils;
import org.clever.graaljs.data.jdbc.builtin.wrap.JdbcDatabase;
import org.clever.graaljs.data.jdbc.builtin.wrap.MyBatisJdbcDatabase;
import org.clever.graaljs.data.redis.DataRedisEngineGlobalUtils;
import org.clever.graaljs.data.redis.builtin.wrap.RedisDatabase;
import org.clever.graaljs.fast.api.config.FastApiConfig;
import org.clever.graaljs.fast.api.config.MvcConfig;
import org.clever.graaljs.fast.api.intercept.FastApiHttpInterceptor;
import org.clever.graaljs.fast.api.plugin.PutGlobalObjects;
import org.clever.graaljs.fast.api.service.HttpApiFileResourceCacheService;
import org.clever.graaljs.meta.data.MetaDataEngineGlobalUtils;
import org.clever.graaljs.meta.data.builtin.wrap.MateDataManage;
import org.clever.graaljs.spring.SpringCommonEngineGlobalUtils;
import org.clever.graaljs.spring.mvc.DefaultExceptionResolver;
import org.clever.graaljs.spring.mvc.ExceptionResolver;
import org.clever.task.core.TaskInstance;
import org.clever.task.core.config.SchedulerConfig;
import org.clever.task.core.job.HttpJobExecutor;
import org.clever.task.core.job.JavaJobExecutor;
import org.clever.task.core.listeners.JobLogListener;
import org.clever.task.core.listeners.JobTriggerLogListener;
import org.clever.task.core.listeners.SchedulerLogListener;
import org.clever.task.ext.job.JsJobExecutor;
import org.clever.task.ext.job.ShellJobExecutor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.*;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/14 09:57 <br/>
 */
@ComponentScan(basePackages =
        {
                "org.clever.graaljs.fast.api.controller",
                "org.clever.graaljs.fast.api.websocket",
                "org.clever.graaljs.fast.api.service"
        }
)
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE - 3)
@EnableConfigurationProperties({FastApiConfig.class})
@Configuration
@Slf4j
public class FastApiAutoConfiguration {
    private final FastApiConfig fastApiConfig;

    public FastApiAutoConfiguration(FastApiConfig fastApiConfig) {
        this.fastApiConfig = fastApiConfig;
    }

    @Bean("scriptEngineInstance")
    @ConditionalOnMissingBean
    public ScriptEngineInstance scriptEngineInstance(ObjectProvider<List<PutGlobalObjects>> putGlobalObjectsListObjectProvider) {
        final ScriptEngineConfig scriptEngine = fastApiConfig.getScriptEngine();
        final Map<String, Object> contextMap = new HashMap<>();
        MetaDataEngineGlobalUtils.putGlobalObjects(contextMap);
        DataJdbcEngineGlobalUtils.putGlobalObjects(contextMap);
        DataRedisEngineGlobalUtils.putGlobalObjects(contextMap);
        SpringCommonEngineGlobalUtils.putGlobalObjects(contextMap);
        // 其他内置对象
        List<PutGlobalObjects> putGlobalObjectsList = putGlobalObjectsListObjectProvider.getIfAvailable();
        if (putGlobalObjectsList != null && !putGlobalObjectsList.isEmpty()) {
            for (PutGlobalObjects putGlobalObjects : putGlobalObjectsList) {
                putGlobalObjects.handle(contextMap);
            }
        }
        final Map<String, Object> global = new HashMap<>();
        final ScriptEngineInstance scriptEngineInstance = new ScriptEngineInstance(
                scriptEngine.toGenericObjectPoolConfig(),
                null,
                contextMap,
                global
        );
        log.info(
                "ScriptEngineInstance创建成功 -> Name={} | Version={} | {}",
                scriptEngineInstance.getEngineName(),
                scriptEngineInstance.getEngineVersion(),
                scriptEngineInstance.getLanguageVersion()
        );
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                log.info("关闭MateDataManage 开始...");
                MateDataManage.Instance.delAll();
                log.info("关闭MateDataManage 完成!");
            } catch (Exception e) {
                log.warn("关闭MateDataManage 失败", e);
            }
            try {
                log.info("关闭JdbcDatabase 开始...");
                JdbcDatabase.Instance.delAll();
                log.info("关闭JdbcDatabase 完成!");
            } catch (Exception e) {
                log.warn("关闭JdbcDatabase 失败", e);
            }
            try {
                log.info("关闭MyBatisJdbcDatabase 开始...");
                MyBatisJdbcDatabase.Instance.delAll();
                log.info("关闭MyBatisJdbcDatabase 完成!");
            } catch (Exception e) {
                log.warn("关闭MyBatisJdbcDatabase 失败", e);
            }
            try {
                log.info("关闭RedisDatabase 开始...");
                RedisDatabase.Instance.delAll();
                log.info("关闭RedisDatabase 完成!");
            } catch (Exception e) {
                log.warn("关闭RedisDatabase 失败", e);
            }
            try {
                log.info("关闭脚本引擎 开始...");
                scriptEngineInstance.close();
                log.info("关闭脚本引擎 完成!");
            } catch (Exception e) {
                log.warn("关闭脚本引擎 失败", e);
            }
        }));
        return scriptEngineInstance;
    }

    @Bean("taskInstance")
    @ConditionalOnMissingBean
    public TaskInstance taskInstance(DataSource dataSource, ScriptEngineInstance scriptEngineInstance) {
        final SchedulerConfig schedulerConfig = fastApiConfig.getTaskConfig().toSchedulerConfig();
        schedulerConfig.setNamespace(fastApiConfig.getNamespace());
        return new TaskInstance(
                dataSource,
                schedulerConfig,
                Arrays.asList(new HttpJobExecutor(), new JavaJobExecutor(), new ShellJobExecutor(), new JsJobExecutor(scriptEngineInstance)),
                Collections.singletonList(new SchedulerLogListener()),
                Collections.singletonList(new JobTriggerLogListener()),
                Collections.singletonList(new JobLogListener())
        );
    }

    @Bean("exceptionResolver")
    @ConditionalOnMissingBean
    public ExceptionResolver exceptionResolver() {
        return DefaultExceptionResolver.Instance;
    }

    @Bean("fastApiHttpInterceptor")
    @ConditionalOnMissingBean
    public FastApiHttpInterceptor fastApiHttpInterceptor(
            ExceptionResolver exceptionResolver,
            ScriptEngineInstance scriptEngineInstance,
            HttpApiFileResourceCacheService httpApiFileResourceCacheService,
            ObjectProvider<ConversionService> conversionService) {
        Assert.notNull(conversionService.getIfAvailable(), String.format("依赖实例%s未注入", ConversionService.class.getName()));
        final MvcConfig mvc = fastApiConfig.getMvc();
        return new FastApiHttpInterceptor(
                mvc.getPrefix(),
                mvc.getCors(),
                scriptEngineInstance,
                exceptionResolver,
                conversionService.getIfAvailable(),
                httpApiFileResourceCacheService
        );
    }
}
