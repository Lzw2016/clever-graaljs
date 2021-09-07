package org.clever.graaljs.spring.mvc.builtin.wrap;

import org.clever.graaljs.core.internal.utils.InteropJavaToScriptUtils;
import org.clever.graaljs.core.internal.utils.InteropScriptToJavaUtils;
import org.clever.graaljs.spring.core.builtin.wrap.ValidatorUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindException;

import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/08/31 20:17 <br/>
 */
public class HttpRequestWrapper {
    protected HttpContext httpContext;
    private final org.clever.graaljs.spring.mvc.builtin.adapter.HttpRequestWrapper delegate;

    // public HttpRequestWrapper(HttpContext httpContext) {
    //     Assert.notNull(httpContext, "参数httpContext不能为空");
    //     this.httpContext = httpContext;
    //     this.delegate = httpContext.request.delegate;
    // }

    protected HttpRequestWrapper(org.clever.graaljs.spring.mvc.builtin.adapter.HttpRequestWrapper request) {
        this.delegate = request;
    }

    /**
     * 原始HTTP请求对象
     */
    public HttpServletRequest originalRequest() {
        return delegate.originalRequest();
    }

    /**
     * 返回一个包含所有的绑定到请求的对象名称的字符串对象的集合
     */
    public List<String> getAttributeNames() {
        return delegate.getAttributeNames();
    }

    /**
     * 返回具有给定名字的 servlet container 的属性,或者当没有具有所给名字的属性时，返回一个空值
     */
    public Object getAttribute(String name) {
        return delegate.getAttribute(name);
    }

    /**
     * 存储一个请求消息中的属性
     */
    public void setAttribute(String name, Object value) {
        delegate.setAttribute(name, value);
    }

    /**
     * 从请求消息中删除一个属性
     */
    public void removeAttribute(String name) {
        delegate.removeAttribute(name);
    }

    /**
     * 返回用在请求信息的body编码的字符的名称
     */
    public String getCharacterEncoding() {
        return delegate.getCharacterEncoding();
    }

    /**
     * 返回请求体的用字节表示的长度，并被输入流改变为有效。如果长度未知，就返回-1
     */
    public int getContentLength() {
        return delegate.getContentLength();
    }

    /**
     * 返回请求体的 MIME类型，当长度未知是就返回一个空值
     */
    public String getContentType() {
        return delegate.getContentType();
    }

    /**
     * 用一个 ServletInputStream 重新得到二进制的请求消息体
     */
    public ServletInputStream getInputStream() throws IOException {
        return delegate.getInputStream();
    }

    /**
     * 用一个 BufferedReader 重新得到字符串数据的请求消息体
     */
    public BufferedReader getReader() throws IOException {
        return delegate.getReader();
    }

    /**
     * 返回 基于Accept-Language 的头部的客户端将接收内容的首选的场所
     */
    public Locale getLocale() {
        return delegate.getLocale();
    }

    /**
     * 返回一个地址对象的枚举变量，它以开始的首选地址的降序排列指明了基于 Accept-Language header 的客户端可接受的地点
     */
    public List<Locale> getLocales() {
        return delegate.getLocales();
    }

    /**
     * 以 protocol/majorVersion.minorVersion, 的格式返回请求所用协议的名称和版本。例如HTTP/1.1
     */
    public String getProtocol() {
        return delegate.getProtocol();
    }

    /**
     * 返回一个布尔值以指明是否这个请求使用了一个安全信道，如HTTPS
     */
    public boolean isSecure() {
        return delegate.isSecure();
    }

    /**
     * 返回一个作为位于给定路径的资源资源的封装器的 RequestDispatcher 对象
     */
    public RequestDispatcher getRequestDispatcher(String path) {
        return delegate.getRequestDispatcher(path);
    }

    public DispatcherType getDispatcherType() {
        return delegate.getDispatcherType();
    }

    /**
     * 返回用以作出请求消息的方案的名称，如 http, https, 或ftp等
     */
    public String getScheme() {
        return delegate.getScheme();
    }

    /**
     * 返回收到请求的服务器主机的名字
     */
    public String getServerName() {
        return delegate.getServerName();
    }

    /**
     * 返回收到请求的端口号
     */
    public int getServerPort() {
        return delegate.getServerPort();
    }

    /**
     * 返回一个请求参数的字符串值。若该参数不存在，则返回一个空值
     */
    public String getParameter(String name) {
        return delegate.getParameter(name);
    }

    /**
     * 返回一个包含了请求中的参数名字的字符串对象的枚举变量
     */
    public List<String> getParameterNames() {
        return delegate.getParameterNames();
    }

    /**
     * 返回一个包含所有的给定请求参数的值的字符串对象的向量。若该参数不存在，则返回一个空值
     */
    public String[] getParameterValues(String name) {
        return delegate.getParameterValues(name);
    }

    /**
     * 获取所有请求参数
     */
    public Map<String, String[]> getParameterMap() {
        return delegate.getParameterMap();
    }

    /**
     * 返回客户端发送请求的IP地址
     */
    public String getRemoteAddr() {
        return delegate.getRemoteAddr();
    }

    /**
     * 返回发送请求的客户端的完全合格的名称；或者如果客户端的名字没有确定则返回其IP地址
     */
    public String getRemoteHost() {
        return delegate.getRemoteHost();
    }

    /**
     * Remote Port
     */
    public int getRemotePort() {
        return delegate.getRemotePort();
    }

    /**
     * Local Addr
     */
    public String getLocalAddr() {
        return delegate.getLocalAddr();
    }

    /**
     * Local Name
     */
    public String getLocalName() {
        return delegate.getLocalName();
    }

    /**
     * Local Port
     */
    public int getLocalPort() {
        return delegate.getLocalPort();
    }

    /**
     * 返回用于保护servlet的认证方案的名字，例如， "BASIC" 或 "SSL,"。如果servlet没有被保护则返回一个空值
     */
    public String getAuthType() {
        return delegate.getAuthType();
    }

    /**
     * 如果用户已被授权认证，则返回用户作出请求的登录情况；否则就返回一个空值
     */
    public String getRemoteUser() {
        return delegate.getRemoteUser();
    }

    /**
     * 返回由客户机指定的会话ID
     */
    public String getRequestedSessionId() {
        return delegate.getRequestedSessionId();
    }

    /**
     * 返回关于该请求的当前会话。或者若该请求没有会话则就创建一个
     */
    public HttpSessionWrapper getSession(boolean create) {
        org.clever.graaljs.spring.mvc.builtin.adapter.HttpSessionWrapper session = delegate.getSession();
        final boolean exists = session != null;
        session = delegate.getSession(create);
        final boolean nowExists = session != null;
        if (exists != nowExists) {
            // 创建了 HttpSession - 需要刷新 httpContext.session - 值
            httpContext.session = new HttpSessionWrapper(session);
        }
        return httpContext.session;
    }

    /**
     * 返回有关本请求的当前HttpSession，或者若该请求没有会话，且“创建”属性为真，则就创建一个
     */
    public HttpSessionWrapper getSession() {
        return httpContext.session;
    }

    /**
     * 检查是否要求的会话ID已作为cookie来到
     */
    public boolean isRequestedSessionIdFromCookie() {
        return delegate.isRequestedSessionIdFromCookie();
    }

    /**
     * 检查是否要求的会话ID已作为请求URL的一部分到达
     */
    public boolean isRequestedSessionIdFromURL() {
        return delegate.isRequestedSessionIdFromURL();
    }

    /**
     * 检查是否要求的会话ID仍有效
     */
    public boolean isRequestedSessionIdValid() {
        return delegate.isRequestedSessionIdValid();
    }

    /**
     * 返回一个包含了当前被授权的用户名字的 java.security.Principal 对象
     */
    public Principal getUserPrincipal() {
        return delegate.getUserPrincipal();
    }

    /**
     * 返回一个布尔值以指明是否被授权的用户已被包含在指定的逻辑"role"中
     */
    public boolean isUserInRole(String role) {
        return delegate.isUserInRole(role);
    }

    /**
     * 返回指明请求context的请求URL的部分
     */
    public String getContextPath() {
        return delegate.getContextPath();
    }

    /**
     * 返回调用servlet的请求的URL部分<br />
     * 请求url path，如：/api/demo_1
     */
    public String getServletPath() {
        return delegate.getServletPath();
    }

    /**
     * 返回用以作出请求的HTTP方法的名称，例如 GET, POST,或 PUT等等
     */
    public String getMethod() {
        return delegate.getMethod();
    }

    /**
     * 返回该请求消息的URL中HTTP协议第一行里从协议名称到请求字符串的部分<br />
     * 请求url path，如：/api/demo_1
     */
    public String getRequestURI() {
        return delegate.getRequestURI();
    }

    /**
     * 请求url(protocol、host、path)，如：http://demo.msvc.top:18081/api/demo_1
     */
    public String getRequestURL() {
        return String.valueOf(delegate.getRequestURL());
    }

    /**
     * 返回有关当客户端作出请求时的URL的任何额外的路径信息
     */
    public String getPathInfo() {
        return delegate.getPathInfo();
    }

    /**
     * 返回在servlet名字之后查询字符串之前的任何额外的路径信息，并将其转换成一个真正的路径
     */
    public String getPathTranslated() {
        return delegate.getPathTranslated();
    }

    /**
     * 返回包含在请求URL中路径之后的查询字符串<br />
     * 查询字符串，如：a=123a&b=456b
     */
    public String getQueryString() {
        return delegate.getQueryString();
    }

    /**
     * 返回所有的本请求消息包含的头名字的集合
     */
    public List<String> getHeaderNames() {
        return delegate.getHeaderNames();
    }

    /**
     * 返回指定的作为字符串请求消息头的值
     */
    public String getHeader(String name) {
        return delegate.getHeader(name);
    }

    /**
     * 以一个字符串对象集合的形式，返回包含指定请求消息头的所有值
     */
    public List<String> getHeaders(String name) {
        return delegate.getHeaders(name);
    }

    /**
     * 返回一个指定的请求消息头的整数值
     */
    public int getIntHeader(String name) {
        return delegate.getIntHeader(name);
    }

    /**
     * 返回指定的请求消息头的值，返回值格式是描述日期对象的长型数值
     */
    public long getDateHeader(String name) {
        return delegate.getDateHeader(name);
    }

    /**
     * 返回包含所有的客户端随请求信息发送来的cookie对象的一个 array
     */
    public Cookie[] getCookies() {
        return delegate.getCookies();
    }

    public ServletContextWrapper getServletContext() {
        return httpContext.servletContext;
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------- 高阶封装

    /**
     * 获取数据库排序查询参数
     */
    public Map<String, Object> getQueryBySort() {
        return delegate.getQueryBySort();
    }

    /**
     * 获取数据库分页查询参数
     */
    public Map<String, Object> getQueryByPage() {
        return delegate.getQueryByPage();
    }

    /**
     * 获取参数对象
     */
    public Object getParams() {
        Map<String, Object> body = delegate.getParams();
        return InteropJavaToScriptUtils.Instance.convertMap(body);
    }

    /**
     * 获取Body对象
     */
    public Object getBody() throws IOException {
        Map<String, Object> body = delegate.getBody();
        return InteropJavaToScriptUtils.Instance.convertMap(body);
    }

    /**
     * 获取参数Map
     */
    public MultiValueMap<String, String> getParamsMap() {
        return delegate.getParamsMap();
    }

    /**
     * 获取请求参数(Parameter和Body都会取)
     */
    public Object getRequestData() throws IOException {
        Map<String, Object> data = delegate.getRequestData();
        return InteropJavaToScriptUtils.Instance.convertMap(data);
    }

    /**
     * 从Body中填充数据
     *
     * @param model    数据结构以及初始值
     * @param fillNull null值是否也要填充
     */
    public void fillFromBody(Map<String, Object> model, boolean fillNull) throws IOException {
        model = InteropScriptToJavaUtils.Instance.deepConvertMap(model);
        delegate.fillFromBody(model, fillNull);
        InteropJavaToScriptUtils.Instance.convertMap(model);
    }

    /**
     * 从Body中填充数据
     *
     * @param model 数据结构以及初始值
     */
    public void fillFromBody(Map<String, Object> model) throws IOException {
        model = InteropScriptToJavaUtils.Instance.deepConvertMap(model);
        delegate.fillFromBody(model);
        InteropJavaToScriptUtils.Instance.convertMap(model);
    }

    /**
     * 从Params中填充数据
     *
     * @param model    数据结构以及初始值
     * @param fillNull null值是否也要填充
     */
    public void fillFromParams(Map<String, Object> model, boolean fillNull) {
        model = InteropScriptToJavaUtils.Instance.deepConvertMap(model);
        delegate.fillFromParams(model, fillNull);
        InteropJavaToScriptUtils.Instance.convertMap(model);
    }

    /**
     * 从Params中填充数据
     *
     * @param model 数据结构以及初始值
     */
    public void fillFromParams(Map<String, Object> model) {
        model = InteropScriptToJavaUtils.Instance.deepConvertMap(model);
        delegate.fillFromParams(model);
        InteropJavaToScriptUtils.Instance.convertMap(model);
    }

    /**
     * 从Body或者Params中填充数据
     *
     * @param model    数据结构以及初始值
     * @param fillNull null值是否也要填充
     */
    public void fillFromAny(Map<String, Object> model, boolean fillNull) throws IOException {
        model = InteropScriptToJavaUtils.Instance.deepConvertMap(model);
        delegate.fillFromAny(model, fillNull);
        InteropJavaToScriptUtils.Instance.convertMap(model);
    }

    /**
     * 从Body或者Params中填充数据
     *
     * @param model 数据结构以及初始值
     */
    public void fillFromAny(Map<String, Object> model) throws IOException {
        model = InteropScriptToJavaUtils.Instance.deepConvertMap(model);
        delegate.fillFromAny(model);
        InteropJavaToScriptUtils.Instance.convertMap(model);
    }

    /**
     * 从Body中填充数据然后验证数据
     *
     * @param model    数据结构以及初始值
     * @param rule     校验规则
     * @param fillNull null值是否也要填充
     * @param fast     快速验证(只要有一个错误就抛出异常)
     */
    public void fillAndValidatedFromBody(Map<String, Object> model, Map<String, Object> rule, boolean fillNull, boolean fast) throws IOException, BindException {
        model = InteropScriptToJavaUtils.Instance.deepConvertMap(model);
        fillFromBody(model, fillNull);
        ValidatorUtils.Instance.validated(model, rule, fast);
        InteropJavaToScriptUtils.Instance.convertMap(model);
    }

    /**
     * 从Body中填充数据然后验证数据
     *
     * @param model 数据结构以及初始值
     * @param rule  校验规则
     */
    public void fillAndValidatedFromBody(Map<String, Object> model, Map<String, Object> rule) throws IOException, BindException {
        model = InteropScriptToJavaUtils.Instance.deepConvertMap(model);
        fillFromBody(model);
        ValidatorUtils.Instance.validated(model, rule);
        InteropJavaToScriptUtils.Instance.convertMap(model);
    }

    /**
     * 从Params中填充数据然后验证数据
     *
     * @param model    数据结构以及初始值
     * @param rule     校验规则
     * @param fillNull null值是否也要填充
     * @param fast     快速验证(只要有一个错误就抛出异常)
     */
    public void fillAndValidatedFromParams(Map<String, Object> model, Map<String, Object> rule, boolean fillNull, boolean fast) throws BindException {
        model = InteropScriptToJavaUtils.Instance.deepConvertMap(model);
        fillFromParams(model, fillNull);
        ValidatorUtils.Instance.validated(model, rule, fast);
        InteropJavaToScriptUtils.Instance.convertMap(model);
    }

    /**
     * 从Params中填充数据然后验证数据
     *
     * @param model 数据结构以及初始值
     * @param rule  校验规则
     */
    public void fillAndValidatedFromParams(Map<String, Object> model, Map<String, Object> rule) throws BindException {
        model = InteropScriptToJavaUtils.Instance.deepConvertMap(model);
        fillFromParams(model);
        ValidatorUtils.Instance.validated(model, rule);
        InteropJavaToScriptUtils.Instance.convertMap(model);
    }

    /**
     * 从Body或者Params中填充数据然后验证数据
     *
     * @param model    数据结构以及初始值
     * @param rule     校验规则
     * @param fillNull null值是否也要填充
     * @param fast     快速验证(只要有一个错误就抛出异常)
     */
    public void fillAndValidatedFromAny(Map<String, Object> model, Map<String, Object> rule, boolean fillNull, boolean fast) throws IOException, BindException {
        model = InteropScriptToJavaUtils.Instance.deepConvertMap(model);
        fillFromAny(model, fillNull);
        ValidatorUtils.Instance.validated(model, rule, fast);
        InteropJavaToScriptUtils.Instance.convertMap(model);
    }

    /**
     * 从Body或者Params中填充数据然后验证数据
     *
     * @param model 数据结构以及初始值
     * @param rule  校验规则
     */
    public void fillAndValidatedFromAny(Map<String, Object> model, Map<String, Object> rule) throws IOException, BindException {
        model = InteropScriptToJavaUtils.Instance.deepConvertMap(model);
        fillFromAny(model);
        ValidatorUtils.Instance.validated(model, rule);
        InteropJavaToScriptUtils.Instance.convertMap(model);
    }

    /**
     * 获取上传的文件名称
     */
    public List<String> getUploadFileNames() {
        return delegate.getUploadFileNames();
    }

    /**
     * 获取上传的文件
     *
     * @param filename 文件名称
     */
    public org.clever.graaljs.spring.mvc.builtin.adapter.HttpRequestWrapper.UploadFile getUploadFile(String filename) {
        return delegate.getUploadFile(filename);
    }

    /**
     * 获取上传的文件
     *
     * @param filename 文件名称
     */
    public List<org.clever.graaljs.spring.mvc.builtin.adapter.HttpRequestWrapper.UploadFile> getUploadFiles(String filename) {
        return delegate.getUploadFiles(filename);
    }

    /**
     * 获取所有上传的文件
     */
    public MultiValueMap<String, org.clever.graaljs.spring.mvc.builtin.adapter.HttpRequestWrapper.UploadFile> getAllUploadFiles() {
        return delegate.getAllUploadFiles();
    }

    /**
     * 获取所有上传的文件
     *
     * @param filename 文件名称
     */
    public List<org.clever.graaljs.spring.mvc.builtin.adapter.HttpRequestWrapper.UploadFile> getAllUploadFiles(String filename) {
        return delegate.getAllUploadFiles(filename);
    }

    /**
     * 获取第一个上传的文件
     */
    public org.clever.graaljs.spring.mvc.builtin.adapter.HttpRequestWrapper.UploadFile getFirstUploadFile() {
        return delegate.getFirstUploadFile();
    }
}
