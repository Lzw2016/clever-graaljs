package org.clever.graaljs.spring.mvc.builtin.wrap;

import org.springframework.util.Assert;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/08/31 20:17 <br/>
 */
public class ServletContextWrapper {
    protected HttpContext httpContext;
    private final org.clever.graaljs.spring.mvc.builtin.adapter.ServletContextWrapper delegate;

    protected ServletContextWrapper(org.clever.graaljs.spring.mvc.builtin.adapter.ServletContextWrapper servletContext) {
        Assert.notNull(servletContext, "参数servletContext不能为空");
        this.delegate = servletContext;
    }

    /**
     * 原始ServletContext对象
     */
    public ServletContext originalContext() {
        return delegate.originalContext();
    }

    /**
     * 返回一个于服务器上指定的URL进行通信的 ServletContext 对象
     */
    public ServletContextWrapper getContext(String uripath) {
        return new ServletContextWrapper(delegate.getContext(uripath));
    }

    /**
     * Web应用程序的上下文路径，或者为根上下文“”
     */
    public String getContextPath() {
        return delegate.getContextPath();
    }

    /**
     * 返回与此ServletContext相对应的此Web应用程序的名称，该名称由display-name元素为此Web应用程序的部署描述符中指定。
     */
    public String getServletContextName() {
        return delegate.getServletContextName();
    }

    /**
     * 返回 servlet 容器支持的最低的 Java Servlet API版本号
     */
    public int getMinorVersion() {
        return delegate.getMinorVersion();
    }

    /**
     * 返回 servlet 容器支持的较高的 Java Servlet API版本号
     */
    public int getMajorVersion() {
        return delegate.getMajorVersion();
    }

    public int getEffectiveMajorVersion() {
        return delegate.getEffectiveMajorVersion();
    }

    public int getEffectiveMinorVersion() {
        return delegate.getEffectiveMinorVersion();
    }

    /**
     * 返回servlet运行的servlet 容器的版本和名称
     */
    public String getServerInfo() {
        return delegate.getServerInfo();
    }

    /**
     * 返回一个作为给定名字的servlet的封装器的 RequestDispatcher对象
     */
    public RequestDispatcher getNamedDispatcher(String name) {
        return delegate.getNamedDispatcher(name);
    }

    /**
     * 返回一个作为位于给定路径的资源资源的封装器的 RequestDispatcher 对象
     */
    public RequestDispatcher getRequestDispatcher(String path) {
        return delegate.getRequestDispatcher(path);
    }

    /**
     * 返回一个到指明路径的资源的URL
     */
    public URL getResource(String path) throws MalformedURLException {
        return delegate.getResource(path);
    }

    /**
     * 返回包含给定的虚拟路径的真实路径的字符串
     */
    public String getRealPath(String path) {
        return delegate.getRealPath(path);
    }

    /**
     * 返回位于指定路径的InputStream 对象资源
     */
    public InputStream getResourceAsStream(String path) {
        return delegate.getResourceAsStream(path);
    }

    /**
     * 将指定的消息写到一个servlet日志文件，通常是一个事件日志文件
     */
    public void log(String msg) {
        delegate.log(msg);
    }

    /**
     * 将一个解释性消息和一个给定的Throwable异常跟踪消息写入到servlet 日志文件
     */
    public void log(String message, Throwable throwable) {
        delegate.log(message, throwable);
    }

    /**
     * 返回一个包含servlet context中属性名字变量的集合
     */
    public List<String> getAttributeNames() {
        return delegate.getAttributeNames();
    }

    /**
     * 返回具有给动名字的servlet container 的属性,或者当没有具有所给名字的属性时，返回一个空值
     */
    public Object getAttribute(String name) {
        return delegate.getAttribute(name);
    }

    /**
     * 绑定一个对象到在 servlet context中给定的属性名称
     */
    public void setAttribute(String name, Object object) {
        delegate.setAttribute(name, object);
    }

    /**
     * 从servlet context中删除带有给定名字的属性
     */
    public void removeAttribute(String name) {
        delegate.removeAttribute(name);
    }

    /**
     * 返回指定文件的MIME类型，若未知，则返回空值
     */
    public String getMimeType(String file) {
        return delegate.getMimeType(file);
    }

    /**
     * 返回一个包含给定名字的context-wide 初始化参数，若此参数不存在就返回一个空值
     */
    public String getInitParameter(String name) {
        return delegate.getInitParameter(name);
    }

    /**
     * 返回context的初始化参数的名字 ，用一个字符串对象枚举变量的形式。如果相应的context没有初始化参数，则就返回一个空的枚举变量
     */
    public List<String> getInitParameterNames() {
        return delegate.getInitParameterNames();
    }

    public Set<String> getResourcePaths(String path) {
        return delegate.getResourcePaths(path);
    }

    public boolean setInitParameter(String name, String value) {
        return delegate.setInitParameter(name, value);
    }

    public String getVirtualServerName() {
        return delegate.getVirtualServerName();
    }

    /**
     * 获取此ServletContext默认支持的会话超时（以分钟为单位）
     */
    public int getSessionTimeout() {
        return delegate.getSessionTimeout();
    }

    public void setSessionTimeout(int sessionTimeout) {
        delegate.setSessionTimeout(sessionTimeout);
    }

    public String getRequestCharacterEncoding() {
        return delegate.getRequestCharacterEncoding();
    }

    public void setRequestCharacterEncoding(String encoding) {
        delegate.setRequestCharacterEncoding(encoding);
    }

    public String getResponseCharacterEncoding() {
        return delegate.getResponseCharacterEncoding();
    }

    public void setResponseCharacterEncoding(String encoding) {
        delegate.setResponseCharacterEncoding(encoding);
    }
}
