package org.clever.graaljs.fast.api.controller;

import org.clever.graaljs.core.utils.tree.SimpleTreeNode;
import org.clever.graaljs.fast.api.dto.request.AddHttpApiReq;
import org.clever.graaljs.fast.api.dto.response.AddHttpApiRes;
import org.clever.graaljs.fast.api.dto.response.ApiFileResourceRes;
import org.clever.graaljs.fast.api.dto.response.HttpApiFileResourceRes;
import org.clever.graaljs.fast.api.entity.FileResource;
import org.clever.graaljs.fast.api.entity.HttpApi;
import org.clever.graaljs.fast.api.service.FileResourceManageService;
import org.clever.graaljs.fast.api.service.HttpApiManageService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @Resource
    private FileResourceManageService fileResourceManageService;

    @GetMapping("/http_api_tree")
    public List<SimpleTreeNode<ApiFileResourceRes>> getHttpApiTree() {
        return httpApiManageService.getHttpApiTree();
    }

    @GetMapping("/http_api_file_resource")
    public HttpApiFileResourceRes getHttpApiFileResource(@RequestParam("httpApiId") Long httpApiId) {
        HttpApi httpApi = httpApiManageService.getHttpApi(httpApiId);
        if (httpApi == null) {
            return null;
        }
        FileResource fileResource = fileResourceManageService.getFileResource(httpApi.getFileResourceId());
        if (fileResource == null) {
            return null;
        }
        HttpApiFileResourceRes res = new HttpApiFileResourceRes();
        res.setHttpApi(httpApi);
        res.setFileResource(fileResource);
        return res;
    }

    @PostMapping("/add_http_api")
    public AddHttpApiRes addHttpApi(@RequestBody @Validated AddHttpApiReq req) {
        return httpApiManageService.addHttpApi(req);
    }
}
