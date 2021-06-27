package org.clever.graaljs.fast.api.controller;

import org.clever.graaljs.fast.api.dto.request.SaveFileContentReq;
import org.clever.graaljs.fast.api.entity.FileResource;
import org.clever.graaljs.fast.api.service.FileResourceManageService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/26 21:27 <br/>
 */
@RequestMapping("/fast_api/file_resource_manage")
@RestController
public class FileResourceManageController {
    @Resource
    private FileResourceManageService fileResourceManageService;

    @GetMapping("/file_resource")
    public FileResource getFileResource(@RequestParam("id") Long id) {
        return fileResourceManageService.getFileResource(id);
    }

    @PutMapping("/save_file_content")
    public FileResource saveFileContent(@RequestBody @Validated SaveFileContentReq req) {
        return fileResourceManageService.saveFileContent(req);
    }
}
