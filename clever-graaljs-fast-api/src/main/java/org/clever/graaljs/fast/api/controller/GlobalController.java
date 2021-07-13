package org.clever.graaljs.fast.api.controller;

import org.clever.graaljs.core.ScriptEngineInstance;
import org.clever.graaljs.core.ScriptEngineInstanceStatus;
import org.clever.graaljs.fast.api.config.FastApiConfig;
import org.clever.graaljs.fast.api.model.FastApiGlobalEnv;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/05 13:37 <br/>
 */
@RequestMapping("/fast_api")
@Controller
public class GlobalController {
    private static final String VERSION = "0.0.1-SNAPSHOT";

    @Resource
    private FastApiConfig fastApiConfig;
    @Resource
    private ScriptEngineInstance scriptEngineInstance;

    @GetMapping
    public String indexPage() {
        // noinspection SpringMVCViewInspection
        return "/webjars/fast-api/index.html";
    }

    @ResponseBody
    @GetMapping("/global_env")
    public FastApiGlobalEnv getGlobalEnv() {
        FastApiGlobalEnv env = new FastApiGlobalEnv();
        env.setVersion(VERSION);
        env.setNamespace(fastApiConfig.getNamespace());
        env.setApiPrefix(fastApiConfig.getMvc().getPrefix());
        return env;
    }

    @ResponseBody
    @GetMapping("/script_engine_status")
    public ScriptEngineInstanceStatus getStatus() {
        return scriptEngineInstance.getStatus();
    }
}
