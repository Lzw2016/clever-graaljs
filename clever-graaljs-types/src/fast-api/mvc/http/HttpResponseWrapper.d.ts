interface HttpResponseWrapper extends JObject {
    /**
     * 原始HTTP响应对象
     */
    originalResponse(): JHttpServletResponse;

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
    setContentType(type: JString | MediaType): void;

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

    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 将指定的cookie加入到响应中
     */
    addCookie(cookie: JCookie): void

    /**
     * 给定的名字和和数值加到响应的头部
     */
    addHeader(name: JString, value: JString): void;

    /**
     * 把给定的名字和日期值加入到响应的头部
     */
    addDateHeader(name: JString, date: JLong): void;

    /**
     * 将给定的名字和整数值加到一个相应的头部
     */
    addIntHeader(name: JString, value: JInt): void;

    /**
     * 设置带有给定的名字和数值的响应消息头
     */
    setHeader(name: JString, value: JString): void;

    /**
     * 设置带有给定的名字和数值的响应消息头
     */
    setDateHeader(name: JString, date: JLong): void;

    /**
     * 设置带有给定的名字和整数值的响应消息头
     */
    setIntHeader(name: JString, value: JInt): void;

    /**
     * 获取响应消息头信息
     */
    getHeader(name: JString): JString;

    /**
     * 获取响应消息头信息
     */
    getHeaders(name: JString): JCollection<JString>;

    /**
     * 获取所有响应消息头名称
     */
    getHeaderNames(): JCollection<JString>;

    /**
     * 返回一个布尔值以反映是否指定的响应消息头部已被设置过了
     */
    containsHeader(name: JString): JBoolean;

    /**
     * 为在 sendRedirect 方法中使用的指定的URL进行编码。或者，如果编码是没有必要的，就返回没有改变的URL
     */
    encodeRedirectURL(url: JString): JString;

    /**
     * 通过调用URL中的会话ID对指定的URL进行编码，或者如果编码时不必要的就返回“URL不变”
     */
    encodeURL(url: JString): JString;

    /**
     * 用指定的重定位URL向客户端发送一个临时重定位响应消息
     */
    sendRedirect(location: JString): void;

    /**
     * 设置响应消息的状态码
     */
    setStatus(sc: JInt): JString;

    /**
     * 获取响应状态码
     */
    getStatus(): JInt;

    /**
     * 用指定的状态向客户端发送一个错误响应
     */
    sendError(sc: JInt): void;

    /**
     * 用指定的状态码和描述消息向客户端发送一个错误响应
     */
    sendError(sc: JInt, msg: JString): void;

    //---------------------------------------------------------------------------------------------------------------------------------------------- 高阶封装

    /**
     * 设置当前响应是下载文件
     *
     * @param fileName      文件名称
     * @param contentLength 文件内容大小
     */
    setDownloadFileName(fileName: JString, contentLength: JLong): void;

    /**
     * 设置当前响应是下载文件
     *
     * @param fileName      文件名称
     */
    setDownloadFileName(fileName: JString): void;
}
