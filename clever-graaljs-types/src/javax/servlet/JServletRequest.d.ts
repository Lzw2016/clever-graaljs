interface JServletRequest extends JObject {
    javax_servlet_ServletRequest: "javax.servlet.ServletRequest";

    /**
     * 返回具有给定名字的 servlet container 的属性,或者当没有具有所给名字的属性时，返回一个空值
     */
    getAttribute<T = any>(name: JString): T | null;

    /**
     * 从请求消息中删除一个属性
     */
    removeAttribute(name: JString): void;

    /**
     * 存储一个请求消息中的属性
     */
    setAttribute(name: JString, obj: any): void;

    /**
     * 返回一个包含servlet context中属性名字变量的集合
     */
    getAttributeNames(): JEnumeration<JString>;

    /**
     * 返回用在请求信息的body编码的字符的名称
     */
    getCharacterEncoding(): JString;

    /**
     * 返回请求体的用字节表示的长度，并被输入流改变为有效。如果长度未知，就返回-1
     */
    getContentLength(): JInt;

    /**
     * 返回请求体的 MIME类型，当长度未知是就返回一个空值
     */
    getContentType(): JString;

    /**
     * 用一个 ServletInputStream 重新得到二进制的请求消息体
     */
    getInputStream(): JInputStream;

    /**
     * 用一个 BufferedReader 重新得到字符串数据的请求消息体
     */
    getReader(): JReader;

    // /**
    //  * 返回 基于Accept-Language 的头部的客户端将接收内容的首选的场所
    //  */
    // getLocale(): JLocale;

    // /**
    //  * 返回一个地址对象的枚举变量，它以开始的首选地址的降序排列指明了基于 Accept-Language header 的客户端可接受的地点
    //  */
    // getLocales(): JEnumeration<JLocale>;

    /**
     * 以 protocol/majorVersion.minorVersion, 的格式返回请求所用协议的名称和版本。例如HTTP/1.1
     */
    getProtocol(): JString;

    /**
     * 返回一个布尔值以指明是否这个请求使用了一个安全信道，如HTTPS
     */
    isSecure(): JBoolean;

    // /**
    //  * 返回一个作为位于给定路径的资源资源的封装器的 RequestDispatcher 对象
    //  */
    // getRequestDispatcher(path: JString): JRequestDispatcher;

    /**
     * 返回用以作出请求消息的方案的名称，如 http, https, 或ftp等
     */
    getScheme(): JString;

    /**
     * 返回收到请求的服务器主机的名字
     */
    getServerName(): JString;

    /**
     * 返回收到请求的端口号
     */
    getServerPort(): JInt;

    /**
     * 返回一个请求参数的字符串值。若该参数不存在，则返回一个空值
     */
    getParameter(name: JString): JString;

    /**
     * 返回一个包含了请求中的参数名字的字符串对象的枚举变量
     */
    getParameterNames(): JEnumeration<JString>;

    /**
     * 返回一个包含所有的给定请求参数的值的字符串对象的向量。若该参数不存在，则返回一个空值
     */
    getParameterValues(name: JString): JString[];

    /**
     * 返回客户端发送请求的IP地址
     */
    getRemoteAddr(): JString;

    /**
     * 返回发送请求的客户端的完全合格的名称；或者如果客户端的名字没有确定则返回其IP地址
     */
    getRemoteHost(): JString;

    getRemotePort(): JInt;

    getLocalAddr(): JString;

    getLocalName(): JString;

    getLocalPort(): JInt;

    getServletContext(): JServletContext;
}