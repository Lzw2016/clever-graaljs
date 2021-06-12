package org.clever.graaljs.data.jdbc.support.mybatisplus;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/12 11:06 <br/>
 */
@SuppressWarnings("ALL")
public class SqlParserUtils {
    private static ISqlParser COUNT_SQL_PARSER = null;

    /**
     * 获取 COUNT 原生 SQL 包装
     *
     * @param originalSql ignore
     * @return ignore
     */
    public static String getOriginalCountSql(String originalSql) {
        return String.format("SELECT COUNT(1) FROM ( %s ) TOTAL", originalSql);
    }

    /**
     * 获取CountOptimize
     *
     * @param optimizeCountSql 是否优化 Count SQL
     * @param sqlParser        Count SQL 解析类
     * @param originalSql      需要计算Count SQL
     * @return SqlInfo
     */
    public static SqlInfo getOptimizeCountSql(boolean optimizeCountSql, ISqlParser sqlParser, String originalSql) {
        if (!optimizeCountSql) {
            return SqlInfo.newInstance().setSql(getOriginalCountSql(originalSql));
        }
        // COUNT SQL 解析器
        if (null == COUNT_SQL_PARSER) {
            if (null != sqlParser) {
                // 用户自定义 COUNT SQL 解析
                COUNT_SQL_PARSER = sqlParser;
            } else {
                // 默认 JsqlParser 优化 COUNT
                COUNT_SQL_PARSER = new JsqlParserCountOptimize();
            }
        }
        return COUNT_SQL_PARSER.parser(null, originalSql);
    }
}
