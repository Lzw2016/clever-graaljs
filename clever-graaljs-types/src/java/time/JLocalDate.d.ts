/**
 * ISO-8601日历系统中没有时区的日期，如2007-12-03
 */
interface JLocalDate extends JObject, JComparable<JLocalDate> {
    java_time_LocalDate: "java.time.LocalDate";

    /**
     * 获取年份字段
     */
    getYear(): JInt;

    /**
     * 获取从1到12的月份字段
     */
    getMonthValue(): JInt;

    /**
     * 获取“day-of-month”字段
     */
    getDayOfMonth(): JInt;

    /**
     * 获取“day-of-year”字段
     */
    getDayOfYear(): JInt;

    /**
     * 根据ISO proleptic日历系统规则检查年份是否为闰年
     */
    isLeapYear(): JBoolean;

    /**
     * 返回此日期表示的月份长度
     */
    lengthOfMonth(): JInt;

    /**
     * 返回此日期表示的年份长度
     */
    lengthOfYear(): JInt;

    /**
     * 返回更改了年份的{@code LocalDate}的副本
     */
    withYear(year: JInt): JLocalDate;

    /**
     * 返回此LocalDate的一个副本，并更改月份
     */
    withMonth(month: JInt): JLocalDate;

    /**
     * 返回此LocalDate的副本，更改了月份的日期
     */
    withDayOfMonth(dayOfMonth: JInt): JLocalDate;

    /**
     * 返回此LocalDate的副本，更改日期为一年中的某一天
     */
    withDayOfYear(dayOfYear: JInt): JLocalDate;

    /**
     * 返回此LocalDate的副本，并添加指定的年数
     */
    plusYears(yearsToAdd: JLong): JLocalDate;

    /**
     * 返回此LocalDate的副本，并添加指定的月数
     */
    plusMonths(monthsToAdd: JLong): JLocalDate;

    /**
     * 返回此LocalDate的副本，并添加指定的周数
     */
    plusWeeks(weeksToAdd: JLong): JLocalDate;

    /**
     * 返回此LocalDate的副本，并添加指定的天数
     */
    plusDays(daysToAdd: JLong): JLocalDate;

    /**
     * 返回此LocalDate的副本，并减去指定的年数
     */
    minusYears(yearsToSubtract: JLong): JLocalDate;

    /**
     * 返回此LocalDate的副本，并减去指定的月数
     */
    minusMonths(monthsToSubtract: JLong): JLocalDate;

    /**
     * 返回此LocalDate的副本，并减去指定的周数
     */
    minusWeeks(weeksToSubtract: JLong): JLocalDate;

    /**
     * 返回此LocalDate的副本，并减去指定的天数
     */
    minusDays(daysToSubtract: JLong): JLocalDate;

    // /**
    //  * 将此日期与时间组合以创建JLocalDateTime
    //  */
    // atTime(hour: JInt, minute: JInt): JLocalDateTime;
    //
    // /**
    //  * 将此日期与时间组合以创建JLocalDateTime
    //  */
    // atTime(hour: JInt, minute: JInt, second: JInt): JLocalDateTime;
    //
    // /**
    //  * 将此日期与时间组合以创建JLocalDateTime
    //  */
    // atTime(hour: JInt, minute: JInt, second: JInt, nanoOfSecond: JInt): JLocalDateTime;
    //
    // /**
    //  * 将此日期与午夜时间合并，以在此日期开始处创建JLocalDateTime
    //  */
    // atStartOfDay(): JLocalDateTime;

    /**
     * 检查此日期是否在指定日期之后
     */
    isAfter(other: JLocalDate): JBoolean;

    /**
     * 检查此日期是否早于指定日期
     */
    isBefore(other: JLocalDate): JBoolean;
}

