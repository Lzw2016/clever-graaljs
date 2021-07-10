package org.clever.graaljs.fast.api.dto.response;

import lombok.Data;
import org.clever.graaljs.fast.api.model.DebugRequestData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/10 21:29 <br/>
 */
@Data
public class HttpGlobalRequestDataRes implements Serializable {
    private Long id;

    private String title;

    private List<DebugRequestData.RequestItem> params = new ArrayList<>();

    private List<DebugRequestData.RequestItem> headers = new ArrayList<>();

    private List<DebugRequestData.RequestItem> cookies = new ArrayList<>();
}
