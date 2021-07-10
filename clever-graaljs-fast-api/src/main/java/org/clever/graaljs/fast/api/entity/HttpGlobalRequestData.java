package org.clever.graaljs.fast.api.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * HTTP全局请求数据(HttpGlobalRequestData)实体类
 *
 * @author lizw
 * @since 2021-07-10 21:25:19
 */
@Data
public class HttpGlobalRequestData implements Serializable {
    private static final long serialVersionUID = -11130688106753599L;
    /**
     * 主键id
     */
    private Long id;

    /**
     * 命名空间
     */
    private String namespace;

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
