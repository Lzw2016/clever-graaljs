package org.clever.graaljs.core.pool;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.clever.graaljs.core.GraalConstant;
import org.clever.graaljs.core.ScriptContextInstance;
import org.clever.graaljs.core.utils.Assert;
import org.clever.graaljs.core.utils.ScriptContextUtils;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.HostAccess;

import java.io.Closeable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 单个Engine对象的 GraalScriptEngineInstance 创建工厂
 * <p>
 * 作者：lizw <br/>
 * 创建时间：2020/08/24 19:24 <br/>
 */
@Slf4j
public class GraalSingleEngineFactory extends BasePooledObjectFactory<ScriptContextInstance> implements Closeable {
    // private final static String TEST_SCRIPT_CONTEXT_INSTANCE = "return 1;";
    /**
     * 脚本引擎编号
     */
    private final static String COUNTER_NAME = "org.clever.graaljs.core.ScriptContextInstance#COUNTER";
    private final static AtomicLong COUNTER = new AtomicLong(0);

    /**
     * ScriptEngine
     */
    @Getter
    private final Engine engine;
    /**
     * ScriptEngine访问Java对象策略
     */
    @Getter
    private final HostAccess hostAccess;
    /**
     * 自定义引擎全局对象
     */
    @Getter
    private final ConcurrentMap<String, Object> contextMap = new ConcurrentHashMap<>(32);
    /**
     * 引擎全局变量
     */
    @Getter
    private final ConcurrentMap<String, Object> global = new ConcurrentHashMap<>(16);

    /**
     * @param engine          ScriptEngine
     * @param denyAccessClass 不允许脚本访问的Class
     * @param contextMap      脚本引擎共有的全局变量
     * @param global          脚本引擎共享的全局遍历global
     */
    public GraalSingleEngineFactory(Engine engine, Set<Class<?>> denyAccessClass, Map<String, Object> contextMap, Map<String, Object> global) {
        Assert.notNull(engine, "参数engine不能为空");
        this.engine = engine;
        this.hostAccess = ScriptContextUtils.getHostAccessBuilder(denyAccessClass).build();
        if (contextMap != null && !contextMap.isEmpty()) {
            this.contextMap.putAll(contextMap);
        }
        EngineGlobalUtils.putGlobalObjects(this.contextMap);
        if (global != null && !global.isEmpty()) {
            this.global.putAll(global);
        }
    }

    /**
     * @param engine          ScriptEngine
     * @param denyAccessClass 不允许脚本访问的Class
     * @param contextMap      脚本引擎共有的全局变量
     */
    public GraalSingleEngineFactory(Engine engine, Set<Class<?>> denyAccessClass, Map<String, Object> contextMap) {
        this(engine, denyAccessClass, contextMap, null);
    }

    /**
     * @param engine          ScriptEngine
     * @param denyAccessClass 不允许脚本访问的Class
     */
    public GraalSingleEngineFactory(Engine engine, Set<Class<?>> denyAccessClass) {
        this(engine, denyAccessClass, null, null);
    }

    /**
     * @param engine ScriptEngine
     */
    public GraalSingleEngineFactory(Engine engine) {
        this(engine, null, null, null);
    }

    /**
     * 创建一个新的对象
     */
    @Override
    public ScriptContextInstance create() {
        Context context = ScriptContextUtils.getContextBuilder(engine, null)
                .allowHostAccess(hostAccess)
                .build();
        ScriptContextInstance instance = new ScriptContextInstance(context);
        long counter = COUNTER.incrementAndGet();
        if (counter < 0) {
            synchronized (COUNTER) {
                counter = COUNTER.get();
                if (counter < 0) {
                    COUNTER.set(1);
                    counter = 1;
                } else {
                    counter = COUNTER.incrementAndGet();
                }
            }
        }
        instance.putBindingsMember(COUNTER_NAME, counter);
        log.debug("创建 GraalScriptEngineInstance | EngineInstance编号={}", counter);
        return instance;
    }

    /**
     * 封装为池化对象
     */
    @Override
    public PooledObject<ScriptContextInstance> wrap(ScriptContextInstance obj) {
        return new DefaultPooledObject<>(obj);
    }

    /**
     * 验证对象是否可用
     */
    @Override
    public boolean validateObject(PooledObject<ScriptContextInstance> p) {
        try {
            p.getObject().getBindingsMember(COUNTER_NAME);
            // p.getObject().wrapFunctionObject(TEST_SCRIPT_CONTEXT_INSTANCE).executeVoid();
        } catch (Exception e) {
            log.warn("验证 ScriptContextInstance | 结果:验证失败");
            return false;
        }
        return true;
    }

    /**
     * 激活对象，从池中取对象时会调用此方法
     */
    @Override
    public void activateObject(PooledObject<ScriptContextInstance> p) {
        contextMap.forEach((identifier, value) -> p.getObject().putBindingsMember(identifier, value));
        p.getObject().putBindingsMember(GraalConstant.Engine_Global, global);
        Object counter = p.getObject().getBindingsMember(COUNTER_NAME);
        log.debug("初始化 ScriptContextInstance 全局变量 | EngineInstance编号={}", counter);
    }

    /**
     * 钝化对象，向池中返还对象时会调用此方法
     */
    @Override
    public void passivateObject(PooledObject<ScriptContextInstance> p) {
        // log.debug("passivateObject");
    }

    /**
     * 销毁对象
     */
    @Override
    public void destroyObject(PooledObject<ScriptContextInstance> p) {
        if (p.getObject() != null) {
            p.getObject().close();
            Object counter = p.getObject().getBindingsMember(COUNTER_NAME);
            log.debug("关闭 GraalScriptEngineInstance | EngineInstance编号={}", counter);
        }
    }

    /**
     * 释放 Engine 对象
     */
    @Override
    public void close() {
        engine.close(true);
    }
}
