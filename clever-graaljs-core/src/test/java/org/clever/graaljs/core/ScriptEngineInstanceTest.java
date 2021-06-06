package org.clever.graaljs.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.graalvm.polyglot.Value;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/06 20:12 <br/>
 */
@Slf4j
public class ScriptEngineInstanceTest {
    private String getSourceCode() throws IOException {
        return IOUtils.resourceToString("/t01.js", StandardCharsets.UTF_8);
    }

    @Test
    public void t01() throws IOException {
        final ScriptEngineConfig config = new ScriptEngineConfig();
        config.setMaxIdle(8);
        config.setMinIdle(0);
        config.setMaxTotal(32);
        config.setMaxWaitMillis(1000 * 8);
        ScriptEngineInstance scriptEngineInstance = new ScriptEngineInstance(config);
        log.info("EngineName-> {}", scriptEngineInstance.getEngineName());
        log.info("EngineVersion-> {}", scriptEngineInstance.getEngineVersion());
        log.info("LanguageVersion-> {}", scriptEngineInstance.getLanguageVersion());
        String code = getSourceCode();
        scriptEngineInstance.wrapFunctionAndEval(code, scriptObject -> {
            if (scriptObject.originalValue().canExecute()) {
                Map<String, Object> args = new HashMap<>();
                args.put("test", "lzw");
                Value res = scriptObject.originalValue().execute(args);
                log.info("res-> {}", res);
            }
        });
        scriptEngineInstance.close();
    }
}
