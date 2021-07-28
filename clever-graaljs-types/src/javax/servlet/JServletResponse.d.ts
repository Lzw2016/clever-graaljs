interface JServletResponse extends JObject {
    javax_servlet_ServletResponse: "javax.servlet.ServletResponse";

    /**
     * 强制将buffer中的任何内容写入客户端
     */
    flushBuffer(): void;

    /**
     * 清除所有存在于buffer和状态码以及头的数据
     */
    reset(): void;

    /**
     * 返回实际用于响应消息的buffer的大小
     */
    getBufferSize(): JInt;

    /**
     * 为响应消息体设置合适的 buffer的大小
     */
    setBufferSize(size: JInt): void;

    /**
     * 返回用于在这个响应消息中被发送的 MIME body的charset的名称
     */
    getCharacterEncoding(): JString;

    /**
     * 设置响应编码
     */
    setCharacterEncoding(charset: JString): void;

    /**
     * 返回一个适用于在响应消息中写入二进制数据的 ServletOutputStream
     */
    getOutputStream(): JOutputStream;

    /**
     * 返回能够向一个客户机发送字符的 PrintWriter 对象
     */
    getWriter(): JPrintWriter;

    /**
     * 返回一个布尔值，以指明是否响应消息已被执行
     */
    isCommitted(): JBoolean;

    /**
     * 设置响应中的内容长度。在HTTP servlet 中，这个方法就是设置HTTP Content-Length header
     */
    setContentLength(len: JInt): void;

    /**
     * 设置响应中的内容长度。在HTTP servlet 中，这个方法就是设置HTTP Content-Length header
     */
    setContentLengthLong(len: JLong): void;

    /**
     * 设置正被发往客户端的响应的内容类型
     */
    setContentType(type: JString): void;

    /**
     * 获取响应的内容类型
     */
    getContentType(): JString;

    // /**
    //  * 返回分配给响应消息的场所
    //  */
    // getLocale(): Locale;

    // /**
    //  * 设置响应消息的地址。适当地设置消息的头（包括 Content-Type的字符设置）
    //  */
    // setLocale(loc: Locale): void;
}