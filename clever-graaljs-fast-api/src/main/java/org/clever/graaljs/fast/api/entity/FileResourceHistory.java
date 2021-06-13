package org.clever.graaljs.fast.api.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;

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
     * 文件路径
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
     * 说明
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 更新时间
     */
    private Date updateAt;
}
