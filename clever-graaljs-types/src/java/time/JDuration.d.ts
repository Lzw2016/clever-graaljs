/**
 * 表示持续时间
 */
interface JDuration extends JObject, JComparable<JDuration> {
    java_time_Duration: "java.time.Duration";

    /**
     * 检查此持续时间是否为零长度
     */
    isZero(): JBoolean;

    /**
     * 检查此持续时间是否为负，不包括零
     */
    isNegative(): JBoolean;

    /**
     * 获取整数值形式的 SECONDS 字段的值，如果不存在，则值为 0
     */
    getSeconds(): JLong;

    /**
     * 获取此持续时间内秒内的纳秒数
     */
    getNano(): JInt;

    /**
     * 返回具有指定秒数的此持续时间的副本
     */
    withSeconds(seconds: JLong): JDuration;

    /**
     * 返回此持续时间的副本，并指定纳秒数
     */
    withNanos(nanoOfSecond: JInt): JDuration;

    /**
     * 返回添加了指定持续时间的此持续时间的副本
     */
    plus(duration: JDuration): JDuration;

    /**
     * 返回此持续时间的副本，并添加指定的持续时间（以标准24小时为单位）
     */
    plusDays(daysToAdd: JLong): JDuration;

    /**
     * 返回此持续时间的副本，并添加指定的持续时间（以小时为单位）
     */
    plusHours(hoursToAdd: JLong): JDuration;

    /**
     * 返回此持续时间的副本，并添加指定的持续时间（以分钟为单位）
     */
    plusMinutes(minutesToAdd: JLong): JDuration;

    /**
     * 返回此持续时间的副本，并添加指定的持续时间（以秒为单位）
     */
    plusSeconds(secondsToAdd: JLong): JDuration;

    /**
     * 返回此持续时间的副本，并添加指定的持续时间（以毫秒为单位）
     */
    plusMillis(millisToAdd: JLong): JDuration;

    /**
     * 返回此持续时间的副本，并添加指定的持续时间（以纳秒为单位）
     */
    plusNanos(nanosToAdd: JLong): JDuration;

    /**
     * 返回此持续时间的副本，并减去指定的持续时间
     */
    minus(duration: JDuration): JDuration;

    //-----------------------------------------------------------------------
    /**
     * 返回此持续时间的副本，并减去指定的持续时间（以标准24小时为单位）
     */
    minusDays(daysToSubtract: JLong): JDuration;

    /**
     * 返回此持续时间的副本，并减去指定的持续时间（以小时为单位）
     */
    minusHours(hoursToSubtract: JLong): JDuration;

    /**
     * 返回此持续时间的副本，并减去指定的持续时间（以分钟为单位）
     */
    minusMinutes(minutesToSubtract: JLong): JDuration;

    /**
     * 返回此持续时间的副本，并减去指定的持续时间（以秒为单位）
     */
    minusSeconds(secondsToSubtract: JLong): JDuration;

    /**
     * 返回此持续时间的副本，并减去指定的持续时间（毫秒）
     */
    minusMillis(millisToSubtract: JLong): JDuration;

    /**
     * 返回此持续时间的副本，并减去指定的持续时间（以纳秒为单位）
     */
    minusNanos(nanosToSubtract: JLong): JDuration;

    /**
     * 返回此持续时间乘以标量的副本
     */
    multipliedBy(multiplicand: JLong): JDuration;

    /**
     * 返回此持续时间除以指定值的副本
     */
    dividedBy(divisor: JLong): JDuration;

    /**
     * 返回指定持续时间在此持续时间内发生的整次次数
     */
    dividedBy(divisor: JDuration): JLong;

    /**
     * 返回长度为负数的此持续时间的副本
     */
    negated(): JDuration;

    /**
     * 返回此持续时间的正长度副本
     */
    abs(): JDuration;

    /**
     * 获取此持续时间的天数
     */
    toDays(): JLong;

    /**
     * 获取此持续时间内的小时数
     */
    toHours(): JLong;

    /**
     * 获取此持续时间内的分钟数
     */
    toMinutes(): JLong;

    /**
     * 获取此持续时间内的秒数
     */
    toSeconds(): JLong;

    /**
     * 将此持续时间转换为总长度（毫秒）
     */
    toMillis(): JLong;

    /**
     * 将此持续时间转换为以long表示的总长度（以纳秒为单位）
     */
    toNanos(): JLong;

    /**
     * 提取持续时间中的天数
     */
    toDaysPart(): JLong;

    /**
     * 提取持续时间中的小时数部分
     */
    toHoursPart(): JInt;

    /**
     * 提取持续时间中的分钟数部分
     */
    toMinutesPart(): JInt;

    /**
     * 提取持续时间中的秒数部分
     */
    toSecondsPart(): JInt;

    /**
     * 提取持续时间的毫秒数部分
     */
    toMillisPart(): JInt;

    /**
     * 在持续时间的几秒钟内获取纳秒部分
     */
    toNanosPart(): JInt;
}
