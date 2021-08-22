package org.clever.graaljs.fast.api.dto.request;

import lombok.Data;
import org.clever.graaljs.spring.core.utils.validator.annotation.ValidIntegerStatus;
import org.clever.task.core.entity.EnumConstant;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/08/22 14:15 <br/>
 */
@Data
public class AddJsJobReq implements Serializable {
    /**
     * 任务名称
     */
    @NotBlank
    protected String name;
    /**
     * 文件路径(以"/"结束)
     */
    @Pattern(regexp = "^(/[a-zA-Z0-9\\u4e00-\\u9fa5_-]+)*/?$")
    @NotBlank
    private String filePath;
    /**
     * 文件名称
     */
    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5_-]+(.js)?$")
    @NotBlank
    private String fileName;
    /**
     * 最大重入执行数量(对于单个节点当前任务未执行完成就触发了下一次执行导致任务重入执行)，小于等于0：表示禁止重入执行
     */
    @Range(min = 0)
    protected Integer maxReentry;
    /**
     * 是否允许多节点并发执行，使用悲观锁实现(不建议使用)，0：禁止，1：允许
     */
    @ValidIntegerStatus({
            EnumConstant.JOB_ALLOW_CONCURRENT_0,
            EnumConstant.JOB_ALLOW_CONCURRENT_1
    })
    protected Integer allowConcurrent;
    /**
     * 执行失败时的最大重试次数
     */
    @Range(min = 0)
    protected Integer maxRetryCount;
//    /**
//     * 路由策略，0：不启用，1：指定节点优先，2：固定节点白名单，3：固定节点黑名单
//     */
//    protected Integer routeStrategy;
//    /**
//     * 路由策略，1-指定节点优先，调度器名称集合
//     */
//    protected String firstScheduler;
//    /**
//     * 路由策略，2-固定节点白名单，调度器名称集合
//     */
//    protected String whitelistScheduler;
//    /**
//     * 路由策略，3-固定节点黑名单，调度器名称集合
//     */
//    protected String blacklistScheduler;
//    /**
//     * 负载均衡策略，1：抢占，2：随机，3：轮询，4：一致性HASH
//     */
//    protected Integer loadBalance;
    /**
     * 是否更新任务数据，0：不更新，1：更新
     */
    @ValidIntegerStatus({
            EnumConstant.JOB_IS_UPDATE_DATA_0,
            EnumConstant.JOB_IS_UPDATE_DATA_1
    })
    protected Integer isUpdateData;
    /**
     * 任务数据(json格式)
     */
    protected String jobData;
    /**
     * 是否禁用：0-启用，1-禁用
     */
    @ValidIntegerStatus({
            EnumConstant.JOB_DISABLE_0,
            EnumConstant.JOB_DISABLE_1
    })
    protected Integer disable;
    /**
     * 描述
     */
    protected String description;
    /**
     * js文件内容
     */
    private String fileContent;

    /**
     * cron表达式
     */
    @NotBlank
    private String cron;
    /**
     * 触发开始时间
     */
    protected Date startTime;
    /**
     * 触发结束时间
     */
    protected Date endTime;
    /**
     * 错过触发策略，1：忽略，2：立即补偿触发一次
     */
    @ValidIntegerStatus({
            EnumConstant.JOB_TRIGGER_MISFIRE_STRATEGY_1,
            EnumConstant.JOB_TRIGGER_MISFIRE_STRATEGY_2
    })
    protected Integer misfireStrategy;
    /**
     * 是否允许多节点并行触发，使用悲观锁实现，0：禁止，1：允许
     */
    @ValidIntegerStatus({
            EnumConstant.JOB_TRIGGER_ALLOW_CONCURRENT_0,
            EnumConstant.JOB_TRIGGER_ALLOW_CONCURRENT_1
    })
    protected Integer triggerAllowConcurrent;
}
