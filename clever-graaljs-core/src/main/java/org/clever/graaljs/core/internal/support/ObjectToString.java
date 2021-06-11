package org.clever.graaljs.core.internal.support;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.clever.graaljs.core.GraalConstant;
import org.clever.graaljs.core.utils.ExceptionUtils;
import org.clever.graaljs.core.utils.mapper.JacksonMapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/26 11:42 <br/>
 */
public class ObjectToString {
    public static final ObjectToString Instance = new ObjectToString();

    public static final String EMPTY_JSON = "{}";
    public static final char C_BACKSLASH = '\\';
    public static final char C_DELIMITER_START = '{';
    // public static final char UNDERLINE = '_';

    protected ObjectToString() {
    }

    /**
     * 格式化字符串,类似log4j日志实现<br>
     * 此方法只是简单将占位符"{}"按照顺序替换为参数<br>
     * 如果想输出"{}"使用"\\"转义"{"即可，如果想输出"{}"之前的"\"使用双转义符"\\\\"即可<br>
     * <pre>
     *  format("this is {} for {}", "a", "b")       -> this is a for b
     *  format("this is \\{} for {}", "a", "b")     -> this is \{} for a
     *  format("this is \\\\{} for {}", "a", "b")   -> this is \a for b
     * </pre>
     *
     * @param strPattern 字符串模板
     * @param argArray   参数列表
     */
    public String format(final String strPattern, final Object... argArray) {
        if (StringUtils.isBlank(strPattern) || argArray == null || argArray.length <= 0) {
            return strPattern;
        }
        final int strPatternLength = strPattern.length();
        // 初始化定义好的长度以获得更好的性能
        StringBuilder sb = new StringBuilder(strPatternLength + 50);
        int handledPosition = 0;    // 记录已经处理到的位置
        int delimiterIndex;         // 占位符所在位置
        for (int argIndex = 0; argIndex < argArray.length; argIndex++) {
            delimiterIndex = strPattern.indexOf(EMPTY_JSON, handledPosition);
            if (delimiterIndex == -1) {         // 剩余部分无占位符
                if (handledPosition == 0) {     // 不带占位符的模板直接返回
                    return strPattern;
                }
                // 字符串模板剩余部分不再包含占位符，加入剩余部分后返回结果
                sb.append(strPattern, handledPosition, strPatternLength);
                return sb.toString();
            }
            // 转义符
            if (delimiterIndex > 0 && strPattern.charAt(delimiterIndex - 1) == C_BACKSLASH) {       // 转义符
                if (delimiterIndex > 1 && strPattern.charAt(delimiterIndex - 2) == C_BACKSLASH) {   // 双转义符
                    // 转义符之前还有一个转义符，占位符依旧有效
                    sb.append(strPattern, handledPosition, delimiterIndex - 1);
                    sb.append(toString(argArray[argIndex]));
                    handledPosition = delimiterIndex + 2;
                } else {
                    // 占位符被转义
                    argIndex--;
                    sb.append(strPattern, handledPosition, delimiterIndex - 1);
                    sb.append(C_DELIMITER_START);
                    handledPosition = delimiterIndex + 1;
                }
            } else {
                // 正常占位符
                sb.append(strPattern, handledPosition, delimiterIndex);
                sb.append(toString(argArray[argIndex]));
                handledPosition = delimiterIndex + 2;
            }
        }
        // append the characters following the last {} pair.
        // 加入最后一个占位符后所有的字符
        sb.append(strPattern, handledPosition, strPattern.length());
        return sb.toString();
    }

    /**
     * 数组或集合转String
     *
     * @param obj 集合或数组对象
     * @return 数组字符串，与集合转字符串格式相同
     */
    @SuppressWarnings("ConstantConditions")
    public String toString(Object obj) {
        if (null == obj) {
            return null;
        }
        if (isArray(obj)) {
            try {
                return Arrays.deepToString((Object[]) obj);
            } catch (Exception e) {
                final String className = obj.getClass().getComponentType().getName();
                switch (className) {
                    case "long":
                        return Arrays.toString((long[]) obj);
                    case "int":
                        return Arrays.toString((int[]) obj);
                    case "short":
                        return Arrays.toString((short[]) obj);
                    case "char":
                        return Arrays.toString((char[]) obj);
                    case "byte":
                        return Arrays.toString((byte[]) obj);
                    case "boolean":
                        return Arrays.toString((boolean[]) obj);
                    case "float":
                        return Arrays.toString((float[]) obj);
                    case "double":
                        return Arrays.toString((double[]) obj);
                    default:
                        throw ExceptionUtils.unchecked(e);
                }
            }
        }
        String str;
        if (obj instanceof Byte
                || obj instanceof Short
                || obj instanceof Integer
                || obj instanceof Long
                || obj instanceof Float
                || obj instanceof Double
                || obj instanceof BigInteger
                || obj instanceof BigDecimal
                || obj instanceof Boolean
                || obj instanceof String) {
            str = String.valueOf(obj);
        } else if (obj instanceof Date) {
            str = DateFormatUtils.format((Date) obj, GraalConstant.JS_Default_Format);
        } else {
            str = JacksonMapper.getInstance().toJson(obj);
        }
        return str;
    }

    /**
     * 对象是否为数组对象
     *
     * @param obj 对象
     * @return 是否为数组对象，如果为{@code null} 返回false
     */
    public boolean isArray(Object obj) {
        if (null == obj) {
            return false;
        }
        return obj.getClass().isArray();
    }
}
