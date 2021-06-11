package org.clever.graaljs.core.builtin.adapter;

import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/28 22:33 <br/>
 */
public class PinyinUtils {
    public static final PinyinUtils Instance = new PinyinUtils();

    private PinyinUtils() {
    }

    /**
     * 获取一个汉字的所有拼音
     *
     * @param c      汉字字符
     * @param format 设置拼音的格式
     * @return 如果参数c不是汉字或转换，成功返回所有拼音
     */
    public String[] toAllPinYin(char c, HanyuPinyinOutputFormat format) {
        return org.clever.graaljs.core.utils.pinyin.PinyinUtils.toAllPinYin(c, format);
    }

    /**
     * 获取一个汉字的所有拼音<br/>
     * 使用默认格式：1.全小写；2.用音调字符表示音调；3.'ü'的表示方式"ü"，默认格式<br/>
     *
     * @param c 汉字字符
     * @return 如果参数c不是汉字或转换失败返回null，成功返回所有拼音
     */
    public String[] toAllPinYin(char c) {
        return org.clever.graaljs.core.utils.pinyin.PinyinUtils.toAllPinYin(c);
    }

    /**
     * 获取一个汉字的一个发音
     *
     * @param c      汉字字符
     * @param format 设置拼音的格式
     * @return 如果参数c不是汉字或转换失败返回null，成功返回第一个发音的拼音
     */
    public String toPinYin(char c, HanyuPinyinOutputFormat format) {
        return org.clever.graaljs.core.utils.pinyin.PinyinUtils.toPinYin(c, format);
    }

    /**
     * 获取一个汉字的一个发音
     *
     * @param c 汉字字符
     * @return 如果参数c不是汉字或转换失败返回null，成功返回第一个发音的拼音
     */
    public String toPinYin(char c) {
        return org.clever.graaljs.core.utils.pinyin.PinyinUtils.toPinYin(c);
    }

    /**
     * 把一个汉字字符串转成拼音字符串，不能转换的部分原样输出
     *
     * @param str       汉字字符串
     * @param separator 拼音之间的分隔符
     * @param format    设置拼音的格式
     * @return 转换后的字符串，转换失败返回null
     */
    public String getStringPinYin(String str, String separator, HanyuPinyinOutputFormat format) {
        return org.clever.graaljs.core.utils.pinyin.PinyinUtils.getStringPinYin(str, separator, format);
    }

    /**
     * 把一个汉字字符串转成拼音字符串，不能转换的部分原样输出<br/>
     * 使用默认格式：1.全小写；2.用音调字符表示音调；3.'ü'的表示方式"ü"，默认格式<br/>
     *
     * @param str       汉字字符串
     * @param separator 拼音之间的分隔符
     * @return 转换后的字符串，转换失败返回null
     */
    public String getStringPinYin(String str, String separator) {
        return org.clever.graaljs.core.utils.pinyin.PinyinUtils.getStringPinYin(str, separator);
    }

    /**
     * 把一个汉字字符串转成拼音字符串，忽略不能转换的部分<br/>
     * <b>注意：不输出不能转换的部分</b>
     *
     * @param str       汉字字符串
     * @param separator 拼音之间的分隔符
     * @param format    设置拼音的格式
     * @return 转换后的字符串，转换失败返回null
     */
    public String getStringPurePinYin(String str, String separator, HanyuPinyinOutputFormat format) {
        return org.clever.graaljs.core.utils.pinyin.PinyinUtils.getStringPurePinYin(str, separator, format);
    }

    /**
     * 把一个汉字字符串转成拼音字符串，忽略不能转换的部分<br/>
     * 使用默认格式：1.全小写；2.用音调字符表示音调；3.'ü'的表示方式"ü"，默认格式<br/>
     * <b>注意：不输出不能转换的部分</b>
     *
     * @param str       汉字字符串
     * @param separator 拼音之间的分隔符
     * @return 转换后的字符串
     */
    public String getStringPurePinYin(String str, String separator) {
        return org.clever.graaljs.core.utils.pinyin.PinyinUtils.getStringPurePinYin(str, separator);
    }

    /**
     * 获取汉字的拼音首字母<br/>
     *
     * @param c 汉字字符
     * @return 成功返回首字母
     */
    public char toHeadPinYin(char c) {
        return org.clever.graaljs.core.utils.pinyin.PinyinUtils.toHeadPinYin(c);
    }

    /**
     * 获取一个中文字符串的所有首拼，不能转换的部分原样输出<br/>
     *
     * @param str       汉字字符串
     * @param separator 拼音之间的分隔符
     * @return 转换后的字符串，转换失败返回null
     */
    public String getStringHeadPinYin(String str, String separator) {
        return org.clever.graaljs.core.utils.pinyin.PinyinUtils.getStringHeadPinYin(str, separator);
    }

    /**
     * 获取一个中文字符串的所有首拼，忽略不能转换的部分<br/>
     * <b>注意：不输出不能转换的部分</b>
     *
     * @param str       汉字字符串
     * @param separator 拼音之间的分隔符
     * @return 转换后的字符串，转换失败返回null
     */
    public String getStringHeadPurePinYin(String str, String separator) {
        return org.clever.graaljs.core.utils.pinyin.PinyinUtils.getStringHeadPurePinYin(str, separator);
    }

    /**
     * 判断一个字符串是否全部由中文汉字组成<br/>
     *
     * @param str 判断的字符串
     * @return str完全由汉字组成返回true
     */
    public boolean isChineseString(String str) {
        return org.clever.graaljs.core.utils.pinyin.PinyinUtils.isChineseString(str);
    }
}
