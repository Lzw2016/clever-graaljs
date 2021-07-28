interface JMapEntry<K, V> extends JObject {
    java_util_Map_Entry: "java.util.Map.Entry";

    /**
     * 返回与此项对应的键
     */
    getKey(): K;

    /**
     * 返回与此项对应的值
     */
    getValue(): V;

    /**
     * 用指定的值替换与此项对应的值
     */
    setValue(value: V): V;
}

/**
 * java.util.Map 类型，不要在JavaScript/TypeScript环境中创建该类型对象<br />
 * @see Interop
 */
interface JMap<K, V> extends JObject {
    java_util_Map: "java.util.Map";

    // [key: K]: V;

    /**
     * 从此映射中移除所有映射关系
     */
    clear(): void;

    /**
     * 如果此映射包含指定键的映射关系，则返回 true
     */
    containsKey(key: any): JBoolean;

    /**
     * 如果此映射为指定值映射一个或多个键，则返回 true
     */
    containsValue(value: any): JBoolean;

    /**
     * 返回此映射中包含的映射关系的 set 视图
     */
    entrySet(): JSet<JMapEntry<K, V>>;

    /**
     * 返回此映射中映射到指定键的值
     */
    get(key: any): V | null;

    /**
     * 如果此映射未包含键-值映射关系，则返回 true
     */
    isEmpty(): JBoolean;

    /**
     * 返回此映射中包含的键的 set 视图
     */
    keySet(): JSet<K>;

    /**
     * 将指定的值与此映射中的指定键相关联（可选操作）
     */
    put(key: K, value: V): V;

    /**
     * 从指定映射中将所有映射关系复制到此映射中（可选操作）
     */
    putAll<TK extends K, TV extends V>(t: JMap<TK, TV>): void;

    /**
     * 如果存在此键的映射关系，则将其从映射中移除（可选操作）
     */
    remove(key: any): V;

    /**
     * 返回此映射中的键-值映射关系数
     */
    size(): JInt;

    /**
     * 返回此映射中包含的值的 collection 视图
     */
    values(): JCollection<V>;
}