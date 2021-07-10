package org.clever.graaljs.fast.api.dto.request;

import lombok.Data;
import org.clever.graaljs.fast.api.model.DebugRequestData;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/10 21:40 <br/>
 */
@Data
public class AddHttpGlobalRequestDataReq implements Serializable {
    private Long id;

    @NotBlank
    private String title;

    @NotNull
    private List<DebugRequestData.RequestItem> params = new ArrayList<>();

    @NotNull
    private List<DebugRequestData.RequestItem> headers = new ArrayList<>();

    @NotNull
    private List<DebugRequestData.RequestItem> cookies = new ArrayList<>();
}
