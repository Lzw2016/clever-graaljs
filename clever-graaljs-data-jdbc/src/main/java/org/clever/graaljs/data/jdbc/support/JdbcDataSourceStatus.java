package org.clever.graaljs.data.jdbc.support;

import lombok.Data;

import java.io.Serializable;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/08/08 22:18 <br/>
 */
@Data
public class JdbcDataSourceStatus implements Serializable {

    private int totalConnections;

    private int activeConnections;

    private int idleConnections;

    private int threadsAwaitingConnection;
}
