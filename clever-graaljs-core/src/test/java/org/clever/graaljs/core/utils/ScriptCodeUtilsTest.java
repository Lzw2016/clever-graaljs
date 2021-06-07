package org.clever.graaljs.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/06 18:52 <br/>
 */
@Slf4j
public class ScriptCodeUtilsTest {

    @Test
    public void wrapFunction() {
        log.info("-->\n{}\n<----", ScriptCodeUtils.wrapFunction("var a = 3;\r\n   var b = 3;", 1));
    }
}
