/**
 * 数据库数据行
 */
interface DataRowEntity {
    [field: string]: SqlFieldType;
}

interface MyBatisJdbcDataSource {
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
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    queryEntity<T = DataRowEntity>(sqlId: JString, paramMap: SqlParamMap): T;

    /**
     * 查询一条数据，返回一个Map
     *
     * @param sqlId    SqlID
     */
    queryEntity<T = DataRowEntity>(sqlId: JString): T;

    /**
     * 查询多条数据，返回一个Map数组
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    queryList<T = DataRowEntity>(sqlId: JString, paramMap: SqlParamMap): JList<T>;

    /**
     * 查询多条数据，返回一个Map数组
     *
     * @param sqlId    SqlID
     */
    queryList<T = DataRowEntity>(sqlId: JString): JList<T>;

    /**
     * 查询返回一个 String
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    queryString(sqlId: JString, paramMap: SqlParamMap): JString;

    /**
     * 查询返回一个 String
     *
     * @param sqlId    SqlID
     */
    queryString(sqlId: JString): JString;

    /**
     * 查询返回一个 Long
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    queryLong(sqlId: JString, paramMap: SqlParamMap): JLong;

    /**
     * 查询返回一个 Long
     *
     * @param sqlId    SqlID
     */
    queryLong(sqlId: JString): JLong;

    /**
     * 查询返回一个 Double
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    queryDouble(sqlId: JString, paramMap: SqlParamMap): JDouble;

    /**
     * 查询返回一个 Double
     *
     * @param sqlId    SqlID
     */
    queryDouble(sqlId: JString): JDouble;

    /**
     * 查询返回一个 BigDecimal
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    queryBigDecimal(sqlId: JString, paramMap: SqlParamMap): JBigDecimal;

    /**
     * 查询返回一个 BigDecimal
     *
     * @param sqlId    SqlID
     */
    queryBigDecimal(sqlId: JString): JBigDecimal

    /**
     * 查询返回一个 Boolean
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    queryBoolean(sqlId: JString, paramMap: SqlParamMap): JBoolean;

    /**
     * 查询返回一个 Boolean
     *
     * @param sqlId    SqlID
     */
    queryBoolean(sqlId: JString): JBoolean;

    /**
     * 查询返回一个 Date
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    queryDate(sqlId: JString, paramMap: SqlParamMap): JDate;

    /**
     * 查询返回一个 Date
     *
     * @param sqlId    SqlID
     */
    queryDate(sqlId: JString): JDate;

    /**
     * SQL Count(获取一个SQL返回的数据总量)
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    queryCount(sqlId: JString, paramMap: SqlParamMap): JLong;

    /**
     * SQL Count(获取一个SQL返回的数据总量)
     *
     * @param sqlId    SqlID
     */
    queryCount(sqlId: JString): JLong;

    /**
     * 查询多条数据(大量数据)，使用游标读取
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     * @param batchSize 一个批次的数据量
     * @param consumer  游标批次读取数据消费者
     */
    query<T = DataRowEntity>(sqlId: JString, paramMap: SqlParamMap, batchSize: JInt, consumer: BatchQueryCallback<T>): void;

    /**
     * 查询多条数据(大量数据)，使用游标读取
     *
     * @param sqlId    SqlID
     * @param batchSize 一个批次的数据量
     * @param consumer  游标批次读取数据消费者
     */
    query<T = DataRowEntity>(sqlId: JString, batchSize: JInt, consumer: BatchQueryCallback<T>): void

    /**
     * 查询多条数据(大量数据)，使用游标读取
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     * @param consumer 游标读取数据消费者
     */
    query<T = DataRowEntity>(sqlId: JString, paramMap: SqlParamMap, consumer: QueryCallback<T>): void;

    /**
     * 查询多条数据(大量数据)，使用游标读取
     *
     * @param sqlId    SqlID
     * @param consumer 游标读取数据消费者
     */
    query<T = DataRowEntity>(sqlId: JString, consumer: QueryCallback<T>): void;

    /**
     * 排序查询
     *
     * @param sqlId    SqlID
     * @param sort     排序配置
     * @param paramMap 查询参数
     */
    queryBySort<T = DataRowEntity>(sqlId: JString, sort: QueryBySort, paramMap: SqlParamMap): JList<T>

    /**
     * 排序查询
     *
     * @param sqlId    SqlID
     * @param sort 排序配置
     */
    queryBySort<T = DataRowEntity>(sqlId: JString, sort: QueryBySort): JList<T>;

    /**
     * 分页查询(支持排序)，返回分页对象
     *
     * @param sqlId    SqlID
     * @param pagination 分页配置(支持排序)
     * @param paramMap 查询参数
     */
    queryByPage<T = DataRowEntity>(sqlId: JString, pagination: QueryByPage, paramMap: SqlParamMap): IPage<T>;

    /**
     * 分页查询(支持排序)，返回分页对象
     *
     * @param sqlId    SqlID
     * @param pagination 分页配置(支持排序) - 支持加入查询参数
     */
    queryByPage<T = DataRowEntity>(sqlId: JString, pagination: QueryByPage): IPage<T>;

    /**
     * 查询数据库表数据
     *
     * @param tableName         表名称
     * @param whereMap          更新条件字段(只支持=，and条件)
     */
    queryTableList<T = DataRowEntity>(tableName: JString, whereMap: { [field in keyof T]?: T[field] } | { [field: string]: SqlFieldType }): JList<T>

    /**
     * 查询数据库表数据
     *
     * @param tableName         表名称
     * @param whereMap          更新条件字段(只支持=，and条件)
     */
    queryTableEntity<T = DataRowEntity>(tableName: JString, whereMap: { [field in keyof T]?: T[field] } | { [field: string]: SqlFieldType }): T;

    // --------------------------------------------------------------------------------------------
    // Update 操作
    // --------------------------------------------------------------------------------------------

    /**
     * 执行更新SQL，返回更新影响数据量
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    update(sqlId: JString, paramMap: SqlParamMap): JInt;

    /**
     * 执行更新SQL，返回更新影响数据量
     *
     * @param sqlId    SqlID
     */
    update(sqlId: JString): JInt;

    /**
     * 更新数据库表数据
     *
     * @param tableName 表名称
     * @param fields    更新字段值
     * @param whereMap  更新条件字段(只支持=，and条件)
     */
    updateTable<T = DataRowEntity>(tableName: JString, fields: { [field in keyof T]?: T[field] } | { [field: string]: SqlFieldType }, whereMap: { [field in keyof T]?: T[field] } | { [field: string]: SqlFieldType }): JInt;

    /**
     * 更新数据库表数据
     *
     * @param tableName 表名称
     * @param fields    更新字段值
     * @param where     自定义where条件(不用写where关键字)
     */
    updateTable<T = DataRowEntity>(tableName: JString, fields: { [field in keyof T]?: T[field] } | { [field: string]: SqlFieldType }, where: JString): JInt;

    /**
     * 批量执行更新SQL，返回更新影响数据量
     *
     * @param sqlId         SqlID
     * @param paramMapList  参数数组
     */
    batchUpdate(sqlId: JString, paramMapList: Array<SqlParamMap>): Array<JInt>;

    // --------------------------------------------------------------------------------------------
    // Delete 操作
    // --------------------------------------------------------------------------------------------

    /**
     * 删除数据库表数据
     *
     * @param tableName 表名称
     * @param whereMap  更新条件字段(只支持=，and条件)
     */
    deleteTable<T = DataRowEntity>(tableName: JString, whereMap: { [field in keyof T]?: T[field] } | { [field: string]: SqlFieldType }): JInt;

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
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    insert(sqlId: JString, paramMap: SqlParamMap): InsertResult;

    /**
     * 执行insert SQL，返回数据库自增主键值和新增数据量
     *
     * @param sqlId    SqlID
     */
    insert(sqlId: JString): InsertResult;

    /**
     * 数据插入到表
     *
     * @param tableName 表名称
     * @param fields    字段名
     */
    insertTable<T = DataRowEntity>(tableName: JString, fields: { [field in keyof T]?: T[field] } | { [field: string]: SqlFieldType }): InsertResult;

    /**
     * 数据插入到表
     *
     * @param tableName  表名称
     * @param fieldsList 字段名集合
     */
    insertTables<T = DataRowEntity>(tableName: JString, fieldsList: Array<{ [field in keyof T]?: T[field] } | { [field: string]: SqlFieldType }>): JList<InsertResult>;

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
