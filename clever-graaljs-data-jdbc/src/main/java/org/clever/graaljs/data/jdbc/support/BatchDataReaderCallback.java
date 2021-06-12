package org.clever.graaljs.data.jdbc.support;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 游标批量读取模式
 * <p>
 * 作者：lizw <br/>
 * 创建时间：2020/02/19 17:53 <br/>
 */
public class BatchDataReaderCallback extends AbstractRowCountCallbackHandler {
    private static final int Default_Batch_Size = 200;

    /**
     * 一个批次的数据量
     */
    private final int batchSize;
    /**
     * 游标批次读取数据消费者
     */
    private final Consumer<BatchData> consumer;
    /**
     * 下划线转驼峰
     */
    private final boolean underlineToCamel;
    /**
     * 读取数据
     */
    private List<Map<String, Object>> rowDataList;

    /**
     * @param batchSize 一个批次的数据量
     * @param consumer  游标批次读取数据消费者
     */
    public BatchDataReaderCallback(int batchSize, Consumer<BatchData> consumer, boolean underlineToCamel) {
        this.batchSize = batchSize <= 0 ? Default_Batch_Size : batchSize;
        this.consumer = consumer;
        this.underlineToCamel = underlineToCamel;
        this.rowDataList = new ArrayList<>(this.batchSize);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected synchronized void processRow(ResultSet rs, int rowNum) throws SQLException {
        Map<String, Object> rowData = getRowData(rs, rowNum);
        if (underlineToCamel) {
            rowData = UnderlineToCamelUtils.underlineToCamel(rowData);
        }
        rowDataList.add(rowData);
        if (rowDataList.size() >= batchSize) {
            consumer.accept(new BatchData(getColumnNames(), getColumnTypes(), getColumnCount(), rowDataList, this.getRowCount()));
            rowDataList = new ArrayList<>(this.batchSize);
        }
    }

    public synchronized void processEnd() {
        if (rowDataList.isEmpty()) {
            return;
        }
        consumer.accept(new BatchData(getColumnNames(), getColumnTypes(), getColumnCount(), rowDataList, this.getRowCount()));
        rowDataList = new ArrayList<>();
    }
}
