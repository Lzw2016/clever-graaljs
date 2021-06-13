package org.clever.graaljs.spring.mvc.builtin.adapter;

import org.springframework.util.Assert;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 作者：lizw <br/>
 * 创建时间：2019/09/20 17:28 <br/>
 */
public class HttpSessionWrapper {
    protected HttpContext httpContext;
    private final HttpSession delegate;

    // public HttpSessionWrapper(HttpContext httpContext) {
    //     Assert.notNull(httpContext, "参数httpContext不能为空");
    //     this.httpContext = httpContext;
    //     this.delegate = httpContext.session.delegate;
    // }

    protected HttpSessionWrapper(HttpSession session) {
        Assert.notNull(session, "参数session不能为空");
        this.delegate = session;
    }

    /**
     * 原始HTTP Session对象
     */
    public HttpSession originalSession() {
        return delegate;
    }

    /**
     * 返回一个包含所有的绑定到会话的 对象名称的字符串对象的集合
     */
    public List<String> getAttributeNames() {
        List<String> list = new ArrayList<>();
        Enumeration<String> enumeration = delegate.getAttributeNames();
        while (enumeration.hasMoreElements()) {
            list.add(enumeration.nextElement());
        }
        return list;
    }

    /**
     * 返回在本会话中绑定了指定名字的对象，当没有所要求的对象时，返回一个空值
     */
    public Object getAttribute(String name) {
        return delegate.getAttribute(name);
    }

    /**
     * 用指定的名字将一个对象绑定到一个会话
     */
    public void setAttribute(String name, Object value) {
        delegate.setAttribute(name, value);
    }

    /**
     * 从会话中删除绑定到指定名字的对象
     */
    public void removeAttribute(String name) {
        delegate.removeAttribute(name);
    }

    /**
     * 返回会话建立的时间，在格林威治时间1970年1月1日子夜开始计算，单位是milliseconds
     */
    public long getCreationTime() {
        return delegate.getCreationTime();
    }

    /**
     * 返回一个包含分配给会话的唯一的标示符的字符串
     */
    public String getId() {
        return delegate.getId();
    }

    /**
     * 返回客户端发出的关于本会话的请求消息的最后一次的时间。单位是毫秒，自格林威治时间1970年1月1日午夜开始计时
     */
    public long getLastAccessedTime() {
        return delegate.getLastAccessedTime();
    }

    /**
     * 返回servlet容器将在两个客户端访问之间保持会话开放的间隔的最大时间，以秒计时
     */
    public int getMaxInactiveInterval() {
        return delegate.getMaxInactiveInterval();
    }

    /**
     * 指定在客户端请求消息之间servlet容器将该会话设为无效之前的以秒计的时间
     */
    public void setMaxInactiveInterval(int interval) {
        delegate.setMaxInactiveInterval(interval);
    }

    /**
     * 使该会话无效，然后取消绑定到该会话的任何对象
     */
    public void invalidate() {
        delegate.invalidate();
    }

    /**
     * 如果客户端尚不知道会话或客户端选择不加入会话，则返回true。例如，如果服务器仅使用基于cookie的会话，而客户端禁用了cookie的使用，则在每个请求上会话都是新的<br />
     * 如果服务器创建了会话，但客户端尚未加入，则为true
     */
    public boolean isNew() {
        return delegate.isNew();
    }

    public ServletContextWrapper getServletContext() {
        return httpContext.servletContext;
    }
}
