package org.clever.graaljs.data.jdbc.builtin.adapter;

import lombok.extern.slf4j.Slf4j;
import org.clever.dynamic.sql.BoundSql;
import org.clever.graaljs.core.utils.TupleTow;
import org.clever.graaljs.data.common.AbstractDataSource;
import org.clever.graaljs.data.common.model.request.QueryByPage;
import org.clever.graaljs.data.common.model.request.QueryBySort;
import org.clever.graaljs.data.common.support.mybatisplus.IPage;
import org.clever.graaljs.data.jdbc.mybatis.MyBatisMapperSql;
import org.clever.graaljs.data.jdbc.support.*;
import org.clever.graaljs.data.jdbc.support.mybatisplus.DbType;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/09/02 19:53 <br/>
 */
@Slf4j
public class MyBatisJdbcDataSource extends AbstractDataSource {
    /**
     * JDBC数据源
     */
    private final JdbcDataSource jdbcDataSource;
    /**
     * Mapper动态SQL
     */
    private final MyBatisMapperSql mapperSql;

    public MyBatisJdbcDataSource(JdbcDataSource jdbcDataSource, MyBatisMapperSql mapperSql) {
        Assert.notNull(jdbcDataSource, "参数jdbcDataSource不能为空");
        Assert.notNull(mapperSql, "参数mapperSql不能为空");
        this.jdbcDataSource = jdbcDataSource;
        this.mapperSql = mapperSql;
    }

    /**
     * 获取数据库类型
     */
    public DbType getDbType() {
        return jdbcDataSource.getDbType();
    }

    @Override
    public boolean isClosed() {
        return jdbcDataSource.isClosed();
    }

    @Override
    public void close() {
        jdbcDataSource.close();
    }

    // --------------------------------------------------------------------------------------------
    // Query 操作
    // --------------------------------------------------------------------------------------------

    /**
     * 查询一条数据，返回一个Map
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    public Map<String, Object> queryEntity(String sqlId, Map<String, Object> paramMap) {
        TupleTow<String, Map<String, Object>> sqlInfo = getSql(sqlId, paramMap);
        return jdbcDataSource.queryMap(sqlInfo.getValue1(), sqlInfo.getValue2(), true);
    }

    /**
     * 查询一条数据，返回一个Map
     *
     * @param sqlId SqlID
     */
    public Map<String, Object> queryEntity(String sqlId) {
        return jdbcDataSource.queryMap(getSql(sqlId), true);
    }

    /**
     * 查询多条数据，返回一个Map数组
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    public List<Map<String, Object>> queryList(String sqlId, Map<String, Object> paramMap) {
        TupleTow<String, Map<String, Object>> sqlInfo = getSql(sqlId, paramMap);
        return jdbcDataSource.queryList(sqlInfo.getValue1(), sqlInfo.getValue2(), true);
    }

    /**
     * 查询多条数据，返回一个Map数组
     *
     * @param sqlId SqlID
     */
    public List<Map<String, Object>> queryList(String sqlId) {
        return jdbcDataSource.queryList(getSql(sqlId), true);
    }

    /**
     * 查询返回一个 String
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    public String queryString(String sqlId, Map<String, Object> paramMap) {
        TupleTow<String, Map<String, Object>> sqlInfo = getSql(sqlId, paramMap);
        return jdbcDataSource.queryString(sqlInfo.getValue1(), sqlInfo.getValue2());
    }

    /**
     * 查询返回一个 String
     *
     * @param sqlId SqlID
     */
    public String queryString(String sqlId) {
        return jdbcDataSource.queryString(getSql(sqlId));
    }

    /**
     * 查询返回一个 Long
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    public Long queryLong(String sqlId, Map<String, Object> paramMap) {
        TupleTow<String, Map<String, Object>> sqlInfo = getSql(sqlId, paramMap);
        return jdbcDataSource.queryLong(sqlInfo.getValue1(), sqlInfo.getValue2());
    }

    /**
     * 查询返回一个 Long
     *
     * @param sqlId SqlID
     */
    public Long queryLong(String sqlId) {
        return jdbcDataSource.queryLong(getSql(sqlId));
    }

    /**
     * 查询返回一个 Double
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    public Double queryDouble(String sqlId, Map<String, Object> paramMap) {
        TupleTow<String, Map<String, Object>> sqlInfo = getSql(sqlId, paramMap);
        return jdbcDataSource.queryDouble(sqlInfo.getValue1(), sqlInfo.getValue2());
    }

    /**
     * 查询返回一个 Double
     *
     * @param sqlId SqlID
     */
    public Double queryDouble(String sqlId) {
        return jdbcDataSource.queryDouble(getSql(sqlId));
    }

    /**
     * 查询返回一个 BigDecimal
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    public BigDecimal queryBigDecimal(String sqlId, Map<String, Object> paramMap) {
        TupleTow<String, Map<String, Object>> sqlInfo = getSql(sqlId, paramMap);
        return jdbcDataSource.queryBigDecimal(sqlInfo.getValue1(), sqlInfo.getValue2());
    }

    /**
     * 查询返回一个 BigDecimal
     *
     * @param sqlId SqlID
     */
    public BigDecimal queryBigDecimal(String sqlId) {
        return jdbcDataSource.queryBigDecimal(getSql(sqlId));
    }

    /**
     * 查询返回一个 Boolean
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    public Boolean queryBoolean(String sqlId, Map<String, Object> paramMap) {
        TupleTow<String, Map<String, Object>> sqlInfo = getSql(sqlId, paramMap);
        return jdbcDataSource.queryBoolean(sqlInfo.getValue1(), sqlInfo.getValue2());
    }

    /**
     * 查询返回一个 Boolean
     *
     * @param sqlId SqlID
     */
    public Boolean queryBoolean(String sqlId) {
        return jdbcDataSource.queryBoolean(getSql(sqlId));
    }

    /**
     * 查询返回一个 Date
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    public Date queryDate(String sqlId, Map<String, Object> paramMap) {
        TupleTow<String, Map<String, Object>> sqlInfo = getSql(sqlId, paramMap);
        return jdbcDataSource.queryDate(sqlInfo.getValue1(), sqlInfo.getValue2());
    }

    /**
     * 查询返回一个 Date
     *
     * @param sqlId SqlID
     */
    public Date queryDate(String sqlId) {
        return jdbcDataSource.queryDate(getSql(sqlId));
    }

    /**
     * SQL Count(获取一个SQL返回的数据总量)
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     */
    public long queryCount(String sqlId, Map<String, Object> paramMap) {
        TupleTow<String, Map<String, Object>> sqlInfo = getSql(sqlId, paramMap);
        return jdbcDataSource.queryCount(sqlInfo.getValue1(), sqlInfo.getValue2());
    }

    /**
     * SQL Count(获取一个SQL返回的数据总量)
     *
     * @param sqlId SqlID
     */
    public long queryCount(String sqlId) {
        return jdbcDataSource.queryCount(getSql(sqlId));
    }

    /**
     * 查询多条数据(大量数据)，使用游标读取
     *
     * @param sqlId     SqlID
     * @param paramMap  查询参数
     * @param batchSize 一个批次的数据量
     * @param consumer  游标批次读取数据消费者
     */
    public void query(String sqlId, Map<String, Object> paramMap, int batchSize, Consumer<BatchData> consumer) {
        TupleTow<String, Map<String, Object>> sqlInfo = getSql(sqlId, paramMap);
        jdbcDataSource.query(sqlInfo.getValue1(), sqlInfo.getValue2(), batchSize, consumer, true);
    }

    /**
     * 查询多条数据(大量数据)，使用游标读取
     *
     * @param sqlId     SqlID
     * @param batchSize 一个批次的数据量
     * @param consumer  游标批次读取数据消费者
     */
    public void query(String sqlId, int batchSize, Consumer<BatchData> consumer) {
        jdbcDataSource.query(getSql(sqlId), batchSize, consumer, true);
    }

    /**
     * 查询多条数据(大量数据)，使用游标读取
     *
     * @param sqlId    SqlID
     * @param paramMap 查询参数
     * @param consumer 游标读取数据消费者
     */
    public void query(String sqlId, Map<String, Object> paramMap, Consumer<RowData> consumer) {
        TupleTow<String, Map<String, Object>> sqlInfo = getSql(sqlId, paramMap);
        jdbcDataSource.query(sqlInfo.getValue1(), sqlInfo.getValue2(), consumer, true);
    }

    /**
     * 查询多条数据(大量数据)，使用游标读取
     *
     * @param sqlId    SqlID
     * @param consumer 游标读取数据消费者
     */
    public void query(String sqlId, Consumer<RowData> consumer) {
        jdbcDataSource.query(getSql(sqlId), consumer, true);
    }

    /**
     * 排序查询
     *
     * @param sqlId    SqlID
     * @param sort     排序配置
     * @param paramMap 查询参数
     */
    public List<Map<String, Object>> queryBySort(String sqlId, QueryBySort sort, Map<String, Object> paramMap) {
        TupleTow<String, Map<String, Object>> sqlInfo = getSql(sqlId, paramMap);
        return jdbcDataSource.queryBySort(sqlInfo.getValue1(), sort, sqlInfo.getValue2(), true);
    }

    /**
     * 排序查询
     *
     * @param sqlId SqlID
     * @param sort  排序配置
     */
    public List<Map<String, Object>> queryBySort(String sqlId, QueryBySort sort) {
        return jdbcDataSource.queryBySort(getSql(sqlId), sort, true);
    }

    /**
     * 分页查询(支持排序)，返回分页对象
     *
     * @param sqlId      SqlID
     * @param pagination 分页配置(支持排序)
     * @param paramMap   查询参数
     */
    public IPage<Map<String, Object>> queryByPage(String sqlId, QueryByPage pagination, Map<String, Object> paramMap) {
        TupleTow<String, Map<String, Object>> sqlInfo = getSql(sqlId, paramMap);
        return jdbcDataSource.queryByPage(sqlInfo.getValue1(), pagination, sqlInfo.getValue2(), true);
    }

    /**
     * 分页查询(支持排序)，返回分页对象
     *
     * @param sqlId      SqlID
     * @param pagination 分页配置(支持排序)
     * @param countQuery 是否要执行count查询(可选)
     */
    public IPage<Map<String, Object>> queryByPage(String sqlId, QueryByPage pagination, boolean countQuery) {
        return jdbcDataSource.queryByPage(getSql(sqlId), pagination, Collections.emptyMap(), true);
    }

    /**
     * 分页查询(支持排序)，返回分页对象
     *
     * @param sqlId      SqlID
     * @param pagination 分页配置(支持排序)
     */
    public IPage<Map<String, Object>> queryByPage(String sqlId, QueryByPage pagination) {
        return jdbcDataSource.queryByPage(getSql(sqlId), pagination, Collections.emptyMap(), true);
    }

    /**
     * 查询数据库表数据
     *
     * @param tableName 表名称
     * @param whereMap  更新条件字段(只支持=，and条件)
     */
    public List<Map<String, Object>> queryTableList(String tableName, Map<String, Object> whereMap) {
        return jdbcDataSource.queryTableList(tableName, whereMap, true, true);
    }

    /**
     * 查询数据库表数据
     *
     * @param tableName 表名称
     * @param whereMap  更新条件字段(只支持=，and条件)
     */
    public Map<String, Object> queryTableEntity(String tableName, Map<String, Object> whereMap) {
        return jdbcDataSource.queryTableMap(tableName, whereMap, true, true);
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
        TupleTow<String, Map<String, Object>> sqlInfo = getSql(sqlId, paramMap);
        return jdbcDataSource.update(sqlInfo.getValue1(), sqlInfo.getValue2());
    }

    /**
     * 执行更新SQL，返回更新影响数据量
     *
     * @param sqlId SqlID
     */
    public int update(String sqlId) {
        return jdbcDataSource.update(getSql(sqlId));
    }

    /**
     * 更新数据库表数据
     *
     * @param tableName 表名称
     * @param fields    更新字段值
     * @param whereMap  更新条件字段(只支持=，and条件)
     */
    public int updateTable(String tableName, Map<String, Object> fields, Map<String, Object> whereMap) {
        return jdbcDataSource.updateTable(tableName, fields, whereMap, true);
    }

    /**
     * 更新数据库表数据
     *
     * @param tableName 表名称
     * @param fields    更新字段值
     * @param where     自定义where条件(不用写where关键字)
     */
    public int updateTable(String tableName, Map<String, Object> fields, String where) {
        return jdbcDataSource.updateTable(tableName, fields, where, true);
    }

    /**
     * 批量执行更新SQL，返回更新影响数据量
     *
     * @param sqlId        SqlID
     * @param paramMapList 参数数组
     */
    public int[] batchUpdate(String sqlId, Collection<Map<String, Object>> paramMapList) {
        return jdbcDataSource.batchUpdate(getSql(sqlId), paramMapList);
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
        return jdbcDataSource.deleteTable(tableName, whereMap, true);
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
        TupleTow<String, Map<String, Object>> sqlInfo = getSql(sqlId, paramMap);
        return jdbcDataSource.insert(sqlInfo.getValue1(), sqlInfo.getValue2());
    }

    /**
     * 执行insert SQL，返回数据库自增主键值和新增数据量
     *
     * @param sqlId SqlID
     */
    public InsertResult insert(String sqlId) {
        return jdbcDataSource.insert(getSql(sqlId));
    }

    /**
     * 数据插入到表
     *
     * @param tableName 表名称
     * @param fields    字段名
     */
    public InsertResult insertTable(String tableName, Map<String, Object> fields) {
        return jdbcDataSource.insertTable(tableName, fields, true);
    }

    /**
     * 数据插入到表
     *
     * @param tableName  表名称
     * @param fieldsList 字段名集合
     */
    public List<InsertResult> insertTables(String tableName, Collection<Map<String, Object>> fieldsList) {
        return jdbcDataSource.insertTables(tableName, fieldsList, true);
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
     * @param <T>                 返回值类型
     * @see org.springframework.transaction.TransactionDefinition
     */
    public <T> T beginTX(TransactionCallback<T> action, int propagationBehavior, int timeout, int isolationLevel, boolean readOnly) {
        return jdbcDataSource.beginTX(action, propagationBehavior, timeout, isolationLevel, readOnly);
    }

    /**
     * 在事务内支持操作
     *
     * @param action              事务内数据库操作
     * @param propagationBehavior 设置事务传递性 {@link org.springframework.transaction.TransactionDefinition#PROPAGATION_REQUIRED}
     * @param timeout             设置事务超时时间(单位：秒)
     * @param isolationLevel      设置事务隔离级别 {@link org.springframework.transaction.TransactionDefinition#ISOLATION_DEFAULT}
     * @param <T>                 返回值类型
     * @see org.springframework.transaction.TransactionDefinition
     */
    public <T> T beginTX(TransactionCallback<T> action, int propagationBehavior, int timeout, int isolationLevel) {
        return jdbcDataSource.beginTX(action, propagationBehavior, timeout, isolationLevel);
    }

    /**
     * 在事务内支持操作
     *
     * @param action              事务内数据库操作
     * @param propagationBehavior 设置事务传递性 {@link org.springframework.transaction.TransactionDefinition#PROPAGATION_REQUIRED}
     * @param timeout             设置事务超时时间(单位：秒)
     * @param <T>                 返回值类型
     * @see org.springframework.transaction.TransactionDefinition
     */
    public <T> T beginTX(TransactionCallback<T> action, int propagationBehavior, int timeout) {
        return jdbcDataSource.beginTX(action, propagationBehavior, timeout);
    }

    /**
     * 在事务内支持操作
     *
     * @param action              事务内数据库操作
     * @param propagationBehavior 设置事务传递性 {@link org.springframework.transaction.TransactionDefinition#PROPAGATION_REQUIRED}
     * @param <T>                 返回值类型
     * @see org.springframework.transaction.TransactionDefinition
     */
    public <T> T beginTX(TransactionCallback<T> action, int propagationBehavior) {
        return jdbcDataSource.beginTX(action, propagationBehavior);
    }

    /**
     * 在事务内支持操作
     *
     * @param action 事务内数据库操作
     * @param <T>    返回值类型
     * @see org.springframework.transaction.TransactionDefinition
     */
    public <T> T beginTX(TransactionCallback<T> action) {
        return jdbcDataSource.beginTX(action);
    }

    /**
     * 在事务内支持操作
     *
     * @param action              事务内数据库操作
     * @param propagationBehavior 设置事务传递性 {@link org.springframework.transaction.TransactionDefinition#PROPAGATION_REQUIRED}
     * @param timeout             设置事务超时时间，-1表示不超时(单位：秒)
     * @param isolationLevel      设置事务隔离级别 {@link org.springframework.transaction.TransactionDefinition#ISOLATION_DEFAULT}
     * @param <T>                 返回值类型
     * @see org.springframework.transaction.TransactionDefinition
     */
    public <T> T beginReadOnlyTX(TransactionCallback<T> action, int propagationBehavior, int timeout, int isolationLevel) {
        return jdbcDataSource.beginReadOnlyTX(action, propagationBehavior, timeout, isolationLevel);
    }

    /**
     * 在事务内支持操作
     *
     * @param action              事务内数据库操作
     * @param propagationBehavior 设置事务传递性 {@link org.springframework.transaction.TransactionDefinition#PROPAGATION_REQUIRED}
     * @param timeout             设置事务超时时间，-1表示不超时(单位：秒)
     * @param <T>                 返回值类型
     * @see org.springframework.transaction.TransactionDefinition
     */
    public <T> T beginReadOnlyTX(TransactionCallback<T> action, int propagationBehavior, int timeout) {
        return jdbcDataSource.beginReadOnlyTX(action, propagationBehavior, timeout);
    }

    /**
     * 在事务内支持操作
     *
     * @param action              事务内数据库操作
     * @param propagationBehavior 设置事务传递性 {@link org.springframework.transaction.TransactionDefinition#PROPAGATION_REQUIRED}
     * @param <T>                 返回值类型
     * @see org.springframework.transaction.TransactionDefinition
     */
    public <T> T beginReadOnlyTX(TransactionCallback<T> action, int propagationBehavior) {
        return jdbcDataSource.beginReadOnlyTX(action, propagationBehavior);
    }

    /**
     * 在事务内支持操作
     *
     * @param action 事务内数据库操作
     * @param <T>    返回值类型
     * @see org.springframework.transaction.TransactionDefinition
     */
    public <T> T beginReadOnlyTX(TransactionCallback<T> action) {
        return jdbcDataSource.beginReadOnlyTX(action);
    }

    // --------------------------------------------------------------------------------------------
    //  其它 操作
    // --------------------------------------------------------------------------------------------

    /**
     * 获取数据源信息
     */
    public JdbcInfo getInfo() {
        return jdbcDataSource.getInfo();
    }

    /**
     * 获取数据源状态
     */
    public JdbcDataSourceStatus getStatus() {
        return jdbcDataSource.getStatus();
    }

    private TupleTow<String, Map<String, Object>> getSql(String sqlId, Object parameter) {
        Assert.hasText(sqlId, "参数sqlId不能为空");
        if (parameter == null) {
            parameter = new HashMap<>();
        }
        BoundSql boundSql = mapperSql.getBoundSql(sqlId, parameter);
        Assert.notNull(boundSql, "SQL不存在，sqlId=" + sqlId);
        return TupleTow.creat(boundSql.getNamedParameterSql(), boundSql.getParameterMap());
    }

    private String getSql(String sqlId) {
        Assert.hasText(sqlId, "参数sqlId不能为空");
        BoundSql boundSql = mapperSql.getBoundSql(sqlId, new HashMap<>());
        return boundSql.getNamedParameterSql();
    }
}
