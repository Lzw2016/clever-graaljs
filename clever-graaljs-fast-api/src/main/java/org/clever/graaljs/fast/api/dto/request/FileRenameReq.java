package org.clever.graaljs.fast.api.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/01 09:51 <br/>
 */
@Data
public class FileRenameReq implements Serializable {
    @NotNull
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5_.-]+$")
    @NotBlank
    private String newName;
}
