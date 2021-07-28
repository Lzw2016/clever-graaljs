/**
 * ISO-8601日历系统中没有时区的时间，如 10:15:30
 */
interface JLocalTime extends JObject, JComparable<JLocalTime> {
    java_time_LocalTime: "java.time.LocalTime";

    /**
     * 获取hour-of-day值
     */
    getHour(): JInt;

    /**
     * 获取minute-of-hour值
     */
    getMinute(): JInt;

    /**
     * 获取second-of-minute值
     */
    getSecond(): JInt;

    /**
     * 获取nano-of-second值
     */
    getNano(): JInt;

    /**
     * 返回此LocalTime的副本，并更改一天中的小时数
     */
    withHour(hour: JInt): JLocalTime;

    /**
     * 返回此LocalTime的副本，并更改小时分钟数
     */
    withMinute(minute: JInt): JLocalTime;

    /**
     * 返回此LocalTime的副本，更改秒数
     */
    withSecond(second: JInt): JLocalTime;

    /**
     * 返回此LocalTime的副本，其nano值为秒
     */
    withNano(nanoOfSecond: JInt): JLocalTime;

    /**
     * 返回此LocalTime的副本，并添加指定的小时数
     */
    plusHours(hoursToAdd: JLong): JLocalTime;

    /**
     * 返回此LocalTime的副本，并添加指定的分钟数
     */
    plusMinutes(minutesToAdd: JLong): JLocalTime;

    /**
     * 返回添加了指定秒数的LocalTime的副本
     */
    plusSeconds(secondsToAdd: JLong): JLocalTime;

    /**
     * 返回添加了指定纳秒数的LocalTime的副本
     */
    plusNanos(nanosToAdd: JLong): JLocalTime;

    /**
     * 返回此LocalTime的副本，并减去指定的小时数
     */
    minusHours(hoursToSubtract: JLong): JLocalTime;

    /**
     * 返回此LocalTime的副本，并减去指定的分钟数
     */
    minusMinutes(minutesToSubtract: JLong): JLocalTime;

    /**
     * 返回此LocalTime的副本，并减去指定的秒数
     */
    minusSeconds(secondsToSubtract: JLong): JLocalTime;

    /**
     * 返回此LocalTime的副本，并减去指定的纳秒数
     */
    minusNanos(nanosToSubtract: JLong): JLocalTime;

    //-----------------------------------------------------------------------
    /**
     * 将此时间与日期组合以创建JLocalDateTime
     */
    atDate(date: JLocalDate): JLocalDateTime;

    /**
     * 将时间提取为每天的秒数
     */
    toSecondOfDay(): JInt;

    /**
     * 将时间提取为一天中的纳秒
     */
    toNanoOfDay(): JLong;

    /**
     * 检查此时间是否在指定时间之后
     */
    isAfter(other: JLocalTime): JBoolean;

    /**
     * 检查此时间是否早于指定时间
     */
    isBefore(other: JLocalTime): JBoolean;
}

