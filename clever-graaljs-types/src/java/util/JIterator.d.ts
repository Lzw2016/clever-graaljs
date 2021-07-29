interface JIterator<E> extends JObject {
    java_util_Iterator: "java.util.Iterator";

    /**
     * 如果仍有元素可以迭代，则返回 true
     */
    hasNext(): JBoolean;

    /**
     * 返回迭代的下一个元素
     */
    next(): E;

    /**
     * 从迭代器指向的集合中移除迭代器返回的最后一个元素（可选操作）
     */
    remove(): void;
}