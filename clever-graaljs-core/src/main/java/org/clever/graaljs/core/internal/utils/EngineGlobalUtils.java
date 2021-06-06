package org.clever.graaljs.core.internal.utils;

import org.clever.graaljs.core.internal.GraalInterop;
import org.clever.graaljs.core.internal.GraalLoggerFactory;
import org.clever.graaljs.core.internal.LoggerConsole;
import org.clever.graaljs.core.internal.support.GraalObjectToString;

import java.util.Map;

public class EngineGlobalUtils {

    public static void putGlobalObjects(Map<String, Object> contextMap) {
        LoggerConsole.Instance.setObjectToString(GraalObjectToString.Instance);
        contextMap.put("console", LoggerConsole.Instance);
        contextMap.put("print", LoggerConsole.Instance);
        contextMap.put("LoggerFactory", GraalLoggerFactory.Instance);
        contextMap.put("Interop", GraalInterop.Instance);
    }
}
