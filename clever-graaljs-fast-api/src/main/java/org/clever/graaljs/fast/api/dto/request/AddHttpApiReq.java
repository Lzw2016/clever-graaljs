package org.clever.graaljs.fast.api.dto.request;

import lombok.Data;
import org.clever.graaljs.fast.api.entity.EnumConstant;
import org.clever.graaljs.spring.core.utils.validator.annotation.ValidStringStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/30 09:35 <br/>
 */
@Data
public class AddHttpApiReq implements Serializable {
    @Pattern(regexp = "^(/[a-zA-Z0-9_-]+)*/?$")
    @NotBlank
    private String path;

    @Pattern(regexp = "^[a-zA-Z0-9_-]+(\\.[Jj][Ss])?$")
    @NotBlank
    private String name;

    @ValidStringStatus(value = {
            EnumConstant.REQUEST_METHOD_ALL,
            EnumConstant.REQUEST_METHOD_GET,
            EnumConstant.REQUEST_METHOD_HEAD,
            EnumConstant.REQUEST_METHOD_POST,
            EnumConstant.REQUEST_METHOD_PUT,
            EnumConstant.REQUEST_METHOD_DELETE,
            EnumConstant.REQUEST_METHOD_CONNECT,
            EnumConstant.REQUEST_METHOD_OPTIONS,
            EnumConstant.REQUEST_METHOD_TRACE,
            EnumConstant.REQUEST_METHOD_PATCH,
    })
    @NotBlank
    private String RequestMethod;

    private String requestMapping;

    private String content;
}
