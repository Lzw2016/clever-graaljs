package org.clever.graaljs.core.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Http请求工具,使用 okhttp 实现
 * 1. 默认 readTimeout = 60s<br />
 * 2. 默认 connectTimeout = 60s<br />
 * 3. 默认 cacheSize = 100MB<br />
 * <p>
 * 作者：lzw <br/>
 * 创建时间：2017-09-02 20:14 <br/>
 */
@Slf4j
public class HttpUtils {

    public static final String MediaType_Json = "application/json;charset=UTF-8";
    public static final String MediaType_XML = "text/xml;charset=UTF-8";
    public static final String MediaType_Plain = "text/plain;charset=UTF-8";
    public static final String MediaType_HTML = "text/html;charset=UTF-8";
    public static final String MediaType_Png = "image/png";
    public static final String MediaType_Pdf = "application/pdf";

    private static final HttpUtils HTTP_UTILS;

    /**
     * 内部 OkHttpClient (建议单例)
     */
    private final OkHttpClient okHttpClient;

    public HttpUtils(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    static {
        long readTimeout = 60L;
        long connectTimeout = 5L;
        long cacheSize = 1024 * 1024 * 100L;
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .addHeader("Accept-Language", "zh-CN,zh;q=0.8")
                            .build();
                    long start = System.currentTimeMillis();
                    log.info(String.format("---> 请求 [%1$s] %2$s ", request.method(), request.url()));
                    Response response = chain.proceed(request);
                    long end = System.currentTimeMillis();
                    log.info(String.format("<--- 响应 [%1$d] %2$s (%3$dms)", response.code(), response.request().url(), (end - start)));
                    return response;
                })
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .cache(new Cache(new File("./httpCache"), cacheSize))
                .build();
        HTTP_UTILS = new HttpUtils(okHttpClient);
    }

    // --------------------------------------------------------------------------------------------
    // 类方法
    // --------------------------------------------------------------------------------------------

    public static HttpUtils getInner() {
        return HTTP_UTILS;
    }

    /**
     * 创建基本的 Request.Builder
     *
     * @param url     请求url(非空)
     * @param headers 请求头(可选)
     * @param params  Url Query Parameter(可选)
     */
    public static Request.Builder createRequestBuilder(final String url, final Map<String, String> headers, final Map<String, String> params) {
        if (StringUtils.isBlank(url)) {
            throw new RuntimeException("url参数不能为空");
        }
        final Request.Builder builder = new Request.Builder();
        // 请求头
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        // url params
        if (params != null) {
            HttpUrl httpUrl = HttpUrl.parse(url);
            if (httpUrl == null) {
                throw new RuntimeException("url参数错误");
            }
            HttpUrl.Builder urlBuilder = httpUrl.newBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
            httpUrl = urlBuilder.build();
            builder.url(httpUrl);
        } else {
            builder.url(url);
        }
        return builder;
    }

    /**
     * 创建Get Request
     *
     * @param url     请求url(非空)
     * @param headers 请求头(可选)
     * @param params  Url Query Parameter(可选)
     */
    public static Request createGetRequest(final String url, final Map<String, String> headers, final Map<String, String> params) {
        final Request.Builder builder = createRequestBuilder(url, headers, params);
        builder.get();
        return builder.build();
    }

    /**
     * 创建Get Request
     *
     * @param url         请求url(非空)
     * @param headers     请求头(可选)
     * @param params      Url Query Parameter(可选)
     * @param requestBody Request Body(非空)
     */
    public static Request createPostRequest(final String url, final Map<String, String> headers, final Map<String, String> params, RequestBody requestBody) {
        final Request.Builder builder = createRequestBuilder(url, headers, params);
        builder.post(requestBody);
        return builder.build();
    }

    /**
     * 执行同步请求返回Response对象
     */
    public static Response execute(OkHttpClient okHttpClient, Request request) {
        try {
            return okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

    /**
     * 执行同步请求返回Body字符串
     */
    public static String executeReturnStr(OkHttpClient okHttpClient, Request request) {
        String body = null;
        Response response = execute(okHttpClient, request);
        if (response != null) {
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                try {
                    body = responseBody.string();
                } catch (IOException e) {
                    throw ExceptionUtils.unchecked(e);
                }
            }
        }
        return body;
    }

    /**
     * 执行同步请求返回Body Byte内容
     */
    public static byte[] executeReturnByte(OkHttpClient okHttpClient, Request request) {
        byte[] body = null;
        Response response = execute(okHttpClient, request);
        if (response != null) {
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                try {
                    body = responseBody.bytes();
                } catch (IOException e) {
                    throw ExceptionUtils.unchecked(e);
                }
            }
        }
        return body;
    }

    /**
     * 执行异步请求
     */
    public static void enqueue(OkHttpClient okHttpClient, Request request, Callback callback) {
        okHttpClient.newCall(request).enqueue(callback);
    }

    // --------------------------------------------------------------------------------------------
    // 对象方法
    // --------------------------------------------------------------------------------------------

    /**
     * 返回内部OkHttpClient(建议单例)
     */
    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    // --------------------------------------------------------------------------------------------
    // GET请求
    // --------------------------------------------------------------------------------------------

    /**
     * 使用HTTP GET请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param headers 请求头(可选)
     * @param params  Url Query Parameter(可选)
     */
    public String getStr(final String url, final Map<String, String> headers, final Map<String, String> params) {
        Request request = createGetRequest(url, headers, params);
        return executeReturnStr(this.okHttpClient, request);
    }

    /**
     * 使用HTTP GET请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    public String getStr(final String url, Map<String, String> params) {
        Request request = createGetRequest(url, null, params);
        return executeReturnStr(this.okHttpClient, request);
    }

    /**
     * 使用HTTP GET请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    public String getStr(final String url) {
        Request request = createGetRequest(url, null, null);
        return executeReturnStr(this.okHttpClient, request);
    }

    /**
     * 使用HTTP GET请求获取数据，支持参数，字节数组
     *
     * @param url     请求url(非空)
     * @param headers 请求头(可选)
     * @param params  Url Query Parameter(可选)
     */
    public byte[] getByte(final String url, final Map<String, String> headers, final Map<String, String> params) {
        Request request = createGetRequest(url, headers, params);
        return executeReturnByte(this.okHttpClient, request);
    }


    // --------------------------------------------------------------------------------------------
    // POST请求
    // --------------------------------------------------------------------------------------------

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url      请求url(非空)
     * @param headers  请求头(可选)
     * @param params   Url Query Parameter(可选)
     * @param jsonBody Json Body(非空)
     */
    public String postStr(final String url, final Map<String, String> headers, final Map<String, String> params, String jsonBody) {
        RequestBody requestBody = RequestBody.create(jsonBody, MediaType.parse(MediaType_Json));
        Request request = createPostRequest(url, headers, params, requestBody);
        return executeReturnStr(this.okHttpClient, request);
    }

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url      请求url(非空)
     * @param params   Url Query Parameter(可选)
     * @param jsonBody Json Body(非空)
     */
    public String postStr(final String url, final Map<String, String> params, String jsonBody) {
        RequestBody requestBody = RequestBody.create(jsonBody, MediaType.parse(MediaType_Json));
        Request request = createPostRequest(url, null, params, requestBody);
        return executeReturnStr(this.okHttpClient, request);
    }

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url      请求url(非空)
     * @param jsonBody Json Body(非空)
     */
    public String postStr(String url, String jsonBody) {
        RequestBody requestBody = RequestBody.create(jsonBody, MediaType.parse(MediaType_Json));
        Request request = createPostRequest(url, null, null, requestBody);
        return executeReturnStr(this.okHttpClient, request);
    }

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字节数组
     *
     * @param url      请求url(非空)
     * @param headers  请求头(可选)
     * @param params   Url Query Parameter(可选)
     * @param jsonBody Json Body(非空)
     */
    public byte[] postByte(final String url, final Map<String, String> headers, final Map<String, String> params, String jsonBody) {
        RequestBody requestBody = RequestBody.create(jsonBody, MediaType.parse(MediaType_Json));
        Request request = createPostRequest(url, headers, params, requestBody);
        return executeReturnByte(this.okHttpClient, request);
    }
}
