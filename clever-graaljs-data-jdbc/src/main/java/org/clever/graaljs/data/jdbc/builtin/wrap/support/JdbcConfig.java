package org.clever.graaljs.data.jdbc.builtin.wrap.support;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zaxxer.hikari.HikariConfig;
import lombok.Data;
import org.clever.graaljs.core.utils.Assert;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class JdbcConfig implements Serializable {

    private String driverClassName;

    private String jdbcUrl;

    private String username;

    private String password;

    private Boolean autoCommit;

    private Boolean readOnly;

    private Integer maxPoolSize;

    private Integer minIdle;

    private Long maxLifetimeMs;

    private Long connectionTimeoutMs;

    private Long idleTimeoutMs;

    private String connectionTestQuery;

    private Map<String, Object> dataSourceProperties = new HashMap<>();

    @JsonIgnore
    public HikariConfig getHikariConfig() {
        Assert.isNotBlank(driverClassName, "参数driverClassName不能为空");
        Assert.isNotBlank(jdbcUrl, "参数jdbcUrl不能为空");
        Assert.isNotBlank(username, "参数username不能为空");
        Assert.isNotBlank(password, "参数password不能为空");
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        if (autoCommit != null) {
            hikariConfig.setAutoCommit(autoCommit);
        }
        if (readOnly != null) {
            hikariConfig.setReadOnly(readOnly);
        }
        if (maxPoolSize != null) {
            hikariConfig.setMaximumPoolSize(maxPoolSize);
        }
        if (minIdle != null) {
            hikariConfig.setMinimumIdle(minIdle);
        }
        if (maxLifetimeMs != null) {
            hikariConfig.setMaxLifetime(maxLifetimeMs);
        }
        if (connectionTimeoutMs != null) {
            hikariConfig.setConnectionTimeout(connectionTimeoutMs);
        }
        if (idleTimeoutMs != null) {
            hikariConfig.setIdleTimeout(idleTimeoutMs);
        }
        if (connectionTestQuery != null) {
            hikariConfig.setConnectionTestQuery(connectionTestQuery);
        }
        if (dataSourceProperties != null && !dataSourceProperties.isEmpty()) {
            hikariConfig.getDataSourceProperties().putAll(dataSourceProperties);
        }
        return hikariConfig;
    }
}
