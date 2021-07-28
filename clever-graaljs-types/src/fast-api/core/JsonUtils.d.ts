interface JsonUtils {
    /**
     * Object可以是POJO，也可以是Collection或数组。 如果对象为Null, 返回"null". 如果集合为空集合, 返回"[]".<br/>
     *
     * @param obj 需要序列化的对象
     * @return 序列化后的Json字符串
     */
    toJson(obj: object): JString

    /**
     * 输出JSON格式数据.
     */
    toJsonP(functionName: JString, obj: object): JString

    /**
     * 把Json字符串转换成Map对象
     */
    toMap<K extends JString, V extends any>(json: JString): JMap<K, V>

    /**
     * 当JSON里只含有Bean的部分属性时，更新一个已存在Bean，只覆盖该部分的属性.
     *
     * @param json   Json字符串
     * @param obj 需要更新的对象
     * @return 操作成功返回true
     */
    update(json: JString, obj: object): JBoolean

    /**
     * XML字符串转换成Json字符串<br/>
     *
     * @param xml XML字符串
     * @return Json字符串
     */
    xmlToJson(xml: JString): JString

    /**
     * Json字符串转换成XML字符串<br/>
     *
     * @param json Json字符串
     * @return XML字符串
     */
    jsonToXml(json: JString): JString
}

declare const JsonUtils: JsonUtils;
