package org.clever.graaljs.data.jdbc.builtin.wrap;

import org.clever.graaljs.core.internal.utils.InteropScriptToJavaUtils;
import org.clever.graaljs.data.jdbc.support.mybatisplus.DbType;

import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/24 14:43 <br/>
 */
public class SQLBuilder {
    public static final SQLBuilder Instance = new SQLBuilder();

    private SQLBuilder() {
    }

    /**
     * 创建一个Select SQL构建器
     */
    public SelectBuilder newSelectBuilder() {
        return new SelectBuilder();
    }

    /**
     * 创建一个Update SQL构建器
     */
    public UpdateBuilder newUpdateBuilder() {
        return new UpdateBuilder();
    }

    /**
     * 创建一个Insert SQL构建器
     */
    public InsertBuilder newInsertBuilder() {
        return new InsertBuilder();
    }

    /**
     * 创建一个Delete SQL构建器
     */
    public DeleteBuilder newDeleteBuilder() {
        return new DeleteBuilder();
    }

    public static class SelectBuilder {
        private final org.clever.graaljs.data.jdbc.builtin.adapter.SQLBuilder.SelectBuilder delegate = org.clever.graaljs.data.jdbc.builtin.adapter.SQLBuilder.newSelectBuilder();

        private SelectBuilder() {
        }

        public String getDbType() {
            return delegate.getDbType().getDb();
        }

        /**
         * 获取 sql
         */
        public String buildSql() {
            return delegate.buildSql();
        }

        /**
         * 获取 count sql
         */
        public String buildCountSql() {
            return delegate.buildCountSql();
        }

        public SelectBuilder setDbType(String dbType) {
            delegate.setDbType(DbType.getDbType(dbType));
            return this;
        }

        /**
         * 获取参数
         */
        public Map<String, Object> getParams() {
            return delegate.getParams();
        }

        /**
         * 增加参数
         *
         * @param name  参数名
         * @param value 参数值
         */
        public SelectBuilder addParam(String name, Object value, boolean bool) {
            value = InteropScriptToJavaUtils.Instance.deepToJavaObject(value);
            delegate.addParam(name, value, bool);
            return this;
        }

        /**
         * 增加参数
         *
         * @param name  参数名
         * @param value 参数值
         */
        public SelectBuilder addParam(String name, Object value) {
            value = InteropScriptToJavaUtils.Instance.deepToJavaObject(value);
            delegate.addParam(name, value);
            return this;
        }

        /**
         * 增加参数
         */
        public SelectBuilder addParams(Map<String, Object> params, boolean bool) {
            params = InteropScriptToJavaUtils.Instance.convertMap(params);
            delegate.addParams(params, bool);
            return this;
        }

        /**
         * 增加参数
         */
        public SelectBuilder addParams(Map<String, Object> params) {
            params = InteropScriptToJavaUtils.Instance.convertMap(params);
            delegate.addParams(params);
            return this;
        }

        /**
         * 重新设置查询列
         */
        public SelectBuilder setSelect(String columns, boolean bool) {
            delegate.setSelect(columns, bool);
            return this;
        }

        /**
         * 重新设置查询列
         */
        public SelectBuilder setSelect(String columns) {
            delegate.setSelect(columns);
            return this;
        }

        /**
         * 新增查询列
         */
        public SelectBuilder addColumn(String column, boolean bool) {
            delegate.addColumn(column, bool);
            return this;
        }

        /**
         * 新增查询列
         */
        public SelectBuilder addColumn(String column) {
            delegate.addColumn(column);
            return this;
        }

        /**
         * 重新设置查询的表(包含关联语句: join...on)
         */
        public SelectBuilder setTable(String table, boolean bool) {
            delegate.setTable(table, bool);
            return this;
        }

        /**
         * 重新设置查询的表(包含关联语句: join...on)
         */
        public SelectBuilder setTable(String table) {
            delegate.setTable(table);
            return this;
        }

        /**
         * 新增查询的表
         */
        public SelectBuilder addTable(String table, boolean bool) {
            delegate.addTable(table, bool);
            return this;
        }

        /**
         * 新增查询的表
         */
        public SelectBuilder addTable(String table) {
            delegate.addTable(table);
            return this;
        }

        /**
         * 内关联查询
         *
         * @param joinTable 关联表
         * @param condition 关联条件: on (...)
         */
        public SelectBuilder innerJoin(String joinTable, String condition, boolean bool) {
            delegate.innerJoin(joinTable, condition, bool);
            return this;
        }

        /**
         * 内关联查询
         *
         * @param joinTable 关联表
         * @param condition 关联条件: on (...)
         */
        public SelectBuilder innerJoin(String joinTable, String condition) {
            delegate.innerJoin(joinTable, condition);
            return this;
        }

        /**
         * 左关联查询
         *
         * @param joinTable 关联表
         * @param condition 关联条件: on (...)
         */
        public SelectBuilder leftJoin(String joinTable, String condition, boolean bool) {
            delegate.leftJoin(joinTable, condition, bool);
            return this;
        }

        /**
         * 左关联查询
         *
         * @param joinTable 关联表
         * @param condition 关联条件: on (...)
         */
        public SelectBuilder leftJoin(String joinTable, String condition) {
            delegate.leftJoin(joinTable, condition);
            return this;
        }

        /**
         * 右关联查询
         *
         * @param joinTable 关联表
         * @param condition 关联条件: on (...)
         */
        public SelectBuilder rightJoin(String joinTable, String condition, boolean bool) {
            delegate.rightJoin(joinTable, condition, bool);
            return this;
        }

        /**
         * 右关联查询
         *
         * @param joinTable 关联表
         * @param condition 关联条件: on (...)
         */
        public SelectBuilder rightJoin(String joinTable, String condition) {
            delegate.rightJoin(joinTable, condition);
            return this;
        }

        /**
         * 重新设置查询条件
         *
         * @param condition 查询条件
         * @param params    查询参数
         */
        public SelectBuilder setWhere(String condition, Map<String, Object> params, boolean bool) {
            params = InteropScriptToJavaUtils.Instance.convertMap(params);
            delegate.setWhere(condition, params, bool);
            return this;
        }

        /**
         * 重新设置查询条件
         *
         * @param condition 查询条件
         * @param params    查询参数
         */
        public SelectBuilder setWhere(String condition, Map<String, Object> params) {
            params = InteropScriptToJavaUtils.Instance.convertMap(params);
            delegate.setWhere(condition, params);
            return this;
        }

        /**
         * 重新设置查询条件
         *
         * @param condition 查询条件
         */
        public SelectBuilder setWhere(String condition) {
            delegate.setWhere(condition);
            return this;
        }

        /**
         * 增加查询条件
         *
         * @param condition 查询条件
         * @param params    查询参数
         */
        public SelectBuilder addWhere(String condition, Map<String, Object> params, boolean bool) {
            params = InteropScriptToJavaUtils.Instance.convertMap(params);
            delegate.addWhere(condition, params, bool);
            return this;
        }

        /**
         * 增加查询条件
         *
         * @param condition 查询条件
         * @param params    查询参数
         */
        public SelectBuilder addWhere(String condition, Map<String, Object> params) {
            params = InteropScriptToJavaUtils.Instance.convertMap(params);
            delegate.addWhere(condition, params);
            return this;
        }

        /**
         * 增加查询条件
         *
         * @param condition 查询条件
         */
        public SelectBuilder addWhere(String condition) {
            delegate.addWhere(condition);
            return this;
        }

        /**
         * 重新设置查询分组
         */
        public SelectBuilder setGroupBy(String groupBy, boolean bool) {
            delegate.setGroupBy(groupBy, bool);
            return this;
        }

        /**
         * 重新设置查询分组
         */
        public SelectBuilder setGroupBy(String groupBy) {
            delegate.setGroupBy(groupBy);
            return this;
        }

        /**
         * 重新设置查询分组
         */
        public SelectBuilder addGroupBy(String groupBy, boolean bool) {
            delegate.addGroupBy(groupBy, bool);
            return this;
        }

        /**
         * 重新设置查询分组
         */
        public SelectBuilder addGroupBy(String groupBy) {
            delegate.addGroupBy(groupBy);
            return this;
        }

        /**
         * 重新设置查询条件
         *
         * @param having 过滤分组条件
         * @param params 查询参数
         */
        public SelectBuilder setHaving(String having, Map<String, Object> params, boolean bool) {
            params = InteropScriptToJavaUtils.Instance.convertMap(params);
            delegate.setHaving(having, params, bool);
            return this;
        }

        /**
         * 重新设置查询条件
         *
         * @param having 过滤分组条件
         * @param params 查询参数
         */
        public SelectBuilder setHaving(String having, Map<String, Object> params) {
            params = InteropScriptToJavaUtils.Instance.convertMap(params);
            delegate.setHaving(having, params);
            return this;
        }

        /**
         * 重新设置过滤分组条件
         *
         * @param having 过滤分组条件
         */
        public SelectBuilder setHaving(String having) {
            delegate.setHaving(having);
            return this;
        }

        /**
         * 增加过滤分组条件
         *
         * @param having 过滤分组条件
         * @param params 参数
         */
        public SelectBuilder addHaving(String having, Map<String, Object> params, boolean bool) {
            params = InteropScriptToJavaUtils.Instance.convertMap(params);
            delegate.addHaving(having, params, bool);
            return this;
        }

        /**
         * 增加过滤分组条件
         *
         * @param having 过滤分组条件
         * @param params 参数
         */
        public SelectBuilder addHaving(String having, Map<String, Object> params) {
            params = InteropScriptToJavaUtils.Instance.convertMap(params);
            delegate.addHaving(having, params);
            return this;
        }

        /**
         * 增加过滤分组条件
         *
         * @param having 过滤分组条件
         */
        public SelectBuilder addHaving(String having) {
            delegate.addHaving(having);
            return this;
        }

        /**
         * 重新设置排序字段
         */
        public SelectBuilder setOrderBy(String orderBy, boolean bool) {
            delegate.setOrderBy(orderBy, bool);
            return this;
        }

        /**
         * 重新设置排序字段
         */
        public SelectBuilder setOrderBy(String orderBy) {
            delegate.setOrderBy(orderBy);
            return this;
        }

        /**
         * 增加排序字段
         *
         * @param orderBy 排序字段
         * @param sort    排序规则 ASC DESC
         */
        public SelectBuilder addOrderBy(String orderBy, String sort, boolean bool) {
            delegate.addOrderBy(orderBy, sort, bool);
            return this;
        }

        /**
         * 增加排序字段
         *
         * @param orderBy 排序字段
         * @param sort    排序规则 ASC DESC
         */
        public SelectBuilder addOrderBy(String orderBy, String sort) {
            delegate.addOrderBy(orderBy, sort);
            return this;
        }

        /**
         * 增加排序字段
         *
         * @param orderBy 排序字段
         */
        public SelectBuilder addOrderBy(String orderBy) {
            delegate.addOrderBy(orderBy);
            return this;
        }

        /**
         * 增加排序字段
         *
         * @param orderBy 排序字段
         */
        public SelectBuilder addOrderByAsc(String orderBy, boolean bool) {
            delegate.addOrderByAsc(orderBy, bool);
            return this;
        }

        /**
         * 增加排序字段
         *
         * @param orderBy 排序字段
         */
        public SelectBuilder addOrderByAsc(String orderBy) {
            delegate.addOrderByAsc(orderBy);
            return this;
        }

        /**
         * 增加排序字段
         *
         * @param orderBy 排序字段
         */
        public SelectBuilder addOrderByDesc(String orderBy, boolean bool) {
            delegate.addOrderByDesc(orderBy, bool);
            return this;
        }

        /**
         * 增加排序字段
         *
         * @param orderBy 排序字段
         */
        public SelectBuilder addOrderByDesc(String orderBy) {
            delegate.addOrderByDesc(orderBy);
            return this;
        }

        /**
         * 设置查询的数据位置
         *
         * @param offset 数据偏移量
         * @param size   数据数量
         */
        public SelectBuilder setLimit(int offset, int size, boolean bool) {
            delegate.setLimit(offset, size, bool);
            return this;
        }

        /**
         * 设置查询的数据位置
         *
         * @param offset 数据偏移量
         * @param size   数据数量
         */
        public SelectBuilder setLimit(int offset, int size) {
            delegate.setLimit(offset, size);
            return this;
        }

        /**
         * 清空查询的数据位置
         */
        public SelectBuilder clearLimit(boolean bool) {
            delegate.clearLimit(bool);
            return this;
        }

        /**
         * 清空查询的数据位置
         */
        public SelectBuilder clearLimit() {
            delegate.clearLimit();
            return this;
        }

        /**
         * 设置查询分页位置
         *
         * @param pageNo   数据页(>=1)
         * @param pageSize 页大小
         */
        public SelectBuilder setPagination(int pageNo, int pageSize, boolean bool) {
            delegate.setPagination(pageNo, pageSize, bool);
            return this;
        }

        /**
         * 设置查询分页位置
         *
         * @param pageNo   数据页(>=1)
         * @param pageSize 页大小
         */
        public SelectBuilder setPagination(int pageNo, int pageSize) {
            delegate.setPagination(pageNo, pageSize);
            return this;
        }

        /**
         * 清空查询分页位置
         */
        public SelectBuilder clearPagination(boolean bool) {
            delegate.clearPagination(bool);
            return this;
        }

        /**
         * 清空查询分页位置
         */
        public SelectBuilder clearPagination() {
            delegate.clearPagination();
            return this;
        }

        /**
         * 设置查询的数据量
         *
         * @param top 查询前n条数据(>=1)
         */
        public SelectBuilder setTop(int top, boolean bool) {
            delegate.setTop(top, bool);
            return this;
        }

        /**
         * 设置查询的数据量
         *
         * @param top 查询前n条数据(>=1)
         */
        public SelectBuilder setTop(int top) {
            delegate.setTop(top);
            return this;
        }

        /**
         * 清空查询的数据量
         */
        public SelectBuilder clearTop(boolean bool) {
            delegate.clearTop(bool);
            return this;
        }

        /**
         * 清空查询的数据量
         */
        public SelectBuilder clearTop() {
            delegate.clearTop();
            return this;
        }
    }

    public static class UpdateBuilder {
        private final org.clever.graaljs.data.jdbc.builtin.adapter.SQLBuilder.UpdateBuilder delegate = org.clever.graaljs.data.jdbc.builtin.adapter.SQLBuilder.newUpdateBuilder();

        private UpdateBuilder() {
        }

        /**
         * 获取 sql
         */
        public String buildSql() {
            return delegate.buildSql();
        }

        /**
         * 获取参数
         */
        public Map<String, Object> getParams() {
            return delegate.getParams();
        }

        /**
         * 增加参数
         *
         * @param name  参数名
         * @param value 参数值
         */
        public UpdateBuilder addParam(String name, Object value, boolean bool) {
            value = InteropScriptToJavaUtils.Instance.deepToJavaObject(value);
            delegate.addParam(name, value, bool);
            return this;
        }

        /**
         * 增加参数
         *
         * @param name  参数名
         * @param value 参数值
         */
        public UpdateBuilder addParam(String name, Object value) {
            value = InteropScriptToJavaUtils.Instance.deepToJavaObject(value);
            delegate.addParam(name, value);
            return this;
        }

        /**
         * 增加参数
         */
        public UpdateBuilder addParams(Map<String, Object> params, boolean bool) {
            params = InteropScriptToJavaUtils.Instance.convertMap(params);
            delegate.addParams(params, bool);
            return this;
        }

        /**
         * 增加参数
         */
        public UpdateBuilder addParams(Map<String, Object> params) {
            params = InteropScriptToJavaUtils.Instance.convertMap(params);
            delegate.addParams(params);
            return this;
        }

        /**
         * 设置更新的表
         */
        public UpdateBuilder setTable(String table, boolean bool) {
            delegate.setTable(table, bool);
            return this;
        }

        /**
         * 设置更新的表
         */
        public UpdateBuilder setTable(String table) {
            delegate.setTable(table);
            return this;
        }

        /**
         * 重新设置更新的字段
         *
         * @param fields 需要更新的字段值 field=:fieldValue
         */
        public UpdateBuilder setFields(String fields, boolean bool) {
            delegate.setFields(fields, bool);
            return this;
        }

        /**
         * 重新设置更新的字段
         *
         * @param fields 需要更新的字段值 field=:fieldValue
         */
        public UpdateBuilder setFields(String fields) {
            delegate.setFields(fields);
            return this;
        }

        /**
         * 重新设置更新的字段以及字段值
         *
         * @param fields            需要更新的字段值
         * @param camelToUnderscore 是否使用驼峰转下划线
         */
        public UpdateBuilder setFieldsAndValues(Map<String, Object> fields, boolean camelToUnderscore, boolean bool) {
            fields = InteropScriptToJavaUtils.Instance.convertMap(fields);
            delegate.setFieldsAndValues(fields, camelToUnderscore, bool);
            return this;
        }

        /**
         * 重新设置更新的字段以及字段值
         *
         * @param fields            需要更新的字段值
         * @param camelToUnderscore 是否使用驼峰转下划线
         */
        public UpdateBuilder setFieldsAndValues(Map<String, Object> fields, boolean camelToUnderscore) {
            fields = InteropScriptToJavaUtils.Instance.convertMap(fields);
            delegate.setFieldsAndValues(fields, camelToUnderscore);
            return this;
        }

        /**
         * 重新设置更新的字段以及字段值
         *
         * @param fields 需要更新的字段值
         */
        public UpdateBuilder setFieldsAndValues(Map<String, Object> fields) {
            fields = InteropScriptToJavaUtils.Instance.convertMap(fields);
            delegate.setFieldsAndValues(fields);
            return this;
        }

        /**
         * 重新设置更新的字段
         *
         * @param field  字段 field=:fieldValue
         * @param params 参数
         */
        public UpdateBuilder addField(String field, Map<String, Object> params, boolean bool) {
            params = InteropScriptToJavaUtils.Instance.convertMap(params);
            delegate.addField(field, params, bool);
            return this;
        }

        /**
         * 重新设置更新的字段
         *
         * @param field  字段 field=:fieldValue
         * @param params 参数
         */
        public UpdateBuilder addField(String field, Map<String, Object> params) {
            params = InteropScriptToJavaUtils.Instance.convertMap(params);
            delegate.addField(field, params);
            return this;
        }

        /**
         * 重新设置更新的字段
         *
         * @param field 字段 field=:fieldValue
         */
        public UpdateBuilder addField(String field) {
            delegate.addField(field);
            return this;
        }

        /**
         * 重新设置更新的字段以及字段值
         *
         * @param fields            需要更新的字段值
         * @param camelToUnderscore 是否使用驼峰转下划线
         */
        public UpdateBuilder addFieldsAndValues(Map<String, Object> fields, boolean camelToUnderscore, boolean bool) {
            fields = InteropScriptToJavaUtils.Instance.convertMap(fields);
            delegate.addFieldsAndValues(fields, camelToUnderscore, bool);
            return this;
        }

        /**
         * 重新设置更新的字段以及字段值
         *
         * @param fields            需要更新的字段值
         * @param camelToUnderscore 是否使用驼峰转下划线
         */
        public UpdateBuilder addFieldsAndValues(Map<String, Object> fields, boolean camelToUnderscore) {
            fields = InteropScriptToJavaUtils.Instance.convertMap(fields);
            delegate.addFieldsAndValues(fields, camelToUnderscore);
            return this;
        }

        /**
         * 重新设置更新的字段以及字段值
         *
         * @param fields 需要更新的字段值
         */
        public UpdateBuilder addFieldsAndValues(Map<String, Object> fields) {
            fields = InteropScriptToJavaUtils.Instance.convertMap(fields);
            delegate.addFieldsAndValues(fields);
            return this;
        }

        /**
         * 重新设置更新条件
         *
         * @param condition 更新条件
         * @param params    更新参数
         */
        public UpdateBuilder setWhere(String condition, Map<String, Object> params, boolean bool) {
            params = InteropScriptToJavaUtils.Instance.convertMap(params);
            delegate.setWhere(condition, params, bool);
            return this;
        }

        /**
         * 重新设置更新条件
         *
         * @param condition 更新条件
         * @param params    更新参数
         */
        public UpdateBuilder setWhere(String condition, Map<String, Object> params) {
            params = InteropScriptToJavaUtils.Instance.convertMap(params);
            delegate.setWhere(condition, params);
            return this;
        }

        /**
         * 重新设置更新条件
         *
         * @param condition 更新条件
         */
        public UpdateBuilder setWhere(String condition) {
            delegate.setWhere(condition);
            return this;
        }

        /**
         * 增加更新条件
         *
         * @param condition 更新条件
         * @param params    更新参数
         */
        public UpdateBuilder addWhere(String condition, Map<String, Object> params, boolean bool) {
            params = InteropScriptToJavaUtils.Instance.convertMap(params);
            delegate.addWhere(condition, params, bool);
            return this;
        }

        /**
         * 增加更新条件
         *
         * @param condition 更新条件
         * @param params    更新参数
         */
        public UpdateBuilder addWhere(String condition, Map<String, Object> params) {
            params = InteropScriptToJavaUtils.Instance.convertMap(params);
            delegate.addWhere(condition, params);
            return this;
        }

        /**
         * 增加更新条件
         *
         * @param condition 更新条件
         */
        public UpdateBuilder addWhere(String condition) {
            delegate.addWhere(condition);
            return this;
        }
    }

    public static class InsertBuilder {
        private final org.clever.graaljs.data.jdbc.builtin.adapter.SQLBuilder.InsertBuilder delegate = org.clever.graaljs.data.jdbc.builtin.adapter.SQLBuilder.newInsertBuilder();

        private InsertBuilder() {
        }

        /**
         * 获取 sql
         */
        public String buildSql() {
            return delegate.buildSql();
        }

        /**
         * 获取参数
         */
        public Map<String, Object> getParams() {
            return delegate.getParams();
        }

        /**
         * 设置插入语句，默认是"INSERT INTO"，可改为"REPLACE INTO"
         */
        public InsertBuilder setInsertSql(String insertSql, boolean bool) {
            delegate.setInsertSql(insertSql, bool);
            return this;
        }

        /**
         * 设置插入语句
         */
        public InsertBuilder setInsertSql(String insertSql) {
            delegate.setInsertSql(insertSql);
            return this;
        }

        /**
         * 设置插入的表
         */
        public InsertBuilder setTable(String table, boolean bool) {
            delegate.setTable(table, bool);
            return this;
        }

        /**
         * 设置插入的表
         */
        public InsertBuilder setTable(String table) {
            delegate.setTable(table);
            return this;
        }

        /**
         * 重新设置插入的字段以及字段值
         *
         * @param fields            需要插入的字段值
         * @param camelToUnderscore 是否使用驼峰转下划线
         */
        public InsertBuilder setFieldsAndValues(Map<String, Object> fields, boolean camelToUnderscore, boolean bool) {
            fields = InteropScriptToJavaUtils.Instance.convertMap(fields);
            delegate.setFieldsAndValues(fields, camelToUnderscore, bool);
            return this;
        }

        /**
         * 重新设置插入的字段以及字段值
         *
         * @param fields            需要插入的字段值
         * @param camelToUnderscore 是否使用驼峰转下划线
         */
        public InsertBuilder setFieldsAndValues(Map<String, Object> fields, boolean camelToUnderscore) {
            fields = InteropScriptToJavaUtils.Instance.convertMap(fields);
            delegate.setFieldsAndValues(fields, camelToUnderscore);
            return this;
        }

        /**
         * 重新设置插入的字段以及字段值
         *
         * @param fields 需要插入的字段值
         */
        public InsertBuilder setFieldsAndValues(Map<String, Object> fields) {
            fields = InteropScriptToJavaUtils.Instance.convertMap(fields);
            delegate.setFieldsAndValues(fields);
            return this;
        }

        /**
         * 增加插入的字段以及字段值
         *
         * @param fields            需要插入的字段值
         * @param camelToUnderscore 是否使用驼峰转下划线
         */
        public InsertBuilder addFieldsAndValues(Map<String, Object> fields, boolean camelToUnderscore, boolean bool) {
            fields = InteropScriptToJavaUtils.Instance.convertMap(fields);
            delegate.addFieldsAndValues(fields, camelToUnderscore, bool);
            return this;
        }

        /**
         * 增加插入的字段以及字段值
         *
         * @param fields            需要插入的字段值
         * @param camelToUnderscore 是否使用驼峰转下划线
         */
        public InsertBuilder addFieldsAndValues(Map<String, Object> fields, boolean camelToUnderscore) {
            fields = InteropScriptToJavaUtils.Instance.convertMap(fields);
            delegate.addFieldsAndValues(fields, camelToUnderscore);
            return this;
        }

        /**
         * 增加插入的字段以及字段值
         *
         * @param fields 需要插入的字段值
         */
        public InsertBuilder addFieldsAndValues(Map<String, Object> fields) {
            fields = InteropScriptToJavaUtils.Instance.convertMap(fields);
            delegate.addFieldsAndValues(fields);
            return this;
        }

        /**
         * 增加插入的字段以及字段值
         *
         * @param name  字段名
         * @param value 字段值
         */
        public InsertBuilder addFieldAndValue(String name, Object value, boolean camelToUnderscore, boolean bool) {
            value = InteropScriptToJavaUtils.Instance.deepToJavaObject(value);
            delegate.addFieldAndValue(name, value, camelToUnderscore, bool);
            return this;
        }

        /**
         * 增加插入的字段以及字段值
         *
         * @param name  字段名
         * @param value 字段值
         */
        public InsertBuilder addFieldAndValue(String name, Object value, boolean camelToUnderscore) {
            value = InteropScriptToJavaUtils.Instance.deepToJavaObject(value);
            delegate.addFieldAndValue(name, value, camelToUnderscore);
            return this;
        }

        /**
         * 增加插入的字段以及字段值
         *
         * @param name  字段名
         * @param value 字段值
         */
        public InsertBuilder addFieldAndValue(String name, Object value) {
            value = InteropScriptToJavaUtils.Instance.deepToJavaObject(value);
            delegate.addFieldAndValue(name, value);
            return this;
        }
    }

    public static class DeleteBuilder {
        private final org.clever.graaljs.data.jdbc.builtin.adapter.SQLBuilder.DeleteBuilder delegate = org.clever.graaljs.data.jdbc.builtin.adapter.SQLBuilder.newDeleteBuilder();

        private DeleteBuilder() {
        }

        /**
         * 获取 sql
         */
        public String buildSql() {
            return delegate.buildSql();
        }

        /**
         * 获取参数
         */
        public Map<String, Object> getParams() {
            return delegate.getParams();
        }

        /**
         * 增加参数
         *
         * @param name  参数名
         * @param value 参数值
         */
        public DeleteBuilder addParam(String name, Object value, boolean bool) {
            value = InteropScriptToJavaUtils.Instance.deepToJavaObject(value);
            delegate.addParam(name, value, bool);
            return this;
        }

        /**
         * 增加参数
         *
         * @param name  参数名
         * @param value 参数值
         */
        public DeleteBuilder addParam(String name, Object value) {
            value = InteropScriptToJavaUtils.Instance.deepToJavaObject(value);
            delegate.addParam(name, value);
            return this;
        }

        /**
         * 增加参数
         */
        public DeleteBuilder addParams(Map<String, Object> params, boolean bool) {
            params = InteropScriptToJavaUtils.Instance.convertMap(params);
            delegate.addParams(params, bool);
            return this;
        }

        /**
         * 增加参数
         */
        public DeleteBuilder addParams(Map<String, Object> params) {
            params = InteropScriptToJavaUtils.Instance.convertMap(params);
            delegate.addParams(params);
            return this;
        }

        /**
         * 设置删除的表
         */
        public DeleteBuilder setTable(String table, boolean bool) {
            delegate.setTable(table, bool);
            return this;
        }

        /**
         * 设置删除的表
         */
        public DeleteBuilder setTable(String table) {
            delegate.setTable(table);
            return this;
        }

        /**
         * 重新设置删除条件
         *
         * @param condition 删除条件
         * @param params    删除参数
         */
        public DeleteBuilder setWhere(String condition, Map<String, Object> params, boolean bool) {
            params = InteropScriptToJavaUtils.Instance.convertMap(params);
            delegate.setWhere(condition, params, bool);
            return this;
        }

        /**
         * 重新设置删除条件
         *
         * @param condition 删除条件
         * @param params    删除参数
         */
        public DeleteBuilder setWhere(String condition, Map<String, Object> params) {
            params = InteropScriptToJavaUtils.Instance.convertMap(params);
            delegate.setWhere(condition, params);
            return this;
        }

        /**
         * 重新设置删除条件
         *
         * @param condition 删除条件
         */
        public DeleteBuilder setWhere(String condition) {
            delegate.setWhere(condition);
            return this;
        }

        /**
         * 增加删除条件
         *
         * @param condition 删除条件
         * @param params    删除参数
         */
        public DeleteBuilder addWhere(String condition, Map<String, Object> params, boolean bool) {
            params = InteropScriptToJavaUtils.Instance.convertMap(params);
            delegate.addWhere(condition, params, bool);
            return this;
        }

        /**
         * 增加删除条件
         *
         * @param condition 删除条件
         * @param params    删除参数
         */
        public DeleteBuilder addWhere(String condition, Map<String, Object> params) {
            params = InteropScriptToJavaUtils.Instance.convertMap(params);
            delegate.addWhere(condition, params);
            return this;
        }

        /**
         * 增加删除条件
         *
         * @param condition 删除条件
         */
        public DeleteBuilder addWhere(String condition) {
            delegate.addWhere(condition);
            return this;
        }
    }
}
