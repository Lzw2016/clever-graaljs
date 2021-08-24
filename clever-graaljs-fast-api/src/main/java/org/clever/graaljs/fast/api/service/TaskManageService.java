package org.clever.graaljs.fast.api.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.core.utils.tree.BuildTreeUtils;
import org.clever.graaljs.core.utils.tree.SimpleTreeNode;
import org.clever.graaljs.fast.api.config.FastApiConfig;
import org.clever.graaljs.fast.api.dto.request.AddDirReq;
import org.clever.graaljs.fast.api.dto.request.AddJsJobReq;
import org.clever.graaljs.fast.api.dto.response.AddJsJobRes;
import org.clever.graaljs.fast.api.dto.response.DelJsJobRes;
import org.clever.graaljs.fast.api.dto.response.JobFileResourceRes;
import org.clever.graaljs.fast.api.dto.response.JsJobInfoRes;
import org.clever.graaljs.fast.api.entity.EnumConstant;
import org.clever.graaljs.fast.api.entity.FileResource;
import org.clever.task.core.TaskInstance;
import org.clever.task.core.entity.Job;
import org.clever.task.core.entity.JobTrigger;
import org.clever.task.core.entity.JsJob;
import org.clever.task.core.model.AbstractTrigger;
import org.clever.task.core.model.AddJobRes;
import org.clever.task.core.model.CronTrigger;
import org.clever.task.core.model.JsJobModel;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/08/21 20:06 <br/>
 */
@Service
@Slf4j
public class TaskManageService {
    private static final String QUERY_ALL_JS_JOB = "" +
            "select " +
            "    a.id as jobId, d.id as jobTriggerId, c.id as fileResourceId, c.namespace, c.path, c.name, c.is_file, c.`read_only`, " +
            "    a.name as jobName, a.max_reentry, a.allow_concurrent, a.max_retry_count, a.route_strategy, a.first_scheduler, " +
            "    a.whitelist_scheduler, a.blacklist_scheduler, a.load_balance, a.disable, " +
            "    d.start_time, d.end_time, d.last_fire_time, d.next_fire_time, d.misfire_strategy, " +
            "    d.allow_concurrent as triggerAllowConcurrent, d.type, d.cron, d.fixed_interval, d.disable as triggerDisable " +
            "from job a " +
            "    left join js_job b on (a.id = b.job_id and a.namespace=b.namespace) " +
            "    left join file_resource c on (b.file_resource_id=c.id and b.namespace=c.namespace) " +
            "    left join job_trigger d on (a.id=d.job_id and c.namespace=d.namespace) " +
            "where a.type=3 " +
            "    and b.id is not null " +
            "    and c.is_file=1 " +
            "    and c.module=4 " +
            "    and a.namespace=? " +
            "order by c.name";
    private static final String QUERY_ALL_DIR = "" +
            "select " +
            "   id as fileResourceId, " +
            "   path as path, " +
            "   name as name, " +
            "   is_file as isFile, " +
            "   `read_only` as readOnly " +
            "from file_resource " +
            "where is_file=0 and module=4 and namespace=? " +
            "order by name";
    private static final String GET_JOB_BY_ID = "select * from job where namespace=? and id=?";
    private static final String GET_JOB_TRIGGER_BY_ID = "select * from job_trigger where namespace=? and job_id=?";
    private static final String GET_JS_JOB_BY_ID = "select * from js_job where namespace=? and job_id=?";
    private static final String GET_JOB_TRIGGER_ID_BY_JOB_ID = "select id from job_trigger where namespace=? and job_id=?";
    private static final String GET_JS_JOB_ID_BY_RESOURCE_ID = "select job_id from js_job where namespace=? and file_resource_id in ( %s )";

    /**
     * FileResource 命名空间
     */
    private final String namespace;
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Resource
    private FileResourceManageService fileResourceManageService;
    @Resource
    private TaskInstance taskInstance;

    public TaskManageService(FastApiConfig fastApiConfig) {
        this.namespace = fastApiConfig.getNamespace();
    }

    @Transactional(readOnly = true)
    public List<SimpleTreeNode<JobFileResourceRes>> getJsJobTree() {
        // 查询所有文件夹
        List<JobFileResourceRes> allDir = jdbcTemplate.query(
                QUERY_ALL_DIR,
                DataClassRowMapper.newInstance(JobFileResourceRes.class),
                namespace
        );
        // 查询所有接口文件
        List<JobFileResourceRes> list = jdbcTemplate.query(
                QUERY_ALL_JS_JOB,
                DataClassRowMapper.newInstance(JobFileResourceRes.class),
                namespace
        );
        list.addAll(allDir);
        List<SimpleTreeNode<JobFileResourceRes>> tree = new ArrayList<>(list.size());
        for (JobFileResourceRes jobFileResourceRes : list) {
            // noinspection DuplicatedCode
            allDir.stream()
                    .filter(dir -> {
                        if (!Objects.equals(EnumConstant.IS_FILE_0, dir.getIsFile())) {
                            return false;
                        }
                        String path = dir.getPath() + dir.getName() + "/";
                        return Objects.equals(jobFileResourceRes.getPath(), path);
                    })
                    .findFirst()
                    .ifPresent(dir -> jobFileResourceRes.setParentFileResourceId(dir.getFileResourceId()));
            SimpleTreeNode<JobFileResourceRes> node = new SimpleTreeNode<>(
                    jobFileResourceRes.getParentFileResourceId(),
                    jobFileResourceRes.getFileResourceId(),
                    jobFileResourceRes
            );
            tree.add(node);
        }
        return BuildTreeUtils.buildTree(tree);
    }

    @Transactional(readOnly = true)
    public JsJobInfoRes getJsJobInfo(Long jobId) {
        JsJobInfoRes res = new JsJobInfoRes();
        List<Job> jobList = jdbcTemplate.query(
                GET_JOB_BY_ID,
                DataClassRowMapper.newInstance(Job.class),
                namespace, jobId
        );
        if (!jobList.isEmpty()) {
            res.setJob(jobList.get(0));
        }
        List<JobTrigger> jobTriggerList = jdbcTemplate.query(
                GET_JOB_TRIGGER_BY_ID,
                DataClassRowMapper.newInstance(JobTrigger.class),
                namespace, jobId
        );
        if (!jobTriggerList.isEmpty()) {
            res.setJobTrigger(jobTriggerList.get(0));
        }
        List<JsJob> jsJobList = jdbcTemplate.query(
                GET_JS_JOB_BY_ID,
                DataClassRowMapper.newInstance(JsJob.class),
                namespace, jobId
        );
        if (!jsJobList.isEmpty()) {
            JsJob jsJob = jsJobList.get(0);
            FileResource fileResource = fileResourceManageService.getFileResource(jsJob.getFileResourceId());
            res.setFileResource(fileResource);
        }
        return res;
    }

    @Transactional
    public AddJsJobRes addJsJob(AddJsJobReq req) {
        if (!req.getFileName().toLowerCase().endsWith(".js")) {
            req.setFileName(req.getFileName() + ".js");
        }
        final String name = req.getFileName().substring(0, req.getFileName().length() - 3);
        req.setFileName(name + ".js");
        AddJsJobRes res = new AddJsJobRes();
        AddDirReq addDirReq = new AddDirReq();
        addDirReq.setModule(EnumConstant.MODULE_4);
        addDirReq.setFullPath(req.getFilePath());
        List<FileResource> fileResourceList = fileResourceManageService.addDir(addDirReq);
        res.getFileList().addAll(fileResourceList);
        JsJobModel jobModel = new JsJobModel(req.getName(), req.getFilePath(), req.getFileName(), req.getFileContent());
        jobModel.setMaxReentry(req.getMaxReentry());
        jobModel.setAllowConcurrent(req.getJobAllowConcurrent());
        jobModel.setMaxRetryCount(req.getMaxRetryCount());
        // jobModel.setRouteStrategy();
        // jobModel.setFirstScheduler();
        // jobModel.setWhitelistScheduler();
        // jobModel.setBlacklistScheduler();
        // jobModel.setLoadBalance();
        jobModel.setIsUpdateData(req.getIsUpdateData());
        jobModel.setJobData(req.getJobData());
        jobModel.setDisable(req.getDisable());
        jobModel.setDescription(req.getDescription());
        AbstractTrigger trigger = new CronTrigger(String.format("%s_trigger", req.getName()), req.getCron());
        trigger.setStartTime(req.getStartTime());
        trigger.setEndTime(req.getEndTime());
        trigger.setMisfireStrategy(req.getMisfireStrategy());
        trigger.setAllowConcurrent(req.getTriggerAllowConcurrent());
        trigger.setDisable(req.getTriggerDisable());
        AddJobRes addJobRes = taskInstance.addJob(jobModel, trigger);
        if (addJobRes.getFileResource() != null && addJobRes.getFileResource().getId() != null) {
            FileResource fileResource = fileResourceManageService.getFileResource(addJobRes.getFileResource().getId());
            if (fileResource != null) {
                res.getFileList().add(fileResource);
            }
        }
        res.setJob(addJobRes.getJob());
        res.setJobTrigger(addJobRes.getJobTrigger());
        return res;
    }

    @Transactional
    public DelJsJobRes delJsJob(Long fileResourceId) {
        DelJsJobRes res = new DelJsJobRes();
        // 删除文件资源
        List<FileResource> fileList = fileResourceManageService.delFileResource(fileResourceId);
        res.setFileList(fileList);
        // 删除Job
        List<Long> fileResourceIds = new ArrayList<>();
        fileList.forEach(fileResource -> {
            if (Objects.equals(fileResource.getIsFile(), EnumConstant.IS_FILE_1)) {
                fileResourceIds.add(fileResource.getId());
            }
        });
        if (fileResourceIds.isEmpty()) {
            return res;
        }
        String sql = String.format(GET_JS_JOB_ID_BY_RESOURCE_ID, StringUtils.repeat("?", ", ", fileResourceIds.size()));
        List<Object> args = new ArrayList<>(fileResourceIds.size() + 1);
        args.add(namespace);
        args.addAll(fileResourceIds);
        List<Long> jobIds = jdbcTemplate.queryForList(sql, Long.class, args.toArray());
        if (jobIds.isEmpty()) {
            return res;
        }
        taskInstance.deleteJobs(jobIds);
        return res;
    }

    @Transactional
    public void disable(Long jobId) {
        List<Long> triggerIds = jdbcTemplate.queryForList(GET_JOB_TRIGGER_ID_BY_JOB_ID, Long.class, namespace, jobId);
        taskInstance.disableTriggers(triggerIds);
    }

    @Transactional
    public void enable(Long jobId) {
        List<Long> triggerIds = jdbcTemplate.queryForList(GET_JOB_TRIGGER_ID_BY_JOB_ID, Long.class, namespace, jobId);
        taskInstance.enableTriggers(triggerIds);
    }
}
