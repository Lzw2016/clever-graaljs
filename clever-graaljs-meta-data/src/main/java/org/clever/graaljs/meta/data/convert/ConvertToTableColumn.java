package org.clever.graaljs.meta.data.convert;

import org.clever.graaljs.meta.data.model.TableColumn;
import schemacrawler.schema.Column;
import schemacrawler.schema.Schema;
import schemacrawler.schema.Table;

/**
 * 作者：lizw <br/>
 * 创建时间：2020-10-01 19:31 <br/>
 */
public class ConvertToTableColumn {
    public static TableColumn convert(Table table, Column column) {
        final Schema schema = table.getSchema();
        TableColumn tableColumn = new TableColumn();
        tableColumn.setSchemaName(schema.getCatalogName());
        tableColumn.setTableName(table.getName());
        tableColumn.setOrdinalPosition(column.getOrdinalPosition());
        tableColumn.setColumnName(column.getName());
        tableColumn.setDataType(column.getColumnDataType().getFullName());
        tableColumn.setSize(column.getSize());
        tableColumn.setWidth(column.getWidth());
        tableColumn.setDecimalDigits(column.getDecimalDigits());
        tableColumn.setNotNull(column.isNullable());
        tableColumn.setAutoIncrement(column.isAutoIncremented());
        tableColumn.setDefaultValue(column.getDefaultValue());
        tableColumn.setComment(column.getRemarks());
        tableColumn.setGenerated(column.isGenerated());
        tableColumn.setHidden(column.isHidden());
        tableColumn.setPartOfForeignKey(column.isPartOfForeignKey());
        tableColumn.setPartOfIndex(column.isPartOfIndex());
        tableColumn.setPartOfPrimaryKey(column.isPartOfPrimaryKey());
        tableColumn.setPartOfUniqueIndex(column.isPartOfUniqueIndex());
        tableColumn.setMappedClass(column.getColumnDataType().getTypeMappedClass());
        tableColumn.getAttributes().putAll(column.getAttributes());
        return tableColumn;
    }
}
