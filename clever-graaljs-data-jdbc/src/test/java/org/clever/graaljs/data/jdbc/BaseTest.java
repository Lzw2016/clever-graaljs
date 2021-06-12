package org.clever.graaljs.data.jdbc;

import com.zaxxer.hikari.HikariConfig;
import org.clever.graaljs.data.jdbc.builtin.adapter.JdbcDataSource;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/12 21:39 <br/>
 */
public class BaseTest {
    public static HikariConfig newHikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mysql://mysql.hinny.top:12000/test-data");
        hikariConfig.setUsername("test-data");
        hikariConfig.setPassword("Aa123456!");
        hikariConfig.setAutoCommit(false);
        hikariConfig.setMinimumIdle(1);
        hikariConfig.setMaximumPoolSize(10);
        return hikariConfig;
    }

    public static JdbcDataSource newJdbcDataSource() {
        return new JdbcDataSource(newHikariConfig());
    }
}
