package org.clever.graaljs.data.jdbc.dialects;

import java.util.Map;

/**
 * 作者： lzw<br/>
 * 创建时间：2019-10-03 12:52 <br/>
 */
public class DB2Dialect extends AbstractDialect {
    @Override
    public String doBuildPaginationSql(String originalSql, long offset, long limit, Map<String, Object> paramMap, String firstMark, String secondMark) {
        long firstParam = offset + 1;
        long secondParam = offset + limit;
        paramMap.put(firstMark, firstParam);
        paramMap.put(secondMark, secondParam);
        return "SELECT * FROM (SELECT TMP_PAGE.*,ROWNUMBER() OVER() AS ROW_ID FROM ( " + originalSql + " ) AS TMP_PAGE) TMP_PAGE WHERE ROW_ID BETWEEN " + (COLON + firstMark) + " AND " + (COLON + secondMark);
    }

    @Override
    public String buildPaginationSql(String originalSql, long offset, long limit) {
        long firstParam = offset + 1;
        long secondParam = offset + limit;
        return "SELECT * FROM (SELECT TMP_PAGE.*,ROWNUMBER() OVER() AS ROW_ID FROM ( " + originalSql + " ) AS TMP_PAGE) TMP_PAGE WHERE ROW_ID BETWEEN " + firstParam + " AND " + secondParam;
    }
}
