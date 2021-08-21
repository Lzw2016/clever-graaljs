package org.clever.graaljs.fast.api.config;

import lombok.Data;
import org.clever.task.core.config.SchedulerConfig;

import java.io.Serializable;
import java.time.Duration;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/08/21 19:32 <br/>
 */
@Data
public class TaskConfig implements Serializable {
    /**
     * 调度器实例名称
     */
    private String instanceName = "node01";
    /**
     * 心跳频率，建议：300 ~ 15000(单位：毫秒)
     */
    private Duration heartbeatInterval = Duration.ofSeconds(3);
    /**
     * 描述
     */
    private String description;
    /**
     * 调度线程池大小
     */
    private int schedulerExecutorPoolSize = 8;
    /**
     * 定时任务执行线程池大小
     */
    private int jobExecutorPoolSize = 32;
    /**
     * 负载权重
     */
    private double loadWeight = 1.0;
    /**
     * 最大并发任务数(大于等于jobExecutorPoolSize值)
     */
    private int maxConcurrent = 10240;

    public SchedulerConfig toSchedulerConfig() {
        SchedulerConfig schedulerConfig = new SchedulerConfig();
        schedulerConfig.setInstanceName(getInstanceName());
        schedulerConfig.setHeartbeatInterval(getHeartbeatInterval().toMillis());
        schedulerConfig.setDescription(getDescription());
        schedulerConfig.setSchedulerExecutorPoolSize(getSchedulerExecutorPoolSize());
        schedulerConfig.setJobExecutorPoolSize(getJobExecutorPoolSize());
        schedulerConfig.setLoadWeight(getLoadWeight());
        schedulerConfig.setMaxConcurrent(getMaxConcurrent());
        return schedulerConfig;
    }
}
