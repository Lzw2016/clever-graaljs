package org.clever.graaljs.fast.api.autoconfigure;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.core.utils.mapper.JacksonMapper;
import org.clever.graaljs.data.jdbc.builtin.adapter.JdbcDataSource;
import org.clever.graaljs.data.jdbc.builtin.adapter.MyBatisJdbcDataSource;
import org.clever.graaljs.data.jdbc.builtin.wrap.JdbcDatabase;
import org.clever.graaljs.data.jdbc.builtin.wrap.MyBatisJdbcDatabase;
import org.clever.graaljs.data.jdbc.builtin.wrap.support.JdbcConfig;
import org.clever.graaljs.data.jdbc.mybatis.MyBatisMapperSql;
import org.clever.graaljs.fast.api.config.FastApiConfig;
import org.clever.graaljs.fast.api.config.MultipleJdbcConfig;
import org.clever.graaljs.fast.api.entity.DataSourceConfig;
import org.clever.graaljs.fast.api.entity.EnumConstant;
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
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.*;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/14 14:19 <br/>
 */
@AutoConfigureAfter({FastApiAutoConfiguration.class})
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE - 2)
@EnableConfigurationProperties({FastApiConfig.class})
@ConditionalOnClass({HikariDataSource.class, JdbcDataSource.class, JdbcDatabase.class, MyBatisJdbcDataSource.class, MyBatisJdbcDatabase.class})
@Configuration
@Slf4j
public class MultipleJdbcAutoConfigure implements CommandLineRunner {
    private static final String ALL_DATA_SOURCE_CONFIG = "select * from data_source_config where namespace=? and type=?";

    private static final Set<String> IMMUTABLE_DATA_SOURCE_NAMES = new HashSet<>();

    public static boolean isImmutable(String name) {
        return IMMUTABLE_DATA_SOURCE_NAMES.contains(name);
    }

    private final String namespace;
    private final List<DataSource> dataSourceList = new ArrayList<>();
    private final MultipleJdbcConfig multipleJdbc;
    private final MyBatisMapperSql mybatisMapperSql;
    private final JdbcTemplate jdbcTemplate;

    protected volatile boolean initialized = false;

    public MultipleJdbcAutoConfigure(
            ObjectProvider<DataSource> dataSourceList,
            FastApiConfig fastApiConfig,
            ObjectProvider<MyBatisMapperSql> mybatisMapperSql,
            ObjectProvider<JdbcTemplate> jdbcTemplate) {
        Assert.notNull(mybatisMapperSql.getIfUnique(), String.format("依赖实例%s未注入或注入多个", MyBatisMapperSql.class.getName()));
        Assert.notNull(jdbcTemplate.getIfAvailable(), String.format("依赖实例%s未注入", JdbcTemplate.class.getName()));
        this.namespace = fastApiConfig.getNamespace();
        dataSourceList.forEach(this.dataSourceList::add);
        this.multipleJdbc = fastApiConfig.getMultipleJdbc() == null ? new MultipleJdbcConfig() : fastApiConfig.getMultipleJdbc();
        this.mybatisMapperSql = mybatisMapperSql.getIfUnique();
        this.jdbcTemplate = jdbcTemplate.getIfAvailable();
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
            IMMUTABLE_DATA_SOURCE_NAMES.add(name);
            addJdbcDataSource(name, dataSource, mybatisMapperSql);
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
        // 加载数据库配置
        List<DataSourceConfig> configList = jdbcTemplate.query(
                ALL_DATA_SOURCE_CONFIG, DataClassRowMapper.newInstance(DataSourceConfig.class),
                namespace, EnumConstant.TYPE_JDBC
        );
        configList.forEach(config -> {
            try {
                JdbcConfig jdbcConfig = JacksonMapper.getInstance().fromJson(config.getConfig(), JdbcConfig.class);
                if (IMMUTABLE_DATA_SOURCE_NAMES.contains(config.getName())) {
                    throw new RuntimeException("Jdbc数据源名称冲突：" + config.getName());
                }
                addJdbcDataSource(config.getName(), jdbcConfig, mybatisMapperSql);
            } catch (Exception e) {
                log.error("初始化Jdbc数据源失败", e);
                System.exit(-1);
            }
        });
    }

    public static void addJdbcDataSource(String name, JdbcConfig jdbcConfig, MyBatisMapperSql mybatisMapperSql) {
        final HikariConfig hikariConfig = jdbcConfig.getHikariConfig();
        hikariConfig.setPoolName(name);
        final DataSource dataSource = new HikariDataSource(hikariConfig);
        addJdbcDataSource(name, dataSource, mybatisMapperSql);
    }

    private static void addJdbcDataSource(String name, DataSource dataSource, MyBatisMapperSql mybatisMapperSql) {
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

    public static void delJdbcDataSource(String name) {
        MateDataManage.Instance.del(name);
        JdbcDatabase.Instance.del(name);
        MyBatisJdbcDatabase.Instance.del(name);
    }
}
