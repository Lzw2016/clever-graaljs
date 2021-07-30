interface MyBatisJdbcDatabaseManage {
    /**
     * 设置默认数据源
     *
     * @param defaultName 默认数据源名称
     * @return 默认数据源对象
     */
    setDefault(defaultName: JString): MyBatisJdbcDataSource;

    /**
     * 设置默认数据源
     *
     * @param defaultName           默认数据源名称
     * @param myBatisJdbcDataSource 默认数据源对象
     */
    setDefault(defaultName: JString, myBatisJdbcDataSource: MyBatisJdbcDataSource): MyBatisJdbcDataSource;

    /**
     * 添加数据源
     *
     * @param name                  数据源名称
     * @param myBatisJdbcDataSource MyBatis数据源
     */
    add(name: JString, myBatisJdbcDataSource: MyBatisJdbcDataSource): MyBatisJdbcDataSource;

    /**
     * 添加数据源
     *
     * @param name       数据源名称
     * @param config     数据源配置
     * @param mapperSql  mapper sql资源
     */
    add(name: JString, config: JdbcConfig, mapperSql: MyBatisMapperSql): MyBatisJdbcDataSource;

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

declare const MyBatisJdbcDatabaseManage: MyBatisJdbcDatabaseManage;
