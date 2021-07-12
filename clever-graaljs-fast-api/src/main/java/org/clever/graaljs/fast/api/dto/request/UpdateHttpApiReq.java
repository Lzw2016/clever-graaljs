package org.clever.graaljs.fast.api.dto.request;

import lombok.Data;
import org.clever.graaljs.fast.api.entity.EnumConstant;
import org.clever.graaljs.spring.core.utils.validator.annotation.ValidIntegerStatus;
import org.clever.graaljs.spring.core.utils.validator.annotation.ValidStringStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/12 20:00 <br/>
 */
@Data
public class UpdateHttpApiReq implements Serializable {
    /**
     * 主键id
     */
    @NotNull
    private Long id;
    /**
     * http请求路径
     */
    @NotBlank
    private String requestMapping;

    /**
     * http请求method，ALL GET HEAD POST PUT DELETE CONNECT OPTIONS TRACE PATCH
     */
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
    private String requestMethod;

    /**
     * 禁用http请求：0-启用，1-禁用
     */
    @ValidIntegerStatus(value = {EnumConstant.DISABLE_REQUEST_0, EnumConstant.DISABLE_REQUEST_1})
    private Integer disableRequest = EnumConstant.DISABLE_REQUEST_0;
}
