package org.clever.graaljs.fast.api.controller;

import org.clever.graaljs.core.utils.tree.SimpleTreeNode;
import org.clever.graaljs.fast.api.dto.request.AddJsJobReq;
import org.clever.graaljs.fast.api.dto.response.AddJsJobRes;
import org.clever.graaljs.fast.api.dto.response.DelJsJobRes;
import org.clever.graaljs.fast.api.dto.response.JobFileResourceRes;
import org.clever.graaljs.fast.api.dto.response.JsJobInfoRes;
import org.clever.graaljs.fast.api.service.TaskManageService;
import org.clever.task.core.TaskInstance;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/08/21 20:04 <br/>
 */
@RequestMapping("/fast_api/task_manage")
@RestController
public class TaskManageController {
    @Resource
    private TaskManageService taskManageService;
    @Resource
    private TaskInstance taskInstance;

    @GetMapping("/js_job_tree")
    public List<SimpleTreeNode<JobFileResourceRes>> getJsJobTree() {
        return taskManageService.getJsJobTree();
    }

    @GetMapping("/js_job_info")
    public JsJobInfoRes getJsJobInfo(@RequestParam("jobId") Long jobId) {
        return taskManageService.getJsJobInfo(jobId);
    }

    @PostMapping("/add_js_job")
    public AddJsJobRes addJsJob(@RequestBody @Validated AddJsJobReq req) {
        return taskManageService.addJsJob(req);
    }

    @DeleteMapping("/del_js_job")
    public DelJsJobRes delJsJob(@RequestParam("fileResourceId") Long fileResourceId) {
        return taskManageService.delJsJob(fileResourceId);
    }

    @PostMapping("/disable")
    public JsJobInfoRes disable(@RequestParam("jobId") Long jobId) {
        taskManageService.disable(jobId);
        return taskManageService.getJsJobInfo(jobId);
    }

    @PostMapping("/enable")
    public JsJobInfoRes enable(@RequestParam("jobId") Long jobId) {
        taskManageService.enable(jobId);
        return taskManageService.getJsJobInfo(jobId);
    }

    @PostMapping("/exec_job")
    public void execJob(@RequestParam("jobId") Long jobId) {
        taskInstance.execJob(jobId);
    }

    // TODO 更新定时任务
}
