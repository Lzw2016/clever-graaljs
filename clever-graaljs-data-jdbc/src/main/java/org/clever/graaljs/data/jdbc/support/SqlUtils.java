package org.clever.graaljs.data.jdbc.support;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.core.utils.StrFormatter;
import org.clever.graaljs.core.utils.TupleTow;
import org.clever.graaljs.data.common.model.request.QueryBySort;
import org.clever.graaljs.data.jdbc.support.mybatisplus.SqlInfo;
import org.clever.graaljs.data.jdbc.support.mybatisplus.SqlParserUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/10 09:16 <br/>
 */
public class SqlUtils {
    public static final String ASC = "ASC";
    public static final String DESC = "DESC";
    public static final String COMMA = ",";
    /**
     * Count Sql 缓存(最大3K条数据)
     */
    private static final Cache<String, String> Count_Sql_Cache = CacheBuilder.newBuilder().maximumSize(4096).initialCapacity(256).build();

    /**
     * 查询SQL拼接Order By
     *
     * @param originalSql 需要拼接的SQL
     * @param queryBySort 排序对象
     * @return ignore
     */
    public static String concatOrderBy(String originalSql, QueryBySort queryBySort) {
        if (null != queryBySort && queryBySort.getOrderFields() != null && queryBySort.getOrderFields().size() > 0) {
            List<String> orderFields = queryBySort.getOrderFieldsSql();
            List<String> sorts = queryBySort.getSortsSql();
            StringBuilder buildSql = new StringBuilder(originalSql);
            StringBuilder orderBySql = new StringBuilder();
            for (int index = 0; index < orderFields.size(); index++) {
                String orderField = orderFields.get(index);
                if (orderField != null) {
                    orderField = orderField.trim();
                }
                if (orderField == null || orderField.length() <= 0) {
                    continue;
                }
                String sort = ASC;
                if (sorts.size() > index) {
                    sort = sorts.get(index);
                    if (sort != null) {
                        sort = sort.trim();
                    }
                    if (!DESC.equalsIgnoreCase(sort) && !ASC.equalsIgnoreCase(sort)) {
                        sort = ASC;
                    }
                }
                String orderByStr = concatOrderBuilder(orderField, sort.toUpperCase());
                if (StringUtils.isNotBlank(orderByStr)) {
                    if (orderBySql.length() > 0) {
                        orderBySql.append(COMMA).append(' ');
                    }
                    orderBySql.append(orderByStr.trim());
                }
            }
            if (orderBySql.length() > 0) {
                buildSql.append(" ORDER BY ").append(orderBySql.toString());
            }
            return buildSql.toString();
        }
        return originalSql;
    }

    /**
     * 获取优化后的count查询sql语句
     *
     * @param sql 原始sql
     */
    public static String getCountSql(String sql) {
        String countSql = Count_Sql_Cache.getIfPresent(StringUtils.trim(sql));
        if (StringUtils.isBlank(countSql)) {
            SqlInfo sqlInfo = SqlParserUtils.getOptimizeCountSql(true, null, sql);
            countSql = sqlInfo.getSql();
            Count_Sql_Cache.put(sql, countSql);
        }
        return countSql;
    }

    /**
     * 生成更新table的sql TODO 不更新null??
     *
     * @param tableName         表名称
     * @param fields            字段值
     * @param whereMap          where条件(and条件，只支持=)
     * @param camelToUnderscore 是否使用驼峰转下划线
     */
    public static TupleTow<String, Map<String, Object>> updateSql(String tableName, Map<String, Object> fields, Map<String, Object> whereMap, boolean camelToUnderscore) {
        Map<String, Object> paramMap = new HashMap<>(fields.size() + (whereMap == null ? 0 : whereMap.size()));
        StringBuilder sb = new StringBuilder();
        sb.append("update ").append(tableName).append(" set");
        int index = 0;
        for (Map.Entry<String, ?> field : fields.entrySet()) {
            String fieldName = field.getKey();
            Object value = field.getValue();
            String fieldParam = "set_" + fieldName;
            if (index == 0) {
                sb.append(' ');
            } else {
                sb.append(", ");
            }
            sb.append(getFieldName(fieldName, camelToUnderscore)).append("=:").append(fieldParam);
            paramMap.put(fieldParam, value);
            index++;
        }
        TupleTow<String, Map<String, Object>> whereSql = getWhereSql(whereMap, camelToUnderscore);
        sb.append(whereSql.getValue1());
        paramMap.putAll(whereSql.getValue2());
        return TupleTow.creat(sb.toString(), paramMap);
    }

    /**
     * 生成删除table的sql
     *
     * @param tableName         表名称
     * @param whereMap          where条件(and条件，只支持=)
     * @param camelToUnderscore 是否使用驼峰转下划线
     */
    public static TupleTow<String, Map<String, Object>> deleteSql(String tableName, Map<String, Object> whereMap, boolean camelToUnderscore) {
        Map<String, Object> paramMap = new HashMap<>((whereMap == null ? 0 : whereMap.size()));
        StringBuilder sb = new StringBuilder();
        sb.append("delete from ").append(tableName);
        TupleTow<String, Map<String, Object>> whereSql = getWhereSql(whereMap, camelToUnderscore);
        sb.append(whereSql.getValue1());
        paramMap.putAll(whereSql.getValue2());
        return TupleTow.creat(sb.toString(), paramMap);
    }

    // TODO 不插入null??
    public static TupleTow<String, Map<String, Object>> insertSql(String tableName, Map<String, Object> fields, boolean camelToUnderscore) {
        Map<String, Object> paramMap = new HashMap<>(fields.size());
        StringBuilder sb = new StringBuilder();
        sb.append("insert into ").append(tableName).append(" (");
        int index = 0;
        for (Map.Entry<String, ?> field : fields.entrySet()) {
            String fieldName = field.getKey();
            if (index != 0) {
                sb.append(", ");
            }
            sb.append(getFieldName(fieldName, camelToUnderscore));
            index++;
        }
        sb.append(") values (");
        index = 0;
        for (Map.Entry<String, ?> field : fields.entrySet()) {
            String fieldName = field.getKey();
            Object value = field.getValue();
            if (index != 0) {
                sb.append(", ");
            }
            sb.append(":").append(fieldName);
            paramMap.put(fieldName, value);
            index++;
        }
        sb.append(")");
        return TupleTow.creat(sb.toString(), paramMap);
    }

    /**
     * 生成查询table的sql
     *
     * @param tableName         表名称
     * @param whereMap          where条件(and条件，只支持=)
     * @param camelToUnderscore 是否使用驼峰转下划线
     */
    public static TupleTow<String, Map<String, Object>> selectSql(String tableName, Map<String, Object> whereMap, boolean camelToUnderscore) {
        Map<String, Object> paramMap = new HashMap<>((whereMap == null ? 0 : whereMap.size()));
        StringBuilder sb = new StringBuilder();
        sb.append("select * from ").append(tableName);
        TupleTow<String, Map<String, Object>> whereSql = getWhereSql(whereMap, camelToUnderscore);
        sb.append(whereSql.getValue1());
        paramMap.putAll(whereSql.getValue2());
        return TupleTow.creat(sb.toString(), paramMap);
    }

    /**
     * 根据where参数生成where sql字符串
     */
    private static TupleTow<String, Map<String, Object>> getWhereSql(Map<String, Object> whereMap, boolean camelToUnderscore) {
        Map<String, Object> paramMap = new HashMap<>((whereMap == null ? 0 : whereMap.size()));
        StringBuilder sb = new StringBuilder();
        if (whereMap != null && !whereMap.isEmpty()) {
            sb.append(" where");
            int index = 0;
            for (Map.Entry<String, ?> where : whereMap.entrySet()) {
                String fieldName = where.getKey();
                Object value = where.getValue();
                String fieldParam = "where_" + fieldName;
                if (index == 0) {
                    sb.append(' ');
                } else {
                    sb.append(" and ");
                }
                sb.append(getFieldName(fieldName, camelToUnderscore)).append("=:").append(fieldParam);
                paramMap.put(fieldParam, value);
                index++;
            }
        }
        return TupleTow.creat(sb.toString(), paramMap);
    }

    /**
     * 拼接多个排序方法
     *
     * @param column    ignore
     * @param orderWord ignore
     */
    private static String concatOrderBuilder(String column, String orderWord) {
        if (StringUtils.isNotBlank(column)) {
            return column + ' ' + orderWord;
        }
        return StringUtils.EMPTY;
    }

    private static String getFieldName(String fieldName, boolean camelToUnderscore) {
        if (!camelToUnderscore) {
            return fieldName;
        }
        return StrFormatter.camelToUnderline(fieldName);
    }
}
