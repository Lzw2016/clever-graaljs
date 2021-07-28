interface JComparable<T> {
    java_lang_Comparable: "java.lang.Comparable"

    /**
     * 将此对象与指定的对象进行比较以进行排序 <br>
     * this 早于 参数o 返回-1  <br>
     * this 晚于 参数o 返回 1  <br>
     * 时间相等返回0  <br>
     */
    compareTo(o: T): JInt;
}
