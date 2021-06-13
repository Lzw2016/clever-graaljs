package org.clever.graaljs.spring.core.autoconfigure;

import org.clever.graaljs.spring.core.utils.spring.SpringContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/13 14:41 <br/>
 */
@Order
@Configuration
public class GraaljsSpringCoreAutoConfiguration {
    @Order
    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }
}
