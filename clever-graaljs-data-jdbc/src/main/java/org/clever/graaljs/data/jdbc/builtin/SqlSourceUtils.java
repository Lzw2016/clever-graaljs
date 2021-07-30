package org.clever.graaljs.data.jdbc.builtin;

import org.clever.dynamic.sql.BoundSql;
import org.clever.dynamic.sql.builder.SqlSource;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/30 22:58 <br/>
 */
public class SqlSourceUtils {
    public static final SqlSourceUtils Instance = new SqlSourceUtils();

    private SqlSourceUtils() {
    }

    /**
     * 获取 SqlSource
     *
     * @param xmlSql MyBatis XML sql
     */
    public SqlSource getSqlSource(String xmlSql) {
        return null;
    }

    /**
     * 获取 BoundSql
     *
     * @param xmlSql    MyBatis XML sql
     * @param parameter SQL参数
     */
    public BoundSql getBoundSql(String xmlSql, Object parameter) {
        return null;
    }
}
