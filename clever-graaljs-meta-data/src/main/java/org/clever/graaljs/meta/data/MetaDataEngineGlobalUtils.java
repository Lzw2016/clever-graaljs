package org.clever.graaljs.meta.data;

import org.clever.graaljs.meta.data.builtin.wrap.MateDataManage;

import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/16 17:44 <br/>
 */
public class MetaDataEngineGlobalUtils {
    /**
     * 设置引擎默认的全局对象
     */
    public static void putGlobalObjects(Map<String, Object> contextMap) {
        // mate-data
        contextMap.put("MateDataManage", MateDataManage.Instance);
    }
}
