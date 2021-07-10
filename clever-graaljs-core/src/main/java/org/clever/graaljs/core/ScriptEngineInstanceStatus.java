package org.clever.graaljs.core;

import lombok.Data;

import java.io.Serializable;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/09 09:54 <br/>
 */
@Data
public class ScriptEngineInstanceStatus implements Serializable {
    // ---------------------------------------------------------------------------------------------------- 配置
    /**
     * 配置-最大数量
     */
    private int maxTotal;
    /**
     * 配置-最大空闲
     */
    private int maxIdle;
    /**
     * 配置-最小空闲
     */
    private int minIdle;
    /**
     * 配置-最大等待时间
     */
    private long maxWaitMillis;
    /**
     * 配置-对象在符合逐出条件之前可以在池中处于空闲状态的最短时间
     */
    private long minEvictableIdleTimeMillis;
    /**
     * 配置-退出程序运行之间的休眠毫秒数
     */
    private long timeBetweenEvictionRunsMillis;
    // ---------------------------------------------------------------------------------------------------- 状态
    /**
     * 状态-当前最活动数量
     */
    private int numActive;
    /**
     * 状态-当前空闲数量
     */
    private int numIdle;
    /**
     * 状态-当前阻塞的等待池中对象的线程数的估计值
     */
    private int numWaiters;
    /**
     * 状态-在最近返回的对象中从池中签出对象的平均时间
     */
    private long activeTimes;
    /**
     * 状态-在最近借用的对象中，对象在池中空闲的平均时间
     */
    private long idleTimes;
    /**
     * 状态-最近服务的线程必须等待从池中借用对象的平均时间（毫秒）
     */
    private long waitTimes;
    /**
     * 状态-自创建池以来的最大等待时间（毫秒）
     */
    private long maxBorrowWaitTimeMillis;
    /**
     * 状态-创建数量
     */
    private long createdCount;
    /**
     * 状态-借数量
     */
    private long borrowedCount;
    /**
     * 状态-还数量
     */
    private long returnedCount;
    /**
     * 状态-销户数量
     */
    private long destroyedCount;
    /**
     * 状态-验证销毁对象计数
     */
    private long destroyedByBorrowValidationCount;
    /**
     * 状态-逐出器销毁的对象计数
     */
    private long destroyedByEvictorCount;
}
