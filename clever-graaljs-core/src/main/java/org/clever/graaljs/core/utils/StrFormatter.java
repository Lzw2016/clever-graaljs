package org.clever.graaljs.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.core.utils.mapper.JacksonMapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * 字符串格式化工具
 */
public class StrFormatter {
    public static final String EMPTY_JSON = "{}";
    public static final char C_BACKSLASH = '\\';
    public static final char C_DELIMITER_START = '{';
    public static final char UNDERLINE = '_';

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
    public static String format(final String strPattern, final Object... argArray) {
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
     * 是否空白符<br>
     * 空白符包括空格、制表符、全角空格和不间断空格<br>
     *
     * @param c 字符
     * @return 是否空白符
     * @see Character#isWhitespace(int)
     * @see Character#isSpaceChar(int)
     */
    public static boolean isBlankChar(int c) {
        return Character.isWhitespace(c) || Character.isSpaceChar(c) || c == '\ufeff' || c == '\u202a';
    }

    /**
     * 是否空白符<br>
     * 空白符包括空格、制表符、全角空格和不间断空格<br>
     *
     * @param c 字符
     * @return 是否空白符
     * @see Character#isWhitespace(int)
     * @see Character#isSpaceChar(int)
     */
    public static boolean isBlankChar(char c) {
        return isBlankChar((int) c);
    }

    /**
     * 字符串是否为空白 空白的定义如下： <br>
     * 1、为null <br>
     * 2、为不可见字符（如空格）<br>
     * 3、""<br>
     *
     * @param str 被检测的字符串
     * @return 是否为空
     */
    public static boolean isBlank(CharSequence str) {
        int length;
        if (str == null || (length = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < length; i++) {
            // 只要有一个非空字符即为非空字符串
            if (!isBlankChar(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 将byte数组转为字符串
     *
     * @param bytes   byte数组
     * @param charset 字符集
     * @return 字符串
     */
    public static String str(byte[] bytes, String charset) {
        return str(bytes, isBlank(charset) ? Charset.defaultCharset() : Charset.forName(charset));
    }

    /**
     * 解码字节码
     *
     * @param data    字符串
     * @param charset 字符集，如果此字段为空，则解码的结果取决于平台
     * @return 解码后的字符串
     */
    public static String str(byte[] data, Charset charset) {
        if (data == null) {
            return null;
        }

        if (null == charset) {
            return new String(data);
        }
        return new String(data, charset);
    }

    /**
     * 将Byte数组转为字符串
     *
     * @param bytes   byte数组
     * @param charset 字符集
     * @return 字符串
     */
    public static String str(Byte[] bytes, String charset) {
        return str(bytes, isBlank(charset) ? Charset.defaultCharset() : Charset.forName(charset));
    }

    /**
     * 解码字节码
     *
     * @param data    字符串
     * @param charset 字符集，如果此字段为空，则解码的结果取决于平台
     * @return 解码后的字符串
     */
    public static String str(Byte[] data, Charset charset) {
        if (data == null) {
            return null;
        }

        byte[] bytes = new byte[data.length];
        Byte dataByte;
        for (int i = 0; i < data.length; i++) {
            dataByte = data[i];
            bytes[i] = (null == dataByte) ? -1 : dataByte;
        }

        return str(bytes, charset);
    }

    /**
     * 将编码的byteBuffer数据转换为字符串
     *
     * @param data    数据
     * @param charset 字符集，如果为空使用当前系统字符集
     * @return 字符串
     */
    public static String str(ByteBuffer data, String charset) {
        if (data == null) {
            return null;
        }

        return str(data, Charset.forName(charset));
    }

    /**
     * 将编码的byteBuffer数据转换为字符串
     *
     * @param data    数据
     * @param charset 字符集，如果为空使用当前系统字符集
     * @return 字符串
     */
    public static String str(ByteBuffer data, Charset charset) {
        if (null == charset) {
            charset = Charset.defaultCharset();
        }
        return charset.decode(data).toString();
    }

    /**
     * {@link CharSequence} 转为字符串，null安全
     *
     * @param cs {@link CharSequence}
     * @return 字符串
     */
    public static String str(CharSequence cs) {
        return null == cs ? null : cs.toString();
    }

    /**
     * 数组或集合转String
     *
     * @param obj 集合或数组对象
     * @return 数组字符串，与集合转字符串格式相同
     */
    @SuppressWarnings("ConstantConditions")
    public static String toString(Object obj) {
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
            str = DateTimeUtils.formatToString((Date) obj);
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
    public static boolean isArray(Object obj) {
        if (null == obj) {
            return false;
        }
        return obj.getClass().isArray();
    }

    /**
     * 字符串驼峰转下划线格式
     *
     * @param param 需要转换的字符串
     * @return 转换好的字符串
     */
    public static String camelToUnderline(String param) {
        if (StringUtils.isBlank(param)) {
            return StringUtils.EMPTY;
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len + 16);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c) && i > 0) {
                sb.append(UNDERLINE);
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /**
     * 字符串下划线转驼峰格式
     *
     * @param param 需要转换的字符串
     * @return 转换好的字符串
     */
    public static String underlineToCamel(String param) {
        if (StringUtils.isBlank(param)) {
            return StringUtils.EMPTY;
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        boolean flag = false;
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Objects.equals(UNDERLINE, c)) {
                flag = true;
                continue;
            }
            if (flag) {
                flag = false;
                c = Character.toUpperCase(c);
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
