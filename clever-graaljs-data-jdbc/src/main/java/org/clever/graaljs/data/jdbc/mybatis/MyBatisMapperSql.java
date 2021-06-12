package org.clever.graaljs.data.jdbc.mybatis;

import org.clever.dynamic.sql.BoundSql;
import org.clever.dynamic.sql.builder.SqlSource;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/09/30 15:28 <br/>
 */
public interface MyBatisMapperSql {

    /**
     * 获取 SqlSource
     *
     * @param sqlId SQL ID
     */
    SqlSource getSqlSource(String sqlId);

    /**
     * 获取 BoundSql
     *
     * @param sqlId     SQL ID
     * @param parameter SQL参数
     */
    BoundSql getBoundSql(String sqlId, Object parameter);
}
