package org.clever.graaljs.data.redis.builtin.wrap;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.clever.graaljs.core.ScriptEngineConfig;
import org.clever.graaljs.core.ScriptEngineInstance;
import org.clever.graaljs.core.ScriptObject;
import org.clever.graaljs.data.redis.BaseTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/13 00:31 <br/>
 */
@Slf4j
public class RedisDatabaseTest {
    @Test
    public void t01() throws IOException {
        RedisDatabase.Instance.setDefault("default", BaseTest.newRedisDataSource());
        String code = IOUtils.resourceToString("/builtin/RedisDatabaseTest.js", StandardCharsets.UTF_8);
        final ScriptEngineConfig config = new ScriptEngineConfig();
        final Map<String, Object> contextMap = new HashMap<>();
        contextMap.put("RedisDatabase", RedisDatabase.Instance);
        ScriptEngineInstance scriptEngineInstance = new ScriptEngineInstance(config, null, contextMap);
        scriptEngineInstance.wrapFunctionAndEval(code, (Consumer<ScriptObject>) ScriptObject::executeVoid);
        RedisDatabase.Instance.delAll();
        scriptEngineInstance.close();
    }
}
