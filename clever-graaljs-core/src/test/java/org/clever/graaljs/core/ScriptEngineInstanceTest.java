package org.clever.graaljs.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.graalvm.polyglot.Value;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/06 20:12 <br/>
 */
@Slf4j
public class ScriptEngineInstanceTest {
    private String getSourceCode(String name) throws IOException {
        return IOUtils.resourceToString(name, StandardCharsets.UTF_8);
    }

    @Test
    public void t01() throws IOException {
        String code = getSourceCode("/t01.js");
        final ScriptEngineConfig config = new ScriptEngineConfig();
        config.setMaxIdle(8);
        config.setMinIdle(0);
        config.setMaxTotal(32);
        config.setMaxWaitMillis(1000 * 8);
        ScriptEngineInstance scriptEngineInstance = new ScriptEngineInstance(config);
        log.info("EngineName-> {}", scriptEngineInstance.getEngineName());
        log.info("EngineVersion-> {}", scriptEngineInstance.getEngineVersion());
        log.info("LanguageVersion-> {}", scriptEngineInstance.getLanguageVersion());
        scriptEngineInstance.wrapFunctionAndEval(code, scriptObject -> {
            if (scriptObject.originalValue().canExecute()) {
                Map<String, Object> args = new HashMap<>();
                args.put("test", "lzw");
                Map<String, Object> map = new HashMap<>();
                args.put("map", map);
                map.put("test", "aaa");
                Map<String, Object> map_1 = new HashMap<>();
                map.put("map", map_1);
                map_1.put("test", "@@@@##$#$$#$#");
                Value res = scriptObject.execute(args);
                log.info("res-> {}", res);
            }
        });
        scriptEngineInstance.close();
    }

    @Test
    public void t02() throws IOException {
        String code = getSourceCode("/t02.js");
        final ScriptEngineConfig config = new ScriptEngineConfig();
        ScriptEngineInstance scriptEngineInstance = new ScriptEngineInstance(config);
        scriptEngineInstance.wrapFunctionAndEval(code, (Consumer<ScriptObject>) ScriptObject::executeVoid);
        scriptEngineInstance.close();
    }

    @Test
    public void t03() throws IOException {
        String code = getSourceCode("/t03.js");
        final ScriptEngineConfig config = new ScriptEngineConfig();
        ScriptEngineInstance scriptEngineInstance = new ScriptEngineInstance(config);
        scriptEngineInstance.wrapFunctionAndEval(code, (Consumer<ScriptObject>) ScriptObject::executeVoid);
        scriptEngineInstance.close();
    }

    @Test
    public void t04() throws IOException {
        String code = getSourceCode("/t04.js");
        final ScriptEngineConfig config = new ScriptEngineConfig();
        ScriptEngineInstance scriptEngineInstance = new ScriptEngineInstance(config);
        scriptEngineInstance.wrapFunctionAndEval(code, (Consumer<ScriptObject>) ScriptObject::executeVoid);
        scriptEngineInstance.close();
    }

    @Test
    public void t05() throws IOException {
        String code = getSourceCode("/t05.js");
        Map<String, Object> contextMap = new HashMap<>();
        contextMap.put("JavaInterop", JavaInteropTest.Instance);
        Map<String, Object> global = new HashMap<>();
        global.put("a", "aa");
        final ScriptEngineConfig config = new ScriptEngineConfig();
        ScriptEngineInstance scriptEngineInstance = new ScriptEngineInstance(config, null, contextMap, global);
        scriptEngineInstance.wrapFunctionAndEval(code, (Consumer<ScriptObject>) ScriptObject::executeVoid);
        scriptEngineInstance.close();
    }

//    @Test
//    public void t06() throws IOException, InterruptedException {
//        String code = getSourceCode("/t06.js");
//        Map<String, Object> contextMap = new HashMap<>();
//        contextMap.put("JavaInterop", JavaInteropTest.Instance);
//        Map<String, Object> global = new HashMap<>();
//        global.put("a", "aa");
//        final ScriptEngineConfig config = new ScriptEngineConfig();
//        ScriptEngineInstance scriptEngineInstance = new ScriptEngineInstance(config, null, contextMap, global);
//        Value value = scriptEngineInstance.wrapFunctionAndEval(code, (Function<ScriptObject, Value>) ScriptObject::execute);
//        final int count = 16;
//        List<Thread> list = new ArrayList<>(count);
//        for (int i = 0; i < count; i++) {
//            list.add(new Thread(() -> {
//                log.info("### -> {}", GraalObjectToString.Instance.toString(value));
//            }));
//        }
//        for (Thread thread : list) {
//            thread.start();
//        }
//        Thread.sleep(5 * 1000);
//        scriptEngineInstance.close();
//        list.clear();
//        for (int i = 0; i < count; i++) {
//            list.add(new Thread(() -> {
//                log.info("### -> {}", GraalObjectToString.Instance.toString(value));
//            }));
//        }
//        for (Thread thread : list) {
//            thread.start();
//        }
//        Thread.sleep(5 * 1000);
//        log.info("### END");
//    }
}
