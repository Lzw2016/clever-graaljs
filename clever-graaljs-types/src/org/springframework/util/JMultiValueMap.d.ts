interface JMultiValueMap<K, V> extends JMap<K, JList<V>> {
    /**
     * 返回给定键的第一个值
     */
    getFirst(key: K): V | null;

    /**
     * 将给定的单个值添加到给定键的当前值列表中
     */
    add(key: K, value: V): void;

    /**
     * 将给定列表的所有值添加到给定键的当前值列表中
     */
    addAll(key: K, values: JList<V>): void;


    /**
     * 将给定MultiValueMap的所有值添加到当前值
     */
    addAll(values: JMultiValueMap<K, V>): void;

    /**
     * 在给定的键下设置给定的单个值
     */
    set(key: K, value: V): void;

    /**
     * 设置给定值
     */
    setAll(values: JMap<K, V>): void;

    /**
     * 返回包含此MultiValueMap中的第一个值的Map
     */
    toSingleValueMap(): JMap<K, V>;
}
