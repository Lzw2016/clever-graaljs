package org.clever.graaljs.data.jdbc.dialects;


import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 作者： lzw<br/>
 * 创建时间：2019-10-03 12:59 <br/>
 */
@SuppressWarnings("DuplicatedCode")
public class SQLServer2005Dialect extends AbstractDialect {
    private static String getOrderByPart(String sql) {
        String loweredString = sql.toLowerCase();
        int orderByIndex = loweredString.indexOf("order by");
        if (orderByIndex != -1) {
            return sql.substring(orderByIndex);
        } else {
            return StringUtils.EMPTY;
        }
    }

    @Override
    public String doBuildPaginationSql(String originalSql, long offset, long limit, Map<String, Object> paramMap, String firstMark, String secondMark) {
        StringBuilder pagingBuilder = new StringBuilder();
        String orderBy = getOrderByPart(originalSql);
        String distinctStr = StringUtils.EMPTY;

        String loweredString = originalSql.toLowerCase();
        String sqlPartString = originalSql;
        if (loweredString.trim().startsWith("select")) {
            int index = 6;
            if (loweredString.startsWith("select distinct")) {
                distinctStr = "DISTINCT ";
                index = 15;
            }
            sqlPartString = sqlPartString.substring(index);
        }
        pagingBuilder.append(sqlPartString);

        // if no ORDER BY is specified use fake ORDER BY field to avoid errors
        if (StringUtils.isBlank(orderBy)) {
            orderBy = "ORDER BY CURRENT_TIMESTAMP";
        }
        long firstParam = offset + 1;
        long secondParam = offset + limit;
        paramMap.put(firstMark, firstParam);
        paramMap.put(secondMark, secondParam);
        return "WITH selectTemp AS (SELECT " + distinctStr + "TOP 100 PERCENT " +
                " ROW_NUMBER() OVER (" + orderBy + ") as __row_number__, " + pagingBuilder +
                ") SELECT * FROM selectTemp WHERE __row_number__ BETWEEN " +
                //FIX#299：原因：mysql中limit 10(offset,size) 是从第10开始（不包含10）,；而这里用的BETWEEN是两边都包含，所以改为offset+1
                firstParam + " AND " + secondParam + " ORDER BY __row_number__";
    }

    @Override
    public String buildPaginationSql(String originalSql, long offset, long limit) {
        StringBuilder pagingBuilder = new StringBuilder();
        String orderBy = getOrderByPart(originalSql);
        String distinctStr = StringUtils.EMPTY;

        String loweredString = originalSql.toLowerCase();
        String sqlPartString = originalSql;
        if (loweredString.trim().startsWith("select")) {
            int index = 6;
            if (loweredString.startsWith("select distinct")) {
                distinctStr = "DISTINCT ";
                index = 15;
            }
            sqlPartString = sqlPartString.substring(index);
        }
        pagingBuilder.append(sqlPartString);

        // if no ORDER BY is specified use fake ORDER BY field to avoid errors
        if (StringUtils.isBlank(orderBy)) {
            orderBy = "ORDER BY CURRENT_TIMESTAMP";
        }
        long firstParam = offset + 1;
        long secondParam = offset + limit;
        return "WITH selectTemp AS (SELECT " + distinctStr + "TOP 100 PERCENT " +
                " ROW_NUMBER() OVER (" + orderBy + ") as __row_number__, " + pagingBuilder +
                ") SELECT * FROM selectTemp WHERE __row_number__ BETWEEN " +
                //FIX#299：原因：mysql中limit 10(offset,size) 是从第10开始（不包含10）,；而这里用的BETWEEN是两边都包含，所以改为offset+1
                firstParam + " AND " + secondParam + " ORDER BY __row_number__";
    }
}
