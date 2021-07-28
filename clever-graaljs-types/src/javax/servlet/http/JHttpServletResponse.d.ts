interface JHttpServletResponse extends JServletResponse {
    javax_servlet_http_HttpServletResponse: "javax.servlet.http.HttpServletResponse";

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
    getStatus():JInt;

    /**
     * 用指定的状态向客户端发送一个错误响应
     */
    sendError(sc: JInt): void;

    /**
     * 用指定的状态码和描述消息向客户端发送一个错误响应
     */
    sendError(sc: JInt, msg: JString): void;
}