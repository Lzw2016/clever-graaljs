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
    write(cBuf: JChar[]): void;

    /**
     * 写入字符数组的某一部分
     */
    write(cBuf: JChar[], off: JInt, len: JInt): void;

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
