package org.clever.graaljs.spring.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/09/16 21:50 <br/>
 */
public interface ScriptHandler {

    /**
     * 使用Script代码处理HTTP请求
     *
     * @param request  HTTP请求
     * @param response HTTP响应
     * @param handler  原始处理请求的对象
     * @return 已处理请求返回true，未处理返回false
     */
    boolean handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
}
