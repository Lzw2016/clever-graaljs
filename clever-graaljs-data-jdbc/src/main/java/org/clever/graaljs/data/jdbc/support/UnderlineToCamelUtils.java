package org.clever.graaljs.data.jdbc.support;

import org.clever.graaljs.core.utils.StrFormatter;

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
     * @param map     数据
     */
    public static void underlineToCamelMapping(Map<String, String> mapping, Map<String, Object> map) {
        if (mapping == null || map == null) {
            return;
        }
        for (String key : map.keySet()) {
            if (mapping.containsKey(key)) {
                continue;
            }
            mapping.put(key, StrFormatter.underlineToCamel(key));
        }
    }

    /**
     * Key 下划线转驼峰格式
     *
     * @param mapping 映射缓存
     */
    public static Map<String, Object> underlineToCamel(Map<String, Object> map, Map<String, String> mapping) {
        if (map == null) {
            return null;
        }
        if (mapping == null || mapping.isEmpty()) {
            mapping = new HashMap<>(map.size());
            underlineToCamelMapping(mapping, map);
        }
        Map<String, Object> result = new LinkedHashMap<>(map.size());
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = mapping.getOrDefault(entry.getKey(), entry.getKey());
            result.put(StrFormatter.underlineToCamel(key), entry.getValue());
        }
        return result;
    }

    /**
     * Key 下划线转驼峰格式
     */
    public static Map<String, Object> underlineToCamel(Map<String, Object> map) {
        return underlineToCamel(map, null);
    }

    /**
     * Key 下划线转驼峰格式
     */
    public static List<Map<String, Object>> underlineToCamel(List<Map<String, Object>> mapList) {
        if (mapList == null) {
            return null;
        }
        Map<String, String> mapping = null;
        List<Map<String, Object>> result = new ArrayList<>(mapList.size());
        for (Map<String, Object> map : mapList) {
            if (mapping == null) {
                mapping = new HashMap<>(map.size());
            }
            underlineToCamelMapping(mapping, map);
            result.add(underlineToCamel(map, mapping));
        }
        return result;
    }
}
