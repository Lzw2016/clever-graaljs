package org.clever.graaljs.data.jdbc.dialects;

import java.util.Map;

/**
 * 作者： lzw<br/>
 * 创建时间：2019-10-03 12:55 <br/>
 */
public class H2Dialect extends AbstractDialect {
    @Override
    public String doBuildPaginationSql(String originalSql, long offset, long limit, Map<String, Object> paramMap, String firstMark, String secondMark) {
        String sql = originalSql + " limit " + (COLON + firstMark);
        if (offset > 0) {
            sql += (" offset " + (COLON + secondMark));
        }
        return sql;
    }

    @Override
    public String buildPaginationSql(String originalSql, long offset, long limit) {
        String sql = originalSql + " limit " + offset;
        if (offset > 0) {
            sql += (" offset " + limit);
        }
        return sql;
    }
}
