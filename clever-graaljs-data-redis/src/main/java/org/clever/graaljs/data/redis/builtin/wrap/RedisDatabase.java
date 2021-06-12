package org.clever.graaljs.data.redis.builtin.wrap;

import lombok.SneakyThrows;
import org.clever.graaljs.core.internal.utils.InteropScriptToJavaUtils;
import org.clever.graaljs.data.redis.builtin.wrap.support.AbstractRedisDatabase;
import org.clever.graaljs.data.redis.builtin.wrap.support.RedisConfig;
import org.clever.graaljs.data.redis.support.RedisDataSourceStatus;
import org.clever.graaljs.data.redis.support.RedisInfo;
import org.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/11/11 16:59 <br/>
 */
public class RedisDatabase extends AbstractRedisDatabase {
    public static final RedisDatabase Instance = new RedisDatabase();

    private static final ConcurrentMap<String, RedisDataSource> DATASOURCE_MAP = new ConcurrentHashMap<>();

    private String defaultName;

    protected RedisDatabase() {
    }

    /**
     * 设置默认数据源
     *
     * @param defaultName 默认数据源名称
     * @return 默认数据源对象
     */
    public RedisDataSource setDefault(String defaultName) {
        Assert.hasText(defaultName, "参数defaultName不能为空");
        Assert.isTrue(DATASOURCE_MAP.containsKey(defaultName) && !DATASOURCE_MAP.get(defaultName).isClosed(), "默认数据源已经关闭(isClosed)");
        this.defaultName = defaultName;
        return getDefault();
    }

    /**
     * 设置默认数据源
     *
     * @param defaultName     默认数据源名称
     * @param redisDataSource 默认数据源对象
     */
    public void setDefault(String defaultName, org.clever.graaljs.data.redis.builtin.adapter.RedisDataSource redisDataSource) {
        Assert.hasText(defaultName, "参数defaultName不能为空");
        Assert.isTrue(!redisDataSource.isClosed(), "默认数据源已经关闭(isClosed)");
        this.defaultName = defaultName;
        add(defaultName, redisDataSource);
    }

    /**
     * 获取默认数据源
     */
    public RedisDataSource getDefault() {
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
    public RedisDataSource getDataSource(String name) {
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
     * @param name            数据源名称
     * @param redisDataSource 数据源
     */
    public void add(String name, org.clever.graaljs.data.redis.builtin.adapter.RedisDataSource redisDataSource) {
        Assert.notNull(redisDataSource, "参数redisDataSource不能为空");
        Assert.isTrue(!DATASOURCE_MAP.containsKey(name), "数据源已经存在");
        DATASOURCE_MAP.put(name, new RedisDataSource(redisDataSource));
    }

    /**
     * 添加数据源
     *
     * @param name   数据源名称
     * @param config 数据源配置
     */
    public RedisDataSource add(String name, Map<String, Object> config) {
        Assert.isTrue(!DATASOURCE_MAP.containsKey(name), "数据源已经存在");
        config = InteropScriptToJavaUtils.Instance.deepConvertMap(config);
        RedisConfig redisConfig = getRedisConfig(config);
        org.clever.graaljs.data.redis.builtin.adapter.RedisDataSource redisDataSource = new org.clever.graaljs.data.redis.builtin.adapter.RedisDataSource(redisConfig.getRedisProperties());
        add(name, redisDataSource);
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
        RedisDataSource redisDataSource = DATASOURCE_MAP.get(name);
        if (redisDataSource != null) {
            redisDataSource.close();
            DATASOURCE_MAP.remove(name);
        }
        return redisDataSource != null;
    }

    /**
     * 删除所有数据源
     */
    @SneakyThrows
    public void delAll() {
        Iterator<Map.Entry<String, RedisDataSource>> iterator = DATASOURCE_MAP.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, RedisDataSource> entry = iterator.next();
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
    public RedisInfo getInfo(String name) {
        RedisDataSource redisDataSource = getDataSource(name);
        return redisDataSource == null ? null : redisDataSource.getInfo();
    }

    /**
     * 获取所有数据源信息
     */
    public Map<String, RedisInfo> allInfos() {
        Map<String, RedisInfo> map = new HashMap<>(DATASOURCE_MAP.size());
        for (Map.Entry<String, RedisDataSource> entry : DATASOURCE_MAP.entrySet()) {
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
    public RedisDataSourceStatus getStatus(String name) {
        RedisDataSource redisDataSource = getDataSource(name);
        return redisDataSource == null ? null : redisDataSource.getStatus();
    }

    /**
     * 获取数据源状态
     */
    public Map<String, RedisDataSourceStatus> allStatus() {
        Map<String, RedisDataSourceStatus> map = new HashMap<>(DATASOURCE_MAP.size());
        for (Map.Entry<String, RedisDataSource> entry : DATASOURCE_MAP.entrySet()) {
            String name = entry.getKey();
            map.put(name, getStatus(name));
        }
        return map;
    }
}
