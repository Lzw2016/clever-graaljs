package org.clever.graaljs.fast.api.plugin;

import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/08/25 11:29 <br/>
 */
public interface PutGlobalObjects {

    /**
     * 设置脚本引擎全局对象
     */
    void handle(Map<String, Object> contextMap);
}
