/**
 * 参数类型
 */
type HttpParamType = JChar | JString | JInt | JLong | JFloat | JDouble | JBigDecimal | JBoolean | JDate | JSqlDate | JSqlTime | JSqlTimestamp | Date | number | string | boolean | null;

/**
 * 请求Query String Parameter参数
 */
interface HttpParamsMap {
    [name: string]: HttpParamType;
}

/**
 * 请求Http Headers参数
 */
interface HttpHeadersMap {
    [name: string]: HttpParamType;
}

/**
 * 请求Body参数
 */
interface HttpBodyDataMap {
    [name: string]: HttpParamType | HttpBodyDataMap;
}

/**
 * Http请求Body
 */
type HttpBodyDataType = HttpBodyDataMap | JString | string | null;

/**
 * Http响应数据
 */
interface HttpResultMap {
    [name: string]: HttpParamType | HttpResultMap;
}

/**
 * Http响应对象
 */
interface HttpResponse {
    /**
     * 响应状态码
     */
    getStatus(): JInt;

    /**
     * HTTP状态消息
     */
    getStatusMessage(): JString;

    /**
     * 响应Body
     */
    getBody(): JString;

    /**
     * 响应Header
     */
    getHeaders(): JMultiValueMap<JString, JString>;

    /**
     * 是否重定向
     */
    isRedirect(): JBoolean;

    /**
     * 是否成功 status is in [200..300)
     */
    isSuccessful(): JBoolean;

    /**
     * 获取响应头信息
     *
     * @param header 响应头名称
     */
    getHeader(header: JString): JList<JString>;

    /**
     * 获取响应头的第一个信息
     *
     * @param header 响应头名称
     */
    getFirstHeader(header: JString): JString;

    /**
     * 获取所有响应头的第一个信息
     */
    getFirstHeaders(): JMap<JString, JString>;

    /**
     * 获取响应头的第一个信息
     *
     * @param header       响应头名称
     * @param defaultValue 默认值
     */
    getFirstHeader(header: JString, defaultValue: JString): JString;

    /**
     * 获取所有的响应头名称
     */
    getHeaderNames(): JSet<JString>;

    /**
     * 获取请求BodyMap对象
     */
    getBodyMap(): JMap<string, any>;
}

interface HttpUtils {
    // GET请求
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 使用HTTP GET请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    getStr(url: JString, params: HttpParamsMap, headers: HttpHeadersMap): JString;

    /**
     * 使用HTTP GET请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    getStr(url: JString, params: HttpParamsMap): JString;

    /**
     * 使用HTTP GET请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    getStr(url: JString): JString;

    /**
     * 使用HTTP GET请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    getMap<T = HttpResultMap>(url: JString, params: HttpParamsMap, headers: HttpHeadersMap): T;

    /**
     * 使用HTTP GET请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    getMap<T = HttpResultMap>(url: JString, params: HttpParamsMap): T;

    /**
     * 使用HTTP GET请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    getMap<T = HttpResultMap>(url: JString): T;

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
    postStr(url: JString, body: HttpBodyDataType, params: HttpParamsMap, headers: HttpHeadersMap): JString;

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    postStr(url: JString, body: HttpBodyDataType, params: HttpParamsMap): JString;

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    postStr(url: JString, body: HttpBodyDataType): JString;

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    postStr(url: JString, params: HttpParamsMap): JString;

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    postStr(url: JString): JString;

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param body    Json Body(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    postMap<T = HttpResultMap>(url: JString, body: HttpBodyDataType, params: HttpParamsMap, headers: HttpHeadersMap): T;

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    postMap<T = HttpResultMap>(url: JString, body: HttpBodyDataType, params: HttpParamsMap): T;

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    postMap<T = HttpResultMap>(url: JString, body: HttpBodyDataType): T;

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    postMap<T = HttpResultMap>(url: JString, params: HttpParamsMap): T;

    /**
     * 使用HTTP POST请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    postMap<T = HttpResultMap>(url: JString): T;

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
    deleteStr(url: JString, body: HttpBodyDataType, params: HttpParamsMap, headers: HttpHeadersMap): JString;

    /**
     * 使用HTTP DELETE请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    deleteStr(url: JString, body: HttpBodyDataType, params: HttpParamsMap): JString;

    /**
     * 使用HTTP DELETE请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    deleteStr(url: JString, body: HttpBodyDataType): JString;

    /**
     * 使用HTTP DELETE请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    deleteStr(url: JString, params: HttpParamsMap): JString;

    /**
     * 使用HTTP DELETE请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    deleteStr(url: JString): JString;

    /**
     * 使用HTTP DELETE请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param body    Json Body(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    deleteMap<T = HttpResultMap>(url: JString, body: HttpBodyDataType, params: HttpParamsMap, headers: HttpHeadersMap): T;

    /**
     * 使用HTTP DELETE请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    deleteMap<T = HttpResultMap>(url: JString, body: HttpBodyDataType, params: HttpParamsMap): T;

    /**
     * 使用HTTP DELETE请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    deleteMap<T = HttpResultMap>(url: JString, body: HttpBodyDataType): T;

    /**
     * 使用HTTP DELETE请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    deleteMap<T = HttpResultMap>(url: JString, params: HttpParamsMap): T;

    /**
     * 使用HTTP DELETE请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    deleteMap<T = HttpResultMap>(url: JString): T;

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
    putStr(url: JString, body: HttpBodyDataType, params: HttpParamsMap, headers: HttpHeadersMap): JString;

    /**
     * 使用HTTP PUT请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    putStr(url: JString, body: HttpBodyDataType, params: HttpParamsMap): JString;

    /**
     * 使用HTTP PUT请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    putStr(url: JString, body: HttpBodyDataType): JString;

    /**
     * 使用HTTP PUT请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    putStr(url: JString, params: HttpParamsMap): JString;

    /**
     * 使用HTTP PUT请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    putStr(url: JString): JString;

    /**
     * 使用HTTP PUT请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param body    Json Body(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    putMap<T = HttpResultMap>(url: JString, body: HttpBodyDataType, params: HttpParamsMap, headers: HttpHeadersMap): T;

    /**
     * 使用HTTP PUT请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    putMap<T = HttpResultMap>(url: JString, body: HttpBodyDataType, params: HttpParamsMap): T;

    /**
     * 使用HTTP PUT请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    putMap<T = HttpResultMap>(url: JString, body: HttpBodyDataType): T;

    /**
     * 使用HTTP PUT请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    putMap<T = HttpResultMap>(url: JString, params: HttpParamsMap): T;

    /**
     * 使用HTTP PUT请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    putMap<T = HttpResultMap>(url: JString): T;

    // HEAD请求
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 使用HTTP HEAD请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    headStr(url: JString, params: HttpParamsMap, headers: HttpHeadersMap): JString;

    /**
     * 使用HTTP HEAD请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    headStr(url: JString, params: HttpParamsMap): JString;

    /**
     * 使用HTTP HEAD请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    headStr(url: JString): JString;

    /**
     * 使用HTTP HEAD请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    headMap<T = HttpResultMap>(url: JString, params: HttpParamsMap, headers: HttpHeadersMap): T;

    /**
     * 使用HTTP HEAD请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    headMap<T = HttpResultMap>(url: JString, params: HttpParamsMap): T;

    /**
     * 使用HTTP HEAD请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    headMap<T = HttpResultMap>(url: JString): T;

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
    optionsStr(url: JString, body: HttpBodyDataType, params: HttpParamsMap, headers: HttpHeadersMap): JString;

    /**
     * 使用HTTP OPTIONS请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    optionsStr(url: JString, body: HttpBodyDataType, params: HttpParamsMap): JString;

    /**
     * 使用HTTP OPTIONS请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    optionsStr(url: JString, body: HttpBodyDataType): JString;

    /**
     * 使用HTTP OPTIONS请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    optionsStr(url: JString, params: HttpParamsMap): JString;

    /**
     * 使用HTTP OPTIONS请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    optionsStr(url: JString): JString;

    /**
     * 使用HTTP OPTIONS请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param body    Json Body(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    optionsMap<T = HttpResultMap>(url: JString, body: HttpBodyDataType, params: HttpParamsMap, headers: HttpHeadersMap): T;

    /**
     * 使用HTTP OPTIONS请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    optionsMap<T = HttpResultMap>(url: JString, body: HttpBodyDataType, params: HttpParamsMap): T;

    /**
     * 使用HTTP OPTIONS请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    optionsMap<T = HttpResultMap>(url: JString, body: HttpBodyDataType): T;

    /**
     * 使用HTTP OPTIONS请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    optionsMap<T = HttpResultMap>(url: JString, params: HttpParamsMap): T;

    /**
     * 使用HTTP OPTIONS请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    optionsMap<T = HttpResultMap>(url: JString): T;

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
    patchStr(url: JString, body: HttpBodyDataType, params: HttpParamsMap, headers: HttpHeadersMap): JString;

    /**
     * 使用HTTP PATCH请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    patchStr(url: JString, body: HttpBodyDataType, params: HttpParamsMap): JString;

    /**
     * 使用HTTP PATCH请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    patchStr(url: JString, body: HttpBodyDataType): JString;

    /**
     * 使用HTTP PATCH请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    patchStr(url: JString, params: HttpParamsMap): JString;

    /**
     * 使用HTTP PATCH请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    patchStr(url: JString): JString;

    /**
     * 使用HTTP PATCH请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param body    Json Body(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    patchMap<T = HttpResultMap>(url: JString, body: HttpBodyDataType, params: HttpParamsMap, headers: HttpHeadersMap): T;

    /**
     * 使用HTTP PATCH请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    patchMap<T = HttpResultMap>(url: JString, body: HttpBodyDataType, params: HttpParamsMap): T;

    /**
     * 使用HTTP PATCH请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    patchMap<T = HttpResultMap>(url: JString, body: HttpBodyDataType): T;

    /**
     * 使用HTTP PATCH请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    patchMap<T = HttpResultMap>(url: JString, params: HttpParamsMap): T;

    /**
     * 使用HTTP PATCH请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    patchMap<T = HttpResultMap>(url: JString): T;

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
    traceStr(url: JString, body: HttpBodyDataType, params: HttpParamsMap, headers: HttpHeadersMap): JString;

    /**
     * 使用HTTP TRACE请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    traceStr(url: JString, body: HttpBodyDataType, params: HttpParamsMap): JString;

    /**
     * 使用HTTP TRACE请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    traceStr(url: JString, body: HttpBodyDataType): JString;

    /**
     * 使用HTTP TRACE请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    traceStr(url: JString, params: HttpParamsMap): JString;

    /**
     * 使用HTTP TRACE请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    traceStr(url: JString): JString;

    /**
     * 使用HTTP TRACE请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param body    Json Body(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    traceMap<T = HttpResultMap>(url: JString, body: HttpBodyDataType, params: HttpParamsMap, headers: HttpHeadersMap): T;

    /**
     * 使用HTTP TRACE请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    traceMap<T = HttpResultMap>(url: JString, body: HttpBodyDataType, params: HttpParamsMap): T;

    /**
     * 使用HTTP TRACE请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    traceMap<T = HttpResultMap>(url: JString, body: HttpBodyDataType): T;

    /**
     * 使用HTTP TRACE请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    traceMap<T = HttpResultMap>(url: JString, params: HttpParamsMap): T;

    /**
     * 使用HTTP TRACE请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    traceMap<T = HttpResultMap>(url: JString): T;

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
    connectStr(url: JString, body: HttpBodyDataType, params: HttpParamsMap, headers: HttpHeadersMap): JString;

    /**
     * 使用HTTP CONNECT请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    connectStr(url: JString, body: HttpBodyDataType, params: HttpParamsMap): JString;

    /**
     * 使用HTTP CONNECT请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    connectStr(url: JString, body: HttpBodyDataType): JString;

    /**
     * 使用HTTP CONNECT请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    connectStr(url: JString, params: HttpParamsMap): JString;

    /**
     * 使用HTTP CONNECT请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    connectStr(url: JString): JString;

    /**
     * 使用HTTP CONNECT请求获取数据，支持参数，返回字符串
     *
     * @param url     请求url(非空)
     * @param body    Json Body(非空)
     * @param params  Url Query Parameter(可选)
     * @param headers 请求头(可选)
     */
    connectMap<T = HttpResultMap>(url: JString, body: HttpBodyDataType, params: HttpParamsMap, headers: HttpHeadersMap): T;

    /**
     * 使用HTTP CONNECT请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param body   Json Body(非空)
     * @param params Url Query Parameter(可选)
     */
    connectMap<T = HttpResultMap>(url: JString, body: HttpBodyDataType, params: HttpParamsMap): T;

    /**
     * 使用HTTP CONNECT请求获取数据，支持参数，返回字符串
     *
     * @param url  请求url(非空)
     * @param body Json Body(非空)
     */
    connectMap<T = HttpResultMap>(url: JString, body: HttpBodyDataType): T;

    /**
     * 使用HTTP CONNECT请求获取数据，支持参数，返回字符串
     *
     * @param url    请求url(非空)
     * @param params Url Query Parameter(可选)
     */
    connectMap<T = HttpResultMap>(url: JString, params: HttpParamsMap): T;

    /**
     * 使用HTTP CONNECT请求获取数据，支持参数，返回字符串
     *
     * @param url 请求url(非空)
     */
    connectMap<T = HttpResultMap>(url: JString): T;

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
    execRequest(method: HttpMethod, url: JString, body: HttpBodyDataType, params: HttpParamsMap, headers: HttpHeadersMap): HttpResponse;
}

declare const HttpUtils: HttpUtils;
