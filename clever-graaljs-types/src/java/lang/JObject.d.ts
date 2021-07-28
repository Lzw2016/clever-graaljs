interface JObject {
    java_lang_Object: "java.lang.Object";

    /**
     * 判断对象值是否相同
     */
    equals(obj: any): JBoolean;

    /**
     * 返回对象的哈希码值
     */
    hashCode(): JInt;

    /**
     * 返回对象的字符串表示形式
     */
    toString(): JString;

    /**
     * 获取对象的 Class
     */
    getClass(): JClass;
}
