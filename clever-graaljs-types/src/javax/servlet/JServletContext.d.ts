interface JServletContext extends JObject {
    javax_servlet_ServletContext: "javax.servlet.ServletContext";

    /**
     * 返回一个于服务器上指定的URL进行通信的 ServletContext 对象
     */
    getContext(uripath: JString): JServletContext;

    /**
     * Web应用程序的上下文路径，或者为根上下文“”
     */
    getContextPath(): JString;

    /**
     * 返回与此ServletContext相对应的此Web应用程序的名称，该名称由display-name元素为此Web应用程序的部署描述符中指定。
     */
    getServletContextName(): JString;

    /**
     * 返回 servlet 容器支持的最低的 Java Servlet API版本号
     */
    getMinorVersion(): JInt;

    /**
     * 返回 servlet 容器支持的较高的 Java Servlet API版本号
     */
    getMajorVersion(): JInt;

    /**
     * 返回servlet运行的servlet 容器的版本和名称
     */
    getServerInfo(): JString;

    // /**
    //  * 返回一个作为给定名字的servlet的封装器的 RequestDispatcher对象
    //  */
    // getNamedDispatcher(name: JString): RequestDispatcher;

    // /**
    //  * 返回一个作为位于给定路径的资源资源的封装器的 RequestDispatcher 对象
    //  */
    // getRequestDispatcher(path: JString): RequestDispatcher;

    // /**
    //  * 返回一个到指明路径的资源的URL
    //  */
    // getResource(path: JString): URL;

    /**
     * 返回包含给定的虚拟路径的真实路径的字符串
     */
    getRealPath(path: JString): JString;

    /**
     * 返回位于指定路径的InputStream 对象资源
     */
    getResourceAsStream(path: JString): JInputStream;

    /**
     * 将指定的消息写到一个servlet日志文件，通常是一个事件日志文件
     */
    log(msg: JString): void;

    /**
     * 将一个解释性消息和一个给定的Throwable异常跟踪消息写入到servlet 日志文件
     */
    log(message: JString, throwable: JThrowable): void;

    /**
     * 返回一个包含servlet context中属性名字变量的集合
     */
    getAttributeNames(): JEnumeration<JString>;

    /**
     * 返回具有给动名字的servlet container 的属性,或者当没有具有所给名字的属性时，返回一个空值
     */
    getAttribute<T = any>(name: JString): T | null;

    /**
     * 绑定一个对象到在 servlet context中给定的属性名称
     */
    setAttribute(name: JString, obj: any): void;

    /**
     * 从servlet context中删除带有给定名字的属性
     */
    removeAttribute(name: JString): JString;

    /**
     * 返回指定文件的MIME类型，若未知，则返回空值
     */
    getMimeType(file: JString): JString;

    /**
     * 返回一个包含给定名字的context-wide 初始化参数，若此参数不存在就返回一个空值
     */
    getInitParameter(name: JString): JString | null;

    /**
     * 返回context的初始化参数的名字 ，用一个字符串对象枚举变量的形式。如果相应的context没有初始化参数，则就返回一个空的枚举变量
     */
    getInitParameterNames(): JEnumeration<JString>;

    /**
     * 获取此ServletContext默认支持的会话超时（以分钟为单位）
     */
    getSessionTimeout(): JInt;
}