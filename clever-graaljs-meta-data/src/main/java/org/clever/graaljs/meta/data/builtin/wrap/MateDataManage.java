package org.clever.graaljs.meta.data.builtin.wrap;

import lombok.SneakyThrows;
import org.clever.graaljs.core.utils.Assert;

import javax.sql.DataSource;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 作者：lizw <br/>
 * 创建时间：2020-10-01 21:15 <br/>
 */
public class MateDataManage {
    public static final MateDataManage Instance = new MateDataManage();

    private static final ConcurrentMap<String, MateDataService> MateDataService_Map = new ConcurrentHashMap<>();

    private String defaultName;

    protected MateDataManage() {
    }

    /**
     * 设置默认数据源元数据
     *
     * @param defaultName 默认数据源元数据名称
     */
    public MateDataService setDefault(String defaultName) {
        Assert.hasText(defaultName, "参数defaultName不能为空");
        Assert.isTrue(MateDataService_Map.containsKey(defaultName), "数据源元数据不存在");
        this.defaultName = defaultName;
        return getDefault();
    }

    /**
     * 设置默认数据源元数据
     *
     * @param defaultName 默认数据源元数据名称
     * @param dataSource  默认数据源对象
     */
    public void setDefault(String defaultName, DataSource dataSource) {
        Assert.hasText(defaultName, "参数defaultName不能为空");
        this.defaultName = defaultName;
        add(defaultName, dataSource);
    }

    /**
     * 获取默认数据源元数据
     */
    public MateDataService getDefault() {
        return getMateDataService(defaultName);
    }

    /**
     * 获取默认数据源元数据名称
     */
    public String getDefaultName() {
        return defaultName;
    }

    /**
     * 根据名称获取数据源元数据
     *
     * @param name 数据源元数据名称
     */
    public MateDataService getMateDataService(String name) {
        return MateDataService_Map.get(name);
    }


    /**
     * 判断数据源元数据是否存在
     *
     * @param name 数据源元数据名称
     */
    public boolean hasMateDataService(String name) {
        return MateDataService_Map.containsKey(name);
    }


    /**
     * 添加数据源元数据
     *
     * @param name       数据源元数据名称
     * @param dataSource 数据源
     */
    public void add(String name, DataSource dataSource) {
        Assert.notNull(dataSource, "参数jdbcDataSource不能为空");
        Assert.isTrue(!MateDataService_Map.containsKey(name), "数据源元数据已经存在");
        MateDataService_Map.put(name, new MateDataService(dataSource));
    }

    /**
     * 删除数据源元数据
     *
     * @param name 数据源名称
     */
    @SneakyThrows
    public boolean del(String name) {
        Assert.isTrue(!Objects.equals(name, defaultName), "不能删除默认数据源元数据");
        MateDataService mateDataService = MateDataService_Map.get(name);
        if (mateDataService != null) {
            MateDataService_Map.remove(name);
        }
        return mateDataService != null;
    }

    /**
     * 删除所有数据源元数据
     */
    @SneakyThrows
    public void delAll() {
        Iterator<Map.Entry<String, MateDataService>> iterator = MateDataService_Map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, MateDataService> entry = iterator.next();
            iterator.remove();
        }
        defaultName = null;
    }

    /**
     * 获取所有数据源元数据名称
     */
    public Set<String> allNames() {
        return MateDataService_Map.keySet();
    }
}
