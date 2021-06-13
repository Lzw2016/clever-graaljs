package org.clever.graaljs.spring.mvc.builtin.adapter;

import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.core.utils.codec.EncodeDecodeUtils;
import org.springframework.util.Assert;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

/**
 * 作者：lizw <br/>
 * 创建时间：2019/09/20 17:28 <br/>
 */
public class HttpResponseWrapper {
    protected HttpContext httpContext;
    private final HttpServletResponse delegate;

    // public HttpResponseWrapper(HttpContext httpContext) {
    //     Assert.notNull(httpContext, "参数httpContext不能为空");
    //     this.httpContext = httpContext;
    //     this.delegate = httpContext.response.delegate;
    // }

    protected HttpResponseWrapper(HttpServletResponse response) {
        Assert.notNull(response, "参数response不能为空");
        this.delegate = response;
    }

    /**
     * 原始HTTP响应对象
     */
    public HttpServletResponse originalResponse() {
        return delegate;
    }

    /**
     * 强制将buffer中的任何内容写入客户端
     */
    public void flushBuffer() throws IOException {
        delegate.flushBuffer();
    }

    /**
     * 清除所有存在于buffer和状态码以及头的数据
     */
    public void reset() {
        delegate.reset();
    }

    public void resetBuffer() {
        delegate.resetBuffer();
    }

    /**
     * 返回实际用于响应消息的buffer的大小
     */
    public int getBufferSize() {
        return delegate.getBufferSize();
    }

    /**
     * 为响应消息体设置合适的 buffer的大小
     */
    public void setBufferSize(int size) {
        delegate.setBufferSize(size);
    }

    /**
     * 返回用于在这个响应消息中被发送的 MIME body的charset的名称
     */
    public String getCharacterEncoding() {
        return delegate.getCharacterEncoding();
    }

    /**
     * 设置响应编码
     */
    public void setCharacterEncoding(String charset) {
        delegate.setCharacterEncoding(charset);
    }

    /**
     * 返回一个适用于在响应消息中写入二进制数据的 ServletOutputStream
     */
    public ServletOutputStream getOutputStream() throws IOException {
        return delegate.getOutputStream();
    }

    /**
     * 返回能够向一个客户机发送字符的 PrintWriter 对象
     */
    public PrintWriter getWriter() throws IOException {
        return delegate.getWriter();
    }

    /**
     * 返回一个布尔值，以指明是否响应消息已被执行
     */
    public boolean isCommitted() {
        return delegate.isCommitted();
    }

    /**
     * 设置响应中的内容长度。在HTTP servlet 中，这个方法就是设置HTTP Content-Length header
     */
    public void setContentLength(int len) {
        delegate.setContentLength(len);
    }

    /**
     * 设置响应中的内容长度。在HTTP servlet 中，这个方法就是设置HTTP Content-Length header
     */
    public void setContentLengthLong(long len) {
        delegate.setContentLengthLong(len);
    }

    /**
     * 设置正被发往客户端的响应的内容类型
     */
    public void setContentType(String type) {
        delegate.setContentType(type);
    }

    /**
     * 获取响应的内容类型
     */
    public String getContentType() {
        return delegate.getContentType();
    }

    /**
     * 设置响应消息的地址。适当地设置消息的头（包括 Content-Type的字符设置）
     */
    public void setLocale(Locale loc) {
        delegate.setLocale(loc);
    }

    /**
     * 返回分配给响应消息的场所
     */
    public Locale getLocale() {
        return delegate.getLocale();
    }

    /**
     * 将指定的cookie加入到响应中
     */
    public void addCookie(Cookie cookie) {
        delegate.addCookie(cookie);
    }

    /**
     * 给定的名字和和数值加到响应的头部
     */
    public void addHeader(String name, String value) {
        delegate.addHeader(name, value);
    }

    /**
     * 把给定的名字和日期值加入到响应的头部
     */
    public void addDateHeader(String name, long date) {
        delegate.addDateHeader(name, date);
    }

    /**
     * 将给定的名字和整数值加到一个相应的头部
     */
    public void addIntHeader(String name, int value) {
        delegate.addIntHeader(name, value);
    }

    /**
     * 设置带有给定的名字和数值的响应消息头
     */
    public void setHeader(String name, String value) {
        delegate.setHeader(name, value);
    }

    /**
     * 设置带有给定的名字和数值的响应消息头
     */
    public void setDateHeader(String name, long date) {
        delegate.setDateHeader(name, date);
    }

    /**
     * 设置带有给定的名字和整数值的响应消息头
     */
    public void setIntHeader(String name, int value) {
        delegate.setIntHeader(name, value);
    }

    /**
     * 获取响应消息头信息
     */
    public String getHeader(String name) {
        return delegate.getHeader(name);
    }

    /**
     * 获取响应消息头信息
     */
    public Collection<String> getHeaders(String name) {
        return delegate.getHeaders(name);
    }

    /**
     * 获取所有响应消息头名称
     */
    public Collection<String> getHeaderNames() {
        return delegate.getHeaderNames();
    }

    /**
     * 返回一个布尔值以反映是否指定的响应消息头部已被设置过了
     */
    public boolean containsHeader(String name) {
        return delegate.containsHeader(name);
    }

    /**
     * 为在 sendRedirect 方法中使用的指定的URL进行编码。或者，如果编码是没有必要的，就返回没有改变的URL
     */
    public String encodeRedirectURL(String url) {
        return delegate.encodeRedirectURL(url);
    }

    /**
     * 通过调用URL中的会话ID对指定的URL进行编码，或者如果编码时不必要的就返回“URL不变”
     */
    public String encodeURL(String url) {
        return delegate.encodeURL(url);
    }

    /**
     * 用指定的重定位URL向客户端发送一个临时重定位响应消息
     */
    public void sendRedirect(String location) throws IOException {
        delegate.sendRedirect(location);
    }

    /**
     * 设置响应消息的状态码
     */
    public void setStatus(int sc) {
        delegate.setStatus(sc);
    }

    /**
     * 用指定的状态码和描述消息向客户端发送一个错误响应
     */
    public void sendError(int sc) throws IOException {
        delegate.sendError(sc);
    }

    /**
     * 用指定的状态向客户端发送一个错误响应
     */
    public void sendError(int sc, String msg) throws IOException {
        delegate.sendError(sc, msg);
    }

    /**
     * 获取响应状态码
     */
    public int getStatus() {
        return delegate.getStatus();
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------- 高阶封装

    /**
     * 设置当前响应是下载文件
     *
     * @param fileName      文件名称
     * @param contentLength 文件内容大小
     */
    public void setDownloadFileName(String fileName, Long contentLength) {
        fileName = StringUtils.trim(fileName);
        Assert.hasText(fileName, "参数fileName不能为空");
        fileName = EncodeDecodeUtils.browserDownloadFileName(httpContext.request.getHeader("User-Agent"), fileName);
        delegate.setContentType("application/force-download");
        delegate.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
        if (contentLength != null && contentLength >= 0) {
            delegate.setContentLengthLong(contentLength);
        }
    }

    /**
     * 设置当前响应是下载文件
     *
     * @param fileName 文件名称
     */
    public void setDownloadFileName(String fileName) {
        setDownloadFileName(fileName, null);
    }
}