package org.clever.graaljs.fast.api.dto.request;

import lombok.Data;
import org.clever.graaljs.fast.api.model.DebugRequestData;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/05 11:37 <br/>
 */
@Data
public class UpdateHttpApiDebugReq implements Serializable {
    @NotNull
    private Long id;

    /**
     * 标题
     */
    @NotBlank
    private String title;

    /**
     * 请求数据
     */
    @NotNull
    private DebugRequestData requestData;
}
