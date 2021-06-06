package org.clever.graaljs.core.internal.utils;

import org.graalvm.polyglot.proxy.ProxyObject;

import java.util.Map;

/**
 * Java 对象转 Script 对象
 * 作者：lizw <br/>
 * 创建时间：2020/09/08 16:02 <br/>
 */
public class InteropJavaToScriptUtils {

    public static final InteropJavaToScriptUtils Instance = new InteropJavaToScriptUtils();

    private InteropJavaToScriptUtils() {
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------- Map

    /**
     * 把Java Map转成Script Object
     */
    @SuppressWarnings("unchecked")
    public Object convertMap(Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Map) {
                Map<String, Object> valueMap = (Map<String, Object>) value;
                // 递归
                convertMap(valueMap);
                // 不是原生对象就包装一下
                if (!value.getClass().getName().startsWith("com.oracle.truffle.")) {
                    map.put(key, ProxyObject.fromMap(valueMap));
                }
            }
        }
        return ProxyObject.fromMap(map);
    }
}
