package org.clever.graaljs.spring.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.clever.graaljs.core.utils.ExceptionUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Set;

/**
 * HttpServletRequest工具类<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-6-19 23:12 <br/>
 */
@Slf4j
public class HttpServletRequestUtils {
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
    public static String getRequestURL(HttpServletRequest request) {
        if (request == null) {
            return "";
        } else {
            return request.getRequestURL().toString();
        }
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
    public static String getRequestURI(HttpServletRequest request) {
        if (request == null) {
            return "";
        } else {
            return request.getRequestURI();
        }
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
    public static String getRequestURINotSuffix(HttpServletRequest request) {
        if (request == null) {
            return "";
        } else {
            String requestUrl = request.getRequestURI();
            int positionBySeparator = requestUrl.lastIndexOf("/");
            int positionPoint = requestUrl.lastIndexOf(".");
            if (positionPoint >= 0 && positionPoint > positionBySeparator) {
                requestUrl = requestUrl.substring(0, positionPoint);
            }
            return requestUrl;
        }
    }

    /**
     * 字符串数组输出
     */
    private static String arrayToString(String[] array) {
        if (array == null || array.length <= 0) {
            return "";
        }
        if (array.length == 1) {
            return array[0];
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : array) {
            if (stringBuilder.length() <= 0) {
                stringBuilder.append(str);
            } else {
                stringBuilder.append(",").append(str);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 获取请求的所有参数
     *
     * @param request 请求对象
     * @return 请求数据值(已解码)
     */
    public static String getRequestParams(HttpServletRequest request) {
        if (request != null && request.getClass().getName().startsWith("org.springframework.web.multipart.MultipartHttpServletRequest")) {
            // 用户上传文件请求[系统判断]
            return "";
        }
        if (request == null) {
            return "";
        }
        StringBuilder paramStr = new StringBuilder();
        Set<Map.Entry<String, String[]>> paramMap = request.getParameterMap().entrySet();
        for (Map.Entry<String, String[]> entry : paramMap) {
            if (paramStr.length() <= 0) {
                paramStr.append(entry.getKey()).append("=").append(arrayToString(entry.getValue()));
            } else {
                paramStr.append("&").append(entry.getKey()).append("=").append(arrayToString(entry.getValue()));
            }
        }
        String result = paramStr.toString();
        try {
            result = URLDecoder.decode(result, "UTF-8");
        } catch (Throwable e) {
            throw ExceptionUtils.unchecked(e);
        }
        return result;
    }


}
