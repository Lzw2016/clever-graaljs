package org.clever.graaljs.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.clever.graaljs.core.pool.GraalSingleEngineFactory;
import org.clever.graaljs.core.utils.Assert;
import org.clever.graaljs.core.utils.ExceptionUtils;
import org.clever.graaljs.core.utils.ScriptEngineUtils;
import org.graalvm.polyglot.Engine;

import java.io.Closeable;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/03 09:57 <br/>
 */
@Slf4j
public class ScriptEngineInstance implements Closeable {
    /**
     * ScriptEngineFactory
     */
    private final GraalSingleEngineFactory engineFactory;
    /**
     * ScriptContext对象池
     */
    private final GenericObjectPool<ScriptContextInstance> pool;

    /**
     * @param config          ScriptContext对象池配置
     * @param denyAccessClass 不允许脚本访问的Class
     * @param contextMap      脚本引擎共有的全局变量
     * @param global          脚本引擎共享的全局遍历global
     */
    public ScriptEngineInstance(final ScriptEngineConfig config, Set<Class<?>> denyAccessClass, Map<String, Object> contextMap, Map<String, Object> global) {
        Engine engine = ScriptEngineUtils.creatEngine();
        this.engineFactory = new GraalSingleEngineFactory(engine, denyAccessClass, contextMap, global);
        this.pool = new GenericObjectPool<>(engineFactory, config.toGenericObjectPoolConfig());
    }

    /**
     * @param config          ScriptContext对象池配置
     * @param denyAccessClass 不允许脚本访问的Class
     * @param contextMap      脚本引擎共有的全局变量
     */
    public ScriptEngineInstance(final ScriptEngineConfig config, Set<Class<?>> denyAccessClass, Map<String, Object> contextMap) {
        Engine engine = ScriptEngineUtils.creatEngine();
        this.engineFactory = new GraalSingleEngineFactory(engine, denyAccessClass, contextMap);
        this.pool = new GenericObjectPool<>(engineFactory, config.toGenericObjectPoolConfig());
    }

    /**
     * @param config          ScriptContext对象池配置
     * @param denyAccessClass 不允许脚本访问的Class
     */
    public ScriptEngineInstance(final ScriptEngineConfig config, Set<Class<?>> denyAccessClass) {
        Engine engine = ScriptEngineUtils.creatEngine();
        this.engineFactory = new GraalSingleEngineFactory(engine, denyAccessClass);
        this.pool = new GenericObjectPool<>(engineFactory, config.toGenericObjectPoolConfig());
    }

    /**
     * @param config ScriptContext对象池配置
     */
    public ScriptEngineInstance(final ScriptEngineConfig config) {
        Engine engine = ScriptEngineUtils.creatEngine();
        this.engineFactory = new GraalSingleEngineFactory(engine);
        this.pool = new GenericObjectPool<>(engineFactory, config.toGenericObjectPoolConfig());
    }

    /**
     * @param config          ScriptContext对象池配置
     * @param denyAccessClass 不允许脚本访问的Class
     * @param contextMap      脚本引擎共有的全局变量
     * @param global          脚本引擎共享的全局遍历global
     */
    public ScriptEngineInstance(final GenericObjectPoolConfig<ScriptContextInstance> config, Set<Class<?>> denyAccessClass, Map<String, Object> contextMap, Map<String, Object> global) {
        Engine engine = ScriptEngineUtils.creatEngine();
        this.engineFactory = new GraalSingleEngineFactory(engine, denyAccessClass, contextMap, global);
        this.pool = new GenericObjectPool<>(engineFactory, config);
    }

    /**
     * @param config          ScriptContext对象池配置
     * @param denyAccessClass 不允许脚本访问的Class
     * @param contextMap      脚本引擎共有的全局变量
     */
    public ScriptEngineInstance(final GenericObjectPoolConfig<ScriptContextInstance> config, Set<Class<?>> denyAccessClass, Map<String, Object> contextMap) {
        Engine engine = ScriptEngineUtils.creatEngine();
        this.engineFactory = new GraalSingleEngineFactory(engine, denyAccessClass, contextMap);
        this.pool = new GenericObjectPool<>(engineFactory, config);
    }

    /**
     * @param config          ScriptContext对象池配置
     * @param denyAccessClass 不允许脚本访问的Class
     */
    public ScriptEngineInstance(final GenericObjectPoolConfig<ScriptContextInstance> config, Set<Class<?>> denyAccessClass) {
        Engine engine = ScriptEngineUtils.creatEngine();
        this.engineFactory = new GraalSingleEngineFactory(engine, denyAccessClass);
        this.pool = new GenericObjectPool<>(engineFactory, config);
    }

    /**
     * @param config ScriptContext对象池配置
     */
    public ScriptEngineInstance(final GenericObjectPoolConfig<ScriptContextInstance> config) {
        Engine engine = ScriptEngineUtils.creatEngine();
        this.engineFactory = new GraalSingleEngineFactory(engine);
        this.pool = new GenericObjectPool<>(engineFactory, config);
    }

    // --------------------------------------------------------------------------------------------------- Pool

    /**
     * 从池中获取引擎对象。使用完后必须使用 returnObject 方法返还获取的对象
     */
    private ScriptContextInstance borrowObject() throws Exception {
        return pool.borrowObject();
    }

    /**
     * 将引擎对象返还到池中。对象必须是从 borrowObject 方法获取到的
     */
    private void returnObject(ScriptContextInstance obj) {
        try {
            obj.getContext().resetLimits();
        } finally {
            pool.returnObject(obj);
        }
    }

    /**
     * 使池中的引擎对象失效，当获取到的对象被确定无效时（由于异常或其他问题），应该调用该方法
     */
    private void invalidateObject(ScriptContextInstance obj) throws Exception {
        pool.invalidateObject(obj);
    }

    /**
     * 池中当前闲置的引擎对象数量
     */
    public int getNumIdle() {
        return pool.getNumIdle();
    }

    /**
     * 当前从池中借出的引擎对象的数量
     */
    public int getNumActive() {
        return pool.getNumActive();
    }

    /**
     * 获取引擎实例状态
     */
    public ScriptEngineInstanceStatus getStatus() {
        ScriptEngineInstanceStatus status = new ScriptEngineInstanceStatus();
        // 配置
        status.setMaxTotal(pool.getMaxTotal());
        status.setMaxIdle(pool.getMaxIdle());
        status.setMinIdle(pool.getMinIdle());
        status.setMaxWaitMillis(pool.getMaxWaitMillis());
        status.setMinEvictableIdleTimeMillis(pool.getMinEvictableIdleTimeMillis());
        status.setTimeBetweenEvictionRunsMillis(pool.getTimeBetweenEvictionRunsMillis());
        // 状态
        status.setNumActive(pool.getNumActive());
        status.setNumIdle(pool.getNumIdle());
        status.setNumWaiters(pool.getNumWaiters());
        status.setActiveTimes(pool.getMeanActiveTimeMillis());
        status.setIdleTimes(pool.getMeanIdleTimeMillis());
        status.setWaitTimes(pool.getMeanBorrowWaitTimeMillis());
        status.setMaxBorrowWaitTimeMillis(pool.getMaxBorrowWaitTimeMillis());
        status.setCreatedCount(pool.getCreatedCount());
        status.setBorrowedCount(pool.getBorrowedCount());
        status.setReturnedCount(pool.getReturnedCount());
        status.setDestroyedCount(pool.getDestroyedCount());
        status.setDestroyedByBorrowValidationCount(pool.getDestroyedByBorrowValidationCount());
        status.setDestroyedByEvictorCount(pool.getDestroyedByEvictorCount());
        return status;
    }

    /**
     * 清除池中闲置的引擎对象
     */
    public void clear() {
        pool.clear();
    }

    /**
     * 关闭这个池，并释放与之相关的资源
     */
    @Override
    public void close() {
        pool.close();
        engineFactory.close();
    }

    // --------------------------------------------------------------------------------------------------- Engine

    /**
     * 引擎名称
     */
    public String getEngineName() {
        final String engineName = engineFactory.getEngine().getImplementationName();
        if (GraalConstant.Error_Engine_Name.equalsIgnoreCase(engineName)) {
            log.error("当前GraalJs未使用GraalVM compiler功能，请使用GraalVM compiler功能以提升性能(2 ~ 10倍性能提升)!");
        }
        return engineName;
    }

    /**
     * 引擎版本
     */
    public String getEngineVersion() {
        return engineFactory.getEngine().getVersion();
    }

    /**
     * 获取语言版本
     */
    public String getLanguageVersion() {
        return "ECMAScript Version: " + GraalConstant.ECMAScript_Version;
    }

    // --------------------------------------------------------------------------------------------------- Script

    /**
     * 把脚本代码包装成函数对象(预加载脚本代码)
     *
     * @param scriptCode 脚本代码
     */
    public void wrapFunction(String scriptCode) {
        Assert.isNotBlank(scriptCode, "脚本代码不能为空");
        try {
            ScriptContextInstance instance = borrowObject();
            try {
                instance.wrapFunctionObject(scriptCode);
            } finally {
                returnObject(instance);
            }
        } catch (Exception e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

    /**
     * 把脚本代码包装成函数对象，在回调函数中执行
     *
     * @param scriptCode 脚本代码
     * @param callable   回调函数(应该在此函数中执行脚本函数)
     */
    public void wrapFunctionAndEval(String scriptCode, Consumer<ScriptObject> callable) {
        Assert.isNotBlank(scriptCode, "脚本代码不能为空");
        Assert.notNull(callable, "参数callable不能为空");
        try {
            ScriptContextInstance instance = borrowObject();
            try {
                callable.accept(instance.wrapFunctionObject(scriptCode));
            } finally {
                returnObject(instance);
            }
        } catch (Exception e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

    /**
     * 把脚本代码包装成函数对象，在回调函数中执行
     *
     * @param scriptCode 脚本代码
     * @param callable   回调函数(应该在此函数中执行脚本函数)
     * @param <T>        返回值类型
     */
    public <T> T wrapFunctionAndEval(String scriptCode, Function<ScriptObject, T> callable) {
        Assert.isNotBlank(scriptCode, "脚本代码不能为空");
        Assert.notNull(callable, "参数callable不能为空");
        try {
            ScriptContextInstance instance = borrowObject();
            try {
                ScriptObject scriptObject = instance.wrapFunctionObject(scriptCode);
                return callable.apply(scriptObject);
            } finally {
                returnObject(instance);
            }
        } catch (Exception e) {
            throw ExceptionUtils.unchecked(e);
        }
    }
}
