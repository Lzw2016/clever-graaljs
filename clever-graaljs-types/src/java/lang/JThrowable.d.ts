interface JThrowable extends JObject {
    java_lang_Throwable: "java.lang.Throwable";

    /**
     * 返回此 throwable 的 cause；如果 cause 不存在或未知，则返回 null
     */
    getCause(): JThrowable;

    /**
     * 返回此 throwable 的详细消息字符串
     */
    getMessage(): JString;

    /**
     * 将此 throwable 及其追踪输出至标准错误流
     */
    printStackTrace(): void;

    /**
     * 返回此 throwable 的简短描述
     */
    toString(): JString;
}
