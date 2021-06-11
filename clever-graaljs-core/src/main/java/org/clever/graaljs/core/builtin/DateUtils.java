package org.clever.graaljs.core.builtin;


import org.clever.graaljs.core.utils.DateTimeUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/28 22:32 <br/>
 */
public class DateUtils {
    public static final DateUtils Instance = new DateUtils();

    private DateUtils() {
    }

    /**
     * 获取当前时间
     */
    public Date now() {
        return new Date();
    }

    /**
     * 获取当前时间撮
     */
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    // 当前时间字符串
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 得到当前时间的日期字符串，如：2016-4-27、2016-4-27 21:57:15<br/>
     *
     * @param pattern 日期格式字符串，如："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public String getCurrentDate(String pattern) {
        return DateTimeUtils.getCurrentDate(pattern);
    }

    /**
     * 得到当前时间的日期字符串，格式（yyyy-MM-dd）<br/>
     */
    public String getCurrentDate() {
        return DateTimeUtils.getCurrentDate();
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     *
     * @return 当前时间字符串，如：12:14:21
     */
    public String getCurrentTime() {
        return DateTimeUtils.getCurrentTime();
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     *
     * @return 当前时间字符串，如：2014-01-02 10:14:10
     */
    public String getCurrentDateTime() {
        return DateTimeUtils.getCurrentDateTime();
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     *
     * @return 当前年字符串，如：2014
     */
    public String getYear() {
        return DateTimeUtils.getYear();
    }

    /**
     * 得到当前月份字符串 格式（MM）
     *
     * @return 当前月字符串，如：02
     */
    public String getMonth() {
        return DateTimeUtils.getMonth();
    }

    /**
     * 得到当天字符串 格式（dd）
     *
     * @return 当前天字符串，如：21
     */
    public String getDay() {
        return DateTimeUtils.getDay();
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     *
     * @return 当前日期是星期几，如：5
     */
    public String getWeek() {
        return DateTimeUtils.getWeek();
    }

    // 时间格式化
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd）
     *
     * @param date    日期对象
     * @param pattern 日期格式，如："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public String formatToString(Date date, String pattern) {
        return DateTimeUtils.formatToString(date, pattern);
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     *
     * @param date 日期对象
     * @return 日期格式字符串，如：2015-03-01 10:21:14
     */
    public String formatToString(Date date) {
        return DateTimeUtils.formatToString(date);
    }

    // 解析时间得到字符串
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 根据时间数，得到日期字符串<br/>
     *
     * @param dateTime 时间数，可通过System.currentTimeMillis()得到
     * @param pattern  时间格式字符串，如："yyyy-MM-dd HH:mm:ss"，默认是：yyyy-MM-dd
     * @return 时间字符串
     */
    public String getDate(long dateTime, String pattern) {
        return DateTimeUtils.getDate(dateTime, pattern);
    }

    /**
     * 根据时间数，得到日期字符串，格式：yyyy-MM-dd HH:mm:ss<br/>
     *
     * @param dateTime 时间数，可通过System.currentTimeMillis()得到
     * @return 时间字符串，如：2014-03-02 03:12:03
     */
    public String getDate(long dateTime) {
        return DateTimeUtils.getDate(dateTime);
    }

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
    public Date parseDate(Object str) {
        return DateTimeUtils.parseDate(str);
    }

    /**
     * 把字符串解析成时间对象
     */
    public Date parseDate(final String str, final String... parsePatterns) throws ParseException {
        return DateTimeUtils.parseDate(str, parsePatterns);
    }

    // 时间差
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 获取两个时间之间的年数，“end - start” 的年数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return “end - start” 的年数
     */
    public int pastYears(Date start, Date end) {
        return DateTimeUtils.pastYears(start, end);
    }

    /**
     * 获取两个时间之间的天数，“end - start” 的天数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return “end - start” 的天数
     */
    public int pastDays(Date start, Date end) {
        return DateTimeUtils.pastDays(start, end);
    }

    /**
     * 获取两个时间之间的小时数，“end - start” 的小时数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return “end - start” 的小时数
     */
    public int pastHours(Date start, Date end) {
        return DateTimeUtils.pastHours(start, end);
    }

    /**
     * 获取两个时间之间的分钟数，“end - start” 的分钟数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return “end - start” 的分钟数
     */
    public int pastMinutes(Date start, Date end) {
        return DateTimeUtils.pastMinutes(start, end);
    }

    /**
     * 获取两个时间之间的秒数，“end - start” 的秒数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return “end - start” 的秒数
     */
    public int pastSeconds(Date start, Date end) {
        return DateTimeUtils.pastSeconds(start, end);
    }

    // 获取边缘时间点
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 得到指定时间当天的开始时间<br/>
     * 例如：传入"2014-01-03 08:36:21" 返回 "2014-01-03 00:00:00"
     */
    public Date getDayStartTime(Date date) {
        return DateTimeUtils.getDayStartTime(date);
    }

    /**
     * 得到指定时间当天的截止时间<br/>
     * 例如：传入"2014-01-03 08:36:21" 返回 "2014-01-03 23:59:59"
     */
    public Date getDayEndTime(Date date) {
        return DateTimeUtils.getDayEndTime(date);
    }

    /**
     * 得到指定时间当小时的开始时间<br/>
     * 例如：传入"2014-01-03 08:36:21" 返回 "2014-01-03 08:00:00"
     */
    public Date getHourStartTime(Date date) {
        return DateTimeUtils.getHourStartTime(date);
    }

    /**
     * 得到指定时间当小时的截止时间<br/>
     * 例如：传入"2014-01-03 08:36:21" 返回 "2014-01-03 08:59:59"
     */
    public Date getHourEndTime(Date date) {
        return DateTimeUtils.getHourEndTime(date);
    }

    // 比较时间
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 是否是同一天
     */
    public boolean isSameDay(final Date date1, final Date date2) {
        return DateTimeUtils.isSameDay(date1, date2);
    }

    /**
     * 是否是同一毫秒(同一时刻)
     */
    public boolean isSameInstant(final Date date1, final Date date2) {
        return DateTimeUtils.isSameInstant(date1, date2);
    }

    /**
     * 截断时间然后判断是否相等
     */
    public boolean truncatedEquals(final Date date1, final Date date2, final int field) {
//        Calendar.YEAR
//        Calendar.MONTH
//        Calendar.DAY_OF_MONTH
//        Calendar.HOUR_OF_DAY
//        Calendar.MINUTE
//        Calendar.SECOND
//        Calendar.MILLISECOND
        return DateTimeUtils.truncatedEquals(date1, date2, field);
    }

    /**
     * 截断时间然后比较
     */
    public int truncatedCompareTo(final Date date1, final Date date2, final int field) {
        return DateTimeUtils.truncatedCompareTo(date1, date2, field);
    }

    // 时间计算
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 增加年(可以加负数)
     */
    public Date addYears(final Date date, final int amount) {
        return DateTimeUtils.addYears(date, amount);
    }

    /**
     * 增加月(可以加负数)
     */
    public Date addMonths(final Date date, final int amount) {
        return DateTimeUtils.addMonths(date, amount);
    }

    /**
     * 增加天(可以加负数)
     */
    public Date addDays(final Date date, final int amount) {
        return DateTimeUtils.addDays(date, amount);
    }

    /**
     * 增加小时(可以加负数)
     */
    public Date addHours(final Date date, final int amount) {
        return DateTimeUtils.addHours(date, amount);
    }

    /**
     * 增加分钟(可以加负数)
     */
    public Date addMinutes(final Date date, final int amount) {
        return DateTimeUtils.addMinutes(date, amount);
    }

    /**
     * 增加秒(可以加负数)
     */
    public Date addSeconds(final Date date, final int amount) {
        return DateTimeUtils.addSeconds(date, amount);
    }

    /**
     * 增加毫秒(可以加负数)
     */
    public Date addMilliseconds(final Date date, final int amount) {
        return DateTimeUtils.addMilliseconds(date, amount);
    }

    /**
     * 增加周(可以加负数)
     */
    public Date addWeeks(final Date date, final int amount) {
        return DateTimeUtils.addWeeks(date, amount);
    }

    // 设置时间值
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 设置年
     */
    public Date setYears(final Date date, final int amount) {
        return DateTimeUtils.setYears(date, amount);
    }

    /**
     * 设置月
     */
    public Date setMonths(final Date date, final int amount) {
        return DateTimeUtils.setMonths(date, amount);
    }

    /**
     * 设置天
     */
    public Date setDays(final Date date, final int amount) {
        return DateTimeUtils.setDays(date, amount);
    }

    /**
     * 设置小时
     */
    public Date setHours(final Date date, final int amount) {
        return DateTimeUtils.setHours(date, amount);
    }

    /**
     * 设置分钟
     */
    public Date setMinutes(final Date date, final int amount) {
        return DateTimeUtils.setMinutes(date, amount);
    }

    /**
     * 设置秒值
     */
    public Date setSeconds(final Date date, final int amount) {
        return DateTimeUtils.setSeconds(date, amount);
    }

    /**
     * 设置毫秒值
     */
    public Date setMilliseconds(final Date date, final int amount) {
        return DateTimeUtils.setMilliseconds(date, amount);
    }

    // 时间取值
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 截断时间<br />
     * <pre>
     *  2002-03-28 13:45:01.231 + HOUR      -> 2002-03-28 13:00:00.000
     *  2002-03-28 13:45:01.231 + MONTH     -> 2002-03-01 00:00:00.000
     * </pre>
     */
    public Date truncate(final Date date, final int field) {
        return DateTimeUtils.truncate(date, field);
    }

    /**
     * 获取日期上限，将字段指定为最重要的字段<br />
     * <pre>
     *  2002-03-28 13:45:01.231 + HOUR      -> 2002-03-28 14:00:00.000
     *  2002-03-28 13:45:01.231 + MONTH     -> 2002-04-01 00:00:00.000
     * </pre>
     */
    public Date ceiling(final Date date, final int field) {
        return DateTimeUtils.ceiling(date, field);
    }
}
