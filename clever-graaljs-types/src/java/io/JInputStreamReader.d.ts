interface JInputStreamReader extends JReader {
    java_io_InputStreamReader: "java.io.InputStreamReader";

    /**
     * 返回此流使用的字符编码的名称
     */
    getEncoding(): JString;
}
