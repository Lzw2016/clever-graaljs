package org.clever.graaljs.fast.api.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/08/24 16:43 <br/>
 */
@Data
public class RunJsReq implements Serializable {
    /**
     * js文件ID
     */
    private Long fileResourceId;
}
