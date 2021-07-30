interface HanyuPinyinOutputFormat {
    net_sourceforge_pinyin4j_format_HanyuPinyinOutputFormat: "net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat",
    // /**
    //  * 输出大小写配置
    //  */
    // setCaseType(caseType: HanyuPinyinCaseType): void;
    //
    // /**
    //  * 定义汉语声调的输出格式
    //  */
    // setToneType(toneType: HanyuPinyinToneType): void;
    //
    // /**
    //  * 定义字符'u'('ü')的输出格式
    //  */
    // setVCharType(vCharType: HanyuPinyinVCharType): void;
    //
    // /**
    //  * 输出大小写配置
    //  */
    // getCaseType(): void;
    //
    // /**
    //  * 定义汉语声调的输出格式
    //  */
    // getToneType(): void;
    //
    // /**
    //  * 定义字符'u'('ü')的输出格式
    //  */
    // getVCharType(): void;
}

interface PinyinUtils {
    /**
     * 获取一个汉字的所有拼音
     *
     * @param c      汉字字符
     * @param format 设置拼音的格式
     * @return 如果参数c不是汉字或转换，成功返回所有拼音
     */
    toAllPinYin(c: JChar, format: HanyuPinyinOutputFormat): JString[];

    /**
     * 获取一个汉字的所有拼音<br/>
     * 使用默认格式：1.全小写；2.用音调字符表示音调；3.'ü'的表示方式"ü"，默认格式<br/>
     *
     * @param c 汉字字符
     * @return 如果参数c不是汉字或转换失败返回null，成功返回所有拼音
     */
    toAllPinYin(c: JChar): JString[];

    /**
     * 获取一个汉字的一个发音
     *
     * @param c      汉字字符
     * @param format 设置拼音的格式
     * @return 如果参数c不是汉字或转换失败返回null，成功返回第一个发音的拼音
     */
    toPinYin(c: JChar, format: HanyuPinyinOutputFormat): JString;

    /**
     * 获取一个汉字的一个发音
     *
     * @param c 汉字字符
     * @return 如果参数c不是汉字或转换失败返回null，成功返回第一个发音的拼音
     */
    toPinYin(c: JChar): JString;

    /**
     * 把一个汉字字符串转成拼音字符串，不能转换的部分原样输出
     *
     * @param str       汉字字符串
     * @param separator 拼音之间的分隔符
     * @param format    设置拼音的格式
     * @return 转换后的字符串，转换失败返回null
     */
    getStringPinYin(str: JString, separator: JString, format: HanyuPinyinOutputFormat): JString;

    /**
     * 把一个汉字字符串转成拼音字符串，不能转换的部分原样输出<br/>
     * 使用默认格式：1.全小写；2.用音调字符表示音调；3.'ü'的表示方式"ü"，默认格式<br/>
     *
     * @param str       汉字字符串
     * @param separator 拼音之间的分隔符
     * @return 转换后的字符串，转换失败返回null
     */
    getStringPinYin(str: JString, separator: JString): JString;

    /**
     * 把一个汉字字符串转成拼音字符串，忽略不能转换的部分<br/>
     * <b>注意：不输出不能转换的部分</b>
     *
     * @param str       汉字字符串
     * @param separator 拼音之间的分隔符
     * @param format    设置拼音的格式
     * @return 转换后的字符串，转换失败返回null
     */
    getStringPurePinYin(str: JString, separator: JString, format: HanyuPinyinOutputFormat): JString;

    /**
     * 把一个汉字字符串转成拼音字符串，忽略不能转换的部分<br/>
     * 使用默认格式：1.全小写；2.用音调字符表示音调；3.'ü'的表示方式"ü"，默认格式<br/>
     * <b>注意：不输出不能转换的部分</b>
     *
     * @param str       汉字字符串
     * @param separator 拼音之间的分隔符
     * @return 转换后的字符串
     */
    getStringPurePinYin(str: JString, separator: JString): JString;

    /**
     * 获取汉字的拼音首字母<br/>
     *
     * @param c 汉字字符
     * @return 成功返回首字母
     */
    toHeadPinYin(c: JChar): JChar;

    /**
     * 获取一个中文字符串的所有首拼，不能转换的部分原样输出<br/>
     *
     * @param str       汉字字符串
     * @param separator 拼音之间的分隔符
     * @return 转换后的字符串，转换失败返回null
     */
    getStringHeadPinYin(str: JString, separator: JString): JString;

    /**
     * 获取一个中文字符串的所有首拼，忽略不能转换的部分<br/>
     * <b>注意：不输出不能转换的部分</b>
     *
     * @param str       汉字字符串
     * @param separator 拼音之间的分隔符
     * @return 转换后的字符串，转换失败返回null
     */
    getStringHeadPurePinYin(str: JString, separator: JString): JString;

    /**
     * 判断一个字符串是否全部由中文汉字组成<br/>
     *
     * @param str 判断的字符串
     * @return str完全由汉字组成返回true
     */
    isChineseString(str: JString): JBoolean;

    /**
     * @param caseType  输出大小写配置
     * @param toneType  定义汉语声调的输出格式
     * @param vCharType 定义字符'u'('ü')的输出格式
     */
    getFormat(caseType: HanyuPinyinCaseType, toneType: HanyuPinyinToneType, vCharType: HanyuPinyinVCharType): HanyuPinyinOutputFormat;
}

declare const PinyinUtils: PinyinUtils;
