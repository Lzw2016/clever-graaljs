package org.clever.graaljs.data.jdbc.builtin.wrap;


import lombok.SneakyThrows;
import org.clever.graaljs.core.internal.utils.InteropScriptToJavaUtils;
import org.clever.graaljs.data.jdbc.builtin.wrap.support.AbstractJdbcDatabase;
import org.clever.graaljs.data.jdbc.builtin.wrap.support.JdbcConfig;
import org.clever.graaljs.data.jdbc.support.JdbcDataSourceStatus;
import org.clever.graaljs.data.jdbc.support.JdbcInfo;
import org.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/29 12:50 <br/>
 */
public class JdbcDatabase extends AbstractJdbcDatabase {
    public static final JdbcDatabase Instance = new JdbcDatabase();

    private static final ConcurrentMap<String, JdbcDataSource> DATASOURCE_MAP = new ConcurrentHashMap<>();

    private String defaultName;

    protected JdbcDatabase() {
    }

    /**
     * 设置默认数据源
     *
     * @param defaultName 默认数据源名称
     * @return 默认数据源对象
     */
    public JdbcDataSource setDefault(String defaultName) {
        Assert.hasText(defaultName, "参数defaultName不能为空");
        Assert.isTrue(DATASOURCE_MAP.containsKey(defaultName) && !DATASOURCE_MAP.get(defaultName).isClosed(), "默认数据源已经关闭(isClosed)");
        this.defaultName = defaultName;
        return getDefault();
    }

    /**
     * 设置默认数据源
     *
     * @param defaultName    默认数据源名称
     * @param jdbcDataSource 默认数据源对象
     */
    public void setDefault(String defaultName, org.clever.graaljs.data.jdbc.builtin.adapter.JdbcDataSource jdbcDataSource) {
        Assert.hasText(defaultName, "参数defaultName不能为空");
        Assert.isTrue(!jdbcDataSource.isClosed(), "默认数据源已经关闭(isClosed)");
        this.defaultName = defaultName;
        add(defaultName, jdbcDataSource);
    }

    /**
     * 获取默认数据源
     */
    public JdbcDataSource getDefault() {
        return getDataSource(defaultName);
    }

    /**
     * 获取默认数据源名称
     */
    public String getDefaultName() {
        return defaultName;
    }

    /**
     * 根据名称获取数据源
     *
     * @param name 数据源名称
     */
    public JdbcDataSource getDataSource(String name) {
        return DATASOURCE_MAP.get(name);
    }

    /**
     * 判断数据源是否存在
     *
     * @param name 数据源名称
     */
    public boolean hasDataSource(String name) {
        return DATASOURCE_MAP.containsKey(name);
    }

    /**
     * 添加数据源
     *
     * @param name           数据源名称
     * @param jdbcDataSource 数据源
     */
    public void add(String name, org.clever.graaljs.data.jdbc.builtin.adapter.JdbcDataSource jdbcDataSource) {
        Assert.notNull(jdbcDataSource, "参数jdbcDataSource不能为空");
        Assert.isTrue(!DATASOURCE_MAP.containsKey(name), "数据源已经存在");
        DATASOURCE_MAP.put(name, new JdbcDataSource(jdbcDataSource));
    }

    /**
     * 添加数据源
     *
     * @param name   数据源名称
     * @param config 数据源配置
     */
    @SuppressWarnings("DuplicatedCode")
    public JdbcDataSource add(String name, Map<String, Object> config) {
        Assert.isTrue(!DATASOURCE_MAP.containsKey(name), "数据源已经存在");
        config = InteropScriptToJavaUtils.Instance.convertMap(config);
        JdbcConfig jdbcConfig = getJdbcConfig(config);
        org.clever.graaljs.data.jdbc.builtin.adapter.JdbcDataSource jdbcDataSource = new org.clever.graaljs.data.jdbc.builtin.adapter.JdbcDataSource(jdbcConfig.getHikariConfig());
        add(name, jdbcDataSource);
        return DATASOURCE_MAP.get(name);
    }

    /**
     * 删除数据源
     *
     * @param name 数据源名称
     */
    @SneakyThrows
    public boolean del(String name) {
        Assert.isTrue(!Objects.equals(name, defaultName), "不能删除默认数据源");
        JdbcDataSource jdbcDataSource = DATASOURCE_MAP.get(name);
        if (jdbcDataSource != null) {
            jdbcDataSource.close();
            DATASOURCE_MAP.remove(name);
        }
        return jdbcDataSource != null;
    }

    /**
     * 删除所有数据源
     */
    @SneakyThrows
    public void delAll() {
        Iterator<Map.Entry<String, JdbcDataSource>> iterator = DATASOURCE_MAP.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, JdbcDataSource> entry = iterator.next();
            entry.getValue().close();
            iterator.remove();
        }
        defaultName = null;
    }

    /**
     * 获取所有数据源名称
     */
    public Set<String> allNames() {
        return DATASOURCE_MAP.keySet();
    }

    /**
     * 获取数据源信息
     *
     * @param name 数据源名称
     */
    public JdbcInfo getInfo(String name) {
        JdbcDataSource jdbcDataSource = getDataSource(name);
        return jdbcDataSource == null ? null : jdbcDataSource.getInfo();
    }

    /**
     * 获取所有数据源信息
     */
    public Map<String, JdbcInfo> allInfos() {
        Map<String, JdbcInfo> map = new HashMap<>(DATASOURCE_MAP.size());
        for (Map.Entry<String, JdbcDataSource> entry : DATASOURCE_MAP.entrySet()) {
            String name = entry.getKey();
            map.put(name, getInfo(name));
        }
        return map;
    }

    /**
     * 获取数据源状态
     *
     * @param name 数据源名称
     */
    public JdbcDataSourceStatus getStatus(String name) {
        JdbcDataSource jdbcDataSource = getDataSource(name);
        return jdbcDataSource == null ? null : jdbcDataSource.getStatus();
    }

    /**
     * 获取数据源状态
     */
    public Map<String, JdbcDataSourceStatus> allStatus() {
        Map<String, JdbcDataSourceStatus> map = new HashMap<>(DATASOURCE_MAP.size());
        for (Map.Entry<String, JdbcDataSource> entry : DATASOURCE_MAP.entrySet()) {
            String name = entry.getKey();
            map.put(name, getStatus(name));
        }
        return map;
    }
}
