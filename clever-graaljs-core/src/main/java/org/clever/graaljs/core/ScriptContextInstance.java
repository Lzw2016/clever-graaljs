package org.clever.graaljs.core;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.clever.graaljs.core.utils.Assert;
import org.clever.graaljs.core.utils.ScriptCodeUtils;
import org.clever.graaljs.core.utils.TupleTow;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import java.io.Closeable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/03 09:46 <br/>
 */
@Slf4j
public class ScriptContextInstance implements Closeable {
    private static final AtomicInteger FUC_COUNTER = new AtomicInteger(0);
    private static final int CODE_MAP_CAPACITY = 10;
    private static final ConcurrentHashMap<String, Integer> CODE_MAP = new ConcurrentHashMap<>(CODE_MAP_CAPACITY);

    /**
     * 缓存code的count值
     *
     * @return {@code TupleTow<压缩后的code, code的count值>}
     */
    private static synchronized TupleTow<String, Integer> cacheCodeCount(String code) {
        Assert.isNotBlank(code, "脚本代码不能为空");
        // 控制CODE_MAP容量
        final int preCount = FUC_COUNTER.get();
        if (preCount >= Integer.MAX_VALUE) {
            CODE_MAP.clear();
            FUC_COUNTER.set(0);
        }
        if (CODE_MAP.size() >= CODE_MAP_CAPACITY) {
            // 释放10%空间
            final int max = (preCount - CODE_MAP_CAPACITY) + (CODE_MAP_CAPACITY / 10);
            CODE_MAP.entrySet().removeIf(entry -> entry.getValue() <= max);
        }
        // 获取code count
        code = ScriptCodeUtils.compressCode(code, true);
        final int currentCount = CODE_MAP.computeIfAbsent(code, strCode -> FUC_COUNTER.incrementAndGet());
        return TupleTow.creat(code, currentCount);
    }

    /**
     * 代码缓存
     */
    private final ScriptObjectCache scriptObjectCache;
    /**
     * ScriptContext
     */
    @Getter
    private final Context context;

    public ScriptContextInstance(Context context) {
        Assert.notNull(context, "参数engine不能为空");
        this.scriptObjectCache = new ScriptObjectCache();
        this.context = context;
    }

    /**
     * 关闭ScriptContext释放资源
     */
    @Override
    public void close() {
        context.close(true);
        scriptObjectCache.clear();
    }

    /**
     * 向ScriptContext中加入全局变量
     *
     * @param identifier 全局变量名
     * @param value      全局变量值
     */
    public void putBindingsMember(String identifier, Object value) {
        context.getBindings(GraalConstant.Js_Language_Id).putMember(identifier, value);
    }

    /**
     * 向ScriptContext中获取全局变量
     *
     * @param identifier 全局变量名
     */
    public Value getBindingsMember(String identifier) {
        return context.getBindings(GraalConstant.Js_Language_Id).getMember(identifier);
    }

    /**
     * 把脚本代码包装成函数对象
     *
     * @param scriptCode 脚本代码
     * @return ScriptObject
     */
    public ScriptObject wrapFunctionObject(String scriptCode) {
        Assert.isNotBlank(scriptCode, "脚本代码不能为空");
        final TupleTow<String, Integer> function = cacheCodeCount(scriptCode);
        final String code = function.getValue1();
        final Integer count = function.getValue2();
        synchronized (scriptObjectCache) {
            ScriptObject scriptObject = scriptObjectCache.get(code);
            if (scriptObject != null) {
                return scriptObject;
            }
            final String fucCode = ScriptCodeUtils.wrapFunction(code, count);
            Source source = Source.newBuilder(GraalConstant.Js_Language_Id, fucCode, String.format("/__fuc_autogenerate_%s.js", count))
                    .cached(true)
                    .buildLiteral();
            Value value;
            try {
                context.enter();
                value = context.eval(source);
            } finally {
                context.leave();
            }
            Assert.isTrue(value != null && value.canExecute(), String.format("脚本代码不可以执行:\n%s\n", code));
            scriptObject = new ScriptObject(value);
            scriptObjectCache.put(code, scriptObject);
            return scriptObject;
        }
    }
}
