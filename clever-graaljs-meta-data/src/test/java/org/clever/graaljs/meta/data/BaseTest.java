package org.clever.graaljs.meta.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/12 21:39 <br/>
 */
@Slf4j
public class BaseTest {
    public static HikariDataSource newHikariDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mysql://192.168.1.201:12000/test-data");
        hikariConfig.setUsername("test-data");
        hikariConfig.setPassword("Aa123456!");
        hikariConfig.setAutoCommit(false);
        hikariConfig.setMinimumIdle(1);
        hikariConfig.setMaximumPoolSize(10);
        return new HikariDataSource(hikariConfig);
    }
}
