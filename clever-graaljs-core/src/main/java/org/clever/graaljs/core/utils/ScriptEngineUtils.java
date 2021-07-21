package org.clever.graaljs.core.utils;

import org.graalvm.polyglot.Engine;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/21 13:43 <br/>
 */
public class ScriptEngineUtils {
    /**
     * 默认选项
     */
    public static final Map<String, String> Engine_Default_Options = new HashMap<String, String>() {{
        // 开启预初始化Contexts - 实验性
        // put("engine.PreinitializeContexts", GraalConstant.Js_Language_Id);
        // 关闭警告日志
        put("engine.WarnInterpreterOnly", "false");
    }};

    public static Engine.Builder getEngineBuilder(Map<String, String> options) {
        final Engine.Builder builder = Engine.newBuilder()
                // 允许使用实验性的选项
                // .allowExperimentalOptions(true)
                // 使用系统属性
                .useSystemProperties(true)
                // 自定义引擎选项
                .options(Engine_Default_Options);
        if (options != null && !options.isEmpty()) {
            options.forEach(builder::option);
        }
        return builder;
    }

    public static Engine.Builder getEngineBuilder() {
        return getEngineBuilder(null);
    }

    public static Engine creatEngine() {
        return getEngineBuilder(null).build();
    }
}
