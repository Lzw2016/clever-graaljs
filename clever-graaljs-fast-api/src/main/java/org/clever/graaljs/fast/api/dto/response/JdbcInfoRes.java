package org.clever.graaljs.fast.api.dto.response;

import lombok.Data;
import org.clever.graaljs.data.jdbc.support.JdbcDataSourceStatus;
import org.clever.graaljs.data.jdbc.support.JdbcInfo;
import org.clever.graaljs.fast.api.entity.DataSourceConfig;

import java.io.Serializable;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/07 15:58 <br/>
 */
@Data
public class JdbcInfoRes implements Serializable {
    private String name;

    private boolean def = false;

    private boolean immutable = false;

    private JdbcInfo jdbcInfo;

    private JdbcDataSourceStatus status;

    private DataSourceConfig dataSourceConfig;
}
