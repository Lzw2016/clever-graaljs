package org.clever.graaljs.fast.api.dto.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/28 17:18 <br/>
 */
@Data
public class FileResourceTreeNodeRes implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 父级编号(资源文件id)
     */
    private Long parentFileResourceId;

    /**
     * 命名空间
     */
    private String namespace;

    /**
     * 所属模块：0-自定义扩展，1-资源文件，2-初始化脚本，3-HTTP API，4-定时任务
     */
    private Integer module;

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
}
