package org.clever.graaljs.fast.api.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/09/01 15:08 <br/>
 */
@Data
public class DebugApiReq implements Serializable {
    /**
     * debug id
     */
    private String apiDebugId;
}
