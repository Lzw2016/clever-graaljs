interface JOutputStream extends JCloseable {
    java_io_OutputStream: "java.io.OutputStream";

    /**
     * 刷新此输出流并强制写出所有缓冲的输出字节
     */
    flush(): void;

    /**
     * 将 b.length 个字节从指定的字节数组写入此输出流
     */
    write(b: JByte[]): void;

    /**
     * 将指定字节数组中从偏移量 off 开始的 len 个字节写入此输出流
     */
    write(b: JByte[], off: JInt, len: JInt): void;

    /**
     * 将指定的字节写入此输出流
     */
    write(b: JInt): void;
}
