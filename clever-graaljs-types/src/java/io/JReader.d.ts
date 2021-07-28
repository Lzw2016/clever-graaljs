interface JReader extends JCloseable {
    java_io_Reader: "java.io.Reader";

    /**
     * 标记流中的当前位置
     */
    mark(readAheadLimit: JInt): void;

    /**
     * 测试此输入流是否支持 mark 和 reset 方法
     */
    markSupported(): JBoolean;

    /**
     * 读取单个字符
     */
    read(): JInt;

    /**
     * 将字符读入数组
     */
    read(cbuf: JChar[]): JInt;

    /**
     * 将字符读入数组的某一部分
     */
    read(cbuf: JChar[], off: JInt, len: JInt): JInt;

    /**
     * 判断是否准备读取此流
     */
    ready(): JBoolean;

    /**
     * 重置该流
     */
    reset(): void;

    /**
     * 跳过字符
     */
    skip(n: JLong): JLong;
}

interface JInputStreamReader extends JReader {
    java_io_InputStreamReader: "java.io.InputStreamReader";

    /**
     * 返回此流使用的字符编码的名称
     */
    getEncoding(): JString;
}