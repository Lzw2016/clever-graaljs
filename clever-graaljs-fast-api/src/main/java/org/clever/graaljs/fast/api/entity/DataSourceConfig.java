package org.clever.graaljs.fast.api.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;

/**
 * 数据源配置(DataSourceConfig)实体类
 *
 * @author lizw
 * @since 2021-06-13 22:19:32
 */
@Data
public class DataSourceConfig implements Serializable {
    private static final long serialVersionUID = 355215508809274177L;
    /**
     * 主键id
     */
    private Long id;

    /**
     * 命名空间
     */
    private String namespace;

    /**
     * 数据源类型，jdbc redis rabbitmq rocketmq kafka elasticsearch ...
     */
    private String type;

    /**
     * 数据源名称
     */
    private String name;

    /**
     * 数据源配置
     */
    private String config;

    /**
     * 禁用：0-启用，1-禁用
     */
    private Integer disable;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 更新时间
     */
    private Date updateAt;
}
