package org.clever.graaljs.fast.api.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.clever.graaljs.fast.api.intercept.FastApiHttpInterceptor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Objects;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/14 22:07 <br/>
 */
@AutoConfigureAfter({FastApiAutoConfiguration.class})
@Configuration
@Order
@Slf4j
public class FastApiWebMvcConfigurer implements WebMvcConfigurer {
    private final FastApiHttpInterceptor fastApiHttpInterceptor;

    public FastApiWebMvcConfigurer(ObjectProvider<FastApiHttpInterceptor> fastApiHttpInterceptor) {
        this.fastApiHttpInterceptor = Objects.requireNonNull(fastApiHttpInterceptor.getIfAvailable());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(fastApiHttpInterceptor).addPathPatterns("/**").order(Integer.MAX_VALUE);
    }
}
