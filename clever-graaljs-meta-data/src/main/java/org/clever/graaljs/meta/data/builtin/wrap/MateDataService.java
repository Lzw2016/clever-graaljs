package org.clever.graaljs.meta.data.builtin.wrap;

import org.clever.graaljs.meta.data.model.DataBaseSummary;
import org.clever.graaljs.meta.data.model.TableSchema;

import javax.sql.DataSource;
import java.util.List;

/**
 * 作者：lizw <br/>
 * 创建时间：2020-10-01 21:07 <br/>
 */
public class MateDataService {
    private final org.clever.graaljs.meta.data.builtin.adapter.MateDataService delegate;

    public MateDataService(DataSource dataSource) {
        delegate = new org.clever.graaljs.meta.data.builtin.adapter.MateDataService(dataSource);
    }

    /**
     * 重新加载数据库元数据
     */
    public void reload() {
        delegate.reload();
    }

    public List<DataBaseSummary> getDataBaseSummaryList() {
        return delegate.getDataBaseSummaryList();
    }

    /**
     * @param database 数据库名称(schema名称)
     */
    public DataBaseSummary getDataBaseSummary(String database) {
        return delegate.getDataBaseSummary(database);
    }

    /**
     * @param database  数据库名称(schema名称)
     * @param tableName 表名称
     */
    public TableSchema getTableSchema(String database, String tableName) {
        return delegate.getTableSchema(database, tableName);
    }
}
