package org.clever.graaljs.fast.api.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.clever.graaljs.core.utils.ExceptionUtils;
import org.clever.graaljs.fast.api.config.FastApiConfig;
import org.clever.graaljs.spring.mvc.DefaultExceptionResolver;
import org.clever.graaljs.spring.mvc.ScriptHandlerCorsConfig;
import org.clever.graaljs.spring.mvc.builtin.adapter.HttpContext;
import org.clever.hot.reload.spring.HotReloadExtendUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.cors.CorsProcessor;
import org.springframework.web.cors.DefaultCorsProcessor;

import java.io.IOException;
import java.util.Objects;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/08/28 13:03 <br/>
 */
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
@EnableConfigurationProperties({FastApiConfig.class})
@Configuration
@Slf4j
public class HotReloadInitCommandLineRunner implements CommandLineRunner {
    private final FastApiConfig fastApiConfig;
    private final ObjectProvider<ConversionService> conversionService;

    public HotReloadInitCommandLineRunner(FastApiConfig fastApiConfig, ObjectProvider<ConversionService> conversionService) {
        this.fastApiConfig = fastApiConfig;
        this.conversionService = conversionService;
    }

    @Override
    public void run(String... args) {
        HotReloadExtendUtils.EXCEPTION_RESOLVER = DefaultExceptionResolver.Instance::resolveException;
        HotReloadExtendUtils.CONSTRUCTOR_METHOD_PARAMETER = (springContextHolder, request, response, routeInfo, method) -> {
            final Object[] methodArgs = new Object[method.getParameterTypes().length];
            final Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                Class<?> parameterType = parameterTypes[i];
                if (Objects.equals(parameterType, HttpContext.class)) {
                    methodArgs[i] = new HttpContext(request, response, conversionService.getIfUnique());
                } else {
                    ObjectProvider<?> objectProvider = springContextHolder.getBeanProvider(parameterType);
                    methodArgs[i] = objectProvider.getIfAvailable();
                }
            }
            return methodArgs;
        };
        final CorsProcessor corsProcessor = new DefaultCorsProcessor();
        final ScriptHandlerCorsConfig corsConfig = fastApiConfig.getMvc().getCors() == null ? new ScriptHandlerCorsConfig() : fastApiConfig.getMvc().getCors();
        HotReloadExtendUtils.CORS_PROCESSOR = (request, response) -> {
            try {
                return corsProcessor.processRequest(corsConfig.getCorsConfiguration(), request, response);
            } catch (IOException e) {
                throw ExceptionUtils.unchecked(e);
            }
        };
    }
}
