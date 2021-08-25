package org.clever.graaljs.fast.api.demo.engine;

import org.clever.graaljs.fast.api.demo.utils.JSUtils;
import org.clever.graaljs.fast.api.plugin.PutGlobalObjects;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/08/25 11:36 <br/>
 */
@Component
public class MyPutGlobalObjects implements PutGlobalObjects {
    @Override
    public void handle(Map<String, Object> contextMap) {
        contextMap.put("JSUtils", JSUtils.Instance);
    }
}
