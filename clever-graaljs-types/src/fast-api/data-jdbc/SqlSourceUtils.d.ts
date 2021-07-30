interface SqlSourceUtils {
    /**
     * 获取 SqlSource
     *
     * @param xmlSql MyBatis XML sql
     */
    getSqlSource(xmlSql: JString): SqlSource;

    /**
     * 获取 BoundSql
     *
     * @param xmlSql    MyBatis XML sql
     * @param parameter SQL参数
     */
    getBoundSql(xmlSql: JString, parameter: object): BoundSql;
}

declare const SqlSourceUtils: SqlSourceUtils;
