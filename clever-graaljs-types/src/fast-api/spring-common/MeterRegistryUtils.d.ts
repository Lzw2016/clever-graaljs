/**
 * 作者：lizw <br/>
 * 创建时间：2021/08/05 10:21 <br/>
 */
interface MeterRegistryUtils extends JObject {
    /**
     * 获取监控指标收集器
     */
    getMeterRegistry(): MeterRegistry;

    // ---------------------------------------------------------------------------------------------------------------------------------------- gauge
    /**
     * 注册一个瞬时指标值
     *
     * @param number      指标值
     * @param name        指标名称
     * @param tags        指标tag
     * @param description 指标描述
     * @param unit        指标单位
     */
    gauge(number: JDouble, name: JString, tags: JMap<JString, JString>, description: JString, unit: JString): void;

    /**
     * 注册一个瞬时指标值
     *
     * @param number      指标值
     * @param name        指标名称
     * @param tags        指标tag
     * @param description 指标描述
     */
    gauge(number: JDouble, name: JString, tags: JMap<JString, JString>, description: JString): void;

    /**
     * 注册一个瞬时指标值
     *
     * @param number 指标值
     * @param name   指标名称
     * @param tags   指标tag
     */
    gauge(number: JDouble, name: JString, tags: JMap<JString, JString>): void;

    /**
     * 注册一个瞬时指标值
     *
     * @param number 指标值
     * @param name   指标名称
     */
    gauge(number: JDouble, name: JString): void;

    // ---------------------------------------------------------------------------------------------------------------------------------------- counter
    /**
     * 创建一个累计指标值构造器
     *
     * @param name 指标名称
     */
    counterBuilder(name: JString): Counter.Builder;

    /**
     * 创建一个累计指标值
     *
     * @param name 指标名称
     * @param tags 指标tag
     * @return 累计指标值对象
     */
    counter(name: JString, tags: JMap<JString, JString>): Counter;

    /**
     * 创建一个累计指标值
     *
     * @param name 指标名称
     * @return 累计指标值对象
     */
    counter(name: JString): Counter;

    // ---------------------------------------------------------------------------------------------------------------------------------------- other

    /**
     * 得到指标Tags
     */
    toTags(tags: JMap<JString, JString>): JList<MeterTag>;
}

declare const MeterRegistryUtils: MeterRegistryUtils;
