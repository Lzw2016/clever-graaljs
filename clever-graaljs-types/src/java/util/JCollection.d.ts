interface JCollection<E> extends JIterable<E> {
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