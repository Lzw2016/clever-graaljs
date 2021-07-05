package org.clever.graaljs.fast.api.dto.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/05 10:47 <br/>
 */
@Data
public class HttpApiDebugTitleRes implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * HTTP接口id
     */
    private Long httpApiId;

    /**
     * 标题
     */
    private String title;
}
