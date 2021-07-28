interface TableSchema {
    /**
     * 是否是视图
     */
    isView(): JBoolean;

    /**
     * 数据库名称
     */
    getSchemaName(): JString;

    /**
     * 表名称
     */
    getTableName(): JString;

    /**
     * 表注释说明
     */
    getDescription(): JString;

    /**
     * 其他列属性
     */
    getAttributes(): JMap<JString, any>;

    /**
     * 数据库列
     */
    getColumnList(): JList<TableColumn>;
}