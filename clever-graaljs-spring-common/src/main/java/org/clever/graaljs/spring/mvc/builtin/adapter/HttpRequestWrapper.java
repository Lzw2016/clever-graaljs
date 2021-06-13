package org.clever.graaljs.spring.mvc.builtin.adapter;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.clever.graaljs.core.internal.jackson.JacksonMapperSupport;
import org.clever.graaljs.spring.core.builtin.adapter.ValidatorUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.*;

/**
 * 作者：lizw <br/>
 * 创建时间：2019/09/20 17:27 <br/>
 */
public class HttpRequestWrapper {
    final String ParamName_OrderFields = "orderFields";
    final String ParamName_Sorts = "sorts";
    final String ParamName_OrderField = "orderField";
    final String ParamName_Sort = "sort";
    final String ParamName_FieldsMapping = "fieldsMapping";

    final String ParamName_PageSize = "pageSize";
    final String ParamName_PageNo = "pageNo";
    final String ParamName_IsSearchCount = "isSearchCount";

    private final ConversionService conversionService;
    protected HttpContext httpContext;
    private final HttpServletRequest delegate;

    /**
     * 缓存 body Data Map
     */
    protected Map<String, Object> bodyMap;

    // public HttpRequestWrapper(HttpContext httpContext) {
    //     Assert.notNull(httpContext, "参数httpContext不能为空");
    //     this.httpContext = httpContext;
    //     this.delegate = httpContext.request.delegate;
    // }

    protected HttpRequestWrapper(HttpServletRequest request, ConversionService conversionService) {
        Assert.notNull(request, "参数request不能为空");
        this.delegate = request;
        if (conversionService == null) {
            conversionService = new DefaultFormattingConversionService();
        }
        this.conversionService = conversionService;
    }

    protected HttpRequestWrapper(HttpServletRequest request) {
        this(request, null);
    }

    /**
     * 原始HTTP请求对象
     */
    public HttpServletRequest originalRequest() {
        return delegate;
    }

    /**
     * 返回一个包含所有的绑定到请求的对象名称的字符串对象的集合
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
        List<Locale> list = new ArrayList<>();
        Enumeration<Locale> enumeration = delegate.getLocales();
        while (enumeration.hasMoreElements()) {
            list.add(enumeration.nextElement());
        }
        return list;
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
        List<String> list = new ArrayList<>();
        Enumeration<String> enumeration = delegate.getParameterNames();
        while (enumeration.hasMoreElements()) {
            list.add(enumeration.nextElement());
        }
        return list;
    }

    /**
     * 返回一个包含所有的给定请求参数的值的字符串对象的向量。若该参数不存在，则返回一个空值
     */
    public String[] getParameterValues(String name) {
        String[] parameters = delegate.getParameterValues(name);
        if (parameters == null) {
            parameters = new String[0];
        }
        return parameters;
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
        HttpSession session = delegate.getSession();
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
        List<String> list = new ArrayList<>();
        Enumeration<String> enumeration = delegate.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            list.add(enumeration.nextElement());
        }
        return list;
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
        List<String> list = new ArrayList<>();
        Enumeration<String> enumeration = delegate.getHeaders(name);
        while (enumeration.hasMoreElements()) {
            list.add(enumeration.nextElement());
        }
        return list;
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
    @SuppressWarnings("DuplicatedCode")
    @SneakyThrows
    public Map<String, Object> getQueryBySort() {
        Map<String, String[]> paramMap = delegate.getParameterMap();
        Map<String, Object> requestData = this.getRequestData();
        Map<String, Object> queryBySort = new HashMap<>(5);
        // 高优先级排序字段
        String[] orderFields = paramMap.get(ParamName_OrderFields);
        if (orderFields != null) {
            queryBySort.put(ParamName_OrderFields, orderFields);
        } else {
            Object obj = requestData.get(ParamName_OrderFields);
            if (obj instanceof List) {
                queryBySort.put(ParamName_OrderFields, ((List<?>) obj).toArray());
            }
        }
        String[] sorts = paramMap.get(ParamName_Sorts);
        if (sorts != null) {
            queryBySort.put(ParamName_Sorts, sorts);
        } else {
            Object obj = requestData.get(ParamName_Sorts);
            if (obj instanceof List) {
                queryBySort.put(ParamName_Sorts, ((List<?>) obj).toArray());
            }
        }
        // 低优先级排序字段
        String[] orderField = paramMap.get(ParamName_OrderField);
        if (orderField != null && orderField.length > 0) {
            queryBySort.put(ParamName_OrderField, orderField[0]);
        } else {
            Object obj = requestData.get(ParamName_OrderField);
            if (obj != null) {
                queryBySort.put(ParamName_OrderField, String.valueOf(obj));
            }
        }
        String[] sort = paramMap.get(ParamName_Sort);
        if (sort != null && sort.length > 0) {
            queryBySort.put(ParamName_Sort, sort[0]);
        } else {
            Object obj = requestData.get(ParamName_Sort);
            if (obj != null) {
                queryBySort.put(ParamName_Sort, String.valueOf(obj));
            }
        }
        // 排序字段 映射Map
        queryBySort.put(ParamName_FieldsMapping, new HashMap<>());
        return queryBySort;
    }

    /**
     * 获取数据库分页查询参数
     */
    @SneakyThrows
    public Map<String, Object> getQueryByPage() {
        Map<String, String[]> paramMap = delegate.getParameterMap();
        Map<String, Object> requestData = this.getRequestData();
        Map<String, Object> queryByPage = new HashMap<>(8);
        queryByPage.putAll(getQueryBySort());
        // 每页的数据量(1 <= pageSize <= 100)
        String[] pageSize = paramMap.get(ParamName_PageSize);
        if (pageSize != null && pageSize.length > 0) {
            queryByPage.put(ParamName_PageSize, NumberUtils.toInt(pageSize[0], 10));
        } else {
            Object obj = requestData.get(ParamName_PageSize);
            if (obj != null) {
                queryByPage.put(ParamName_PageSize, NumberUtils.toInt(String.valueOf(obj), 10));
            }
        }
        // 当前页面的页码数(pageNo >= 1)
        String[] pageNo = paramMap.get(ParamName_PageNo);
        if (pageNo != null && pageNo.length > 0) {
            queryByPage.put(ParamName_PageNo, NumberUtils.toInt(pageNo[0], 1));
        } else {
            Object obj = requestData.get(ParamName_PageNo);
            if (obj != null) {
                queryByPage.put(ParamName_PageNo, NumberUtils.toInt(String.valueOf(obj), 1));
            }
        }
        // 当前页面的页码数(pageNo >= 1)
        String[] isSearchCount = paramMap.get(ParamName_IsSearchCount);
        if (isSearchCount != null && isSearchCount.length > 0) {
            queryByPage.put(ParamName_IsSearchCount, BooleanUtils.toBoolean(isSearchCount[0]));
        } else {
            Object obj = requestData.get(ParamName_IsSearchCount);
            if (obj != null) {
                queryByPage.put(ParamName_IsSearchCount, BooleanUtils.toBoolean(String.valueOf(obj)));
            }
        }
        return queryByPage;
    }

    /**
     * 获取参数对象
     */
    public Map<String, Object> getParams() {
        Map<String, String[]> params = delegate.getParameterMap();
        Map<String, Object> result = new HashMap<>(params.size());
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            String key = entry.getKey();
            String[] values = entry.getValue();
            if (values != null && values.length >= 1) {
                result.put(key, values[0]);
            } else {
                result.put(key, null);
            }
        }
        return result;
    }

    /**
     * 获取Body对象
     */
    public Map<String, Object> getBody() throws IOException {
        return getBodyMap();
    }

    /**
     * 获取参数Map
     */
    public MultiValueMap<String, String> getParamsMap() {
        Map<String, String[]> map = delegate.getParameterMap();
        MultiValueMap<String, String> result = new LinkedMultiValueMap<>();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            String key = entry.getKey();
            String[] values = entry.getValue();
            if (values != null && values.length > 0) {
                for (String value : values) {
                    result.add(key, value);
                }
            } else {
                result.add(key, null);
            }
        }
        return result;
    }

    /**
     * 获取请求参数(Parameter和Body都会取)
     */
    public Map<String, Object> getRequestData() throws IOException {
        Map<String, Object> params = getParams();
        Map<String, Object> body = getBody();
        Map<String, Object> data = new HashMap<>(params.size() + body.size());
        data.putAll(params);
        for (Map.Entry<String, Object> entry : body.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (data.containsKey(key)) {
                throw new IllegalArgumentException("参数字段:" + key + "值冲突，params或body中都存在字段:" + key);
            }
            data.put(key, value);
        }
        return data;
    }

    /**
     * 从Body中填充数据
     *
     * @param model    数据结构以及初始值
     * @param fillNull null值是否也要填充
     */
    public void fillFromBody(Map<String, Object> model, boolean fillNull) throws IOException {
        try {
            Map<String, Object> bodyMap = getBodyMap();
            fillData(model, bodyMap, fillNull, 1);
        } catch (Exception e) {
            if (e instanceof HttpMessageConversionException) {
                throw e;
            }
            throw new HttpMessageConversionException("请求body数据转换失败", e);
        }
    }

    /**
     * 从Body中填充数据
     *
     * @param model 数据结构以及初始值
     */
    public void fillFromBody(Map<String, Object> model) throws IOException {
        fillFromBody(model, true);
    }

    /**
     * 从Params中填充数据
     *
     * @param model    数据结构以及初始值
     * @param fillNull null值是否也要填充
     */
    public void fillFromParams(Map<String, Object> model, boolean fillNull) {
        Map<String, String[]> params = delegate.getParameterMap();
        try {
            fillData(model, params, fillNull, 1);
        } catch (Exception e) {
            if (e instanceof HttpMessageConversionException) {
                throw e;
            }
            throw new HttpMessageConversionException("请求params数据转换失败", e);
        }
    }

    /**
     * 从Params中填充数据
     *
     * @param model 数据结构以及初始值
     */
    public void fillFromParams(Map<String, Object> model) {
        fillFromParams(model, true);
    }

    /**
     * 从Body或者Params中填充数据
     *
     * @param model    数据结构以及初始值
     * @param fillNull null值是否也要填充
     */
    public void fillFromAny(Map<String, Object> model, boolean fillNull) throws IOException {
        try {
            Map<String, String[]> params = delegate.getParameterMap();
            Map<String, Object> bodyMap = getBodyMap();
            Map<String, Object> fillMap = new HashMap<>(params.size() + bodyMap.size());
            fillMap.putAll(params);
            for (Map.Entry<String, Object> entry : bodyMap.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (fillMap.containsKey(key) && model.containsKey(key)) {
                    throw new IllegalArgumentException("参数字段:" + key + "值冲突，params或body中都存在字段:" + key);
                }
                fillMap.put(key, value);
            }
            fillData(model, fillMap, fillNull, 1);
        } catch (Exception e) {
            if (e instanceof HttpMessageConversionException) {
                throw e;
            }
            throw new HttpMessageConversionException("请求params或body数据转换失败", e);
        }
    }

    /**
     * 从Body或者Params中填充数据
     *
     * @param model 数据结构以及初始值
     */
    public void fillFromAny(Map<String, Object> model) throws IOException {
        fillFromAny(model, true);
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
        fillFromBody(model, fillNull);
        ValidatorUtils.Instance.validated(model, rule, fast);
    }

    /**
     * 从Body中填充数据然后验证数据
     *
     * @param model 数据结构以及初始值
     * @param rule  校验规则
     */
    public void fillAndValidatedFromBody(Map<String, Object> model, Map<String, Object> rule) throws IOException, BindException {
        fillAndValidatedFromBody(model, rule, true, false);
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
        fillFromParams(model, fillNull);
        ValidatorUtils.Instance.validated(model, rule, fast);
    }

    /**
     * 从Params中填充数据然后验证数据
     *
     * @param model 数据结构以及初始值
     * @param rule  校验规则
     */
    public void fillAndValidatedFromParams(Map<String, Object> model, Map<String, Object> rule) throws BindException {
        fillAndValidatedFromParams(model, rule, true, false);
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
        fillFromAny(model, fillNull);
        ValidatorUtils.Instance.validated(model, rule, fast);
    }

    /**
     * 从Body或者Params中填充数据然后验证数据
     *
     * @param model 数据结构以及初始值
     * @param rule  校验规则
     */
    public void fillAndValidatedFromAny(Map<String, Object> model, Map<String, Object> rule) throws IOException, BindException {
        fillAndValidatedFromAny(model, rule, true, false);
    }

    /**
     * 获取上传的文件名称
     */
    public List<String> getUploadFileNames() {
        Assert.isTrue(delegate instanceof MultipartHttpServletRequest, "当前请求并非上传文件的请求");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) delegate;
        List<String> fileNames = new ArrayList<>();
        Iterator<String> names = multipartRequest.getFileNames();
        while (names.hasNext()) {
            fileNames.add(names.next());
        }
        return fileNames;
    }

    /**
     * 获取上传的文件
     *
     * @param filename 文件名称
     */
    public UploadFile getUploadFile(String filename) {
        Assert.isTrue(delegate instanceof MultipartHttpServletRequest, "当前请求并非上传文件的请求");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) delegate;
        MultipartFile file = multipartRequest.getFile(filename);
        if (file == null) {
            return null;
        }
        return new UploadFile(file);
    }

    /**
     * 获取上传的文件
     *
     * @param filename 文件名称
     */
    public List<UploadFile> getUploadFiles(String filename) {
        Assert.isTrue(delegate instanceof MultipartHttpServletRequest, "当前请求并非上传文件的请求");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) delegate;
        List<MultipartFile> files = multipartRequest.getFiles(filename);
        List<UploadFile> uploadFiles = new ArrayList<>(files.size());
        for (MultipartFile file : files) {
            uploadFiles.add(new UploadFile(file));
        }
        return uploadFiles;
    }

    /**
     * 获取所有上传的文件
     */
    public MultiValueMap<String, UploadFile> getAllUploadFiles() {
        Assert.isTrue(delegate instanceof MultipartHttpServletRequest, "当前请求并非上传文件的请求");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) delegate;
        MultiValueMap<String, MultipartFile> fileMap = multipartRequest.getMultiFileMap();
        MultiValueMap<String, UploadFile> multiFiles = new LinkedMultiValueMap<>(fileMap.size());
        for (Map.Entry<String, List<MultipartFile>> entry : fileMap.entrySet()) {
            String name = entry.getKey();
            List<MultipartFile> files = entry.getValue();
            List<UploadFile> uploadFiles = new ArrayList<>(files == null ? 0 : files.size());
            if (files != null) {
                for (MultipartFile file : files) {
                    uploadFiles.add(new UploadFile(file));
                }
            }
            multiFiles.put(name, uploadFiles);
        }
        return multiFiles;
    }

    /**
     * 获取所有上传的文件
     *
     * @param filename 文件名称
     */
    public List<UploadFile> getAllUploadFiles(String filename) {
        MultiValueMap<String, UploadFile> allFiles = getAllUploadFiles();
        List<UploadFile> allUploadFiles = new ArrayList<>(allFiles.size());
        for (List<UploadFile> files : allFiles.values()) {
            allUploadFiles.addAll(files);
        }
        return allUploadFiles;
    }

    /**
     * 获取第一个上传的文件
     */
    public UploadFile getFirstUploadFile() {
        Assert.isTrue(delegate instanceof MultipartHttpServletRequest, "当前请求并非上传文件的请求");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) delegate;
        MultiValueMap<String, MultipartFile> fileMap = multipartRequest.getMultiFileMap();
        for (Map.Entry<String, List<MultipartFile>> entry : fileMap.entrySet()) {
            List<MultipartFile> files = entry.getValue();
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    return new UploadFile(file);
                }
            }
        }
        return null;
    }

    /**
     * 获取Body数据
     */
    @SuppressWarnings("unchecked")
    protected Map<String, Object> getBodyMap() throws IOException {
        if (bodyMap != null) {
            return bodyMap;
        }
        bodyMap = new HashMap<>();
        try {
            delegate.getReader().reset();
        } catch (Exception ignored) {
        }
        final String body = StringUtils.trim(IOUtils.toString(delegate.getReader()));
        // body 内容为空
        if (StringUtils.isBlank(body)) {
            return bodyMap;
        }
        // body 不是JSON格式
        if (!body.startsWith("{") || !body.endsWith("}")) {
            return bodyMap;
        }
        // 反序列化
        bodyMap = (Map<String, Object>) JacksonMapperSupport.getHttpApiJacksonMapper().fromJson(body, LinkedHashMap.class);
        return bodyMap;
    }

    /**
     * 填充数据
     *
     * @param model       数据结构以及初始值
     * @param data        填充数据
     * @param fillNull    null值是否也要填充
     * @param currentDeep 当前填充深度(从1开始)
     */
    @SuppressWarnings("unchecked")
    protected void fillData(final Map<String, Object> model, final Map<String, ?> data, final boolean fillNull, final int currentDeep) {
        if (model.size() <= 0) {
            model.putAll(data);
            return;
        }
        for (Map.Entry<String, Object> entry : model.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            Object dataValue = data.get(key);
            // 填充数据为空
            if (dataValue == null) {
                if (fillNull) {
                    model.put(key, null);
                }
                continue;
            }
            // 如果是dataValue是Parameter中的值，且数组长度为1，者转换成String
            if (currentDeep == 1 && delegate.getParameterValues(key) != null && dataValue instanceof String[] && ((String[]) dataValue).length <= 1) {
                dataValue = ((String[]) dataValue)[0];
            }
            // 数据结构值为空
            if (value == null) {
                model.put(key, dataValue);
                continue;
            }
            // 数据结构值为“Map”
            if (value instanceof Map) {
                if (dataValue instanceof Map) {
                    int nextDeep = currentDeep + 1;
                    fillData((Map<String, Object>) value, (Map<String, Object>) dataValue, fillNull, nextDeep);
                } else {
                    model.put(key, dataValue);
                }
                continue;
            }
            // 数据结构值为“数组”
            if (value.getClass().isArray()) {
                model.put(key, conversionService.convert(dataValue, value.getClass()));
                continue;
            }
            // 数据结构值为“集合”
            if (value instanceof Collection) {
                model.put(key, conversionService.convert(dataValue, value.getClass()));
                continue;
            }
            // 数据结构值为“基础类型” (byte | short | int | long | float | double | char | boolean | String)
            if (value instanceof Number
                    || value instanceof CharSequence
                    || value instanceof Character
                    || value instanceof Boolean) {
                model.put(key, conversionService.convert(dataValue, value.getClass()));
                continue;
            }
            // 数据结构值为“其他类型”
            model.put(key, conversionService.convert(dataValue, value.getClass()));
        }
    }

    public static class UploadFile {
        private final MultipartFile multipartFile;

        public UploadFile(MultipartFile multipartFile) {
            this.multipartFile = multipartFile;
        }

        /**
         * 参数的名称
         */
        public String getName() {
            return multipartFile.getName();
        }

        /**
         * 原始文件名，如果在多部分形式中未选择文件，则为空字符串；如果未定义或不可用，则为空字符串
         */
        public String getOriginalFilename() {
            return multipartFile.getOriginalFilename();
        }

        /**
         * 内容类型，如果未定义，则为空（或在多部分形式中未选择文件）
         */
        public String getContentType() {
            return multipartFile.getContentType();
        }

        /**
         * 返回上传的文件是否为空，即没有在多部分表单中选择文件或所选文件没有内容
         */
        public boolean isEmpty() {
            return multipartFile.isEmpty();
        }

        /**
         * 文件的大小，如果为空，则为0
         */
        public long getSize() {
            return multipartFile.getSize();
        }

        /**
         * 文件的内容为字节，如果为空，则为空字节数组
         */
        public byte[] getBytes() throws IOException {
            return multipartFile.getBytes();
        }

        /**
         * 文件的内容为流，如果为空，则为空流
         */
        public InputStream getInputStream() throws IOException {
            return multipartFile.getInputStream();
        }

        /**
         * 将接收到的文件传输到给定的目标文件
         */
        public void transferTo(String filePath) throws IOException, IllegalStateException {
            multipartFile.transferTo(new File(filePath));
        }
    }
}
