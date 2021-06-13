package org.clever.graaljs.meta.data.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：lizw <br/>
 * 创建时间：2020-10-01 16:19 <br/>
 */
@Data
public class DataBaseSummary implements Serializable {
    /**
     * 数据库名称
     */
    private String schemaName;

    /**
     * 数据库表名称
     */
    private final List<TableSchema> tableList = new ArrayList<>();

    /**
     * @param tableName 表名称
     */
    public TableSchema getTableSchema(String tableName) {
        for (TableSchema tableSchema : tableList) {
            if (StringUtils.equalsIgnoreCase(tableName, tableSchema.getTableName())) {
                return tableSchema;
            }
        }
        return null;
    }
}
