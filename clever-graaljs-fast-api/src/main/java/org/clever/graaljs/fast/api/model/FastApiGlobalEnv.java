package org.clever.graaljs.fast.api.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/05 13:35 <br/>
 */
@Data
public class FastApiGlobalEnv implements Serializable {
    /**
     * Fast-Api版本
     */
    private String version;
    /**
     * 当前的命名空间
     */
    private String namespace;
    /**
     * 接口请求前缀
     */
    private String apiPrefix;
}
