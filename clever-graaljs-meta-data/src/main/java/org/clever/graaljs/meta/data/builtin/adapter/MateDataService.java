package org.clever.graaljs.meta.data.builtin.adapter;

import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.meta.data.convert.ConvertToDataBaseSummary;
import org.clever.graaljs.meta.data.convert.ConvertToTableColumn;
import org.clever.graaljs.meta.data.convert.ConvertToTableSchema;
import org.clever.graaljs.meta.data.model.DataBaseSummary;
import org.clever.graaljs.meta.data.model.TableColumn;
import org.clever.graaljs.meta.data.model.TableSchema;
import schemacrawler.inclusionrule.ExcludeAll;
import schemacrawler.schema.Catalog;
import schemacrawler.schema.Column;
import schemacrawler.schema.Schema;
import schemacrawler.schema.Table;
import schemacrawler.schemacrawler.*;
import schemacrawler.tools.utility.SchemaCrawlerUtility;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：lizw <br/>
 * 创建时间：2020-10-01 16:24 <br/>
 */
public class MateDataService {
    private final DataSource dataSource;
    @Getter
    private final List<DataBaseSummary> dataBaseSummaryList = new ArrayList<>();

    public MateDataService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 重新加载数据库元数据
     */
    public void reload() {
        dataBaseSummaryList.clear();
        dataBaseSummaryList.addAll(allDataBaseSummary());
    }

    /**
     * @param database 数据库名称(schema名称)
     */
    public DataBaseSummary getDataBaseSummary(String database) {
        for (DataBaseSummary dataBaseSummary : dataBaseSummaryList) {
            if (StringUtils.equalsIgnoreCase(database, dataBaseSummary.getSchemaName())) {
                return dataBaseSummary;
            }
        }
        return null;
    }

    /**
     * @param database  数据库名称(schema名称)
     * @param tableName 表名称
     */
    public TableSchema getTableSchema(String database, String tableName) {
        DataBaseSummary dataBaseSummary = getDataBaseSummary(database);
        if (dataBaseSummary != null) {
            return dataBaseSummary.getTableSchema(tableName);
        }
        return null;
    }

    @SneakyThrows
    protected Connection getConnection() {
        return this.dataSource.getConnection();
    }

    /**
     * 获取数据库基本信息(概要)<br/>
     * <b>
     * 1.所有数据库名称<br/>
     * 2.数据库得所有表名称<br/>
     * </b>
     *
     * @return 数据库基本信息(概要)
     */
    @SneakyThrows
    protected List<DataBaseSummary> allDataBaseSummary() {
        final LimitOptionsBuilder limitOptionsBuilder = LimitOptionsBuilder.builder()
                .includeRoutines(new ExcludeAll());
        final LoadOptionsBuilder loadOptionsBuilder = LoadOptionsBuilder.builder()
                .withSchemaInfoLevel(SchemaInfoLevelBuilder.maximum());
        final SchemaCrawlerOptions options = SchemaCrawlerOptionsBuilder.newSchemaCrawlerOptions()
                .withLimitOptions(limitOptionsBuilder.toOptions())
                .withLoadOptions(loadOptionsBuilder.toOptions());
        final List<DataBaseSummary> dataBaseSummaryList = new ArrayList<>();
        try (Connection connection = getConnection()) {
            final Catalog catalog = SchemaCrawlerUtility.getCatalog(connection, options);
            for (final Schema schema : catalog.getSchemas()) {
                final DataBaseSummary dataBaseSummary = ConvertToDataBaseSummary.convert(schema);
                dataBaseSummaryList.add(dataBaseSummary);
                for (final Table table : catalog.getTables(schema)) {
                    final TableSchema tableSchema = ConvertToTableSchema.convert(table);
                    dataBaseSummary.getTableList().add(tableSchema);
                    for (final Column column : table.getColumns()) {
                        TableColumn tableColumn = ConvertToTableColumn.convert(table, column);
                        tableSchema.getColumnList().add(tableColumn);
                    }
                }
            }
        }
        return dataBaseSummaryList;
    }
}
