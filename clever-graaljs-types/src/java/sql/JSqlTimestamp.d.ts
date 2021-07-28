/**
 * java.sql.Timestamp 类型，不要在JavaScript/TypeScript环境中创建该类型对象<br />
 * 请使用内置对象“Interop”创建该类型实例<br />
 * @see Interop
 */
interface JSqlTimestamp extends JDate {
    java_sql_Timestamp: "java.sql.Timestamp";

    /**
     * 指示此 Timestamp 对象是否晚于给定的 Timestamp 对象
     */
    after(ts: JSqlTimestamp): JBoolean;

    /**
     * 指示此 Timestamp 对象是否早于给定的 Timestamp 对象
     */
    before(ts: JSqlTimestamp): JBoolean;

    /**
     * 将此 Timestamp 对象与给定的 Date（必须为 Timestamp 对象）相比较
     * 将此 Timestamp 对象与给定的 Date（必须为 Timestamp 对象）相比较 <br>
     * this 早于 参数o 返回-1  <br>
     * this 晚于 参数o 返回 1  <br>
     * 时间相等返回0  <br>
     */
    compareTo(o: JSqlTimestamp): JBoolean;

    /**
     * 获取此 Timestamp 对象的 nanos 值
     */
    getNanos(): JInt;
}