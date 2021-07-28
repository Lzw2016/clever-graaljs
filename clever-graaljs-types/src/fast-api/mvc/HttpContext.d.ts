interface HttpContext {
    /**
     * HTTP 请求对象
     */
    readonly request: HttpRequestWrapper;

    /**
     * HTTP 响应对象
     */
    readonly response: HttpResponseWrapper;

    /**
     * HTTP Session对象
     */
    readonly session: HttpSessionWrapper;
    /**
     * Servlet 容器对象
     */
    readonly servletContext: ServletContextWrapper;
}

/**
 * http请求上下文，仅在实现http接口时可用
 */
declare const ctx: HttpContext;
