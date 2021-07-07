package org.clever.graaljs.fast.api.dto.request;

import lombok.Data;
import org.clever.graaljs.data.jdbc.builtin.wrap.support.JdbcConfig;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/07 22:19 <br/>
 */
@Data
public class UpdateJdbcReq implements Serializable {
    @NotBlank
    private String name;

    @NotNull
    private JdbcConfig jdbcConfig;
}
