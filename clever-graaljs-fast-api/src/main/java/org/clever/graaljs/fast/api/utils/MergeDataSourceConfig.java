package org.clever.graaljs.fast.api.utils;

import com.zaxxer.hikari.HikariConfig;

import java.util.Objects;

/**
 * 作者：lizw <br/>
 * 创建时间：2019/09/30 15:35 <br/>
 */
public class MergeDataSourceConfig {
    private static final HikariConfig DEF_CONFIG = new HikariConfig();

    public static HikariConfig mergeConfig(HikariConfig source, HikariConfig target) {
        if (source == null) {
            return target;
        }
        if (Objects.equals(target.getCatalog(), DEF_CONFIG.getCatalog())) {
            target.setCatalog(source.getCatalog());
        }
        if (Objects.equals(target.getConnectionTimeout(), DEF_CONFIG.getConnectionTimeout())) {
            target.setConnectionTimeout(source.getConnectionTimeout());
        }
        if (Objects.equals(target.getValidationTimeout(), DEF_CONFIG.getValidationTimeout())) {
            target.setValidationTimeout(source.getValidationTimeout());
        }
        if (Objects.equals(target.getIdleTimeout(), DEF_CONFIG.getIdleTimeout())) {
            target.setIdleTimeout(source.getIdleTimeout());
        }
        if (Objects.equals(target.getLeakDetectionThreshold(), DEF_CONFIG.getLeakDetectionThreshold())) {
            target.setLeakDetectionThreshold(source.getLeakDetectionThreshold());
        }
        if (Objects.equals(target.getMaxLifetime(), DEF_CONFIG.getMaxLifetime())) {
            target.setMaxLifetime(source.getMaxLifetime());
        }
        if (Objects.equals(target.getMaximumPoolSize(), DEF_CONFIG.getMaximumPoolSize())) {
            target.setMaximumPoolSize(source.getMaximumPoolSize());
        }
        if (Objects.equals(target.getMinimumIdle(), DEF_CONFIG.getMinimumIdle())) {
            target.setMinimumIdle(source.getMinimumIdle());
        }
        if (Objects.equals(target.getUsername(), DEF_CONFIG.getUsername())) {
            target.setUsername(source.getUsername());
        }
        if (Objects.equals(target.getPassword(), DEF_CONFIG.getPassword())) {
            target.setPassword(source.getPassword());
        }
        if (Objects.equals(target.getInitializationFailTimeout(), DEF_CONFIG.getInitializationFailTimeout())) {
            target.setInitializationFailTimeout(source.getInitializationFailTimeout());
        }
        if (Objects.equals(target.getConnectionInitSql(), DEF_CONFIG.getConnectionInitSql())) {
            target.setConnectionInitSql(source.getConnectionInitSql());
        }
        if (Objects.equals(target.getConnectionTestQuery(), DEF_CONFIG.getConnectionTestQuery())) {
            target.setConnectionTestQuery(source.getConnectionTestQuery());
        }
        if (Objects.equals(target.getDataSourceClassName(), DEF_CONFIG.getDataSourceClassName())) {
            target.setDataSourceClassName(source.getDataSourceClassName());
        }
        if (Objects.equals(target.getDataSourceJNDI(), DEF_CONFIG.getDataSourceJNDI())) {
            target.setDataSourceJNDI(source.getDataSourceJNDI());
        }
        if (Objects.equals(target.getDriverClassName(), DEF_CONFIG.getDriverClassName())) {
            target.setDriverClassName(source.getDriverClassName());
        }
        if (Objects.equals(target.getJdbcUrl(), DEF_CONFIG.getJdbcUrl())) {
            target.setJdbcUrl(source.getJdbcUrl());
        }
        if (Objects.equals(target.getPoolName(), DEF_CONFIG.getPoolName())) {
            target.setPoolName(source.getPoolName());
        }
        if (Objects.equals(target.getSchema(), DEF_CONFIG.getSchema())) {
            target.setSchema(source.getSchema());
        }
        if (Objects.equals(target.getTransactionIsolation(), DEF_CONFIG.getTransactionIsolation())) {
            target.setTransactionIsolation(source.getTransactionIsolation());
        }
        if (source.getDataSourceProperties() != null) {
            if (Objects.equals(target.getDataSourceProperties(), DEF_CONFIG.getDataSourceProperties())) {
                target.setDataSourceProperties(source.getDataSourceProperties());
            } else {
                source.getDataSourceProperties().forEach((propertyName, value) -> target.getDataSourceProperties().putIfAbsent(propertyName, value));
            }
        }
        if (source.getHealthCheckProperties() != null) {
            if (Objects.equals(target.getHealthCheckProperties(), DEF_CONFIG.getHealthCheckProperties())) {
                target.setHealthCheckProperties(source.getHealthCheckProperties());
            } else {
                source.getHealthCheckProperties().forEach((propertyName, value) -> target.getHealthCheckProperties().putIfAbsent(propertyName, value));
            }
        }
        return target;
    }
}
