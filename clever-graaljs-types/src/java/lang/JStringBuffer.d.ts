interface JStringBuffer extends JObject {
    java_lang_StringBuffer: "java.lang.StringBuffer";

    /**
     * 将 boolean 参数的字符串表示形式追加到序列
     */
    append(b: JBoolean): JStringBuffer;

    /**
     * 将 char 参数的字符串表示形式追加到此序列
     */
    append(c: JChar): JStringBuffer;

    /**
     * 将 char 数组参数的字符串表示形式追加到此序列
     */
    append(str: JChar[]): JStringBuffer;

    /**
     * 将 char 数组参数的子数组的字符串表示形式追加到此序列
     */
    append(str: JChar[], offset: JInt, len: JInt): JStringBuffer;

    /**
     * 将指定的 CharSequence 追加到该序列
     */
    append(s: JCharSequence | JString): JStringBuffer;

    /**
     * 将指定 CharSequence 的子序列追加到此序列
     */
    append(s: JCharSequence | JString, offset: JInt, len: JInt): JStringBuffer;

    /**
     * 将 double 参数的字符串表示形式追加到此序列
     */
    append(d: JDouble): JStringBuffer;

    /**
     * 将 float 参数的字符串表示形式追加到此序列
     */
    append(f: JFloat): JStringBuffer;

    /**
     * 将 int 参数的字符串表示形式追加到此序列
     */
    append(i: JInt): JStringBuffer;

    /**
     * 将 long 参数的字符串表示形式追加到此序列
     */
    append(l: JLong): JStringBuffer;

    /**
     * 追加 Object 参数的字符串表示形式
     */
    append(obj: any): JStringBuffer;

    /**
     * 将指定的字符串追加到此字符序列
     */
    append(str: JString): JStringBuffer;

    /**
     * 将指定的 StringBuffer 追加到此序列中
     */
    append(sb: JStringBuffer): JStringBuffer;

    /**
     * 将 codePoint 参数的字符串表示形式追加到此序列
     */
    appendCodePoint(codePoint: JInt): JStringBuffer;

    /**
     * 返回当前容量
     */
    capacity(): JInt;

    /**
     * 返回此序列中指定索引处的 char 值
     */
    charAt(index: JInt): JChar;

    /**
     * 返回指定索引处的字符（统一代码点）
     */
    codePointAt(index: JInt): JInt;

    /**
     * 返回指定索引前的字符（统一代码点）
     */
    codePointBefore(index: JInt): JInt;

    /**
     * 返回此序列指定文本范围内的统一代码点
     */
    codePointCount(beginIndex: JInt, endIndex: JInt): JInt;

    /**
     * 移除此序列的子字符串中的字符
     */
    delete(start: JInt, end: JInt): JStringBuffer;

    /**
     * 移除此序列指定位置的 char
     */
    deleteCharAt(index: JInt): JStringBuffer;

    /**
     * 确保容量至少等于指定的最小值
     */
    ensureCapacity(minimumCapacity: JInt): void;

    /**
     * 将字符从此序列复制到目标字符数组 dst
     */
    getChars(srcBegin: JInt, srcEnd: JInt, dst: JChar[], dstBegin: JInt): void;

    /**
     * 返回第一次出现的指定子字符串在该字符串中的索引
     */
    indexOf(str: JString): JInt;

    /**
     * 从指定的索引处开始，返回第一次出现的指定子字符串在该字符串中的索引
     */
    indexOf(str: JString, fromIndex: JInt): JInt;

    /**
     * 将 boolean 参数的字符串表示形式插入此序列中
     */
    insert(offset: JInt, b: JBoolean): JStringBuffer;

    /**
     * 将 char 参数的字符串表示形式插入此序列中
     */
    insert(offset: JInt, c: JChar): JStringBuffer;

    /**
     * 将 char 数组参数的字符串表示形式插入此序列中
     */
    insert(offset: JInt, str: JChar[]): JStringBuffer;

    /**
     * 将数组参数 str 的子数组的字符串表示形式插入此序列中
     */
    insert(index: JInt, str: JChar[], offset: JInt, len: JInt): JStringBuffer;

    /**
     * 将指定 CharSequence 插入此序列中
     */
    insert(offset: JInt, s: JCharSequence | JString): JStringBuffer;

    /**
     * 将指定 CharSequence 的子序列插入此序列中
     */
    insert(dstOffset: JInt, s: JCharSequence | JString, start: JInt, end: JInt): JStringBuffer;

    /**
     * 将 double 参数的字符串表示形式插入此序列中
     */
    insert(offset: JInt, d: JDouble): JStringBuffer;

    /**
     * 将 float 参数的字符串表示形式插入此序列中
     */
    insert(offset: JInt, f: JFloat): JStringBuffer;

    /**
     * 将 int 参数的字符串表示形式插入此序列中
     */
    insert(offset: JInt, i: JInt): JStringBuffer;

    /**
     * 将 long 参数的字符串表示形式插入此序列中
     */
    insert(offset: JInt, l: JLong): JStringBuffer;

    /**
     * 将 Object 参数的字符串表示形式插入此字符序列中
     */
    insert(offset: JInt, obj: Object): JStringBuffer;

    /**
     * 将字符串插入此字符序列中
     */
    insert(offset: JInt, str: JString): JStringBuffer;

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
     * 返回此序列中的一个索引，该索引是从给定 index 偏移 codePointOffset 个代码点后得到的
     */
    offsetByCodePoints(index: JInt, codePointOffset: JInt): JStringBuffer;

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
     * 设置字符序列的长度
     */
    setLength(newLength: JInt): void;

    /**
     * 返回一个新的字符序列，该字符序列是此序列的子序列
     */
    subSequence(start: JInt, end: JInt): JCharSequence;

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

    /**
     * 尝试减少用于字符序列的存储空间
     */
    trimToSize(s: JString): void;
}
