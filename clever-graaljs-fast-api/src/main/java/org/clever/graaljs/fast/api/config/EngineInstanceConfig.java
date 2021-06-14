package org.clever.graaljs.fast.api.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.clever.graaljs.core.ScriptEngineConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Script引擎池配置
 * <p>
 * 作者：lizw <br/>
 * 创建时间：2020/09/11 18:46 <br/>
 */
@ConfigurationProperties(prefix = Constant.ENGINE_INSTANCE_CONFIG)
@EqualsAndHashCode(callSuper = true)
@Data
public class EngineInstanceConfig extends ScriptEngineConfig {
}
