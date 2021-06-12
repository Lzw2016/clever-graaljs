package org.clever.graaljs.data.jdbc.builtin.wrap;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.clever.graaljs.core.ScriptEngineConfig;
import org.clever.graaljs.core.ScriptEngineInstance;
import org.clever.graaljs.core.ScriptObject;
import org.clever.graaljs.data.jdbc.BaseTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/12 21:11 <br/>
 */
@Slf4j
public class JdbcDatabaseTest {
    @Test
    public void t01() throws IOException {
        JdbcDatabase.Instance.setDefault("default", BaseTest.newJdbcDataSource());
        String code = IOUtils.resourceToString("/builtin/JdbcDatabaseTest.js", StandardCharsets.UTF_8);
        final ScriptEngineConfig config = new ScriptEngineConfig();
        final Map<String, Object> contextMap = new HashMap<>();
        contextMap.put("JdbcDatabase", JdbcDatabase.Instance);
        ScriptEngineInstance scriptEngineInstance = new ScriptEngineInstance(config, null, contextMap);
        scriptEngineInstance.wrapFunctionAndEval(code, (Consumer<ScriptObject>) ScriptObject::executeVoid);
        JdbcDatabase.Instance.delAll();
        scriptEngineInstance.close();
    }
}
