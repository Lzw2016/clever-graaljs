package org.clever.graaljs.fast.api.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/05 11:12 <br/>
 */
@Data
public class DebugRequestData implements Serializable {
    private String method;
    private String path;
    private Map<String, String> headers;
    private String jsonBody;
    private Map<String, FormItem> formBody;

    @Data
    public static class FormItem implements Serializable {
        private String type;
        private String value;
    }
}
