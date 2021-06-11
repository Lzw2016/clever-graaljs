package org.clever.graaljs.core.builtin.wrap;

import org.clever.graaljs.core.internal.utils.InteropScriptToJavaUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/08/15 20:08 <br/>
 */
public class HttpUtils {
    public static final HttpUtils Instance = new HttpUtils();

    private final org.clever.graaljs.core.builtin.adapter.HttpUtils delegate;

    private HttpUtils() {
        delegate = org.clever.graaljs.core.builtin.adapter.HttpUtils.Instance;
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
    public String getStr(final String url, Map<String, Object> params, Map<String, Object> headers) {
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        headers = InteropScriptToJavaUtils.Instance.convertMap(headers);
        return delegate.getStr(url, convertMap(params), convertMap(headers));
    }

    /**
     * 使用HTTP GET请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public String getStr(final String url, Map<String, Object> params) {
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.getStr(url, convertMap(params));
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
    public Map<String, Object> getMap(final String url, Map<String, Object> params, Map<String, Object> headers) {
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        headers = InteropScriptToJavaUtils.Instance.convertMap(headers);
        return delegate.getMap(url, convertMap(params), convertMap(headers));
    }

    /**
     * 使用HTTP GET请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public Map<String, Object> getMap(final String url, Map<String, Object> params) {
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.getMap(url, convertMap(params));
    }

    /**
     * 使用HTTP GET请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public Map<String, Object> getMap(final String url) {
        return delegate.getMap(url);
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
    public String postStr(final String url, Object body, Map<String, Object> params, Map<String, Object> headers) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        headers = InteropScriptToJavaUtils.Instance.convertMap(headers);
        return delegate.postStr(url, body, convertMap(params), convertMap(headers));
    }

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    public String postStr(final String url, Object body, Map<String, Object> params) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.postStr(url, body, convertMap(params));
    }

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    public String postStr(final String url, Object body) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        return delegate.postStr(url, body);
    }

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public String postStr(final String url, Map<String, Object> params) {
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.postStr(url, convertMap(params));
    }

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public String postStr(final String url) {
        return delegate.postStr(url);
    }

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param body    Json Body(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    public Map<String, Object> postMap(final String url, Object body, Map<String, Object> params, Map<String, Object> headers) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        headers = InteropScriptToJavaUtils.Instance.convertMap(headers);
        return delegate.postMap(url, body, convertMap(params), convertMap(headers));
    }

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    public Map<String, Object> postMap(final String url, Object body, Map<String, Object> params) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.postMap(url, body, convertMap(params));
    }

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    public Map<String, Object> postMap(final String url, Object body) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        return delegate.postMap(url, body);
    }

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public Map<String, Object> postMap(final String url, Map<String, Object> params) {
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.postMap(url, convertMap(params));
    }

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public Map<String, Object> postMap(final String url) {
        return delegate.postMap(url);
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
    public String deleteStr(final String url, Object body, Map<String, Object> params, Map<String, Object> headers) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        headers = InteropScriptToJavaUtils.Instance.convertMap(headers);
        return delegate.deleteStr(url, body, convertMap(params), convertMap(headers));
    }

    /**
     * 使用HTTP DELETE请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    public String deleteStr(final String url, Object body, Map<String, Object> params) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.deleteStr(url, body, convertMap(params));
    }

    /**
     * 使用HTTP DELETE请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    public String deleteStr(final String url, Object body) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        return delegate.deleteStr(url, body);
    }

    /**
     * 使用HTTP DELETE请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public String deleteStr(final String url, Map<String, Object> params) {
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.deleteStr(url, convertMap(params));
    }

    /**
     * 使用HTTP DELETE请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public String deleteStr(final String url) {
        return delegate.deleteStr(url);
    }

    /**
     * 使用HTTP DELETE请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param body    Json Body(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    public Map<String, Object> deleteMap(final String url, Object body, Map<String, Object> params, Map<String, Object> headers) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        headers = InteropScriptToJavaUtils.Instance.convertMap(headers);
        return delegate.deleteMap(url, body, convertMap(params), convertMap(headers));
    }

    /**
     * 使用HTTP DELETE请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    public Map<String, Object> deleteMap(final String url, Object body, Map<String, Object> params) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.deleteMap(url, body, convertMap(params));
    }

    /**
     * 使用HTTP DELETE请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    public Map<String, Object> deleteMap(final String url, Object body) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        return delegate.deleteMap(url, body);
    }

    /**
     * 使用HTTP DELETE请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public Map<String, Object> deleteMap(final String url, Map<String, Object> params) {
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.deleteMap(url, convertMap(params));
    }

    /**
     * 使用HTTP DELETE请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public Map<String, Object> deleteMap(final String url) {
        return delegate.deleteMap(url);
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
    public String putStr(final String url, Object body, Map<String, Object> params, Map<String, Object> headers) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        headers = InteropScriptToJavaUtils.Instance.convertMap(headers);
        return delegate.putStr(url, body, convertMap(params), convertMap(headers));
    }

    /**
     * 使用HTTP PUT请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    public String putStr(final String url, Object body, Map<String, Object> params) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.putStr(url, body, convertMap(params));
    }

    /**
     * 使用HTTP PUT请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    public String putStr(final String url, Object body) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        return delegate.putStr(url, body);
    }

    /**
     * 使用HTTP PUT请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public String putStr(final String url, Map<String, Object> params) {
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.putStr(url, convertMap(params));
    }

    /**
     * 使用HTTP PUT请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public String putStr(final String url) {
        return delegate.putStr(url);
    }

    /**
     * 使用HTTP PUT请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param body    Json Body(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    public Map<String, Object> putMap(final String url, Object body, Map<String, Object> params, Map<String, Object> headers) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        headers = InteropScriptToJavaUtils.Instance.convertMap(headers);
        return delegate.putMap(url, body, convertMap(params), convertMap(headers));
    }

    /**
     * 使用HTTP PUT请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    public Map<String, Object> putMap(final String url, Object body, Map<String, Object> params) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.putMap(url, body, convertMap(params));
    }

    /**
     * 使用HTTP PUT请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    public Map<String, Object> putMap(final String url, Object body) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        return delegate.putMap(url, body);
    }

    /**
     * 使用HTTP PUT请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public Map<String, Object> putMap(final String url, Map<String, Object> params) {
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.putMap(url, convertMap(params));
    }

    /**
     * 使用HTTP PUT请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public Map<String, Object> putMap(final String url) {
        return delegate.putMap(url);
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
    public String headStr(final String url, Map<String, Object> params, Map<String, Object> headers) {
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        headers = InteropScriptToJavaUtils.Instance.convertMap(headers);
        return delegate.headStr(url, convertMap(params), convertMap(headers));
    }

    /**
     * 使用HTTP HEAD请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public String headStr(final String url, Map<String, Object> params) {
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.headStr(url, convertMap(params));
    }

    /**
     * 使用HTTP HEAD请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public String headStr(final String url) {
        return delegate.headStr(url);
    }

    /**
     * 使用HTTP HEAD请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    public Map<String, Object> headMap(final String url, Map<String, Object> params, Map<String, Object> headers) {
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        headers = InteropScriptToJavaUtils.Instance.convertMap(headers);
        return delegate.headMap(url, convertMap(params), convertMap(headers));
    }

    /**
     * 使用HTTP HEAD请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public Map<String, Object> headMap(final String url, Map<String, Object> params) {
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.headMap(url, convertMap(params));
    }

    /**
     * 使用HTTP HEAD请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public Map<String, Object> headMap(final String url) {
        return delegate.headMap(url);
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
    public String optionsStr(final String url, Object body, Map<String, Object> params, Map<String, Object> headers) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        headers = InteropScriptToJavaUtils.Instance.convertMap(headers);
        return delegate.optionsStr(url, body, convertMap(params), convertMap(headers));
    }

    /**
     * 使用HTTP OPTIONS请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    public String optionsStr(final String url, Object body, Map<String, Object> params) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.optionsStr(url, body, convertMap(params));
    }

    /**
     * 使用HTTP OPTIONS请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    public String optionsStr(final String url, Object body) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        return delegate.optionsStr(url, body);
    }

    /**
     * 使用HTTP OPTIONS请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public String optionsStr(final String url, Map<String, Object> params) {
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.optionsStr(url, convertMap(params));
    }

    /**
     * 使用HTTP OPTIONS请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public String optionsStr(final String url) {
        return delegate.optionsStr(url);
    }

    /**
     * 使用HTTP OPTIONS请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param body    Json Body(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    public Map<String, Object> optionsMap(final String url, Object body, Map<String, Object> params, Map<String, Object> headers) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        headers = InteropScriptToJavaUtils.Instance.convertMap(headers);
        return delegate.optionsMap(url, body, convertMap(params), convertMap(headers));
    }

    /**
     * 使用HTTP OPTIONS请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    public Map<String, Object> optionsMap(final String url, Object body, Map<String, Object> params) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.optionsMap(url, body, convertMap(params));
    }

    /**
     * 使用HTTP OPTIONS请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    public Map<String, Object> optionsMap(final String url, Object body) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        return delegate.optionsMap(url, body);
    }

    /**
     * 使用HTTP OPTIONS请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public Map<String, Object> optionsMap(final String url, Map<String, Object> params) {
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.optionsMap(url, convertMap(params));
    }

    /**
     * 使用HTTP OPTIONS请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public Map<String, Object> optionsMap(final String url) {
        return delegate.optionsMap(url);
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
    public String patchStr(final String url, Object body, Map<String, Object> params, Map<String, Object> headers) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        headers = InteropScriptToJavaUtils.Instance.convertMap(headers);
        return delegate.patchStr(url, body, convertMap(params), convertMap(headers));
    }

    /**
     * 使用HTTP PATCH请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    public String patchStr(final String url, Object body, Map<String, Object> params) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.patchStr(url, body, convertMap(params));
    }

    /**
     * 使用HTTP PATCH请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    public String patchStr(final String url, Object body) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        return delegate.patchStr(url, body);
    }

    /**
     * 使用HTTP PATCH请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public String patchStr(final String url, Map<String, Object> params) {
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.patchStr(url, convertMap(params));
    }

    /**
     * 使用HTTP PATCH请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public String patchStr(String url) {
        return delegate.patchStr(url);
    }

    /**
     * 使用HTTP PATCH请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param body    Json Body(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    public Map<String, Object> patchMap(final String url, Object body, Map<String, Object> params, Map<String, Object> headers) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        headers = InteropScriptToJavaUtils.Instance.convertMap(headers);
        return delegate.patchMap(url, body, convertMap(params), convertMap(headers));
    }

    /**
     * 使用HTTP PATCH请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    public Map<String, Object> patchMap(final String url, Object body, Map<String, Object> params) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.patchMap(url, body, convertMap(params));
    }

    /**
     * 使用HTTP PATCH请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    public Map<String, Object> patchMap(final String url, Object body) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        return delegate.patchMap(url, body);
    }

    /**
     * 使用HTTP PATCH请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public Map<String, Object> patchMap(final String url, Map<String, Object> params) {
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.patchMap(url, convertMap(params));
    }

    /**
     * 使用HTTP PATCH请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public Map<String, Object> patchMap(final String url) {
        return delegate.patchMap(url);
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
    public String traceStr(final String url, Object body, Map<String, Object> params, Map<String, Object> headers) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        headers = InteropScriptToJavaUtils.Instance.convertMap(headers);
        return delegate.traceStr(url, body, convertMap(params), convertMap(headers));
    }

    /**
     * 使用HTTP TRACE请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    public String traceStr(final String url, Object body, Map<String, Object> params) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.traceStr(url, body, convertMap(params));
    }

    /**
     * 使用HTTP TRACE请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    public String traceStr(final String url, Object body) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        return delegate.traceStr(url, body);
    }

    /**
     * 使用HTTP TRACE请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public String traceStr(final String url, Map<String, Object> params) {
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.traceStr(url, convertMap(params));
    }

    /**
     * 使用HTTP TRACE请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public String traceStr(final String url) {
        return delegate.traceStr(url);
    }

    /**
     * 使用HTTP TRACE请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param body    Json Body(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    public Map<String, Object> traceMap(final String url, Object body, Map<String, Object> params, Map<String, Object> headers) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        headers = InteropScriptToJavaUtils.Instance.convertMap(headers);
        return delegate.traceMap(url, body, convertMap(params), convertMap(headers));
    }

    /**
     * 使用HTTP TRACE请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    public Map<String, Object> traceMap(final String url, Object body, Map<String, Object> params) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.traceMap(url, body, convertMap(params));
    }

    /**
     * 使用HTTP TRACE请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    public Map<String, Object> traceMap(final String url, Object body) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        return delegate.traceMap(url, body);
    }

    /**
     * 使用HTTP TRACE请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public Map<String, Object> traceMap(final String url, Map<String, Object> params) {
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.traceMap(url, convertMap(params));
    }

    /**
     * 使用HTTP TRACE请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public Map<String, Object> traceMap(final String url) {
        return delegate.traceMap(url);
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
    public String connectStr(final String url, Object body, Map<String, Object> params, Map<String, Object> headers) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        headers = InteropScriptToJavaUtils.Instance.convertMap(headers);
        return delegate.connectStr(url, body, convertMap(params), convertMap(headers));
    }

    /**
     * 使用HTTP CONNECT请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    public String connectStr(final String url, Object body, Map<String, Object> params) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.connectStr(url, body, convertMap(params));
    }

    /**
     * 使用HTTP CONNECT请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    public String connectStr(final String url, Object body) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        return delegate.connectStr(url, body);
    }

    /**
     * 使用HTTP CONNECT请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public String connectStr(final String url, Map<String, Object> params) {
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.connectStr(url, convertMap(params));
    }

    /**
     * 使用HTTP CONNECT请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public String connectStr(final String url) {
        return delegate.connectStr(url);
    }

    /**
     * 使用HTTP CONNECT请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param body    Json Body(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    public Map<String, Object> connectMap(final String url, Object body, Map<String, Object> params, Map<String, Object> headers) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        headers = InteropScriptToJavaUtils.Instance.convertMap(headers);
        return delegate.connectMap(url, body, convertMap(params), convertMap(headers));
    }

    /**
     * 使用HTTP CONNECT请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    public Map<String, Object> connectMap(final String url, Object body, Map<String, Object> params) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.connectMap(url, body, convertMap(params));
    }

    /**
     * 使用HTTP CONNECT请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    public Map<String, Object> connectMap(final String url, Object body) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        return delegate.connectMap(url, body);
    }

    /**
     * 使用HTTP CONNECT请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public Map<String, Object> connectMap(final String url, Map<String, Object> params) {
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        return delegate.connectMap(url, convertMap(params));
    }

    /**
     * 使用HTTP CONNECT请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public Map<String, Object> connectMap(final String url) {
        return delegate.connectMap(url);
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
    public org.clever.graaljs.core.builtin.adapter.HttpUtils.HttpResponse execRequest(final String method, String url, Object body, Map<String, Object> params, Map<String, Object> headers) {
        body = InteropScriptToJavaUtils.Instance.deepToJavaObject(body);
        params = InteropScriptToJavaUtils.Instance.convertMap(params);
        headers = InteropScriptToJavaUtils.Instance.convertMap(headers);
        return delegate.execRequest(method, url, body, convertMap(params), convertMap(headers));
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------

    public Map<String, String> convertMap(Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        final Map<String, String> resMap = new HashMap<>(map.size());
        map.forEach((k, v) -> resMap.put(k, v == null ? null : String.valueOf(v)));
        return resMap;
    }
}
