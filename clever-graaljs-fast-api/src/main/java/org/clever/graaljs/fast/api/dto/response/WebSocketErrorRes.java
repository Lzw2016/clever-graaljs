package org.clever.graaljs.fast.api.dto.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/08/24 19:45 <br/>
 */
@Data
public class WebSocketErrorRes implements Serializable {
    private String errorStackTrace;
}
