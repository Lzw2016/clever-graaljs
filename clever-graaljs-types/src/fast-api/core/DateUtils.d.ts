/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/28 22:32 <br/>
 */
interface DateUtils extends JObject {
    /**
     * 获取当前时间
     */
    now(): JDate;

    /**
     * 获取当前时间撮
     */
    currentTimeMillis(): JLong;

    // 当前时间字符串
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 得到当前时间的日期字符串，如：2016-4-27、2016-4-27 21:57:15<br/>
     *
     * @param pattern 日期格式字符串，如："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    getCurrentDate(pattern: JString): JString;

    /**
     * 得到当前时间的日期字符串，格式（yyyy-MM-dd）<br/>
     */
    getCurrentDate(): JString;

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     *
     * @return 当前时间字符串，如：12:14:21
     */
    getCurrentTime(): JString;

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     *
     * @return 当前时间字符串，如：2014-01-02 10:14:10
     */
    getCurrentDateTime(): JString;

    /**
     * 得到当前年份字符串 格式（yyyy）
     *
     * @return 当前年字符串，如：2014
     */
    getYear(): JString;

    /**
     * 得到当前月份字符串 格式（MM）
     *
     * @return 当前月字符串，如：02
     */
    getMonth(): JString;

    /**
     * 得到当天字符串 格式（dd）
     *
     * @return 当前天字符串，如：21
     */
    getDay(): JString;

    /**
     * 得到当前星期字符串 格式（E）星期几
     *
     * @return 当前日期是星期几，如：5
     */
    getWeek(): JString;

    // 时间格式化
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd）
     *
     * @param date    日期对象
     * @param pattern 日期格式，如："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    formatToString(date: JDate, pattern: JString): JString;

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     *
     * @param date 日期对象
     * @return 日期格式字符串，如：2015-03-01 10:21:14
     */
    formatToString(date: JDate): JString;

    // 解析时间得到字符串
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 根据时间数，得到日期字符串<br/>
     *
     * @param dateTime 时间数，可通过System.currentTimeMillis()得到
     * @param pattern  时间格式字符串，如："yyyy-MM-dd HH:mm:ss"，默认是：yyyy-MM-dd
     * @return 时间字符串
     */
    getDate(dateTime: JLong, pattern: JString): JString;

    /**
     * 根据时间数，得到日期字符串，格式：yyyy-MM-dd HH:mm:ss<br/>
     *
     * @param dateTime 时间数，可通过System.currentTimeMillis()得到
     * @return 时间字符串，如：2014-03-02 03:12:03
     */
    getDate(dateTime: JLong): JString;

    // 解析时间
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 日期型字符串转化为日期,支持格式如下：<br/>
     * "yyyy-MM-dd"<br/>
     * "yyyy-MM-dd HH:mm:ss"<br/>
     * "yyyy-MM-dd HH:mm"<br/>
     * "yyyy/MM/dd"<br/>
     * "yyyy/MM/dd HH:mm:ss"<br/>
     * "yyyy/MM/dd HH:mm"<br/>
     * "yyyyMMdd"<br/>
     * "yyyyMMdd HH:mm:ss"<br/>
     * "yyyyMMdd HH:mm"<br/>
     * "yyyy-MM-dd'T'HH:mm:ss.SSSZ"<br/>
     * "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"<br/>
     * 时间搓(毫秒)<br/>
     *
     * @param str 日期字符串，如：2014/03/01 12:15:10
     * @return 失败返回 null
     */
    parseDate(str: any): JDate;

    /**
     * 把字符串解析成时间对象
     */
    parseDate(str: JString, ...parsePatterns: JString[]): JDate;

    // 时间差
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 获取两个时间之间的年数，“end - start” 的年数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return “end - start” 的年数
     */
    pastYears(start: JDate, end: JDate): JInt;

    /**
     * 获取两个时间之间的天数，“end - start” 的天数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return “end - start” 的天数
     */
    pastDays(start: JDate, end: JDate): JInt;

    /**
     * 获取两个时间之间的小时数，“end - start” 的小时数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return “end - start” 的小时数
     */
    pastHours(start: JDate, end: JDate): JInt;

    /**
     * 获取两个时间之间的分钟数，“end - start” 的分钟数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return “end - start” 的分钟数
     */
    pastMinutes(start: JDate, end: JDate): JInt;

    /**
     * 获取两个时间之间的秒数，“end - start” 的秒数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return “end - start” 的秒数
     */
    pastSeconds(start: JDate, end: JDate): JInt;

    // 获取边缘时间点
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 得到指定时间当天的开始时间<br/>
     * 例如：传入"2014-01-03 08:36:21" 返回 "2014-01-03 00:00:00"
     */
    getDayStartTime(date: JDate): JDate;

    /**
     * 得到指定时间当天的截止时间<br/>
     * 例如：传入"2014-01-03 08:36:21" 返回 "2014-01-03 23:59:59"
     */
    getDayEndTime(date: JDate): JDate;

    /**
     * 得到指定时间当小时的开始时间<br/>
     * 例如：传入"2014-01-03 08:36:21" 返回 "2014-01-03 08:00:00"
     */
    getHourStartTime(date: JDate): JDate;

    /**
     * 得到指定时间当小时的截止时间<br/>
     * 例如：传入"2014-01-03 08:36:21" 返回 "2014-01-03 08:59:59"
     */
    getHourEndTime(date: JDate): JDate;

    // 比较时间
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 是否是同一天
     */
    isSameDay(date1: JDate, date2: JDate): JBoolean;

    /**
     * 是否是同一毫秒(同一时刻)
     */
    isSameInstant(date1: JDate, date2: JDate): JBoolean;

    /**
     * 截断时间然后判断是否相等
     */
    truncatedEquals(date1: JDate, date2: JDate, field: JInt): JBoolean;

    /**
     * 截断时间然后比较
     */
    truncatedCompareTo(date1: JDate, date2: JDate, field: JInt): JInt;

    // 时间计算
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 增加年(可以加负数)
     */
    addYears(date: JDate, amount: JInt): JDate;

    /**
     * 增加月(可以加负数)
     */
    addMonths(date: JDate, amount: JInt): JDate;

    /**
     * 增加天(可以加负数)
     */
    addDays(date: JDate, amount: JInt): JDate;

    /**
     * 增加小时(可以加负数)
     */
    addHours(date: JDate, amount: JInt): JDate;

    /**
     * 增加分钟(可以加负数)
     */
    addMinutes(date: JDate, amount: JInt): JDate;

    /**
     * 增加秒(可以加负数)
     */
    addSeconds(date: JDate, amount: JInt): JDate;

    /**
     * 增加毫秒(可以加负数)
     */
    addMilliseconds(date: JDate, amount: JInt): JDate;

    /**
     * 增加周(可以加负数)
     */
    addWeeks(date: JDate, amount: JInt): JDate;

    // 设置时间值
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 设置年
     */
    setYears(date: JDate, amount: JInt): JDate;

    /**
     * 设置月
     */
    setMonths(date: JDate, amount: JInt): JDate;

    /**
     * 设置天
     */
    setDays(date: JDate, amount: JInt): JDate;

    /**
     * 设置小时
     */
    setHours(date: JDate, amount: JInt): JDate;

    /**
     * 设置分钟
     */
    setMinutes(date: JDate, amount: JInt): JDate;

    /**
     * 设置秒值
     */
    setSeconds(date: JDate, amount: JInt): JDate;

    /**
     * 设置毫秒值
     */
    setMilliseconds(date: JDate, amount: JInt): JDate;

    // 时间取值
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 截断时间<br />
     * <pre>
     *  2002-03-28 13:45:01.231 + HOUR      -> 2002-03-28 13:00:00.000
     *  2002-03-28 13:45:01.231 + MONTH     -> 2002-03-01 00:00:00.000
     * </pre>
     */
    truncate(date: JDate, field: JInt): JDate;

    /**
     * 获取日期上限，将字段指定为最重要的字段<br />
     * <pre>
     *  2002-03-28 13:45:01.231 + HOUR      -> 2002-03-28 14:00:00.000
     *  2002-03-28 13:45:01.231 + MONTH     -> 2002-04-01 00:00:00.000
     * </pre>
     */
    ceiling(date: JDate, field: JInt): JDate;
}

declare const DateUtils: DateUtils;
