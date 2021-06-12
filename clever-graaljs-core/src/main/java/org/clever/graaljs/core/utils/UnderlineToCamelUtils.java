package org.clever.graaljs.core.utils;

import java.util.*;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/09/10 16:50 <br/>
 */
public class UnderlineToCamelUtils {
    /**
     * 下划线转驼峰格式映射缓存
     *
     * @param mapping 映射缓存
     * @param dataMap 数据
     */
    private static void underlineToCamelMapping(Map<String, String> mapping, Map<String, Object> dataMap) {
        if (mapping == null || dataMap == null) {
            return;
        }
        for (String key : dataMap.keySet()) {
            if (mapping.containsKey(key)) {
                continue;
            }
            mapping.put(key, StrFormatter.underlineToCamel(key));
        }
    }

    /**
     * key下划线转驼峰格式
     *
     * @param dataMap 数据
     * @param mapping 字段映射配置
     */
    public static Map<String, Object> underlineToCamel(Map<String, Object> dataMap, Map<String, String> mapping) {
        if (dataMap == null) {
            return null;
        }
        if (mapping == null || mapping.isEmpty()) {
            mapping = new HashMap<>(dataMap.size());
            underlineToCamelMapping(mapping, dataMap);
        }
        Map<String, Object> result = new LinkedHashMap<>(dataMap.size());
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String key = mapping.getOrDefault(entry.getKey(), entry.getKey());
            result.put(StrFormatter.underlineToCamel(key), entry.getValue());
        }
        return result;
    }

    /**
     * key下划线转驼峰格式
     *
     * @param dataMap 数据
     */
    public static Map<String, Object> underlineToCamel(Map<String, Object> dataMap) {
        return underlineToCamel(dataMap, null);
    }

    /**
     * key下划线转驼峰格式
     *
     * @param dataMapList 数据集合
     * @param mapping     字段映射配置
     */
    public static List<Map<String, Object>> underlineToCamel(List<Map<String, Object>> dataMapList, Map<String, String> mapping) {
        if (dataMapList == null) {
            return null;
        }
        List<Map<String, Object>> result = new ArrayList<>(dataMapList.size());
        for (Map<String, Object> map : dataMapList) {
            if (mapping == null || mapping.isEmpty()) {
                mapping = new HashMap<>(map.size());
            }
            underlineToCamelMapping(mapping, map);
            result.add(underlineToCamel(map, mapping));
        }
        return result;
    }

    /**
     * key下划线转驼峰格式
     *
     * @param dataMapList 数据集合
     */
    public static List<Map<String, Object>> underlineToCamel(List<Map<String, Object>> dataMapList) {
        return underlineToCamel(dataMapList, null);
    }
}
