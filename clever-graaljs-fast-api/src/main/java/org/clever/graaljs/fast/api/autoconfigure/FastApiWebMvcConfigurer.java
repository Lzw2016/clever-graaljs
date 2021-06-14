package org.clever.graaljs.fast.api.autoconfigure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/14 22:07 <br/>
 */
@Configuration
//@AutoConfigureAfter({AutoConfigureMvcHandler.class})
public class FastApiWebMvcConfigurer implements WebMvcConfigurer {
//    private final HttpRequestScriptHandler<?, ?> httpRequestScriptHandler;
//
//    public FastApiAutoConfiguration(ObjectProvider<HttpRequestScriptHandler<?, ?>> httpRequestScriptHandler) {
//        this.httpRequestScriptHandler = Objects.requireNonNull(httpRequestScriptHandler.getIfAvailable());
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(httpRequestScriptHandler).addPathPatterns("/**").order(Integer.MAX_VALUE);
//    }
}
