package org.clever.graaljs.spring.mvc.model.response;

import lombok.Data;
import org.clever.graaljs.core.utils.RingBuffer;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/06 17:43 <br/>
 */
@Data
public class ApiDebugRes implements Serializable {
    public static final String REQUEST_ATTRIBUTE = ApiDebugRes.class.getName() + "_request_attribute";

    private Object data;
    private RingBuffer.BufferContent<String> logs;

    public ApiDebugRes(HttpServletRequest request) {
        request.setAttribute(REQUEST_ATTRIBUTE, this);
    }
}
