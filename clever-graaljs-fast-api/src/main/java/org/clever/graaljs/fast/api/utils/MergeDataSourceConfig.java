package org.clever.graaljs.fast.api.utils;

import com.zaxxer.hikari.HikariConfig;

/**
 * 作者：lizw <br/>
 * 创建时间：2019/09/30 15:35 <br/>
 */
public class MergeDataSourceConfig {

    public static HikariConfig mergeConfig(HikariConfig source, HikariConfig target) {
        if (source == null) {
            return target;
        }
        if (target.getCatalog() == null) {
            target.setCatalog(source.getCatalog());
        }
        if (target.getConnectionTimeout() == 0) {
            target.setConnectionTimeout(source.getConnectionTimeout());
        }
        if (target.getValidationTimeout() == 0) {
            target.setValidationTimeout(source.getValidationTimeout());
        }
        if (target.getIdleTimeout() == 0) {
            target.setIdleTimeout(source.getIdleTimeout());
        }
        if (target.getLeakDetectionThreshold() == 0) {
            target.setLeakDetectionThreshold(source.getLeakDetectionThreshold());
        }
        if (target.getMaxLifetime() == 0) {
            target.setMaxLifetime(source.getMaxLifetime());
        }
        if (target.getMaximumPoolSize() == 0) {
            target.setMaximumPoolSize(source.getMaximumPoolSize());
        }
        if (target.getMinimumIdle() == 0) {
            target.setMinimumIdle(source.getMinimumIdle());
        }
        if (target.getUsername() == null) {
            target.setUsername(source.getUsername());
        }
        if (target.getPassword() == null) {
            target.setPassword(source.getPassword());
        }
        if (target.getInitializationFailTimeout() == 0) {
            target.setInitializationFailTimeout(source.getInitializationFailTimeout());
        }
        if (target.getConnectionInitSql() == null) {
            target.setConnectionInitSql(source.getConnectionInitSql());
        }
        if (target.getConnectionTestQuery() == null) {
            target.setConnectionTestQuery(source.getConnectionTestQuery());
        }
        if (target.getDataSourceClassName() == null) {
            target.setDataSourceClassName(source.getDataSourceClassName());
        }
        if (target.getDataSourceJNDI() == null) {
            target.setDataSourceJNDI(source.getDataSourceJNDI());
        }
        if (target.getDriverClassName() == null) {
            target.setDriverClassName(source.getDriverClassName());
        }
        if (target.getJdbcUrl() == null) {
            target.setJdbcUrl(source.getJdbcUrl());
        }
        if (target.getPoolName() == null) {
            target.setPoolName(source.getPoolName());
        }
        if (target.getSchema() == null) {
            target.setSchema(source.getSchema());
        }
        if (target.getTransactionIsolation() == null) {
            target.setTransactionIsolation(source.getTransactionIsolation());
        }
        if (source.getDataSourceProperties() != null) {
            if (target.getDataSourceProperties() == null) {
                target.setDataSourceProperties(source.getDataSourceProperties());
            } else {
                source.getDataSourceProperties().forEach((propertyName, value) -> target.getDataSourceProperties().putIfAbsent(propertyName, value));
            }
        }
        if (source.getHealthCheckProperties() != null) {
            if (target.getHealthCheckProperties() == null) {
                target.setHealthCheckProperties(source.getHealthCheckProperties());
            } else {
                source.getHealthCheckProperties().forEach((propertyName, value) -> target.getHealthCheckProperties().putIfAbsent(propertyName, value));
            }
        }
        return target;
    }
}
