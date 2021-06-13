package org.clever.graaljs.core.builtin.adapter;

import lombok.Data;
import okhttp3.*;
import okhttp3.internal.http.HttpMethod;
import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.core.utils.Assert;
import org.clever.graaljs.core.utils.ExceptionUtils;
import org.clever.graaljs.core.utils.mapper.JacksonMapper;
import org.clever.graaljs.core.utils.support.LinkedMultiValueMap;
import org.clever.graaljs.core.utils.support.MultiValueMap;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/28 22:32 <br/>
 */
public class HttpUtils {
    public static final HttpUtils Instance = new HttpUtils();

    private final org.clever.graaljs.core.utils.HttpUtils delegate;

    private HttpUtils() {
        delegate = org.clever.graaljs.core.utils.HttpUtils.getInner();
    }

    // GET请求
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 使用HTTP GET请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    public String getStr(final String url, final Map<String, String> params, final Map<String, String> headers) {
        return delegate.getStr(url, headers, params);
    }

    /**
     * 使用HTTP GET请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public String getStr(final String url, Map<String, String> params) {
        return delegate.getStr(url, params);
    }

    /**
     * 使用HTTP GET请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public String getStr(final String url) {
        return delegate.getStr(url);
    }

    /**
     * 使用HTTP GET请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getMap(final String url, final Map<String, String> params, final Map<String, String> headers) {
        String body = delegate.getStr(url, headers, params);
        return JacksonMapper.getInstance().fromJson(body, LinkedHashMap.class);
    }

    /**
     * 使用HTTP GET请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getMap(final String url, Map<String, String> params) {
        String body = delegate.getStr(url, params);
        return JacksonMapper.getInstance().fromJson(body, LinkedHashMap.class);
    }

    /**
     * 使用HTTP GET请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getMap(final String url) {
        String body = delegate.getStr(url);
        return JacksonMapper.getInstance().fromJson(body, LinkedHashMap.class);
    }

    // POST请求
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param body    Json Body(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    public String postStr(final String url, final Object body, final Map<String, String> params, final Map<String, String> headers) {
        String json = getJsonBody(body);
        return delegate.postStr(url, headers, params, json);
    }

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    public String postStr(final String url, final Object body, final Map<String, String> params) {
        String json = getJsonBody(body);
        return delegate.postStr(url, params, json);
    }


    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    public String postStr(final String url, final Object body) {
        String json = getJsonBody(body);
        return delegate.postStr(url, json);
    }

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public String postStr(final String url, final Map<String, String> params) {
        String json = getJsonBody(null);
        return delegate.postStr(url, params, json);
    }

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public String postStr(final String url) {
        String json = getJsonBody(null);
        return delegate.postStr(url, json);
    }

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param body    Json Body(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> postMap(final String url, final Object body, final Map<String, String> params, final Map<String, String> headers) {
        String json = getJsonBody(body);
        String res = delegate.postStr(url, headers, params, json);
        return JacksonMapper.getInstance().fromJson(res, LinkedHashMap.class);
    }

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> postMap(final String url, final Object body, final Map<String, String> params) {
        String json = getJsonBody(body);
        String res = delegate.postStr(url, params, json);
        return JacksonMapper.getInstance().fromJson(res, LinkedHashMap.class);
    }

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> postMap(final String url, final Object body) {
        String json = getJsonBody(body);
        String res = delegate.postStr(url, json);
        return JacksonMapper.getInstance().fromJson(res, LinkedHashMap.class);
    }

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> postMap(final String url, final Map<String, String> params) {
        String json = getJsonBody(null);
        String res = delegate.postStr(url, params, json);
        return JacksonMapper.getInstance().fromJson(res, LinkedHashMap.class);
    }

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> postMap(final String url) {
        String json = getJsonBody(null);
        String res = delegate.postStr(url, json);
        return JacksonMapper.getInstance().fromJson(res, LinkedHashMap.class);
    }

    // DELETE请求
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 使用HTTP DELETE请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param body    Json Body(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    public String deleteStr(final String url, final Object body, final Map<String, String> params, final Map<String, String> headers) {
        HttpResponse httpResponse = execRequest("DELETE", url, body, params, headers);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP DELETE请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    public String deleteStr(final String url, final Object body, final Map<String, String> params) {
        HttpResponse httpResponse = execRequest("DELETE", url, body, params, null);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP DELETE请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    public String deleteStr(final String url, final Object body) {
        HttpResponse httpResponse = execRequest("DELETE", url, body, null, null);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP DELETE请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public String deleteStr(final String url, final Map<String, String> params) {
        HttpResponse httpResponse = execRequest("DELETE", url, null, params, null);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP DELETE请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public String deleteStr(final String url) {
        HttpResponse httpResponse = execRequest("DELETE", url, null, null, null);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP DELETE请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param body    Json Body(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    public Map<String, Object> deleteMap(final String url, final Object body, final Map<String, String> params, final Map<String, String> headers) {
        HttpResponse httpResponse = execRequest("DELETE", url, body, params, headers);
        return httpResponse.getBodyMap();
    }

    /**
     * 使用HTTP DELETE请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    public Map<String, Object> deleteMap(final String url, final Object body, final Map<String, String> params) {
        HttpResponse httpResponse = execRequest("DELETE", url, body, params, null);
        return httpResponse.getBodyMap();
    }

    /**
     * 使用HTTP DELETE请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    public Map<String, Object> deleteMap(final String url, final Object body) {
        HttpResponse httpResponse = execRequest("DELETE", url, body, null, null);
        return httpResponse.getBodyMap();
    }

    /**
     * 使用HTTP DELETE请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public Map<String, Object> deleteMap(final String url, final Map<String, String> params) {
        HttpResponse httpResponse = execRequest("DELETE", url, null, params, null);
        return httpResponse.getBodyMap();
    }

    /**
     * 使用HTTP DELETE请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public Map<String, Object> deleteMap(final String url) {
        HttpResponse httpResponse = execRequest("DELETE", url, null, null, null);
        return httpResponse.getBodyMap();
    }

    // PUT请求
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 使用HTTP PUT请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param body    Json Body(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    public String putStr(final String url, final Object body, final Map<String, String> params, final Map<String, String> headers) {
        HttpResponse httpResponse = execRequest("PUT", url, body, params, headers);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP PUT请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    public String putStr(final String url, final Object body, final Map<String, String> params) {
        HttpResponse httpResponse = execRequest("PUT", url, body, params, null);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP PUT请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    public String putStr(final String url, final Object body) {
        HttpResponse httpResponse = execRequest("PUT", url, body, null, null);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP PUT请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public String putStr(final String url, final Map<String, String> params) {
        HttpResponse httpResponse = execRequest("PUT", url, null, params, null);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP PUT请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public String putStr(final String url) {
        HttpResponse httpResponse = execRequest("PUT", url, null, null, null);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP PUT请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param body    Json Body(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    public Map<String, Object> putMap(final String url, final Object body, final Map<String, String> params, final Map<String, String> headers) {
        HttpResponse httpResponse = execRequest("PUT", url, body, params, headers);
        return httpResponse.getBodyMap();
    }

    /**
     * 使用HTTP PUT请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    public Map<String, Object> putMap(final String url, final Object body, final Map<String, String> params) {
        HttpResponse httpResponse = execRequest("PUT", url, body, params, null);
        return httpResponse.getBodyMap();
    }

    /**
     * 使用HTTP PUT请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    public Map<String, Object> putMap(final String url, final Object body) {
        HttpResponse httpResponse = execRequest("PUT", url, body, null, null);
        return httpResponse.getBodyMap();
    }

    /**
     * 使用HTTP PUT请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public Map<String, Object> putMap(final String url, final Map<String, String> params) {
        HttpResponse httpResponse = execRequest("PUT", url, null, params, null);
        return httpResponse.getBodyMap();
    }

    /**
     * 使用HTTP PUT请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public Map<String, Object> putMap(final String url) {
        HttpResponse httpResponse = execRequest("PUT", url, null, null, null);
        return httpResponse.getBodyMap();
    }

    // HEAD请求
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 使用HTTP HEAD请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    public String headStr(final String url, final Map<String, String> params, final Map<String, String> headers) {
        HttpResponse httpResponse = execRequest("HEAD", url, null, params, headers);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP HEAD请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public String headStr(final String url, final Map<String, String> params) {
        HttpResponse httpResponse = execRequest("HEAD", url, null, params, null);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP HEAD请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public String headStr(final String url) {
        HttpResponse httpResponse = execRequest("HEAD", url, null, null, null);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP HEAD请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    public Map<String, Object> headMap(final String url, final Map<String, String> params, final Map<String, String> headers) {
        HttpResponse httpResponse = execRequest("HEAD", url, null, params, headers);
        return httpResponse.getBodyMap();
    }

    /**
     * 使用HTTP HEAD请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public Map<String, Object> headMap(final String url, final Map<String, String> params) {
        HttpResponse httpResponse = execRequest("HEAD", url, null, params, null);
        return httpResponse.getBodyMap();
    }

    /**
     * 使用HTTP HEAD请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public Map<String, Object> headMap(final String url) {
        HttpResponse httpResponse = execRequest("HEAD", url, null, null, null);
        return httpResponse.getBodyMap();
    }

    // OPTIONS请求
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 使用HTTP OPTIONS请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param body    Json Body(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    public String optionsStr(final String url, final Object body, final Map<String, String> params, final Map<String, String> headers) {
        HttpResponse httpResponse = execRequest("OPTIONS", url, body, params, headers);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP OPTIONS请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    public String optionsStr(final String url, final Object body, final Map<String, String> params) {
        HttpResponse httpResponse = execRequest("OPTIONS", url, body, params, null);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP OPTIONS请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    public String optionsStr(final String url, final Object body) {
        HttpResponse httpResponse = execRequest("OPTIONS", url, body, null, null);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP OPTIONS请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public String optionsStr(final String url, final Map<String, String> params) {
        HttpResponse httpResponse = execRequest("OPTIONS", url, null, params, null);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP OPTIONS请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public String optionsStr(final String url) {
        HttpResponse httpResponse = execRequest("OPTIONS", url, null, null, null);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP OPTIONS请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param body    Json Body(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    public Map<String, Object> optionsMap(final String url, final Object body, final Map<String, String> params, final Map<String, String> headers) {
        HttpResponse httpResponse = execRequest("OPTIONS", url, body, params, headers);
        return httpResponse.getBodyMap();
    }

    /**
     * 使用HTTP OPTIONS请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    public Map<String, Object> optionsMap(final String url, final Object body, final Map<String, String> params) {
        HttpResponse httpResponse = execRequest("OPTIONS", url, body, params, null);
        return httpResponse.getBodyMap();
    }

    /**
     * 使用HTTP OPTIONS请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    public Map<String, Object> optionsMap(final String url, final Object body) {
        HttpResponse httpResponse = execRequest("OPTIONS", url, body, null, null);
        return httpResponse.getBodyMap();
    }

    /**
     * 使用HTTP OPTIONS请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public Map<String, Object> optionsMap(final String url, final Map<String, String> params) {
        HttpResponse httpResponse = execRequest("OPTIONS", url, null, params, null);
        return httpResponse.getBodyMap();
    }

    /**
     * 使用HTTP OPTIONS请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public Map<String, Object> optionsMap(final String url) {
        HttpResponse httpResponse = execRequest("OPTIONS", url, null, null, null);
        return httpResponse.getBodyMap();
    }

    // PATCH请求
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 使用HTTP PATCH请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param body    Json Body(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    public String patchStr(final String url, final Object body, final Map<String, String> params, final Map<String, String> headers) {
        HttpResponse httpResponse = execRequest("PATCH", url, body, params, headers);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP PATCH请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    public String patchStr(final String url, final Object body, final Map<String, String> params) {
        HttpResponse httpResponse = execRequest("PATCH", url, body, params, null);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP PATCH请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    public String patchStr(final String url, final Object body) {
        HttpResponse httpResponse = execRequest("PATCH", url, body, null, null);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP PATCH请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public String patchStr(final String url, final Map<String, String> params) {
        HttpResponse httpResponse = execRequest("PATCH", url, null, params, null);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP PATCH请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public String patchStr(final String url) {
        HttpResponse httpResponse = execRequest("PATCH", url, null, null, null);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP PATCH请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param body    Json Body(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    public Map<String, Object> patchMap(final String url, final Object body, final Map<String, String> params, final Map<String, String> headers) {
        HttpResponse httpResponse = execRequest("PATCH", url, body, params, headers);
        return httpResponse.getBodyMap();
    }

    /**
     * 使用HTTP PATCH请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    public Map<String, Object> patchMap(final String url, final Object body, final Map<String, String> params) {
        HttpResponse httpResponse = execRequest("PATCH", url, body, params, null);
        return httpResponse.getBodyMap();
    }

    /**
     * 使用HTTP PATCH请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    public Map<String, Object> patchMap(final String url, final Object body) {
        HttpResponse httpResponse = execRequest("PATCH", url, body, null, null);
        return httpResponse.getBodyMap();
    }

    /**
     * 使用HTTP PATCH请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public Map<String, Object> patchMap(final String url, final Map<String, String> params) {
        HttpResponse httpResponse = execRequest("PATCH", url, null, params, null);
        return httpResponse.getBodyMap();
    }

    /**
     * 使用HTTP PATCH请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public Map<String, Object> patchMap(final String url) {
        HttpResponse httpResponse = execRequest("PATCH", url, null, null, null);
        return httpResponse.getBodyMap();
    }

    // TRACE请求
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 使用HTTP TRACE请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param body    Json Body(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    public String traceStr(final String url, final Object body, final Map<String, String> params, final Map<String, String> headers) {
        HttpResponse httpResponse = execRequest("TRACE", url, body, params, headers);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP TRACE请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    public String traceStr(final String url, final Object body, final Map<String, String> params) {
        HttpResponse httpResponse = execRequest("TRACE", url, body, params, null);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP TRACE请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    public String traceStr(final String url, final Object body) {
        HttpResponse httpResponse = execRequest("TRACE", url, body, null, null);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP TRACE请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public String traceStr(final String url, final Map<String, String> params) {
        HttpResponse httpResponse = execRequest("TRACE", url, null, params, null);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP TRACE请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public String traceStr(final String url) {
        HttpResponse httpResponse = execRequest("TRACE", url, null, null, null);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP TRACE请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param body    Json Body(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    public Map<String, Object> traceMap(final String url, final Object body, final Map<String, String> params, final Map<String, String> headers) {
        HttpResponse httpResponse = execRequest("TRACE", url, body, params, headers);
        return httpResponse.getBodyMap();
    }

    /**
     * 使用HTTP TRACE请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    public Map<String, Object> traceMap(final String url, final Object body, final Map<String, String> params) {
        HttpResponse httpResponse = execRequest("TRACE", url, body, params, null);
        return httpResponse.getBodyMap();
    }

    /**
     * 使用HTTP TRACE请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    public Map<String, Object> traceMap(final String url, final Object body) {
        HttpResponse httpResponse = execRequest("TRACE", url, body, null, null);
        return httpResponse.getBodyMap();
    }

    /**
     * 使用HTTP TRACE请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public Map<String, Object> traceMap(final String url, final Map<String, String> params) {
        HttpResponse httpResponse = execRequest("TRACE", url, null, params, null);
        return httpResponse.getBodyMap();
    }

    /**
     * 使用HTTP TRACE请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public Map<String, Object> traceMap(final String url) {
        HttpResponse httpResponse = execRequest("TRACE", url, null, null, null);
        return httpResponse.getBodyMap();
    }

    // CONNECT请求
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 使用HTTP CONNECT请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param body    Json Body(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    public String connectStr(final String url, final Object body, final Map<String, String> params, final Map<String, String> headers) {
        HttpResponse httpResponse = execRequest("CONNECT", url, body, params, headers);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP CONNECT请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    public String connectStr(final String url, final Object body, final Map<String, String> params) {
        HttpResponse httpResponse = execRequest("CONNECT", url, body, params, null);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP CONNECT请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    public String connectStr(final String url, final Object body) {
        HttpResponse httpResponse = execRequest("CONNECT", url, body, null, null);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP CONNECT请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public String connectStr(final String url, final Map<String, String> params) {
        HttpResponse httpResponse = execRequest("CONNECT", url, null, params, null);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP CONNECT请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public String connectStr(final String url) {
        HttpResponse httpResponse = execRequest("CONNECT", url, null, null, null);
        return httpResponse.getBody();
    }

    /**
     * 使用HTTP CONNECT请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param body    Json Body(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    public Map<String, Object> connectMap(final String url, final Object body, final Map<String, String> params, final Map<String, String> headers) {
        HttpResponse httpResponse = execRequest("CONNECT", url, body, params, headers);
        return httpResponse.getBodyMap();
    }

    /**
     * 使用HTTP CONNECT请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    public Map<String, Object> connectMap(final String url, final Object body, final Map<String, String> params) {
        HttpResponse httpResponse = execRequest("CONNECT", url, body, params, null);
        return httpResponse.getBodyMap();
    }

    /**
     * 使用HTTP CONNECT请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    public Map<String, Object> connectMap(final String url, final Object body) {
        HttpResponse httpResponse = execRequest("CONNECT", url, body, null, null);
        return httpResponse.getBodyMap();
    }

    /**
     * 使用HTTP CONNECT请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public Map<String, Object> connectMap(final String url, final Map<String, String> params) {
        HttpResponse httpResponse = execRequest("CONNECT", url, null, params, null);
        return httpResponse.getBodyMap();
    }

    /**
     * 使用HTTP CONNECT请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public Map<String, Object> connectMap(final String url) {
        HttpResponse httpResponse = execRequest("CONNECT", url, null, null, null);
        return httpResponse.getBodyMap();
    }

    // 自定义请求
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 执行HTTP请求
     *
     * @param method  HTTP请求方法 GET | POST | DELETE | PUT | HEAD | OPTIONS | PATCH | TRACE | CONNECT | MOVE | PROPPATCH | REPORT
     * @param url     请求地址
     * @param body    请求Body
     * @param params  请求参数
     * @param headers 请求头
     */
    public HttpResponse execRequest(final String method, String url, final Object body, final Map<String, String> params, final Map<String, String> headers) {
        Assert.hasText(method, "参数method不能为空");
        final Request.Builder builder = org.clever.graaljs.core.utils.HttpUtils.createRequestBuilder(url, headers, params);
        final String json = getJsonBody(body);
        RequestBody requestBody = null;
        if (HttpMethod.requiresRequestBody(method)) {
            requestBody = RequestBody.create(json, MediaType.parse(org.clever.graaljs.core.utils.HttpUtils.MediaType_Json));
        } else if (body != null) {
            requestBody = RequestBody.create(json, MediaType.parse(org.clever.graaljs.core.utils.HttpUtils.MediaType_Json));
        }
        builder.method(method.toUpperCase(), requestBody);
        Request request = builder.build();
        Response response = org.clever.graaljs.core.utils.HttpUtils.execute(delegate.getOkHttpClient(), request);
        HttpResponse httpResponse = new HttpResponse();
        String res = null;
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            try {
                res = responseBody.string();
            } catch (IOException e) {
                throw ExceptionUtils.unchecked(e);
            }
        }
        httpResponse.setStatus(response.code());
        httpResponse.setBody(res);
        httpResponse.setRedirect(response.isRedirect());
        httpResponse.setSuccessful(response.isSuccessful());
        httpResponse.setStatusMessage(response.message());
        Map<String, List<String>> resHeaders = response.headers().toMultimap();
        for (Map.Entry<String, List<String>> entry : resHeaders.entrySet()) {
            String key = entry.getKey();
            httpResponse.getHeaders().addAll(StringUtils.lowerCase(key), entry.getValue());
        }
        return httpResponse;
    }

    protected String getJsonBody(Object body) {
        String json;
        if (body == null) {
            json = null;
        } else if (body instanceof CharSequence) {
            json = String.valueOf(body);
        } else {
            json = JacksonMapper.getInstance().toJson(body);
        }
        if (StringUtils.isBlank(json)) {
            json = "{}";
        }
        return json;
    }

    @Data
    public static class HttpResponse implements Serializable {
        /**
         * 响应状态码
         */
        private int status;
        /**
         * HTTP状态消息
         */
        private String statusMessage;
        /**
         * 响应Body
         */
        private String body;
        /**
         * 响应Header
         */
        private final MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        /**
         * 是否重定向
         */
        private boolean redirect;
        /**
         * 是否成功 status is in [200..300)
         */
        private boolean successful;

        /**
         * 获取响应头信息
         *
         * @param header 响应头名称
         */
        public List<String> getHeader(String header) {
            return headers.get(StringUtils.lowerCase(header));
        }

        /**
         * 获取响应头的第一个信息
         *
         * @param header 响应头名称
         */
        public String getFirstHeader(String header) {
            return headers.getFirst(StringUtils.lowerCase(header));
        }

        /**
         * 获取所有响应头的第一个信息
         */
        public Map<String, String> getFirstHeaders() {
            return headers.toSingleValueMap();
        }

        /**
         * 获取响应头的第一个信息
         *
         * @param header       响应头名称
         * @param defaultValue 默认值
         */
        public String getFirstHeader(String header, String defaultValue) {
            String value = headers.getFirst(StringUtils.lowerCase(header));
            return value == null ? defaultValue : value;
        }

        /**
         * 获取所有的响应头名称
         */
        public Set<String> getHeaderNames() {
            return headers.keySet();
        }

        /**
         * 获取请求BodyMap对象
         */
        @SuppressWarnings("unchecked")
        public Map<String, Object> getBodyMap() {
            if (StringUtils.isBlank(body)) {
                return Collections.emptyMap();
            }
            return JacksonMapper.getInstance().fromJson(body, LinkedHashMap.class);
        }
    }
}
