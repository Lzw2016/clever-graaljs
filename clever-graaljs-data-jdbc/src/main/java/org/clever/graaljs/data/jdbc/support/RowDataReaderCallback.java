package org.clever.graaljs.data.jdbc.support;

import org.clever.graaljs.core.utils.UnderlineToCamelUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/09 22:37 <br/>
 */
public class RowDataReaderCallback extends AbstractRowCountCallbackHandler {
    /**
     * 游标读取数据消费者
     */
    private final Consumer<RowData> consumer;
    /**
     * 下划线转驼峰
     */
    private final boolean underlineToCamel;

    public RowDataReaderCallback(Consumer<RowData> consumer, boolean underlineToCamel) {
        this.consumer = consumer;
        this.underlineToCamel = underlineToCamel;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected void processRow(ResultSet rs, int rowNum) throws SQLException {
        Map<String, Object> rowData = getRowData(rs, rowNum);
        if (underlineToCamel) {
            rowData = UnderlineToCamelUtils.underlineToCamel(rowData);
        }
        consumer.accept(new RowData(getColumnNames(), getColumnTypes(), getColumnCount(), rowData, this.getRowCount()));
    }
}
