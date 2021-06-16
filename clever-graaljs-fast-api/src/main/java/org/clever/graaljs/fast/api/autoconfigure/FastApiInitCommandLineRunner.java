package org.clever.graaljs.fast.api.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.clever.graaljs.data.jdbc.mybatis.MyBatisMapperSql;
import org.clever.graaljs.fast.api.service.FileResourceCacheService;
import org.clever.graaljs.fast.api.service.FileResourceMyBatisMapperSqlService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.util.Assert;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/16 13:27 <br/>
 */
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
@Configuration
@Slf4j
public class FastApiInitCommandLineRunner implements CommandLineRunner {
    private final ScheduledExecutorService scheduled = Executors.newSingleThreadScheduledExecutor();
    private final FileResourceCacheService fileResourceCacheService;
    private final MyBatisMapperSql mybatisMapperSql;

    public FastApiInitCommandLineRunner(
            ObjectProvider<FileResourceCacheService> fileResourceCacheService,
            ObjectProvider<MyBatisMapperSql> mybatisMapperSql) {
        Assert.notNull(fileResourceCacheService.getIfUnique(), String.format("依赖实例%s未注入或注入多个", FileResourceCacheService.class.getName()));
        Assert.notNull(mybatisMapperSql.getIfUnique(), String.format("依赖实例%s未注入或注入多个", MyBatisMapperSql.class.getName()));
        this.fileResourceCacheService = fileResourceCacheService.getIfUnique();
        this.mybatisMapperSql = mybatisMapperSql.getIfUnique();
    }

    @Override
    public void run(String... args) {
        log.info("FastApi初始化...");
        fileResourceCacheService.reload();
        if (mybatisMapperSql instanceof FileResourceMyBatisMapperSqlService) {
            FileResourceMyBatisMapperSqlService fileResourceMyBatisMapperSqlService = (FileResourceMyBatisMapperSqlService) mybatisMapperSql;
            fileResourceMyBatisMapperSqlService.reload();
        }
        // 定时更新
        scheduled.scheduleWithFixedDelay(() -> {
            try {
                fileResourceCacheService.updateCache();
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
        log.info("FastApi初始化完成!");
    }
}
