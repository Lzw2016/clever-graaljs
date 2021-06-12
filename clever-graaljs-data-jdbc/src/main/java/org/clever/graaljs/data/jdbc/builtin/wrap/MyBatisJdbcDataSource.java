package org.clever.graaljs.data.jdbc.builtin.wrap;


import lombok.Getter;
import org.clever.graaljs.core.internal.utils.InteropScriptToJavaUtils;
import org.clever.graaljs.data.common.model.request.QueryByPage;
import org.clever.graaljs.data.common.model.request.QueryBySort;
import org.clever.graaljs.data.common.support.mybatisplus.IPage;
import org.clever.graaljs.data.common.support.mybatisplus.Page;
import org.clever.graaljs.data.jdbc.builtin.wrap.support.AbstractDataSource;
import org.clever.graaljs.data.jdbc.support.*;
import org.clever.graaljs.data.jdbc.support.mybatisplus.DbType;
import org.graalvm.polyglot.Value;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/09/02 20:59 <br/>
 */
public class MyBatisJdbcDataSource extends AbstractDataSource {
    /**
     * JDBC数据源
     */
    private final org.clever.graaljs.data.jdbc.builtin.adapter.MyBatisJdbcDataSource delegate;

    public MyBatisJdbcDataSource(org.clever.graaljs.data.jdbc.builtin.adapter.MyBatisJdbcDataSource delegate) {
        this.delegate = delegate;
    }

    /**
     * 获取数据库类型
     */
    public DbType getDbType() {
        return delegate.getDbType();
    }

    /**
     * 当前数据源是否关闭
     */
    public boolean isClosed() {
        return delegate.isClosed();
    }

    /**
     * 关闭当前数据源
     */
    public void close() {
        delegate.close();
    }

    /**
     * 查询一条数据，返回一个Map
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    public Map<String, Object> queryEntity(String sqlId, Map<String, Object> paramMap) {
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        return delegate.queryEntity(sqlId, paramMap);
    }

    /**
     * 查询一条数据，返回一个Map
     *
     * @param sqlId SqlID
     */
    public Map<String, Object> queryEntity(String sqlId) {
        return delegate.queryEntity(sqlId);
    }

    /**
     * 查询多条数据，返回一个Map数组
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    public List<Map<String, Object>> queryList(String sqlId, Map<String, Object> paramMap) {
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        return delegate.queryList(sqlId, paramMap);
    }

    /**
     * 查询多条数据，返回一个Map数组
     *
     * @param sqlId SqlID
     */
    public List<Map<String, Object>> queryList(String sqlId) {
        return delegate.queryList(sqlId);
    }

    /**
     * 查询返回一个 String
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    public String queryString(String sqlId, Map<String, Object> paramMap) {
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        return delegate.queryString(sqlId, paramMap);
    }

    /**
     * 查询返回一个 String
     *
     * @param sqlId SqlID
     */
    public String queryString(String sqlId) {
        return delegate.queryString(sqlId);
    }

    /**
     * 查询返回一个 Long
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    public Long queryLong(String sqlId, Map<String, Object> paramMap) {
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        return delegate.queryLong(sqlId, paramMap);
    }

    /**
     * 查询返回一个 Long
     *
     * @param sqlId SqlID
     */
    public Long queryLong(String sqlId) {
        return delegate.queryLong(sqlId);
    }

    /**
     * 查询返回一个 Double
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    public Double queryDouble(String sqlId, Map<String, Object> paramMap) {
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        return delegate.queryDouble(sqlId, paramMap);
    }


    /**
     * 查询返回一个 Double
     *
     * @param sqlId SqlID
     */
    public Double queryDouble(String sqlId) {
        return delegate.queryDouble(sqlId);
    }

    /**
     * 查询返回一个 BigDecimal
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    public BigDecimal queryBigDecimal(String sqlId, Map<String, Object> paramMap) {
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        return delegate.queryBigDecimal(sqlId, paramMap);
    }

    /**
     * 查询返回一个 BigDecimal
     *
     * @param sqlId SqlID
     */
    public BigDecimal queryBigDecimal(String sqlId) {
        return delegate.queryBigDecimal(sqlId);
    }

    /**
     * 查询返回一个 Boolean
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    public Boolean queryBoolean(String sqlId, Map<String, Object> paramMap) {
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        return delegate.queryBoolean(sqlId, paramMap);
    }

    /**
     * 查询返回一个 Boolean
     *
     * @param sqlId SqlID
     */
    public Boolean queryBoolean(String sqlId) {
        return delegate.queryBoolean(sqlId);
    }

    /**
     * 查询返回一个 Date
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    public Date queryDate(String sqlId, Map<String, Object> paramMap) {
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        return delegate.queryDate(sqlId, paramMap);
    }

    /**
     * 查询返回一个 Date
     *
     * @param sqlId SqlID
     */
    public Date queryDate(String sqlId) {
        return delegate.queryDate(sqlId);
    }

    /**
     * SQL Count(获取一个SQL返回的数据总量)
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    public long queryCount(String sqlId, Map<String, Object> paramMap) {
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        return delegate.queryCount(sqlId, paramMap);
    }

    /**
     * SQL Count(获取一个SQL返回的数据总量)
     *
     * @param sqlId SqlID
     */
    public long queryCount(String sqlId) {
        return delegate.queryCount(sqlId);
    }

    /**
     * 查询多条数据(大量数据)，使用游标读取
     *
     * @param sqlId     SqlID
     * @param paramMap  查询参数
     * @param batchSize 一个批次的数据量
     * @param consumer  游标批次读取数据消费者
     */
    public void query(String sqlId, Map<String, Object> paramMap, int batchSize, Value consumer) {
        Assert.isTrue(consumer != null && consumer.canExecute(), "参数consumer必须是回调函数");
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        delegate.query(sqlId, paramMap, batchSize, getBatchDataConsumer(consumer));
    }

    /**
     * 查询多条数据(大量数据)，使用游标读取
     *
     * @param sqlId     SqlID
     * @param batchSize 一个批次的数据量
     * @param consumer  游标批次读取数据消费者
     */
    public void query(String sqlId, int batchSize, Value consumer) {
        Assert.isTrue(consumer != null && consumer.canExecute(), "参数consumer必须是回调函数");
        delegate.query(sqlId, batchSize, getBatchDataConsumer(consumer));
    }

    /**
     * 查询多条数据(大量数据)，使用游标读取
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     * @param consumer 游标读取数据消费者
     */
    public void query(String sqlId, Map<String, Object> paramMap, Value consumer) {
        Assert.isTrue(consumer != null && consumer.canExecute(), "参数consumer必须是回调函数");
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        delegate.query(sqlId, paramMap, getRowDataConsumer(consumer));
    }

    /**
     * 查询多条数据(大量数据)，使用游标读取
     *
     * @param sqlId    SqlID
     * @param consumer 游标读取数据消费者
     */
    public void query(String sqlId, Value consumer) {
        Assert.isTrue(consumer != null && consumer.canExecute(), "参数consumer必须是回调函数");
        delegate.query(sqlId, getRowDataConsumer(consumer));
    }

    /**
     * 排序查询
     *
     * @param sqlId    SqlID
     * @param sortMap  排序配置
     * @param paramMap 查询参数
     */
    public List<Map<String, Object>> queryBySort(String sqlId, Map<String, Object> sortMap, Map<String, Object> paramMap) {
        sortMap = InteropScriptToJavaUtils.Instance.convertMap(sortMap);
        QueryBySort sort = getQueryBySort(sortMap, null);
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        return delegate.queryBySort(sqlId, sort, paramMap);
    }

    /**
     * 排序查询
     *
     * @param sqlId   SqlID
     * @param sortMap 排序配置
     */
    public List<Map<String, Object>> queryBySort(String sqlId, Map<String, Object> sortMap) {
        sortMap = InteropScriptToJavaUtils.Instance.convertMap(sortMap);
        QueryBySort sort = getQueryBySort(sortMap, null);
        return delegate.queryBySort(sqlId, sort);
    }

    /**
     * 分页查询(支持排序)，返回分页对象
     *
     * @param sqlId         SqlID
     * @param paramMap      查询参数
     * @param paginationMap 分页配置(支持排序)
     * @param countQuery    是否要执行count查询(可选)
     */
    public IPage<Map<String, Object>> queryByPage(String sqlId, Map<String, Object> paginationMap, Map<String, Object> paramMap, boolean countQuery) {
        paginationMap = InteropScriptToJavaUtils.Instance.convertMap(paginationMap);
        QueryByPage pagination = getQueryByPage(paginationMap);
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        IPage<Map<String, Object>> page = delegate.queryByPage(sqlId, pagination, paramMap, countQuery);
        return getProxyObjectPage(page);
    }

    /**
     * 分页查询(支持排序)，返回分页对象
     *
     * @param sqlId         SqlID
     * @param paginationMap 分页配置(支持排序)
     * @param paramMap      查询参数
     */
    public IPage<Map<String, Object>> queryByPage(String sqlId, Map<String, Object> paginationMap, Map<String, Object> paramMap) {
        paginationMap = InteropScriptToJavaUtils.Instance.convertMap(paginationMap);
        QueryByPage pagination = getQueryByPage(paginationMap);
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        IPage<Map<String, Object>> page = delegate.queryByPage(sqlId, pagination, paramMap);
        return getProxyObjectPage(page);
    }

    /**
     * 分页查询(支持排序)，返回分页对象
     *
     * @param sqlId         SqlID
     * @param paginationMap 分页配置(支持排序)
     * @param countQuery    是否要执行count查询(可选)
     */
    public IPage<Map<String, Object>> queryByPage(String sqlId, Map<String, Object> paginationMap, boolean countQuery) {
        paginationMap = InteropScriptToJavaUtils.Instance.convertMap(paginationMap);
        QueryByPage pagination = getQueryByPage(paginationMap);
        IPage<Map<String, Object>> page = delegate.queryByPage(sqlId, pagination, countQuery);
        return getProxyObjectPage(page);
    }

    /**
     * 分页查询(支持排序)，返回分页对象
     *
     * @param sqlId         SqlID
     * @param paginationMap 分页配置(支持排序) - 支持加入查询参数
     */
    @SuppressWarnings("DuplicatedCode")
    public IPage<Map<String, Object>> queryByPage(String sqlId, Map<String, Object> paginationMap) {
        paginationMap = InteropScriptToJavaUtils.Instance.convertMap(paginationMap);
        QueryByPage pagination = getQueryByPage(paginationMap);
        Map<String, Object> paramMap = new HashMap<>(paginationMap);
        paramMap.remove(Order_Field_Name);
        paramMap.remove(Sort_Name);
        paramMap.remove(Order_Fields_Name);
        paramMap.remove(Sorts_Name);
        paramMap.remove(Fields_Mapping_Name);
        paramMap.remove(Page_Size_Name);
        paramMap.remove(Page_No_Name);
        paramMap.remove(Is_Search_Count_Name);
        IPage<Map<String, Object>> page = delegate.queryByPage(sqlId, pagination, paramMap);
        return getProxyObjectPage(page);
    }

    /**
     * 查询数据库表数据
     *
     * @param tableName 表名称
     * @param whereMap  更新条件字段(只支持=，and条件)
     */
    public List<Map<String, Object>> queryTableList(String tableName, Map<String, Object> whereMap) {
        whereMap = InteropScriptToJavaUtils.Instance.convertMap(whereMap);
        return delegate.queryTableList(tableName, whereMap);
    }

    /**
     * 查询数据库表数据
     *
     * @param tableName 表名称
     * @param whereMap  更新条件字段(只支持=，and条件)
     */
    public Map<String, Object> queryTableEntity(String tableName, Map<String, Object> whereMap) {
        whereMap = InteropScriptToJavaUtils.Instance.convertMap(whereMap);
        return delegate.queryTableEntity(tableName, whereMap);
    }

    // --------------------------------------------------------------------------------------------
    // Update 操作
    // --------------------------------------------------------------------------------------------

    /**
     * 执行更新SQL，返回更新影响数据量
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    public int update(String sqlId, Map<String, Object> paramMap) {
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        return delegate.update(sqlId, paramMap);
    }

    /**
     * 执行更新SQL，返回更新影响数据量
     *
     * @param sqlId SqlID
     */
    public int update(String sqlId) {
        return delegate.update(sqlId);
    }

    /**
     * 更新数据库表数据
     *
     * @param tableName 表名称
     * @param fields    更新字段值
     * @param whereMap  更新条件字段(只支持=，and条件)
     */
    public int updateTable(String tableName, Map<String, Object> fields, Map<String, Object> whereMap) {
        fields = InteropScriptToJavaUtils.Instance.convertMap(fields);
        whereMap = InteropScriptToJavaUtils.Instance.convertMap(whereMap);
        return delegate.updateTable(tableName, fields, whereMap);
    }

    /**
     * 更新数据库表数据
     *
     * @param tableName 表名称
     * @param fields    更新字段值
     * @param where     自定义where条件(不用写where关键字)
     */
    public int updateTable(String tableName, Map<String, Object> fields, String where) {
        fields = InteropScriptToJavaUtils.Instance.convertMap(fields);
        return delegate.updateTable(tableName, fields, where);
    }

    /**
     * 批量执行更新SQL，返回更新影响数据量
     *
     * @param sqlId        SqlID
     * @param paramMapList 参数数组
     */
    public int[] batchUpdate(String sqlId, List<Map<String, Object>> paramMapList) {
        paramMapList = InteropScriptToJavaUtils.Instance.convertMapList(paramMapList);
        return delegate.batchUpdate(sqlId, paramMapList);
    }

    // --------------------------------------------------------------------------------------------
    // Delete 操作
    // --------------------------------------------------------------------------------------------

    /**
     * 删除数据库表数据
     *
     * @param tableName 表名称
     * @param whereMap  更新条件字段(只支持=，and条件)
     */
    public int deleteTable(String tableName, Map<String, Object> whereMap) {
        whereMap = InteropScriptToJavaUtils.Instance.convertMap(whereMap);
        return delegate.deleteTable(tableName, whereMap);
    }

    // --------------------------------------------------------------------------------------------
    // Insert 操作
    // --------------------------------------------------------------------------------------------

    /**
     * 执行insert SQL，返回数据库自增主键值和新增数据量
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    public InsertResult insert(String sqlId, Map<String, Object> paramMap) {
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        return delegate.insert(sqlId, paramMap);
    }

    /**
     * 执行insert SQL，返回数据库自增主键值和新增数据量
     *
     * @param sqlId SqlID
     */
    public InsertResult insert(String sqlId) {
        return delegate.insert(sqlId);
    }

    /**
     * 数据插入到表
     *
     * @param tableName 表名称
     * @param fields    字段名
     */
    public InsertResult insertTable(String tableName, Map<String, Object> fields) {
        fields = InteropScriptToJavaUtils.Instance.convertMap(fields);
        return delegate.insertTable(tableName, fields);
    }

    /**
     * 数据插入到表
     *
     * @param tableName  表名称
     * @param fieldsList 字段名集合
     */
    public List<InsertResult> insertTables(String tableName, List<Map<String, Object>> fieldsList) {
        fieldsList = InteropScriptToJavaUtils.Instance.convertMapList(fieldsList);
        return delegate.insertTables(tableName, fieldsList);
    }

    // --------------------------------------------------------------------------------------------
    //  事务操作
    // --------------------------------------------------------------------------------------------

    /**
     * 在事务内支持操作
     *
     * @param action              事务内数据库操作
     * @param propagationBehavior 设置事务传递性 {@link org.springframework.transaction.TransactionDefinition#PROPAGATION_REQUIRED}
     * @param timeout             设置事务超时时间，-1表示不超时(单位：秒)
     * @param isolationLevel      设置事务隔离级别 {@link org.springframework.transaction.TransactionDefinition#ISOLATION_DEFAULT}
     * @param readOnly            设置事务是否只读
     * @see org.springframework.transaction.TransactionDefinition
     */
    public Value beginTX(Value action, int propagationBehavior, int timeout, int isolationLevel, boolean readOnly) {
        Assert.isTrue(action != null && action.canExecute(), "参数action必须是回调函数");
        return delegate.beginTX(action::execute, propagationBehavior, timeout, isolationLevel, readOnly);
    }

    /**
     * 在事务内支持操作
     *
     * @param action              事务内数据库操作
     * @param propagationBehavior 设置事务传递性 {@link org.springframework.transaction.TransactionDefinition#PROPAGATION_REQUIRED}
     * @param timeout             设置事务超时时间(单位：秒)
     * @param isolationLevel      设置事务隔离级别 {@link org.springframework.transaction.TransactionDefinition#ISOLATION_DEFAULT}
     * @see org.springframework.transaction.TransactionDefinition
     */
    public Value beginTX(Value action, int propagationBehavior, int timeout, int isolationLevel) {
        Assert.isTrue(action != null && action.canExecute(), "参数action必须是回调函数");
        return delegate.beginTX(action::execute, propagationBehavior, timeout, isolationLevel);
    }

    /**
     * 在事务内支持操作
     *
     * @param action              事务内数据库操作
     * @param propagationBehavior 设置事务传递性 {@link org.springframework.transaction.TransactionDefinition#PROPAGATION_REQUIRED}
     * @param timeout             设置事务超时时间(单位：秒)
     * @see org.springframework.transaction.TransactionDefinition
     */
    public Value beginTX(Value action, int propagationBehavior, int timeout) {
        Assert.isTrue(action != null && action.canExecute(), "参数action必须是回调函数");
        return delegate.beginTX(action::execute, propagationBehavior, timeout);
    }

    /**
     * 在事务内支持操作
     *
     * @param action              事务内数据库操作
     * @param propagationBehavior 设置事务传递性 {@link org.springframework.transaction.TransactionDefinition#PROPAGATION_REQUIRED}
     * @see org.springframework.transaction.TransactionDefinition
     */
    public Value beginTX(Value action, int propagationBehavior) {
        Assert.isTrue(action != null && action.canExecute(), "参数action必须是回调函数");
        return delegate.beginTX(action::execute, propagationBehavior);
    }

    /**
     * 在事务内支持操作
     *
     * @param action 事务内数据库操作
     * @see org.springframework.transaction.TransactionDefinition
     */
    public Value beginTX(Value action) {
        Assert.isTrue(action != null && action.canExecute(), "参数action必须是回调函数");
        return delegate.beginTX(action::execute);
    }

    /**
     * 在事务内支持操作
     *
     * @param action              事务内数据库操作
     * @param propagationBehavior 设置事务传递性 {@link org.springframework.transaction.TransactionDefinition#PROPAGATION_REQUIRED}
     * @param timeout             设置事务超时时间，-1表示不超时(单位：秒)
     * @param isolationLevel      设置事务隔离级别 {@link org.springframework.transaction.TransactionDefinition#ISOLATION_DEFAULT}
     * @see org.springframework.transaction.TransactionDefinition
     */
    public Value beginReadOnlyTX(Value action, int propagationBehavior, int timeout, int isolationLevel) {
        Assert.isTrue(action != null && action.canExecute(), "参数action必须是回调函数");
        return delegate.beginReadOnlyTX(action::execute, propagationBehavior, timeout, isolationLevel);
    }

    /**
     * 在事务内支持操作
     *
     * @param action              事务内数据库操作
     * @param propagationBehavior 设置事务传递性 {@link org.springframework.transaction.TransactionDefinition#PROPAGATION_REQUIRED}
     * @param timeout             设置事务超时时间，-1表示不超时(单位：秒)
     * @see org.springframework.transaction.TransactionDefinition
     */
    public Value beginReadOnlyTX(Value action, int propagationBehavior, int timeout) {
        Assert.isTrue(action != null && action.canExecute(), "参数action必须是回调函数");
        return delegate.beginReadOnlyTX(action::execute, propagationBehavior, timeout);
    }

    /**
     * 在事务内支持操作
     *
     * @param action              事务内数据库操作
     * @param propagationBehavior 设置事务传递性 {@link org.springframework.transaction.TransactionDefinition#PROPAGATION_REQUIRED}
     * @see org.springframework.transaction.TransactionDefinition
     */
    public Value beginReadOnlyTX(Value action, int propagationBehavior) {
        Assert.isTrue(action != null && action.canExecute(), "参数action必须是回调函数");
        return delegate.beginReadOnlyTX(action::execute, propagationBehavior);
    }

    /**
     * 在事务内支持操作
     *
     * @param action 事务内数据库操作
     * @see org.springframework.transaction.TransactionDefinition
     */
    public Value beginReadOnlyTX(Value action) {
        Assert.isTrue(action != null && action.canExecute(), "参数action必须是回调函数");
        return delegate.beginReadOnlyTX(action::execute);
    }

    // --------------------------------------------------------------------------------------------
    //  其它 操作
    // --------------------------------------------------------------------------------------------

    /**
     * 获取数据源信息
     */
    public JdbcInfo getInfo() {
        return delegate.getInfo();
    }

    /**
     * 获取数据源状态
     */
    public JdbcDataSourceStatus getStatus() {
        return delegate.getStatus();
    }

    // --------------------------------------------------------------------------------------------
    //  内部函数
    // --------------------------------------------------------------------------------------------

    private static IPage<Map<String, Object>> getProxyObjectPage(IPage<Map<String, Object>> page) {
        Page<Map<String, Object>> result = new Page<>(page.getCurrent(), page.getSize(), page.getTotal(), page.isSearchCount());
        result.setOptimizeCountSql(page.optimizeCountSql());
        result.setOrders(page.orders());
        result.setRecords(page.getRecords());
        return result;
    }

    public Consumer<BatchData> getBatchDataConsumer(Value consumer) {
        return batchData -> consumer.executeVoid(new EntityBatchData(batchData));
    }

    public Consumer<RowData> getRowDataConsumer(Value consumer) {
        return rowData -> consumer.executeVoid(new EntityRowData(rowData));
    }

    @Getter
    public static class EntityBatchData extends BatchData {
        /**
         * 当前批次数
         */
        private final List<Map<String, Object>> rowDataList;

        @SuppressWarnings("unchecked")
        public EntityBatchData(BatchData batchData) {
            super(
                    batchData.getColumnNames().toArray(new String[0]),
                    batchData.originalGetColumnTypes(),
                    batchData.getColumnCount(),
                    (List<Map<String, Object>>) batchData.getRowDataList(),
                    batchData.getRowCount()
            );
            this.rowDataList = (List<Map<String, Object>>) batchData.getRowDataList();
        }
    }

    @Getter
    public static class EntityRowData extends RowData {
        /**
         * 当前批次数
         */
        private final Map<String, Object> rowData;

        @SuppressWarnings("unchecked")
        public EntityRowData(RowData rowData) {
            super(
                    rowData.getColumnNames().toArray(new String[0]),
                    rowData.originalGetColumnTypes(),
                    rowData.getColumnCount(),
                    (Map<String, Object>) rowData.getRowData(),
                    rowData.getRowCount()
            );
            this.rowData = (Map<String, Object>) rowData.getRowData();
        }
    }
}
