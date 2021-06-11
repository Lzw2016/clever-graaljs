package org.clever.graaljs.core.builtin;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.clever.graaljs.core.ScriptEngineConfig;
import org.clever.graaljs.core.ScriptEngineInstance;
import org.clever.graaljs.core.ScriptObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/11 15:37 <br/>
 */
@Slf4j
public class CryptoUtilsTest {
    @Test
    public void t01() throws IOException {
        String code = IOUtils.resourceToString("/builtin/CryptoUtilsTest.js", StandardCharsets.UTF_8);
        final ScriptEngineConfig config = new ScriptEngineConfig();
        ScriptEngineInstance scriptEngineInstance = new ScriptEngineInstance(config);
        scriptEngineInstance.wrapFunctionAndEval(code, (Consumer<ScriptObject>) ScriptObject::executeVoid);
        scriptEngineInstance.close();
    }
}
