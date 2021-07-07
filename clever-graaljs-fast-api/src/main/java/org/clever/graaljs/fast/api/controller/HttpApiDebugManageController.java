package org.clever.graaljs.fast.api.controller;

import org.clever.graaljs.fast.api.dto.request.AddHttpApiDebugReq;
import org.clever.graaljs.fast.api.dto.request.UpdateHttpApiDebugReq;
import org.clever.graaljs.fast.api.dto.response.HttpApiDebugRes;
import org.clever.graaljs.fast.api.dto.response.HttpApiDebugTitleRes;
import org.clever.graaljs.fast.api.entity.HttpApiDebug;
import org.clever.graaljs.fast.api.service.HttpApiDebugManageService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/05 10:28 <br/>
 */
@RequestMapping("/fast_api/http_api_debug_manage")
@RestController
public class HttpApiDebugManageController {
    @Resource
    private HttpApiDebugManageService httpApiDebugManageService;

    @GetMapping("/title_list")
    public List<HttpApiDebugTitleRes> getTitleList(@RequestParam("httpApiId") Long httpApiId) {
        return httpApiDebugManageService.getTitleList(httpApiId);
    }

    @GetMapping("/http_api_debug")
    public HttpApiDebugRes getHttpApiDebug(@RequestParam("id") Long id) {
        return httpApiDebugManageService.getHttpApiDebug(id);
    }

    @PostMapping("/add_debug")
    public HttpApiDebug addHttpApiDebug(@RequestBody @Validated AddHttpApiDebugReq req) {
        return httpApiDebugManageService.addHttpApiDebug(req);
    }

    @PutMapping("/update_debug")
    public HttpApiDebug updateHttpApiDebug(@RequestBody @Validated UpdateHttpApiDebugReq req) {
        return httpApiDebugManageService.updateHttpApiDebug(req);
    }

    @DeleteMapping("/del_debug")
    public HttpApiDebug delHttpApiDebug(@RequestParam("id") Long id) {
        return httpApiDebugManageService.delHttpApiDebug(id);
    }
}
