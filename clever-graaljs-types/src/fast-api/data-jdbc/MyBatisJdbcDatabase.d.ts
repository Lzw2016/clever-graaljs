/**
 * MyBatis JDBC数据库管理器
 */
interface MyBatisJdbcDatabase {
    /**
     * 获取默认数据源
     */
    getDefault(): MyBatisJdbcDataSource;

    /**
     * 获取默认数据源名称
     */
    getDefaultName(): JString;

    /**
     * 根据名称获取数据源
     *
     * @param name 数据源名称
     */
    getDataSource(name: JString): MyBatisJdbcDataSource | null;

    /**
     * 判断数据源是否存在
     *
     * @param name 数据源名称
     */
    hasDataSource(name: JString): JBoolean;

    /**
     * 添加数据源
     *
     * @param name       数据源名称
     * @param config     数据源配置
     */
    add(name: JString, config: JdbcConfig): MyBatisJdbcDataSource;

    /**
     * 获取所有数据源名称
     */
    allNames(): JSet<JString>;

    /**
     * 获取数据源信息
     *
     * @param name 数据源名称
     */
    getInfo(name: JString): JdbcInfo

    /**
     * 获取所有数据源信息
     */
    allInfos(): JMap<JString, JdbcInfo>;

    /**
     * 获取数据源信息
     * @param name 数据源名称
     */
    getStatus(name: JString): JdbcDataSourceStatus;

    /**
     * 获取数据源状态
     */
    allStatus(): JMap<JString, JdbcDataSourceStatus>;
}

/**
 * MyBatis JDBC数据源管理对象
 */
declare const MyBatisJdbcDatabase: MyBatisJdbcDatabase;
