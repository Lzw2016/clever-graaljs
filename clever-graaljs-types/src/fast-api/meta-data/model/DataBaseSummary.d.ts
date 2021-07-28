interface DataBaseSummary {
    /**
     * 数据库名称
     */
    getSchemaName(): JString;

    /**
     * 数据库表名称
     */
    getTableList(): JList<TableSchema>;

    /**
     * @param tableName 表名称
     */
    getTableSchema(tableName: JString): TableSchema;
}
