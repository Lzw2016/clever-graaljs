package org.clever.graaljs.meta.data.convert;

import org.clever.graaljs.meta.data.model.TableSchema;
import schemacrawler.schema.Schema;
import schemacrawler.schema.Table;
import schemacrawler.schema.View;

/**
 * 作者：lizw <br/>
 * 创建时间：2020-10-01 19:14 <br/>
 */
public class ConvertToTableSchema {
    public static TableSchema convert(Table table) {
        final Schema schema = table.getSchema();
        TableSchema tableSchema = new TableSchema();
        tableSchema.setView(table instanceof View);
        tableSchema.setSchemaName(schema.getCatalogName());
        tableSchema.setTableName(table.getName());
        tableSchema.setDescription(table.getRemarks());
        tableSchema.getAttributes().putAll(table.getAttributes());
        return tableSchema;
    }
}
