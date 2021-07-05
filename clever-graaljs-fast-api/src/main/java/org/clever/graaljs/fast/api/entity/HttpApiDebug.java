package org.clever.graaljs.fast.api.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;

/**
 * HTTP接口(HttpApiDebug)实体类
 *
 * @author lizw
 * @since 2021-07-05 10:26:09
 */
@Data
public class HttpApiDebug implements Serializable {
    private static final long serialVersionUID = -72186229447100658L;
    /**
     * 主键id
     */
    private Long id;

    /**
     * 命名空间
     */
    private String namespace;

    /**
     * HTTP接口id
     */
    private Long httpApiId;

    /**
     * 标题
     */
    private String title;

    /**
     * 请求数据json格式
     */
    private String requestData;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 更新时间
     */
    private Date updateAt;

}
