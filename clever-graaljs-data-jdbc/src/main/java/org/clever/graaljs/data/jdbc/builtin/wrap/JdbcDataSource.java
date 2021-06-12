package org.clever.graaljs.data.jdbc.builtin.wrap;

import org.clever.graaljs.core.internal.utils.InteropScriptToJavaUtils;
import org.clever.graaljs.data.common.model.request.QueryByPage;
import org.clever.graaljs.data.common.model.request.QueryBySort;
import org.clever.graaljs.data.common.support.mybatisplus.IPage;
import org.clever.graaljs.data.jdbc.builtin.wrap.support.AbstractDataSource;
import org.clever.graaljs.data.jdbc.support.InsertResult;
import org.clever.graaljs.data.jdbc.support.JdbcDataSourceStatus;
import org.clever.graaljs.data.jdbc.support.JdbcInfo;
import org.clever.graaljs.data.jdbc.support.mybatisplus.DbType;
import org.graalvm.polyglot.Value;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/29 12:51 <br/>
 */
public class JdbcDataSource extends AbstractDataSource {
    private final org.clever.graaljs.data.jdbc.builtin.adapter.JdbcDataSource delegate;

    public JdbcDataSource(org.clever.graaljs.data.jdbc.builtin.adapter.JdbcDataSource delegate) {
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
    public void close() throws Exception {
        delegate.close();
    }

    // --------------------------------------------------------------------------------------------
    // Query 操作
    // --------------------------------------------------------------------------------------------

    /**
     * 查询一条数据，返回一个Map
     *
     * @param sql              sql脚本，参数格式[:param]
     * @param paramMap         参数(可选)，参数格式[:param]
     * @param underlineToCamel 下划线转驼峰
     */
    public Map<String, Object> queryMap(String sql, Map<String, Object> paramMap, boolean underlineToCamel) {
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        return delegate.queryMap(sql, paramMap, underlineToCamel);
    }

    /**
     * 查询一条数据，返回一个Map
     *
     * @param sql      sql脚本，参数格式[:param]
     * @param paramMap 参数(可选)，参数格式[:param]
     */
    public Map<String, Object> queryMap(String sql, Map<String, Object> paramMap) {
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        return delegate.queryMap(sql, paramMap);
    }

    /**
     * 查询一条数据，返回一个Map
     *
     * @param sql              sql脚本，参数格式[:param]
     * @param underlineToCamel 下划线转驼峰
     */
    public Map<String, Object> queryMap(String sql, boolean underlineToCamel) {
        return delegate.queryMap(sql, underlineToCamel);
    }

    /**
     * 查询一条数据，返回一个Map
     *
     * @param sql sql脚本，参数格式[:param]
     */
    public Map<String, Object> queryMap(String sql) {
        return delegate.queryMap(sql);
    }

    /**
     * 查询多条数据，返回一个Map数组
     *
     * @param sql              sql脚本，参数格式[:param]
     * @param paramMap         参数(可选)，参数格式[:param]
     * @param underlineToCamel 下划线转驼峰
     */
    public List<Map<String, Object>> queryList(String sql, Map<String, Object> paramMap, boolean underlineToCamel) {
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        return delegate.queryList(sql, paramMap, underlineToCamel);
    }

    /**
     * 查询多条数据，返回一个Map数组
     *
     * @param sql      sql脚本，参数格式[:param]
     * @param paramMap 参数(可选)，参数格式[:param]
     */
    public List<Map<String, Object>> queryList(String sql, Map<String, Object> paramMap) {
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        return delegate.queryList(sql, paramMap);
    }

    /**
     * 查询多条数据，返回一个Map数组
     *
     * @param sql              sql脚本，参数格式[:param]
     * @param underlineToCamel 下划线转驼峰
     */
    public List<Map<String, Object>> queryList(String sql, boolean underlineToCamel) {
        return delegate.queryList(sql, underlineToCamel);
    }

    /**
     * 查询多条数据，返回一个Map数组
     *
     * @param sql sql脚本，参数格式[:param]
     */
    public List<Map<String, Object>> queryList(String sql) {
        return delegate.queryList(sql);
    }

    /**
     * 查询返回一个 String
     *
     * @param sql      sql脚本，参数格式[:param]
     * @param paramMap 参数(可选)，参数格式[:param]
     */
    public String queryString(String sql, Map<String, Object> paramMap) {
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        return delegate.queryString(sql, paramMap);
    }

    /**
     * 查询返回一个 String
     *
     * @param sql sql脚本，参数格式[:param]
     */
    public String queryString(String sql) {
        return delegate.queryString(sql);
    }

    /**
     * 查询返回一个 Long
     *
     * @param sql      sql脚本，参数格式[:param]
     * @param paramMap 参数(可选)，参数格式[:param]
     */
    public Long queryLong(String sql, Map<String, Object> paramMap) {
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        return delegate.queryLong(sql, paramMap);
    }

    /**
     * 查询返回一个 Long
     *
     * @param sql sql脚本，参数格式[:param]
     */
    public Long queryLong(String sql) {
        return delegate.queryLong(sql);
    }

    /**
     * 查询返回一个 Double
     *
     * @param sql      sql脚本，参数格式[:param]
     * @param paramMap 参数(可选)，参数格式[:param]
     */
    public Double queryDouble(String sql, Map<String, Object> paramMap) {
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        return delegate.queryDouble(sql, paramMap);
    }


    /**
     * 查询返回一个 Double
     *
     * @param sql sql脚本，参数格式[:param]
     */
    public Double queryDouble(String sql) {
        return delegate.queryDouble(sql);
    }

    /**
     * 查询返回一个 BigDecimal
     *
     * @param sql      sql脚本，参数格式[:param]
     * @param paramMap 参数(可选)，参数格式[:param]
     */
    public BigDecimal queryBigDecimal(String sql, Map<String, Object> paramMap) {
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        return delegate.queryBigDecimal(sql, paramMap);
    }

    /**
     * 查询返回一个 BigDecimal
     *
     * @param sql sql脚本，参数格式[:param]
     */
    public BigDecimal queryBigDecimal(String sql) {
        return delegate.queryBigDecimal(sql);
    }

    /**
     * 查询返回一个 Boolean
     *
     * @param sql      sql脚本，参数格式[:param]
     * @param paramMap 参数(可选)，参数格式[:param]
     */
    public Boolean queryBoolean(String sql, Map<String, Object> paramMap) {
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        return delegate.queryBoolean(sql, paramMap);
    }

    /**
     * 查询返回一个 Boolean
     *
     * @param sql sql脚本，参数格式[:param]
     */
    public Boolean queryBoolean(String sql) {
        return delegate.queryBoolean(sql);
    }

    /**
     * 查询返回一个 Date
     *
     * @param sql      sql脚本，参数格式[:param]
     * @param paramMap 参数(可选)，参数格式[:param]
     */
    public Date queryDate(String sql, Map<String, Object> paramMap) {
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        return delegate.queryDate(sql, paramMap);
    }

    /**
     * 查询返回一个 Date
     *
     * @param sql sql脚本，参数格式[:param]
     */
    public Date queryDate(String sql) {
        return delegate.queryDate(sql);
    }

    /**
     * SQL Count(获取一个SQL返回的数据总量)
     *
     * @param sql      sql脚本，参数格式[:param]
     * @param paramMap 参数(可选)，参数格式[:param]
     */
    public long queryCount(String sql, Map<String, Object> paramMap) {
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        return delegate.queryCount(sql, paramMap);
    }

    /**
     * SQL Count(获取一个SQL返回的数据总量)
     *
     * @param sql sql脚本，参数格式[:param]
     */
    public long queryCount(String sql) {
        return delegate.queryCount(sql);
    }

    /**
     * 查询多条数据(大量数据)，使用游标读取
     *
     * @param sql              sql脚本，参数格式[:param]
     * @param paramMap         参数(可选)，参数格式[:param]
     * @param batchSize        一个批次的数据量
     * @param consumer         游标批次读取数据消费者
     * @param underlineToCamel 下划线转驼峰
     */
    public void query(String sql, Map<String, Object> paramMap, int batchSize, Value consumer, boolean underlineToCamel) {
        Assert.isTrue(consumer != null && consumer.canExecute(), "参数consumer必须是回调函数");
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        delegate.query(sql, paramMap, batchSize, consumer::executeVoid, underlineToCamel);
    }

    /**
     * 查询多条数据(大量数据)，使用游标读取
     *
     * @param sql       sql脚本，参数格式[:param]
     * @param paramMap  参数(可选)，参数格式[:param]
     * @param batchSize 一个批次的数据量
     * @param consumer  游标批次读取数据消费者
     */
    public void query(String sql, Map<String, Object> paramMap, int batchSize, Value consumer) {
        Assert.isTrue(consumer != null && consumer.canExecute(), "参数consumer必须是回调函数");
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        delegate.query(sql, paramMap, batchSize, consumer::executeVoid);
    }

    /**
     * 查询多条数据(大量数据)，使用游标读取
     *
     * @param sql              sql脚本，参数格式[:param]
     * @param batchSize        一个批次的数据量
     * @param consumer         游标批次读取数据消费者
     * @param underlineToCamel 下划线转驼峰
     */
    public void query(String sql, int batchSize, Value consumer, boolean underlineToCamel) {
        Assert.isTrue(consumer != null && consumer.canExecute(), "参数consumer必须是回调函数");
        delegate.query(sql, batchSize, consumer::executeVoid, underlineToCamel);
    }

    /**
     * 查询多条数据(大量数据)，使用游标读取
     *
     * @param sql       sql脚本，参数格式[:param]
     * @param batchSize 一个批次的数据量
     * @param consumer  游标批次读取数据消费者
     */
    public void query(String sql, int batchSize, Value consumer) {
        Assert.isTrue(consumer != null && consumer.canExecute(), "参数consumer必须是回调函数");
        delegate.query(sql, batchSize, consumer::executeVoid);
    }

    /**
     * 查询多条数据(大量数据)，使用游标读取
     *
     * @param sql              sql脚本，参数格式[:param]
     * @param paramMap         参数(可选)，参数格式[:param]
     * @param consumer         游标读取数据消费者
     * @param underlineToCamel 下划线转驼峰
     */
    public void query(String sql, Map<String, Object> paramMap, Value consumer, boolean underlineToCamel) {
        Assert.isTrue(consumer != null && consumer.canExecute(), "参数consumer必须是回调函数");
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        delegate.query(sql, paramMap, consumer::executeVoid, underlineToCamel);
    }

    /**
     * 查询多条数据(大量数据)，使用游标读取
     *
     * @param sql      sql脚本，参数格式[:param]
     * @param paramMap 参数(可选)，参数格式[:param]
     * @param consumer 游标读取数据消费者
     */
    public void query(String sql, Map<String, Object> paramMap, Value consumer) {
        Assert.isTrue(consumer != null && consumer.canExecute(), "参数consumer必须是回调函数");
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        delegate.query(sql, paramMap, consumer::executeVoid);
    }

    /**
     * 查询多条数据(大量数据)，使用游标读取
     *
     * @param sql              sql脚本，参数格式[:param]
     * @param consumer         游标读取数据消费者
     * @param underlineToCamel 下划线转驼峰
     */
    public void query(String sql, Value consumer, boolean underlineToCamel) {
        Assert.isTrue(consumer != null && consumer.canExecute(), "参数consumer必须是回调函数");
        delegate.query(sql, consumer::executeVoid, underlineToCamel);
    }

    /**
     * 查询多条数据(大量数据)，使用游标读取
     *
     * @param sql      sql脚本，参数格式[:param]
     * @param consumer 游标读取数据消费者
     */
    public void query(String sql, Value consumer) {
        Assert.isTrue(consumer != null && consumer.canExecute(), "参数consumer必须是回调函数");
        delegate.query(sql, consumer::executeVoid);
    }

    /**
     * 排序查询
     *
     * @param sql              sql脚本，参数格式[:param]
     * @param sortMap          排序配置
     * @param paramMap         参数，参数格式[:param]
     * @param underlineToCamel 下划线转驼峰
     */
    public List<Map<String, Object>> queryBySort(String sql, Map<String, Object> sortMap, Map<String, Object> paramMap, boolean underlineToCamel) {
        sortMap = InteropScriptToJavaUtils.Instance.convertMap(sortMap);
        QueryBySort sort = getQueryBySort(sortMap, null);
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        return delegate.queryBySort(sql, sort, paramMap, underlineToCamel);
    }

    /**
     * 排序查询
     *
     * @param sql      sql脚本，参数格式[:param]
     * @param sortMap  排序配置
     * @param paramMap 参数，参数格式[:param]
     */
    public List<Map<String, Object>> queryBySort(String sql, Map<String, Object> sortMap, Map<String, Object> paramMap) {
        sortMap = InteropScriptToJavaUtils.Instance.convertMap(sortMap);
        QueryBySort sort = getQueryBySort(sortMap, null);
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        return delegate.queryBySort(sql, sort, paramMap);
    }

    /**
     * 排序查询
     *
     * @param sql              sql脚本，参数格式[:param]
     * @param sortMap          排序配置
     * @param underlineToCamel 下划线转驼峰
     */
    public List<Map<String, Object>> queryBySort(String sql, Map<String, Object> sortMap, boolean underlineToCamel) {
        sortMap = InteropScriptToJavaUtils.Instance.convertMap(sortMap);
        QueryBySort sort = getQueryBySort(sortMap, null);
        return delegate.queryBySort(sql, sort, underlineToCamel);
    }

    /**
     * 排序查询
     *
     * @param sql     sql脚本，参数格式[:param]
     * @param sortMap 排序配置
     */
    public List<Map<String, Object>> queryBySort(String sql, Map<String, Object> sortMap) {
        sortMap = InteropScriptToJavaUtils.Instance.convertMap(sortMap);
        QueryBySort sort = getQueryBySort(sortMap, null);
        return delegate.queryBySort(sql, sort);
    }

    /**
     * 分页查询(支持排序)，返回分页对象
     *
     * @param sql              sql脚本，参数格式[:param]
     * @param paginationMap    分页配置(支持排序)
     * @param paramMap         参数，参数格式[:param]
     * @param countQuery       是否要执行count查询(可选)
     * @param underlineToCamel 下划线转驼峰
     */
    public IPage<Map<String, Object>> queryByPage(String sql, Map<String, Object> paginationMap, Map<String, Object> paramMap, boolean countQuery, boolean underlineToCamel) {
        paginationMap = InteropScriptToJavaUtils.Instance.convertMap(paginationMap);
        QueryByPage pagination = getQueryByPage(paginationMap);
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        return delegate.queryByPage(sql, pagination, paramMap, countQuery, underlineToCamel);
    }

    /**
     * 分页查询(支持排序)，返回分页对象
     *
     * @param sql           sql脚本，参数格式[:param]
     * @param paginationMap 分页配置(支持排序)
     * @param paramMap      参数，参数格式[:param]
     * @param countQuery    是否要执行count查询(可选)
     */
    public IPage<Map<String, Object>> queryByPage(String sql, Map<String, Object> paginationMap, Map<String, Object> paramMap, boolean countQuery) {
        paginationMap = InteropScriptToJavaUtils.Instance.convertMap(paginationMap);
        QueryByPage pagination = getQueryByPage(paginationMap);
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        return delegate.queryByPage(sql, pagination, paramMap, countQuery);
    }

    /**
     * 分页查询(支持排序)，返回分页对象
     *
     * @param sql           sql脚本，参数格式[:param]
     * @param paginationMap 分页配置(支持排序)
     * @param paramMap      参数，参数格式[:param]
     */
    public IPage<Map<String, Object>> queryByPage(String sql, Map<String, Object> paginationMap, Map<String, Object> paramMap) {
        paginationMap = InteropScriptToJavaUtils.Instance.convertMap(paginationMap);
        QueryByPage pagination = getQueryByPage(paginationMap);
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        return delegate.queryByPage(sql, pagination, paramMap);
    }

    /**
     * 分页查询(支持排序)，返回分页对象
     *
     * @param sql           sql脚本，参数格式[:param]
     * @param paginationMap 分页配置(支持排序)
     * @param countQuery    是否要执行count查询(可选)
     */
    public IPage<Map<String, Object>> queryByPage(String sql, Map<String, Object> paginationMap, boolean countQuery) {
        paginationMap = InteropScriptToJavaUtils.Instance.convertMap(paginationMap);
        QueryByPage pagination = getQueryByPage(paginationMap);
        return delegate.queryByPage(sql, pagination, countQuery);
    }

    /**
     * 分页查询(支持排序)，返回分页对象
     *
     * @param sql           sql脚本，参数格式[:param]
     * @param paginationMap 分页配置(支持排序) - 支持加入查询参数
     */
    @SuppressWarnings("DuplicatedCode")
    public IPage<Map<String, Object>> queryByPage(String sql, Map<String, Object> paginationMap) {
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
        return delegate.queryByPage(sql, pagination, paramMap);
    }

    /**
     * 查询数据库表数据
     *
     * @param tableName         表名称
     * @param whereMap          更新条件字段(只支持=，and条件)
     * @param camelToUnderscore whereMap字段驼峰转下划线(可选)
     * @param underlineToCamel  返回数据下划线转驼峰
     */
    public List<Map<String, Object>> queryTableList(String tableName, Map<String, Object> whereMap, boolean camelToUnderscore, boolean underlineToCamel) {
        whereMap = InteropScriptToJavaUtils.Instance.convertMap(whereMap);
        return delegate.queryTableList(tableName, whereMap, camelToUnderscore, underlineToCamel);
    }

    /**
     * 查询数据库表数据
     *
     * @param tableName         表名称
     * @param whereMap          更新条件字段(只支持=，and条件)
     * @param camelToUnderscore 字段驼峰转下划线(可选)
     */
    public List<Map<String, Object>> queryTableList(String tableName, Map<String, Object> whereMap, boolean camelToUnderscore) {
        whereMap = InteropScriptToJavaUtils.Instance.convertMap(whereMap);
        return delegate.queryTableList(tableName, whereMap, camelToUnderscore);
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
     * @param tableName         表名称
     * @param whereMap          更新条件字段(只支持=，and条件)
     * @param camelToUnderscore whereMap字段驼峰转下划线(可选)
     * @param underlineToCamel  返回数据下划线转驼峰
     */
    public Map<String, Object> queryTableMap(String tableName, Map<String, Object> whereMap, boolean camelToUnderscore, boolean underlineToCamel) {
        whereMap = InteropScriptToJavaUtils.Instance.convertMap(whereMap);
        return delegate.queryTableMap(tableName, whereMap, camelToUnderscore, underlineToCamel);
    }

    /**
     * 查询数据库表数据
     *
     * @param tableName         表名称
     * @param whereMap          更新条件字段(只支持=，and条件)
     * @param camelToUnderscore 字段驼峰转下划线(可选)
     */
    public Map<String, Object> queryTableMap(String tableName, Map<String, Object> whereMap, boolean camelToUnderscore) {
        whereMap = InteropScriptToJavaUtils.Instance.convertMap(whereMap);
        return delegate.queryTableMap(tableName, whereMap, camelToUnderscore);
    }

    /**
     * 查询数据库表数据
     *
     * @param tableName 表名称
     * @param whereMap  更新条件字段(只支持=，and条件)
     */
    public Map<String, Object> queryTableMap(String tableName, Map<String, Object> whereMap) {
        whereMap = InteropScriptToJavaUtils.Instance.convertMap(whereMap);
        return delegate.queryTableMap(tableName, whereMap);
    }

    // --------------------------------------------------------------------------------------------
    // Update 操作
    // --------------------------------------------------------------------------------------------

    /**
     * 执行更新SQL，返回更新影响数据量
     *
     * @param sql      sql脚本，参数格式[:param]
     * @param paramMap 参数(可选)，参数格式[:param]
     */
    public int update(String sql, Map<String, Object> paramMap) {
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        return delegate.update(sql, paramMap);
    }

    /**
     * 执行更新SQL，返回更新影响数据量
     *
     * @param sql sql脚本，参数格式[:param]
     */
    public int update(String sql) {
        return delegate.update(sql);
    }

    /**
     * 更新数据库表数据
     *
     * @param tableName         表名称
     * @param fields            更新字段值
     * @param whereMap          更新条件字段(只支持=，and条件)
     * @param camelToUnderscore 字段驼峰转下划线(可选)
     */
    public int updateTable(String tableName, Map<String, Object> fields, Map<String, Object> whereMap, boolean camelToUnderscore) {
        fields = InteropScriptToJavaUtils.Instance.convertMap(fields);
        whereMap = InteropScriptToJavaUtils.Instance.convertMap(whereMap);
        return delegate.updateTable(tableName, fields, whereMap, camelToUnderscore);
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
     * @param tableName         表名称
     * @param fields            更新字段值
     * @param where             自定义where条件(不用写where关键字)
     * @param camelToUnderscore 字段驼峰转下划线(可选)
     */
    public int updateTable(String tableName, Map<String, Object> fields, String where, boolean camelToUnderscore) {
        fields = InteropScriptToJavaUtils.Instance.convertMap(fields);
        return delegate.updateTable(tableName, fields, where, camelToUnderscore);
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
     * @param sql          sql脚本，参数格式[:param]
     * @param paramMapList 参数数组，参数格式[:param]
     */
    public int[] batchUpdate(String sql, List<Map<String, Object>> paramMapList) {
        paramMapList = InteropScriptToJavaUtils.Instance.convertMapList(paramMapList);
        return delegate.batchUpdate(sql, paramMapList);
    }

    // --------------------------------------------------------------------------------------------
    // Delete 操作
    // --------------------------------------------------------------------------------------------

    /**
     * 删除数据库表数据
     *
     * @param tableName         表名称
     * @param whereMap          更新条件字段(只支持=，and条件)
     * @param camelToUnderscore 字段驼峰转下划线(可选)
     */
    public int deleteTable(String tableName, Map<String, Object> whereMap, boolean camelToUnderscore) {
        whereMap = InteropScriptToJavaUtils.Instance.convertMap(whereMap);
        return delegate.deleteTable(tableName, whereMap, camelToUnderscore);
    }

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
     * @param sql      sql脚本，参数格式[:param]
     * @param paramMap 参数(可选)，参数格式[:param]
     */
    public InsertResult insert(String sql, Map<String, Object> paramMap) {
        paramMap = InteropScriptToJavaUtils.Instance.convertMap(paramMap);
        return delegate.insert(sql, paramMap);
    }

    /**
     * 执行insert SQL，返回数据库自增主键值和新增数据量
     *
     * @param sql sql脚本，参数格式[:param]
     */
    public InsertResult insert(String sql) {
        return delegate.insert(sql);
    }

    /**
     * 数据插入到表
     *
     * @param tableName         表名称
     * @param fields            字段名
     * @param camelToUnderscore 字段驼峰转下划线(可选)
     */
    public InsertResult insertTable(String tableName, Map<String, Object> fields, boolean camelToUnderscore) {
        fields = InteropScriptToJavaUtils.Instance.convertMap(fields);
        return delegate.insertTable(tableName, fields, camelToUnderscore);
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
     * @param tableName         表名称
     * @param fieldsList        字段名集合
     * @param camelToUnderscore 字段驼峰转下划线(可选)
     */
    public List<InsertResult> insertTables(String tableName, List<Map<String, Object>> fieldsList, boolean camelToUnderscore) {
        fieldsList = InteropScriptToJavaUtils.Instance.convertMapList(fieldsList);
        return delegate.insertTables(tableName, fieldsList, camelToUnderscore);
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
}
