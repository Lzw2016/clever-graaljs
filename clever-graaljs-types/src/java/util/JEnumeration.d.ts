interface JEnumeration<E> extends JObject {
    java_util_Enumeration: "java.util.Enumeration";

    /**
     * 测试此枚举是否包含更多的元素
     */
    hasMoreElements(): JBoolean;

    /**
     * 如果此枚举对象至少还有一个可提供的元素，则返回此枚举的下一个元素
     */
    nextElement(): E;
}