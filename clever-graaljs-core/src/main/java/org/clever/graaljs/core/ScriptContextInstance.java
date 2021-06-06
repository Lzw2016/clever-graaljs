package org.clever.graaljs.core;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.clever.graaljs.core.utils.Assert;
import org.clever.graaljs.core.utils.ScriptCodeUtils;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import java.io.Closeable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/03 09:46 <br/>
 */
@Slf4j
public class ScriptContextInstance implements Closeable {
    private final static AtomicLong FUC_COUNTER = new AtomicLong(0);

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
        final long counter = Math.abs(FUC_COUNTER.incrementAndGet());
        final String code = ScriptCodeUtils.wrapFunction(scriptCode, counter);
        synchronized (scriptObjectCache) {
            ScriptObject scriptObject = scriptObjectCache.get(code);
            if (scriptObject != null) {
                return scriptObject;
            }
            Source source = Source.newBuilder(GraalConstant.Js_Language_Id, code, String.format("/__fuc_autogenerate_%s.js", counter))
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
