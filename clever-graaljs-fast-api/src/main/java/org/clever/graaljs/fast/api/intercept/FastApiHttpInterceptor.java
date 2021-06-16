package org.clever.graaljs.fast.api.intercept;

import org.clever.graaljs.core.ScriptEngineInstance;
import org.clever.graaljs.core.utils.ScriptCodeUtils;
import org.clever.graaljs.core.utils.TupleTow;
import org.clever.graaljs.fast.api.model.HttpApiFileResource;
import org.clever.graaljs.fast.api.service.FileResourceCacheService;
import org.clever.graaljs.spring.mvc.ExceptionResolver;
import org.clever.graaljs.spring.mvc.HttpInterceptorScriptHandler;
import org.clever.graaljs.spring.mvc.ScriptHandlerCorsConfig;
import org.springframework.core.convert.ConversionService;

import javax.servlet.http.HttpServletRequest;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/14 11:26 <br/>
 */
public class FastApiHttpInterceptor extends HttpInterceptorScriptHandler {
    /**
     * 资源文件缓存对象
     */
    protected final FileResourceCacheService fileResourceCacheService;

    /**
     * @param supportPrefix            支持的请求前缀
     * @param corsConfig               跨域配置
     * @param scriptEngineInstance     脚本引擎实例
     * @param exceptionResolver        异常处理对象
     * @param conversionService        mvc请求数据转换对象
     * @param fileResourceCacheService 资源文件缓存对象
     */
    public FastApiHttpInterceptor(
            String supportPrefix,
            ScriptHandlerCorsConfig corsConfig,
            ScriptEngineInstance scriptEngineInstance,
            ExceptionResolver exceptionResolver,
            ConversionService conversionService,
            FileResourceCacheService fileResourceCacheService) {
        super(supportPrefix, corsConfig, scriptEngineInstance, exceptionResolver, conversionService);
        this.fileResourceCacheService = fileResourceCacheService;
    }

    @Override
    protected TupleTow<String, String> getScriptFileResource(HttpServletRequest request) {
        final String requestMapping = request.getRequestURI().substring(supportPrefix.length());
        final String requestMethod = request.getMethod();
        final HttpApiFileResource resource = fileResourceCacheService.getScriptFileResource(requestMapping, requestMethod);
        if (resource == null) {
            return null;
        }
        final String fullPath = resource.getPath() + resource.getName();
        final String content = resource.getContent();
        return TupleTow.creat(fullPath, ScriptCodeUtils.compressCode(content, true));
    }
}
