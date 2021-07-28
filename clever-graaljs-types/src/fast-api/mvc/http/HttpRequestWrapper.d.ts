interface UploadFile {
    /**
     * 参数的名称
     */
    getName(): JString;

    /**
     * 原始文件名，如果在多部分形式中未选择文件，则为空字符串；如果未定义或不可用，则为空字符串
     */
    getOriginalFilename(): JString;

    /**
     * 内容类型，如果未定义，则为空（或在多部分形式中未选择文件）
     */
    getContentType(): JString;

    /**
     * 返回上传的文件是否为空，即没有在多部分表单中选择文件或所选文件没有内容
     */
    isEmpty(): JBoolean;

    /**
     * 文件的大小，如果为空，则为0
     */
    getSize(): JLong;

    /**
     * 文件的内容为字节，如果为空，则为空字节数组
     */
    getBytes(): JByte[];

    /**
     * 文件的内容为流，如果为空，则为空流
     */
    getInputStream(): JInputStream;

    /**
     * 将接收到的文件传输到给定的目标文件
     */
    transferTo(filePath: JString): void;
}

interface HttpRequestWrapper extends JObject {
    /**
     * 原始HTTP请求对象
     */
    originalRequest(): JHttpServletRequest;

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
    getAttributeNames(): JList<JString>;

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
    // getLocales(): JList<JLocale>;

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
    getParameterNames(): JList<JString>;

    /**
     * 返回一个包含所有的给定请求参数的值的字符串对象的向量。若该参数不存在，则返回一个空值
     */
    getParameterValues(name: JString): JString[];

    /**
     * 获取所有请求参数
     */
    getParameterMap(): JMap<JString, JString[]>;

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

    getServletContext(): ServletContextWrapper;

    //----------------------------------------------------------------------------------------------------------------------------------------------

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
    getSession(): HttpSessionWrapper;

    /**
     * 返回有关本请求的当前HttpSession，或者若该请求没有会话，且“创建”属性为真，则就创建一个
     */
    getSession(create: JBoolean): HttpSessionWrapper;

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
    getRequestURL(): JString;

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
    getHeaderNames(): JList<JString>;

    /**
     * 返回指定的作为字符串请求消息头的值
     */
    getHeader(name: JString): JString;

    /**
     * 以一个字符串对象集合的形式，返回包含指定请求消息头的所有值
     */
    getHeaders(name: JString): JList<JString>;

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

    //---------------------------------------------------------------------------------------------------------------------------------------------- 高阶封装

    /**
     * 获取数据库排序查询参数
     */
    getQueryBySort(): QueryBySort;

    /**
     * 获取数据库分页查询参数
     */
    getQueryByPage(): QueryByPage;

    /**
     * 获取参数对象
     */
    getParams<T extends object = any>(): T;

    /**
     * 获取Body对象
     */
    getBody<T extends object = any>(): T;

    /**
     * 获取参数Map
     */
    getParamsMap(): JMultiValueMap<JString, JString>;

    /**
     * 获取请求参数(Parameter和Body都会取)
     */
    getRequestData<T extends object = any>(): T;

    /**
     * 从Body中填充数据
     *
     * @param model    数据结构以及初始值
     * @param fillNull null值是否也要填充
     */
    fillFromBody(model: object, fillNull: boolean): void;

    /**
     * 从Body中填充数据
     *
     * @param model 数据结构以及初始值
     */
    fillFromBody(model: object): void;

    /**
     * 从Params中填充数据
     *
     * @param model    数据结构以及初始值
     * @param fillNull null值是否也要填充
     */
    fillFromParams(model: object, fillNull: boolean): void;

    /**
     * 从Params中填充数据
     *
     * @param model 数据结构以及初始值
     */
    fillFromParams(model: object): void;

    /**
     * 从Body或者Params中填充数据
     *
     * @param model    数据结构以及初始值
     * @param fillNull null值是否也要填充
     */
    fillFromAny(model: object, fillNull: boolean): void;

    /**
     * 从Body或者Params中填充数据
     *
     * @param model    数据结构以及初始值
     */
    fillFromAny(model: object): void;

    /**
     * 从Body中填充数据然后验证数据
     *
     * @param model    数据结构以及初始值
     * @param rule     校验规则
     * @param fillNull null值是否也要填充
     * @param fast     快速验证(只要有一个错误就抛出异常)
     */
    fillAndValidatedFromBody<T extends object = ValidatorBean>(model: T, rule: ValidatorRule<T>, fillNull: boolean, fast: boolean): void;

    /**
     * 从Body中填充数据然后验证数据
     *
     * @param model 数据结构以及初始值
     * @param rule  校验规则
     */
    fillAndValidatedFromBody<T extends object = ValidatorBean>(model: T, rule: ValidatorRule<T>): void;

    /**
     * 从Params中填充数据然后验证数据
     *
     * @param model    数据结构以及初始值
     * @param rule     校验规则
     * @param fillNull null值是否也要填充
     * @param fast     快速验证(只要有一个错误就抛出异常)
     */
    fillAndValidatedFromParams<T extends object = ValidatorBean>(model: T, rule: ValidatorRule<T>, fillNull: boolean, fast: boolean): void;

    /**
     * 从Params中填充数据然后验证数据
     *
     * @param model 数据结构以及初始值
     * @param rule  校验规则
     */
    fillAndValidatedFromParams<T extends object = ValidatorBean>(model: T, rule: ValidatorRule<T>): void;

    /**
     * 从Body或者Params中填充数据然后验证数据
     *
     * @param model    数据结构以及初始值
     * @param rule     校验规则
     * @param fillNull null值是否也要填充
     * @param fast     快速验证(只要有一个错误就抛出异常)
     */
    fillAndValidatedFromAny<T extends object = ValidatorBean>(model: T, rule: ValidatorRule<T>, fillNull: boolean, fast: boolean): void;

    /**
     * 从Body或者Params中填充数据然后验证数据
     *
     * @param model 数据结构以及初始值
     * @param rule  校验规则
     */
    fillAndValidatedFromAny<T extends object = ValidatorBean>(model: T, rule: ValidatorRule<T>): void;

    /**
     * 获取上传的文件名称
     */
    getUploadFileNames(): JList<JString>;

    /**
     * 获取上传的文件
     *
     * @param filename 文件名称
     */
    getUploadFile(filename: JString): UploadFile;

    /**
     * 获取上传的文件
     *
     * @param filename 文件名称
     */
    getUploadFiles(filename: JString): JList<UploadFile>;

    /**
     * 获取所有上传的文件
     */
    getAllUploadFiles(): JMultiValueMap<JString, UploadFile>;

    /**
     * 获取所有上传的文件
     *
     * @param filename 文件名称
     */
    getAllUploadFiles(filename: JString): JList<UploadFile>;

    /**
     * 获取第一个上传的文件
     */
    getFirstUploadFile(): UploadFile;
}
