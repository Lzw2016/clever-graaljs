interface MateDataService {
    /**
     * 重新加载数据库元数据
     */
    reload(): void;

    getDataBaseSummaryList(): JList<DataBaseSummary>;

    /**
     * @param database 数据库名称(schema名称)
     */
    getDataBaseSummary(database: JString): DataBaseSummary;

    /**
     * @param database  数据库名称(schema名称)
     * @param tableName 表名称
     */
    getTableSchema(database: JString, tableName: JString): TableSchema;
}
