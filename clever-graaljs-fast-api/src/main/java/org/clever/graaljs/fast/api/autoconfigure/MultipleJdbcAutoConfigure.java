package org.clever.graaljs.fast.api.autoconfigure;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.data.jdbc.builtin.adapter.JdbcDataSource;
import org.clever.graaljs.data.jdbc.builtin.adapter.MyBatisJdbcDataSource;
import org.clever.graaljs.data.jdbc.builtin.wrap.JdbcDatabase;
import org.clever.graaljs.data.jdbc.builtin.wrap.MyBatisJdbcDatabase;
import org.clever.graaljs.data.jdbc.mybatis.MyBatisMapperSql;
import org.clever.graaljs.fast.api.config.FastApiConfig;
import org.clever.graaljs.fast.api.config.MultipleJdbcConfig;
import org.clever.graaljs.fast.api.utils.MergeDataSourceConfig;
import org.clever.graaljs.meta.data.builtin.wrap.MateDataManage;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/14 14:19 <br/>
 */
@AutoConfigureAfter({FastApiAutoConfiguration.class})
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
@EnableConfigurationProperties({FastApiConfig.class})
@ConditionalOnClass({HikariDataSource.class, JdbcDataSource.class, JdbcDatabase.class, MyBatisJdbcDataSource.class, MyBatisJdbcDatabase.class})
@Configuration
@Slf4j
public class MultipleJdbcAutoConfigure implements CommandLineRunner {
    private final List<DataSource> dataSourceList = new ArrayList<>();
    private final MultipleJdbcConfig multipleJdbc;
    private final MyBatisMapperSql mybatisMapperSql;

    protected volatile boolean initialized = false;

    public MultipleJdbcAutoConfigure(
            ObjectProvider<DataSource> dataSourceList,
            FastApiConfig fastApiConfig,
            ObjectProvider<MyBatisMapperSql> mybatisMapperSql) {
        Assert.notNull(mybatisMapperSql.getIfUnique(), String.format("依赖实例%s未注入或注入多个", MyBatisMapperSql.class.getName()));
        dataSourceList.forEach(this.dataSourceList::add);
        this.multipleJdbc = fastApiConfig.getMultipleJdbc() == null ? new MultipleJdbcConfig() : fastApiConfig.getMultipleJdbc();
        this.mybatisMapperSql = mybatisMapperSql.getIfUnique();
    }

    @Override
    public synchronized void run(String... args) {
        if (initialized) {
            return;
        }
        initialized = true;
        if (multipleJdbc.isDisable()) {
            return;
        }
        int dataSourceCount = multipleJdbc.getJdbcMap().size() + dataSourceList.size();
        final Map<String, DataSource> dataSourceMap = new HashMap<>(dataSourceCount);
        // 加入已存在的数据源
        for (DataSource dataSource : dataSourceList) {
            String name = null;
            if (dataSource instanceof HikariDataSource) {
                HikariDataSource tmp = (HikariDataSource) dataSource;
                name = tmp.getPoolName();
            }
            if (StringUtils.isBlank(name)) {
                name = dataSource.toString();
            }
            if (dataSourceMap.containsKey(name)) {
                throw new RuntimeException("JdbcDataSource 名称重复: " + name);
            }
            dataSourceMap.put(name, dataSource);
            if (StringUtils.isBlank(multipleJdbc.getDefaultName())) {
                multipleJdbc.setDefaultName(name);
            }
        }
        if (StringUtils.isBlank(multipleJdbc.getDefaultName())) {
            throw new RuntimeException("默认的数据源名称 defaultName 不能是空");
        }
        // 初始化配置的数据源
        final HikariConfig dataSourceGlobalConfig = multipleJdbc.getGlobal();
        multipleJdbc.getJdbcMap().forEach((name, hikariConfig) -> {
            if (dataSourceMap.containsKey(name)) {
                throw new RuntimeException("JdbcDataSource 名称重复: " + name);
            }
            hikariConfig = MergeDataSourceConfig.mergeConfig(dataSourceGlobalConfig, hikariConfig);
            if (StringUtils.isBlank(hikariConfig.getPoolName())) {
                hikariConfig.setPoolName(name);
            }
            HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
            dataSourceMap.put(name, hikariDataSource);
        });
        // 初始化 JdbcDatabase、MyBatisJdbcDatabase、MateDataManage
        for (Map.Entry<String, DataSource> entry : dataSourceMap.entrySet()) {
            String name = entry.getKey();
            DataSource dataSource = entry.getValue();
            // 初始化 MateDataManage
            MateDataManage.Instance.add(name, dataSource);
            log.info("初始化 MateDataManage: {}", name);
            // 初始化 JdbcDataSource
            JdbcDataSource jdbcDataSource = new JdbcDataSource(dataSource);
            JdbcDatabase.Instance.add(name, jdbcDataSource);
            log.info("初始化 JdbcDataSource: {}", name);
            // 初始化 MyBatisJdbcDataSource
            if (mybatisMapperSql != null) {
                MyBatisJdbcDataSource myBatisJdbcDataSource = new MyBatisJdbcDataSource(jdbcDataSource, mybatisMapperSql);
                MyBatisJdbcDatabase.Instance.add(name, myBatisJdbcDataSource);
                log.info("初始化 MyBatisJdbcDataSource: {}", name);
            }
        }
        // 默认的 MateDataManage
        MateDataManage.Instance.setDefault(multipleJdbc.getDefaultName());
        log.info("默认的 MateDataManage: {}", multipleJdbc.getDefaultName());
        // 默认的 JdbcDataSource
        JdbcDatabase.Instance.setDefault(multipleJdbc.getDefaultName());
        log.info("默认的 JdbcDataSource: {}", JdbcDatabase.Instance.getDefaultName());
        // 默认的 MyBatisJdbcDataSource
        if (mybatisMapperSql != null) {
            MyBatisJdbcDatabase.Instance.setDefault(multipleJdbc.getDefaultName());
            log.info("默认的 MyBatisJdbcDataSource: {}", multipleJdbc.getDefaultName());
        }
    }
}
