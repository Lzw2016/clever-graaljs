interface JdbcDatabaseManage {
    /**
     * 设置默认数据源
     *
     * @param defaultName 默认数据源名称
     * @return 默认数据源对象
     */
    setDefault(defaultName: JString): JdbcDataSource;

    /**
     * 添加数据源
     *
     * @param name       数据源名称
     * @param config     数据源配置
     */
    add(name: JString, config: JdbcConfig): JdbcDataSource;

    /**
     * 删除数据源
     *
     * @param name 数据源名称
     */
    del(name: JString): JBoolean;

    /**
     * 删除所有数据源
     */
    delAll(): void;
}

/**
 * JDBC数据源管理对象
 */
declare const JdbcDatabaseManage: JdbcDatabaseManage;
