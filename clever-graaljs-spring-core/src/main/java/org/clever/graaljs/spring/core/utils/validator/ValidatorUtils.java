package org.clever.graaljs.spring.core.utils.validator;


import java.util.regex.Pattern;

/**
 * 字段验证器
 */
public class ValidatorUtils {

    /**
     * 英文字母 、数字和下划线
     */
    public final static Pattern GENERAL = Pattern.compile("^\\w+$");
    /**
     * 数字
     */
    public final static Pattern NUMBERS = Pattern.compile("\\d+");
    /**
     * 字母
     */
    public final static Pattern WORD = Pattern.compile("[a-zA-Z]+");
    /**
     * 单个中文汉字
     */
    public final static Pattern CHINESE = Pattern.compile("[\u4E00-\u9FFF]");
    /**
     * 中文汉字
     */
    public final static Pattern CHINESE_S = Pattern.compile("[\u4E00-\u9FFF]+");
    /**
     * 分组
     */
    public final static Pattern GROUP_VAR = Pattern.compile("\\$(\\d+)");
    /**
     * IP v4
     */
    public final static Pattern IPV4 = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
    /**
     * IP v6
     */
    public final static Pattern IPV6 = Pattern.compile("(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]+|::(ffff(:0{1,4})?:)?((25[0-5]|(2[0-4]|1?[0-9])?[0-9])\\.){3}(25[0-5]|(2[0-4]|1?[0-9])?[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1?[0-9])?[0-9])\\.){3}(25[0-5]|(2[0-4]|1?[0-9])?[0-9]))");
    /**
     * 货币
     */
    public final static Pattern MONEY = Pattern.compile("^(\\d+(?:\\.\\d+)?)$");
    /**
     * 邮件，符合RFC 5322规范，正则来自：http://emailregex.com/
     */
    // public final static Pattern EMAIL = Pattern.compile("(\\w|.)+@\\w+(\\.\\w+){1,2}");
    public final static Pattern EMAIL = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])", Pattern.CASE_INSENSITIVE);
    /**
     * 移动电话
     */
    public final static Pattern MOBILE = Pattern.compile("(?:0|86|\\+86)?1[3456789]\\d{9}");
    /**
     * 18位身份证号码
     */
    public final static Pattern CITIZEN_ID = Pattern.compile("[1-9]\\d{5}[1-2]\\d{3}((0\\d)|(1[0-2]))(([012]\\d)|3[0-1])\\d{3}(\\d|X|x)");
    /**
     * 邮编
     */
    public final static Pattern ZIP_CODE = Pattern.compile("[1-9]\\d{5}(?!\\d)");
    /**
     * 生日
     */
    public final static Pattern BIRTHDAY = Pattern.compile("^(\\d{2,4})([/\\-.年]?)(\\d{1,2})([/\\-.月]?)(\\d{1,2})日?$");
    /**
     * URL
     */
    public final static Pattern URL = Pattern.compile("[a-zA-z]+://[^\\s]*");
    /**
     * Http URL
     */
    public final static Pattern URL_HTTP = Pattern.compile("(https://|http://)?([\\w-]+\\.)+[\\w-]+(:\\d+)*(/[\\w- ./?%&=]*)?");
    /**
     * 中文字、英文字母、数字和下划线
     */
    public final static Pattern GENERAL_WITH_CHINESE = Pattern.compile("^[\u4E00-\u9FFF\\w]+$");
    /**
     * UUID
     */
    public final static Pattern UUID = Pattern.compile("^[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}$");
    /**
     * 不带横线的UUID
     */
    public final static Pattern UUID_SIMPLE = Pattern.compile("^[0-9a-z]{32}$");
    /**
     * 中国车牌号码
     */
    public final static Pattern PLATE_NUMBER = Pattern.compile("^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z][A-Z][A-Z0-9]{4}[A-Z0-9挂学警港澳]$");
    /**
     * MAC地址正则
     */
    public static final Pattern MAC_ADDRESS = Pattern.compile("((?:[A-F0-9]{1,2}[:-]){5}[A-F0-9]{1,2})|(?:0x)(\\d{12})(?:.+ETHER)", Pattern.CASE_INSENSITIVE);
    /**
     * 16进制字符串
     */
    public static final Pattern HEX = Pattern.compile("^[a-f0-9]+$", Pattern.CASE_INSENSITIVE);
    /**
     * 时间正则
     */
    public static final Pattern TIME = Pattern.compile("\\d{1,2}:\\d{1,2}(:\\d{1,2})?");

    private ValidatorUtils() {
    }

    /**
     * 给定内容是否匹配正则
     *
     * @param pattern 模式
     * @param content 内容
     * @return 正则为null或者""则不检查，返回true，内容为null返回false
     */
    public static boolean isMatch(Pattern pattern, CharSequence content) {
        if (content == null || pattern == null) {
            // 提供null的字符串为不匹配
            return false;
        }
        return pattern.matcher(content).matches();
    }

    /**
     * 通过正则表达式验证
     *
     * @param pattern 正则模式
     * @param value   值
     * @return 是否匹配正则
     */
    public static boolean isMatchRegex(Pattern pattern, CharSequence value) {
        return isMatch(pattern, value);
    }

    /**
     * 验证是否为IPV4地址
     *
     * @param value 值
     * @return 是否为IPV4地址
     */
    public static boolean isIpv4(CharSequence value) {
        return isMatchRegex(IPV4, value);
    }

    /**
     * 验证是否为英文字母 、数字和下划线
     *
     * @param value 值
     * @return 是否为英文字母 、数字和下划线
     */
    public static boolean isGeneral(CharSequence value) {
        return isMatchRegex(GENERAL, value);
    }

    /**
     * 验证该字符串是否是字母（包括大写和小写字母）
     *
     * @param value 字符串内容
     * @return 是否是字母（包括大写和小写字母）
     * @since 4.1.8
     */
    public static boolean isWord(CharSequence value) {
        return isMatchRegex(WORD, value);
    }

    /**
     * 验证是否为货币
     *
     * @param value 值
     * @return 是否为货币
     */
    public static boolean isMoney(CharSequence value) {
        return isMatchRegex(MONEY, value);
    }

    /**
     * 验证是否为邮政编码（中国）
     *
     * @param value 值
     * @return 是否为邮政编码（中国）
     */
    public static boolean isZipCode(CharSequence value) {
        return isMatchRegex(ZIP_CODE, value);
    }

    /**
     * 验证是否为可用邮箱地址
     *
     * @param value 值
     * @return 否为可用邮箱地址
     */
    public static boolean isEmail(CharSequence value) {
        return isMatchRegex(EMAIL, value);
    }

    /**
     * 验证是否为手机号码（中国）
     *
     * @param value 值
     * @return 是否为手机号码（中国）
     */
    public static boolean isMobile(CharSequence value) {
        return isMatchRegex(MOBILE, value);
    }

    /**
     * 验证是否为身份证号码（18位中国）<br>
     * 出生日期只支持到到2999年
     *
     * @param value 值
     * @return 是否为身份证号码（18位中国）
     */
    public static boolean isCitizenId(CharSequence value) {
        return isMatchRegex(CITIZEN_ID, value);
    }

    /**
     * 验证是否为IPV6地址
     *
     * @param value 值
     * @return 是否为IPV6地址
     */
    public static boolean isIpv6(CharSequence value) {
        return isMatchRegex(IPV6, value);
    }

    /**
     * 验证是否为MAC地址
     *
     * @param value 值
     * @return 是否为MAC地址
     * @since 4.1.3
     */
    public static boolean isMac(CharSequence value) {
        return isMatchRegex(MAC_ADDRESS, value);
    }

    /**
     * 验证是否为中国车牌号
     *
     * @param value 值
     * @return 是否为中国车牌号
     * @since 3.0.6
     */
    public static boolean isPlateNumber(CharSequence value) {
        return isMatchRegex(PLATE_NUMBER, value);
    }

    /**
     * 验证是否为中文字、英文字母、数字和下划线
     *
     * @param value 值
     * @return 是否为中文字、英文字母、数字和下划线
     */
    public static boolean isGeneralWithChinese(CharSequence value) {
        return isMatchRegex(GENERAL_WITH_CHINESE, value);
    }

    /**
     * 验证是否为UUID<br>
     * 包括带横线标准格式和不带横线的简单模式
     *
     * @param value 值
     * @return 是否为UUID
     */
    public static boolean isUUID(CharSequence value) {
        return isMatchRegex(UUID, value) || isMatchRegex(UUID_SIMPLE, value);
    }

    /**
     * 验证是否为Hex（16进制）字符串
     *
     * @param value 值
     * @return 是否为Hex（16进制）字符串
     * @since 4.3.3
     */
    public static boolean isHex(CharSequence value) {
        return isMatchRegex(HEX, value);
    }
}
