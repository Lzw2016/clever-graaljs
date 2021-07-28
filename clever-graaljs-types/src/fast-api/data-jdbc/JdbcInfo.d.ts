interface JdbcInfo extends JObject {
    /** 数据库驱动名称: com.mysql.cj.jdbc.Driver */
    getDriverClassName(): JString;

    /** 数据库链接url: jdbc:mysql://host:3306/db-name */
    getJdbcUrl(): JString;

    /** 是否自动提交 */
    isAutoCommit(): JBoolean;

    /** 是否只读 */
    isReadOnly(): JBoolean;

    /** 数据库类型 */
    getDbType(): DbType;

    /** 数据源是否关闭 */
    isClosed(): JBoolean;
}
