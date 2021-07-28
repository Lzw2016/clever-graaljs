/**
 * SQL参数类型
 */
type SqlParamType = undefined | null | Date
    | JByte | JShort | JInt | JLong | JFloat | JDouble | JBoolean | JChar | JString
    | JBigDecimal | JBigInteger | JDate | JCharSequence;

/**
 * SQL参数Map
 */
interface SqlParamMap {
    [name: string]: SqlParamType | JCollection<SqlParamType>;
}

/**
 * 数据库字段值类型
 */
type SqlFieldType = JByte | JShort | JInt | JLong | JFloat | JDouble | JBoolean | JChar | JString
    | JDate | JSqlDate | JSqlTime | JSqlTimestamp
    | JBigDecimal | JBigInteger;

/**
 * 通用数据库实体类型
 */
interface AnyEntity {
    [key: string]: SqlFieldType;
}

/**
 * 数据库数据行
 */
type DataRowMap = JMap<JString, SqlFieldType>;

/**
 * 查询排序参数
 */
interface QueryBySort {
    /**
     * 排序字段(单字段排序-低优先级)
     */
    orderField?: JString;
    /**
     * 排序类型ASC DESC(单字段排序-低优先级)
     */
    sort?: SortType;

    /**
     * 排序字段集合(多字段排序-高优先级)
     */
    orderFields?: Array<JString>;
    /**
     * 排序类型 ASC DESC(多字段排序-高优先级)
     */
    sorts?: Array<SortType>;

    /**
     * 排序字段 映射Map
     */
    fieldsMapping: { [name: string]: JString };
}

/**
 * 查询分页参数
 */
interface QueryByPage extends Partial<QueryBySort> {
    /**
     * 每页的数据量(1 <= pageSize <= 100)
     */
    pageSize?: JInt;
    /**
     * 当前页面的页码数(pageNo >= 1)
     */
    pageNo?: JInt;
    /**
     * 是否进行 count 查询
     */
    isSearchCount?: JBoolean;

    /** 查询参数 */
    [name: string]: any;
}

interface JdbcConfig {
    /** 数据库驱动名称: com.mysql.cj.jdbc.Driver */
    driverClassName: JString;
    /** 数据库链接url: jdbc:mysql://host:3306/db-name */
    jdbcUrl: JString;
    /** 用户名 */
    username: JString;
    /** 密码 */
    password: JString;
    /** 是否自动提交 */
    autoCommit?: JBoolean;
    /** 是否只读 */
    readOnly?: JBoolean;
    /** 连接池最大大小 */
    maxPoolSize?: JInt;
    /** 最小空闲连接数 */
    minIdle?: JInt;
    /** 连接最大存活时间(毫秒) */
    maxLifetimeMs?: JInt;
    /** 连接超时时间(毫秒) */
    connectionTimeoutMs?: JInt;
    /** 允许连接在池中处于空闲状态的最长时间(毫秒) 。值为0表示从不从池中删除空闲连接 */
    idleTimeoutMs?: JInt;
    /** 测试连接可用的SQL语句 */
    connectionTestQuery?: JString;
    /** 数据源连接属性 */
    dataSourceProperties?: JMap<JString, JString | JDouble | JBoolean>;
}

interface BatchData<T = DataRowMap> {
    /**
     * 列名称集合
     */
    getColumnNames(): JList<JString>;

    /**
     * 列类型集合
     */
    getColumnTypes(): JList<JInt>;

    /**
     * 列宽(列数)
     */
    getColumnCount(): JInt;

    /**
     * 当前批次数
     */
    getRowDataList(): JList<T>;

    /**
     * 当前读取的行数
     */
    getRowCount(): JInt;

    /**
     * 返回当前批次数据量
     */
    getBatchCount(): JInt;
}

interface RowData<T = DataRowMap> {
    /**
     * 列名称集合
     */
    getColumnNames(): JList<JString>;

    /**
     * 列类型集合
     */
    getColumnTypes(): JList<JInt>;

    /**
     * 列宽(列数)
     */
    getColumnCount(): JInt;

    /**
     * 当前行数据
     */
    getRowData(): T;

    /**
     * 当前读取的行数
     */
    getRowCount(): JInt;
}

/**
 * 游标读取数据回调函数(批量读取)
 */
interface BatchQueryCallback<T = DataRowMap> {
    (batch: BatchData<T>): void;
}

/**
 * 游标读取数据回调函数
 */
interface QueryCallback<T = DataRowMap> {
    (row: RowData<T>): void;
}

interface OrderItem extends JObject {
    /**
     * 需要进行排序的字段
     */
    getColumn(): JString;

    /**
     * 是否正序排列，默认 true
     */
    isAsc(): JBoolean;
}

/**
 * 分页查询返回值
 */
interface IPage<T = DataRowMap> extends JObject {
    /**
     * 当前页，默认 1
     */
    getCurrent(): JLong;

    /**
     * 获取排序信息，排序的字段和正反序
     */
    orders(): JList<OrderItem>;

    /**
     * 当前分页总页数
     */
    getPages(): JLong;

    /**
     * 分页记录列表
     */
    getRecords(): JList<T>;

    /**
     * 进行 count 查询 【 默认: true 】
     */
    isSearchCount(): JBoolean;

    /**
     * 当前分页总页数
     */
    getSize(): JLong;

    /**
     * 当前满足条件总行数
     */
    getTotal(): JLong;
}

interface KeyHolder extends JObject {
    /**
     * 所有自动生成的key
     */
    getKeysList(): JList<JMap<JString, SqlFieldType>>;

    /**
     * 当keysList只有一个元素时，才有这个值，值就是那个元素
     */
    getKeys(): JMap<JString, SqlFieldType>;

    /**
     * 当keys只有一个元素时，才有这个值，值就是那个元素的value <br />
     * 一般是自增长主键值
     */
    getKey(): any;
}

/**
 * sql insert 返回值
 */
interface InsertResult extends JObject {
    /**
     * 新增数据量
     */
    getInsertCount(): JInt;

    /**
     * Insert时，数据库自动生成的key
     */
    getKeyHolder(): KeyHolder;

    /**
     * 当更新数据只有一个自动生成的key时，才会有这个字段，其值就是自动生成的key的值<br />
     * 一般是自增长主键值
     */
    getKeyHolderValue(): any;
}

/**
 * 事务状态
 */
interface TransactionStatus extends JObject {
    /**
     * 将底层会话刷新到数据存储（如果适用）
     */
    flush(): void;

    /**
     * 此事务是否在内部携带保存点，即是否已基于保存点创建为嵌套事务
     */
    hasSavepoint(): JBoolean;

    /**
     * 此事务是否已完成，即是否已提交或回滚
     */
    isCompleted(): JBoolean;

    /**
     * 当前事务是否为新事务；否则将参与现有事务，或者可能不会首先在实际事务中运行
     */
    isNewTransaction(): JBoolean;

    /**
     * 事务是否已标记为仅回滚（由应用程序或事务基础结构标记）
     */
    isRollbackOnly(): JBoolean;

    /**
     * 仅设置事务回滚。这将指示事务管理器事务的唯一可能结果可能是回滚，作为引发异常的替代方法，后者将反过来触发回滚
     */
    setRollbackOnly(): void;
}

/**
 * 事务内操作回调函数
 */
interface ActionInTX<T = any> {
    (status: TransactionStatus): T;
}
