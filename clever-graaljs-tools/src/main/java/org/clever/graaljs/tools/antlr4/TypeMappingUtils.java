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
        TYPE_MAPPING.put("Number", "number");
        TYPE_MAPPING.put("Number[]", "number[]");

        TYPE_MAPPING.put("ByteArrayInputStream", "JByteArrayInputStream");
        TYPE_MAPPING.put("ByteArrayOutputStream", "JByteArrayOutputStream");
        TYPE_MAPPING.put("Closeable", "JCloseable");
        TYPE_MAPPING.put("FileInputStream", "JFileInputStream");
        TYPE_MAPPING.put("FileOutputStream", "JFileOutputStream");
        TYPE_MAPPING.put("InputStream", "JInputStream");
        TYPE_MAPPING.put("InputStreamReader", "JInputStreamReader");
        TYPE_MAPPING.put("OutputStream", "JOutputStream");
        TYPE_MAPPING.put("OutputStreamWriter", "JOutputStreamWriter");
        TYPE_MAPPING.put("PrintWriter", "JPrintWriter");
        TYPE_MAPPING.put("Reader", "JReader");
        TYPE_MAPPING.put("Writer", "JWriter");
        TYPE_MAPPING.put("Class", "JClass");
        TYPE_MAPPING.put("Class<?>", "JClass");
        TYPE_MAPPING.put("Comparable", "JComparable");
        TYPE_MAPPING.put("StringBuffer", "JStringBuffer");
        TYPE_MAPPING.put("StringBuilder", "JStringBuilder");
        TYPE_MAPPING.put("Thread", "JThread");
        TYPE_MAPPING.put("Throwable", "JThrowable");
        TYPE_MAPPING.put("BigDecimal", "JBigDecimal");
        TYPE_MAPPING.put("BigInteger", "JBigInteger");
        TYPE_MAPPING.put("RoundingMode", "JRoundingMode");
        TYPE_MAPPING.put("SqlDate", "JSqlDate");
        TYPE_MAPPING.put("SqlTime", "JSqlTime");
        TYPE_MAPPING.put("SqlTimestamp", "JSqlTimestamp");
        TYPE_MAPPING.put("Duration", "JDuration");
        TYPE_MAPPING.put("Instant", "JInstant");
        TYPE_MAPPING.put("LocalDate", "JLocalDate");
        TYPE_MAPPING.put("LocalTime", "JLocalTime");
        TYPE_MAPPING.put("ZoneId", "JZoneId");
        TYPE_MAPPING.put("Collection", "JCollection");
        TYPE_MAPPING.put("Collection<Object>", "JCollection<any>");
        TYPE_MAPPING.put("Date", "JDate");
        TYPE_MAPPING.put("Enumeration", "JEnumeration");
        TYPE_MAPPING.put("Iterable", "JIterable");
        TYPE_MAPPING.put("Iterator", "JIterator");
        TYPE_MAPPING.put("List", "JList");
        TYPE_MAPPING.put("Map", "JMap");
        TYPE_MAPPING.put("MapEntry", "JMapEntry");
        TYPE_MAPPING.put("Set", "JSet");
        TYPE_MAPPING.put("Cookie", "JCookie");
        TYPE_MAPPING.put("HttpServletRequest", "JHttpServletRequest");
        TYPE_MAPPING.put("HttpServletResponse", "JHttpServletResponse");
        TYPE_MAPPING.put("HttpSession", "JHttpSession");
        TYPE_MAPPING.put("ServletContext", "JServletContext");
        TYPE_MAPPING.put("ServletRequest", "JServletRequest");
        TYPE_MAPPING.put("ServletResponse", "JServletResponse");
        TYPE_MAPPING.put("MultiValueMap", "JMultiValueMap");
    }

    public static void addMapping(String javaType, String tsType) {
        TYPE_MAPPING.put(javaType, tsType);
    }

    public static String getType(String javaType) {
        String tsType = TYPE_MAPPING.get(javaType);
        return StringUtils.isBlank(tsType) ? javaType : tsType;
    }
}
