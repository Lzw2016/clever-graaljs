/**
 * 时区ID，如 Europe/Paris
 */
interface JZoneId extends JObject {
    java_time_ZoneId: "java.time.ZoneId";

    //-----------------------------------------------------------------------
    /**
     * 获取唯一的时区ID
     */
    getId(): JString;

    /**
     * 规范化时区ID，尽可能返回 ZoneOffset
     */
    normalized(): JZoneId;
}

