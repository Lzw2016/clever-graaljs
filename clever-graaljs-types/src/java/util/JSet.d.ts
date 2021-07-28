/**
 * java.util.Set 类型，不要在JavaScript/TypeScript环境中创建该类型对象<br />
 * @see Interop
 */
interface JSet<E> extends JCollection<E> {
    java_util_Set: "java.util.Set";

    /**
     * 如果 set 中尚未存在指定的元素，则添加此元素（可选操作）
     */
    add(o: E): JBoolean;

    /**
     * 如果 set 中没有指定 collection 中的所有元素，则将其添加到此 set 中（可选操作）
     */
    addAll<T extends E>(c: JCollection<T>): JBoolean;

    /**
     * 移除 set 中的所有元素（可选操作）
     */
    clear(): void;

    /**
     * 如果 set 包含指定的元素，则返回 true
     */
    contains(o: any): JBoolean;

    /**
     * 如果此 set 包含指定 collection 的所有元素，则返回 true
     */
    containsAll<T extends E>(c: JCollection<T>): JBoolean;

    /**
     * 如果 set 不包含元素，则返回 true
     */
    isEmpty(): JBoolean;

    /**
     * 返回在此 set 中的元素上进行迭代的迭代器
     */
    iterator(): JIterator<E>;

    /**
     * 如果 set 中存在指定的元素，则将其移除（可选操作）
     */
    remove(o: any): JBoolean;

    /**
     * 移除 set 中那些包含在指定 collection 中的元素（可选操作）
     */
    removeAll<T extends E>(c: JCollection<T>): JBoolean;

    /**
     * 仅保留 set 中那些包含在指定 collection 中的元素（可选操作）
     */
    retainAll<T extends E>(c: JCollection<T>): JBoolean;

    /**
     * 返回 set 中的元素数（其容量）
     */
    size(): JInt;

    /**
     * 返回一个包含 set 中所有元素的数组；返回数组的运行时类型是指定数组的类型
     */
    toArray(): E[];
}