interface JCookie extends JObject{
    javax_servlet_http_Cookie: "javax.servlet.http.Cookie";

    /**
     * 返回用于描述cookie的目的的注释，如果没有注释，就返回一个空值
     */
    getComment(): JString | null;

    /**
     * 返回cookie设置的域名
     */
    getDomain(): JString;

    /**
     * 返回cookie的最大存活时间，以秒计算，在缺省情况下，-1标示该cookie将一致持续到浏览器shutdown为止
     */
    getMaxAge(): JInt;

    /**
     * 返回cookie的名称
     */
    getName(): JString;

    /**
     * 返回服务器上浏览器返回cookie的路径
     */
    getPath(): JString;

    /**
     * 如果浏览器之通过安全协议发送cookie则返回一个真值，否则，若浏览器可以用任何协议发送cookie，则返回一个"false"值
     */
    getSecure(): JBoolean;

    /**
     * 返回 cookie的取值
     */
    getValue(): JString;

    /**
     * 返回本cookie遵从的协议的版本
     */
    getVersion(): JInt;

    /**
     * 指定一个注释来描述cookie的目的
     */
    setComment(purpose: JString): void;

    /**
     * 指明cookie应当被声明的域
     */
    setDomain(pattern: JString): void;

    /**
     * 设置以秒计的cookie的最大存活时间
     */
    setMaxAge(expiry: JInt): void;

    /**
     * 指定客户端将cookie返回的cookie的路径
     */
    setPath(uri: JString): void;

    /**
     * 指定是否cookie应该只通过安全协议，例如HTTPS或SSL,传送给浏览器
     */
    setSecure(flag: JBoolean): void;

    /**
     *
     * 在一个cookie创建之后，给其分配一个新的值
     */
    setValue(newValue: JString): void;

    /**
     * 设置本cookie遵循的cookie的协议的版本
     */
    setVersion(v: JInt): void;
}