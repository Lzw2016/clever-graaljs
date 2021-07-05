package org.clever.graaljs.fast.api.dto.response;

import lombok.Data;
import org.clever.graaljs.fast.api.model.DebugRequestData;

import java.io.Serializable;
import java.util.Date;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/05 11:17 <br/>
 */
@Data
public class HttpApiDebugRes implements Serializable {
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
     * 请求数据
     */
    private DebugRequestData requestData;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 更新时间
     */
    private Date updateAt;
}
