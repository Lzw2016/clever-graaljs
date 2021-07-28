interface JOutputStreamWriter extends JWriter {
    java_io_OutputStreamWriter: "java.io.OutputStreamWriter";

    /**
     * 返回此流使用的字符编码的名称
     */
    getEncoding(): JString;
}
