package org.clever.graaljs.spring.mvc.builtin.wrap;

import org.springframework.core.convert.ConversionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 作者：lizw <br/>
 * 创建时间：2020/08/31 20:17 <br/>
 */
public class HttpContext {
    public final HttpRequestWrapper request;
    public final HttpResponseWrapper response;
    public HttpSessionWrapper session;
    public final ServletContextWrapper servletContext;

    public HttpContext(HttpServletRequest request, HttpServletResponse response, ConversionService conversionService) {
        org.clever.graaljs.spring.mvc.builtin.adapter.HttpContext httpContext = new org.clever.graaljs.spring.mvc.builtin.adapter.HttpContext(request, response, conversionService);
        this.request = new HttpRequestWrapper(httpContext.request);
        this.response = new HttpResponseWrapper(httpContext.response);
        HttpSessionWrapper sessionWrapper = null;
        if (httpContext.session != null) {
            sessionWrapper = new HttpSessionWrapper(httpContext.session);
        }
        ServletContextWrapper servletContextWrapper = null;
        if (httpContext.servletContext != null) {
            servletContextWrapper = new ServletContextWrapper(httpContext.servletContext);
        }
        this.session = sessionWrapper;
        this.servletContext = servletContextWrapper;
        init();
    }

    private void init() {
        this.request.httpContext = this;
        this.response.httpContext = this;
        this.session.httpContext = this;
        this.servletContext.httpContext = this;
    }
}
