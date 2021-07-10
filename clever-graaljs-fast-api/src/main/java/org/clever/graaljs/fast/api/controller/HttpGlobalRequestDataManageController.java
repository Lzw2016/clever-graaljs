package org.clever.graaljs.fast.api.controller;

import org.clever.graaljs.fast.api.dto.request.AddHttpGlobalRequestDataReq;
import org.clever.graaljs.fast.api.dto.response.HttpGlobalRequestDataRes;
import org.clever.graaljs.fast.api.service.HttpGlobalRequestDataManageService;
import org.clever.graaljs.spring.mvc.model.response.AjaxMessage;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/10 21:26 <br/>
 */
@RequestMapping("/fast_api/http_global_request_data_manage")
@RestController
public class HttpGlobalRequestDataManageController {
    @Resource
    private HttpGlobalRequestDataManageService httpGlobalRequestDataManageService;

    @GetMapping("/all")
    public List<HttpGlobalRequestDataRes> getAll() {
        return httpGlobalRequestDataManageService.getAll();
    }

    @PostMapping("/save_or_update")
    public HttpGlobalRequestDataRes saveOrUpdate(@RequestBody @Validated AddHttpGlobalRequestDataReq req) {
        return httpGlobalRequestDataManageService.saveOrUpdate(req);
    }

    @DeleteMapping("/delete")
    public AjaxMessage<Integer> delete(@RequestParam("id") Long id) {
        return new AjaxMessage<>(httpGlobalRequestDataManageService.delete(id), "操作成功");
    }
}
