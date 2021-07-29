interface JIterable<T> extends JObject {
    java_util_Iterable: "java.util.Iterable";

    /**
     * 返回一个在一组 T 类型的元素上进行迭代的迭代器
     */
    iterator(): JIterator<T>;
}
