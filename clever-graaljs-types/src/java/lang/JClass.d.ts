interface JClass extends JObject {
    java_lang_Class: "java.lang.Class";

    /**
     * 返回由Java语言规范定义的基础类的规范名称
     */
    getCanonicalName(): JString;

    /**
     * 返回由类对象表示的实体（类，接口，数组类，原始类型或空白）的名称
     */
    getName(): JString;

    /**
     * 返回源代码中给出的基础类的简单名称
     */
    getSimpleName(): JString;

    /**
     * 为此类型的名称返回一个内容丰富的字符串
     */
    getTypeName(): JString;

    /**
     * 如果此 类对象表示注释类型，则返回true
     */
    isAnnotation(): JBoolean;

    /**
     * 类对象是否表示数组类
     */
    isArray(): JBoolean;

    /**
     * 当且仅当该类在源代码中被声明为枚举时才返回true
     */
    isEnum(): JBoolean;

    /**
     * 确定指定的Object是否与此Object表示的对象分配类
     */
    isInstance(obj: any): JBoolean;

    /**
     * 类对象表示接口类型
     */
    isInterface(): JBoolean;

    /**
     * 返回true当且仅当基础类是本地类时
     */
    isLocalClass(): JBoolean;

    /**
     * 返回true当且仅当基础类是成员类时
     */
    isMemberClass(): JBoolean;

    /**
     * 确定类对象表示一个基本类型
     */
    isPrimitive(): JBoolean;

    /**
     * 如果这个类是一个合成类，返回true
     */
    isSynthetic(): JBoolean;
}
