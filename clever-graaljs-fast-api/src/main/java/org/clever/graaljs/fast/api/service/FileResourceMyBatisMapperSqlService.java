package org.clever.graaljs.fast.api.service;

import org.clever.graaljs.data.jdbc.mybatis.AbstractMyBatisMapperSql;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/14 14:42 <br/>
 */
public class FileResourceMyBatisMapperSqlService extends AbstractMyBatisMapperSql {
    private final JdbcTemplate jdbcTemplate;

    public FileResourceMyBatisMapperSqlService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void reloadAll() {

    }

    @Override
    public void reloadFile(String absolutePath) {

    }
}
