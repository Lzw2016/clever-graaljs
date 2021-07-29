package org.clever.graaljs.tools.antlr4;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/27 22:00 <br/>
 */
public class TypeMappingUtils {
    private static final Map<String, String> TYPE_MAPPING = new HashMap<>();

    static {
        TYPE_MAPPING.put("Byte", "JByte");
        TYPE_MAPPING.put("byte", "JByte");
        TYPE_MAPPING.put("Short", "JShort");
        TYPE_MAPPING.put("short", "JShort");
        TYPE_MAPPING.put("Integer", "JInt");
        TYPE_MAPPING.put("int", "JInt");
        TYPE_MAPPING.put("Long", "JLong");
        TYPE_MAPPING.put("long", "JLong");
        TYPE_MAPPING.put("Float", "JFloat");
        TYPE_MAPPING.put("float", "JFloat");
        TYPE_MAPPING.put("Double", "JDouble");
        TYPE_MAPPING.put("double", "JDouble");
        TYPE_MAPPING.put("Boolean", "JBoolean");
        TYPE_MAPPING.put("boolean", "JBoolean");
        TYPE_MAPPING.put("Character", "JChar");
        TYPE_MAPPING.put("char", "JChar");
        TYPE_MAPPING.put("String", "JString");
        TYPE_MAPPING.put("CharSequence", "JString");
        TYPE_MAPPING.put("Object", "any");

        TYPE_MAPPING.put("Byte[]", "JByte[]");
        TYPE_MAPPING.put("byte[]", "JByte[]");
        TYPE_MAPPING.put("Short[]", "JShort[]");
        TYPE_MAPPING.put("short[]", "JShort[]");
        TYPE_MAPPING.put("Integer[]", "JInt[]");
        TYPE_MAPPING.put("int[]", "JInt[]");
        TYPE_MAPPING.put("Long[]", "JLong[]");
        TYPE_MAPPING.put("long[]", "JLong[]");
        TYPE_MAPPING.put("Float[]", "JFloat[]");
        TYPE_MAPPING.put("float[]", "JFloat[]");
        TYPE_MAPPING.put("Double[]", "JDouble[]");
        TYPE_MAPPING.put("double[]", "JDouble[]");
        TYPE_MAPPING.put("Boolean[]", "JBoolean[]");
        TYPE_MAPPING.put("boolean[]", "JBoolean[]");
        TYPE_MAPPING.put("Character[]", "JChar[]");
        TYPE_MAPPING.put("char[]", "JChar[]");
        TYPE_MAPPING.put("String[]", "JString[]");
        TYPE_MAPPING.put("CharSequence[]", "JString[]");
        TYPE_MAPPING.put("Object[]", "any[]");

        TYPE_MAPPING.put("Collection", "JCollection");
        TYPE_MAPPING.put("Collection<Object>", "JCollection<any>");
    }

    public static void addMapping(String javaType, String tsType) {
        TYPE_MAPPING.put(javaType, tsType);
    }

    public static String getType(String javaType) {
        String tsType = TYPE_MAPPING.get(javaType);
        return StringUtils.isBlank(tsType) ? javaType : tsType;
    }
}
