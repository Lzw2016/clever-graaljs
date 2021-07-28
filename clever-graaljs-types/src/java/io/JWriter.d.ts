interface JWriter extends JCloseable {
    java_io_Writer: "java.io.Writer";

    /**
     * 将指定字符追加到此 writer
     */
    append(c: JChar): JWriter;

    /**
     * 将指定字符序列追加到此 writer
     * @param csq
     */
    append(csq: JString): JWriter;

    /**
     * 将指定字符序列的子序列追加到此 writer.Appendable
     */
    append(csq: JString, start: JInt, end: JInt): JWriter;

    /**
     * 刷新此流
     */
    flush(): void;

    /**
     * 写入字符数组
     */
    write(cbuf: JChar[]): void;

    /**
     * 写入字符数组的某一部分
     */
    write(cbuf: JChar[], off: JInt, len: JInt): void;

    /**
     * 写入单个字符
     */
    write(c: JInt): void;

    /**
     * 写入字符串
     */
    write(str: JString): void;

    /**
     * 写入字符串的某一部分
     */
    write(str: JString, off: JInt, len: JInt): void;
}

interface JOutputStreamWriter extends JWriter {
    java_io_OutputStreamWriter: "java.io.OutputStreamWriter";

    /**
     * 返回此流使用的字符编码的名称
     */
    getEncoding(): JString;
}

interface JPrintWriter extends JWriter {
    java_io_PrintWriter: "java.io.PrintWriter";

    /**
     * 将指定字符追加到此 writer
     */
    append(c: JChar): JPrintWriter;

    /**
     * 将指定字符序列追加到此 writer
     * @param csq
     */
    append(csq: JString): JPrintWriter;

    /**
     * 将指定字符序列的子序列追加到此 writer.Appendable
     */
    append(csq: JString, start: JInt, end: JInt): JPrintWriter;

    /**
     * 如果流没有关闭，则刷新流且检查其错误状态
     */
    checkError(): JBoolean;

    /**
     * 使用指定格式字符串和参数将一个格式化字符串写入此 writer 中
     */
    format(format: JString, ...args: JObject[]): JPrintWriter;

    /**
     * 打印变量值
     */
    print(obj: any): void;

    /**
     * 使用指定格式字符串和参数将格式化的字符串写入此 writer 的便捷方法
     */
    printf(format: JString, ...args: Object[]): JPrintWriter;

    /**
     * 通过写入行分隔符字符串终止当前行
     */
    println(): void;

    /**
     * 打印变量值，然后终止该行
     */
    println(obj: any): void;
}