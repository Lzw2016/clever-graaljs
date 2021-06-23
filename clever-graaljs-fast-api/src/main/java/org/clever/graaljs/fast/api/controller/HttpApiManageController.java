package org.clever.graaljs.fast.api.controller;

import org.clever.graaljs.core.utils.tree.SimpleTreeNode;
import org.clever.graaljs.fast.api.dto.response.ApiFileResourceRes;
import org.clever.graaljs.fast.api.service.HttpApiManageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/23 21:13 <br/>
 */
@RequestMapping("/fast_api/http_api_manage")
@RestController
public class HttpApiManageController {
    @Resource
    private HttpApiManageService httpApiManageService;

    @GetMapping("/http_api_tree")
    public List<SimpleTreeNode<ApiFileResourceRes>> getHttpApiTree() {
        return httpApiManageService.getHttpApiTree();
    }
}
