package org.clever.graaljs.core.builtin.wrap;

import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import org.apache.commons.lang3.StringUtils;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/08/16 16:13 <br/>
 */
public class PinyinUtils {
    public static final PinyinUtils Instance = new PinyinUtils();

    private final org.clever.graaljs.core.builtin.adapter.PinyinUtils delegate;

    private PinyinUtils() {
        delegate = org.clever.graaljs.core.builtin.adapter.PinyinUtils.Instance;
    }

    /**
     * 获取一个汉字的所有拼音
     *
     * @param c      汉字字符
     * @param format 设置拼音的格式
     * @return 如果参数c不是汉字或转换，成功返回所有拼音
     */
    public String[] toAllPinYin(char c, HanyuPinyinOutputFormat format) {
        return delegate.toAllPinYin(c, format);
    }

    /**
     * 获取一个汉字的所有拼音<br/>
     * 使用默认格式：1.全小写；2.用音调字符表示音调；3.'ü'的表示方式"ü"，默认格式<br/>
     *
     * @param c 汉字字符
     * @return 如果参数c不是汉字或转换失败返回null，成功返回所有拼音
     */
    public String[] toAllPinYin(char c) {
        return delegate.toAllPinYin(c);
    }

    /**
     * 获取一个汉字的一个发音
     *
     * @param c      汉字字符
     * @param format 设置拼音的格式
     * @return 如果参数c不是汉字或转换失败返回null，成功返回第一个发音的拼音
     */
    public String toPinYin(char c, HanyuPinyinOutputFormat format) {
        return delegate.toPinYin(c, format);
    }

    /**
     * 获取一个汉字的一个发音
     *
     * @param c 汉字字符
     * @return 如果参数c不是汉字或转换失败返回null，成功返回第一个发音的拼音
     */
    public String toPinYin(char c) {
        return delegate.toPinYin(c);
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
        return delegate.getStringPinYin(str, separator, format);
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
        return delegate.getStringPinYin(str, separator);
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
        return delegate.getStringPurePinYin(str, separator, format);
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
        return delegate.getStringPurePinYin(str, separator);
    }

    /**
     * 获取汉字的拼音首字母<br/>
     *
     * @param c 汉字字符
     * @return 成功返回首字母
     */
    public char toHeadPinYin(char c) {
        return delegate.toHeadPinYin(c);
    }

    /**
     * 获取一个中文字符串的所有首拼，不能转换的部分原样输出<br/>
     *
     * @param str       汉字字符串
     * @param separator 拼音之间的分隔符
     * @return 转换后的字符串，转换失败返回null
     */
    public String getStringHeadPinYin(String str, String separator) {
        return delegate.getStringHeadPinYin(str, separator);
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
        return delegate.getStringHeadPurePinYin(str, separator);
    }

    /**
     * 判断一个字符串是否全部由中文汉字组成<br/>
     *
     * @param str 判断的字符串
     * @return str完全由汉字组成返回true
     */
    public boolean isChineseString(String str) {
        return delegate.isChineseString(str);
    }

    /**
     * @param caseType  输出大小写配置
     * @param toneType  定义汉语声调的输出格式
     * @param vCharType 定义字符'u'('ü')的输出格式
     */
    public HanyuPinyinOutputFormat getFormat(String caseType, String toneType, String vCharType) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        if (StringUtils.isNotBlank(caseType)) {
            caseType = caseType.toUpperCase();
            switch (caseType) {
                case "LOWERCASE":
                    format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
                    break;
                case "UPPERCASE":
                    format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
                    break;
                default:
                    throw new IllegalArgumentException("不支持caseType：" + caseType);
            }
        }
        if (StringUtils.isNotBlank(toneType)) {
            toneType = toneType.toUpperCase();
            switch (toneType) {
                case "WITH_TONE_MARK":
                    format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
                    break;
                case "WITH_TONE_NUMBER":
                    format.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
                    break;
                case "WITHOUT_TONE":
                    format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
                    break;
                default:
                    throw new IllegalArgumentException("不支持toneType：" + toneType);
            }
        }
        if (StringUtils.isNotBlank(vCharType)) {
            vCharType = vCharType.toUpperCase();
            switch (vCharType) {
                case "WITH_U_AND_COLON":
                    format.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);
                    break;
                case "WITH_U_UNICODE":
                    format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
                    break;
                case "WITH_V":
                    format.setVCharType(HanyuPinyinVCharType.WITH_V);
                    break;
                default:
                    throw new IllegalArgumentException("不支持vCharType：" + vCharType);
            }
        }
        return format;
    }
}
