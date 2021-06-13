package org.clever.graaljs.spring.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/09/21 11:25 <br/>
 */
public interface ExceptionResolver {
    /**
     * 异常处理
     *
     * @return 返回对象不为空会被序列化成json响应给客户端
     */
    Object resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex);
}
