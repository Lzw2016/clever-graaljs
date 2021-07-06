package org.clever.graaljs.spring.mvc.model.request;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/06 18:10 <br/>
 */
@Data
public class ApiDebugReq implements Serializable {
    public static final String REQUEST_ATTRIBUTE = ApiDebugReq.class.getName() + "_request_attribute";
    /**
     * 是否调试接口
     */
    private static final String API_DEBUG_HEADER = "api-debug";

    private final String apiDebugUniqueId;
    private final boolean isDebug;
    private final int debugBufferSize;

    public ApiDebugReq(HttpServletRequest request) {
        apiDebugUniqueId = StringUtils.trim(request.getHeader(API_DEBUG_HEADER));
        isDebug = StringUtils.isNotBlank(apiDebugUniqueId) && apiDebugUniqueId.length() > 16;
        debugBufferSize = 2048;
        request.setAttribute(REQUEST_ATTRIBUTE, this);
    }
}
