interface HttpRequestUtils {
    /**
     * 获取请求的URL地址
     * <pre>
     * 示例：
     * 当前url：http://localhost:8080/CarsiLogCenter_new/idpstat.jsp?action=idp.sptopn
     * request.getRequestURL() http://localhost:8080/CarsiLogCenter_new/idpstat.jsp
     * request.getRequestURI() /CarsiLogCenter_new/idpstat.jsp
     * request.getContextPath()/CarsiLogCenter_new
     * request.getServletPath() /idpstat.jsp
     * request.getQueryString()action=idp.sptopn
     * </pre>
     *
     * @param request 请求对象
     * @return 请求的URL地址
     */
    getRequestURL(request: JHttpServletRequest): JString;

    /**
     * 获取请求的URI地址
     * <pre>
     * 示例：
     * 当前url：http://localhost:8080/CarsiLogCenter_new/idpstat.jsp?action=idp.sptopn
     * request.getRequestURI() /CarsiLogCenter_new/idpstat.jsp
     * </pre>
     *
     * @param request 请求对象
     * @return 请求的URL地址
     */
    getRequestURI(request: JHttpServletRequest): JString;

    /**
     * 获取请求的URI地址(不包含后缀,如:.json、.xml、.html、.jsp等)
     * <pre>
     * 示例：
     * 当前url：http://localhost:8080/CarsiLogCenter_new/idpstat.jsp?action=idp.sptopn
     * request.getRequestURI() /CarsiLogCenter_new/idpstat
     * </pre>
     *
     * @param request 请求对象
     * @return 请求的URL地址
     */
    getRequestURINotSuffix(request: JHttpServletRequest): JString;

    /**
     * 获取请求的所有参数
     *
     * @param request 请求对象
     * @return 请求数据值(已解码)
     */
    getRequestParams(request: JHttpServletRequest): JString;
}

declare const HttpRequestUtils: HttpRequestUtils;
