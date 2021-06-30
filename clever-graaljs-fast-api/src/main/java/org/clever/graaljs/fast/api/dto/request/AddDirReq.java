package org.clever.graaljs.fast.api.dto.request;

import lombok.Data;
import org.clever.graaljs.fast.api.entity.EnumConstant;
import org.clever.graaljs.spring.core.utils.validator.annotation.ValidIntegerStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/29 22:17 <br/>
 */
@Data
public class AddDirReq implements Serializable {
    /**
     * 所属模块：0-自定义扩展，1-资源文件，2-初始化脚本，3-HTTP API，4-定时任务
     */
    @NotNull
    @ValidIntegerStatus(value = {EnumConstant.MODULE_0, EnumConstant.MODULE_1, EnumConstant.MODULE_2, EnumConstant.MODULE_3, EnumConstant.MODULE_4})
    private Integer module;
    /**
     * 文件夹全路径
     */
    @Pattern(regexp = "^(/[a-zA-Z0-9_-\\u4e00-\\u9fa5]+)+/?$")
    @NotBlank
    private String fullPath;
}
