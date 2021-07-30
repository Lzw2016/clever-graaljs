interface StringUtils {
    // Other
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 字符串驼峰转下划线格式
     *
     * @param param 需要转换的字符串
     * @return 转换好的字符串
     */
    camelToUnderline(param: JString): JString;

    /**
     * 字符串下划线转驼峰格式
     *
     * @param param 需要转换的字符串
     * @return 转换好的字符串
     */
    underlineToCamel(param: JString): JString;

    /**
     * 字符串下划线转驼峰格式
     *
     * @param param          需要转换的字符串
     * @param firstUpperCase 首字母是否大写
     * @return 转换好的字符串
     */
    underlineToCamel(param: JString, firstUpperCase: JBoolean): JString;

    /**
     * 获取字符串byte[]数据
     */
    getByteFromString(str: JString): JByte[];

    /**
     * 根据byte[]数据初始化字符串
     */
    getStringFromByte(bytes: JByte[]): JString;

    /**
     * 调用对象的toString方法，如果对象为空返回默认值
     *
     * @param object     需要toString的对象
     * @param defaultStr 对象为空时返回的默认值
     * @return 返回对象的toString方法结果
     */
    objectToString(object: any, defaultStr: JString): JString;

    /**
     * 除去html标签
     *
     * @param htmlStr 含有html标签的字符串
     * @return 网页文本内容
     */
    delHTMLTag(htmlStr: JString): JString;

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
    isEmpty(cs: JString): JBoolean;

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
    isNotEmpty(cs: JString): JBoolean;

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
    isAnyEmpty(...css: JString[]): JBoolean;

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
    isNoneEmpty(...css: JString[]): JBoolean;

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
    isAllEmpty(...css: JString[]): JBoolean;

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
    isBlank(cs: JString): JBoolean;

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
    isNotBlank(cs: JString): JBoolean;

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
    isAnyBlank(...css: JString[]): JBoolean;

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
    isNoneBlank(...css: JString[]): JBoolean;

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
    isAllBlank(...css: JString[]): JBoolean;

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
    trim(str: JString): JString;

    /**
     * <pre>
     *  .trimToNull(null)          = null
     *  .trimToNull("")            = null
     *  .trimToNull("     ")       = null
     *  .trimToNull("abc")         = "abc"
     *  .trimToNull("    abc    ") = "abc"
     * </pre>
     */
    trimToNull(str: JString): JString;

    /**
     * <pre>
     *  .trimToEmpty(null)          = ""
     *  .trimToEmpty("")            = ""
     *  .trimToEmpty("     ")       = ""
     *  .trimToEmpty("abc")         = "abc"
     *  .trimToEmpty("    abc    ") = "abc"
     * </pre>
     */
    trimToEmpty(str: JString): JString;

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
    truncate(str: JString, maxWidth: JInt): JString;

    /**
     * 截断字符串
     *
     * @param str      被截断的字符串
     * @param offset   起始位置
     * @param maxWidth 结果字符串的最大长度
     */
    truncate(str: JString, offset: JInt, maxWidth: JInt): JString;

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
    strip(str: JString): JString;

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
    stripToNull(str: JString): JString;

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
    stripToEmpty(str: JString): JString;

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
    strip(str: JString, stripChars: JString): JString;

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
    stripStart(str: JString, stripChars: JString): JString;

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
    stripEnd(str: JString, stripChars: JString): JString;

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
    stripAll(...strs: JString[]): JString[];

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
    stripAll(strs: JString[], stripChars: JString): JString[];

    /**
     * 从字符串中删除音调符号。例如，“à”将被“a”替换。
     * <pre>
     *  .stripAccents(null)         = null
     *  .stripAccents("")           = ""
     *  .stripAccents("control")    = "control"
     *  .stripAccents("éclair")     = "eclair"
     * </pre>
     */
    stripAccents(input: JString): JString;

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
    equals(cs1: JString, cs2: JString): JBoolean;

    /**
     * <pre>
     *  .equalsIgnoreCase(null, null)   = true
     *  .equalsIgnoreCase(null, "abc")  = false
     *  .equalsIgnoreCase("abc", null)  = false
     *  .equalsIgnoreCase("abc", "abc") = true
     *  .equalsIgnoreCase("abc", "ABC") = true
     * </pre>
     */
    equalsIgnoreCase(str1: JString, str2: JString): JBoolean;

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
    compare(str1: JString, str2: JString): JInt;

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
    compare(str1: JString, str2: JString, nullIsLess: JBoolean): JInt;

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
    compareIgnoreCase(str1: JString, str2: JString): JInt;

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
    compareIgnoreCase(str1: JString, str2: JString, nullIsLess: JBoolean): JInt;

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
    equalsAny(string: JString, ...searchStrings: JString[]): JBoolean;

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
    equalsAnyIgnoreCase(string: JString, ...searchStrings: JString[]): JBoolean;

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
    indexOf(seq: JString, searchChar: JInt): JInt;

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
    indexOf(seq: JString, searchChar: JInt, startPos: JInt): JInt;

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
    indexOf(seq: JString, searchSeq: JString): JInt;

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
    indexOf(seq: JString, searchSeq: JString, startPos: JInt): JInt;

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
    ordinalIndexOf(str: JString, searchStr: JString, ordinal: JInt): JInt;

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
    indexOfIgnoreCase(str: JString, searchStr: JString): JInt;

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
    indexOfIgnoreCase(str: JString, searchStr: JString, startPos: JInt): JInt;

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
    lastIndexOf(seq: JString, searchChar: JInt): JInt;

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
    lastIndexOf(seq: JString, searchChar: JInt, startPos: JInt): JInt;

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
    lastIndexOf(seq: JString, searchSeq: JString): JInt;

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
    lastOrdinalIndexOf(str: JString, searchStr: JString, ordinal: JInt): JInt;

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
    lastIndexOf(seq: JString, searchSeq: JString, startPos: JInt): JInt;

    /**
     * <pre>
     *  .lastIndexOfIgnoreCase(null, *)          = -1
     *  .lastIndexOfIgnoreCase(*, null)          = -1
     *  .lastIndexOfIgnoreCase("aabaabaa", "A")  = 7
     *  .lastIndexOfIgnoreCase("aabaabaa", "B")  = 5
     *  .lastIndexOfIgnoreCase("aabaabaa", "AB") = 4
     * </pre>
     */
    lastIndexOfIgnoreCase(str: JString, searchStr: JString): JInt;

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
    lastIndexOfIgnoreCase(str: JString, searchStr: JString, startPos: JInt): JInt;

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
    contains(seq: JString, searchChar: JInt): JBoolean;

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
    contains(seq: JString, searchSeq: JString): JBoolean;

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
    containsIgnoreCase(str: JString, searchStr: JString): JBoolean;

    /**
     * 检查给定的CharSequence是否包含任何空白字符
     */
    containsWhitespace(seq: JString): JBoolean;

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
    indexOfAny(cs: JString, ...searchChars: JChar[]): JInt;

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
    indexOfAny(cs: JString, searchChars: JString): JInt;

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
    containsAny(cs: JString, ...searchChars: JChar[]): JBoolean;

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
    containsAny(cs: JString, searchChars: JString): JBoolean;

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
    containsAny(cs: JString, ...searchCharSequences: JString[]): JBoolean;

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
    indexOfAnyBut(cs: JString, ...searchChars: JChar[]): JInt;

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
    indexOfAnyBut(seq: JString, searchChars: JString): JInt;

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
    containsOnly(cs: JString, ...valid: JChar[]): JBoolean;

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
    containsOnly(cs: JString, validChars: JString): JBoolean;

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
    containsNone(cs: JString, ...searchChars: JChar[]): JBoolean;

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
    containsNone(cs: JString, invalidChars: JString): JBoolean;

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
    indexOfAny(str: JString, ...searchStrs: JString[]): JInt;

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
    lastIndexOfAny(str: JString, ...searchStrs: JString[]): JInt;

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
    substring(str: JString, start: JInt): JString;

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
    substring(str: JString, start: JInt, end: JInt): JString;

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
    left(str: JString, len: JInt): JString;

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
    right(str: JString, len: JInt): JString;

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
    mid(str: JString, pos: JInt, len: JInt): JString;

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
    substringBefore(str: JString, separator: JString): JString;

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
    substringAfter(str: JString, separator: JString): JString;

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
    substringBeforeLast(str: JString, separator: JString): JString;

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
    substringAfterLast(str: JString, separator: JString): JString;

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
    substringBetween(str: JString, tag: JString): JString;

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
    substringBetween(str: JString, open: JString, close: JString): JString;

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
    substringsBetween(str: JString, open: JString, close: JString): JString[];

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
    split(str: JString): JString[];

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
    split(str: JString, separatorChar: JChar): JString[];

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
    split(str: JString, separatorChars: JString): JString[];

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
    split(str: JString, separatorChars: JString, max: JInt): JString[];

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
    splitByWholeSeparator(str: JString, separator: JString): JString[];

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
    splitByWholeSeparator(str: JString, separator: JString, max: JInt): JString[];

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
    splitByWholeSeparatorPreserveAllTokens(str: JString, separator: JString): JString[];

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
    splitByWholeSeparatorPreserveAllTokens(str: JString, separator: JString, max: JInt): JString[];

    /**
     * <pre>
     *  .splitPreserveAllTokens(null)       = null
     *  .splitPreserveAllTokens("")         = []
     *  .splitPreserveAllTokens("abc def")  = ["abc", "def"]
     *  .splitPreserveAllTokens("abc  def") = ["abc", "", "def"]
     *  .splitPreserveAllTokens(" abc ")    = ["", "abc", ""]
     * </pre>
     */
    splitPreserveAllTokens(str: JString): JString[];

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
    splitPreserveAllTokens(str: JString, separatorChar: JChar): JString[];

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
    splitPreserveAllTokens(str: JString, separatorChars: JString): JString[];

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
    splitPreserveAllTokens(str: JString, separatorChars: JString, max: JInt): JString[];

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
    splitByCharacterType(str: JString): JString[];

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
    splitByCharacterTypeCamelCase(str: JString): JString[];

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
    join(array: any[], separator: JChar): JString;

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join([1, 2, 3], ';')  = "1;2;3"
     *  .join([1, 2, 3], null) = "123"
     * </pre>
     */
    join(array: JLong[], separator: JChar): JString;

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join([1, 2, 3], ';')  = "1;2;3"
     *  .join([1, 2, 3], null) = "123"
     * </pre>
     */
    join(array: JInt[], separator: JChar): JString;

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join([1, 2, 3], ';')  = "1;2;3"
     *  .join([1, 2, 3], null) = "123"
     * </pre>
     */
    join(array: JShort[], separator: JChar): JString;

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join([1, 2, 3], ';')  = "1;2;3"
     *  .join([1, 2, 3], null) = "123"
     * </pre>
     */
    join(array: JByte[], separator: JChar): JString;

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join([1, 2, 3], ';')  = "1;2;3"
     *  .join([1, 2, 3], null) = "123"
     * </pre>
     */
    join(array: JChar[], separator: JChar): JString;

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join([1, 2, 3], ';')  = "1;2;3"
     *  .join([1, 2, 3], null) = "123"
     * </pre>
     */
    join(array: JFloat[], separator: JChar): JString;

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join([1, 2, 3], ';')  = "1;2;3"
     *  .join([1, 2, 3], null) = "123"
     * </pre>
     */
    join(array: JDouble[], separator: JChar): JString;

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
    join(array: any[], separator: JChar, startIndex: JInt, endIndex: JInt): JString;

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join([1, 2, 3], ';')  = "1;2;3"
     *  .join([1, 2, 3], null) = "123"
     * </pre>
     */
    join(array: JLong[], separator: JChar, startIndex: JInt, endIndex: JInt): JString;

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join([1, 2, 3], ';')  = "1;2;3"
     *  .join([1, 2, 3], null) = "123"
     * </pre>
     */
    join(array: JInt[], separator: JChar, startIndex: JInt, endIndex: JInt): JString;

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join([1, 2, 3], ';')  = "1;2;3"
     *  .join([1, 2, 3], null) = "123"
     * </pre>
     */
    join(array: JByte[], separator: JChar, startIndex: JInt, endIndex: JInt): JString;

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join([1, 2, 3], ';')  = "1;2;3"
     *  .join([1, 2, 3], null) = "123"
     * </pre>
     */
    join(array: JShort[], separator: JChar, startIndex: JInt, endIndex: JInt): JString;

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join([1, 2, 3], ';')  = "1;2;3"
     *  .join([1, 2, 3], null) = "123"
     * </pre>
     */
    join(array: JChar[], separator: JChar, startIndex: JInt, endIndex: JInt): JString;

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join([1, 2, 3], ';')  = "1;2;3"
     *  .join([1, 2, 3], null) = "123"
     * </pre>
     */
    join(array: JDouble[], separator: JChar, startIndex: JInt, endIndex: JInt): JString;

    /**
     * <pre>
     *  .join(null, *)               = null
     *  .join([], *)                 = ""
     *  .join([null], *)             = ""
     *  .join([1, 2, 3], ';')  = "1;2;3"
     *  .join([1, 2, 3], null) = "123"
     * </pre>
     */
    join(array: JFloat[], separator: JChar, startIndex: JInt, endIndex: JInt): JString;

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
    join(array: any[], separator: JString): JString;

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
    join(array: any[], separator: JString, startIndex: JInt, endIndex: JInt): JString;

    /**
     * 将所提供迭代器的元素联接到包含所提供元素的单个字符串中
     */
    join(iterator: Iterator<any>, separator: JChar): JString;

    /**
     * 将所提供迭代器的元素联接到包含所提供元素的单个字符串中
     */
    join(iterator: JIterator<any>, separator: JString): JString;

    /**
     * 将提供的Iterable的元素联接到包含所提供元素的单个字符串中
     */
    join(iterable: JIterable<any>, separator: JChar): JString;

    /**
     * 将提供的Iterable的元素联接到包含所提供元素的单个字符串中
     */
    join(iterable: JIterable<any>, separator: JString): JString;

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
    join(list: JList<any>, separator: JChar, startIndex: JInt, endIndex: JInt): JString;

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
    join(list: JList<any>, separator: JString, startIndex: JInt, endIndex: JInt): JString;

    /**
     * <pre>
     *  .joinWith(",", {"a", "b"})        = "a,b"
     *  .joinWith(",", {"a", "b",""})     = "a,b,"
     *  .joinWith(",", {"a", null, "b"})  = "a,,b"
     *  .joinWith(null, {"a", "b"})       = "ab"
     * </pre>
     */
    joinWith(separator: JString, ...objects: any[]): JString;
}

declare const StringUtils: StringUtils;
