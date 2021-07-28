enum DbType {
    MYSQL = "mysql",
    MARIADB = "mariadb",
    ORACLE = "oracle",
    DB2 = "db2",
    H2 = "h2",
    HSQL = "hsql",
    SQLITE = "sqlite",
    POSTGRE_SQL = "postgresql",
    SQL_SERVER2005 = "sqlserver2005",
    SQL_SERVER = "sqlserver",
    DM = "dm",
    OTHER = "other",
}

/**
 * 排序类型
 */
enum SortType {
    /**
     * 由小到大排序
     */
    ASC = "ASC",
    /**
     * 由大到小排序
     */
    DESC = "DESC",
}

/**
 * 事务隔离级别
 */
enum IsolationLevel {
    /**
     * 使用底层数据存储的默认隔离级别
     */
    DEFAULT = -1,
    /**
     * 未提交读<br />
     * 可能发生脏读、不可重复读和幻象读
     */
    READ_UNCOMMITTED = 1,
    /**
     * 提交读<br />
     * 可能发生不可重复读和幻象读
     */
    READ_COMMITTED = 2,
    /**
     * 可重读读<br />
     * 可能发生虚读
     */
    REPEATABLE_READ = 4,
    /**
     * 串行化
     */
    SERIALIZABLE = 8
}

/**
 * 事务传递特性
 */
enum Propagation {
    /**
     * 如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中。这是最常见的选择
     */
    REQUIRED = 0,
    /**
     * 支持当前事务，如果当前没有事务，就以非事务方式执行
     */
    SUPPORTS = 1,
    /**
     * 使用当前的事务，如果当前没有事务，就抛出异常
     */
    MANDATORY = 2,
    /**
     * 新建事务，如果当前存在事务，把当前事务挂起
     */
    REQUIRES_NEW = 3,
    /**
     * 以非事务方式执行操作，如果当前存在事务，就把当前事务挂起
     */
    NOT_SUPPORTED = 4,
    /**
     * 以非事务方式执行，如果当前存在事务，则抛出异常
     */
    NEVER = 5,
    /**
     * 如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行与PROPAGATION_REQUIRED类 似的操作
     */
    NESTED = 6
}
