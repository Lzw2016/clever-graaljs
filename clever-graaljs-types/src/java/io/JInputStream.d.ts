interface JInputStream extends JCloseable {
    java_io_InputStream: "java.io.InputStream";

    /**
     * 返回此输入流方法的下一个调用方可以不受阻塞地从此输入流读取（或跳过）的字节数
     */
    available(): JInt;

    /**
     * 在此输入流中标记当前的位置
     */
    mark(readlimit: JInt): void;

    /**
     * 测试此输入流是否支持 mark 和 reset 方法
     */
    markSupported(): JBoolean;

    /**
     * 从输入流读取下一个数据字节
     */
    read(): JInt;

    /**
     * 从输入流中读取一定数量的字节并将其存储在缓冲区数组 b 中
     */
    read(b: JByte[]): JInt;

    /**
     * 将输入流中最多 len 个数据字节读入字节数组
     */
    read(b: JByte[], off: JInt, len: JInt): JInt;

    /**
     * 将此流重新定位到对此输入流最后调用 mark 方法时的位置
     */
    reset(): void;

    /**
     * 跳过和放弃此输入流中的 n 个数据字节
     */
    skip(n: JLong): JLong;
}

interface JFileInputStream extends JInputStream {
    java_io_FileInputStream: "java.io.FileInputStream";
}

interface JByteArrayInputStream extends JInputStream {
    java_io_ByteArrayInputStream: "java.io.ByteArrayInputStream";
}