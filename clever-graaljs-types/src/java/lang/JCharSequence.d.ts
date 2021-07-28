interface JCharSequence extends JObject {
    java_lang_Writer: "java.lang.CharSequence";

    /**
     * 返回指定索引的 char 值
     */
    charAt(index: JInt): JChar;

    /**
     * 返回此字符序列的长度
     */
    length(): JInt;

    /**
     * 返回一个新的 CharSequence，它是此序列的子序列
     */
    subSequence(start: JInt, end: JInt): JCharSequence;
}
