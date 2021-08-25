package org.clever.graaljs.fast.api.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.core.ScriptEngineInstance;
import org.clever.graaljs.data.jdbc.mybatis.MyBatisMapperSql;
import org.clever.graaljs.fast.api.config.FastApiConfig;
import org.clever.graaljs.fast.api.model.HttpApiFileResource;
import org.clever.graaljs.fast.api.service.FileResourceMyBatisMapperSqlService;
import org.clever.graaljs.fast.api.service.HttpApiFileResourceCacheService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.util.Assert;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/16 13:27 <br/>
 */
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
@EnableConfigurationProperties({FastApiConfig.class})
@Configuration
@Slf4j
public class FastApiInitCommandLineRunner implements CommandLineRunner {
    private final FastApiConfig fastApiConfig;
    private final ScriptEngineInstance scriptEngineInstance;
    private final ScheduledExecutorService scheduled = Executors.newSingleThreadScheduledExecutor();
    private final HttpApiFileResourceCacheService httpApiFileResourceCacheService;
    private final MyBatisMapperSql mybatisMapperSql;

    public FastApiInitCommandLineRunner(
            FastApiConfig fastApiConfig,
            ScriptEngineInstance scriptEngineInstance,
            ObjectProvider<HttpApiFileResourceCacheService> fileResourceCacheService,
            ObjectProvider<MyBatisMapperSql> mybatisMapperSql) {
        Assert.notNull(fileResourceCacheService.getIfUnique(), String.format("依赖实例%s未注入或注入多个", HttpApiFileResourceCacheService.class.getName()));
        Assert.notNull(mybatisMapperSql.getIfUnique(), String.format("依赖实例%s未注入或注入多个", MyBatisMapperSql.class.getName()));
        this.fastApiConfig = fastApiConfig;
        this.scriptEngineInstance = scriptEngineInstance;
        this.httpApiFileResourceCacheService = fileResourceCacheService.getIfUnique();
        this.mybatisMapperSql = mybatisMapperSql.getIfUnique();
    }

    @Override
    public void run(String... args) {
        log.info("FastApi初始化...");
        httpApiFileResourceCacheService.reload();
        if (mybatisMapperSql instanceof FileResourceMyBatisMapperSqlService) {
            FileResourceMyBatisMapperSqlService fileResourceMyBatisMapperSqlService = (FileResourceMyBatisMapperSqlService) mybatisMapperSql;
            fileResourceMyBatisMapperSqlService.reload();
        }
        // 定时更新
        scheduled.scheduleWithFixedDelay(() -> {
            try {
                httpApiFileResourceCacheService.updateCache();
            } catch (Exception e) {
                log.warn("增量更新FileResource失败", e);
            }
            try {
                if (mybatisMapperSql instanceof FileResourceMyBatisMapperSqlService) {
                    FileResourceMyBatisMapperSqlService fileResourceMyBatisMapperSqlService = (FileResourceMyBatisMapperSqlService) mybatisMapperSql;
                    fileResourceMyBatisMapperSqlService.updateCache();
                }
            } catch (Exception e) {
                log.warn("增量更新MyBatisMapperSql失败", e);
            }
        }, 1, 3, TimeUnit.SECONDS);
        Runtime.getRuntime().addShutdownHook(new Thread(scheduled::shutdownNow));
        // 预加载脚本
        if (fastApiConfig.isPreload()) {
            List<HttpApiFileResource> allHttpApiFileResource = httpApiFileResourceCacheService.getAllHttpApiFileResource();
            for (HttpApiFileResource httpApiFileResource : allHttpApiFileResource) {
                if (StringUtils.isBlank(httpApiFileResource.getContent())) {
                    continue;
                }
                try {
                    scriptEngineInstance.wrapFunction(httpApiFileResource.getContent());
                    log.info("预加载脚本：{} 成功", httpApiFileResource.getPath() + httpApiFileResource.getName());
                } catch (Exception e) {
                    log.error("预加载脚本：{} 失败", httpApiFileResource.getPath() + httpApiFileResource.getName(), e);
                }
            }
        }
        log.info("FastApi初始化完成!");
    }
}
