package org.clever.graaljs.spring.mvc;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.cors.CorsConfiguration;

import java.io.Serializable;
import java.time.Duration;
import java.util.List;

/**
 * 参考 org.springframework.web.cors.CorsConfiguration
 * <p>
 * 作者：lizw <br/>
 * 创建时间：2020/11/26 11:02 <br/>
 */
public class ScriptHandlerCorsConfig implements Serializable {
    /**
     * 设置允许的域，"*"为所有域
     */
    @Getter
    @Setter
    private List<String> allowedOrigins;

    /**
     * 设置允许客户端能发送的Http Method，"*"为所有Method
     */
    @Getter
    @Setter
    private List<String> allowedMethods;

    /**
     * 设置允许客户端能发送的Http Header，"*"为所有Header
     */
    @Getter
    @Setter
    private List<String> allowedHeaders;

    /**
     * 设置允许客户端能获取的的Http Header，不支持"*"
     */
    @Getter
    @Setter
    private List<String> exposedHeaders;

    /**
     * 设置是否允许凭据(Cookies)
     */
    @Getter
    @Setter
    private Boolean allowCredentials;

    /**
     * 预检请求的缓存时间(单位:秒)
     */
    @Getter
    @Setter
    private Duration maxAge = Duration.ofSeconds(1800);

    /**
     * Cors配置缓存
     */
    private CorsConfiguration corsConfiguration;

    public synchronized CorsConfiguration getCorsConfiguration() {
        return getCorsConfiguration(false);
    }

    public synchronized CorsConfiguration getCorsConfiguration(boolean forceRefresh) {
        if (forceRefresh || corsConfiguration == null) {
            corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowedOrigins(this.allowedOrigins);
            corsConfiguration.setAllowedMethods(this.allowedMethods);
            corsConfiguration.setAllowedHeaders(this.allowedHeaders);
            corsConfiguration.setAllowCredentials(this.allowCredentials);
            corsConfiguration.setMaxAge(this.maxAge.getSeconds());
            corsConfiguration.setExposedHeaders(this.exposedHeaders);
        }
        return corsConfiguration;
    }
}
