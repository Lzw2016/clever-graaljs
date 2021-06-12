package org.clever.graaljs.data.jdbc.support;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/11 07:28 <br/>
 */
@Slf4j
public class SqlLoggerUtils {
    private static final String Log_Sql /*         */ = "==>  ExecuteSQL: {}";
    private static final String Log_Parameters /*  */ = "==>  Parameters: {}";
    private static final String Log_Batch_Param /* */ = "==>  BatchParam: {}";
    private static final String Log_Total /*       */ = "<==       Total: {}";
    private static final String Log_Update_Total /**/ = "<==     Updated: {}";
    /**
     * 忽略的包前缀
     */
    private static final List<String> Ignore_Package_Prefix = Arrays.asList("java.lang.", "java.util.", "java.sql.");

    /**
     * 打印SQL以及其参数
     *
     * @param sql      sql语句
     * @param paramMap sql参数
     */
    public static void printfSql(String sql, Map<String, Object> paramMap) {
        if (!log.isDebugEnabled()) {
            return;
        }
        sql = deleteWhitespace(sql);
        log.debug(Log_Sql, sql);
        if (paramMap != null) {
            String paramMapStr = getParamMapStr(paramMap);
            log.debug(Log_Parameters, paramMapStr);
        }
    }

    /**
     * 打印SQL以及其参数
     *
     * @param sql          sql语句
     * @param paramMapList 参数数组
     */
    public static void printfSql(String sql, Collection<Map<String, Object>> paramMapList) {
        if (!log.isDebugEnabled()) {
            return;
        }
        sql = deleteWhitespace(sql);
        log.debug(Log_Sql, sql);
        if (paramMapList != null) {
            for (Map<String, Object> paramMap : paramMapList) {
                String paramMapStr = getParamMapStr(paramMap);
                log.debug(Log_Batch_Param, paramMapStr);
            }
        }
    }

    /**
     * 打印SQL查询结果数据量
     *
     * @param total 查询结果数据量
     */
    public static void printfTotal(Long total) {
        log.debug(Log_Total, total);
    }

    /**
     * 打印SQL查询结果数据量
     *
     * @param total 查询结果数据量
     */
    public static void printfTotal(int total) {
        log.debug(Log_Total, total);
    }

    /**
     * 打印SQL查询结果数据量
     *
     * @param res 查询结果Map
     */
    public static void printfTotal(Map<String, Object> res) {
        log.debug(Log_Total, res == null ? 0 : 1);
    }

    /**
     * 打印SQL查询结果数据量
     *
     * @param resCollection 查询结果Collection
     */
    public static void printfTotal(Collection<?> resCollection) {
        log.debug(Log_Total, resCollection == null ? 0 : resCollection.size());
    }

    /**
     * 打印SQL查询结果数据量
     *
     * @param res 查询结果Object
     */
    public static void printfTotal(Object res) {
        log.debug(Log_Total, res == null ? 0 : 1);
    }

    /**
     * 打印SQL更新数据量
     *
     * @param updateTotal 更新数据量
     */
    public static void printfUpdateTotal(int updateTotal) {
        log.debug(Log_Update_Total, updateTotal);
    }

    /**
     * 打印SQL查询结果数据量
     *
     * @param totals 查询结果数据量
     */
    public static void printfUpdateTotal(int[] totals) {
        log.debug(Log_Update_Total, Arrays.toString(totals));
    }

    private static String getParamMapStr(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(paramMap.size() * 15 + 32);
        for (Map.Entry<String, Object> paramEntry : paramMap.entrySet()) {
            String name = paramEntry.getKey();
            Object value = paramEntry.getValue();
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(name).append("=").append(value);
            if (value != null) {
                String valueType = value.getClass().getName();
                for (String packagePrefix : Ignore_Package_Prefix) {
                    if (valueType.startsWith(packagePrefix)) {
                        valueType = value.getClass().getSimpleName();
                        break;
                    }
                }
                sb.append("(").append(valueType).append(")");
            }
        }
        return sb.toString();
    }

    /**
     * 删除多余的空白字符
     */
    private static String deleteWhitespace(String sql) {
        if (sql == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(sql.length());
        boolean preIsWhitespace = false;
        for (int i = 0; i < sql.length(); i++) {
            char ch = sql.charAt(i);
            boolean isWhitespace = Character.isWhitespace(ch);
            if (preIsWhitespace) {
                // 之前是空白字符
                if (isWhitespace) {
                    // 当前是空白字符
                    continue;
                } else {
                    // 当前非空白字符
                    sb.append(ch);
                }
            } else {
                // 之前非空白字符
                if (isWhitespace) {
                    // 当前是空白字符
                    sb.append(' ');
                } else {
                    // 当前非空白字符
                    sb.append(ch);
                }
            }
            preIsWhitespace = isWhitespace;
        }
        return sb.toString();
    }
}
