package org.clever.graaljs.fast.api.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/05 11:12 <br/>
 */
@Data
public class DebugRequestData implements Serializable {
    private String method = "GET";
    private String path = "/";
    private List<RequestItem> params = new ArrayList<>();
    private List<RequestItem> headers = new ArrayList<>();
    private String bodyType = "None";
    private String jsonBody = "";
    private List<FormItem> formBody = new ArrayList<>();

    @Data
    public static class FormItem implements Serializable {
        private String type;
        private String key;
        private String value;
    }

    @Data
    public static class RequestItem implements Serializable {
        private String key;
        private String value;
        private String description;
        private boolean selected;
    }
}
