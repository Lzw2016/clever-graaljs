/**
 * 时间线上的瞬时点
 */
interface JInstant extends JObject, JComparable<JInstant> {
    java_time_Instant: "java.time.Instant";

    /**
     * 获取Java纪元1970-01-01T00:00:00Z的秒数
     */
    getEpochSecond(): JLong;

    /**
     * 获取从第二个开始沿时间线稍后的纳秒数
     */
    getNano(): JInt;

    /**
     * 返回添加了指定持续时间（以秒为单位）的此瞬间的副本
     */
    plusSeconds(secondsToAdd: JLong): JInstant;

    /**
     * 返回添加了指定持续时间（毫秒）的此瞬间的副本
     */
    plusMillis(millisToAdd: JLong): JInstant;

    /**
     * 返回此瞬间的副本，并添加指定的持续时间（以纳秒为单位）
     */
    plusNanos(nanosToAdd: JLong): JInstant;

    /**
     * 返回此瞬间的副本，并减去指定的持续时间（以秒为单位）
     */
    minusSeconds(secondsToSubtract: JLong): JInstant;

    /**
     * 返回此瞬间的副本，并减去指定的持续时间（毫秒）
     */
    minusMillis(millisToSubtract: JLong): JInstant;

    /**
     * 返回以纳秒为单位减去指定持续时间的该瞬间的副本
     */
    minusNanos(nanosToSubtract: JLong): JInstant;

    /**
     * 将此瞬间转换为1970-01-01T00:00:00Z的历元的毫秒数
     */
    toEpochMilli(): JLong;

    /**
     * 检查此瞬间是否在指定瞬间之后
     */
    isAfter(otherInstant: JInstant): JBoolean;

    /**
     * 检查此瞬间是否在指定瞬间之前
     */
    isBefore(otherInstant: JInstant): JBoolean;
}

