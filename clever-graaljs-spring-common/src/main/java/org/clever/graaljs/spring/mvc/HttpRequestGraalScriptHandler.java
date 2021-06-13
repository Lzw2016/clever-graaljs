//package org.clever.graaljs.spring.mvc;
//
//import org.apache.commons.lang3.StringUtils;
//import org.clever.hinny.api.ScriptEngineContext;
//import org.clever.hinny.api.ScriptEngineInstance;
//import org.clever.hinny.api.ScriptObject;
//import org.clever.hinny.api.folder.Folder;
//import org.clever.hinny.api.pool.EngineInstancePool;
//import org.clever.hinny.api.utils.JacksonMapper;
//import org.clever.hinny.graal.mvc.http.HttpContext;
//import org.clever.hinny.graaljs.jackson.JacksonMapperSupport;
//import org.clever.hinny.mvc.support.IntegerToDateConverter;
//import org.clever.hinny.mvc.support.StringToDateConverter;
//import org.clever.hinny.mvc.support.TupleTow;
//import org.graalvm.polyglot.Context;
//import org.graalvm.polyglot.PolyglotException;
//import org.graalvm.polyglot.Value;
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.core.convert.ConversionService;
//import org.springframework.core.convert.support.GenericConversionService;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.*;
//
///**
// * 作者：lizw <br/>
// * 创建时间：2020/08/24 21:34 <br/>
// */
//public class HttpRequestGraalScriptHandler extends HttpRequestScriptHandler<Context, Value> {
//    /**
//     * 脚本文件后缀
//     */
//    private static final String ScriptSuffix = ".js";
//    /**
//     * 返回数据序列化实现
//     */
//    private static final JacksonMapper Jackson_Mapper = JacksonMapperSupport.getHttpApiJacksonMapper();
//    /**
//     * MVC请求数据装换
//     */
//    private final ObjectProvider<ConversionService> conversionService;
//
//    protected boolean initialized = false;
//
//    public HttpRequestGraalScriptHandler(
//            LinkedHashMap<String, String> supportPrefix,
//            Set<String> supportSuffix,
//            ScriptHandlerCorsConfig corsConfig,
//            EngineInstancePool<Context, Value> engineInstancePool,
//            ExceptionResolver exceptionResolver,
//            ObjectProvider<ConversionService> conversionService) {
//        super(supportPrefix, supportSuffix, corsConfig, engineInstancePool, exceptionResolver);
//        this.conversionService = conversionService;
//    }
//
//    public HttpRequestGraalScriptHandler(EngineInstancePool<Context, Value> engineInstancePool, ObjectProvider<ConversionService> conversionService) {
//        super(engineInstancePool);
//        this.conversionService = conversionService;
//    }
//
//    private void init() {
//        if (initialized) {
//            return;
//        }
//        ConversionService conversion = conversionService.getIfAvailable();
//        if (conversion instanceof GenericConversionService) {
//            GenericConversionService genericConversionService = (GenericConversionService) conversion;
//            genericConversionService.addConverter(String.class, Date.class, StringToDateConverter.Instance);
//            genericConversionService.addConverter(Integer.class, Date.class, IntegerToDateConverter.Instance);
//            genericConversionService.addConverter(int.class, Date.class, IntegerToDateConverter.Instance);
//            initialized = true;
//        }
//    }
//
//    @Override
//    protected boolean fileExists(ScriptEngineInstance<Context, Value> engineInstance, String fullPath) {
//        if (!fullPath.endsWith(ScriptSuffix)) {
//            fullPath = fullPath + ScriptSuffix;
//        }
//        Folder folder = engineInstance.getRootPath().create(fullPath);
//        return folder != null && folder.isFile();
//    }
//
//    @Override
//    protected TupleTow<Object, Boolean> doHandle(HttpServletRequest request, HttpServletResponse response, TupleTow<ScriptObject<Value>, String> handlerScript) {
//        init();
//        HttpContext httpContext = new HttpContext(request, response, conversionService.getIfAvailable());
//        final ScriptObject<Value> scriptObject = handlerScript.getValue1();
//        final String method = handlerScript.getValue2();
//        Object fucObject = scriptObject.getMember(method);
//        //  fucObject类型不正确 - 跳过Script处理
//        if (!(fucObject instanceof Value)) {
//            return TupleTow.creat(null, true);
//        }
//        Value fucValue = (Value) fucObject;
//        // fucValue是一个函数
//        if (fucValue.canExecute()) {
//            Value res = fucValue.execute(httpContext);
//            return TupleTow.creat(res, false);
//        }
//        // fucValue 是 HttpRouter 对象
//        final String httpMethod = StringUtils.lowerCase(httpContext.request.getMethod());
//        Value httpRouter = fucValue.getMember(httpMethod);
//        if (httpRouter != null && httpRouter.canExecute()) {
//            Value res = httpRouter.execute(httpContext);
//            return TupleTow.creat(res, false);
//        }
//        // 尝试 httpHandle
//        Value httpHandle = getHttpHandle(httpMethod, fucValue);
//        if (httpHandle == null) {
//            // httpHandle 不支持 - 跳过Script处理
//            return TupleTow.creat(null, true);
//        }
//        Value res = httpHandle.execute(httpContext);
//        return TupleTow.creat(res, false);
//    }
//
//    @Override
//    protected ScriptEngineInstance<Context, Value> borrowEngineInstance() throws Exception {
//        ScriptEngineInstance<Context, Value> scriptEngineInstance = super.borrowEngineInstance();
//        ScriptEngineContext<Context, Value> scriptEngineContext = scriptEngineInstance.getContext();
//        Context context = scriptEngineContext.getEngine();
//        context.enter();
//        return scriptEngineInstance;
//    }
//
//    @Override
//    protected void returnEngineInstance(ScriptEngineInstance<Context, Value> engineInstance) {
//        Context engine = null;
//        if (engineInstance != null && engineInstance.getContext() != null && engineInstance.getContext().getEngine() != null) {
//            engine = engineInstance.getContext().getEngine();
//        }
//        try {
//            if (engine != null) {
//                engine.leave();
//            }
//        } finally {
//            super.returnEngineInstance(engineInstance);
//        }
//    }
//
//    @Override
//    protected boolean resIsEmpty(Object res) {
//        if (res instanceof Value) {
//            return ((Value) res).isNull();
//        }
//        return res == null;
//    }
//
//    @Override
//    protected String serializeRes(Object res) {
//        return Jackson_Mapper.toJson(res);
//        // return JacksonMapper.getInstance().toJson(res);
//    }
//
//    @Override
//    protected void errHandle(Throwable e) throws Exception {
//        PolyglotException polyglotException = null;
//        if (e instanceof PolyglotException) {
//            polyglotException = (PolyglotException) e;
//        } else if (e.getCause() instanceof PolyglotException) {
//            polyglotException = (PolyglotException) e.getCause();
//        }
//        if (polyglotException != null && polyglotException.isHostException()) {
//            Throwable err = polyglotException.asHostException();
//            if (err == null) {
//                throw polyglotException;
//            }
//            e = err;
//        }
//        if (e instanceof Exception) {
//            throw (Exception) e;
//        }
//        throw new RuntimeException(e);
//    }
//
//    /**
//     * 获取 HttpHandle 函数
//     */
//    protected Value getHttpHandle(String httpMethod, Value fucValue) {
//        Set<String> methodSet = getHttpMethod(fucValue);
//        // 匹配 HttpMethod
//        if (!methodSet.isEmpty() && !methodSet.contains(StringUtils.upperCase(httpMethod))) {
//            return null;
//        }
//        // HttpHandle 是可执行函数
//        Value httpHandle = fucValue.getMember("handle");
//        if (httpHandle == null || !httpHandle.canExecute()) {
//            return null;
//        }
//        return httpHandle;
//    }
//
//    /**
//     * 获取 HttpHandle 函数支持的 HttpMethod
//     */
//    protected Set<String> getHttpMethod(Value fucValue) {
//        Value methods = fucValue.getMember("method");
//        if (methods == null) {
//            return Collections.emptySet();
//        }
//        if (methods.isString()) {
//            Set<String> methodSet = new HashSet<>(1);
//            methodSet.add(methods.asString());
//            return methodSet;
//        }
//        if (methods.hasArrayElements()) {
//            Set<String> methodSet = new HashSet<>((int) methods.getArraySize());
//            for (int i = 0; i < methods.getArraySize(); i++) {
//                Value item = methods.getArrayElement(i);
//                if (item != null && item.isString()) {
//                    methodSet.add(item.asString());
//                }
//            }
//            return methodSet;
//        }
//        return Collections.emptySet();
//    }
//}
