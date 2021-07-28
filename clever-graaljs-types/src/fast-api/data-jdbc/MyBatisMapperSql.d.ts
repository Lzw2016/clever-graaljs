interface MyBatisMapperSql {
    /**
     * 获取 SqlSource
     *
     * @param sqlId SQL ID
     */
    getSqlSource(sqlId: JString): SqlSource | null;

    /**
     * 获取 SqlSource
     *
     * @param sqlId     SQL ID
     * @param parameter SQL参数
     */
    getBoundSql(sqlId: JString, parameter: object): BoundSql | null;
}

interface SqlSource {
    /**
     * 获取 SqlSource
     *
     * @param parameter SQL参数
     */
    getBoundSql(parameter: object): BoundSql;
}

interface BoundSql {
    /**
     * 原始参数对象
     */
    getParameterObject(): object;

    /**
     * 生成的SQL语句
     */
    getSql(): JString;

    /**
     * 参数名称形式的sql
     */
    getNamedParameterSql(): JString;

    /**
     * Sql参数名称列表(有顺序)
     */
    getParameterList(): JList<JString>;

    /**
     * 动态sql中的表达式变量
     */
    getParameterExpressionSet(): JSet<JString>;

    /**
     * 参数值列表(有顺序)
     */
    getParameterValueList(): JList<object>;

    /**
     * Sql参数Map集合
     */
    getParameterMap(): JMap<JString, object>;

    /**
     * 设置附加的参数
     * @param name  参数名
     * @param value 参数值
     */
    setAdditionalParameter(name: JString, value: object): void;
}
