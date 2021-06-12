package org.clever.graaljs.data.jdbc.builtin.wrap.support;

import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/09/02 21:32 <br/>
 */
public abstract class AbstractJdbcDatabase {

    @SuppressWarnings("unchecked")
    protected JdbcConfig getJdbcConfig(Map<String, Object> config) {
        JdbcConfig jdbcConfig = new JdbcConfig();
        jdbcConfig.setDriverClassName((String) config.get("driverClassName"));
        jdbcConfig.setJdbcUrl((String) config.get("jdbcUrl"));
        jdbcConfig.setUsername((String) config.get("username"));
        jdbcConfig.setPassword((String) config.get("password"));
        jdbcConfig.setAutoCommit((Boolean) config.get("autoCommit"));
        jdbcConfig.setReadOnly((Boolean) config.get("readOnly"));
        jdbcConfig.setMaxPoolSize((Integer) config.get("maxPoolSize"));
        jdbcConfig.setMinIdle((Integer) config.get("minIdle"));
        jdbcConfig.setMaxLifetimeMs((Long) config.get("maxLifetimeMs"));
        jdbcConfig.setConnectionTimeoutMs((Long) config.get("connectionTimeoutMs"));
        jdbcConfig.setIdleTimeoutMs((Long) config.get("idleTimeoutMs"));
        jdbcConfig.setConnectionTestQuery((String) config.get("connectionTestQuery"));
        jdbcConfig.setDataSourceProperties((Map<String, Object>) config.get("dataSourceProperties"));
        return jdbcConfig;
    }
}
