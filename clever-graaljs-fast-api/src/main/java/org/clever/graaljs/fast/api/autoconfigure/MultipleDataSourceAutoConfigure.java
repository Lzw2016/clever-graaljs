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
import org.clever.graaljs.fast.api.config.MultipleDataSourceConfig;
import org.clever.graaljs.fast.api.utils.MergeDataSourceConfig;
import org.clever.graaljs.meta.data.builtin.wrap.MateDataManage;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/14 14:19 <br/>
 */
@Order
@ConditionalOnClass({HikariDataSource.class, JdbcDataSource.class, JdbcDatabase.class, MyBatisJdbcDataSource.class, MyBatisJdbcDatabase.class})
//@AutoConfigureAfter({AutoConfigureMyBatisMapperSql.class})
@Configuration
@EnableConfigurationProperties({MultipleDataSourceConfig.class})
@Slf4j
public class MultipleDataSourceAutoConfigure implements CommandLineRunner {
    private final List<DataSource> dataSourceList = new ArrayList<>();
    private final MultipleDataSourceConfig multipleDataSourceConfig;
    private final MyBatisMapperSql mybatisMapperSql;

    protected volatile boolean initialized = false;

    public MultipleDataSourceAutoConfigure(
            ObjectProvider<DataSource> dataSourceList,
            ObjectProvider<MultipleDataSourceConfig> multipleDataSourceConfig,
            ObjectProvider<MyBatisMapperSql> mybatisMapperSql) {
        for (DataSource dataSource : dataSourceList) {
            this.dataSourceList.add(dataSource);
        }
        this.multipleDataSourceConfig = multipleDataSourceConfig.getIfAvailable() == null ? new MultipleDataSourceConfig() : multipleDataSourceConfig.getIfAvailable();
        this.mybatisMapperSql = mybatisMapperSql.getIfAvailable();
    }

    @Override
    public synchronized void run(String... args) {
        if (initialized) {
            return;
        }
        initialized = true;
        if (multipleDataSourceConfig.isDisable()) {
            return;
        }
        int dataSourceCount = multipleDataSourceConfig.getJdbcMap().size() + dataSourceList.size();
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
            if (StringUtils.isBlank(multipleDataSourceConfig.getDefaultName())) {
                multipleDataSourceConfig.setDefaultName(name);
            }
        }
        if (StringUtils.isBlank(multipleDataSourceConfig.getDefaultName())) {
            throw new RuntimeException("默认的数据源名称 defaultName 不能是空");
        }
        // 初始化配置的数据源
        final HikariConfig dataSourceGlobalConfig = multipleDataSourceConfig.getGlobalConfig();
        multipleDataSourceConfig.getJdbcMap().forEach((name, hikariConfig) -> {
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
        MateDataManage.Instance.setDefault(multipleDataSourceConfig.getDefaultName());
        log.info("默认的 MateDataManage: {}", multipleDataSourceConfig.getDefaultName());
        // 默认的 JdbcDataSource
        JdbcDatabase.Instance.setDefault(multipleDataSourceConfig.getDefaultName());
        log.info("默认的 JdbcDataSource: {}", JdbcDatabase.Instance.getDefaultName());
        // 默认的 MyBatisJdbcDataSource
        if (mybatisMapperSql != null) {
            MyBatisJdbcDatabase.Instance.setDefault(multipleDataSourceConfig.getDefaultName());
            log.info("默认的 MyBatisJdbcDataSource: {}", multipleDataSourceConfig.getDefaultName());
        }
    }
}
