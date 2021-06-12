package org.clever.graaljs.data.jdbc;

import com.zaxxer.hikari.HikariConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOCase;
import org.clever.graaljs.core.utils.watch.FileSystemWatcher;
import org.clever.graaljs.data.jdbc.builtin.adapter.JdbcDataSource;
import org.clever.graaljs.data.jdbc.builtin.adapter.MyBatisJdbcDataSource;
import org.clever.graaljs.data.jdbc.mybatis.FileSystemMyBatisMapperSql;
import org.xml.sax.SAXParseException;

import java.util.HashSet;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/12 21:39 <br/>
 */
@Slf4j
public class BaseTest {
    public static HikariConfig newHikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mysql://192.168.1.201:12000/test-data");
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

    public static MyBatisJdbcDataSource newMyBatisJdbcDataSource(String absolutePath) {
        FileSystemMyBatisMapperSql mapperSql = new FileSystemMyBatisMapperSql(absolutePath);
        FileSystemWatcher watcher = new FileSystemWatcher(
                absolutePath,
                file -> {
                    final String absPath = file.getAbsolutePath();
                    try {
                        mapperSql.reloadFile(absPath);
                    } catch (Exception e) {
                        String error = e.getMessage();
                        if (e.getCause() instanceof SAXParseException) {
                            SAXParseException saxParseException = (SAXParseException) e.getCause();
                            error = String.format(
                                    "#第%d行，第%d列存在错误: %s",
                                    saxParseException.getLineNumber(),
                                    saxParseException.getColumnNumber(),
                                    saxParseException.getMessage()
                            );
                        }
                        log.error("#重新加载Mapper.xml文件失败 | path={} | error={}", absPath, error);
                    }
                },
                new HashSet<>(),
                new HashSet<String>() {{
                    add("*.xml");
                }},
                IOCase.SYSTEM,
                3000
        );
        watcher.start();
        Runtime.getRuntime().addShutdownHook(new Thread(watcher::stop));
        return new MyBatisJdbcDataSource(newJdbcDataSource(), mapperSql);
    }
}
