package org.clever.graaljs.fast.api.config;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.spring.mvc.ScriptHandlerCorsConfig;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.io.Serializable;

/**
 * mvc配置
 * <p>
 * 作者：lizw <br/>
 * 创建时间：2020/09/11 19:10 <br/>
 */
@Data
public class MvcConfig implements Serializable {
    /**
     * 支持的请求前缀
     */
    private String prefix = StringUtils.EMPTY;
    /**
     * 跨域配置
     */
    @NestedConfigurationProperty
    private ScriptHandlerCorsConfig cors = new ScriptHandlerCorsConfig();
}
