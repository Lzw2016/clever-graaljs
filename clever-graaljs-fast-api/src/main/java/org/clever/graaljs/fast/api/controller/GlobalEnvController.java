package org.clever.graaljs.fast.api.controller;

import org.clever.graaljs.fast.api.config.FastApiConfig;
import org.clever.graaljs.fast.api.model.FastApiGlobalEnv;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/05 13:37 <br/>
 */
@RequestMapping("/fast_api/global_env")
@RestController
public class GlobalEnvController {
    private static final String VERSION = "0.0.1-SNAPSHOT";

    @Resource
    private FastApiConfig fastApiConfig;

    @GetMapping
    public FastApiGlobalEnv getGlobalEnv() {
        FastApiGlobalEnv env = new FastApiGlobalEnv();
        env.setVersion(VERSION);
        env.setNamespace(fastApiConfig.getNamespace());
        env.setApiPrefix(fastApiConfig.getMvc().getPrefix());
        return env;
    }
}
