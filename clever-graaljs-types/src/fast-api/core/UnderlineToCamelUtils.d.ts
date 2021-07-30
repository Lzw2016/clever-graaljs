/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/12 12:25 <br/>
 */
interface UnderlineToCamelUtils extends JObject {
    /**
     * key下划线转驼峰格式
     *
     * @param dataMap 数据
     * @param mapping 字段映射配置
     */
    underlineToCamel(dataMap: JMap<JString, any>, mapping: JMap<JString, JString>): JMap<JString, any>;

    /**
     * key下划线转驼峰格式
     *
     * @param dataMap 数据
     */
    underlineToCamel(dataMap: JMap<JString, any>): JMap<JString, any>;

    /**
     * key下划线转驼峰格式
     *
     * @param dataMapList 数据集合
     * @param mapping     字段映射配置
     */
    underlineToCamel(dataMapList: JList<JMap<JString, any>>, mapping: JMap<JString, JString>): JList<JMap<JString, any>>;

    /**
     * key下划线转驼峰格式
     *
     * @param dataMapList 数据集合
     */
    underlineToCamel(dataMapList: JList<JMap<JString, any>>): JList<JMap<JString, any>>;
}

declare const UnderlineToCamelUtils: UnderlineToCamelUtils;
