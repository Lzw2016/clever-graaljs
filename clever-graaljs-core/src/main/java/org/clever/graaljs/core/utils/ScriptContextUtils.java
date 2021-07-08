package org.clever.graaljs.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.clever.graaljs.core.GraalConstant;
import org.graalvm.polyglot.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/20 21:59 <br/>
 */
@Slf4j
public class ScriptContextUtils {
    /**
     * Context 默认选项
     */
    public static final Map<String, String> Context_Default_Options = new HashMap<String, String>() {{
        put("js.ecmascript-version", GraalConstant.ECMAScript_Version);
        // "js.nashorn-compat", "true", // EXPERIMENTAL | js.nashorn-compat -> 实验性特性需要删除
        // "js.experimental-foreign-object-prototype", "true" // 实验性特性需要删除
    }};

    private static final Source Object_Constructor_Source = Source.newBuilder(GraalConstant.Js_Language_Id, "Object", "Unnamed").cached(true).buildLiteral();
    private static final Source Array_Constructor_Source = Source.newBuilder(GraalConstant.Js_Language_Id, "Array", "Unnamed").cached(true).buildLiteral();
    private static final Source Json_Constructor_Source = Source.newBuilder(GraalConstant.Js_Language_Id, "JSON", "Unnamed").cached(true).buildLiteral();

    /**
     * 创建 HostAccess.Builder 对象
     *
     * @param denyAccessClass 不允许访问的Class
     */
    public static HostAccess.Builder getHostAccessBuilder(Set<Class<?>> denyAccessClass) {
        // 沙箱环境控制 - 定义JavaScript可以访问的Class(使用黑名单机制)
        HostAccess.Builder hostAccessBuilder = HostAccess.newBuilder();
        hostAccessBuilder.allowArrayAccess(true);
        hostAccessBuilder.allowListAccess(true);
        hostAccessBuilder.allowPublicAccess(true);
        hostAccessBuilder.allowAllImplementations(true);
        hostAccessBuilder.allowMapAccess(true);
        if (denyAccessClass == null) {
            denyAccessClass = GraalConstant.Default_Deny_Access_Class;
        } else {
            denyAccessClass.addAll(GraalConstant.Default_Deny_Access_Class);
        }
        addDenyAccess(hostAccessBuilder, denyAccessClass);
        return hostAccessBuilder;
    }

    /**
     * 创建 Context.Builder 对象
     *
     * @param engine          Engine对象
     * @param denyAccessClass 不允许访问的Class
     */
    public static Context.Builder getContextBuilder(Engine engine, Set<Class<?>> denyAccessClass) {
        Assert.notNull(engine, "参数engine不能为空");
        Context.Builder contextBuilder = Context.newBuilder(GraalConstant.Js_Language_Id)
                .engine(engine)
                .options(Context_Default_Options)
                // 不允许使用实验特性
                .allowExperimentalOptions(false)
                // 不允许多语言访问
                .allowPolyglotAccess(PolyglotAccess.NONE)
                // 默认允许所有行为
                .allowAllAccess(true)
                // 不允许JavaScript创建进程
                .allowCreateProcess(false)
                // 不允许JavaScript创建线程
                .allowCreateThread(false)
                // 不允许JavaScript访问环境变量
                .allowEnvironmentAccess(EnvironmentAccess.NONE)
                // 不允许JavaScript对主机的IO操作
                .allowIO(false)
                // 不允许JavaScript访问本机接口
                .allowNativeAccess(false)
                // 不允许JavaScript加载Class
                .allowHostClassLoading(false)
                // 定义JavaScript可以加载的Class
                // .allowHostClassLookup()
                // 定义JavaScript可以访问的Class
                // .allowHostAccess(HostAccess.ALL)
                // 限制JavaScript的资源使用(CPU)
                // .resourceLimits()
                ;
        // 沙箱环境控制 - 定义JavaScript可以访问的Class(使用黑名单机制)
        contextBuilder.allowHostAccess(getHostAccessBuilder(denyAccessClass).build());
        // 沙箱环境控制 - 限制JavaScript的资源使用
        final int limit = NumberUtils.toInt(System.getProperty(GraalConstant.ENGINE_EXECUTED_LIMIT), -1);
        if (limit > 0) {
            ResourceLimits resourceLimits = ResourceLimits.newBuilder()
                    .statementLimit(limit, null)
                    .onLimit(event -> {
                        log.warn("执行脚本超过连续执行语句的最大限制:limit={} | -> {}", limit, event.toString());
                    })
                    .build();
            contextBuilder.resourceLimits(resourceLimits);
        }
        return contextBuilder;
    }

    /**
     * 创建一个新的 Context
     *
     * @param engine Engine对象
     */
    public static Context.Builder getContextBuilder(Engine engine) {
        return getContextBuilder(engine, null);
    }

    /**
     * 创建一个新的 Context
     *
     * @param engine          Engine对象
     * @param denyAccessClass 不允许访问的Class
     */
    public static Context creatContext(Engine engine, Set<Class<?>> denyAccessClass) {
        return getContextBuilder(engine, denyAccessClass).build();
    }

    /**
     * 创建一个新的 Context
     *
     * @param engine Engine对象
     */
    public static Context creatContext(Engine engine) {
        return getContextBuilder(engine, null).build();
    }

    /**
     * 定义JavaScript不允许访问的Class
     */
    public static void addDenyAccess(HostAccess.Builder builder, Set<Class<?>> denyAccessClass) {
        for (Class<?> aClass : denyAccessClass) {
            if (aClass == null) {
                continue;
            }
            builder.denyAccess(aClass);
            // for (Field field : aClass.getFields()) {
            //     builder.allowAccess(field);
            // }
            // for (Method method : aClass.getMethods()) {
            //     builder.allowAccess(method);
            // }
        }
    }

    /**
     * 新建一个js 普通对象
     */
    public static Value newObject(Context context, Object... args) {
        Assert.notNull(context, "参数context不能为空");
        Value constructor;
        try {
            context.enter();
            constructor = context.eval(Object_Constructor_Source);
        } finally {
            context.leave();
        }
        return constructor.newInstance(args);
    }

    /**
     * 新建一个js 普通对象
     */
    public static Value newObject(Object... args) {
        Context context = Context.getCurrent();
        Assert.notNull(context, "参数context不能为空");
        Value constructor = context.eval(Object_Constructor_Source);
        return constructor.newInstance(args);
    }

    /**
     * 新建一个js 数组对象
     */
    public static Value newArray(Context context, Object... args) {
        Assert.notNull(context, "参数context不能为空");
        Value constructor;
        try {
            context.enter();
            constructor = context.eval(Array_Constructor_Source);
        } finally {
            context.leave();
        }
        return constructor.newInstance(args);
    }

    /**
     * 新建一个js 数组对象
     */
    public static Value newArray(Object... args) {
        Context context = Context.getCurrent();
        Assert.notNull(context, "参数context不能为空");
        Value constructor = context.eval(Array_Constructor_Source);
        return constructor.newInstance(args);
    }

    /**
     * 解析Json成为 Value 对象
     */
    public static Value parseJson(Context context, String json) {
        Assert.notNull(context, "参数context不能为空");
        Value constructor;
        try {
            context.enter();
            constructor = context.eval(Json_Constructor_Source);
        } finally {
            context.leave();
        }
        return constructor.invokeMember("parse", json);
    }
}
