package org.clever.graaljs.fast.api.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/14 12:05 <br/>
 */
@Data
public class HttpApiFileResource implements Serializable {
    /**
     * HttpApi主键id
     */
    private Long httpApiId;
    /**
     * FileResource主键id
     */
    private Long fileResourceId;
    /**
     * 命名空间
     */
    private String namespace;
    /**
     * http请求路径
     */
    private String requestMapping;
    /**
     * http请求method，ALL GET HEAD POST PUT DELETE CONNECT OPTIONS TRACE PATCH
     */
    private String requestMethod;
    /**
     * 禁用http请求：0-启用，1-禁用
     */
    private Integer disableRequest;
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
