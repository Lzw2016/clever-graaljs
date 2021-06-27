package org.clever.graaljs.fast.api.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/27 20:13 <br/>
 */
@Data
public class SaveFileContentReq implements Serializable {
    /**
     * 主键id
     */
    @NotNull
    private Long id;
    /**
     * 文件内容
     */
    private String content;
}
