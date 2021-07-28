/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/24 14:43 <br/>
 */
interface SQLBuilder extends JObject {
    /**
     * 创建一个Select SQL构建器
     */
    newSelectBuilder(): SelectBuilder;
    /**
     * 创建一个Update SQL构建器
     */
    newUpdateBuilder(): UpdateBuilder;
    /**
     * 创建一个Insert SQL构建器
     */
    newInsertBuilder(): InsertBuilder;
    /**
     * 创建一个Delete SQL构建器
     */
    newDeleteBuilder(): DeleteBuilder;
}

/**
 * Select SQL构建器
 */
interface SelectBuilder extends JObject {
    getDbType(): JString;
    /**
     * 获取 sql
     */
    buildSql(): JString;
    /**
     * 获取 count sql
     */
    buildCountSql(): JString;
    setDbType(dbType: JString): SelectBuilder;
    /**
     * 获取参数
     */
    getParams(): SqlParamMap;
    /**
     * 增加参数
     *
     * @param name  参数名
     * @param value 参数值
     */
    addParam(name: JString, value: any, bool: JBoolean): SelectBuilder;
    /**
     * 增加参数
     *
     * @param name  参数名
     * @param value 参数值
     */
    addParam(name: JString, value: any): SelectBuilder;
    /**
     * 增加参数
     */
    addParams(params: SqlParamMap, bool: JBoolean): SelectBuilder;
    /**
     * 增加参数
     */
    addParams(params: SqlParamMap): SelectBuilder;
    /**
     * 重新设置查询列
     */
    setSelect(columns: JString, bool: JBoolean): SelectBuilder;
    /**
     * 重新设置查询列
     */
    setSelect(columns: JString): SelectBuilder;
    /**
     * 新增查询列
     */
    addColumn(column: JString, bool: JBoolean): SelectBuilder;
    /**
     * 新增查询列
     */
    addColumn(column: JString): SelectBuilder;
    /**
     * 重新设置查询的表(包含关联语句: join...on)
     */
    setTable(table: JString, bool: JBoolean): SelectBuilder;
    /**
     * 重新设置查询的表(包含关联语句: join...on)
     */
    setTable(table: JString): SelectBuilder;
    /**
     * 新增查询的表
     */
    addTable(table: JString, bool: JBoolean): SelectBuilder;
    /**
     * 新增查询的表
     */
    addTable(table: JString): SelectBuilder;
    /**
     * 内关联查询
     *
     * @param joinTable 关联表
     * @param condition 关联条件: on (...)
     */
    innerJoin(joinTable: JString, condition: JString, bool: JBoolean): SelectBuilder;
    /**
     * 内关联查询
     *
     * @param joinTable 关联表
     * @param condition 关联条件: on (...)
     */
    innerJoin(joinTable: JString, condition: JString): SelectBuilder;
    /**
     * 左关联查询
     *
     * @param joinTable 关联表
     * @param condition 关联条件: on (...)
     */
    leftJoin(joinTable: JString, condition: JString, bool: JBoolean): SelectBuilder;
    /**
     * 左关联查询
     *
     * @param joinTable 关联表
     * @param condition 关联条件: on (...)
     */
    leftJoin(joinTable: JString, condition: JString): SelectBuilder;
    /**
     * 右关联查询
     *
     * @param joinTable 关联表
     * @param condition 关联条件: on (...)
     */
    rightJoin(joinTable: JString, condition: JString, bool: JBoolean): SelectBuilder;
    /**
     * 右关联查询
     *
     * @param joinTable 关联表
     * @param condition 关联条件: on (...)
     */
    rightJoin(joinTable: JString, condition: JString): SelectBuilder;
    /**
     * 重新设置查询条件
     *
     * @param condition 查询条件
     * @param params    查询参数
     */
    setWhere(condition: JString, params: SqlParamMap, bool: JBoolean): SelectBuilder;
    /**
     * 重新设置查询条件
     *
     * @param condition 查询条件
     * @param params    查询参数
     */
    setWhere(condition: JString, params: SqlParamMap): SelectBuilder;
    /**
     * 重新设置查询条件
     *
     * @param condition 查询条件
     */
    setWhere(condition: JString): SelectBuilder;
    /**
     * 增加查询条件
     *
     * @param condition 查询条件
     * @param params    查询参数
     */
    addWhere(condition: JString, params: SqlParamMap, bool: JBoolean): SelectBuilder;
    /**
     * 增加查询条件
     *
     * @param condition 查询条件
     * @param params    查询参数
     */
    addWhere(condition: JString, params: SqlParamMap): SelectBuilder;
    /**
     * 增加查询条件
     *
     * @param condition 查询条件
     */
    addWhere(condition: JString): SelectBuilder;
    /**
     * 重新设置查询分组
     */
    setGroupBy(groupBy: JString, bool: JBoolean): SelectBuilder;
    /**
     * 重新设置查询分组
     */
    setGroupBy(groupBy: JString): SelectBuilder;
    /**
     * 重新设置查询分组
     */
    addGroupBy(groupBy: JString, bool: JBoolean): SelectBuilder;
    /**
     * 重新设置查询分组
     */
    addGroupBy(groupBy: JString): SelectBuilder;
    /**
     * 重新设置查询条件
     *
     * @param having 过滤分组条件
     * @param params 查询参数
     */
    setHaving(having: JString, params: SqlParamMap, bool: JBoolean): SelectBuilder;
    /**
     * 重新设置查询条件
     *
     * @param having 过滤分组条件
     * @param params 查询参数
     */
    setHaving(having: JString, params: SqlParamMap): SelectBuilder;
    /**
     * 重新设置过滤分组条件
     *
     * @param having 过滤分组条件
     */
    setHaving(having: JString): SelectBuilder;
    /**
     * 增加过滤分组条件
     *
     * @param having 过滤分组条件
     * @param params 参数
     */
    addHaving(having: JString, params: SqlParamMap, bool: JBoolean): SelectBuilder;
    /**
     * 增加过滤分组条件
     *
     * @param having 过滤分组条件
     * @param params 参数
     */
    addHaving(having: JString, params: SqlParamMap): SelectBuilder;
    /**
     * 增加过滤分组条件
     *
     * @param having 过滤分组条件
     */
    addHaving(having: JString): SelectBuilder;
    /**
     * 重新设置排序字段
     */
    setOrderBy(orderBy: JString, bool: JBoolean): SelectBuilder;
    /**
     * 重新设置排序字段
     */
    setOrderBy(orderBy: JString): SelectBuilder;
    /**
     * 增加排序字段
     *
     * @param orderBy 排序字段
     * @param sort    排序规则 ASC DESC
     */
    addOrderBy(orderBy: JString, sort: JString, bool: JBoolean): SelectBuilder;
    /**
     * 增加排序字段
     *
     * @param orderBy 排序字段
     * @param sort    排序规则 ASC DESC
     */
    addOrderBy(orderBy: JString, sort: JString): SelectBuilder;
    /**
     * 增加排序字段
     *
     * @param orderBy 排序字段
     */
    addOrderBy(orderBy: JString): SelectBuilder;
    /**
     * 增加排序字段
     *
     * @param orderBy 排序字段
     */
    addOrderByAsc(orderBy: JString, bool: JBoolean): SelectBuilder;
    /**
     * 增加排序字段
     *
     * @param orderBy 排序字段
     */
    addOrderByAsc(orderBy: JString): SelectBuilder;
    /**
     * 增加排序字段
     *
     * @param orderBy 排序字段
     */
    addOrderByDesc(orderBy: JString, bool: JBoolean): SelectBuilder;
    /**
     * 增加排序字段
     *
     * @param orderBy 排序字段
     */
    addOrderByDesc(orderBy: JString): SelectBuilder;
    /**
     * 设置查询的数据位置
     *
     * @param offset 数据偏移量
     * @param size   数据数量
     */
    setLimit(offset: JInt, size: JInt, bool: JBoolean): SelectBuilder;
    /**
     * 设置查询的数据位置
     *
     * @param offset 数据偏移量
     * @param size   数据数量
     */
    setLimit(offset: JInt, size: JInt): SelectBuilder;
    /**
     * 清空查询的数据位置
     */
    clearLimit(bool: JBoolean): SelectBuilder;
    /**
     * 清空查询的数据位置
     */
    clearLimit(): SelectBuilder;
    /**
     * 设置查询分页位置
     *
     * @param pageNo   数据页(>=1)
     * @param pageSize 页大小
     */
    setPagination(pageNo: JInt, pageSize: JInt, bool: JBoolean): SelectBuilder;
    /**
     * 设置查询分页位置
     *
     * @param pageNo   数据页(>=1)
     * @param pageSize 页大小
     */
    setPagination(pageNo: JInt, pageSize: JInt): SelectBuilder;
    /**
     * 清空查询分页位置
     */
    clearPagination(bool: JBoolean): SelectBuilder;
    /**
     * 清空查询分页位置
     */
    clearPagination(): SelectBuilder;
    /**
     * 设置查询的数据量
     *
     * @param top 查询前n条数据(>=1)
     */
    setTop(top: JInt, bool: JBoolean): SelectBuilder;
    /**
     * 设置查询的数据量
     *
     * @param top 查询前n条数据(>=1)
     */
    setTop(top: JInt): SelectBuilder;
    /**
     * 清空查询的数据量
     */
    clearTop(bool: JBoolean): SelectBuilder;
    /**
     * 清空查询的数据量
     */
    clearTop(): SelectBuilder;
}

/**
 * Update SQL构建器
 */
interface UpdateBuilder extends JObject {
    /**
     * 获取 sql
     */
    buildSql(): JString;
    /**
     * 获取参数
     */
    getParams(): SqlParamMap;
    /**
     * 增加参数
     *
     * @param name  参数名
     * @param value 参数值
     */
    addParam(name: JString, value: any, bool: JBoolean): UpdateBuilder;
    /**
     * 增加参数
     *
     * @param name  参数名
     * @param value 参数值
     */
    addParam(name: JString, value: any): UpdateBuilder;
    /**
     * 增加参数
     */
    addParams(params: SqlParamMap, bool: JBoolean): UpdateBuilder;
    /**
     * 增加参数
     */
    addParams(params: SqlParamMap): UpdateBuilder;
    /**
     * 设置更新的表
     */
    setTable(table: JString, bool: JBoolean): UpdateBuilder;
    /**
     * 设置更新的表
     */
    setTable(table: JString): UpdateBuilder;
    /**
     * 重新设置更新的字段
     *
     * @param fields 需要更新的字段值 field=:fieldValue
     */
    setFields(fields: JString, bool: JBoolean): UpdateBuilder;
    /**
     * 重新设置更新的字段
     *
     * @param fields 需要更新的字段值 field=:fieldValue
     */
    setFields(fields: JString): UpdateBuilder;
    /**
     * 重新设置更新的字段以及字段值
     *
     * @param fields            需要更新的字段值
     * @param camelToUnderscore 是否使用驼峰转下划线
     */
    setFieldsAndValues(fields: SqlParamMap, camelToUnderscore: JBoolean, bool: JBoolean): UpdateBuilder;
    /**
     * 重新设置更新的字段以及字段值
     *
     * @param fields            需要更新的字段值
     * @param camelToUnderscore 是否使用驼峰转下划线
     */
    setFieldsAndValues(fields: SqlParamMap, camelToUnderscore: JBoolean): UpdateBuilder;
    /**
     * 重新设置更新的字段以及字段值
     *
     * @param fields 需要更新的字段值
     */
    setFieldsAndValues(fields: SqlParamMap): UpdateBuilder;
    /**
     * 重新设置更新的字段
     *
     * @param field  字段 field=:fieldValue
     * @param params 参数
     */
    addField(field: JString, params: SqlParamMap, bool: JBoolean): UpdateBuilder;
    /**
     * 重新设置更新的字段
     *
     * @param field  字段 field=:fieldValue
     * @param params 参数
     */
    addField(field: JString, params: SqlParamMap): UpdateBuilder;
    /**
     * 重新设置更新的字段
     *
     * @param field 字段 field=:fieldValue
     */
    addField(field: JString): UpdateBuilder;
    /**
     * 重新设置更新的字段以及字段值
     *
     * @param fields            需要更新的字段值
     * @param camelToUnderscore 是否使用驼峰转下划线
     */
    addFieldsAndValues(fields: SqlParamMap, camelToUnderscore: JBoolean, bool: JBoolean): UpdateBuilder;
    /**
     * 重新设置更新的字段以及字段值
     *
     * @param fields            需要更新的字段值
     * @param camelToUnderscore 是否使用驼峰转下划线
     */
    addFieldsAndValues(fields: SqlParamMap, camelToUnderscore: JBoolean): UpdateBuilder;
    /**
     * 重新设置更新的字段以及字段值
     *
     * @param fields 需要更新的字段值
     */
    addFieldsAndValues(fields: SqlParamMap): UpdateBuilder;
    /**
     * 重新设置更新条件
     *
     * @param condition 更新条件
     * @param params    更新参数
     */
    setWhere(condition: JString, params: SqlParamMap, bool: JBoolean): UpdateBuilder;
    /**
     * 重新设置更新条件
     *
     * @param condition 更新条件
     * @param params    更新参数
     */
    setWhere(condition: JString, params: SqlParamMap): UpdateBuilder;
    /**
     * 重新设置更新条件
     *
     * @param condition 更新条件
     */
    setWhere(condition: JString): UpdateBuilder;
    /**
     * 增加更新条件
     *
     * @param condition 更新条件
     * @param params    更新参数
     */
    addWhere(condition: JString, params: SqlParamMap, bool: JBoolean): UpdateBuilder;
    /**
     * 增加更新条件
     *
     * @param condition 更新条件
     * @param params    更新参数
     */
    addWhere(condition: JString, params: SqlParamMap): UpdateBuilder;
    /**
     * 增加更新条件
     *
     * @param condition 更新条件
     */
    addWhere(condition: JString): UpdateBuilder;
}

/**
 * Insert SQL构建器
 */
interface InsertBuilder extends JObject {
    /**
     * 获取 sql
     */
    buildSql(): JString;
    /**
     * 获取参数
     */
    getParams(): SqlParamMap;
    /**
     * 设置插入语句，默认是"INSERT INTO"，可改为"REPLACE INTO"
     */
    setInsertSql(insertSql: JString, bool: JBoolean): InsertBuilder;
    /**
     * 设置插入语句
     */
    setInsertSql(insertSql: JString): InsertBuilder;
    /**
     * 设置插入的表
     */
    setTable(table: JString, bool: JBoolean): InsertBuilder;
    /**
     * 设置插入的表
     */
    setTable(table: JString): InsertBuilder;
    /**
     * 重新设置插入的字段以及字段值
     *
     * @param fields            需要插入的字段值
     * @param camelToUnderscore 是否使用驼峰转下划线
     */
    setFieldsAndValues(fields: SqlParamMap, camelToUnderscore: JBoolean, bool: JBoolean): InsertBuilder;
    /**
     * 重新设置插入的字段以及字段值
     *
     * @param fields            需要插入的字段值
     * @param camelToUnderscore 是否使用驼峰转下划线
     */
    setFieldsAndValues(fields: SqlParamMap, camelToUnderscore: JBoolean): InsertBuilder;
    /**
     * 重新设置插入的字段以及字段值
     *
     * @param fields 需要插入的字段值
     */
    setFieldsAndValues(fields: SqlParamMap): InsertBuilder;
    /**
     * 增加插入的字段以及字段值
     *
     * @param fields            需要插入的字段值
     * @param camelToUnderscore 是否使用驼峰转下划线
     */
    addFieldsAndValues(fields: SqlParamMap, camelToUnderscore: JBoolean, bool: JBoolean): InsertBuilder;
    /**
     * 增加插入的字段以及字段值
     *
     * @param fields            需要插入的字段值
     * @param camelToUnderscore 是否使用驼峰转下划线
     */
    addFieldsAndValues(fields: SqlParamMap, camelToUnderscore: JBoolean): InsertBuilder;
    /**
     * 增加插入的字段以及字段值
     *
     * @param fields 需要插入的字段值
     */
    addFieldsAndValues(fields: SqlParamMap): InsertBuilder;
    /**
     * 增加插入的字段以及字段值
     *
     * @param name  字段名
     * @param value 字段值
     */
    addFieldAndValue(name: JString, value: any, camelToUnderscore: JBoolean, bool: JBoolean): InsertBuilder;
    /**
     * 增加插入的字段以及字段值
     *
     * @param name  字段名
     * @param value 字段值
     */
    addFieldAndValue(name: JString, value: any, camelToUnderscore: JBoolean): InsertBuilder;
    /**
     * 增加插入的字段以及字段值
     *
     * @param name  字段名
     * @param value 字段值
     */
    addFieldAndValue(name: JString, value: any): InsertBuilder;
}

/**
 * Delete SQL构建器
 */
interface DeleteBuilder extends JObject {
    /**
     * 获取 sql
     */
    buildSql(): JString;
    /**
     * 获取参数
     */
    getParams(): SqlParamMap;
    /**
     * 增加参数
     *
     * @param name  参数名
     * @param value 参数值
     */
    addParam(name: JString, value: any, bool: JBoolean): DeleteBuilder;
    /**
     * 增加参数
     *
     * @param name  参数名
     * @param value 参数值
     */
    addParam(name: JString, value: any): DeleteBuilder;
    /**
     * 增加参数
     */
    addParams(params: SqlParamMap, bool: JBoolean): DeleteBuilder;
    /**
     * 增加参数
     */
    addParams(params: SqlParamMap): DeleteBuilder;
    /**
     * 设置删除的表
     */
    setTable(table: JString, bool: JBoolean): DeleteBuilder;
    /**
     * 设置删除的表
     */
    setTable(table: JString): DeleteBuilder;
    /**
     * 重新设置删除条件
     *
     * @param condition 删除条件
     * @param params    删除参数
     */
    setWhere(condition: JString, params: SqlParamMap, bool: JBoolean): DeleteBuilder;
    /**
     * 重新设置删除条件
     *
     * @param condition 删除条件
     * @param params    删除参数
     */
    setWhere(condition: JString, params: SqlParamMap): DeleteBuilder;
    /**
     * 重新设置删除条件
     *
     * @param condition 删除条件
     */
    setWhere(condition: JString): DeleteBuilder;
    /**
     * 增加删除条件
     *
     * @param condition 删除条件
     * @param params    删除参数
     */
    addWhere(condition: JString, params: SqlParamMap, bool: JBoolean): DeleteBuilder;
    /**
     * 增加删除条件
     *
     * @param condition 删除条件
     * @param params    删除参数
     */
    addWhere(condition: JString, params: SqlParamMap): DeleteBuilder;
    /**
     * 增加删除条件
     *
     * @param condition 删除条件
     */
    addWhere(condition: JString): DeleteBuilder;
}

/**
 * SQL语句动态构造器
 */
declare const SQLBuilder: SQLBuilder;
