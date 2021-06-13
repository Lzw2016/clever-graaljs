package org.clever.graaljs.core.pool;

import org.clever.graaljs.core.builtin.*;
import org.clever.graaljs.core.builtin.wrap.HttpUtils;
import org.clever.graaljs.core.builtin.wrap.PinyinUtils;
import org.clever.graaljs.core.builtin.wrap.TreeUtils;
import org.clever.graaljs.core.builtin.wrap.ZxingUtils;
import org.clever.graaljs.core.internal.GraalInterop;
import org.clever.graaljs.core.internal.GraalLoggerFactory;
import org.clever.graaljs.core.internal.LoggerConsole;
import org.clever.graaljs.core.internal.support.GraalObjectToString;

import java.util.Map;

public class EngineGlobalUtils {
    /**
     * 设置引擎默认的全局对象
     */
    public static void putGlobalObjects(Map<String, Object> contextMap) {
        LoggerConsole.Instance.setObjectToString(GraalObjectToString.Instance);
        // 基础
        contextMap.put("console", LoggerConsole.Instance);
        contextMap.put("print", LoggerConsole.Instance);
        contextMap.put("LoggerFactory", GraalLoggerFactory.Instance);
        contextMap.put("Interop", GraalInterop.Instance);
        // 通用
        contextMap.put("AssertUtils", AssertUtils.Instance);
        contextMap.put("CommonUtils", CommonUtils.Instance);
        contextMap.put("ThreadUtils", ThreadUtils.Instance);
        contextMap.put("StringUtils", StringUtils.Instance);
        contextMap.put("DateUtils", DateUtils.Instance);
        contextMap.put("HttpUtils", HttpUtils.Instance);
        contextMap.put("JsonUtils", JsonUtils.Instance);
        contextMap.put("XmlUtils", XmlUtils.Instance);
        contextMap.put("IOUtils", IOUtils.Instance);
        contextMap.put("IDGenerateUtils", IDGenerateUtils.Instance);
        contextMap.put("EncodeDecodeUtils", EncodeDecodeUtils.Instance);
        contextMap.put("UnderlineToCamelUtils", UnderlineToCamelUtils.Instance);
        // 特定场景
        contextMap.put("TreeUtils", TreeUtils.Instance);
        contextMap.put("ImageValidateUtils", ImageValidateUtils.Instance);
        contextMap.put("ZxingUtils", ZxingUtils.Instance);
        contextMap.put("DataSizeUtils", DataSizeUtils.Instance);
        contextMap.put("CryptoUtils", CryptoUtils.Instance);
        contextMap.put("DigestUtils", DigestUtils.Instance);
        contextMap.put("IPAddressUtils", IPAddressUtils.Instance);
        contextMap.put("PinyinUtils", PinyinUtils.Instance);
        contextMap.put("IDCardUtils", IDCardUtils.Instance);
        contextMap.put("RMBUtils", RMBUtils.Instance);
    }
}
