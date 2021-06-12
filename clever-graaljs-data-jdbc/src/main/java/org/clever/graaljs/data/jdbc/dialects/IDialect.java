package org.clever.graaljs.data.jdbc.dialects;

import java.util.Map;

/**
 * 作者： lzw<br/>
 * 创建时间：2019-10-03 12:14 <br/>
 */
public interface IDialect {
    String COLON = ":";
    String COMMA = ",";
    String FIRST_MARK = "first_mark";
    String SECOND_MARK = "second_mark";

    /**
     * 组装分页语句(使用分页参数)
     *
     * @param originalSql 原始语句
     * @param offset      偏移量
     * @param limit       界限
     * @param paramMap    Sql参数
     */
    String buildPaginationSql(String originalSql, long offset, long limit, Map<String, Object> paramMap);

    /**
     * 组装分页语句(不使用分页参数)
     *
     * @param originalSql 原始语句
     * @param offset      偏移量
     * @param limit       界限
     */
    String buildPaginationSql(String originalSql, long offset, long limit);
}
