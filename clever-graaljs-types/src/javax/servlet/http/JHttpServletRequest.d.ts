interface JHttpServletRequest extends JServletRequest {
    javax_servlet_http_HttpServletRequest: "javax.servlet.http.HttpServletRequest";

    /**
     * 返回用于保护servlet的认证方案的名字，例如， "BASIC" 或 "SSL,"。如果servlet没有被保护则返回一个空值
     */
    getAuthType(): JString | null;

    /**
     * 如果用户已被授权认证，则返回用户作出请求的登录情况；否则就返回一个空值
     */
    getRemoteUser(): JString;

    /**
     * 返回由客户机指定的会话ID
     */
    getRequestedSessionId(): JString;

    /**
     * 返回关于该请求的当前会话。或者若该请求没有会话则就创建一个
     */
    getSession(): JHttpSession;

    /**
     * 返回有关本请求的当前HttpSession，或者若该请求没有会话，且“创建”属性为真，则就创建一个
     */
    getSession(create: JBoolean): JHttpSession;

    /**
     * 检查是否要求的会话ID已作为cookie来到
     */
    isRequestedSessionIdFromCookie(): JBoolean;

    /**
     * 检查是否要求的会话ID已作为请求URL的一部分到达
     */
    isRequestedSessionIdFromURL(): JBoolean;

    /**
     * 检查是否要求的会话ID仍有效
     */
    isRequestedSessionIdValid(): JBoolean;

    // /**
    //  * 返回一个包含了当前被授权的用户名字的 java.security.Principal 对象
    //  */
    // getUserPrincipal(): Principal;

    // /**
    //  * 返回一个布尔值以指明是否被授权的用户已被包含在指定的逻辑"role"中
    //  */
    // isUserInRole(role: JString): JBoolean;

    /**
     * 返回指明请求context的请求URL的部分
     */
    getContextPath(): JString;

    /**
     * 返回调用servlet的请求的URL部分<br />
     * 请求url path，如：/api/demo_1
     */
    getServletPath(): JString;

    /**
     * 返回用以作出请求的HTTP方法的名称，例如 GET, POST,或 PUT等等
     */
    getMethod(): JString;

    /**
     * 返回该请求消息的URL中HTTP协议第一行里从协议名称到请求字符串的部分<br />
     * 请求url path，如：/api/demo_1
     */
    getRequestURI(): JString;

    /**
     * 请求url(protocol、host、path)，如：http://demo.msvc.top:18081/api/demo_1
     */
    getRequestURL(): JStringBuffer;

    /**
     * 返回有关当客户端作出请求时的URL的任何额外的路径信息
     */
    getPathInfo(): JString;

    /**
     * 返回在servlet名字之后查询字符串之前的任何额外的路径信息，并将其转换成一个真正的路径
     */
    getPathTranslated(): JString;

    /**
     * 返回包含在请求URL中路径之后的查询字符串<br />
     * 查询字符串，如：a=123a&b=456b
     */
    getQueryString(): JString;

    /**
     * 返回所有的本请求消息包含的头名字的集合
     */
    getHeaderNames(): JEnumeration<JString>;

    /**
     * 返回指定的作为字符串请求消息头的值
     */
    getHeader(name: JString): JString;

    /**
     * 以一个字符串对象集合的形式，返回包含指定请求消息头的所有值
     */
    getHeaders(name: JString): JEnumeration<JString>;

    /**
     * 返回一个指定的请求消息头的整数值
     */
    getIntHeader(name: JString): JInt;

    /**
     * 返回指定的请求消息头的值，返回值格式是描述日期对象的长型数值
     */
    getDateHeader(name: JString): JLong;

    /**
     * 返回包含所有的客户端随请求信息发送来的cookie对象的一个 array
     */
    getCookies(): JCookie[];
}