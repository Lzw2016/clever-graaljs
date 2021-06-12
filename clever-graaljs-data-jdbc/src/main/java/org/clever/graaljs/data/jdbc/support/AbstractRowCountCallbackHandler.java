package org.clever.graaljs.data.jdbc.support;

import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/09 22:44 <br/>
 */
public abstract class AbstractRowCountCallbackHandler extends RowCountCallbackHandler {

    protected Map<String, Object> getRowData(ResultSet rs, int rowNum) throws SQLException {
        Map<String, Object> rowData = new LinkedCaseInsensitiveMap<>(getColumnCount());
        for (int i = 0; i < getColumnCount(); i++) {
            assert getColumnNames() != null;
            String column = getColumnNames()[i];
            rowData.putIfAbsent(column, getColumnValue(rs, i + 1));
        }
        return rowData;
    }

    protected Object getColumnValue(ResultSet rs, int index) throws SQLException {
        return org.springframework.jdbc.support.JdbcUtils.getResultSetValue(rs, index);
    }
}
