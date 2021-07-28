interface MateDataManage {
    /**
     * 设置默认数据源元数据
     *
     * @param defaultName 默认数据源元数据名称
     */
    setDefault(defaultName: JString): MateDataService;

    /**
     * 获取默认数据源元数据
     */
    getDefault(): MateDataService;

    /**
     * 获取默认数据源元数据名称
     */
    getDefaultName(): JString;

    /**
     * 根据名称获取数据源元数据
     *
     * @param name 数据源元数据名称
     */
    getMateDataService(name: String): MateDataService;

    /**
     * 判断数据源元数据是否存在
     *
     * @param name 数据源元数据名称
     */
    hasMateDataService(name: String): JBoolean;

    /**
     * 删除数据源元数据
     *
     * @param name 数据源名称
     */
    del(name: JString): JBoolean;

    /**
     * 删除所有数据源元数据
     */
    delAll(): void;

    /**
     * 获取所有数据源元数据名称
     */
    allNames(): JSet<JString>;
}

/**
 * 数据源元数据管理对象
 */
declare const MateDataManage: MateDataManage;
