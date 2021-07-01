package org.clever.graaljs.fast.api.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 资源文件修改历史(FileResourceHistory)实体类
 *
 * @author lizw
 * @since 2021-06-13 22:19:31
 */
@Data
public class FileResourceHistory implements Serializable {
    private static final long serialVersionUID = 680887980020700046L;
    /**
     * 主键id
     */
    private Long id;

    /**
     * 命名空间
     */
    private String namespace;

    /**
     * 所属模块：0-自定义扩展，1-资源文件，2-初始化脚本，3-HTTP API，4-定时任务
     */
    private Integer module;

    /**
     * 资源文件id
     */
    private Long fileResourceId;

    /**
     * 文件路径(以"/"结束)
     */
    private String path;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createAt;
}
