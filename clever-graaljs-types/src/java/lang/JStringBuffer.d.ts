interface JStringBuffer extends JObject {
    java_lang_StringBuffer: "java.lang.StringBuffer";

    /**
     * 将 char 数组参数的子数组的字符串表示形式追加到此序列
     */
    append(str: JChar[], offset: JInt, len: JInt): JStringBuffer;

    /**
     * 将指定 CharSequence 的子序列追加到此序列
     */
    append(s: JCharSequence | JString, offset: JInt, len: JInt): JStringBuffer;

    /**
     * 追加 Object 参数的字符串表示形式
     */
    append(obj: any): JStringBuffer;

    /**
     * 返回当前容量
     */
    capacity(): JInt;

    /**
     * 返回此序列中指定索引处的 char 值
     */
    charAt(index: JInt): JChar;

    /**
     * 移除此序列的子字符串中的字符
     */
    delete(start: JInt, end: JInt): JStringBuffer;

    /**
     * 移除此序列指定位置的 char
     */
    deleteCharAt(index: JInt): JStringBuffer;

    /**
     * 返回第一次出现的指定子字符串在该字符串中的索引
     */
    indexOf(str: JString): JInt;

    /**
     * 从指定的索引处开始，返回第一次出现的指定子字符串在该字符串中的索引
     */
    indexOf(str: JString, fromIndex: JInt): JInt;

    /**
     * 将数组参数 str 的子数组的字符串表示形式插入此序列中
     */
    insert(index: JInt, str: JChar[], offset: JInt, len: JInt): JStringBuffer;

    /**
     * 将指定 CharSequence 的子序列插入此序列中
     */
    insert(dstOffset: JInt, s: JCharSequence | JString, start: JInt, end: JInt): JStringBuffer;

    /**
     * 将 Object 参数的字符串表示形式插入此字符序列中
     */
    insert(offset: JInt, obj: any): JStringBuffer;

    /**
     * 返回最右边出现的指定子字符串在此字符串中的索引
     */
    lastIndexOf(str: JString): JInt;

    /**
     * 返回最后一次出现的指定子字符串在此字符串中的索引
     */
    lastIndexOf(str: JString, fromIndex: JInt): JInt;

    /**
     * 返回长度（字符数）
     */
    length(): JInt;

    /**
     * 使用给定 String 中的字符替换此序列的子字符串中的字符
     */
    replace(start: JInt, end: JInt, str: JString): JStringBuffer;

    /**
     * 将此字符序列用其反转形式取代
     */
    reverse(): JStringBuffer;

    /**
     * 将给定索引处的字符设置为 ch
     */
    setCharAt(index: JInt, ch: JChar): JStringBuffer;

    /**
     * 返回一个新的 String，它包含此字符序列当前所包含的字符子序列
     */
    substring(start: JInt): JString;

    /**
     * 返回一个新的 String，它包含此序列当前所包含的字符子序列
     */
    substring(start: JInt, end: JInt): JString;

    /**
     * 返回此序列中数据的字符串表示形式
     */
    toString(s: JString): JString;
}
