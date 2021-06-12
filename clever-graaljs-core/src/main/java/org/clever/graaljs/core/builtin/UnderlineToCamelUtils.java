package org.clever.graaljs.core.builtin;

import java.util.List;
import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/12 12:25 <br/>
 */
public class UnderlineToCamelUtils {
    public static final UnderlineToCamelUtils Instance = new UnderlineToCamelUtils();

    private UnderlineToCamelUtils() {
    }

    /**
     * key下划线转驼峰格式
     *
     * @param dataMap 数据
     * @param mapping 字段映射配置
     */
    public Map<String, Object> underlineToCamel(Map<String, Object> dataMap, Map<String, String> mapping) {
        return org.clever.graaljs.core.utils.UnderlineToCamelUtils.underlineToCamel(dataMap, mapping);
    }

    /**
     * key下划线转驼峰格式
     *
     * @param dataMap 数据
     */
    public Map<String, Object> underlineToCamel(Map<String, Object> dataMap) {
        return org.clever.graaljs.core.utils.UnderlineToCamelUtils.underlineToCamel(dataMap);
    }

    /**
     * key下划线转驼峰格式
     *
     * @param dataMapList 数据集合
     * @param mapping     字段映射配置
     */
    public List<Map<String, Object>> underlineToCamel(List<Map<String, Object>> dataMapList, Map<String, String> mapping) {
        return org.clever.graaljs.core.utils.UnderlineToCamelUtils.underlineToCamel(dataMapList, mapping);
    }

    /**
     * key下划线转驼峰格式
     *
     * @param dataMapList 数据集合
     */
    public List<Map<String, Object>> underlineToCamel(List<Map<String, Object>> dataMapList) {
        return org.clever.graaljs.core.utils.UnderlineToCamelUtils.underlineToCamel(dataMapList);
    }
}
