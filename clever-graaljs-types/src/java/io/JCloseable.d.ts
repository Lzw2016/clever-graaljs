interface JCloseable extends JObject {
    java_io_Closeable: "java.io.Closeable";

    /**
     * 关闭此流并释放与此流关联的所有系统资源
     */
    close(): void;
}