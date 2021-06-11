package org.clever.graaljs.core.builtin;

import org.clever.graaljs.core.utils.StrFormatter;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/28 22:32 <br/>
 */
public class StringUtils {
    public static final StringUtils Instance = new StringUtils();

    private StringUtils() {
    }

    // Other
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 字符串驼峰转下划线格式
     *
     * @param param 需要转换的字符串
     * @return 转换好的字符串
     */
    public String camelToUnderline(String param) {
        return StrFormatter.camelToUnderline(param);
    }

    /**
     * 字符串下划线转驼峰格式
     *
     * @param param 需要转换的字符串
     * @return 转换好的字符串
     */
    public String underlineToCamel(String param) {
        return StrFormatter.underlineToCamel(param);
    }

    /**
     * 字符串下划线转驼峰格式
     *
     * @param param          需要转换的字符串
     * @param firstUpperCase 首字母是否大写
     * @return 转换好的字符串
     */
    public String underlineToCamel(String param, Object firstUpperCase) {
        if (org.apache.commons.lang3.StringUtils.isBlank(param)) {
            return param;
        }
        String res = StrFormatter.underlineToCamel(param);
        if (firstUpperCase instanceof Boolean && (Boolean) firstUpperCase) {
            res = String.valueOf(res.charAt(0)).toUpperCase() + res.substring(1);
        }
        return res;
    }

    /**
     * 获取字符串byte[]数据
     */
    public byte[] getByteFromString(String str) {
        if (str == null) {
            return null;
        }
        return str.getBytes();
    }

    /**
     * 根据byte[]数据初始化字符串
     */
    public String getStringFromByte(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return new String(bytes);
    }

    /**
     * 调用对象的toString方法，如果对象为空返回默认值
     *
     * @param object     需要toString的对象
     * @param defaultStr 对象为空时返回的默认值
     * @return 返回对象的toString方法结果
     */
    public String objectToString(Object object, String defaultStr) {
        return org.clever.graaljs.core.utils.StringUtils.objectToString(object, defaultStr);
    }

    /**
     * 除去html标签
     *
     * @param htmlStr 含有html标签的字符串
     * @return 网页文本内容
     */
    public String delHTMLTag(String htmlStr) {
        return org.clever.graaljs.core.utils.StringUtils.delHTMLTag(htmlStr);
    }

    // Empty checks
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 是否是空字符串(“”或null)
     * <pre>
     *  .isEmpty(null)      = true
     *  .isEmpty("")        = true
     *  .isEmpty(" ")       = false
     *  .isEmpty("bob")     = false
     *  .isEmpty("  bob  ") = false
     * </pre>
     */
    public boolean isEmpty(String cs) {
        return org.clever.graaljs.core.utils.StringUtils.isEmpty(cs);
    }

    /**
     * 是否是非空字符串(“”或null)
     * <pre>
     *  .isNotEmpty(null)      = false
     *  .isNotEmpty("")        = false
     *  .isNotEmpty(" ")       = true
     *  .isNotEmpty("bob")     = true
     *  .isNotEmpty("  bob  ") = true
     * </pre>
     */
    public boolean isNotEmpty(String cs) {
        return org.clever.graaljs.core.utils.StringUtils.isNotEmpty(cs);
    }

    /**
     * 是否存在空字符串(“”或null)
     * <pre>
     *  .isAnyEmpty((String) null)    = true
     *  .isAnyEmpty((String[]) null)  = false
     *  .isAnyEmpty(null, "foo")      = true
     *  .isAnyEmpty("", "bar")        = true
     *  .isAnyEmpty("bob", "")        = true
     *  .isAnyEmpty("  bob  ", null)  = true
     *  .isAnyEmpty(" ", "bar")       = false
     *  .isAnyEmpty("foo", "bar")     = false
     *  .isAnyEmpty(new String[]{})   = false
     *  .isAnyEmpty(new String[]{""}) = true
     * </pre>
     */
    public boolean isAnyEmpty(String... css) {
        return org.clever.graaljs.core.utils.StringUtils.isAnyEmpty(css);
    }

    /**
     * 是否存在非空字符串(“”或null)
     * <pre>
     *  .isNoneEmpty((String) null)    = false
     *  .isNoneEmpty((String[]) null)  = true
     *  .isNoneEmpty(null, "foo")      = false
     *  .isNoneEmpty("", "bar")        = false
     *  .isNoneEmpty("bob", "")        = false
     *  .isNoneEmpty("  bob  ", null)  = false
     *  .isNoneEmpty(new String[] {})  = true
     *  .isNoneEmpty(new String[]{""}) = false
     *  .isNoneEmpty(" ", "bar")       = true
     *  .isNoneEmpty("foo", "bar")     = true
     * </pre>
     */
    public boolean isNoneEmpty(String... css) {
        return org.clever.graaljs.core.utils.StringUtils.isNoneEmpty(css);
    }

    /**
     * 是否所有字符串都是空字符串(“”或null)
     * <pre>
     *  .isAllEmpty(null)             = true
     *  .isAllEmpty(null, "")         = true
     *  .isAllEmpty(new String[] {})  = true
     *  .isAllEmpty(null, "foo")      = false
     *  .isAllEmpty("", "bar")        = false
     *  .isAllEmpty("bob", "")        = false
     *  .isAllEmpty("  bob  ", null)  = false
     *  .isAllEmpty(" ", "bar")       = false
     *  .isAllEmpty("foo", "bar")     = false
     * </pre>
     */
    public boolean isAllEmpty(String... css) {
        return org.clever.graaljs.core.utils.StringUtils.isAllEmpty(css);
    }

    /**
     * 是否是空字符串(“”、“ ”或null)
     * <pre>
     *  .isBlank(null)      = true
     *  .isBlank("")        = true
     *  .isBlank(" ")       = true
     *  .isBlank("bob")     = false
     *  .isBlank("  bob  ") = false
     * </pre>
     */
    public boolean isBlank(String cs) {
        return org.clever.graaljs.core.utils.StringUtils.isBlank(cs);
    }

    /**
     * 是否是非空字符串(“”、“ ”或null)
     * <pre>
     *  .isNotBlank(null)      = false
     *  .isNotBlank("")        = false
     *  .isNotBlank(" ")       = false
     *  .isNotBlank("bob")     = true
     *  .isNotBlank("  bob  ") = true
     * </pre>
     */
    public boolean isNotBlank(String cs) {
        return org.clever.graaljs.core.utils.StringUtils.isNotBlank(cs);
    }

    /**
     * 是否存在空字符串(“”、“ ”或null)
     * <pre>
     *  .isAnyBlank((String) null)    = true
     *  .isAnyBlank((String[]) null)  = false
     *  .isAnyBlank(null, "foo")      = true
     *  .isAnyBlank(null, null)       = true
     *  .isAnyBlank("", "bar")        = true
     *  .isAnyBlank("bob", "")        = true
     *  .isAnyBlank("  bob  ", null)  = true
     *  .isAnyBlank(" ", "bar")       = true
     *  .isAnyBlank(new String[] {})  = false
     *  .isAnyBlank(new String[]{""}) = true
     *  .isAnyBlank("foo", "bar")     = false
     * </pre>
     */
    public boolean isAnyBlank(String... css) {
        return org.clever.graaljs.core.utils.StringUtils.isAnyBlank(css);
    }

    /**
     * 是否存在非空字符串(“”、“ ”或null)
     * <pre>
     *  .isNoneBlank((String) null)    = false
     *  .isNoneBlank((String[]) null)  = true
     *  .isNoneBlank(null, "foo")      = false
     *  .isNoneBlank(null, null)       = false
     *  .isNoneBlank("", "bar")        = false
     *  .isNoneBlank("bob", "")        = false
     *  .isNoneBlank("  bob  ", null)  = false
     *  .isNoneBlank(" ", "bar")       = false
     *  .isNoneBlank(new String[] {})  = true
     *  .isNoneBlank(new String[]{""}) = false
     *  .isNoneBlank("foo", "bar")     = true
     * </pre>
     */
    public boolean isNoneBlank(String... css) {
        return org.clever.graaljs.core.utils.StringUtils.isNoneBlank(css);
    }

    /**
     * 是否所有字符串都是空字符串(“”、“ ”或null)
     * <pre>
     *  .isAllBlank(null)             = true
     *  .isAllBlank(null, "foo")      = false
     *  .isAllBlank(null, null)       = true
     *  .isAllBlank("", "bar")        = false
     *  .isAllBlank("bob", "")        = false
     *  .isAllBlank("  bob  ", null)  = false
     *  .isAllBlank(" ", "bar")       = false
     *  .isAllBlank("foo", "bar")     = false
     *  .isAllBlank(new String[] {})  = true
     * </pre>
     */
    public boolean isAllBlank(String... css) {
        return org.clever.graaljs.core.utils.StringUtils.isAllBlank(css);
    }

    // Trim
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * <pre>
     *  .trim(null)          = null
     *  .trim("")            = ""
     *  .trim("     ")       = ""
     *  .trim("abc")         = "abc"
     *  .trim("    abc    ") = "abc"
     * </pre>
     */
    public String trim(String str) {
        return org.clever.graaljs.core.utils.StringUtils.trim(str);
    }

    /**
     * <pre>
     *  .trimToNull(null)          = null
     *  .trimToNull("")            = null
     *  .trimToNull("     ")       = null
     *  .trimToNull("abc")         = "abc"
     *  .trimToNull("    abc    ") = "abc"
     * </pre>
     */
    public String trimToNull(String str) {
        return org.clever.graaljs.core.utils.StringUtils.trimToNull(str);
    }

    /**
     * <pre>
     *  .trimToEmpty(null)          = ""
     *  .trimToEmpty("")            = ""
     *  .trimToEmpty("     ")       = ""
     *  .trimToEmpty("abc")         = "abc"
     *  .trimToEmpty("    abc    ") = "abc"
     * </pre>
     */
    public String trimToEmpty(String str) {
        return org.clever.graaljs.core.utils.StringUtils.trimToEmpty(str);
    }

    /**
     * 截断字符串
     * <pre>
     *  .truncate(null, 0)       = null
     *  .truncate(null, 2)       = null
     *  .truncate("", 4)         = ""
     *  .truncate("abcdefg", 4)  = "abcd"
     *  .truncate("abcdefg", 6)  = "abcdef"
     *  .truncate("abcdefg", 7)  = "abcdefg"
     *  .truncate("abcdefg", 8)  = "abcdefg"
     *  .truncate("abcdefg", -1) = throws an IllegalArgumentException
     * </pre>
     */
    public String truncate(String str, int maxWidth) {
        return org.clever.graaljs.core.utils.StringUtils.truncate(str, maxWidth);
    }

    /**
     * 截断字符串
     *
     * @param str      被截断的字符串
     * @param offset   起始位置
     * @param maxWidth 结果字符串的最大长度
     */
    public String truncate(String str, int offset, int maxWidth) {
        return org.clever.graaljs.core.utils.StringUtils.truncate(str, offset, maxWidth);
    }

    // Stripping
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 从字符串的开始和结尾删除空白
     * <pre>
     *  .strip(null)     = null
     *  .strip("")       = ""
     *  .strip("   ")    = ""
     *  .strip("abc")    = "abc"
     *  .strip("  abc")  = "abc"
     *  .strip("abc  ")  = "abc"
     *  .strip(" abc ")  = "abc"
     *  .strip(" ab c ") = "ab c"
     * </pre>
     */
    public String strip(String str) {
        return org.clever.graaljs.core.utils.StringUtils.strip(str);
    }

    /**
     * 从字符串的开始和结尾去除空白，如果字符串在strip之后为空（“”），则返回null
     * <pre>
     *  .stripToNull(null)     = null
     *  .stripToNull("")       = null
     *  .stripToNull("   ")    = null
     *  .stripToNull("abc")    = "abc"
     *  .stripToNull("  abc")  = "abc"
     *  .stripToNull("abc  ")  = "abc"
     *  .stripToNull(" abc ")  = "abc"
     *  .stripToNull(" ab c ") = "ab c"
     * </pre>
     */
    public String stripToNull(String str) {
        return org.clever.graaljs.core.utils.StringUtils.stripToNull(str);
    }

    /**
     * 从字符串的开始和结尾去除空白，如果字符串在strip之后为null，则返回“”
     * <pre>
     *  .stripToEmpty(null)     = ""
     *  .stripToEmpty("")       = ""
     *  .stripToEmpty("   ")    = ""
     *  .stripToEmpty("abc")    = "abc"
     *  .stripToEmpty("  abc")  = "abc"
     *  .stripToEmpty("abc  ")  = "abc"
     *  .stripToEmpty(" abc ")  = "abc"
     *  .stripToEmpty(" ab c ") = "ab c"
     * </pre>
     */
    public String stripToEmpty(String str) {
        return org.clever.graaljs.core.utils.StringUtils.stripToEmpty(str);
    }

    /**
     * <pre>
     *  .strip(null, *)          = null
     *  .strip("", *)            = ""
     *  .strip("abc", null)      = "abc"
     *  .strip("  abc", null)    = "abc"
     *  .strip("abc  ", null)    = "abc"
     *  .strip(" abc ", null)    = "abc"
     *  .strip("  abcyx", "xyz") = "  abc"
     * </pre>
     *
     * @param str        源字符串
     * @param stripChars 要删除的字符，null被视为空白
     */
    public String strip(String str, String stripChars) {
        return org.clever.graaljs.core.utils.StringUtils.strip(str, stripChars);
    }

    /**
     * 删除开始的空白字符
     * <pre>
     *  .stripStart(null, *)          = null
     *  .stripStart("", *)            = ""
     *  .stripStart("abc", "")        = "abc"
     *  .stripStart("abc", null)      = "abc"
     *  .stripStart("  abc", null)    = "abc"
     *  .stripStart("abc  ", null)    = "abc  "
     *  .stripStart(" abc ", null)    = "abc "
     *  .stripStart("yxabc  ", "xyz") = "abc  "
     * </pre>
     *
     * @param str        源字符串
     * @param stripChars 要删除的字符，null被视为空白
     */
    public String stripStart(String str, String stripChars) {
        return org.clever.graaljs.core.utils.StringUtils.stripStart(str, stripChars);
    }

    /**
     * 删除结束的空白字符
     * <pre>
     *  .stripEnd(null, *)          = null
     *  .stripEnd("", *)            = ""
     *  .stripEnd("abc", "")        = "abc"
     *  .stripEnd("abc", null)      = "abc"
     *  .stripEnd("  abc", null)    = "  abc"
     *  .stripEnd("abc  ", null)    = "abc"
     *  .stripEnd(" abc ", null)    = " abc"
     *  .stripEnd("  abcyx", "xyz") = "  abc"
     *  .stripEnd("120.00", ".0")   = "12"
     * </pre>
     *
     * @param str        源字符串
     * @param stripChars 要删除的字符，null被视为空白
     */
    public String stripEnd(String str, String stripChars) {
        return org.clever.graaljs.core.utils.StringUtils.stripEnd(str, stripChars);
    }

    // StripAll
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * <pre>
     *  .stripAll(null)             = null
     *  .stripAll([])               = []
     *  .stripAll(["abc", "  abc"]) = ["abc", "abc"]
     *  .stripAll(["abc  ", null])  = ["abc", null]
     * </pre>
     */
    public String[] stripAll(String... strs) {
        return org.clever.graaljs.core.utils.StringUtils.stripAll(strs);
    }

    /**
     * <pre>
     *  .stripAll(null, *)                = null
     *  .stripAll([], *)                  = []
     *  .stripAll(["abc", "  abc"], null) = ["abc", "abc"]
     *  .stripAll(["abc  ", null], null)  = ["abc", null]
     *  .stripAll(["abc  ", null], "yz")  = ["abc  ", null]
     *  .stripAll(["yabcz", null], "yz")  = ["abc", null]
     * </pre>
     *
     * @param strs       源字符串数组
     * @param stripChars 要删除的字符，null被视为空白
     */
    public String[] stripAll(String[] strs, String stripChars) {
        return org.clever.graaljs.core.utils.StringUtils.stripAll(strs, stripChars);
    }

    /**
     * 从字符串中删除音调符号。例如，“à”将被“a”替换。
     * <pre>
     *  .stripAccents(null)         = null
     *  .stripAccents("")           = ""
     *  .stripAccents("control")    = "control"
     *  .stripAccents("éclair")     = "eclair"
     * </pre>
     */
    public String stripAccents(String input) {
        return org.clever.graaljs.core.utils.StringUtils.stripAccents(input);
    }

    // Equals
    //-----------------------------------------------------------------------

    /**
     * <pre>
     *  .equals(null, null)   = true
     *  .equals(null, "abc")  = false
     *  .equals("abc", null)  = false
     *  .equals("abc", "abc") = true
     *  .equals("abc", "ABC") = false
     * </pre>
     */
    public boolean equals(CharSequence cs1, CharSequence cs2) {
        return org.clever.graaljs.core.utils.StringUtils.equals(cs1, cs2);
    }

    /**
     * <pre>
     *  .equalsIgnoreCase(null, null)   = true
     *  .equalsIgnoreCase(null, "abc")  = false
     *  .equalsIgnoreCase("abc", null)  = false
     *  .equalsIgnoreCase("abc", "abc") = true
     *  .equalsIgnoreCase("abc", "ABC") = true
     * </pre>
     */
    public boolean equalsIgnoreCase(CharSequence str1, CharSequence str2) {
        return org.clever.graaljs.core.utils.StringUtils.equalsIgnoreCase(str1, str2);
    }

    // Compare
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * <pre>
     *  .compare(null, null)   = 0
     *  .compare(null , "a")   < 0
     *  .compare("a", null)    > 0
     *  .compare("abc", "abc") = 0
     *  .compare("a", "b")     < 0
     *  .compare("b", "a")     > 0
     *  .compare("a", "B")     > 0
     *  .compare("ab", "abc")  < 0
     * </pre>
     */
    public int compare(String str1, String str2) {
        return org.clever.graaljs.core.utils.StringUtils.compare(str1, str2);
    }

    /**
     * <pre>
     *  .compare(null, null, *)     = 0
     *  .compare(null , "a", true)  < 0
     *  .compare(null , "a", false) > 0
     *  .compare("a", null, true)   > 0
     *  .compare("a", null, false)  < 0
     *  .compare("abc", "abc", *)   = 0
     *  .compare("a", "b", *)       < 0
     *  .compare("b", "a", *)       > 0
     *  .compare("a", "B", *)       > 0
     *  .compare("ab", "abc", *)    < 0
     * </pre>
     *
     * @param nullIsLess 是否是空值小于非空值
     */
    public int compare(String str1, String str2, boolean nullIsLess) {
        return org.clever.graaljs.core.utils.StringUtils.compare(str1, str2, nullIsLess);
    }

    /**
     * <pre>
     *  .compareIgnoreCase(null, null)   = 0
     *  .compareIgnoreCase(null , "a")   < 0
     *  .compareIgnoreCase("a", null)    > 0
     *  .compareIgnoreCase("abc", "abc") = 0
     *  .compareIgnoreCase("abc", "ABC") = 0
     *  .compareIgnoreCase("a", "b")     < 0
     *  .compareIgnoreCase("b", "a")     > 0
     *  .compareIgnoreCase("a", "B")     < 0
     *  .compareIgnoreCase("A", "b")     < 0
     *  .compareIgnoreCase("ab", "ABC")  < 0
     * </pre>
     */
    public int compareIgnoreCase(String str1, String str2) {
        return org.clever.graaljs.core.utils.StringUtils.compareIgnoreCase(str1, str2);
    }

    /**
     * <pre>
     *  .compareIgnoreCase(null, null, *)     = 0
     *  .compareIgnoreCase(null , "a", true)  < 0
     *  .compareIgnoreCase(null , "a", false) > 0
     *  .compareIgnoreCase("a", null, true)   > 0
     *  .compareIgnoreCase("a", null, false)  < 0
     *  .compareIgnoreCase("abc", "abc", *)   = 0
     *  .compareIgnoreCase("abc", "ABC", *)   = 0
     *  .compareIgnoreCase("a", "b", *)       < 0
     *  .compareIgnoreCase("b", "a", *)       > 0
     *  .compareIgnoreCase("a", "B", *)       < 0
     *  .compareIgnoreCase("A", "b", *)       < 0
     *  .compareIgnoreCase("ab", "abc", *)    < 0
     * </pre>
     *
     * @param nullIsLess 是否是空值小于非空值
     */
    public int compareIgnoreCase(String str1, String str2, boolean nullIsLess) {
        return org.clever.graaljs.core.utils.StringUtils.compareIgnoreCase(str1, str2, nullIsLess);
    }

    /**
     * 将给定字符串与搜索字符串的CharSequences vararg进行比较，如果字符串等于任何搜索字符串，则返回true
     * <pre>
     *  .equalsAny(null, (CharSequence[]) null) = false
     *  .equalsAny(null, null, null)    = true
     *  .equalsAny(null, "abc", "def")  = false
     *  .equalsAny("abc", null, "def")  = false
     *  .equalsAny("abc", "abc", "def") = true
     *  .equalsAny("abc", "ABC", "DEF") = false
     * </pre>
     */
    public boolean equalsAny(CharSequence string, CharSequence... searchStrings) {
        return org.clever.graaljs.core.utils.StringUtils.equalsAny(string, searchStrings);
    }

    /**
     * 将给定字符串与搜索字符串的CharSequences vararg进行比较，如果字符串等于任何搜索字符串，则返回true。忽略大小写
     * <pre>
     *  .equalsAnyIgnoreCase(null, (CharSequence[]) null) = false
     *  .equalsAnyIgnoreCase(null, null, null)    = true
     *  .equalsAnyIgnoreCase(null, "abc", "def")  = false
     *  .equalsAnyIgnoreCase("abc", null, "def")  = false
     *  .equalsAnyIgnoreCase("abc", "abc", "def") = true
     *  .equalsAnyIgnoreCase("abc", "ABC", "DEF") = true
     * </pre>
     */
    public boolean equalsAnyIgnoreCase(CharSequence string, CharSequence... searchStrings) {
        return org.clever.graaljs.core.utils.StringUtils.equalsAnyIgnoreCase(string, searchStrings);
    }

    // IndexOf
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * <pre>
     *  .indexOf(null, *)         = -1
     *  .indexOf("", *)           = -1
     *  .indexOf("aabaabaa", 'a') = 0
     *  .indexOf("aabaabaa", 'b') = 2
     * </pre>
     */
    public int indexOf(CharSequence seq, int searchChar) {
        return org.clever.graaljs.core.utils.StringUtils.indexOf(seq, searchChar);
    }

    /**
     * <pre>
     *  .indexOf(null, *, *)          = -1
     *  .indexOf("", *, *)            = -1
     *  .indexOf("aabaabaa", 'b', 0)  = 2
     *  .indexOf("aabaabaa", 'b', 3)  = 5
     *  .indexOf("aabaabaa", 'b', 9)  = -1
     *  .indexOf("aabaabaa", 'b', -1) = 2
     * </pre>
     */
    public int indexOf(CharSequence seq, int searchChar, int startPos) {
        return org.clever.graaljs.core.utils.StringUtils.indexOf(seq, searchChar, startPos);
    }

    /**
     * <pre>
     *  .indexOf(null, *)          = -1
     *  .indexOf(*, null)          = -1
     *  .indexOf("", "")           = 0
     *  .indexOf("", *)            = -1 (except when * = "")
     *  .indexOf("aabaabaa", "a")  = 0
     *  .indexOf("aabaabaa", "b")  = 2
     *  .indexOf("aabaabaa", "ab") = 1
     *  .indexOf("aabaabaa", "")   = 0
     * </pre>
     */
    public int indexOf(CharSequence seq, CharSequence searchSeq) {
        return org.clever.graaljs.core.utils.StringUtils.indexOf(seq, searchSeq);
    }

    /**
     * <pre>
     *  .indexOf(null, *, *)          = -1
     *  .indexOf(*, null, *)          = -1
     *  .indexOf("", "", 0)           = 0
     *  .indexOf("", *, 0)            = -1 (except when * = "")
     *  .indexOf("aabaabaa", "a", 0)  = 0
     *  .indexOf("aabaabaa", "b", 0)  = 2
     *  .indexOf("aabaabaa", "ab", 0) = 1
     *  .indexOf("aabaabaa", "b", 3)  = 5
     *  .indexOf("aabaabaa", "b", 9)  = -1
     *  .indexOf("aabaabaa", "b", -1) = 2
     *  .indexOf("aabaabaa", "", 2)   = 2
     *  .indexOf("abc", "", 9)        = 3
     * </pre>
     */
    public int indexOf(CharSequence seq, CharSequence searchSeq, int startPos) {
        return org.clever.graaljs.core.utils.StringUtils.indexOf(seq, searchSeq, startPos);
    }

    /**
     * <pre>
     *  .ordinalIndexOf(null, *, *)          = -1
     *  .ordinalIndexOf(*, null, *)          = -1
     *  .ordinalIndexOf("", "", *)           = 0
     *  .ordinalIndexOf("aabaabaa", "a", 1)  = 0
     *  .ordinalIndexOf("aabaabaa", "a", 2)  = 1
     *  .ordinalIndexOf("aabaabaa", "b", 1)  = 2
     *  .ordinalIndexOf("aabaabaa", "b", 2)  = 5
     *  .ordinalIndexOf("aabaabaa", "ab", 1) = 1
     *  .ordinalIndexOf("aabaabaa", "ab", 2) = 4
     *  .ordinalIndexOf("aabaabaa", "", 1)   = 0
     *  .ordinalIndexOf("aabaabaa", "", 2)   = 0
     * </pre>
     *
     * @param ordinal 要查找的第n个searchStr
     */
    public int ordinalIndexOf(CharSequence str, CharSequence searchStr, int ordinal) {
        return org.clever.graaljs.core.utils.StringUtils.ordinalIndexOf(str, searchStr, ordinal);
    }

    /**
     * <pre>
     *  .indexOfIgnoreCase(null, *)          = -1
     *  .indexOfIgnoreCase(*, null)          = -1
     *  .indexOfIgnoreCase("", "")           = 0
     *  .indexOfIgnoreCase("aabaabaa", "a")  = 0
     *  .indexOfIgnoreCase("aabaabaa", "b")  = 2
     *  .indexOfIgnoreCase("aabaabaa", "ab") = 1
     * </pre>
     */
    public int indexOfIgnoreCase(CharSequence str, CharSequence searchStr) {
        return org.clever.graaljs.core.utils.StringUtils.indexOfIgnoreCase(str, searchStr);
    }

    /**
     * <pre>
     *  .indexOfIgnoreCase(null, *, *)          = -1
     *  .indexOfIgnoreCase(*, null, *)          = -1
     *  .indexOfIgnoreCase("", "", 0)           = 0
     *  .indexOfIgnoreCase("aabaabaa", "A", 0)  = 0
     *  .indexOfIgnoreCase("aabaabaa", "B", 0)  = 2
     *  .indexOfIgnoreCase("aabaabaa", "AB", 0) = 1
     *  .indexOfIgnoreCase("aabaabaa", "B", 3)  = 5
     *  .indexOfIgnoreCase("aabaabaa", "B", 9)  = -1
     *  .indexOfIgnoreCase("aabaabaa", "B", -1) = 2
     *  .indexOfIgnoreCase("aabaabaa", "", 2)   = 2
     *  .indexOfIgnoreCase("abc", "", 9)        = -1
     * </pre>
     */
    public int indexOfIgnoreCase(CharSequence str, CharSequence searchStr, int startPos) {
        return org.clever.graaljs.core.utils.StringUtils.indexOfIgnoreCase(str, searchStr, startPos);
    }

    // LastIndexOf
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * <pre>
     *  .lastIndexOf(null, *)         = -1
     *  .lastIndexOf("", *)           = -1
     *  .lastIndexOf("aabaabaa", 'a') = 7
     *  .lastIndexOf("aabaabaa", 'b') = 5
     * </pre>
     */
    public int lastIndexOf(CharSequence seq, int searchChar) {
        return org.clever.graaljs.core.utils.StringUtils.lastIndexOf(seq, searchChar);
    }

    /**
     * <pre>
     *  .lastIndexOf(null, *, *)          = -1
     *  .lastIndexOf("", *,  *)           = -1
     *  .lastIndexOf("aabaabaa", 'b', 8)  = 5
     *  .lastIndexOf("aabaabaa", 'b', 4)  = 2
     *  .lastIndexOf("aabaabaa", 'b', 0)  = -1
     *  .lastIndexOf("aabaabaa", 'b', 9)  = 5
     *  .lastIndexOf("aabaabaa", 'b', -1) = -1
     *  .lastIndexOf("aabaabaa", 'a', 0)  = 0
     * </pre>
     */
    public int lastIndexOf(CharSequence seq, int searchChar, int startPos) {
        return org.clever.graaljs.core.utils.StringUtils.lastIndexOf(seq, searchChar, startPos);
    }

    /**
     * <pre>
     *  .lastIndexOf(null, *)          = -1
     *  .lastIndexOf(*, null)          = -1
     *  .lastIndexOf("", "")           = 0
     *  .lastIndexOf("aabaabaa", "a")  = 7
     *  .lastIndexOf("aabaabaa", "b")  = 5
     *  .lastIndexOf("aabaabaa", "ab") = 4
     *  .lastIndexOf("aabaabaa", "")   = 8
     * </pre>
     */
    public int lastIndexOf(CharSequence seq, CharSequence searchSeq) {
        return org.clever.graaljs.core.utils.StringUtils.lastIndexOf(seq, searchSeq);
    }

    /**
     * <pre>
     *  .lastOrdinalIndexOf(null, *, *)          = -1
     *  .lastOrdinalIndexOf(*, null, *)          = -1
     *  .lastOrdinalIndexOf("", "", *)           = 0
     *  .lastOrdinalIndexOf("aabaabaa", "a", 1)  = 7
     *  .lastOrdinalIndexOf("aabaabaa", "a", 2)  = 6
     *  .lastOrdinalIndexOf("aabaabaa", "b", 1)  = 5
     *  .lastOrdinalIndexOf("aabaabaa", "b", 2)  = 2
     *  .lastOrdinalIndexOf("aabaabaa", "ab", 1) = 4
     *  .lastOrdinalIndexOf("aabaabaa", "ab", 2) = 1
     *  .lastOrdinalIndexOf("aabaabaa", "", 1)   = 8
     *  .lastOrdinalIndexOf("aabaabaa", "", 2)   = 8
     * </pre>
     *
     * @param ordinal 要查找的第n个searchStr
     */
    public int lastOrdinalIndexOf(CharSequence str, CharSequence searchStr, int ordinal) {
        return org.clever.graaljs.core.utils.StringUtils.lastOrdinalIndexOf(str, searchStr, ordinal);
    }

    /**
     * <pre>
     *  .lastIndexOf(null, *, *)          = -1
     *  .lastIndexOf(*, null, *)          = -1
     *  .lastIndexOf("aabaabaa", "a", 8)  = 7
     *  .lastIndexOf("aabaabaa", "b", 8)  = 5
     *  .lastIndexOf("aabaabaa", "ab", 8) = 4
     *  .lastIndexOf("aabaabaa", "b", 9)  = 5
     *  .lastIndexOf("aabaabaa", "b", -1) = -1
     *  .lastIndexOf("aabaabaa", "a", 0)  = 0
     *  .lastIndexOf("aabaabaa", "b", 0)  = -1
     *  .lastIndexOf("aabaabaa", "b", 1)  = -1
     *  .lastIndexOf("aabaabaa", "b", 2)  = 2
     *  .lastIndexOf("aabaabaa", "ba", 2)  = 2
     * </pre>
     */
    public int lastIndexOf(CharSequence seq, CharSequence searchSeq, int startPos) {
        return org.clever.graaljs.core.utils.StringUtils.lastIndexOf(seq, searchSeq, startPos);
    }

    /**
     * <pre>
     *  .lastIndexOfIgnoreCase(null, *)          = -1
     *  .lastIndexOfIgnoreCase(*, null)          = -1
     *  .lastIndexOfIgnoreCase("aabaabaa", "A")  = 7
     *  .lastIndexOfIgnoreCase("aabaabaa", "B")  = 5
     *  .lastIndexOfIgnoreCase("aabaabaa", "AB") = 4
     * </pre>
     */
    public int lastIndexOfIgnoreCase(CharSequence str, CharSequence searchStr) {
        return org.clever.graaljs.core.utils.StringUtils.lastIndexOfIgnoreCase(str, searchStr);
    }

    /**
     * <pre>
     *  .lastIndexOfIgnoreCase(null, *, *)          = -1
     *  .lastIndexOfIgnoreCase(*, null, *)          = -1
     *  .lastIndexOfIgnoreCase("aabaabaa", "A", 8)  = 7
     *  .lastIndexOfIgnoreCase("aabaabaa", "B", 8)  = 5
     *  .lastIndexOfIgnoreCase("aabaabaa", "AB", 8) = 4
     *  .lastIndexOfIgnoreCase("aabaabaa", "B", 9)  = 5
     *  .lastIndexOfIgnoreCase("aabaabaa", "B", -1) = -1
     *  .lastIndexOfIgnoreCase("aabaabaa", "A", 0)  = 0
     *  .lastIndexOfIgnoreCase("aabaabaa", "B", 0)  = -1
     * </pre>
     */
    public int lastIndexOfIgnoreCase(CharSequence str, CharSequence searchStr, int startPos) {
        return org.clever.graaljs.core.utils.StringUtils.lastIndexOfIgnoreCase(str, searchStr, startPos);
    }

    // Contains
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * <pre>
     *  .contains(null, *)    = false
     *  .contains("", *)      = false
     *  .contains("abc", 'a') = true
     *  .contains("abc", 'z') = false
     * </pre>
     */
    public boolean contains(CharSequence seq, int searchChar) {
        return org.clever.graaljs.core.utils.StringUtils.contains(seq, searchChar);
    }

    /**
     * <pre>
     *  .contains(null, *)     = false
     *  .contains(*, null)     = false
     *  .contains("", "")      = true
     *  .contains("abc", "")   = true
     *  .contains("abc", "a")  = true
     *  .contains("abc", "z")  = false
     * </pre>
     */
    public boolean contains(CharSequence seq, CharSequence searchSeq) {
        return org.clever.graaljs.core.utils.StringUtils.contains(seq, searchSeq);
    }

    /**
     * <pre>
     *  .containsIgnoreCase(null, *)    = false
     *  .containsIgnoreCase(*, null)    = false
     *  .containsIgnoreCase("", "")     = true
     *  .containsIgnoreCase("abc", "")  = true
     *  .containsIgnoreCase("abc", "a") = true
     *  .containsIgnoreCase("abc", "z") = false
     *  .containsIgnoreCase("abc", "A") = true
     *  .containsIgnoreCase("abc", "Z") = false
     * </pre>
     */
    public boolean containsIgnoreCase(CharSequence str, CharSequence searchStr) {
        return org.clever.graaljs.core.utils.StringUtils.containsIgnoreCase(str, searchStr);
    }

    /**
     * 检查给定的CharSequence是否包含任何空白字符
     */
    public boolean containsWhitespace(CharSequence seq) {
        return org.clever.graaljs.core.utils.StringUtils.containsWhitespace(seq);
    }

    /**
     * <pre>
     *  .indexOfAny(null, *)                = -1
     *  .indexOfAny("", *)                  = -1
     *  .indexOfAny(*, null)                = -1
     *  .indexOfAny(*, [])                  = -1
     *  .indexOfAny("zzabyycdxx",['z','a']) = 0
     *  .indexOfAny("zzabyycdxx",['b','y']) = 3
     *  .indexOfAny("aba", ['z'])           = -1
     * </pre>
     */
    public int indexOfAny(CharSequence cs, char... searchChars) {
        return org.clever.graaljs.core.utils.StringUtils.indexOfAny(cs, searchChars);
    }

    /**
     * <pre>
     *  .indexOfAny(null, *)            = -1
     *  .indexOfAny("", *)              = -1
     *  .indexOfAny(*, null)            = -1
     *  .indexOfAny(*, "")              = -1
     *  .indexOfAny("zzabyycdxx", "za") = 0
     *  .indexOfAny("zzabyycdxx", "by") = 3
     *  .indexOfAny("aba","z")          = -1
     * </pre>
     */
    public int indexOfAny(CharSequence cs, String searchChars) {
        return org.clever.graaljs.core.utils.StringUtils.indexOfAny(cs, searchChars);
    }

    /**
     * <pre>
     *  .containsAny(null, *)                = false
     *  .containsAny("", *)                  = false
     *  .containsAny(*, null)                = false
     *  .containsAny(*, [])                  = false
     *  .containsAny("zzabyycdxx",['z','a']) = true
     *  .containsAny("zzabyycdxx",['b','y']) = true
     *  .containsAny("zzabyycdxx",['z','y']) = true
     *  .containsAny("aba", ['z'])           = false
     * </pre>
     */
    public boolean containsAny(CharSequence cs, char... searchChars) {
        return org.clever.graaljs.core.utils.StringUtils.containsAny(cs, searchChars);
    }

    /**
     * <pre>
     *  .containsAny(null, *)               = false
     *  .containsAny("", *)                 = false
     *  .containsAny(*, null)               = false
     *  .containsAny(*, "")                 = false
     *  .containsAny("zzabyycdxx", "za")    = true
     *  .containsAny("zzabyycdxx", "by")    = true
     *  .containsAny("zzabyycdxx", "zy")    = true
     *  .containsAny("zzabyycdxx", "\tx")   = true
     *  .containsAny("zzabyycdxx", "$.#yF") = true
     *  .containsAny("aba","z")             = false
     * </pre>
     */
    public boolean containsAny(CharSequence cs, CharSequence searchChars) {
        return org.clever.graaljs.core.utils.StringUtils.containsAny(cs, searchChars);
    }

    /**
     * <pre>
     *  .containsAny(null, *)            = false
     *  .containsAny("", *)              = false
     *  .containsAny(*, null)            = false
     *  .containsAny(*, [])              = false
     *  .containsAny("abcd", "ab", null) = true
     *  .containsAny("abcd", "ab", "cd") = true
     *  .containsAny("abc", "d", "abc")  = true
     * </pre>
     */
    public boolean containsAny(CharSequence cs, CharSequence... searchCharSequences) {
        return org.clever.graaljs.core.utils.StringUtils.containsAny(cs, searchCharSequences);
    }

    // IndexOfAnyBut chars
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 搜索CharSequence以查找不在给定字符集中的任何字符的第一个索引。空的CharSequence将返回-1。空搜索字符串将返回-1。
     * <pre>
     *  .indexOfAnyBut(null, *)                              = -1
     *  .indexOfAnyBut("", *)                                = -1
     *  .indexOfAnyBut(*, null)                              = -1
     *  .indexOfAnyBut(*, [])                                = -1
     *  .indexOfAnyBut("zzabyycdxx", new char[] {'z', 'a'} ) = 3
     *  .indexOfAnyBut("aba", new char[] {'z'} )             = 0
     *  .indexOfAnyBut("aba", new char[] {'a', 'b'} )        = -1
     * </pre>
     */
    public int indexOfAnyBut(CharSequence cs, char... searchChars) {
        return org.clever.graaljs.core.utils.StringUtils.indexOfAnyBut(cs, searchChars);
    }

    /**
     * 搜索CharSequence以查找不在给定字符集中的任何字符的第一个索引。空的CharSequence将返回-1。空搜索字符串将返回-1。
     * <pre>
     *  .indexOfAnyBut(null, *)            = -1
     *  .indexOfAnyBut("", *)              = -1
     *  .indexOfAnyBut(*, null)            = -1
     *  .indexOfAnyBut(*, "")              = -1
     *  .indexOfAnyBut("zzabyycdxx", "za") = 3
     *  .indexOfAnyBut("zzabyycdxx", "")   = -1
     *  .indexOfAnyBut("aba","ab")         = -1
     * </pre>
     */
    public int indexOfAnyBut(CharSequence seq, CharSequence searchChars) {
        return org.clever.graaljs.core.utils.StringUtils.indexOfAnyBut(seq, searchChars);
    }

    // ContainsOnly
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 检查CharSequence是否只包含某些字符
     * <pre>
     *  .containsOnly(null, *)       = false
     *  .containsOnly(*, null)       = false
     *  .containsOnly("", *)         = true
     *  .containsOnly("ab", '')      = false
     *  .containsOnly("abab", 'abc') = true
     *  .containsOnly("ab1", 'abc')  = false
     *  .containsOnly("abz", 'abc')  = false
     * </pre>
     */
    public boolean containsOnly(CharSequence cs, char... valid) {
        return org.clever.graaljs.core.utils.StringUtils.containsOnly(cs, valid);
    }

    /**
     * 检查CharSequence是否只包含某些字符
     * <pre>
     *  .containsOnly(null, *)       = false
     *  .containsOnly(*, null)       = false
     *  .containsOnly("", *)         = true
     *  .containsOnly("ab", "")      = false
     *  .containsOnly("abab", "abc") = true
     *  .containsOnly("ab1", "abc")  = false
     *  .containsOnly("abz", "abc")  = false
     * </pre>
     */
    public boolean containsOnly(CharSequence cs, String validChars) {
        return org.clever.graaljs.core.utils.StringUtils.containsOnly(cs, validChars);
    }

    // ContainsNone
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 检查CharSequence是否不包含某些字符
     * <pre>
     *  .containsNone(null, *)       = true
     *  .containsNone(*, null)       = true
     *  .containsNone("", *)         = true
     *  .containsNone("ab", '')      = true
     *  .containsNone("abab", 'xyz') = true
     *  .containsNone("ab1", 'xyz')  = true
     *  .containsNone("abz", 'xyz')  = false
     * </pre>
     */
    public boolean containsNone(CharSequence cs, char... searchChars) {
        return org.clever.graaljs.core.utils.StringUtils.containsNone(cs, searchChars);
    }

    /**
     * 检查CharSequence是否不包含某些字符
     * <pre>
     *  .containsNone(null, *)       = true
     *  .containsNone(*, null)       = true
     *  .containsNone("", *)         = true
     *  .containsNone("ab", "")      = true
     *  .containsNone("abab", "xyz") = true
     *  .containsNone("ab1", "xyz")  = true
     *  .containsNone("abz", "xyz")  = false
     * </pre>
     */
    public boolean containsNone(CharSequence cs, String invalidChars) {
        return org.clever.graaljs.core.utils.StringUtils.containsNone(cs, invalidChars);
    }

    // IndexOfAny strings
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * <pre>
     *  .indexOfAny(null, *)                     = -1
     *  .indexOfAny(*, null)                     = -1
     *  .indexOfAny(*, [])                       = -1
     *  .indexOfAny("zzabyycdxx", ["ab","cd"])   = 2
     *  .indexOfAny("zzabyycdxx", ["cd","ab"])   = 2
     *  .indexOfAny("zzabyycdxx", ["mn","op"])   = -1
     *  .indexOfAny("zzabyycdxx", ["zab","aby"]) = 1
     *  .indexOfAny("zzabyycdxx", [""])          = 0
     *  .indexOfAny("", [""])                    = 0
     *  .indexOfAny("", ["a"])                   = -1
     * </pre>
     */
    public int indexOfAny(CharSequence str, CharSequence... searchStrs) {
        return org.clever.graaljs.core.utils.StringUtils.indexOfAny(str, searchStrs);
    }

    /**
     * <pre>
     *  .lastIndexOfAny(null, *)                   = -1
     *  .lastIndexOfAny(*, null)                   = -1
     *  .lastIndexOfAny(*, [])                     = -1
     *  .lastIndexOfAny(*, [null])                 = -1
     *  .lastIndexOfAny("zzabyycdxx", ["ab","cd"]) = 6
     *  .lastIndexOfAny("zzabyycdxx", ["cd","ab"]) = 6
     *  .lastIndexOfAny("zzabyycdxx", ["mn","op"]) = -1
     *  .lastIndexOfAny("zzabyycdxx", ["mn","op"]) = -1
     *  .lastIndexOfAny("zzabyycdxx", ["mn",""])   = 10
     * </pre>
     */
    public int lastIndexOfAny(CharSequence str, CharSequence... searchStrs) {
        return org.clever.graaljs.core.utils.StringUtils.lastIndexOfAny(str, searchStrs);
    }

    // Substring
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * <pre>
     *  .substring(null, *)   = null
     *  .substring("", *)     = ""
     *  .substring("abc", 0)  = "abc"
     *  .substring("abc", 2)  = "c"
     *  .substring("abc", 4)  = ""
     *  .substring("abc", -2) = "bc"
     *  .substring("abc", -4) = "abc"
     * </pre>
     */
    public String substring(String str, int start) {
        return org.clever.graaljs.core.utils.StringUtils.substring(str, start);
    }

    /**
     * <pre>
     *  .substring(null, *, *)    = null
     *  .substring("", * ,  *)    = "";
     *  .substring("abc", 0, 2)   = "ab"
     *  .substring("abc", 2, 0)   = ""
     *  .substring("abc", 2, 4)   = "c"
     *  .substring("abc", 4, 6)   = ""
     *  .substring("abc", 2, 2)   = ""
     *  .substring("abc", -2, -1) = "b"
     *  .substring("abc", -4, 2)  = "ab"
     * </pre>
     */
    public String substring(String str, int start, int end) {
        return org.clever.graaljs.core.utils.StringUtils.substring(str, start, end);
    }

    // Left/Right/Mid
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * <pre>
     *  .left(null, *)    = null
     *  .left(*, -ve)     = ""
     *  .left("", *)      = ""
     *  .left("abc", 0)   = ""
     *  .left("abc", 2)   = "ab"
     *  .left("abc", 4)   = "abc"
     * </pre>
     */
    public String left(String str, int len) {
        return org.clever.graaljs.core.utils.StringUtils.left(str, len);
    }

    /**
     * <pre>
     *  .right(null, *)    = null
     *  .right(*, -ve)     = ""
     *  .right("", *)      = ""
     *  .right("abc", 0)   = ""
     *  .right("abc", 2)   = "bc"
     *  .right("abc", 4)   = "abc"
     * </pre>
     */
    public String right(String str, int len) {
        return org.clever.graaljs.core.utils.StringUtils.right(str, len);
    }

    /**
     * <pre>
     *  .mid(null, *, *)    = null
     *  .mid(*, *, -ve)     = ""
     *  .mid("", 0, *)      = ""
     *  .mid("abc", 0, 2)   = "ab"
     *  .mid("abc", 0, 4)   = "abc"
     *  .mid("abc", 2, 4)   = "c"
     *  .mid("abc", 4, 2)   = ""
     *  .mid("abc", -2, 2)  = "ab"
     * </pre>
     */
    public String mid(String str, int pos, int len) {
        return org.clever.graaljs.core.utils.StringUtils.mid(str, pos, len);
    }

    // SubStringAfter/SubStringBefore
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 获取第一次出现分隔符之前的子字符串
     * <pre>
     *  .substringBefore(null, *)      = null
     *  .substringBefore("", *)        = ""
     *  .substringBefore("abc", "a")   = ""
     *  .substringBefore("abcba", "b") = "a"
     *  .substringBefore("abc", "c")   = "ab"
     *  .substringBefore("abc", "d")   = "abc"
     *  .substringBefore("abc", "")    = ""
     *  .substringBefore("abc", null)  = "abc"
     * </pre>
     */
    public String substringBefore(String str, String separator) {
        return org.clever.graaljs.core.utils.StringUtils.substringBefore(str, separator);
    }

    /**
     * 获取第一次出现分隔符后的子字符串
     * <pre>
     *  .substringAfter(null, *)      = null
     *  .substringAfter("", *)        = ""
     *  .substringAfter(*, null)      = ""
     *  .substringAfter("abc", "a")   = "bc"
     *  .substringAfter("abcba", "b") = "cba"
     *  .substringAfter("abc", "c")   = ""
     *  .substringAfter("abc", "d")   = ""
     *  .substringAfter("abc", "")    = "abc"
     * </pre>
     */
    public String substringAfter(String str, String separator) {
        return org.clever.graaljs.core.utils.StringUtils.substringAfter(str, separator);
    }

    /**
     * 获取最后一次出现分隔符之前的子字符串
     * <pre>
     *  .substringBeforeLast(null, *)      = null
     *  .substringBeforeLast("", *)        = ""
     *  .substringBeforeLast("abcba", "b") = "abc"
     *  .substringBeforeLast("abc", "c")   = "ab"
     *  .substringBeforeLast("a", "a")     = ""
     *  .substringBeforeLast("a", "z")     = "a"
     *  .substringBeforeLast("a", null)    = "a"
     *  .substringBeforeLast("a", "")      = "a"
     * </pre>
     */
    public String substringBeforeLast(String str, String separator) {
        return org.clever.graaljs.core.utils.StringUtils.substringBeforeLast(str, separator);
    }

    /**
     * 获取最后一次出现分隔符后的子字符串
     * <pre>
     *  .substringAfterLast(null, *)      = null
     *  .substringAfterLast("", *)        = ""
     *  .substringAfterLast(*, "")        = ""
     *  .substringAfterLast(*, null)      = ""
     *  .substringAfterLast("abc", "a")   = "bc"
     *  .substringAfterLast("abcba", "b") = "a"
     *  .substringAfterLast("abc", "c")   = ""
     *  .substringAfterLast("a", "a")     = ""
     *  .substringAfterLast("a", "z")     = ""
     * </pre>
     */
    public String substringAfterLast(String str, String separator) {
        return org.clever.graaljs.core.utils.StringUtils.substringAfterLast(str, separator);
    }

    // Substring between
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 获取嵌套在同一字符串的两个实例之间的字符串
     * <pre>
     *  .substringBetween(null, *)            = null
     *  .substringBetween("", "")             = ""
     *  .substringBetween("", "tag")          = null
     *  .substringBetween("tagabctag", null)  = null
     *  .substringBetween("tagabctag", "")    = ""
     *  .substringBetween("tagabctag", "tag") = "abc"
     * </pre>
     */
    public String substringBetween(String str, String tag) {
        return org.clever.graaljs.core.utils.StringUtils.substringBetween(str, tag);
    }

    /**
     * 获取嵌套在两个字符串之间的字符串。只返回第一个匹配项
     * <pre>
     *  .substringBetween("wx[b]yz", "[", "]") = "b"
     *  .substringBetween(null, *, *)          = null
     *  .substringBetween(*, null, *)          = null
     *  .substringBetween(*, *, null)          = null
     *  .substringBetween("", "", "")          = ""
     *  .substringBetween("", "", "]")         = null
     *  .substringBetween("", "[", "]")        = null
     *  .substringBetween("yabcz", "", "")     = ""
     *  .substringBetween("yabcz", "y", "z")   = "abc"
     *  .substringBetween("yabczyabcz", "y", "z")   = "abc"
     * </pre>
     */
    public String substringBetween(String str, String open, String close) {
        return org.clever.graaljs.core.utils.StringUtils.substringBetween(str, open, close);
    }

    /**
     * 在字符串中搜索由开始和结束标记分隔的子字符串，返回数组中所有匹配的子字符串
     * <pre>
     *  .substringsBetween("[a][b][c]", "[", "]") = ["a","b","c"]
     *  .substringsBetween(null, *, *)            = null
     *  .substringsBetween(*, null, *)            = null
     *  .substringsBetween(*, *, null)            = null
     *  .substringsBetween("", "[", "]")          = []
     * </pre>
     */
    public String[] substringsBetween(String str, String open, String close) {
        return org.clever.graaljs.core.utils.StringUtils.substringsBetween(str, open, close);
    }

    // Nested extraction
    //----------------------------------------------------------------------------------------------------------------------------------------------

    // Splitting
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 使用空格作为分隔符，将提供的文本拆分为数组
     * <pre>
     *  .split(null)       = null
     *  .split("")         = []
     *  .split("abc def")  = ["abc", "def"]
     *  .split("abc  def") = ["abc", "def"]
     *  .split(" abc ")    = ["abc"]
     * </pre>
     */
    public String[] split(String str) {
        return org.clever.graaljs.core.utils.StringUtils.split(str);
    }

    /**
     * <pre>
     *  .split(null, *)         = null
     *  .split("", *)           = []
     *  .split("a.b.c", '.')    = ["a", "b", "c"]
     *  .split("a..b.c", '.')   = ["a", "b", "c"]
     *  .split("a:b:c", '.')    = ["a:b:c"]
     *  .split("a b c", ' ')    = ["a", "b", "c"]
     * </pre>
     */
    public String[] split(String str, char separatorChar) {
        return org.clever.graaljs.core.utils.StringUtils.split(str, separatorChar);
    }

    /**
     * <pre>
     *  .split(null, *)         = null
     *  .split("", *)           = []
     *  .split("abc def", null) = ["abc", "def"]
     *  .split("abc def", " ")  = ["abc", "def"]
     *  .split("abc  def", " ") = ["abc", "def"]
     *  .split("ab:cd:ef", ":") = ["ab", "cd", "ef"]
     * </pre>
     */
    public String[] split(String str, String separatorChars) {
        return org.clever.graaljs.core.utils.StringUtils.split(str, separatorChars);
    }

    /**
     * <pre>
     *  .split(null, *, *)            = null
     *  .split("", *, *)              = []
     *  .split("ab cd ef", null, 0)   = ["ab", "cd", "ef"]
     *  .split("ab   cd ef", null, 0) = ["ab", "cd", "ef"]
     *  .split("ab:cd:ef", ":", 0)    = ["ab", "cd", "ef"]
     *  .split("ab:cd:ef", ":", 2)    = ["ab", "cd:ef"]
     * </pre>
     */
    public String[] split(String str, String separatorChars, int max) {
        return org.clever.graaljs.core.utils.StringUtils.split(str, separatorChars, max);
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * <pre>
     *  .splitByWholeSeparator(null, *)               = null
     *  .splitByWholeSeparator("", *)                 = []
     *  .splitByWholeSeparator("ab de fg", null)      = ["ab", "de", "fg"]
     *  .splitByWholeSeparator("ab   de fg", null)    = ["ab", "de", "fg"]
     *  .splitByWholeSeparator("ab:cd:ef", ":")       = ["ab", "cd", "ef"]
     *  .splitByWholeSeparator("ab-!-cd-!-ef", "-!-") = ["ab", "cd", "ef"]
     * </pre>
     */
    public String[] splitByWholeSeparator(String str, String separator) {
        return org.clever.graaljs.core.utils.StringUtils.splitByWholeSeparator(str, separator);
    }

    /**
     * <pre>
     *  .splitByWholeSeparator(null, *, *)               = null
     *  .splitByWholeSeparator("", *, *)                 = []
     *  .splitByWholeSeparator("ab de fg", null, 0)      = ["ab", "de", "fg"]
     *  .splitByWholeSeparator("ab   de fg", null, 0)    = ["ab", "de", "fg"]
     *  .splitByWholeSeparator("ab:cd:ef", ":", 2)       = ["ab", "cd:ef"]
     *  .splitByWholeSeparator("ab-!-cd-!-ef", "-!-", 5) = ["ab", "cd", "ef"]
     *  .splitByWholeSeparator("ab-!-cd-!-ef", "-!-", 2) = ["ab", "cd-!-ef"]
     * </pre>
     */
    public String[] splitByWholeSeparator(String str, String separator, int max) {
        return org.clever.graaljs.core.utils.StringUtils.splitByWholeSeparator(str, separator, max);
    }

    /**
     * <pre>
     *  .splitByWholeSeparatorPreserveAllTokens(null, *)               = null
     *  .splitByWholeSeparatorPreserveAllTokens("", *)                 = []
     *  .splitByWholeSeparatorPreserveAllTokens("ab de fg", null)      = ["ab", "de", "fg"]
     *  .splitByWholeSeparatorPreserveAllTokens("ab   de fg", null)    = ["ab", "", "", "de", "fg"]
     *  .splitByWholeSeparatorPreserveAllTokens("ab:cd:ef", ":")       = ["ab", "cd", "ef"]
     *  .splitByWholeSeparatorPreserveAllTokens("ab-!-cd-!-ef", "-!-") = ["ab", "cd", "ef"]
     * </pre>
     */
    public String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator) {
        return org.clever.graaljs.core.utils.StringUtils.splitByWholeSeparatorPreserveAllTokens(str, separator);
    }

    /**
     * <pre>
     *  .splitByWholeSeparatorPreserveAllTokens(null, *, *)               = null
     *  .splitByWholeSeparatorPreserveAllTokens("", *, *)                 = []
     *  .splitByWholeSeparatorPreserveAllTokens("ab de fg", null, 0)      = ["ab", "de", "fg"]
     *  .splitByWholeSeparatorPreserveAllTokens("ab   de fg", null, 0)    = ["ab", "", "", "de", "fg"]
     *  .splitByWholeSeparatorPreserveAllTokens("ab:cd:ef", ":", 2)       = ["ab", "cd:ef"]
     *  .splitByWholeSeparatorPreserveAllTokens("ab-!-cd-!-ef", "-!-", 5) = ["ab", "cd", "ef"]
     *  .splitByWholeSeparatorPreserveAllTokens("ab-!-cd-!-ef", "-!-", 2) = ["ab", "cd-!-ef"]
     * </pre>
     */
    public String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator, int max) {
        return org.clever.graaljs.core.utils.StringUtils.splitByWholeSeparatorPreserveAllTokens(str, separator, max);
    }

    /**
     * <pre>
     *  .splitPreserveAllTokens(null)       = null
     *  .splitPreserveAllTokens("")         = []
     *  .splitPreserveAllTokens("abc def")  = ["abc", "def"]
     *  .splitPreserveAllTokens("abc  def") = ["abc", "", "def"]
     *  .splitPreserveAllTokens(" abc ")    = ["", "abc", ""]
     * </pre>
     */
    public String[] splitPreserveAllTokens(String str) {
        return org.clever.graaljs.core.utils.StringUtils.splitPreserveAllTokens(str);
    }

    /**
     * <pre>
     *  .splitPreserveAllTokens(null, *)         = null
     *  .splitPreserveAllTokens("", *)           = []
     *  .splitPreserveAllTokens("a.b.c", '.')    = ["a", "b", "c"]
     *  .splitPreserveAllTokens("a..b.c", '.')   = ["a", "", "b", "c"]
     *  .splitPreserveAllTokens("a:b:c", '.')    = ["a:b:c"]
     *  .splitPreserveAllTokens("a\tb\nc", null) = ["a", "b", "c"]
     *  .splitPreserveAllTokens("a b c", ' ')    = ["a", "b", "c"]
     *  .splitPreserveAllTokens("a b c ", ' ')   = ["a", "b", "c", ""]
     *  .splitPreserveAllTokens("a b c  ", ' ')   = ["a", "b", "c", "", ""]
     *  .splitPreserveAllTokens(" a b c", ' ')   = ["", a", "b", "c"]
     *  .splitPreserveAllTokens("  a b c", ' ')  = ["", "", a", "b", "c"]
     *  .splitPreserveAllTokens(" a b c ", ' ')  = ["", a", "b", "c", ""]
     * </pre>
     */
    public String[] splitPreserveAllTokens(String str, char separatorChar) {
        return org.clever.graaljs.core.utils.StringUtils.splitPreserveAllTokens(str, separatorChar);
    }


    /**
     * <pre>
     *  .splitPreserveAllTokens(null, *)           = null
     *  .splitPreserveAllTokens("", *)             = []
     *  .splitPreserveAllTokens("abc def", null)   = ["abc", "def"]
     *  .splitPreserveAllTokens("abc def", " ")    = ["abc", "def"]
     *  .splitPreserveAllTokens("abc  def", " ")   = ["abc", "", def"]
     *  .splitPreserveAllTokens("ab:cd:ef", ":")   = ["ab", "cd", "ef"]
     *  .splitPreserveAllTokens("ab:cd:ef:", ":")  = ["ab", "cd", "ef", ""]
     *  .splitPreserveAllTokens("ab:cd:ef::", ":") = ["ab", "cd", "ef", "", ""]
     *  .splitPreserveAllTokens("ab::cd:ef", ":")  = ["ab", "", cd", "ef"]
     *  .splitPreserveAllTokens(":cd:ef", ":")     = ["", cd", "ef"]
     *  .splitPreserveAllTokens("::cd:ef", ":")    = ["", "", cd", "ef"]
     *  .splitPreserveAllTokens(":cd:ef:", ":")    = ["", cd", "ef", ""]
     * </pre>
     */
    public String[] splitPreserveAllTokens(String str, String separatorChars) {
        return org.clever.graaljs.core.utils.StringUtils.splitPreserveAllTokens(str, separatorChars);
    }

    /**
     * <pre>
     *  .splitPreserveAllTokens(null, *, *)            = null
     *  .splitPreserveAllTokens("", *, *)              = []
     *  .splitPreserveAllTokens("ab de fg", null, 0)   = ["ab", "cd", "ef"]
     *  .splitPreserveAllTokens("ab   de fg", null, 0) = ["ab", "cd", "ef"]
     *  .splitPreserveAllTokens("ab:cd:ef", ":", 0)    = ["ab", "cd", "ef"]
     *  .splitPreserveAllTokens("ab:cd:ef", ":", 2)    = ["ab", "cd:ef"]
     *  .splitPreserveAllTokens("ab   de fg", null, 2) = ["ab", "  de fg"]
     *  .splitPreserveAllTokens("ab   de fg", null, 3) = ["ab", "", " de fg"]
     *  .splitPreserveAllTokens("ab   de fg", null, 4) = ["ab", "", "", "de fg"]
     * </pre>
     */
    public String[] splitPreserveAllTokens(String str, String separatorChars, int max) {
        return org.clever.graaljs.core.utils.StringUtils.splitPreserveAllTokens(str, separatorChars, max);
    }

    /**
     * <pre>
     *  .splitByCharacterType(null)         = null
     *  .splitByCharacterType("")           = []
     *  .splitByCharacterType("ab de fg")   = ["ab", " ", "de", " ", "fg"]
     *  .splitByCharacterType("ab   de fg") = ["ab", "   ", "de", " ", "fg"]
     *  .splitByCharacterType("ab:cd:ef")   = ["ab", ":", "cd", ":", "ef"]
     *  .splitByCharacterType("number5")    = ["number", "5"]
     *  .splitByCharacterType("fooBar")     = ["foo", "B", "ar"]
     *  .splitByCharacterType("foo200Bar")  = ["foo", "200", "B", "ar"]
     *  .splitByCharacterType("ASFRules")   = ["ASFR", "ules"]
     * </pre>
     */
    public String[] splitByCharacterType(String str) {
        return org.clever.graaljs.core.utils.StringUtils.splitByCharacterType(str);
    }

    /**
     * <pre>
     *  .splitByCharacterTypeCamelCase(null)         = null
     *  .splitByCharacterTypeCamelCase("")           = []
     *  .splitByCharacterTypeCamelCase("ab de fg")   = ["ab", " ", "de", " ", "fg"]
     *  .splitByCharacterTypeCamelCase("ab   de fg") = ["ab", "   ", "de", " ", "fg"]
     *  .splitByCharacterTypeCamelCase("ab:cd:ef")   = ["ab", ":", "cd", ":", "ef"]
     *  .splitByCharacterTypeCamelCase("number5")    = ["number", "5"]
     *  .splitByCharacterTypeCamelCase("fooBar")     = ["foo", "Bar"]
     *  .splitByCharacterTypeCamelCase("foo200Bar")  = ["foo", "200", "Bar"]
     *  .splitByCharacterTypeCamelCase("ASFRules")   = ["ASF", "Rules"]
     * </pre>
     */
    public String[] splitByCharacterTypeCamelCase(String str) {
        return org.clever.graaljs.core.utils.StringUtils.splitByCharacterTypeCamelCase(str);
    }

    // Joining
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * <pre>
     *  .join(null)            = null
     *  .join([])              = ""
     *  .join([null])          = ""
     *  .join(["a", "b", "c"]) = "abc"
     *  .join([null, "", "a"]) = "a"
     * </pre>
     */
    public <T> String join(T... elements) {
        return org.clever.graaljs.core.utils.StringUtils.join(elements);
    }

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join(["a", "b", "c"], ';')  = "a;b;c"
     *  .join(["a", "b", "c"], null) = "abc"
     *  .join([null, "", "a"], ';')  = ";;a"
     * </pre>
     */
    public String join(Object[] array, char separator) {
        return org.clever.graaljs.core.utils.StringUtils.join(array, separator);
    }

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join([1, 2, 3], ';')  = "1;2;3"
     *  .join([1, 2, 3], null) = "123"
     * </pre>
     */
    public String join(long[] array, char separator) {
        return org.clever.graaljs.core.utils.StringUtils.join(array, separator);
    }

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join([1, 2, 3], ';')  = "1;2;3"
     *  .join([1, 2, 3], null) = "123"
     * </pre>
     */
    public String join(int[] array, char separator) {
        return org.clever.graaljs.core.utils.StringUtils.join(array, separator);
    }

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join([1, 2, 3], ';')  = "1;2;3"
     *  .join([1, 2, 3], null) = "123"
     * </pre>
     */
    public String join(short[] array, char separator) {
        return org.clever.graaljs.core.utils.StringUtils.join(array, separator);
    }

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join([1, 2, 3], ';')  = "1;2;3"
     *  .join([1, 2, 3], null) = "123"
     * </pre>
     */
    public String join(byte[] array, char separator) {
        return org.clever.graaljs.core.utils.StringUtils.join(array, separator);
    }

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join([1, 2, 3], ';')  = "1;2;3"
     *  .join([1, 2, 3], null) = "123"
     * </pre>
     */
    public String join(char[] array, char separator) {
        return org.clever.graaljs.core.utils.StringUtils.join(array, separator);
    }

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join([1, 2, 3], ';')  = "1;2;3"
     *  .join([1, 2, 3], null) = "123"
     * </pre>
     */
    public String join(float[] array, char separator) {
        return org.clever.graaljs.core.utils.StringUtils.join(array, separator);
    }

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join([1, 2, 3], ';')  = "1;2;3"
     *  .join([1, 2, 3], null) = "123"
     * </pre>
     */
    public String join(double[] array, char separator) {
        return org.clever.graaljs.core.utils.StringUtils.join(array, separator);
    }

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join(["a", "b", "c"], ';')  = "a;b;c"
     *  .join(["a", "b", "c"], null) = "abc"
     *  .join([null, "", "a"], ';')  = ";;a"
     * </pre>
     */
    public String join(Object[] array, char separator, int startIndex, int endIndex) {
        return org.clever.graaljs.core.utils.StringUtils.join(array, separator, startIndex, endIndex);
    }

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join([1, 2, 3], ';')  = "1;2;3"
     *  .join([1, 2, 3], null) = "123"
     * </pre>
     */
    public String join(long[] array, char separator, int startIndex, int endIndex) {
        return org.clever.graaljs.core.utils.StringUtils.join(array, separator, startIndex, endIndex);
    }

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join([1, 2, 3], ';')  = "1;2;3"
     *  .join([1, 2, 3], null) = "123"
     * </pre>
     */
    public String join(int[] array, char separator, int startIndex, int endIndex) {
        return org.clever.graaljs.core.utils.StringUtils.join(array, separator, startIndex, endIndex);
    }

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join([1, 2, 3], ';')  = "1;2;3"
     *  .join([1, 2, 3], null) = "123"
     * </pre>
     */
    public String join(byte[] array, char separator, int startIndex, int endIndex) {
        return org.clever.graaljs.core.utils.StringUtils.join(array, separator, startIndex, endIndex);
    }

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join([1, 2, 3], ';')  = "1;2;3"
     *  .join([1, 2, 3], null) = "123"
     * </pre>
     */
    public String join(short[] array, char separator, int startIndex, int endIndex) {
        return org.clever.graaljs.core.utils.StringUtils.join(array, separator, startIndex, endIndex);
    }

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join([1, 2, 3], ';')  = "1;2;3"
     *  .join([1, 2, 3], null) = "123"
     * </pre>
     */
    public String join(char[] array, char separator, int startIndex, int endIndex) {
        return org.clever.graaljs.core.utils.StringUtils.join(array, separator, startIndex, endIndex);
    }

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join([1, 2, 3], ';')  = "1;2;3"
     *  .join([1, 2, 3], null) = "123"
     * </pre>
     */
    public String join(double[] array, char separator, int startIndex, int endIndex) {
        return org.clever.graaljs.core.utils.StringUtils.join(array, separator, startIndex, endIndex);
    }

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join([1, 2, 3], ';')  = "1;2;3"
     *  .join([1, 2, 3], null) = "123"
     * </pre>
     */
    public String join(float[] array, char separator, int startIndex, int endIndex) {
        return org.clever.graaljs.core.utils.StringUtils.join(array, separator, startIndex, endIndex);
    }

    /**
     * <pre>
     *  .join(null, *)                = null
     *  .join([], *)                  = ""
     *  .join([null], *)              = ""
     *  .join(["a", "b", "c"], "--")  = "a--b--c"
     *  .join(["a", "b", "c"], null)  = "abc"
     *  .join(["a", "b", "c"], "")    = "abc"
     *  .join([null, "", "a"], ',')   = ",,a"
     * </pre>
     */
    public String join(Object[] array, String separator) {
        return org.clever.graaljs.core.utils.StringUtils.join(array, separator);
    }

    /**
     * <pre>
     *  .join(null, *, *, *)                = null
     *  .join([], *, *, *)                  = ""
     *  .join([null], *, *, *)              = ""
     *  .join(["a", "b", "c"], "--", 0, 3)  = "a--b--c"
     *  .join(["a", "b", "c"], "--", 1, 3)  = "b--c"
     *  .join(["a", "b", "c"], "--", 2, 3)  = "c"
     *  .join(["a", "b", "c"], "--", 2, 2)  = ""
     *  .join(["a", "b", "c"], null, 0, 3)  = "abc"
     *  .join(["a", "b", "c"], "", 0, 3)    = "abc"
     *  .join([null, "", "a"], ',', 0, 3)   = ",,a"
     * </pre>
     */
    public String join(Object[] array, String separator, int startIndex, int endIndex) {
        return org.clever.graaljs.core.utils.StringUtils.join(array, separator, startIndex, endIndex);
    }

    /**
     * 将所提供迭代器的元素联接到包含所提供元素的单个字符串中
     */
    public String join(Iterator<?> iterator, char separator) {
        return org.clever.graaljs.core.utils.StringUtils.join(iterator, separator);
    }

    /**
     * 将所提供迭代器的元素联接到包含所提供元素的单个字符串中
     */
    public String join(Iterator<?> iterator, String separator) {
        return org.clever.graaljs.core.utils.StringUtils.join(iterator, separator);
    }

    /**
     * 将提供的Iterable的元素联接到包含所提供元素的单个字符串中
     */
    public String join(Iterable<?> iterable, char separator) {
        return org.clever.graaljs.core.utils.StringUtils.join(iterable, separator);
    }

    /**
     * 将提供的Iterable的元素联接到包含所提供元素的单个字符串中
     */
    public String join(Iterable<?> iterable, String separator) {
        return org.clever.graaljs.core.utils.StringUtils.join(iterable, separator);
    }

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join(["a", "b", "c"], ';')  = "a;b;c"
     *  .join(["a", "b", "c"], null) = "abc"
     *  .join([null, "", "a"], ';')  = ";;a"
     * </pre>
     */
    public String join(List<?> list, char separator, int startIndex, int endIndex) {
        return org.clever.graaljs.core.utils.StringUtils.join(list, separator, startIndex, endIndex);
    }

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join(["a", "b", "c"], ';')  = "a;b;c"
     *  .join(["a", "b", "c"], null) = "abc"
     *  .join([null, "", "a"], ';')  = ";;a"
     * </pre>
     */
    public String join(List<?> list, String separator, int startIndex, int endIndex) {
        return org.clever.graaljs.core.utils.StringUtils.join(list, separator, startIndex, endIndex);
    }

    /**
     * <pre>
     *  .joinWith(",", {"a", "b"})        = "a,b"
     *  .joinWith(",", {"a", "b",""})     = "a,b,"
     *  .joinWith(",", {"a", null, "b"})  = "a,,b"
     *  .joinWith(null, {"a", "b"})       = "ab"
     * </pre>
     */
    public String joinWith(String separator, Object... objects) {
        return org.clever.graaljs.core.utils.StringUtils.joinWith(separator, objects);
    }

    // Delete
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * <pre>
     *  .deleteWhitespace(null)         = null
     *  .deleteWhitespace("")           = ""
     *  .deleteWhitespace("abc")        = "abc"
     *  .deleteWhitespace("   ab  c  ") = "abc"
     * </pre>
     */
    public String deleteWhitespace(String str) {
        return org.clever.graaljs.core.utils.StringUtils.deleteWhitespace(str);
    }

    // Remove
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * <pre>
     *  .removeStart(null, *)      = null
     *  .removeStart("", *)        = ""
     *  .removeStart(*, null)      = *
     *  .removeStart("www.domain.com", "www.")   = "domain.com"
     *  .removeStart("domain.com", "www.")       = "domain.com"
     *  .removeStart("www.domain.com", "domain") = "www.domain.com"
     *  .removeStart("abc", "")    = "abc"
     * </pre>
     */
    public String removeStart(String str, String remove) {
        return org.clever.graaljs.core.utils.StringUtils.removeStart(str, remove);
    }

    /**
     * <pre>
     *  .removeStartIgnoreCase(null, *)      = null
     *  .removeStartIgnoreCase("", *)        = ""
     *  .removeStartIgnoreCase(*, null)      = *
     *  .removeStartIgnoreCase("www.domain.com", "www.")   = "domain.com"
     *  .removeStartIgnoreCase("www.domain.com", "WWW.")   = "domain.com"
     *  .removeStartIgnoreCase("domain.com", "www.")       = "domain.com"
     *  .removeStartIgnoreCase("www.domain.com", "domain") = "www.domain.com"
     *  .removeStartIgnoreCase("abc", "")    = "abc"
     * </pre>
     */
    public String removeStartIgnoreCase(String str, String remove) {
        return org.clever.graaljs.core.utils.StringUtils.removeStartIgnoreCase(str, remove);
    }

    /**
     * <pre>
     *  .removeEnd(null, *)      = null
     *  .removeEnd("", *)        = ""
     *  .removeEnd(*, null)      = *
     *  .removeEnd("www.domain.com", ".com.")  = "www.domain.com"
     *  .removeEnd("www.domain.com", ".com")   = "www.domain"
     *  .removeEnd("www.domain.com", "domain") = "www.domain.com"
     *  .removeEnd("abc", "")    = "abc"
     * </pre>
     */
    public String removeEnd(String str, String remove) {
        return org.clever.graaljs.core.utils.StringUtils.removeEnd(str, remove);
    }

    /**
     * <pre>
     *  .removeEndIgnoreCase(null, *)      = null
     *  .removeEndIgnoreCase("", *)        = ""
     *  .removeEndIgnoreCase(*, null)      = *
     *  .removeEndIgnoreCase("www.domain.com", ".com.")  = "www.domain.com"
     *  .removeEndIgnoreCase("www.domain.com", ".com")   = "www.domain"
     *  .removeEndIgnoreCase("www.domain.com", "domain") = "www.domain.com"
     *  .removeEndIgnoreCase("abc", "")    = "abc"
     *  .removeEndIgnoreCase("www.domain.com", ".COM") = "www.domain")
     *  .removeEndIgnoreCase("www.domain.COM", ".com") = "www.domain")
     * </pre>
     */
    public String removeEndIgnoreCase(String str, String remove) {
        return org.clever.graaljs.core.utils.StringUtils.removeEndIgnoreCase(str, remove);
    }

    /**
     * <pre>
     *  .remove(null, *)        = null
     *  .remove("", *)          = ""
     *  .remove(*, null)        = *
     *  .remove(*, "")          = *
     *  .remove("queued", "ue") = "qd"
     *  .remove("queued", "zz") = "queued"
     * </pre>
     */
    public String remove(String str, String remove) {
        return org.clever.graaljs.core.utils.StringUtils.remove(str, remove);
    }

    /**
     * <pre>
     *  .removeIgnoreCase(null, *)        = null
     *  .removeIgnoreCase("", *)          = ""
     *  .removeIgnoreCase(*, null)        = *
     *  .removeIgnoreCase(*, "")          = *
     *  .removeIgnoreCase("queued", "ue") = "qd"
     *  .removeIgnoreCase("queued", "zz") = "queued"
     *  .removeIgnoreCase("quEUed", "UE") = "qd"
     *  .removeIgnoreCase("queued", "zZ") = "queued"
     * </pre>
     */
    public String removeIgnoreCase(String str, String remove) {
        return org.clever.graaljs.core.utils.StringUtils.removeIgnoreCase(str, remove);
    }

    /**
     * <pre>
     *  .remove(null, *)       = null
     *  .remove("", *)         = ""
     *  .remove("queued", 'u') = "qeed"
     *  .remove("queued", 'z') = "queued"
     * </pre>
     */
    public String remove(String str, char remove) {
        return org.clever.graaljs.core.utils.StringUtils.remove(str, remove);
    }

    // Replacing
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * <pre>
     *  .replaceOnce(null, *, *)        = null
     *  .replaceOnce("", *, *)          = ""
     *  .replaceOnce("any", null, *)    = "any"
     *  .replaceOnce("any", *, null)    = "any"
     *  .replaceOnce("any", "", *)      = "any"
     *  .replaceOnce("aba", "a", null)  = "aba"
     *  .replaceOnce("aba", "a", "")    = "ba"
     *  .replaceOnce("aba", "a", "z")   = "zba"
     * </pre>
     */
    public String replaceOnce(String text, String searchString, String replacement) {
        return org.clever.graaljs.core.utils.StringUtils.replaceOnce(text, searchString, replacement);
    }

    /**
     * <pre>
     *  .replaceOnceIgnoreCase(null, *, *)        = null
     *  .replaceOnceIgnoreCase("", *, *)          = ""
     *  .replaceOnceIgnoreCase("any", null, *)    = "any"
     *  .replaceOnceIgnoreCase("any", *, null)    = "any"
     *  .replaceOnceIgnoreCase("any", "", *)      = "any"
     *  .replaceOnceIgnoreCase("aba", "a", null)  = "aba"
     *  .replaceOnceIgnoreCase("aba", "a", "")    = "ba"
     *  .replaceOnceIgnoreCase("aba", "a", "z")   = "zba"
     *  .replaceOnceIgnoreCase("FoOFoofoo", "foo", "") = "Foofoo"
     * </pre>
     */
    public String replaceOnceIgnoreCase(String text, String searchString, String replacement) {
        return org.clever.graaljs.core.utils.StringUtils.replaceOnceIgnoreCase(text, searchString, replacement);
    }

    /**
     * <pre>
     *  .replace(null, *, *)        = null
     *  .replace("", *, *)          = ""
     *  .replace("any", null, *)    = "any"
     *  .replace("any", *, null)    = "any"
     *  .replace("any", "", *)      = "any"
     *  .replace("aba", "a", null)  = "aba"
     *  .replace("aba", "a", "")    = "b"
     *  .replace("aba", "a", "z")   = "zbz"
     * </pre>
     */
    public String replace(String text, String searchString, String replacement) {
        return org.clever.graaljs.core.utils.StringUtils.replace(text, searchString, replacement);
    }

    /**
     * <pre>
     *  .replaceIgnoreCase(null, *, *)        = null
     *  .replaceIgnoreCase("", *, *)          = ""
     *  .replaceIgnoreCase("any", null, *)    = "any"
     *  .replaceIgnoreCase("any", *, null)    = "any"
     *  .replaceIgnoreCase("any", "", *)      = "any"
     *  .replaceIgnoreCase("aba", "a", null)  = "aba"
     *  .replaceIgnoreCase("abA", "A", "")    = "b"
     *  .replaceIgnoreCase("aba", "A", "z")   = "zbz"
     * </pre>
     */
    public String replaceIgnoreCase(String text, String searchString, String replacement) {
        return org.clever.graaljs.core.utils.StringUtils.replaceIgnoreCase(text, searchString, replacement);
    }

    /**
     * <pre>
     *  .replace(null, *, *, *)         = null
     *  .replace("", *, *, *)           = ""
     *  .replace("any", null, *, *)     = "any"
     *  .replace("any", *, null, *)     = "any"
     *  .replace("any", "", *, *)       = "any"
     *  .replace("any", *, *, 0)        = "any"
     *  .replace("abaa", "a", null, -1) = "abaa"
     *  .replace("abaa", "a", "", -1)   = "b"
     *  .replace("abaa", "a", "z", 0)   = "abaa"
     *  .replace("abaa", "a", "z", 1)   = "zbaa"
     *  .replace("abaa", "a", "z", 2)   = "zbza"
     *  .replace("abaa", "a", "z", -1)  = "zbzz"
     * </pre>
     */
    public String replace(String text, String searchString, String replacement, int max) {
        return org.clever.graaljs.core.utils.StringUtils.replace(text, searchString, replacement, max);
    }

    /**
     * <pre>
     *  .replaceIgnoreCase(null, *, *, *)         = null
     *  .replaceIgnoreCase("", *, *, *)           = ""
     *  .replaceIgnoreCase("any", null, *, *)     = "any"
     *  .replaceIgnoreCase("any", *, null, *)     = "any"
     *  .replaceIgnoreCase("any", "", *, *)       = "any"
     *  .replaceIgnoreCase("any", *, *, 0)        = "any"
     *  .replaceIgnoreCase("abaa", "a", null, -1) = "abaa"
     *  .replaceIgnoreCase("abaa", "a", "", -1)   = "b"
     *  .replaceIgnoreCase("abaa", "a", "z", 0)   = "abaa"
     *  .replaceIgnoreCase("abaa", "A", "z", 1)   = "zbaa"
     *  .replaceIgnoreCase("abAa", "a", "z", 2)   = "zbza"
     *  .replaceIgnoreCase("abAa", "a", "z", -1)  = "zbzz"
     * </pre>
     */
    public String replaceIgnoreCase(String text, String searchString, String replacement, int max) {
        return org.clever.graaljs.core.utils.StringUtils.replaceIgnoreCase(text, searchString, replacement, max);
    }

    /**
     * <pre>
     *   .replaceEach(null, *, *)        = null
     *   .replaceEach("", *, *)          = ""
     *   .replaceEach("aba", null, null) = "aba"
     *   .replaceEach("aba", new String[0], null) = "aba"
     *   .replaceEach("aba", null, new String[0]) = "aba"
     *   .replaceEach("aba", new String[]{"a"}, null)  = "aba"
     *   .replaceEach("aba", new String[]{"a"}, new String[]{""})  = "b"
     *   .replaceEach("aba", new String[]{null}, new String[]{"a"})  = "aba"
     *   .replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"w", "t"})  = "wcte"
     *   (example of how it does not repeat)
     *   .replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"})  = "dcte"
     * </pre>
     */
    public String replaceEach(String text, String[] searchList, String[] replacementList) {
        return org.clever.graaljs.core.utils.StringUtils.replaceEach(text, searchList, replacementList);
    }

    /**
     * <pre>
     *  .replaceEachRepeatedly(null, *, *) = null
     *  .replaceEachRepeatedly("", *, *) = ""
     *  .replaceEachRepeatedly("aba", null, null) = "aba"
     *  .replaceEachRepeatedly("aba", new String[0], null) = "aba"
     *  .replaceEachRepeatedly("aba", null, new String[0]) = "aba"
     *  .replaceEachRepeatedly("aba", new String[]{"a"}, null) = "aba"
     *  .replaceEachRepeatedly("aba", new String[]{"a"}, new String[]{""}) = "b"
     *  .replaceEachRepeatedly("aba", new String[]{null}, new String[]{"a"}) = "aba"
     *  .replaceEachRepeatedly("abcde", new String[]{"ab", "d"}, new String[]{"w", "t"}) = "wcte"
     *  (example of how it repeats)
     *  .replaceEachRepeatedly("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"}) = "tcte"
     *  .replaceEachRepeatedly("abcde", new String[]{"ab", "d"}, new String[]{"d", "ab"}) = IllegalStateException
     * </pre>
     */
    public String replaceEachRepeatedly(String text, String[] searchList, String[] replacementList) {
        return org.clever.graaljs.core.utils.StringUtils.replaceEachRepeatedly(text, searchList, replacementList);
    }

    // Replace, character based
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * <pre>
     *  .replaceChars(null, *, *)        = null
     *  .replaceChars("", *, *)          = ""
     *  .replaceChars("abcba", 'b', 'y') = "aycya"
     *  .replaceChars("abcba", 'z', 'y') = "abcba"
     * </pre>
     */
    public String replaceChars(String str, char searchChar, char replaceChar) {
        return org.clever.graaljs.core.utils.StringUtils.replaceChars(str, searchChar, replaceChar);
    }

    /**
     * <pre>
     *  .replaceChars(null, *, *)           = null
     *  .replaceChars("", *, *)             = ""
     *  .replaceChars("abc", null, *)       = "abc"
     *  .replaceChars("abc", "", *)         = "abc"
     *  .replaceChars("abc", "b", null)     = "ac"
     *  .replaceChars("abc", "b", "")       = "ac"
     *  .replaceChars("abcba", "bc", "yz")  = "ayzya"
     *  .replaceChars("abcba", "bc", "y")   = "ayya"
     *  .replaceChars("abcba", "bc", "yzx") = "ayzya"
     * </pre>
     */
    public String replaceChars(String str, String searchChars, String replaceChars) {
        return org.clever.graaljs.core.utils.StringUtils.replaceChars(str, searchChars, replaceChars);
    }

    // Overlay
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * <pre>
     *  .overlay(null, *, *, *)            = null
     *  .overlay("", "abc", 0, 0)          = "abc"
     *  .overlay("abcdef", null, 2, 4)     = "abef"
     *  .overlay("abcdef", "", 2, 4)       = "abef"
     *  .overlay("abcdef", "", 4, 2)       = "abef"
     *  .overlay("abcdef", "zzzz", 2, 4)   = "abzzzzef"
     *  .overlay("abcdef", "zzzz", 4, 2)   = "abzzzzef"
     *  .overlay("abcdef", "zzzz", -1, 4)  = "zzzzef"
     *  .overlay("abcdef", "zzzz", 2, 8)   = "abzzzz"
     *  .overlay("abcdef", "zzzz", -2, -3) = "zzzzabcdef"
     *  .overlay("abcdef", "zzzz", 8, 10)  = "abcdefzzzz"
     * </pre>
     */
    public String overlay(String str, String overlay, int start, int end) {
        return org.clever.graaljs.core.utils.StringUtils.overlay(str, overlay, start, end);
    }

    // Chomping
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * <pre>
     *  .chomp(null)          = null
     *  .chomp("")            = ""
     *  .chomp("abc \r")      = "abc "
     *  .chomp("abc\n")       = "abc"
     *  .chomp("abc\r\n")     = "abc"
     *  .chomp("abc\r\n\r\n") = "abc\r\n"
     *  .chomp("abc\n\r")     = "abc\n"
     *  .chomp("abc\n\rabc")  = "abc\n\rabc"
     *  .chomp("\r")          = ""
     *  .chomp("\n")          = ""
     *  .chomp("\r\n")        = ""
     * </pre>
     */
    public String chomp(String str) {
        return org.clever.graaljs.core.utils.StringUtils.chomp(str);
    }

    // Chopping
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * <pre>
     *  .chop(null)          = null
     *  .chop("")            = ""
     *  .chop("abc \r")      = "abc "
     *  .chop("abc\n")       = "abc"
     *  .chop("abc\r\n")     = "abc"
     *  .chop("abc")         = "ab"
     *  .chop("abc\nabc")    = "abc\nab"
     *  .chop("a")           = ""
     *  .chop("\r")          = ""
     *  .chop("\n")          = ""
     *  .chop("\r\n")        = ""
     * </pre>
     */
    public String chop(String str) {
        return org.clever.graaljs.core.utils.StringUtils.chop(str);
    }

    // Conversion
    //----------------------------------------------------------------------------------------------------------------------------------------------

    // Padding
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * <pre>
     *  .repeat(null, 2) = null
     *  .repeat("", 0)   = ""
     *  .repeat("", 2)   = ""
     *  .repeat("a", 3)  = "aaa"
     *  .repeat("ab", 2) = "abab"
     *  .repeat("a", -2) = ""
     * </pre>
     */
    public String repeat(String str, int repeat) {
        return org.clever.graaljs.core.utils.StringUtils.repeat(str, repeat);
    }

    /**
     * <pre>
     *  .repeat(null, null, 2) = null
     *  .repeat(null, "x", 2)  = null
     *  .repeat("", null, 0)   = ""
     *  .repeat("", "", 2)     = ""
     *  .repeat("", "x", 3)    = "xxx"
     *  .repeat("?", ", ", 3)  = "?, ?, ?"
     * </pre>
     */
    public String repeat(String str, String separator, int repeat) {
        return org.clever.graaljs.core.utils.StringUtils.repeat(str, separator, repeat);
    }

    /**
     * <pre>
     *  .repeat('e', 0)  = ""
     *  .repeat('e', 3)  = "eee"
     *  .repeat('e', -2) = ""
     * </pre>
     */
    public String repeat(char ch, int repeat) {
        return org.clever.graaljs.core.utils.StringUtils.repeat(ch, repeat);
    }

    /**
     * <pre>
     *  .rightPad(null, *)   = null
     *  .rightPad("", 3)     = "   "
     *  .rightPad("bat", 3)  = "bat"
     *  .rightPad("bat", 5)  = "bat  "
     *  .rightPad("bat", 1)  = "bat"
     *  .rightPad("bat", -1) = "bat"
     * </pre>
     */
    public String rightPad(String str, int size) {
        return org.clever.graaljs.core.utils.StringUtils.rightPad(str, size);
    }

    /**
     * <pre>
     *  .rightPad(null, *, *)     = null
     *  .rightPad("", 3, 'z')     = "zzz"
     *  .rightPad("bat", 3, 'z')  = "bat"
     *  .rightPad("bat", 5, 'z')  = "batzz"
     *  .rightPad("bat", 1, 'z')  = "bat"
     *  .rightPad("bat", -1, 'z') = "bat"
     * </pre>
     */
    public String rightPad(String str, int size, char padChar) {
        return org.clever.graaljs.core.utils.StringUtils.rightPad(str, size, padChar);
    }

    /**
     * <pre>
     *  .rightPad(null, *, *)      = null
     *  .rightPad("", 3, "z")      = "zzz"
     *  .rightPad("bat", 3, "yz")  = "bat"
     *  .rightPad("bat", 5, "yz")  = "batyz"
     *  .rightPad("bat", 8, "yz")  = "batyzyzy"
     *  .rightPad("bat", 1, "yz")  = "bat"
     *  .rightPad("bat", -1, "yz") = "bat"
     *  .rightPad("bat", 5, null)  = "bat  "
     *  .rightPad("bat", 5, "")    = "bat  "
     * </pre>
     */
    public String rightPad(String str, int size, String padStr) {
        return org.clever.graaljs.core.utils.StringUtils.rightPad(str, size, padStr);
    }

    /**
     * <pre>
     *  .leftPad(null, *)   = null
     *  .leftPad("", 3)     = "   "
     *  .leftPad("bat", 3)  = "bat"
     *  .leftPad("bat", 5)  = "  bat"
     *  .leftPad("bat", 1)  = "bat"
     *  .leftPad("bat", -1) = "bat"
     * </pre>
     */
    public String leftPad(String str, int size) {
        return org.clever.graaljs.core.utils.StringUtils.leftPad(str, size);
    }

    /**
     * <pre>
     *  .leftPad(null, *, *)     = null
     *  .leftPad("", 3, 'z')     = "zzz"
     *  .leftPad("bat", 3, 'z')  = "bat"
     *  .leftPad("bat", 5, 'z')  = "zzbat"
     *  .leftPad("bat", 1, 'z')  = "bat"
     *  .leftPad("bat", -1, 'z') = "bat"
     * </pre>
     */
    public String leftPad(String str, int size, char padChar) {
        return org.clever.graaljs.core.utils.StringUtils.leftPad(str, size, padChar);
    }

    /**
     * <pre>
     *  .leftPad(null, *, *)      = null
     *  .leftPad("", 3, "z")      = "zzz"
     *  .leftPad("bat", 3, "yz")  = "bat"
     *  .leftPad("bat", 5, "yz")  = "yzbat"
     *  .leftPad("bat", 8, "yz")  = "yzyzybat"
     *  .leftPad("bat", 1, "yz")  = "bat"
     *  .leftPad("bat", -1, "yz") = "bat"
     *  .leftPad("bat", 5, null)  = "  bat"
     *  .leftPad("bat", 5, "")    = "  bat"
     * </pre>
     */
    public String leftPad(String str, int size, String padStr) {
        return org.clever.graaljs.core.utils.StringUtils.leftPad(str, size, padStr);
    }

    /**
     * 获取CharSequence长度，如果CharSequence为null，则获取0
     */
    public int length(CharSequence cs) {
        return org.clever.graaljs.core.utils.StringUtils.length(cs);
    }

    // Centering
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * <pre>
     *  .center(null, *)   = null
     *  .center("", 4)     = "    "
     *  .center("ab", -1)  = "ab"
     *  .center("ab", 4)   = " ab "
     *  .center("abcd", 2) = "abcd"
     *  .center("a", 4)    = " a  "
     * </pre>
     */
    public String center(String str, int size) {
        return org.clever.graaljs.core.utils.StringUtils.center(str, size);
    }

    /**
     * <pre>
     *  .center(null, *, *)     = null
     *  .center("", 4, ' ')     = "    "
     *  .center("ab", -1, ' ')  = "ab"
     *  .center("ab", 4, ' ')   = " ab "
     *  .center("abcd", 2, ' ') = "abcd"
     *  .center("a", 4, ' ')    = " a  "
     *  .center("a", 4, 'y')    = "yayy"
     * </pre>
     */
    public String center(String str, int size, char padChar) {
        return org.clever.graaljs.core.utils.StringUtils.center(str, size, padChar);
    }

    /**
     * <pre>
     *  .center(null, *, *)     = null
     *  .center("", 4, " ")     = "    "
     *  .center("ab", -1, " ")  = "ab"
     *  .center("ab", 4, " ")   = " ab "
     *  .center("abcd", 2, " ") = "abcd"
     *  .center("a", 4, " ")    = " a  "
     *  .center("a", 4, "yz")   = "yayz"
     *  .center("abc", 7, null) = "  abc  "
     *  .center("abc", 7, "")   = "  abc  "
     * </pre>
     */
    public String center(String str, int size, String padStr) {
        return org.clever.graaljs.core.utils.StringUtils.center(str, size, padStr);
    }

    // Case conversion
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * <pre>
     *  .upperCase(null)  = null
     *  .upperCase("")    = ""
     *  .upperCase("aBc") = "ABC"
     * </pre>
     */
    public String upperCase(String str) {
        return org.clever.graaljs.core.utils.StringUtils.upperCase(str);
    }

    /**
     * <pre>
     *  .upperCase(null, Locale.ENGLISH)  = null
     *  .upperCase("", Locale.ENGLISH)    = ""
     *  .upperCase("aBc", Locale.ENGLISH) = "ABC"
     * </pre>
     */
    public String upperCase(String str, Locale locale) {
        return org.clever.graaljs.core.utils.StringUtils.upperCase(str, locale);
    }

    /**
     * <pre>
     *  .lowerCase(null)  = null
     *  .lowerCase("")    = ""
     *  .lowerCase("aBc") = "abc"
     * </pre>
     */
    public String lowerCase(String str) {
        return org.clever.graaljs.core.utils.StringUtils.lowerCase(str);
    }

    /**
     * <pre>
     *  .lowerCase(null, Locale.ENGLISH)  = null
     *  .lowerCase("", Locale.ENGLISH)    = ""
     *  .lowerCase("aBc", Locale.ENGLISH) = "abc"
     * </pre>
     */
    public String lowerCase(String str, Locale locale) {
        return org.clever.graaljs.core.utils.StringUtils.lowerCase(str, locale);
    }

    /**
     * <pre>
     *  .capitalize(null)  = null
     *  .capitalize("")    = ""
     *  .capitalize("cat") = "Cat"
     *  .capitalize("cAt") = "CAt"
     *  .capitalize("'cat'") = "'cat'"
     * </pre>
     */
    public String capitalize(String str) {
        return org.clever.graaljs.core.utils.StringUtils.capitalize(str);
    }

    /**
     * <pre>
     *  .uncapitalize(null)  = null
     *  .uncapitalize("")    = ""
     *  .uncapitalize("cat") = "cat"
     *  .uncapitalize("Cat") = "cat"
     *  .uncapitalize("CAT") = "cAT"
     * </pre>
     */
    public String uncapitalize(String str) {
        return org.clever.graaljs.core.utils.StringUtils.uncapitalize(str);
    }

    /**
     * <pre>
     *  .swapCase(null)                 = null
     *  .swapCase("")                   = ""
     *  .swapCase("The dog has a BONE") = "tHE DOG HAS A bone"
     * </pre>
     */
    public String swapCase(String str) {
        return org.clever.graaljs.core.utils.StringUtils.swapCase(str);
    }

    // Count matches
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * <pre>
     *  .countMatches(null, *)       = 0
     *  .countMatches("", *)         = 0
     *  .countMatches("abba", null)  = 0
     *  .countMatches("abba", "")    = 0
     *  .countMatches("abba", "a")   = 2
     *  .countMatches("abba", "ab")  = 1
     *  .countMatches("abba", "xxx") = 0
     * </pre>
     */
    public int countMatches(CharSequence str, CharSequence sub) {
        return org.clever.graaljs.core.utils.StringUtils.countMatches(str, sub);
    }

    /**
     * <pre>
     *  .countMatches(null, *)       = 0
     *  .countMatches("", *)         = 0
     *  .countMatches("abba", 0)     = 0
     *  .countMatches("abba", 'a')   = 2
     *  .countMatches("abba", 'b')   = 2
     *  .countMatches("abba", 'x')   = 0
     * </pre>
     */
    public int countMatches(CharSequence str, char ch) {
        return org.clever.graaljs.core.utils.StringUtils.countMatches(str, ch);
    }

    // Character Tests
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * <pre>
     *  .isAlpha(null)   = false
     *  .isAlpha("")     = false
     *  .isAlpha("  ")   = false
     *  .isAlpha("abc")  = true
     *  .isAlpha("ab2c") = false
     *  .isAlpha("ab-c") = false
     * </pre>
     */
    public boolean isAlpha(CharSequence cs) {
        return org.clever.graaljs.core.utils.StringUtils.isAlpha(cs);
    }

    /**
     * <pre>
     *  .isAlphaSpace(null)   = false
     *  .isAlphaSpace("")     = true
     *  .isAlphaSpace("  ")   = true
     *  .isAlphaSpace("abc")  = true
     *  .isAlphaSpace("ab c") = true
     *  .isAlphaSpace("ab2c") = false
     *  .isAlphaSpace("ab-c") = false
     * </pre>
     */
    public boolean isAlphaSpace(CharSequence cs) {
        return org.clever.graaljs.core.utils.StringUtils.isAlphaSpace(cs);
    }

    /**
     * <pre>
     *  .isAlphanumeric(null)   = false
     *  .isAlphanumeric("")     = false
     *  .isAlphanumeric("  ")   = false
     *  .isAlphanumeric("abc")  = true
     *  .isAlphanumeric("ab c") = false
     *  .isAlphanumeric("ab2c") = true
     *  .isAlphanumeric("ab-c") = false
     * </pre>
     */
    public boolean isAlphanumeric(CharSequence cs) {
        return org.clever.graaljs.core.utils.StringUtils.isAlphanumeric(cs);
    }

    /**
     * <pre>
     *  .isAlphanumericSpace(null)   = false
     *  .isAlphanumericSpace("")     = true
     *  .isAlphanumericSpace("  ")   = true
     *  .isAlphanumericSpace("abc")  = true
     *  .isAlphanumericSpace("ab c") = true
     *  .isAlphanumericSpace("ab2c") = true
     *  .isAlphanumericSpace("ab-c") = false
     * </pre>
     */
    public boolean isAlphanumericSpace(CharSequence cs) {
        return org.clever.graaljs.core.utils.StringUtils.isAlphanumericSpace(cs);
    }

    /**
     * <pre>
     *  .isAsciiPrintable(null)     = false
     *  .isAsciiPrintable("")       = true
     *  .isAsciiPrintable(" ")      = true
     *  .isAsciiPrintable("Ceki")   = true
     *  .isAsciiPrintable("ab2c")   = true
     *  .isAsciiPrintable("!ab-c~") = true
     *  .isAsciiPrintable(" ") = true
     *  .isAsciiPrintable("!") = true
     *  .isAsciiPrintable("~") = true
     *  .isAsciiPrintable("") = false
     *  .isAsciiPrintable("Ceki Gülcü") = false
     * </pre>
     */
    public boolean isAsciiPrintable(CharSequence cs) {
        return org.clever.graaljs.core.utils.StringUtils.isAsciiPrintable(cs);
    }

    /**
     * <pre>
     *  .isNumeric(null)   = false
     *  .isNumeric("")     = false
     *  .isNumeric("  ")   = false
     *  .isNumeric("123")  = true
     *  .isNumeric("१२३")  = true
     *  .isNumeric("12 3") = false
     *  .isNumeric("ab2c") = false
     *  .isNumeric("12-3") = false
     *  .isNumeric("12.3") = false
     *  .isNumeric("-123") = false
     *  .isNumeric("+123") = false
     * </pre>
     */
    public boolean isNumeric(CharSequence cs) {
        return org.clever.graaljs.core.utils.StringUtils.isNumeric(cs);
    }

    /**
     * <pre>
     *  .isNumericSpace(null)   = false
     *  .isNumericSpace("")     = true
     *  .isNumericSpace("  ")   = true
     *  .isNumericSpace("123")  = true
     *  .isNumericSpace("12 3") = true
     *  .isNumeric("१२३")  = true
     *  .isNumeric("१२ ३")  = true
     *  .isNumericSpace("ab2c") = false
     *  .isNumericSpace("12-3") = false
     *  .isNumericSpace("12.3") = false
     * </pre>
     */
    public boolean isNumericSpace(CharSequence cs) {
        return org.clever.graaljs.core.utils.StringUtils.isNumericSpace(cs);
    }

    /**
     * <pre>
     *  .getDigits(null)  = null
     *  .getDigits("")    = ""
     *  .getDigits("abc") = ""
     *  .getDigits("1000$") = "1000"
     *  .getDigits("1123~45") = "112345"
     *  .getDigits("(541) 754-3010") = "5417543010"
     *  .getDigits("\u0967\u0968\u0969") = "\u0967\u0968\u0969"
     * </pre>
     */
    public String getDigits(String str) {
        return org.clever.graaljs.core.utils.StringUtils.getDigits(str);
    }

    /**
     * <pre>
     *  .isWhitespace(null)   = false
     *  .isWhitespace("")     = true
     *  .isWhitespace("  ")   = true
     *  .isWhitespace("abc")  = false
     *  .isWhitespace("ab2c") = false
     *  .isWhitespace("ab-c") = false
     * </pre>
     */
    public boolean isWhitespace(CharSequence cs) {
        return org.clever.graaljs.core.utils.StringUtils.isWhitespace(cs);
    }

    /**
     * <pre>
     *  .isAllLowerCase(null)   = false
     *  .isAllLowerCase("")     = false
     *  .isAllLowerCase("  ")   = false
     *  .isAllLowerCase("abc")  = true
     *  .isAllLowerCase("abC")  = false
     *  .isAllLowerCase("ab c") = false
     *  .isAllLowerCase("ab1c") = false
     *  .isAllLowerCase("ab/c") = false
     * </pre>
     */
    public boolean isAllLowerCase(CharSequence cs) {
        return org.clever.graaljs.core.utils.StringUtils.isAllLowerCase(cs);
    }

    /**
     * <pre>
     *  .isAllUpperCase(null)   = false
     *  .isAllUpperCase("")     = false
     *  .isAllUpperCase("  ")   = false
     *  .isAllUpperCase("ABC")  = true
     *  .isAllUpperCase("aBC")  = false
     *  .isAllUpperCase("A C")  = false
     *  .isAllUpperCase("A1C")  = false
     *  .isAllUpperCase("A/C")  = false
     * </pre>
     */
    public boolean isAllUpperCase(CharSequence cs) {
        return org.clever.graaljs.core.utils.StringUtils.isAllUpperCase(cs);
    }

    /**
     * <pre>
     *  .isMixedCase(null)    = false
     *  .isMixedCase("")      = false
     *  .isMixedCase("ABC")   = false
     *  .isMixedCase("abc")   = false
     *  .isMixedCase("aBc")   = true
     *  .isMixedCase("A c")   = true
     *  .isMixedCase("A1c")   = true
     *  .isMixedCase("a/C")   = true
     *  .isMixedCase("aC\t")  = true
     * </pre>
     */
    public boolean isMixedCase(CharSequence cs) {
        return org.clever.graaljs.core.utils.StringUtils.isMixedCase(cs);
    }

    // Defaults
    //-----------------------------------------------------------------------

    /**
     * <pre>
     *  .defaultString(null)  = ""
     *  .defaultString("")    = ""
     *  .defaultString("bat") = "bat"
     * </pre>
     */
    public String defaultString(String str) {
        return org.clever.graaljs.core.utils.StringUtils.defaultString(str);
    }

    /**
     * <pre>
     *  .defaultString(null, "NULL")  = "NULL"
     *  .defaultString("", "NULL")    = ""
     *  .defaultString("bat", "NULL") = "bat"
     * </pre>
     */
    public String defaultString(String str, String defaultStr) {
        return org.clever.graaljs.core.utils.StringUtils.defaultString(str, defaultStr);
    }

    /**
     * <pre>
     *  .firstNonBlank(null, null, null)     = null
     *  .firstNonBlank(null, "", " ")        = null
     *  .firstNonBlank("abc")                = "abc"
     *  .firstNonBlank(null, "xyz")          = "xyz"
     *  .firstNonBlank(null, "", " ", "xyz") = "xyz"
     *  .firstNonBlank(null, "xyz", "abc")   = "xyz"
     *  .firstNonBlank()                     = null
     * </pre>
     */
    public <T extends CharSequence> T firstNonBlank(T... values) {
        return org.clever.graaljs.core.utils.StringUtils.firstNonBlank(values);
    }

    /**
     * <pre>
     *  .firstNonEmpty(null, null, null)   = null
     *  .firstNonEmpty(null, null, "")     = null
     *  .firstNonEmpty(null, "", " ")      = " "
     *  .firstNonEmpty("abc")              = "abc"
     *  .firstNonEmpty(null, "xyz")        = "xyz"
     *  .firstNonEmpty("", "xyz")          = "xyz"
     *  .firstNonEmpty(null, "xyz", "abc") = "xyz"
     *  .firstNonEmpty()                   = null
     * </pre>
     */
    public <T extends CharSequence> T firstNonEmpty(T... values) {
        return org.clever.graaljs.core.utils.StringUtils.firstNonEmpty(values);
    }

    /**
     * <pre>
     *  .defaultIfBlank(null, "NULL")  = "NULL"
     *  .defaultIfBlank("", "NULL")    = "NULL"
     *  .defaultIfBlank(" ", "NULL")   = "NULL"
     *  .defaultIfBlank("bat", "NULL") = "bat"
     *  .defaultIfBlank("", null)      = null
     * </pre>
     */
    public <T extends CharSequence> T defaultIfBlank(T str, T defaultStr) {
        return org.clever.graaljs.core.utils.StringUtils.defaultIfBlank(str, defaultStr);
    }

    /**
     * <pre>
     *  .defaultIfEmpty(null, "NULL")  = "NULL"
     *  .defaultIfEmpty("", "NULL")    = "NULL"
     *  .defaultIfEmpty(" ", "NULL")   = " "
     *  .defaultIfEmpty("bat", "NULL") = "bat"
     *  .defaultIfEmpty("", null)      = null
     * </pre>
     */
    public <T extends CharSequence> T defaultIfEmpty(T str, T defaultStr) {
        return org.clever.graaljs.core.utils.StringUtils.defaultIfEmpty(str, defaultStr);
    }

    // Rotating (circular shift)
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * <pre>
     *  .rotate(null, *)        = null
     *  .rotate("", *)          = ""
     *  .rotate("abcdefg", 0)   = "abcdefg"
     *  .rotate("abcdefg", 2)   = "fgabcde"
     *  .rotate("abcdefg", -2)  = "cdefgab"
     *  .rotate("abcdefg", 7)   = "abcdefg"
     *  .rotate("abcdefg", -7)  = "abcdefg"
     *  .rotate("abcdefg", 9)   = "fgabcde"
     *  .rotate("abcdefg", -9)  = "cdefgab"
     * </pre>
     */
    public String rotate(String str, int shift) {
        return org.clever.graaljs.core.utils.StringUtils.rotate(str, shift);
    }

    // Reversing
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * <pre>
     *  .reverse(null)  = null
     *  .reverse("")    = ""
     *  .reverse("bat") = "tab"
     * </pre>
     */
    public String reverse(String str) {
        return org.clever.graaljs.core.utils.StringUtils.reverse(str);
    }

    /**
     * <pre>
     *  .reverseDelimited(null, *)      = null
     *  .reverseDelimited("", *)        = ""
     *  .reverseDelimited("a.b.c", 'x') = "a.b.c"
     *  .reverseDelimited("a.b.c", ".") = "c.b.a"
     * </pre>
     */
    public String reverseDelimited(String str, char separatorChar) {
        return org.clever.graaljs.core.utils.StringUtils.reverseDelimited(str, separatorChar);
    }

    // Abbreviating
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * <pre>
     *  .abbreviate(null, *)      = null
     *  .abbreviate("", 4)        = ""
     *  .abbreviate("abcdefg", 6) = "abc..."
     *  .abbreviate("abcdefg", 7) = "abcdefg"
     *  .abbreviate("abcdefg", 8) = "abcdefg"
     *  .abbreviate("abcdefg", 4) = "a..."
     *  .abbreviate("abcdefg", 3) = IllegalArgumentException
     * </pre>
     */
    public String abbreviate(String str, int maxWidth) {
        return org.clever.graaljs.core.utils.StringUtils.abbreviate(str, maxWidth);
    }

    /**
     * <pre>
     *  .abbreviate(null, *, *)                = null
     *  .abbreviate("", 0, 4)                  = ""
     *  .abbreviate("abcdefghijklmno", -1, 10) = "abcdefg..."
     *  .abbreviate("abcdefghijklmno", 0, 10)  = "abcdefg..."
     *  .abbreviate("abcdefghijklmno", 1, 10)  = "abcdefg..."
     *  .abbreviate("abcdefghijklmno", 4, 10)  = "abcdefg..."
     *  .abbreviate("abcdefghijklmno", 5, 10)  = "...fghi..."
     *  .abbreviate("abcdefghijklmno", 6, 10)  = "...ghij..."
     *  .abbreviate("abcdefghijklmno", 8, 10)  = "...ijklmno"
     *  .abbreviate("abcdefghijklmno", 10, 10) = "...ijklmno"
     *  .abbreviate("abcdefghijklmno", 12, 10) = "...ijklmno"
     *  .abbreviate("abcdefghij", 0, 3)        = IllegalArgumentException
     *  .abbreviate("abcdefghij", 5, 6)        = IllegalArgumentException
     * </pre>
     */
    public String abbreviate(String str, int offset, int maxWidth) {
        return org.clever.graaljs.core.utils.StringUtils.abbreviate(str, offset, maxWidth);
    }

    /**
     * <pre>
     *  .abbreviate(null, "...", *)      = null
     *  .abbreviate("abcdefg", null, *)  = "abcdefg"
     *  .abbreviate("", "...", 4)        = ""
     *  .abbreviate("abcdefg", ".", 5)   = "abcd."
     *  .abbreviate("abcdefg", ".", 7)   = "abcdefg"
     *  .abbreviate("abcdefg", ".", 8)   = "abcdefg"
     *  .abbreviate("abcdefg", "..", 4)  = "ab.."
     *  .abbreviate("abcdefg", "..", 3)  = "a.."
     *  .abbreviate("abcdefg", "..", 2)  = IllegalArgumentException
     *  .abbreviate("abcdefg", "...", 3) = IllegalArgumentException
     * </pre>
     */
    public String abbreviate(String str, String abbrevMarker, int maxWidth) {
        return org.clever.graaljs.core.utils.StringUtils.abbreviate(str, abbrevMarker, maxWidth);
    }

    /**
     * <pre>
     *  .abbreviate(null, "...", *)      = null
     *  .abbreviate("abcdefg", null, *)  = "abcdefg"
     *  .abbreviate("", "...", 4)        = ""
     *  .abbreviate("abcdefg", ".", 5)   = "abcd."
     *  .abbreviate("abcdefg", ".", 7)   = "abcdefg"
     *  .abbreviate("abcdefg", ".", 8)   = "abcdefg"
     *  .abbreviate("abcdefg", "..", 4)  = "ab.."
     *  .abbreviate("abcdefg", "..", 3)  = "a.."
     *  .abbreviate("abcdefg", "..", 2)  = IllegalArgumentException
     *  .abbreviate("abcdefg", "...", 3) = IllegalArgumentException
     * </pre>
     */
    public String abbreviate(String str, String abbrevMarker, int offset, int maxWidth) {
        return org.clever.graaljs.core.utils.StringUtils.abbreviate(str, abbrevMarker, maxWidth);
    }

    /**
     * <pre>
     *  .abbreviateMiddle(null, null, 0)        = null
     *  .abbreviateMiddle("abc", null, 0)       = "abc"
     *  .abbreviateMiddle("abc", ".", 0)        = "abc"
     *  .abbreviateMiddle("abc", ".", 3)        = "abc"
     *  .abbreviateMiddle("abcdef", ".", 4)     = "ab.f"
     * </pre>
     */
    public String abbreviateMiddle(String str, String middle, int length) {
        return org.clever.graaljs.core.utils.StringUtils.abbreviateMiddle(str, middle, length);
    }

    // Difference
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * <pre>
     *  .difference(null, null)          = null
     *  .difference("", "")              = ""
     *  .difference("", "abc")           = "abc"
     *  .difference("abc", "")           = ""
     *  .difference("abc", "abc")        = ""
     *  .difference("abc", "ab")         = ""
     *  .difference("ab", "abxyz")       = "xyz"
     *  .difference("abcde", "abxyz")    = "xyz"
     *  .difference("abcde", "xyz")      = "xyz"
     * </pre>
     */
    public String difference(String str1, String str2) {
        return org.clever.graaljs.core.utils.StringUtils.difference(str1, str2);
    }

    /**
     * <pre>
     *  .indexOfDifference(null, null)       = -1
     *  .indexOfDifference("", "")           = -1
     *  .indexOfDifference("", "abc")        = 0
     *  .indexOfDifference("abc", "")        = 0
     *  .indexOfDifference("abc", "abc")     = -1
     *  .indexOfDifference("ab", "abxyz")    = 2
     *  .indexOfDifference("abcde", "abxyz") = 2
     *  .indexOfDifference("abcde", "xyz")   = 0
     * </pre>
     */
    public int indexOfDifference(CharSequence cs1, CharSequence cs2) {
        return org.clever.graaljs.core.utils.StringUtils.indexOfDifference(cs1, cs2);
    }

    /**
     * <pre>
     *  .indexOfDifference(null)                                            = -1
     *  .indexOfDifference(new String[] {})                                 = -1
     *  .indexOfDifference(new String[] {"abc"})                            = -1
     *  .indexOfDifference(new String[] {null, null})                       = -1
     *  .indexOfDifference(new String[] {"", ""})                           = -1
     *  .indexOfDifference(new String[] {"", null})                         = 0
     *  .indexOfDifference(new String[] {"abc", null, null})                = 0
     *  .indexOfDifference(new String[] {null, null, "abc"})                = 0
     *  .indexOfDifference(new String[] {"", "abc"})                        = 0
     *  .indexOfDifference(new String[] {"abc", ""})                        = 0
     *  .indexOfDifference(new String[] {"abc", "abc"})                     = -1
     *  .indexOfDifference(new String[] {"abc", "a"})                       = 1
     *  .indexOfDifference(new String[] {"ab", "abxyz"})                    = 2
     *  .indexOfDifference(new String[] {"abcde", "abxyz"})                 = 2
     *  .indexOfDifference(new String[] {"abcde", "xyz"})                   = 0
     *  .indexOfDifference(new String[] {"xyz", "abcde"})                   = 0
     *  .indexOfDifference(new String[] {"i am a machine", "i am a robot"}) = 7
     * </pre>
     */
    public int indexOfDifference(CharSequence... css) {
        return org.clever.graaljs.core.utils.StringUtils.indexOfDifference(css);
    }

    /**
     * <pre>
     *  .getCommonPrefix(null)                                              = ""
     *  .getCommonPrefix(new String[] {})                                   = ""
     *  .getCommonPrefix(new String[] {"abc"})                              = "abc"
     *  .getCommonPrefix(new String[] {null, null})                         = ""
     *  .getCommonPrefix(new String[] {"", ""})                             = ""
     *  .getCommonPrefix(new String[] {"", null})                           = ""
     *  .getCommonPrefix(new String[] {"abc", null, null})                  = ""
     *  .getCommonPrefix(new String[] {null, null, "abc"})                  = ""
     *  .getCommonPrefix(new String[] {"", "abc"})                          = ""
     *  .getCommonPrefix(new String[] {"abc", ""})                          = ""
     *  .getCommonPrefix(new String[] {"abc", "abc"})                       = "abc"
     *  .getCommonPrefix(new String[] {"abc", "a"})                         = "a"
     *  .getCommonPrefix(new String[] {"ab", "abxyz"})                      = "ab"
     *  .getCommonPrefix(new String[] {"abcde", "abxyz"})                   = "ab"
     *  .getCommonPrefix(new String[] {"abcde", "xyz"})                     = ""
     *  .getCommonPrefix(new String[] {"xyz", "abcde"})                     = ""
     *  .getCommonPrefix(new String[] {"i am a machine", "i am a robot"})   = "i am a "
     * </pre>
     */
    public String getCommonPrefix(String... strs) {
        return org.clever.graaljs.core.utils.StringUtils.getCommonPrefix(strs);
    }

    // Misc
    //----------------------------------------------------------------------------------------------------------------------------------------------

    // startsWith
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * <pre>
     *  .startsWith(null, null)      = true
     *  .startsWith(null, "abc")     = false
     *  .startsWith("abcdef", null)  = false
     *  .startsWith("abcdef", "abc") = true
     *  .startsWith("ABCDEF", "abc") = false
     * </pre>
     */
    public boolean startsWith(CharSequence str, CharSequence prefix) {
        return org.clever.graaljs.core.utils.StringUtils.startsWith(str, prefix);
    }

    /**
     * <pre>
     *  .startsWithIgnoreCase(null, null)      = true
     *  .startsWithIgnoreCase(null, "abc")     = false
     *  .startsWithIgnoreCase("abcdef", null)  = false
     *  .startsWithIgnoreCase("abcdef", "abc") = true
     *  .startsWithIgnoreCase("ABCDEF", "abc") = true
     * </pre>
     */
    public boolean startsWithIgnoreCase(CharSequence str, CharSequence prefix) {
        return org.clever.graaljs.core.utils.StringUtils.startsWithIgnoreCase(str, prefix);
    }

    /**
     * <pre>
     *  .startsWithAny(null, null)                                   = false
     *  .startsWithAny(null, new String[] {"abc"})                   = false
     *  .startsWithAny("abcxyz", null)                               = false
     *  .startsWithAny("abcxyz", new String[] {""})                  = true
     *  .startsWithAny("abcxyz", new String[] {"abc"})               = true
     *  .startsWithAny("abcxyz", new String[] {null, "xyz", "abc"})  = true
     *  .startsWithAny("abcxyz", null, "xyz", "ABCX")                = false
     *  .startsWithAny("ABCXYZ", null, "xyz", "abc")                 = false
     * </pre>
     */
    public boolean startsWithAny(CharSequence sequence, CharSequence... searchStrings) {
        return org.clever.graaljs.core.utils.StringUtils.startsWithAny(sequence, searchStrings);
    }

    // endsWith
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * <pre>
     *  .endsWith(null, null)      = true
     *  .endsWith(null, "def")     = false
     *  .endsWith("abcdef", null)  = false
     *  .endsWith("abcdef", "def") = true
     *  .endsWith("ABCDEF", "def") = false
     *  .endsWith("ABCDEF", "cde") = false
     *  .endsWith("ABCDEF", "")    = true
     * </pre>
     */
    public boolean endsWith(CharSequence str, CharSequence suffix) {
        return org.clever.graaljs.core.utils.StringUtils.endsWith(str, suffix);
    }

    /**
     * <pre>
     *  .endsWithIgnoreCase(null, null)      = true
     *  .endsWithIgnoreCase(null, "def")     = false
     *  .endsWithIgnoreCase("abcdef", null)  = false
     *  .endsWithIgnoreCase("abcdef", "def") = true
     *  .endsWithIgnoreCase("ABCDEF", "def") = true
     *  .endsWithIgnoreCase("ABCDEF", "cde") = false
     * </pre>
     */
    public boolean endsWithIgnoreCase(CharSequence str, CharSequence suffix) {
        return org.clever.graaljs.core.utils.StringUtils.endsWithIgnoreCase(str, suffix);
    }

    /**
     *
     */
    public String normalizeSpace(String str) {
        return org.clever.graaljs.core.utils.StringUtils.normalizeSpace(str);
    }

    /**
     * <pre>
     *  .endsWithAny(null, null)                                    = false
     *  .endsWithAny(null, new String[] {"abc"})                    = false
     *  .endsWithAny("abcxyz", null)                                = false
     *  .endsWithAny("abcxyz", new String[] {""})                   = true
     *  .endsWithAny("abcxyz", new String[] {"xyz"})                = true
     *  .endsWithAny("abcxyz", new String[] {null, "xyz", "abc"})   = true
     *  .endsWithAny("abcXYZ", "def", "XYZ")                        = true
     *  .endsWithAny("abcXYZ", "def", "xyz")                        = false
     * </pre>
     */
    public boolean endsWithAny(CharSequence sequence, CharSequence... searchStrings) {
        return org.clever.graaljs.core.utils.StringUtils.endsWithAny(sequence, searchStrings);
    }

    /**
     * <pre>
     *  .appendIfMissing(null, null)        = null
     *  .appendIfMissing("abc", null)       = "abc"
     *  .appendIfMissing("", "xyz")         = "xyz"
     *  .appendIfMissing("abc", "xyz")      = "abcxyz"
     *  .appendIfMissing("abcxyz", "xyz")   = "abcxyz"
     *  .appendIfMissing("abcXYZ", "xyz")   = "abcXYZxyz"
     * </pre>
     */
    public String appendIfMissing(String str, CharSequence suffix, CharSequence... suffixes) {
        return org.clever.graaljs.core.utils.StringUtils.appendIfMissing(str, suffix, suffixes);
    }

    /**
     * <pre>
     *  .appendIfMissingIgnoreCase(null, null)      = null
     *  .appendIfMissingIgnoreCase("abc", null)     = "abc"
     *  .appendIfMissingIgnoreCase("", "xyz")       = "xyz"
     *  .appendIfMissingIgnoreCase("abc", "xyz")    = "abcxyz"
     *  .appendIfMissingIgnoreCase("abcxyz", "xyz") = "abcxyz"
     *  .appendIfMissingIgnoreCase("abcXYZ", "xyz") = "abcXYZ"
     * </pre>
     */
    public String appendIfMissingIgnoreCase(String str, CharSequence suffix, CharSequence... suffixes) {
        return org.clever.graaljs.core.utils.StringUtils.appendIfMissingIgnoreCase(str, suffix, suffixes);
    }

    /**
     * <pre>
     *  .prependIfMissing(null, null)      = null
     *  .prependIfMissing("abc", null)     = "abc"
     *  .prependIfMissing("", "xyz")       = "xyz"
     *  .prependIfMissing("abc", "xyz")    = "xyzabc"
     *  .prependIfMissing("xyzabc", "xyz") = "xyzabc"
     *  .prependIfMissing("XYZabc", "xyz") = "xyzXYZabc"
     * </pre>
     */
    public String prependIfMissing(String str, CharSequence prefix, CharSequence... prefixes) {
        return org.clever.graaljs.core.utils.StringUtils.prependIfMissing(str, prefix, prefixes);
    }

    /**
     * <pre>
     *  .prependIfMissingIgnoreCase(null, null)      = null
     *  .prependIfMissingIgnoreCase("abc", null)     = "abc"
     *  .prependIfMissingIgnoreCase("", "xyz")       = "xyz"
     *  .prependIfMissingIgnoreCase("abc", "xyz")    = "xyzabc"
     *  .prependIfMissingIgnoreCase("xyzabc", "xyz") = "xyzabc"
     *  .prependIfMissingIgnoreCase("XYZabc", "xyz") = "XYZabc"
     * </pre>
     */
    public String prependIfMissingIgnoreCase(String str, CharSequence prefix, CharSequence... prefixes) {
        return org.clever.graaljs.core.utils.StringUtils.prependIfMissingIgnoreCase(str, prefix, prefixes);
    }

    /**
     * 使用指定的字符编码将byte[]转换为字符串
     */
    public String toEncodedString(byte[] bytes, Charset charset) {
        return org.clever.graaljs.core.utils.StringUtils.toEncodedString(bytes, charset);
    }

    /**
     * <pre>
     *  .wrap(null, *)        = null
     *  .wrap("", *)          = ""
     *  .wrap("ab", '\0')     = "ab"
     *  .wrap("ab", 'x')      = "xabx"
     *  .wrap("ab", '\'')     = "'ab'"
     *  .wrap("\"ab\"", '\"') = "\"\"ab\"\""
     * </pre>
     */
    public String wrap(String str, char wrapWith) {
        return org.clever.graaljs.core.utils.StringUtils.wrap(str, wrapWith);
    }

    /**
     * <pre>
     *  .wrap(null, *)         = null
     *  .wrap("", *)           = ""
     *  .wrap("ab", null)      = "ab"
     *  .wrap("ab", "x")       = "xabx"
     *  .wrap("ab", "\"")      = "\"ab\""
     *  .wrap("\"ab\"", "\"")  = "\"\"ab\"\""
     *  .wrap("ab", "'")       = "'ab'"
     *  .wrap("'abcd'", "'")   = "''abcd''"
     *  .wrap("\"abcd\"", "'") = "'\"abcd\"'"
     *  .wrap("'abcd'", "\"")  = "\"'abcd'\""
     * </pre>
     */
    public String wrap(String str, String wrapWith) {
        return org.clever.graaljs.core.utils.StringUtils.wrap(str, wrapWith);
    }

    /**
     * <pre>
     *  .wrap(null, *)        = null
     *  .wrap("", *)          = ""
     *  .wrap("ab", '\0')     = "ab"
     *  .wrap("ab", 'x')      = "xabx"
     *  .wrap("ab", '\'')     = "'ab'"
     *  .wrap("\"ab\"", '\"') = "\"ab\""
     *  .wrap("/", '/')  = "/"
     *  .wrap("a/b/c", '/')  = "/a/b/c/"
     *  .wrap("/a/b/c", '/')  = "/a/b/c/"
     *  .wrap("a/b/c/", '/')  = "/a/b/c/"
     * </pre>
     */
    public String wrapIfMissing(String str, char wrapWith) {
        return org.clever.graaljs.core.utils.StringUtils.wrapIfMissing(str, wrapWith);
    }

    /**
     * <pre>
     *  .wrap(null, *)         = null
     *  .wrap("", *)           = ""
     *  .wrap("ab", null)      = "ab"
     *  .wrap("ab", "x")       = "xabx"
     *  .wrap("ab", "\"")      = "\"ab\""
     *  .wrap("\"ab\"", "\"")  = "\"ab\""
     *  .wrap("ab", "'")       = "'ab'"
     *  .wrap("'abcd'", "'")   = "'abcd'"
     *  .wrap("\"abcd\"", "'") = "'\"abcd\"'"
     *  .wrap("'abcd'", "\"")  = "\"'abcd'\""
     *  .wrap("/", "/")  = "/"
     *  .wrap("a/b/c", "/")  = "/a/b/c/"
     *  .wrap("/a/b/c", "/")  = "/a/b/c/"
     *  .wrap("a/b/c/", "/")  = "/a/b/c/"
     * </pre>
     */
    public String wrapIfMissing(String str, String wrapWith) {
        return org.clever.graaljs.core.utils.StringUtils.wrapIfMissing(str, wrapWith);
    }

    /**
     * <pre>
     *  .unwrap(null, null)         = null
     *  .unwrap(null, "")           = null
     *  .unwrap(null, "1")          = null
     *  .unwrap("\'abc\'", "\'")    = "abc"
     *  .unwrap("\"abc\"", "\"")    = "abc"
     *  .unwrap("AABabcBAA", "AA")  = "BabcB"
     *  .unwrap("A", "#")           = "A"
     *  .unwrap("#A", "#")          = "#A"
     *  .unwrap("A#", "#")          = "A#"
     * </pre>
     */
    public String unwrap(String str, String wrapToken) {
        return org.clever.graaljs.core.utils.StringUtils.unwrap(str, wrapToken);
    }

    /**
     * <pre>
     *  .unwrap(null, null)         = null
     *  .unwrap(null, '\0')         = null
     *  .unwrap(null, '1')          = null
     *  .unwrap("\'abc\'", '\'')    = "abc"
     *  .unwrap("AABabcBAA", 'A')   = "ABabcBA"
     *  .unwrap("A", '#')           = "A"
     *  .unwrap("#A", '#')          = "#A"
     *  .unwrap("A#", '#')          = "A#"
     * </pre>
     */
    public String unwrap(String str, char wrapChar) {
        return org.clever.graaljs.core.utils.StringUtils.unwrap(str, wrapChar);
    }

    /**
     * <pre>
     *  .toCodePoints(null)   =  null
     *  .toCodePoints("")     =  []  // empty array
     * </pre>
     */
    public int[] toCodePoints(CharSequence str) {
        return org.clever.graaljs.core.utils.StringUtils.toCodePoints(str);
    }
}
