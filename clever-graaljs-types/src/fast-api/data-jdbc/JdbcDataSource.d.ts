/**
 * JDBC数据库操作对象
 */
interface JdbcDataSource extends JObject {
    /**
     * 获取数据库类型
     */
    getDbType(): DbType;

    /**
     * 当前数据源是否关闭
     */
    isClosed(): JBoolean;

    /**
     * 关闭当前数据源
     */
    close(): void;

    // --------------------------------------------------------------------------------------------
    // Query 操作
    // --------------------------------------------------------------------------------------------

    /**
     * 查询一条数据，返回一个Map
     *
     * @param sql              sql脚本，参数格式[:param]
     * @param paramMap         参数(可选)，参数格式[:param]
     * @param underlineToCamel 下划线转驼峰
     */
    queryMap<T = DataRowMap>(sql: JString, paramMap: SqlParamMap, underlineToCamel: JBoolean): T;

    /**
     * 查询一条数据，返回一个Map
     *
     * @param sql      sql脚本，参数格式[:param]
     * @param paramMap 参数(可选)，参数格式[:param]
     */
    queryMap<T = DataRowMap>(sql: JString, paramMap: SqlParamMap): T;

    /**
     * 查询一条数据，返回一个Map
     *
     * @param sql              sql脚本，参数格式[:param]
     * @param underlineToCamel 下划线转驼峰
     */
    queryMap<T = DataRowMap>(sql: JString, underlineToCamel: JBoolean): T;

    /**
     * 查询一条数据，返回一个Map
     *
     * @param sql sql脚本，参数格式[:param]
     */
    queryMap<T = DataRowMap>(sql: JString): T;

    /**
     * 查询多条数据，返回一个Map数组
     *
     * @param sql              sql脚本，参数格式[:param]
     * @param paramMap         参数(可选)，参数格式[:param]
     * @param underlineToCamel 下划线转驼峰
     */
    queryList<T = DataRowMap>(sql: JString, paramMap: SqlParamMap, underlineToCamel: JBoolean): JList<T>;

    /**
     * 查询多条数据，返回一个Map数组
     *
     * @param sql      sql脚本，参数格式[:param]
     * @param paramMap 参数(可选)，参数格式[:param]
     */
    queryList<T = DataRowMap>(sql: JString, paramMap: SqlParamMap): JList<T>;

    /**
     * 查询多条数据，返回一个Map数组
     *
     * @param sql sql脚本，参数格式[:param]
     * @param underlineToCamel 下划线转驼峰
     */
    queryList<T = DataRowMap>(sql: JString, underlineToCamel: JBoolean): JList<T>;

    /**
     * 查询多条数据，返回一个Map数组
     *
     * @param sql sql脚本，参数格式[:param]
     */
    queryList<T = DataRowMap>(sql: JString): JList<T>;

    /**
     * 查询返回一个 String
     *
     * @param sql      sql脚本，参数格式[:param]
     * @param paramMap 参数(可选)，参数格式[:param]
     */
    queryString(sql: JString, paramMap: SqlParamMap): JString;

    /**
     * 查询返回一个 String
     *
     * @param sql sql脚本，参数格式[:param]
     */
    queryString(sql: JString): JString;

    /**
     * 查询返回一个 Long
     *
     * @param sql      sql脚本，参数格式[:param]
     * @param paramMap 参数(可选)，参数格式[:param]
     */
    queryLong(sql: JString, paramMap: SqlParamMap): JLong;

    /**
     * 查询返回一个 Long
     *
     * @param sql sql脚本，参数格式[:param]
     */
    queryLong(sql: JString): JLong;

    /**
     * 查询返回一个 Double
     *
     * @param sql      sql脚本，参数格式[:param]
     * @param paramMap 参数(可选)，参数格式[:param]
     */
    queryDouble(sql: JString, paramMap: SqlParamMap): JDouble;

    /**
     * 查询返回一个 Double
     *
     * @param sql sql脚本，参数格式[:param]
     */
    queryDouble(sql: JString): JDouble;

    /**
     * 查询返回一个 BigDecimal
     *
     * @param sql      sql脚本，参数格式[:param]
     * @param paramMap 参数(可选)，参数格式[:param]
     */
    queryBigDecimal(sql: JString, paramMap: SqlParamMap): JBigDecimal;

    /**
     * 查询返回一个 BigDecimal
     *
     * @param sql sql脚本，参数格式[:param]
     */
    queryBigDecimal(sql: JString): JBigDecimal

    /**
     * 查询返回一个 Boolean
     *
     * @param sql      sql脚本，参数格式[:param]
     * @param paramMap 参数(可选)，参数格式[:param]
     */
    queryBoolean(sql: JString, paramMap: SqlParamMap): JBoolean;

    /**
     * 查询返回一个 Boolean
     *
     * @param sql sql脚本，参数格式[:param]
     */
    queryBoolean(sql: JString): JBoolean;

    /**
     * 查询返回一个 Date
     *
     * @param sql      sql脚本，参数格式[:param]
     * @param paramMap 参数(可选)，参数格式[:param]
     */
    queryDate(sql: JString, paramMap: SqlParamMap): JDate;

    /**
     * 查询返回一个 Date
     *
     * @param sql sql脚本，参数格式[:param]
     */
    queryDate(sql: JString): JDate;

    /**
     * SQL Count(获取一个SQL返回的数据总量)
     *
     * @param sql      sql脚本，参数格式[:param]
     * @param paramMap 参数(可选)，参数格式[:param]
     */
    queryCount(sql: JString, paramMap: SqlParamMap): JLong;

    /**
     * SQL Count(获取一个SQL返回的数据总量)
     *
     * @param sql      sql脚本，参数格式[:param]
     */
    queryCount(sql: JString): JLong;

    /**
     * 查询多条数据(大量数据)，使用游标读取
     *
     * @param sql              sql脚本，参数格式[:param]
     * @param paramMap         参数(可选)，参数格式[:param]
     * @param batchSize        一个批次的数据量
     * @param consumer         游标批次读取数据消费者
     * @param underlineToCamel 下划线转驼峰
     */
    query<T = DataRowMap>(sql: JString, paramMap: SqlParamMap, batchSize: JInt, consumer: BatchQueryCallback<T>, underlineToCamel: JBoolean): void;

    /**
     * 查询多条数据(大量数据)，使用游标读取
     *
     * @param sql       sql脚本，参数格式[:param]
     * @param paramMap  参数(可选)，参数格式[:param]
     * @param batchSize 一个批次的数据量
     * @param consumer  游标批次读取数据消费者
     */
    query<T = DataRowMap>(sql: JString, paramMap: SqlParamMap, batchSize: JInt, consumer: BatchQueryCallback<T>): void;

    /**
     * 查询多条数据(大量数据)，使用游标读取
     *
     * @param sql              sql脚本，参数格式[:param]
     * @param batchSize        一个批次的数据量
     * @param consumer         游标批次读取数据消费者
     * @param underlineToCamel 下划线转驼峰
     */
    query<T = DataRowMap>(sql: JString, batchSize: JInt, consumer: BatchQueryCallback<T>, underlineToCamel: JBoolean): void

    /**
     * 查询多条数据(大量数据)，使用游标读取
     *
     * @param sql       sql脚本，参数格式[:param]
     * @param batchSize 一个批次的数据量
     * @param consumer  游标批次读取数据消费者
     */
    query<T = DataRowMap>(sql: JString, batchSize: JInt, consumer: BatchQueryCallback<T>): void

    /**
     * 查询多条数据(大量数据)，使用游标读取
     *
     * @param sql              sql脚本，参数格式[:param]
     * @param paramMap         参数(可选)，参数格式[:param]
     * @param consumer         游标读取数据消费者
     * @param underlineToCamel 下划线转驼峰
     */
    query<T = DataRowMap>(sql: JString, paramMap: SqlParamMap, consumer: QueryCallback<T>, underlineToCamel: JBoolean): void;

    /**
     * 查询多条数据(大量数据)，使用游标读取
     *
     * @param sql      sql脚本，参数格式[:param]
     * @param paramMap 参数(可选)，参数格式[:param]
     * @param consumer 游标读取数据消费者
     */
    query<T = DataRowMap>(sql: JString, paramMap: SqlParamMap, consumer: QueryCallback<T>): void;

    /**
     * 查询多条数据(大量数据)，使用游标读取
     *
     * @param sql              sql脚本，参数格式[:param]
     * @param consumer         游标读取数据消费者
     * @param underlineToCamel 下划线转驼峰
     */
    query<T = DataRowMap>(sql: JString, consumer: QueryCallback<T>, underlineToCamel: JBoolean): void;

    /**
     * 查询多条数据(大量数据)，使用游标读取
     *
     * @param sql      sql脚本，参数格式[:param]
     * @param consumer 游标读取数据消费者
     */
    query<T = DataRowMap>(sql: JString, consumer: QueryCallback<T>): void;

    /**
     * 排序查询
     *
     * @param sql              sql脚本，参数格式[:param]
     * @param sort             排序配置
     * @param paramMap         参数，参数格式[:param]
     * @param underlineToCamel 下划线转驼峰
     */
    queryBySort<T = DataRowMap>(sql: JString, sort: QueryBySort, paramMap: SqlParamMap, underlineToCamel: JBoolean): JList<T>

    /**
     * 排序查询
     *
     * @param sql      sql脚本，参数格式[:param]
     * @param sort     排序配置
     * @param paramMap 参数，参数格式[:param]
     */
    queryBySort<T = DataRowMap>(sql: JString, sort: QueryBySort, paramMap: SqlParamMap): JList<T>

    /**
     * 排序查询
     *
     * @param sql               sql脚本，参数格式[:param]
     * @param sort              排序配置
     * @param underlineToCamel  下划线转驼峰
     */
    queryBySort<T = DataRowMap>(sql: JString, sort: QueryBySort, underlineToCamel: JBoolean): JList<T>;

    /**
     * 排序查询
     *
     * @param sql  sql脚本，参数格式[:param]
     * @param sort 排序配置
     */
    queryBySort<T = DataRowMap>(sql: JString, sort: QueryBySort): JList<T>;

    /**
     * 分页查询(支持排序)，返回分页对象
     *
     * @param sql              sql脚本，参数格式[:param]
     * @param pagination       分页配置(支持排序)
     * @param paramMap         参数，参数格式[:param]
     * @param underlineToCamel 下划线转驼峰
     */
    queryByPage<T = DataRowMap>(sql: JString, pagination: QueryByPage, paramMap: SqlParamMap, underlineToCamel: JBoolean): IPage<T>;

    /**
     * 分页查询(支持排序)，返回分页对象
     *
     * @param sql        sql脚本，参数格式[:param]
     * @param pagination 分页配置(支持排序)
     * @param paramMap   参数，参数格式[:param]
     */
    queryByPage<T = DataRowMap>(sql: JString, pagination: QueryByPage, paramMap: SqlParamMap): IPage<T>;

    /**
     * 分页查询(支持排序)，返回分页对象
     *
     * @param sql        sql脚本，参数格式[:param]
     * @param pagination 分页配置(支持排序)
     */
    queryByPage<T = DataRowMap>(sql: JString, pagination: QueryByPage): IPage<T>;

    /**
     * 查询数据库表数据
     *
     * @param tableName         表名称
     * @param whereMap          更新条件字段(只支持=，and条件)
     * @param camelToUnderscore whereMap字段驼峰转下划线(可选)
     * @param underlineToCamel  返回数据下划线转驼峰
     */
    queryTableList<T = DataRowMap>(tableName: JString, whereMap: SqlParamMap, camelToUnderscore: JBoolean, underlineToCamel: JBoolean): JList<T>;

    /**
     * 查询数据库表数据
     *
     * @param tableName         表名称
     * @param whereMap          更新条件字段(只支持=，and条件)
     * @param camelToUnderscore whereMap字段驼峰转下划线(可选)
     */
    queryTableList<T = DataRowMap>(tableName: JString, whereMap: SqlParamMap, camelToUnderscore: JBoolean): JList<T>;

    /**
     * 查询数据库表数据
     *
     * @param tableName         表名称
     * @param whereMap          更新条件字段(只支持=，and条件)
     */
    queryTableList<T = DataRowMap>(tableName: JString, whereMap: SqlParamMap): JList<T>

    /**
     * 查询数据库表数据
     *
     * @param tableName         表名称
     * @param whereMap          更新条件字段(只支持=，and条件)
     * @param camelToUnderscore whereMap字段驼峰转下划线(可选)
     * @param underlineToCamel  返回数据下划线转驼峰
     */
    queryTableMap<T = DataRowMap>(tableName: JString, whereMap: SqlParamMap, camelToUnderscore: JBoolean, underlineToCamel: JBoolean): T;

    /**
     * 查询数据库表数据
     *
     * @param tableName         表名称
     * @param whereMap          更新条件字段(只支持=，and条件)
     * @param camelToUnderscore 字段驼峰转下划线(可选)
     */
    queryTableMap<T = DataRowMap>(tableName: JString, whereMap: SqlParamMap, camelToUnderscore: JBoolean): T;

    /**
     * 查询数据库表数据
     *
     * @param tableName         表名称
     * @param whereMap          更新条件字段(只支持=，and条件)
     */
    queryTableMap<T = DataRowMap>(tableName: JString, whereMap: SqlParamMap): T;

    // --------------------------------------------------------------------------------------------
    // Update 操作
    // --------------------------------------------------------------------------------------------

    /**
     * 执行更新SQL，返回更新影响数据量
     *
     * @param sql      sql脚本，参数格式[:param]
     * @param paramMap 参数(可选)，参数格式[:param]
     */
    update(sql: JString, paramMap: SqlParamMap): JInt;

    /**
     * 执行更新SQL，返回更新影响数据量
     *
     * @param sql sql脚本，参数格式[:param]
     */
    update(sql: JString): JInt;

    /**
     * 更新数据库表数据
     *
     * @param tableName         表名称
     * @param fields            更新字段值
     * @param whereMap          更新条件字段(只支持=，and条件)
     * @param camelToUnderscore 字段驼峰转下划线(可选)
     */
    updateTable(tableName: JString, fields: SqlParamMap, whereMap: SqlParamMap, camelToUnderscore: JBoolean): JInt;

    /**
     * 更新数据库表数据
     *
     * @param tableName 表名称
     * @param fields    更新字段值
     * @param whereMap  更新条件字段(只支持=，and条件)
     */
    updateTable(tableName: JString, fields: SqlParamMap, whereMap: SqlParamMap): JInt;

    /**
     * 更新数据库表数据
     *
     * @param tableName         表名称
     * @param fields            更新字段值
     * @param where             自定义where条件(不用写where关键字)
     * @param camelToUnderscore 字段驼峰转下划线(可选)
     */
    updateTable(tableName: JString, fields: SqlParamMap, where: JString, camelToUnderscore: JBoolean): JInt;

    /**
     * 更新数据库表数据
     *
     * @param tableName 表名称
     * @param fields    更新字段值
     * @param where     自定义where条件(不用写where关键字)
     */
    updateTable(tableName: JString, fields: SqlParamMap, where: JString): JInt;

    /**
     * 批量执行更新SQL，返回更新影响数据量
     *
     * @param sql          sql脚本，参数格式[:param]
     * @param paramMapList 参数数组，参数格式[:param]
     */
    batchUpdate(sql: JString, paramMapList: Array<SqlParamMap>): Array<JInt>;

    // --------------------------------------------------------------------------------------------
    // Delete 操作
    // --------------------------------------------------------------------------------------------

    /**
     * 删除数据库表数据
     *
     * @param tableName         表名称
     * @param whereMap          更新条件字段(只支持=，and条件)
     * @param camelToUnderscore 字段驼峰转下划线(可选)
     */
    deleteTable(tableName: JString, whereMap: SqlParamMap, camelToUnderscore: JBoolean): JInt;

    /**
     * 删除数据库表数据
     *
     * @param tableName 表名称
     * @param whereMap  更新条件字段(只支持=，and条件)
     */
    deleteTable(tableName: JString, whereMap: SqlParamMap): JInt;

    /**
     * 删除数据库表数据
     *
     * @param tableName 表名称
     * @param where     自定义where条件(不用写where关键字)
     */
    deleteTable(tableName: JString, where: JString): JInt;

    // --------------------------------------------------------------------------------------------
    // Insert 操作
    // --------------------------------------------------------------------------------------------

    /**
     * 执行insert SQL，返回数据库自增主键值和新增数据量
     *
     * @param sql      sql脚本，参数格式[:param]
     * @param paramMap 参数(可选)，参数格式[:param]
     */
    insert(sql: JString, paramMap: SqlParamMap): InsertResult;

    /**
     * 执行insert SQL，返回数据库自增主键值和新增数据量
     *
     * @param sql sql脚本，参数格式[:param]
     */
    insert(sql: JString): InsertResult;

    /**
     * 数据插入到表
     *
     * @param tableName         表名称
     * @param fields            字段名
     * @param camelToUnderscore 字段驼峰转下划线(可选)
     */
    insertTable(tableName: JString, fields: SqlParamMap, camelToUnderscore: JBoolean): InsertResult;

    /**
     * 数据插入到表
     *
     * @param tableName 表名称
     * @param fields    字段名
     */
    insertTable(tableName: JString, fields: SqlParamMap): InsertResult;

    /**
     * 数据插入到表
     *
     * @param tableName         表名称
     * @param fieldsList        字段名集合
     * @param camelToUnderscore 字段驼峰转下划线(可选)
     */
    insertTables(tableName: JString, fieldsList: Array<SqlParamMap>, camelToUnderscore: JBoolean): JList<InsertResult>;

    /**
     * 数据插入到表
     *
     * @param tableName  表名称
     * @param fieldsList 字段名集合
     */
    insertTables(tableName: JString, fieldsList: Array<SqlParamMap>): JList<InsertResult>;

    // --------------------------------------------------------------------------------------------
    //  事务操作
    // --------------------------------------------------------------------------------------------

    /**
     * 在事务内支持操作
     *
     * @param action              事务内数据库操作
     * @param propagationBehavior 设置事务传递性
     * @param timeout             设置事务超时时间，-1表示不超时(单位：秒)
     * @param isolationLevel      设置事务隔离级别
     * @param readOnly            设置事务是否只读
     */
    beginTX<T = any>(action: ActionInTX<T>, propagationBehavior: Propagation, timeout: JInt, isolationLevel: IsolationLevel, readOnly: JBoolean): T;

    /**
     * 在事务内支持操作
     *
     * @param action              事务内数据库操作
     * @param propagationBehavior 设置事务传递性
     * @param timeout             设置事务超时时间(单位：秒)
     * @param isolationLevel      设置事务隔离级别
     */
    beginTX<T = any>(action: ActionInTX<T>, propagationBehavior: Propagation, timeout: JInt, isolationLevel: IsolationLevel): T;

    /**
     * 在事务内支持操作
     *
     * @param action              事务内数据库操作
     * @param propagationBehavior 设置事务传递性
     * @param timeout             设置事务超时时间(单位：秒)
     */
    beginTX<T = any>(action: ActionInTX<T>, propagationBehavior: Propagation, timeout: JInt): T;

    /**
     * 在事务内支持操作
     *
     * @param action              事务内数据库操作
     * @param propagationBehavior 设置事务传递性
     */
    beginTX<T = any>(action: ActionInTX<T>, propagationBehavior: Propagation): T;

    /**
     * 在事务内支持操作
     *
     * @param action 事务内数据库操作
     */
    beginTX<T = any>(action: ActionInTX<T>): T;

    /**
     * 在事务内支持操作
     *
     * @param action              事务内数据库操作
     * @param propagationBehavior 设置事务传递性
     * @param timeout             设置事务超时时间，-1表示不超时(单位：秒)
     * @param isolationLevel      设置事务隔离级别
     */
    beginReadOnlyTX<T = any>(action: ActionInTX<T>, propagationBehavior: Propagation, timeout: JInt, isolationLevel: IsolationLevel): T;

    /**
     * 在事务内支持操作
     *
     * @param action              事务内数据库操作
     * @param propagationBehavior 设置事务传递性
     * @param timeout             设置事务超时时间，-1表示不超时(单位：秒)
     */
    beginReadOnlyTX<T = any>(action: ActionInTX<T>, propagationBehavior: Propagation, timeout: JInt): T;

    /**
     * 在事务内支持操作
     *
     * @param action              事务内数据库操作
     * @param propagationBehavior 设置事务传递性
     */
    beginReadOnlyTX<T = any>(action: ActionInTX<T>, propagationBehavior: Propagation): T;

    /**
     * 在事务内支持操作
     *
     * @param action 事务内数据库操作
     */
    beginReadOnlyTX<T = any>(action: ActionInTX<T>): T;

    /**
     * 获取数据源信息
     */
    getInfo(): JdbcInfo;

    /**
     * 获取数据源状态
     */
    getStatus(): JdbcDataSourceStatus;
}
