package org.clever.graaljs.data.jdbc.builtin.adapter;

import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.data.common.model.request.QueryByPage;
import org.clever.graaljs.data.jdbc.dialects.IDialect;
import org.clever.graaljs.data.jdbc.support.DialectFactory;
import org.clever.graaljs.data.jdbc.support.SqlUtils;
import org.clever.graaljs.data.jdbc.support.mybatisplus.DbType;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/22 15:56 <br/>
 */
public class SQLBuilder {
    private static final String SPACE = StringUtils.SPACE;
    private static final String COMMA = ",";
    private static final String WHERE_AND = "AND";
    public static final String ASC = "ASC";
    public static final String DESC = "DESC";

    /**
     * 创建一个Select构建器
     */
    public static SelectBuilder newSelectBuilder() {
        return new SelectBuilder();
    }

    /**
     * 创建一个Update构建器
     */
    public static UpdateBuilder newUpdateBuilder() {
        return new UpdateBuilder();
    }

    /**
     * 创建一个Insert构建器
     */
    public static InsertBuilder newInsertBuilder() {
        return new InsertBuilder();
    }

    /**
     * 当最后一个非空白字符是{delStr}时，将其删除
     */
    private static void delLast(StringBuilder sb, String delStr) {
        for (int i = sb.length() - 1; i >= 0; i--) {
            char c = sb.charAt(i);
            if (Character.isWhitespace(c)) {
                sb.deleteCharAt(i);
                continue;
            }
            if (sb.toString().endsWith(delStr)) {
                sb.delete(sb.length() - delStr.length(), sb.length());
            }
            break;
        }
    }

    public static class SelectBuilder {
        /**
         * 数据库类型(跟分页有关系)
         */
        private DbType dbType = DbType.MYSQL;
        /**
         * 数据偏移量(1 <= pageSize <= 1000)
         */
        private Integer size;
        /**
         * 第一条数据的起始位置(offset >= 0)
         */
        private Integer offset;
        /**
         * 查询参数
         */
        private final Map<String, Object> paramMap = new HashMap<>();
        /**
         * 查询的的列(字段)
         */
        private final StringBuilder select = new StringBuilder();
        /**
         * 查询的表
         */
        private final StringBuilder from = new StringBuilder();
        /**
         * 查询条件
         */
        private final StringBuilder where = new StringBuilder();
        /**
         * 查询分组
         */
        private final StringBuilder groupBy = new StringBuilder();
        /**
         * 过滤分组条件
         */
        private final StringBuilder having = new StringBuilder();
        /**
         * 排序字段(列)
         */
        private final StringBuilder orderBy = new StringBuilder();

        private SelectBuilder() {
        }

        public DbType getDbType() {
            return dbType;
        }

        /**
         * 获取 sql
         */
        public String buildSql() {
            // select <select> from <from> where <where> group by <groupBy> having <having> order by <orderBy>
            StringBuilder sql = new StringBuilder();
            delLast(this.select, COMMA);
            delLast(this.from, COMMA);
            delLast(this.where, WHERE_AND);
            delLast(this.groupBy, COMMA);
            delLast(this.having, WHERE_AND);
            delLast(this.orderBy, COMMA);
            final String select = StringUtils.trimToEmpty(this.select.toString());
            final String from = StringUtils.trimToEmpty(this.from.toString());
            final String where = StringUtils.trimToEmpty(this.where.toString());
            final String groupBy = StringUtils.trimToEmpty(this.groupBy.toString());
            final String having = StringUtils.trimToEmpty(this.having.toString());
            final String orderBy = StringUtils.trimToEmpty(this.orderBy.toString());
            if (StringUtils.isNotBlank(select)) {
                sql.append("SELECT ").append(select).append(SPACE);
            }
            if (StringUtils.isNotBlank(from)) {
                sql.append("FROM ").append(from).append(SPACE);
            }
            if (StringUtils.isNotBlank(where)) {
                sql.append("WHERE ").append(where).append(SPACE);
            }
            if (StringUtils.isNotBlank(groupBy)) {
                sql.append("GROUP BY ").append(groupBy).append(SPACE);
            }
            if (StringUtils.isNotBlank(having)) {
                sql.append("HAVING ").append(having).append(SPACE);
            }
            if (StringUtils.isNotBlank(orderBy)) {
                sql.append("ORDER BY ").append(orderBy).append(SPACE);
            }
            String sqlStr = StringUtils.trimToEmpty(sql.toString());
            if (offset != null && size != null) {
                if (dbType == null) {
                    dbType = DbType.MYSQL;
                }
                if (offset < 0) {
                    offset = 0;
                }
                if (size < 1) {
                    size = 1;
                }
                if (size > QueryByPage.PAGE_SIZE_MAX) {
                    size = QueryByPage.PAGE_SIZE_MAX;
                }
                IDialect dialect = DialectFactory.getDialect(dbType, null);
                sqlStr = dialect.buildPaginationSql(sqlStr, offset, size, paramMap);
            }
            return sqlStr;
        }

        /**
         * 获取 count sql
         */
        public String buildCountSql() {
            // 备份设置
            final Integer offset = this.offset;
            final Integer size = this.size;
            this.offset = null;
            this.size = null;
            try {
                String sql = buildSql();
                return SqlUtils.getCountSql(sql);
            } finally {
                // 还原设置
                this.offset = offset;
                this.size = size;
            }
        }

        public SelectBuilder setDbType(DbType dbType) {
            this.dbType = dbType;
            return this;
        }

        /**
         * 获取参数
         */
        public Map<String, Object> getParams() {
            return paramMap;
        }

        /**
         * 增加参数
         *
         * @param name  参数名
         * @param value 参数值
         */
        public SelectBuilder addParam(String name, Object value, boolean bool) {
            if (bool) {
                paramMap.put(name, value);
            }
            return this;
        }

        /**
         * 增加参数
         *
         * @param name  参数名
         * @param value 参数值
         */
        public SelectBuilder addParam(String name, Object value) {
            return addParam(name, name, true);
        }

        /**
         * 增加参数
         */
        public SelectBuilder addParams(Map<String, Object> params, boolean bool) {
            if (bool && params != null && !params.isEmpty()) {
                paramMap.putAll(params);
            }
            return this;
        }

        /**
         * 增加参数
         */
        public SelectBuilder addParams(Map<String, Object> params) {
            return addParams(params, true);
        }

        /**
         * 重新设置查询列
         */
        public SelectBuilder setSelect(String columns, boolean bool) {
            if (bool) {
                select.delete(0, select.length());
                select.append(columns);
                delLast(select, COMMA);
                select.append(COMMA);
            }
            return this;
        }

        /**
         * 重新设置查询列
         */
        public SelectBuilder setSelect(String columns) {
            return setSelect(columns, true);
        }

        /**
         * 新增查询列
         */
        public SelectBuilder addColumn(String column, boolean bool) {
            if (bool) {
                select.append(SPACE).append(column);
                delLast(select, COMMA);
                select.append(COMMA);
            }
            return this;
        }

        /**
         * 新增查询列
         */
        public SelectBuilder addColumn(String column) {
            return addColumn(column, true);
        }

        /**
         * 重新设置查询的表(包含关联语句: join...on)
         */
        public SelectBuilder setTable(String table, boolean bool) {
            if (bool) {
                from.delete(0, from.length());
                from.append(table);
                delLast(from, COMMA);
                from.append(COMMA);
            }
            return this;
        }

        /**
         * 重新设置查询的表(包含关联语句: join...on)
         */
        public SelectBuilder setTable(String table) {
            return setTable(table, true);
        }

        /**
         * 新增查询的表
         */
        public SelectBuilder addTable(String table, boolean bool) {
            if (bool) {
                from.append(SPACE).append(table);
                delLast(from, COMMA);
                from.append(COMMA);
            }
            return this;
        }

        /**
         * 新增查询的表
         */
        public SelectBuilder addTable(String table) {
            return addTable(table, true);
        }

        /**
         * 内关联查询
         *
         * @param joinTable 关联表
         * @param condition 关联条件: on (...)
         */
        public SelectBuilder innerJoin(String joinTable, String condition, boolean bool) {
            if (bool) {
                delLast(from, COMMA);
                from.append(SPACE).append("INNER JOIN").append(SPACE)
                        .append(joinTable).append(SPACE)
                        .append(condition);
                delLast(from, COMMA);
                from.append(COMMA);
            }
            return this;
        }

        /**
         * 内关联查询
         *
         * @param joinTable 关联表
         * @param condition 关联条件: on (...)
         */
        public SelectBuilder innerJoin(String joinTable, String condition) {
            return innerJoin(joinTable, condition, true);
        }

        /**
         * 左关联查询
         *
         * @param joinTable 关联表
         * @param condition 关联条件: on (...)
         */
        public SelectBuilder leftJoin(String joinTable, String condition, boolean bool) {
            if (bool) {
                delLast(from, COMMA);
                from.append(SPACE).append("LEFT JOIN").append(SPACE)
                        .append(joinTable).append(SPACE)
                        .append(condition);
                delLast(from, COMMA);
                from.append(COMMA);
            }
            return this;
        }

        /**
         * 左关联查询
         *
         * @param joinTable 关联表
         * @param condition 关联条件: on (...)
         */
        public SelectBuilder leftJoin(String joinTable, String condition) {
            return leftJoin(joinTable, condition, true);
        }

        /**
         * 右关联查询
         *
         * @param joinTable 关联表
         * @param condition 关联条件: on (...)
         */
        public SelectBuilder rightJoin(String joinTable, String condition, boolean bool) {
            if (bool) {
                delLast(from, COMMA);
                from.append(SPACE).append("RIGHT JOIN").append(SPACE)
                        .append(joinTable).append(SPACE)
                        .append(condition);
                delLast(from, COMMA);
                from.append(COMMA);
            }
            return this;
        }

        /**
         * 右关联查询
         *
         * @param joinTable 关联表
         * @param condition 关联条件: on (...)
         */
        public SelectBuilder rightJoin(String joinTable, String condition) {
            return rightJoin(joinTable, condition, true);
        }

        /**
         * 重新设置查询条件
         *
         * @param condition 查询条件
         * @param params    查询参数
         */
        public SelectBuilder setWhere(String condition, Map<String, Object> params, boolean bool) {
            if (bool) {
                if (params != null && !params.isEmpty()) {
                    paramMap.putAll(params);
                }
                where.delete(0, where.length());
                where.append(condition);
                delLast(where, WHERE_AND);
                where.append(SPACE).append(WHERE_AND);
            }
            return this;
        }

        /**
         * 重新设置查询条件
         *
         * @param condition 查询条件
         * @param params    查询参数
         */
        public SelectBuilder setWhere(String condition, Map<String, Object> params) {
            return setWhere(condition, params, true);
        }

        /**
         * 重新设置查询条件
         *
         * @param condition 查询条件
         */
        public SelectBuilder setWhere(String condition) {
            return setWhere(condition, null, true);
        }

        /**
         * 增加查询条件
         *
         * @param condition 查询条件
         * @param params    查询参数
         */
        public SelectBuilder addWhere(String condition, Map<String, Object> params, boolean bool) {
            if (bool) {
                if (params != null && !params.isEmpty()) {
                    paramMap.putAll(params);
                }
                where.append(SPACE).append(condition);
                delLast(where, WHERE_AND);
                where.append(SPACE).append(WHERE_AND);
            }
            return this;
        }

        /**
         * 增加查询条件
         *
         * @param condition 查询条件
         * @param params    查询参数
         */
        public SelectBuilder addWhere(String condition, Map<String, Object> params) {
            return addWhere(condition, params, true);
        }

        /**
         * 增加查询条件
         *
         * @param condition 查询条件
         */
        public SelectBuilder addWhere(String condition) {
            return addWhere(condition, null, true);
        }

        /**
         * 重新设置查询分组
         */
        public SelectBuilder setGroupBy(String groupBy, boolean bool) {
            if (bool) {
                this.groupBy.delete(0, this.groupBy.length());
                this.groupBy.append(groupBy);
                delLast(this.groupBy, COMMA);
                this.groupBy.append(COMMA);
            }
            return this;
        }

        /**
         * 重新设置查询分组
         */
        public SelectBuilder setGroupBy(String groupBy) {
            return setGroupBy(groupBy, true);
        }

        /**
         * 重新设置查询分组
         */
        public SelectBuilder addGroupBy(String groupBy, boolean bool) {
            if (bool) {
                this.groupBy.append(SPACE).append(groupBy);
                delLast(this.groupBy, COMMA);
                this.groupBy.append(COMMA);
            }
            return this;
        }

        /**
         * 重新设置查询分组
         */
        public SelectBuilder addGroupBy(String groupBy) {
            return addGroupBy(groupBy, true);
        }

        /**
         * 重新设置查询条件
         *
         * @param having 过滤分组条件
         * @param params 查询参数
         */
        public SelectBuilder setHaving(String having, Map<String, Object> params, boolean bool) {
            if (bool) {
                if (params != null && !params.isEmpty()) {
                    paramMap.putAll(params);
                }
                this.having.delete(0, this.having.length());
                this.having.append(having);
                delLast(this.having, WHERE_AND);
                this.having.append(SPACE).append(WHERE_AND);
            }
            return this;
        }

        /**
         * 重新设置查询条件
         *
         * @param having 过滤分组条件
         * @param params 查询参数
         */
        public SelectBuilder setHaving(String having, Map<String, Object> params) {
            return setHaving(having, params, true);
        }

        /**
         * 重新设置过滤分组条件
         *
         * @param having 过滤分组条件
         */
        public SelectBuilder setHaving(String having) {
            return setHaving(having, null, true);
        }

        /**
         * 增加过滤分组条件
         *
         * @param having 过滤分组条件
         * @param params 参数
         */
        public SelectBuilder addHaving(String having, Map<String, Object> params, boolean bool) {
            if (bool) {
                if (params != null && !params.isEmpty()) {
                    paramMap.putAll(params);
                }
                this.having.append(SPACE).append(having);
                delLast(this.having, WHERE_AND);
                this.having.append(SPACE).append(WHERE_AND);
            }
            return this;
        }

        /**
         * 增加过滤分组条件
         *
         * @param having 过滤分组条件
         * @param params 参数
         */
        public SelectBuilder addHaving(String having, Map<String, Object> params) {
            return addHaving(having, params, true);
        }

        /**
         * 增加过滤分组条件
         *
         * @param having 过滤分组条件
         */
        public SelectBuilder addHaving(String having) {
            return addHaving(having, null, true);
        }

        /**
         * 重新设置排序字段
         */
        public SelectBuilder setOrderBy(String orderBy, boolean bool) {
            if (bool) {
                this.orderBy.delete(0, this.orderBy.length());
                this.orderBy.append(orderBy);
                delLast(this.orderBy, COMMA);
                this.orderBy.append(COMMA);
            }
            return this;
        }

        /**
         * 重新设置排序字段
         */
        public SelectBuilder setOrderBy(String orderBy) {
            return setOrderBy(orderBy, true);
        }

        /**
         * 增加排序字段
         *
         * @param orderBy 排序字段
         * @param sort    排序规则 ASC DESC
         */
        public SelectBuilder addOrderBy(String orderBy, String sort, boolean bool) {
            if (bool) {
                this.orderBy.append(SPACE)
                        .append(orderBy).append(SPACE)
                        .append(sort);
                delLast(this.orderBy, COMMA);
                this.orderBy.append(COMMA);
            }
            return this;
        }

        /**
         * 增加排序字段
         *
         * @param orderBy 排序字段
         * @param sort    排序规则 ASC DESC
         */
        public SelectBuilder addOrderBy(String orderBy, String sort) {
            return addOrderBy(orderBy, sort, true);
        }

        /**
         * 增加排序字段
         *
         * @param orderBy 排序字段
         */
        public SelectBuilder addOrderBy(String orderBy) {
            return addOrderBy(orderBy, ASC, true);
        }

        /**
         * 增加排序字段
         *
         * @param orderBy 排序字段
         */
        public SelectBuilder addOrderByAsc(String orderBy, boolean bool) {
            return addOrderBy(orderBy, ASC, bool);
        }

        /**
         * 增加排序字段
         *
         * @param orderBy 排序字段
         */
        public SelectBuilder addOrderByAsc(String orderBy) {
            return addOrderBy(orderBy, ASC, true);
        }

        /**
         * 增加排序字段
         *
         * @param orderBy 排序字段
         */
        public SelectBuilder addOrderByDesc(String orderBy, boolean bool) {
            return addOrderBy(orderBy, DESC, bool);
        }

        /**
         * 增加排序字段
         *
         * @param orderBy 排序字段
         */
        public SelectBuilder addOrderByDesc(String orderBy) {
            return addOrderBy(orderBy, DESC, true);
        }

        /**
         * 设置查询的数据位置
         *
         * @param offset 数据偏移量
         * @param size   数据数量
         */
        public SelectBuilder setLimit(int offset, int size, boolean bool) {
            if (bool) {
                this.offset = offset;
                this.size = size;
            }
            return this;
        }

        /**
         * 设置查询的数据位置
         *
         * @param offset 数据偏移量
         * @param size   数据数量
         */
        public SelectBuilder setLimit(int offset, int size) {
            return setLimit(offset, size, true);
        }

        /**
         * 清空查询的数据位置
         */
        public SelectBuilder clearLimit(boolean bool) {
            if (bool) {
                this.offset = null;
                this.size = null;
            }
            return this;
        }

        /**
         * 清空查询的数据位置
         */
        public SelectBuilder clearLimit() {
            return clearLimit(true);
        }

        /**
         * 设置查询分页位置
         *
         * @param pageNo   数据页(>=1)
         * @param pageSize 页大小
         */
        public SelectBuilder setPagination(int pageNo, int pageSize, boolean bool) {
            if (bool) {
                if (pageNo < 1) {
                    pageNo = 1;
                }
                this.offset = (pageNo - 1) * pageSize;
                this.size = pageSize;
            }
            return this;
        }

        /**
         * 设置查询分页位置
         *
         * @param pageNo   数据页(>=1)
         * @param pageSize 页大小
         */
        public SelectBuilder setPagination(int pageNo, int pageSize) {
            return setPagination(pageNo, pageSize, true);
        }

        /**
         * 清空查询分页位置
         */
        public SelectBuilder clearPagination(boolean bool) {
            return clearLimit(bool);
        }

        /**
         * 清空查询分页位置
         */
        public SelectBuilder clearPagination() {
            return clearLimit(true);
        }

        /**
         * 设置查询的数据量
         *
         * @param top 查询前n条数据(>=1)
         */
        public SelectBuilder setTop(int top, boolean bool) {
            if (bool) {
                this.offset = 0;
                this.size = top;
            }
            return this;
        }

        /**
         * 设置查询的数据量
         *
         * @param top 查询前n条数据(>=1)
         */
        public SelectBuilder setTop(int top) {
            return setTop(top, true);
        }

        /**
         * 清空查询的数据量
         */
        public SelectBuilder clearTop(boolean bool) {
            return clearLimit(bool);
        }

        /**
         * 清空查询的数据量
         */
        public SelectBuilder clearTop() {
            return clearLimit(true);
        }
    }

    public static class UpdateBuilder {
        private UpdateBuilder() {
        }
    }

    public static class InsertBuilder {
        private InsertBuilder() {
        }
    }
}
