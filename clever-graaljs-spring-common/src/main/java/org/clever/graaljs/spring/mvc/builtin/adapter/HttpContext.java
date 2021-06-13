package org.clever.graaljs.spring.mvc.builtin.adapter;

import org.springframework.core.convert.ConversionService;
import org.springframework.util.Assert;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/08/24 22:04 <br/>
 */
public class HttpContext {

    public final HttpRequestWrapper request;
    public final HttpResponseWrapper response;
    public HttpSessionWrapper session;
    public final ServletContextWrapper servletContext;

    public HttpContext(HttpServletRequest request, HttpServletResponse response, ConversionService conversionService) {
        Assert.notNull(request, "参数request不能为空");
        Assert.notNull(response, "参数response不能为空");
        this.request = new HttpRequestWrapper(request, conversionService);
        this.response = new HttpResponseWrapper(response);
        HttpSessionWrapper sessionWrapper = null;
        HttpSession httpSession = request.getSession();
        if (httpSession != null) {
            sessionWrapper = new HttpSessionWrapper(httpSession);
        }
        ServletContextWrapper servletContextWrapper = null;
        ServletContext servletContext = request.getServletContext();
        if (servletContext != null) {
            servletContextWrapper = new ServletContextWrapper(servletContext);
        }
        this.session = sessionWrapper;
        this.servletContext = servletContextWrapper;
        init();
    }

    public HttpContext(HttpServletRequest request, HttpServletResponse response) {
        this(request, response, null);
    }

    private void init() {
        this.request.httpContext = this;
        this.response.httpContext = this;
        this.session.httpContext = this;
        this.servletContext.httpContext = this;
    }
}
