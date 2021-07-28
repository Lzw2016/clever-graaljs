interface JHttpSession extends JObject {
    javax_servlet_http_HttpSession: "javax.servlet.http.HttpSession";

    /**
     * 返回一个包含所有的绑定到会话的 对象名称的字符串对象的集合
     */
    getAttributeNames(): JEnumeration<JString>;

    /**
     * 返回在本会话中绑定了指定名字的对象，当没有所要求的对象时，返回一个空值
     */
    getAttribute<T = any>(name: JString): T | null;

    /**
     * 用指定的名字将一个对象绑定到一个会话
     */
    setAttribute(name: JString, obj: any): void;

    /**
     * 从会话中删除绑定到指定名字的对象
     */
    removeAttribute(name: JString): void;

    /**
     * 返回会话建立的时间，在格林威治时间1970年1月1日子夜开始计算，单位是milliseconds
     */
    getCreationTime(): JLong;

    /**
     * 返回一个包含分配给会话的唯一的标示符的字符串
     */
    getId(): JString;

    /**
     * 返回客户端发出的关于本会话的请求消息的最后一次的时间。单位是毫秒，自格林威治时间1970年1月1日午夜开始计时
     */
    getLastAccessedTime(): JLong;

    /**
     * 返回servlet容器将在两个客户端访问之间保持会话开放的间隔的最大时间，以秒计时
     */
    getMaxInactiveInterval(): JInt;

    /**
     * 指定在客户端请求消息之间servlet容器将该会话设为无效之前的以秒计的时间
     */
    setMaxInactiveInterval(interval: JInt): void;

    /**
     * 使该会话无效，然后取消绑定到该会话的任何对象
     */
    invalidate(): void;

    /**
     * 如果客户端尚不知道会话或客户端选择不加入会话，则返回true。例如，如果服务器仅使用基于cookie的会话，而客户端禁用了cookie的使用，则在每个请求上会话都是新的<br />
     * 如果服务器创建了会话，但客户端尚未加入，则为true
     */
    isNew(): JBoolean;

    /**
     * 获取 ServletContext
     */
    getServletContext(): JServletContext;
}