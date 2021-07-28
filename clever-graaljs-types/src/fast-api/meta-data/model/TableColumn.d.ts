interface TableColumn {
    /**
     * 数据库名称
     */
    getSchemaName(): JString;

    /**
     * 表名称
     */
    getTableName(): JString;

    /**
     * 序号位置
     */
    getOrdinalPosition(): JInt;

    /**
     * 列名称
     */
    getColumnName(): JString;

    /**
     * 数据类型
     */
    getDataType(): JString;

    /**
     * 字符串最大长度，数字精度
     */
    getSize(): JInt;

    /**
     * 字段精度表示
     */
    getWidth(): JString;

    /**
     * 小数位数
     */
    getDecimalDigits(): JInt;

    /**
     * 是否不能为空
     */
    getNotNull(): JBoolean;

    /**
     * 是否自增长
     */
    getAutoIncrement(): JBoolean;

    /**
     * 默认值
     */
    getDefaultValue(): JString;

    /**
     * 列注释
     */
    getComment(): JString;

    /**
     * 所谓Generated Column，就是数据库中这一列由其他列计算而得
     */
    getGenerated(): JBoolean;

    /**
     * 是否是隐藏的列
     */
    getHidden(): JBoolean;

    /**
     * 是否是外键
     */
    getPartOfForeignKey(): JBoolean;

    /**
     * 是否建了索引
     */
    getPartOfIndex(): JBoolean;

    /**
     * 是否是主键
     */
    getPartOfPrimaryKey(): JBoolean;

    /**
     * 是否唯一约束
     */
    getPartOfUniqueIndex(): JBoolean;

    /**
     * 字段映射的Java类型
     */
    getMappedClass(): JClass;

    /**
     * 其他列属性
     */
    getAttributes(): JMap<JString, any>;
}