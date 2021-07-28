interface RedisDatabaseManage {
    /**
     * 设置默认数据源
     *
     * @param defaultName 默认数据源名称
     * @return 默认数据源对象
     */
    setDefault(defaultName: JString): RedisDataSource;

    /**
     * 添加数据源
     *
     * @param name   数据源名称
     * @param config 数据源配置
     */
    add(name: JString, config: RedisConfig): RedisDataSource;

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
 * Redis数据源管理对象
 */
declare const RedisDatabaseManage: RedisDatabaseManage;
