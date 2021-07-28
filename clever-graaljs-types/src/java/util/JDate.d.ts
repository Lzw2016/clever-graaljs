/**
 * java.util.Date 类型，不要在JavaScript/TypeScript环境中创建该类型对象<br />
 * 请使用内置对象“Interop”创建该类型实例<br />
 * @see Interop
 */
interface JDate extends JObject, JComparable<JDate> {
    java_util_Date: "java.util.Date";

    /**
     * 测试此日期是否在指定日期之后
     */
    after(when: JDate): JBoolean;

    /**
     * 测试此日期是否在指定日期之前
     */
    before(when: JDate): JBoolean;

    /**
     * 返回自1970年1月1日以来，由此Date对象表示的00:00:00 GMT的毫秒数
     */
    getTime(): JLong;

    /**
     * YEAR (从1900年开始的时间数)
     */
    getYear(): JInt;

    /**
     * MONTH (从0开始的月份数)
     */
    getMonth(): JInt;

    /**
     * DAY_OF_MONTH
     */
    getDate(): JInt;

    /**
     * HOUR_OF_DAY
     */
    getHours(): JInt;

    /**
     * MINUTE
     */
    getMinutes(): JInt;

    /**
     * SECOND
     */
    getSeconds(): JInt;

    /**
     * DAY_OF_WEEK (从0开始)
     */
    getDay(): JInt;
}