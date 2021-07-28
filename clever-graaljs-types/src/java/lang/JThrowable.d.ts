interface JThrowable extends JObject {
    /**
     * 在异常堆栈跟踪中填充
     */
    fillInStackTrace(): JThrowable;

    /**
     * 返回此 throwable 的 cause；如果 cause 不存在或未知，则返回 null
     */
    getCause(): JThrowable;

    /**
     * 创建此 throwable 的本地化描述
     */
    getLocalizedMessage(): JString;

    /**
     * 返回此 throwable 的详细消息字符串
     */
    getMessage(): JString;

    // /**
    //  * 提供编程访问由 printStackTrace() 输出的堆栈跟踪信息
    //  */
    // getStackTrace(): JStackTraceElement[];

    /**
     * 将此 throwable 的 cause 初始化为指定值
     */
    initCause(cause: JThrowable): JThrowable;

    /**
     * 将此 throwable 及其追踪输出至标准错误流
     */
    printStackTrace(): void;

    // /**
    //  * 将此 throwable 及其追踪输出到指定的输出流
    //  */
    // printStackTrace(s: JPrintStream): void;

    /**
     * 将此 throwable 及其追踪输出到指定的 PrintWriter
     */
    printStackTrace(s: JPrintWriter): void;

    // /**
    //  * 设置将由 getStackTrace() 返回，并由 printStackTrace() 和相关方法输出的堆栈跟踪元素
    //  */
    // setStackTrace(stackTrace: JStackTraceElement[]): void;

    /**
     * 返回此 throwable 的简短描述
     */
    toString(): JString;
}
