/**
 * 一个可变的字符序列
 */
interface JStringBuilder extends JObject {
    java_lang_StringBuilder: java.lang.StringBuilder;

    compareTo(another: JStringBuilder): JInt;

    length(): JInt;

    capacity(): JInt;

    charAt(index: JInt): JChar;

    setCharAt(index: JInt, ch: JChar): void;

    substring(start: JInt): JString;

    substring(start: JInt, end: JInt): JString;

    append(obj: any): JStringBuilder;

    append(s: JString, start: JInt, end: JInt): JStringBuilder;

    append(str: JChar[], offset: JInt, len: JInt): JStringBuilder;

    delete(start: JInt, end: JInt): JStringBuilder;

    deleteCharAt(index: JInt): JStringBuilder;

    replace(start: JInt, end: JInt, str: JString): JStringBuilder;

    insert(offset: JInt, obj: any): JStringBuilder;

    insert(dstOffset: JInt, s: JString, start: JInt, end: JInt): JStringBuilder;

    insert(index: JInt, str: JChar[], offset: JInt, len: JInt): JStringBuilder;

    indexOf(str: JString): JInt;

    indexOf(str: JString, fromIndex: JInt): JInt;

    lastIndexOf(str: JString): JInt;

    lastIndexOf(str: JString, fromIndex: JInt): JInt;

    reverse(): JStringBuilder;

    toString(): JString;
}
