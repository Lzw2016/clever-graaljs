package org.clever.graaljs.fast.api.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/08/21 21:23 <br/>
 */
@Data
public class JobFileResourceRes implements Serializable {
    private Long jobId;

    private Long jobTriggerId;
    /**
     * 资源文件id
     */
    private Long fileResourceId;
    /**
     * 父级编号(资源文件id)
     */
    private Long parentFileResourceId;
    /**
     * 命名空间
     */
    private String namespace;
    /**
     * 文件路径(以"/"结束)
     */
    private String path;
    /**
     * 文件名称
     */
    private String name;
    /**
     * 数据类型：0-文件夹，1-文件
     */
    private Integer isFile;
    /**
     * 读写权限：0-可读可写，1-只读
     */
    private Integer readOnly;

    // -------------------------------------------------------------------------------- Job

    /**
     * 最大重入执行数量(对于单个节点当前任务未执行完成就触发了下一次执行导致任务重入执行)，小于等于0：表示禁止重入执行
     */
    private Integer maxReentry;
    /**
     * 是否允许多节点并发执行，使用悲观锁实现(不建议使用)，0：禁止，1：允许
     */
    private Integer allowConcurrent;
    /**
     * 执行失败时的最大重试次数
     */
    private Integer maxRetryCount;
    /**
     * 路由策略，0：不启用，1：指定节点优先，2：固定节点白名单，3：固定节点黑名单
     */
    private Integer routeStrategy;
    /**
     * 路由策略，1-指定节点优先，调度器名称集合
     */
    private String firstScheduler;
    /**
     * 路由策略，2-固定节点白名单，调度器名称集合
     */
    private String whitelistScheduler;
    /**
     * 路由策略，3-固定节点黑名单，调度器名称集合
     */
    private String blacklistScheduler;
    /**
     * 负载均衡策略，1：抢占，2：随机，3：轮询，4：一致性HASH
     */
    private Integer loadBalance;
    /**
     * 是否禁用：0-启用，1-禁用
     */
    private Integer disable;

    // -------------------------------------------------------------------------------- JobTrigger

    /**
     * 触发开始时间
     */
    private Date startTime;
    /**
     * 触发结束时间
     */
    private Date endTime;
    /**
     * 上一次触发时间
     */
    private Date lastFireTime;
    /**
     * 下一次触发时间
     */
    private Date nextFireTime;
    /**
     * 错过触发策略，1：忽略，2：立即补偿触发一次
     */
    private Integer misfireStrategy;
    /**
     * 是否允许多节点并行触发，使用悲观锁实现，0：禁止，1：允许
     */
    private Integer triggerAllowConcurrent;
    /**
     * 任务类型，1：cron触发，2：固定速率触发
     */
    private Integer type;
    /**
     * cron表达式
     */
    private String cron;
    /**
     * 固定速率触发，间隔时间(单位：秒)
     */
    private Long fixedInterval;
    /**
     * 是否禁用：0-启用，1-禁用
     */
    private Integer triggerDisable;
}
