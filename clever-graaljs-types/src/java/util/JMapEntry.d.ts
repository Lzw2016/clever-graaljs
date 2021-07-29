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
