package org.clever.graaljs.fast.api.config;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/09/11 21:22 <br/>
 */
public interface Constant {
    String CONFIG_ROOT = "clever.fast-api";

    String MVC_HANDLER_CONFIG = CONFIG_ROOT + ".mvc-handler";

    String ENGINE_INSTANCE_CONFIG = CONFIG_ROOT + ".engine-instance-config";

    String MULTIPLE_JDBC_CONFIG = CONFIG_ROOT + ".multiple-jdbc";

    String MULTIPLE_REDIS_CONFIG = CONFIG_ROOT + ".multiple-redis";
}
