interface JIterable<T> extends JObject {
    java_util_Iterable: "java.util.Iterable";

    /**
     * 返回一个在一组 T 类型的元素上进行迭代的迭代器
     */
    iterator(): JIterator<T>;
}

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

interface JCollection<E> extends JObject {
    java_util_Collection: "java.util.Collection";

    /**
     * 确保此 collection 包含指定的元素（可选操作）
     */
    add(o: E): JBoolean;

    /**
     * 将指定 collection 中的所有元素都添加到此 collection 中（可选操作）
     */
    addAll<T extends E>(c: JCollection<T>): JBoolean;

    /**
     * 移除此 collection 中的所有元素（可选操作）
     */
    clear(): void;

    /**
     * 如果此 collection 包含指定的元素，则返回 true
     */
    contains(o: any): JBoolean;

    /**
     * 如果此 collection 包含指定 collection 中的所有元素，则返回 true
     */
    containsAll(o: JCollection<any>): JBoolean;

    /**
     * 如果此 collection 不包含元素，则返回 true
     */
    isEmpty(): JBoolean;

    /**
     * 返回在此 collection 的元素上进行迭代的迭代器
     */
    iterator(): JIterator<E>;

    /**
     * 从此 collection 中移除指定元素的单个实例，如果存在的话（可选操作）
     */
    remove(o: any): JBoolean;

    /**
     * 移除此 collection 中那些也包含在指定 collection 中的所有元素（可选操作）
     */
    removeAll(o: JCollection<any>): JBoolean;

    /**
     * 仅保留此 collection 中那些也包含在指定 collection 的元素（可选操作）
     */
    retainAll(o: JCollection<any>): JBoolean;

    /**
     * 返回此 collection 中的元素数
     */
    size(o: E): JInt;

    /**
     * 返回包含此 collection 中所有元素的数组
     */
    toArray(): E[];
}