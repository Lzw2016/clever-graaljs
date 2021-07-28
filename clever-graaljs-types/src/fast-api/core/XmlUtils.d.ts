interface XmlUtils {
    /**
     * 对象序列化成XML字符串
     *
     * @param object 需要序列化xml的对象
     * @return 返回xml
     */
    toXml(object: object): JString;

    /**
     * XML字符串反序列化成对象
     *
     * @param xml XML字符串
     * @return 返回的对象
     */
    fromXml<T extends object>(xml: JString): T;

    /**
     * 当XML里只含有Bean的部分属性时，更新一个已存在Bean，只覆盖该部分的属性
     *
     * @param xml    XML字符串
     * @param object 需要更新的对象
     * @return 操作成功返回true
     */
    update(xml: JString, object: object): JBoolean;

    /**
     * XML字符串转换成Json字符串<br/>
     *
     * @param xml XML字符串
     * @return Json字符串
     */
    xmlToJson(xml: JString): JString;

    /**
     * Json字符串转换成XML字符串<br/>
     *
     * @param json Json字符串
     * @return XML字符串
     */
    jsonToXml(json: JString): JString;
}

declare const XmlUtils: XmlUtils;
