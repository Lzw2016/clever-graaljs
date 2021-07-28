interface CookieUtils {
    /**
     * 设置Cookie
     *
     * @param response HTTP响应
     * @param path     Cookie的Path
     * @param name     名称
     * @param value    值
     * @param maxAge   Cookie生存时间，单位：秒。负数表示Cookie永不过期，0表示删除Cookie
     */
    setCookie(response: JHttpServletResponse, path: JString, name: JString, value: JString, maxAge: JInt): void;

    /**
     * 设置Cookie
     *
     * @param response HTTP响应
     * @param path     Cookie的Path
     * @param name     名称
     * @param value    值
     */
    setCookie(response: JHttpServletResponse, path: JString, name: JString, value: JString): void;

    /**
     * 设置Cookie(当前路径)
     *
     * @param response HTTP响应
     * @param name  名称
     * @param value 值
     */
    setCookieForCurrentPath(response: JHttpServletResponse, name: JString, value: JString): void;

    /**
     * 设置Cookie(根路径)
     *
     * @param response HTTP响应
     * @param name  名称
     * @param value 值
     */
    setCookieForRooPath(response: JHttpServletResponse, name: JString, value: JString): void;

    /**
     * 获得指定Cookie的值
     *
     * @param request 请求对象
     * @param name    名字
     * @return Cookie值，不存在返回null
     */
    getCookie(request: JHttpServletRequest, name: JString): JString;

    /**
     * 删除指定Cookie
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param name     名称
     * @param path     Cookie的Path
     */
    delCookie(request: JHttpServletRequest, response: JHttpServletResponse, name: JString, path: JString): void;

    /**
     * 删除指定Cookie(当前路径)
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param name     名称
     */
    delCookieForCurrentPath(request: JHttpServletRequest, response: JHttpServletResponse, name: JString): void;

    /**
     * 删除指定Cookie(根路径)
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param name     名称
     */
    delCookieForRooPath(request: JHttpServletRequest, response: JHttpServletResponse, name: JString): void;
}

declare const CookieUtils: CookieUtils;

