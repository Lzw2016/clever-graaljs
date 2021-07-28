/**
 * java.util.List 类型
 */
interface JList<E> extends JCollection<E> {
    java_util_List: "java.util.List";

    // [index: number]: E;

    /**
     * 向列表的尾部追加指定的元素（可选操作）
     */
    add(o: E): JBoolean;

    /**
     * 在列表的指定位置插入指定元素（可选操作）
     */
    add(index: JInt, element: E): void;

    /**
     * 追加指定 collection 中的所有元素到此列表的结尾，顺序是指定 collection 的迭代器返回这些元素的顺序（可选操作）
     */
    addAll<T extends E>(c: JCollection<T>): JBoolean;

    /**
     * 将指定 collection 中的所有元素都插入到列表中的指定位置（可选操作）
     */
    addAll<T extends E>(index: JInt, c: JCollection<T>): JBoolean;

    /**
     * 从列表中移除所有元素（可选操作）
     */
    clear(): void;

    /**
     * 如果列表包含指定的元素，则返回 true
     */
    contains(o: any): JBoolean;

    /**
     * 如果列表包含指定 collection 的所有元素，则返回 true
     */
    containsAll(c: JCollection<any>): JBoolean;

    /**
     * 返回列表中指定位置的元素
     */
    get(index: JInt): E;

    /**
     * 返回列表中首次出现指定元素的索引，如果列表不包含此元素，则返回 -1
     */
    indexOf(o: any): JInt;

    /**
     * 如果列表不包含元素，则返回 true
     */
    isEmpty(): JBoolean;

    /**
     * 返回以正确顺序在列表的元素上进行迭代的迭代器
     */
    iterator(): JIterator<E>;

    /**
     * 返回列表中最后出现指定元素的索引，如果列表不包含此元素，则返回 -1
     */
    lastIndexOf(o: any): JInt;

    /**
     * 返回列表中元素的列表迭代器（以正确的顺序）
     */
    listIterator(): JIterator<E>;

    /**
     * 返回列表中元素的列表迭代器（以正确的顺序），从列表的指定位置开始
     */
    listIterator(index: JInt): JIterator<E>;

    /**
     * 移除列表中指定位置的元素（可选操作）
     */
    remove(index: JInt): E;

    /**
     * 移除列表中出现的首个指定元素（可选操作）
     */
    remove(o: any): JBoolean;

    /**
     * 从列表中移除指定 collection 中包含的所有元素（可选操作）
     */
    removeAll(c: JCollection<any>): JBoolean;

    /**
     * 仅在列表中保留指定 collection 中所包含的元素（可选操作）
     */
    retainAll(c: JCollection<any>): JBoolean;

    /**
     * 用指定元素替换列表中指定位置的元素（可选操作）
     */
    set(index: JInt, element: E): E;

    /**
     * 返回列表中的元素数
     */
    size(): JInt;

    /**
     * 返回列表中指定的 fromIndex（包括 ）和 toIndex（不包括）之间的部分视图
     */
    subList(fromIndex: JInt, toIndex: JInt): JList<E>;

    /**
     * 返回以正确顺序包含列表中的所有元素的数组
     */
    toArray(): E[];
}