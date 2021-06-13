package org.clever.graaljs.fast.api.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;

/**
 * HTTP接口(HttpApi)实体类
 *
 * @author lizw
 * @since 2021-06-13 22:19:31
 */
@Data
public class HttpApi implements Serializable {
    private static final long serialVersionUID = -27928456215062624L;
    /**
     * 主键id
     */
    private Long id;

    /**
     * 命名空间
     */
    private String namespace;

    /**
     * 资源文件id
     */
    private Long fileResourceId;

    /**
     * http请求路径
     */
    private String requestMapping;

    /**
     * http请求method，GET HEAD POST PUT DELETE CONNECT OPTIONS TRACE PATCH
     */
    private String requestMethod;

    /**
     * 禁用http请求：0-启用，1-禁用
     */
    private Integer disableRequest;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 更新时间
     */
    private Date updateAt;
}
