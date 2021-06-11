package org.clever.graaljs.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类, 继承org.apache.commons.lang3.time.DateUtils类<br/>
 * 使用了joda-time时间处理框架<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-4-27 21:57 <br/>
 */
@Slf4j
public class DateTimeUtils extends DateUtils {

    public static final String HH_mm_ss = "HH:mm:ss";
    public static final String yyyy_MM_dd = "yyyy-MM-dd";
    public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";

    /**
     * 定义可能出现的时间日期格式<br />
     * 参考 https://blog.csdn.net/solocoder/article/details/83655885
     */
    private static final String[] parsePatterns = {
            yyyy_MM_dd, yyyy_MM_dd_HH_mm_ss, yyyy_MM_dd_HH_mm,
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
            "yyyyMMdd", "yyyyMMdd HH:mm:ss", "yyyyMMdd HH:mm",
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    };

    /**
     * 得到当前时间的日期字符串，如：2016-4-27、2016-4-27 21:57:15<br/>
     *
     * @param pattern 日期格式字符串，如："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getCurrentDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到当前时间的日期字符串，格式（yyyy-MM-dd）<br/>
     */
    public static String getCurrentDate() {
        return getCurrentDate("yyyy-MM-dd");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     *
     * @return 当前时间字符串，如：12:14:21
     */
    public static String getCurrentTime() {
        return DateFormatUtils.format(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     *
     * @return 当前时间字符串，如：2014-01-02 10:14:10
     */
    public static String getCurrentDateTime() {
        return DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 根据时间数，得到日期字符串<br/>
     *
     * @param dateTime 时间数，可通过System.currentTimeMillis()得到
     * @param pattern  时间格式字符串，如："yyyy-MM-dd HH:mm:ss"，默认是：yyyy-MM-dd
     * @return 时间字符串
     */
    public static String getDate(long dateTime, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = "yyyy-MM-dd";
        }
        return DateFormatUtils.format(new Date(dateTime), pattern);
    }

    /**
     * 根据时间数，得到日期字符串，格式：yyyy-MM-dd HH:mm:ss<br/>
     *
     * @param dateTime 时间数，可通过System.currentTimeMillis()得到
     * @return 时间字符串，如：2014-03-02 03:12:03
     */
    public static String getDate(long dateTime) {
        return DateFormatUtils.format(new Date(dateTime), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     *
     * @return 当前年字符串，如：2014
     */
    public static String getYear() {
        return DateFormatUtils.format(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     *
     * @return 当前月字符串，如：02
     */
    public static String getMonth() {
        return DateFormatUtils.format(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     *
     * @return 当前天字符串，如：21
     */
    public static String getDay() {
        return DateFormatUtils.format(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     *
     * @return 当前日期是星期几，如：5
     */
    public static String getWeek() {
        return DateFormatUtils.format(new Date(), "E");
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd）
     *
     * @param date    日期对象
     * @param pattern 日期格式，如："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatToString(Date date, String pattern) {
        String formatDate;
        if (StringUtils.isNotBlank(pattern)) {
            formatDate = DateFormatUtils.format(date, pattern);
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     *
     * @param date 日期对象
     * @return 日期格式字符串，如：2015-03-01 10:21:14
     */
    public static String formatToString(Date date) {
        return formatToString(date, "yyyy-MM-dd HH:mm:ss");
    }

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
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        if (str instanceof Long || str instanceof Integer) {
            long time = (long) str;
            return new Date(time);
        }
        if (String.valueOf(str).length() != 8 && NumberUtils.isDigits(String.valueOf(str))) {
            long time = NumberUtils.toLong(String.valueOf(str), -1L);
            if (time != -1L) {
                return new Date(time);
            }
        }
        try {
            return parseDate(String.valueOf(str), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取两个时间之间的年数，“end - start” 的年数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return “end - start” 的年数
     */
    public static int pastYears(Date start, Date end) {
        DateTime startDt = new DateTime(start);
        DateTime endDt = new DateTime(end);
        return Years.yearsBetween(startDt, endDt).getYears();
    }

    /**
     * 获取两个时间之间的天数，“end - start” 的天数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return “end - start” 的天数
     */
    public static int pastDays(Date start, Date end) {
        DateTime startDt = new DateTime(start);
        DateTime endDt = new DateTime(end);
        return Days.daysBetween(startDt, endDt).getDays();
    }

    /**
     * 获取两个时间之间的小时数，“end - start” 的小时数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return “end - start” 的小时数
     */
    public static int pastHours(Date start, Date end) {
        DateTime startDt = new DateTime(start);
        DateTime endDt = new DateTime(end);
        return Hours.hoursBetween(startDt, endDt).getHours();
    }

    /**
     * 获取两个时间之间的分钟数，“end - start” 的分钟数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return “end - start” 的分钟数
     */
    public static int pastMinutes(Date start, Date end) {
        DateTime startDt = new DateTime(start);
        DateTime endDt = new DateTime(end);
        return Minutes.minutesBetween(startDt, endDt).getMinutes();
    }

    /**
     * 获取两个时间之间的秒数，“end - start” 的秒数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return “end - start” 的秒数
     */
    public static int pastSeconds(Date start, Date end) {
        DateTime startDt = new DateTime(start);
        DateTime endDt = new DateTime(end);
        return Seconds.secondsBetween(startDt, endDt).getSeconds();
    }

    /**
     * 得到指定时间当天的开始时间<br/>
     * 例如：传入"2014-01-03 08:36:21" 返回 "2014-01-03 00:00:00"
     */
    public static Date getDayStartTime(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.parse(formatToString(date, "yyyy-MM-dd") + " 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 得到指定时间当天的截止时间<br/>
     * 例如：传入"2014-01-03 08:36:21" 返回 "2014-01-03 23:59:59"
     */
    public static Date getDayEndTime(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.parse(formatToString(date, "yyyy-MM-dd") + " 23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 得到指定时间当小时的开始时间<br/>
     * 例如：传入"2014-01-03 08:36:21" 返回 "2014-01-03 08:00:00"
     */
    public static Date getHourStartTime(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.parse(formatToString(date, "yyyy-MM-dd HH") + ":00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 得到指定时间当小时的截止时间<br/>
     * 例如：传入"2014-01-03 08:36:21" 返回 "2014-01-03 08:59:59"
     */
    public static Date getHourEndTime(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.parse(formatToString(date, "yyyy-MM-dd HH") + ":59:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
