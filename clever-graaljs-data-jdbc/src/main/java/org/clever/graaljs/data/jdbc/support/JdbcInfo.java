package org.clever.graaljs.data.jdbc.support;

import lombok.Data;
import org.clever.graaljs.data.jdbc.support.mybatisplus.DbType;

import java.io.Serializable;

@Data
public class JdbcInfo implements Serializable {
    private String driverClassName;

    private String jdbcUrl;

    private boolean isAutoCommit;

    private boolean isReadOnly;

    private DbType dbType;

    private boolean isClosed;
}
