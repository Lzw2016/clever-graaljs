package org.clever.graaljs.fast.api.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 资源文件(FileResource)实体类
 *
 * @author lizw
 * @since 2021-06-13 22:19:32
 */
@Data
public class FileResource implements Serializable {
    private static final long serialVersionUID = -36851501670140266L;
    /**
     * 主键id
     */
    private Long id;

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
     * 文件内容
     */
    private String content;

    /**
     * 数据类型：0-文件夹，1-文件
     */
    private Integer isFile;

    /**
     * 读写权限：0-可读可写，1-只读
     */
    private Integer readOnly;

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
