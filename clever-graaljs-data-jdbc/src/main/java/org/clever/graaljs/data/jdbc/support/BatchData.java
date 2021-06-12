package org.clever.graaljs.data.jdbc.support;

import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 游标读取批次数据量
 * <p>
 * 作者：lizw <br/>
 * 创建时间：2020/07/09 22:08 <br/>
 */
@Getter
public class BatchData implements Serializable {
    /**
     * 列名称集合
     */
    protected final List<String> columnNames;
    /**
     * 列类型集合
     */
    protected final List<Integer> columnTypes;
    /**
     * 列宽(列数)
     */
    protected final int columnCount;
    /**
     * 当前批次数
     */
    protected final List<Map<String, Object>> rowDataList;
    /**
     * 当前读取的行数
     */
    protected final int rowCount;

    public BatchData(String[] columnNames, int[] columnTypes, int columnCount, List<Map<String, Object>> rowDataList, int rowCount) {
        this.columnNames = Arrays.asList(columnNames);
        Integer[] columnTypesTmp;
        if (columnTypes == null) {
            columnTypesTmp = new Integer[0];
        } else {
            columnTypesTmp = new Integer[columnTypes.length];
            for (int i = 0; i < columnTypes.length; i++) {
                columnTypesTmp[i] = columnTypes[i];
            }
        }
        this.columnTypes = Arrays.asList(columnTypesTmp);
        this.columnCount = columnCount;
        this.rowDataList = rowDataList;
        this.rowCount = rowCount;
    }

    /**
     * 返回当前批次数据量
     */
    public int getBatchCount() {
        if (rowDataList == null || rowDataList.isEmpty()) {
            return 0;
        }
        return rowDataList.size();
    }

    public int[] originalGetColumnTypes() {
        int[] result = new int[columnTypes.size()];
        for (int i = 0; i < columnTypes.size(); i++) {
            result[i] = columnTypes.get(i);
        }
        return result;
    }

    public List<?> getRowDataList() {
        return rowDataList;
    }
}
