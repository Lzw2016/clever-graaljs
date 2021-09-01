package org.clever.graaljs.spring.mvc;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.core.GraalConstant;
import org.clever.graaljs.core.ScriptEngineInstance;
import org.clever.graaljs.core.ScriptObject;
import org.clever.graaljs.core.internal.jackson.JacksonMapperSupport;
import org.clever.graaljs.core.utils.ExceptionUtils;
import org.clever.graaljs.core.utils.TupleOne;
import org.clever.graaljs.core.utils.TupleTow;
import org.clever.graaljs.core.utils.codec.EncodeDecodeUtils;
import org.clever.graaljs.spring.logger.GraalJsDebugLogbackAppender;
import org.clever.graaljs.spring.mvc.builtin.wrap.HttpContext;
import org.clever.graaljs.spring.mvc.support.IntegerToDateConverter;
import org.clever.graaljs.spring.mvc.support.StringToDateConverter;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.cors.CorsProcessor;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.DefaultCorsProcessor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.mvc.ParameterizableViewController;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/08/24 21:34 <br/>
 */
@Slf4j
public abstract class HttpInterceptorScriptHandler implements HandlerInterceptor, ScriptHandler {
    protected static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    /**
     * 是否强制使用Script Handler处理请求
     */
    protected static final String FORCE_USE_SCRIPT = "force-use-script";
    /**
     * 使用Script Handler处理请求时对应的Script File信息(响应头信息)
     */
    @SuppressWarnings("UastIncorrectHttpHeaderInspection")
    protected static final String USE_SCRIPT_HANDLER_HEAD = "script-file-resource";
    /**
     * 是否调试接口
     */
    private static final String API_DEBUG_HEADER = "api-debug";
    /**
     * 初始化状态
     */
    protected volatile boolean initialized = false;
    /**
     * 支持的请求前缀
     */
    protected final String supportPrefix;
    /**
     * 跨域配置
     */
    protected final ScriptHandlerCorsConfig corsConfig;
    /**
     * 脚本引擎实例
     */
    protected final ScriptEngineInstance scriptEngineInstance;
    /**
     * 异常处理对象
     */
    protected final ExceptionResolver exceptionResolver;
    /**
     * 跨域处理器 CorsProcessor
     */
    protected final CorsProcessor corsProcessor = new DefaultCorsProcessor();
    /**
     * mvc请求数据转换对象
     */
    protected final ConversionService conversionService;

    /**
     * @param supportPrefix        支持的请求前缀
     * @param corsConfig           跨域配置
     * @param scriptEngineInstance 脚本引擎实例
     * @param exceptionResolver    异常处理对象
     * @param conversionService    mvc请求数据转换对象
     */
    public HttpInterceptorScriptHandler(
            String supportPrefix,
            ScriptHandlerCorsConfig corsConfig,
            ScriptEngineInstance scriptEngineInstance,
            ExceptionResolver exceptionResolver,
            ConversionService conversionService) {
        Assert.notNull(scriptEngineInstance, "参数scriptEngineInstance不能为空");
        this.supportPrefix = StringUtils.isBlank(supportPrefix) ? StringUtils.EMPTY : StringUtils.trim(supportPrefix);
        this.corsConfig = corsConfig == null ? new ScriptHandlerCorsConfig() : corsConfig;
        this.scriptEngineInstance = scriptEngineInstance;
        this.exceptionResolver = exceptionResolver;
        this.conversionService = conversionService;
        init();
    }

    private synchronized void init() {
        if (initialized) {
            return;
        }
        if (conversionService instanceof GenericConversionService) {
            GenericConversionService genericConversionService = (GenericConversionService) conversionService;
            genericConversionService.addConverter(String.class, Date.class, StringToDateConverter.Instance);
            genericConversionService.addConverter(Integer.class, Date.class, IntegerToDateConverter.Instance);
            genericConversionService.addConverter(int.class, Date.class, IntegerToDateConverter.Instance);
            initialized = true;
        }
    }

    /**
     * 判断请求是否支持 Script 处理
     */
    protected boolean supportScript(HttpServletRequest request, Object handler) {
        final String requestUri = request.getRequestURI();
        // 支持的请求前缀
        boolean support = requestUri.startsWith(supportPrefix);
        // SpringMvc功能冲突处理
        if (handler != null && support) {
            if (handler instanceof HandlerMethod) {
                if (StringUtils.isNotBlank(request.getParameter(FORCE_USE_SCRIPT)) || StringUtils.isNotBlank(request.getHeader(FORCE_USE_SCRIPT))) {
                    log.warn("强制使用Script Handler功能，忽略原生SpringMvc功能 | {}", handler.getClass());
                } else {
                    log.warn("Script Handler被原生SpringMvc功能覆盖 | {}", handler.getClass());
                    support = false;
                }
            } else if (handler instanceof ResourceHttpRequestHandler) {
                ResourceHttpRequestHandler resourceHttpRequestHandler = (ResourceHttpRequestHandler) handler;
                Method method = ReflectionUtils.findMethod(ResourceHttpRequestHandler.class, "getResource", HttpServletRequest.class);
                if (method != null) {
                    if (!method.isAccessible()) {
                        method.setAccessible(true);
                    }
                    Resource resource = (Resource) ReflectionUtils.invokeMethod(method, resourceHttpRequestHandler, request);
                    if (resource != null && resource.exists()) {
                        if (StringUtils.isNotBlank(request.getParameter(FORCE_USE_SCRIPT)) || StringUtils.isNotBlank(request.getHeader(FORCE_USE_SCRIPT))) {
                            log.warn("强制使用Script Handler功能，忽略静态资源 | {}", handler.getClass());
                        } else {
                            log.warn("Script Handler被静态资源覆盖 | {}", handler.getClass());
                            support = false;
                        }
                    }
                }
            } else if (handler instanceof ParameterizableViewController) {
                support = false;
            } else if (!handler.getClass().getName().startsWith("org.springframework.")) {
                log.warn("未知的Handler类型，覆盖Script Handler | {}", handler.getClass());
                support = false;
            }
        }
        return support;
    }

    /**
     * 获取处理请求的Script File Resource
     *
     * @param request 请求对象
     * @return {@code TupleTow<FullPath, Content>}
     */
    protected abstract TupleTow<String, String> getScriptFileResource(HttpServletRequest request);

    /**
     * 当前请求真实处理逻辑
     *
     * @param request             当前请求对象
     * @param response            当前响应对象
     * @param handlerScriptObject 当前请求处理函数对象
     * @return 响应对象
     */
    protected Value doHandle(HttpServletRequest request, HttpServletResponse response, ScriptObject handlerScriptObject) {
        final HttpContext ctx = new HttpContext(request, response, conversionService);
        final Value bindings = handlerScriptObject.getContext().getBindings(GraalConstant.Js_Language_Id);
        final String ctxName = "ctx";
        try {
            bindings.putMember(ctxName, ctx);
            return handlerScriptObject.execute(ctx);
        } finally {
            bindings.removeMember(ctxName);
        }
    }

    /**
     * 返回对象是否是空值
     */
    protected boolean resIsEmpty(Object res) {
        if (res == null) {
            return true;
        }
        if (res instanceof Value) {
            return ((Value) res).isNull();
        }
        return false;
    }

    /**
     * 序列化返回对象
     */
    @SuppressWarnings("SameParameterValue")
    protected String serializeRes(Object res, boolean pretty) {
        if (pretty) {
            return JacksonMapperSupport.getHttpApiJacksonMapper().toPrettyJson(res);
        }
        return JacksonMapperSupport.getHttpApiJacksonMapper().toJson(res);
    }

    /**
     * 序列化返回对象
     */
    protected String serializeRes(Object res) {
        return serializeRes(res, false);
    }

    /**
     * 异常处理(转换异常)
     */
    protected void errHandle(Throwable e) throws Exception {
        PolyglotException polyglotException = null;
        if (e instanceof PolyglotException) {
            polyglotException = (PolyglotException) e;
        } else if (e.getCause() instanceof PolyglotException) {
            polyglotException = (PolyglotException) e.getCause();
        }
        if (polyglotException != null && polyglotException.isHostException()) {
            Throwable err = polyglotException.asHostException();
            if (err == null) {
                throw polyglotException;
            }
            e = err;
        }
        if (e instanceof Exception) {
            throw (Exception) e;
        }
        throw new RuntimeException(e);
    }

    @Override
    public boolean handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断请求是否支持 Script 处理
        if (!supportScript(request, handler)) {
            return false;
        }
        long startTime1 = -1;                                   // 开始查找脚本文件时间
        long startTime2 = -1;                                   // 开始执行脚本时间
        final TupleOne<Long> startTime3 = TupleOne.creat(-1L);  // 开始序列化返回值时间
        TupleTow<String, String> scriptInfo = null;
        try {
            // 1.加载执行脚本代码
            startTime1 = System.currentTimeMillis();
            scriptInfo = getScriptFileResource(request);
            if (scriptInfo == null) {
                return false;
            }
            // 2.执行脚本
            startTime2 = System.currentTimeMillis();
            response.setHeader(USE_SCRIPT_HANDLER_HEAD, EncodeDecodeUtils.urlEncode(scriptInfo.getValue1()));
            String resJson = scriptEngineInstance.wrapFunctionAndEval(scriptInfo.getValue2(), scriptObject -> {
                Object res = doHandle(request, response, scriptObject);
                // 3.序列化返回数据
                startTime3.setValue1(System.currentTimeMillis());
                if (!resIsEmpty(res) && !response.isCommitted()) {
                    return serializeRes(res);
                }
                return null;
            });
            if (resJson != null && !response.isCommitted()) {
                response.setContentType(CONTENT_TYPE);
                response.getWriter().print(resJson);
            }
        } catch (Throwable e) {
            log.error("执行脚本失败:\n{}", ExceptionUtils.getErrorCodeLocation(e), e);
            errHandle(e);
        } finally {
            if (scriptInfo != null) {
                final long endTime = System.currentTimeMillis();
                final long howLongSum = endTime - startTime1;                                                   // 总耗时
                final long howLong1 = startTime2 <= -1 ? -1 : startTime2 - startTime1;                          // 查找脚本耗时
                final long howLong2 = startTime3.getValue1() <= -1 ? -1 : startTime3.getValue1() - startTime2;  // 执行脚本耗时
                final long howLong3 = startTime3.getValue1() <= -1 ? -1 : endTime - startTime3.getValue1();     // 序列化耗时
                // 8.请求处理完成 - 打印日志
                String logText = String.format(
                        "Script处理请求 | [总]耗时:%-3s | 查找脚本耗时:%-3s | 执行脚本耗时:%-3s | 序列化耗时:%-3s | Script=[%s]",
                        howLongSum + "ms",
                        howLong1 <= -1 ? "-" : howLong1 + "ms",
                        howLong2 <= -1 ? "-" : howLong2 + "ms",
                        howLong3 <= -1 ? "-" : howLong3 + "ms",
                        scriptInfo.getValue1()
                );
                log.debug(logText);
            }
        }
        return true;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 跨域处理
        boolean supportCors = corsProcessor.processRequest(corsConfig.getCorsConfiguration(), request, response);
        if (supportCors) {
            boolean preFlightRequest = CorsUtils.isPreFlightRequest(request);
            if (preFlightRequest) {
                // 是预检请求(OPTIONS)
                return false;
            }
        } else {
            // 不支持跨域
            return false;
        }
        final String apiDebugId = StringUtils.trim(request.getHeader(API_DEBUG_HEADER));
        final boolean isDebug = StringUtils.isNotBlank(apiDebugId);
        try {
            if (isDebug) {
                GraalJsDebugLogbackAppender.apiDebugStart(apiDebugId, 2048);
            }
            return !handle(request, response, handler);
        } catch (Exception e) {
            if (response.isCommitted()) {
                log.info("Script处理请求异常", e);
                return false;
            }
            Object res;
            if (exceptionResolver != null) {
                res = exceptionResolver.resolveException(request, response, handler, e);
            } else {
                log.info("Script处理请求异常", e);
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                res = DefaultExceptionResolver.Instance.newErrorResponse(request, response, e);
            }
            if (res != null) {
                response.setContentType(CONTENT_TYPE);
                String json = serializeRes(res);
                response.getWriter().println(json);
            }
        } finally {
            if (isDebug) {
                GraalJsDebugLogbackAppender.apiDebugEnd(apiDebugId);
            }
        }
        return false;
    }
}
