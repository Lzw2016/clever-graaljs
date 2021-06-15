package org.clever.graaljs.fast.api.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/15 22:29 <br/>
 */
@Data
public class MapperFileResource implements Serializable {
    /**
     * FileResource主键id
     */
    private Long fileResourceId;
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
     * 最后修改时间
     */
    private Date lastModifiedTime;
}
