package org.clever.graaljs.core.internal.utils;

import com.oracle.truffle.api.interop.TruffleObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.clever.graaljs.core.internal.GraalInterop;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyArray;
import org.graalvm.polyglot.proxy.ProxyObject;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Script 对象转 Java 对象
 * 作者：lizw <br/>
 * 创建时间：2020/08/03 10:42 <br/>
 */
@Slf4j
public class InteropScriptToJavaUtils {
    public static final int Default_Deep = 16;

    public static final InteropScriptToJavaUtils Instance = new InteropScriptToJavaUtils();

    private InteropScriptToJavaUtils() {
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------- Object

    /**
     * 把JavaScript对象转换成Java对象(深度转换) <br />
     *
     * @param object JavaScript对象
     * @param deep   转换深度值(应该大于等于1)
     */
    public Object deepToJavaObject(Object object, int deep) {
        if (deep <= 0) {
            return object;
        }
        if (object == null) {
            return null;
        }
        Object res = toJavaObjectForBase(object);
        // 转换成功
        if (res != object) {
            return res;
        }
        Value value = toValue(object);
        // 不是 Value 类型
        if (value == null) {
            return object;
        }
        // 是一个 Value Function 对象
        if (value.canExecute()) {
            return object;
        }
        // 数组, 对象
        if (value.hasArrayElements()) {
            long size = value.getArraySize();
            if (size > Integer.MAX_VALUE) {
                throw new ClassCastException("数组 arg.length=" + size + " 太长无法转换");
            }
            Object[] array = new Object[(int) size];
            for (int i = 0; i < size; i++) {
                Object tmp = toJavaObjectForBase(value.getArrayElement(i));
                array[i] = deepToJavaObject(tmp, deep - 1);
            }
            return array;
        } else {
            Set<String> keys = value.getMemberKeys();
            Map<String, Object> map = new HashMap<>(keys.size());
            for (String key : keys) {
                Object tmp = toJavaObjectForBase(value.getMember(key));
                map.put(key, deepToJavaObject(tmp, deep - 1));
            }
            return map;
        }
    }

    /**
     * 把JavaScript对象转换成Java对象(深度转换，深度值为16) <br />
     *
     * @param object JavaScript对象
     */
    public Object deepToJavaObject(Object object) {
        return deepToJavaObject(object, Default_Deep);
    }

    /**
     * 把JavaScript对象转换成Java对象 <br />
     * 1. 只考虑简单对象和“数组”、“Object对象” <br />
     * 2. 只做浅转换(一层转换) <br />
     *
     * @param object JavaScript对象
     */
    public Object toJavaObject(Object object) {
        return deepToJavaObject(object, 1);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------- Map

    /**
     * 深度装换Map
     *
     * @param map  map对象
     * @param deep 转换深度值(应该大于等于1)
     */
    public Map<String, Object> deepConvertMap(Map<String, Object> map, int deep) {
        if (map == null) {
            return null;
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object object = entry.getValue();
            object = deepToJavaObject(object, deep);
            map.put(key, object);
        }
        return map;
    }

    /**
     * 深度装换Map(深度转换，深度值为16)
     *
     * @param map map对象
     */
    public Map<String, Object> deepConvertMap(Map<String, Object> map) {
        return deepConvertMap(map, Default_Deep);
    }

    /**
     * 装换Map(只做一层装换)
     */
    public Map<String, Object> convertMap(Map<String, Object> map) {
        return deepConvertMap(map, 1);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------- List

    /**
     * 深度装换List
     *
     * @param list list对象
     * @param deep 转换深度值(应该大于等于1)
     */
    @SuppressWarnings("unchecked")
    public List<?> deepConvertList(List<?> list, int deep) {
        if (list == null) {
            return null;
        }
        List<Object> res = new ArrayList<>(list.size());
        for (Object item : list) {
            if (item instanceof Map) {
                res.add(deepConvertMap((Map<String, Object>) item, deep));
            } else {
                res.add(deepToJavaObject(item, deep));
            }
        }
        return res;
    }

    /**
     * 深度装换List(深度转换，深度值为16)
     */
    public List<?> deepConvertList(List<?> list) {
        return deepConvertList(list, Default_Deep);
    }

    /**
     * 装换List(只做一层装换)
     */
    public List<?> convertList(List<?> list) {
        return deepConvertList(list, 1);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------- List<Map>

    /**
     * 深度装换{@code List<Map>}
     *
     * @param mapList mapList对象
     * @param deep    转换深度值(应该大于等于1)
     */
    public List<Map<String, Object>> deepConvertMapList(List<Map<String, Object>> mapList, int deep) {
        if (mapList == null) {
            return null;
        }
        List<Map<String, Object>> res = new ArrayList<>(mapList.size());
        for (Map<String, Object> item : mapList) {
            res.add(deepConvertMap(item, deep));
        }
        return res;
    }

    /**
     * 深度装换{@code List<Map>}(深度转换，深度值为16)
     *
     * @param mapList mapList对象
     */
    public List<Map<String, Object>> deepConvertMapList(List<Map<String, Object>> mapList) {
        return deepConvertMapList(mapList, Default_Deep);
    }

    /**
     * 装换{@code List<Map>}(只做一层装换)
     */
    public List<Map<String, Object>> convertMapList(List<Map<String, Object>> mapList) {
        return deepConvertMapList(mapList, 1);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------- 基础类型

    /**
     * 把JavaScript对象转换成Java对象 <br />
     * 1. 只考虑简单对象，不考虑“数组”、“Object对象” <br />
     * 2. 只做浅转换(一层转换) <br />
     *
     * @param object JavaScript对象
     */
    public Object toJavaObjectForBase(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof ProxyObject) {
            return unWrapProxyObject((ProxyObject) object);
        }
        if (object instanceof ProxyArray) {
            return unWrapProxyArray((ProxyArray) object);
        }
        final String className = object.getClass().getName();
        if (!className.startsWith("com.oracle.truffle.") && !className.startsWith("org.graalvm.")) {
            return object;
        }
        Value value = toValue(object);
        if (value == null) {
            return object;
        }
        if (value.isNull()) {
            return null;
        } else if (value.isHostObject()) {                                      // Java对象
            return value.asHostObject();
        } else if (value.isException()) {                                       // 异常
            return value.throwException();
        } else if (value.isBoolean()) {                                         // boolean
            return value.asBoolean();
        } else if (value.isNumber()) {
            if (value.fitsInInt()) {                                            // int
                return value.asInt();
            }
            if (value.fitsInLong()) {                                           // long
                return value.asLong();
            }
            if (value.fitsInShort()) {                                          // short
                return value.asShort();
            }
            if (value.fitsInByte()) {                                           // byte
                return value.asByte();
            }
            if (value.fitsInFloat()) {                                          // float
                return value.asFloat();
            }
            if (value.fitsInDouble()) {                                         // double
                return value.asDouble();
            }
            return value.asDouble();
        } else if (value.isString()) {                                          // String
            return value.asString();
        } else if (value.isDate() || value.isTime() || value.isInstant()) {     // Date 日期+时间
            return GraalInterop.Instance.asJDate(value);
        } else if (value.isDuration()) {                                        // Duration 时间段
            return value.asDuration();
        } else if (value.isTimeZone()) {                                        // Duration 时间段
            return value.asTimeZone();
        } else if (value.isProxyObject()) {                                     // Proxy 对象
            return value.asProxyObject();
        } else if (value.canExecute()) {                                        // Js Function 类型
            return value;
        } else if (value.isMetaObject()) {                                      // Class 类型
            return value;
        } else if (value.isNativePointer()) {                                   // 本机指针
            return value;
        }
        return object;
    }

    /**
     * 得到原始的JavaScript对象
     */
    protected Value toValue(Object object) {
        if (object == null) {
            return null;
        }
        Value value = null;
        if (object instanceof Value) {
            value = (Value) object;
        }
        final String className = object.getClass().getName();
        if (value == null && (object instanceof TruffleObject || className.startsWith("com.oracle.truffle.") || className.startsWith("org.graalvm."))) {
            value = Value.asValue(object);
        }
        return value;
    }

    private static Boolean gotProxyObjectValues;
    private static Boolean gotProxyArrayValues;
    private static Boolean canSetAccessible;

    @SuppressWarnings("unchecked")
    public static Object unWrapProxyObject(ProxyObject proxyObject) {
        if (proxyObject instanceof Map) {
            return new HashMap<>((Map<String, Object>) proxyObject);
        }
        final String className = proxyObject.getClass().getName();
        if (gotProxyObjectValues == null && Objects.equals("org.graalvm.polyglot.proxy.ProxyObject$1", className)) {
            try {
                Field field = proxyObject.getClass().getDeclaredField("val$values");
                // noinspection ConstantConditions
                gotProxyObjectValues = (field != null);
            } catch (Exception ignored) {
                gotProxyObjectValues = false;
            }
        }
        if (gotProxyObjectValues != null && gotProxyObjectValues) {
            try {
                return reflectGetValue(proxyObject, "val$values");
            } catch (Exception ignored) {
                gotProxyObjectValues = false;
            }
        }
        Object[] keys = null;
        if (canSetAccessible == null || canSetAccessible) {
            try {
                keys = (Object[]) reflectGetValue(proxyObject.getMemberKeys(), "keys");
            } catch (Exception ignored) {
                canSetAccessible = false;
            }
        }
        if (keys == null) {
            keys = Value.asValue(proxyObject).getMemberKeys().toArray(new Object[0]);
        }
        Map<String, Object> map = new HashMap<>(keys.length);
        for (Object key : keys) {
            if (key == null) {
                continue;
            }
            String name = String.valueOf(key);
            map.put(name, proxyObject.getMember(name));
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    public static Object unWrapProxyArray(ProxyArray proxyArray) {
        if (proxyArray instanceof List) {
            return new ArrayList<>((List<Object>) proxyArray);
        }
        final String className = proxyArray.getClass().getName();
        if (gotProxyArrayValues == null && (Objects.equals("org.graalvm.polyglot.proxy.ProxyArray$1", className) || Objects.equals("org.graalvm.polyglot.proxy.ProxyArray$2", className))) {
            try {
                Field field = proxyArray.getClass().getDeclaredField("val$values");
                // noinspection ConstantConditions
                gotProxyArrayValues = (field != null);
            } catch (Exception ignored) {
                gotProxyArrayValues = false;
            }
        }
        if (gotProxyArrayValues != null && gotProxyArrayValues) {
            try {
                return reflectGetValue(proxyArray, "val$values");
            } catch (Exception ignored) {
                gotProxyArrayValues = false;
            }
        }
        List<Object> list = new ArrayList<>((int) proxyArray.getSize());
        for (int i = 0; i < proxyArray.getSize(); i++) {
            list.add(proxyArray.get(i));
        }
        return list;
    }

    @SneakyThrows
    private static Object reflectGetValue(Object instance, String fieldName) {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        canSetAccessible = true;
        return field.get(instance);
    }
}
