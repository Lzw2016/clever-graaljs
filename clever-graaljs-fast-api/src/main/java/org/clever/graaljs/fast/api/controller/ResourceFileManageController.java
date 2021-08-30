package org.clever.graaljs.fast.api.controller;

import org.clever.graaljs.core.utils.tree.SimpleTreeNode;
import org.clever.graaljs.fast.api.dto.response.FileResourceTreeNodeRes;
import org.clever.graaljs.fast.api.service.ResourceFileManageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/28 16:50 <br/>
 */
@RequestMapping("/fast_api/resource_file_manage")
@RestController
public class ResourceFileManageController {
    @Resource
    private ResourceFileManageService resourceFileManageService;

    @GetMapping("/file_tree")
    public List<SimpleTreeNode<FileResourceTreeNodeRes>> getFileTree(@RequestParam("module") Integer module) {
        return resourceFileManageService.getFileTree(module);
    }
}
