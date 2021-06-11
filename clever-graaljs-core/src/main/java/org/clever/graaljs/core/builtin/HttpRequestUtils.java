package org.clever.graaljs.core.builtin;

import org.clever.graaljs.core.utils.HttpServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/28 22:33 <br/>
 */
public class HttpRequestUtils {
    public static final HttpRequestUtils Instance = new HttpRequestUtils();

    private HttpRequestUtils() {
    }

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
    public String getRequestURL(HttpServletRequest request) {
        return HttpServletRequestUtils.getRequestURL(request);
    }

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
    public String getRequestURI(HttpServletRequest request) {
        return HttpServletRequestUtils.getRequestURI(request);
    }

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
    public String getRequestURINotSuffix(HttpServletRequest request) {
        return HttpServletRequestUtils.getRequestURINotSuffix(request);
    }

    /**
     * 获取请求的所有参数
     *
     * @param request 请求对象
     * @return 请求数据值(已解码)
     */
    public String getRequestParams(HttpServletRequest request) {
        return HttpServletRequestUtils.getRequestParams(request);
    }
}
