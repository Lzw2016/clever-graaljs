package org.clever.graaljs.fast.api.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.clever.graaljs.core.ScriptEngineInstance;
import org.clever.graaljs.fast.api.config.FastApiConfig;
import org.clever.graaljs.fast.api.config.MvcConfig;
import org.clever.graaljs.fast.api.intercept.FastApiHttpInterceptor;
import org.clever.graaljs.fast.api.service.FileResourceCacheService;
import org.clever.graaljs.spring.mvc.ExceptionResolver;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/14 22:07 <br/>
 */
@AutoConfigureAfter({FastApiAutoConfiguration.class})
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
@EnableConfigurationProperties({FastApiConfig.class})
@Configuration
@Slf4j
public class FastApiWebMvcConfigurer implements WebMvcConfigurer {
    private final FastApiHttpInterceptor fastApiHttpInterceptor;

//    public FastApiWebMvcConfigurer(ObjectProvider<FastApiHttpInterceptor> fastApiHttpInterceptor) {
//        this.fastApiHttpInterceptor = Objects.requireNonNull(fastApiHttpInterceptor.getIfAvailable());
//    }

    public FastApiWebMvcConfigurer(
            FastApiConfig fastApiConfig,
            ExceptionResolver exceptionResolver,
            ScriptEngineInstance scriptEngineInstance,
            FileResourceCacheService fileResourceCacheServices,
            ObjectProvider<ConversionService> conversionService
    ) {
        final MvcConfig mvc = fastApiConfig.getMvc();
        this.fastApiHttpInterceptor = new FastApiHttpInterceptor(
                mvc.getPrefix(),
                mvc.getCors(),
                scriptEngineInstance,
                exceptionResolver,
                conversionService,
                fileResourceCacheServices
        );
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(fastApiHttpInterceptor).addPathPatterns("/**").order(Integer.MAX_VALUE);
    }
}
