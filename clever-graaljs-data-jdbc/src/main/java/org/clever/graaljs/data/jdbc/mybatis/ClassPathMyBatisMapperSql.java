package org.clever.graaljs.data.jdbc.mybatis;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/09/30 15:51 <br/>
 */
@Slf4j
public class ClassPathMyBatisMapperSql extends AbstractMyBatisMapperSql {
    /**
     * 加载Resource实现对象
     */
    private static final PathMatchingResourcePatternResolver Path_Matching_Resolver = new PathMatchingResourcePatternResolver();

    private final String locationPattern;

    private final Set<Resource> resourceSet;

    /**
     * @param locationPattern classpath路径模式
     */
    public ClassPathMyBatisMapperSql(String locationPattern) {
        this.locationPattern = locationPattern;
        this.resourceSet = initResource();
        initLoad();
    }

    @SneakyThrows
    protected synchronized Set<Resource> initResource() {
        // 加载资源
        Resource[] resources = Path_Matching_Resolver.getResources(locationPattern);
        Set<Resource> resourceSet = new HashSet<>(resources.length);
        for (Resource resource : resources) {
            if (resource.isReadable() && resource.getURL().toExternalForm().toLowerCase().endsWith(".xml")) {
                resourceSet.add(resource);
            }
        }
        log.info("Resource加载完成! locationPattern={} | size={} | xml-file-size={}", locationPattern, resources.length, resourceSet.size());
        return resourceSet;
    }

    @Override
    public void reloadAll() {
        mapperFiles.clear();
        sqlSourceMap.clear();
        for (Resource resource : resourceSet) {
            try {
                final String absolutePath = resource.getURL().toExternalForm();
                log.info("# 解析文件: {}", absolutePath);
                loadSqlSource(resource);
            } catch (Exception e) {
                log.error("解析Mapper.xml文件失败 | path={}", resource, e);
            }
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void reloadFile(String absolutePath) {
        if (absolutePath == null || !absolutePath.endsWith(".xml")) {
            return;
        }
        final boolean preExists = mapperFiles.containsKey(absolutePath);
        final Resource resource = Path_Matching_Resolver.getResource(absolutePath);
        final boolean nowExists = resource.isReadable();
        // 删除之前的 SqlId
        if (preExists) {
            List<String> sqlIds = mapperFiles.get(absolutePath);
            if (sqlIds != null) {
                sqlIds.forEach(sqlSourceMap::remove);
            }
            mapperFiles.remove(absolutePath);
        }
        // 解析新的文件
        if (nowExists) {
            long startTime = System.currentTimeMillis();
            loadSqlSource(resource);
            long endTime = System.currentTimeMillis();
            log.info("# 重新解析文件成功 | 耗时: {}ms | path={}", (endTime - startTime), absolutePath);
        } else {
            log.info("# 清除文件SQL | path={}", absolutePath);
        }
    }

    @SneakyThrows
    protected void loadSqlSource(Resource resource) {
        final String absolutePath = resource.getURL().toExternalForm();
        try (InputStream inputStream = resource.getInputStream()) {
            loadSqlSource(absolutePath, inputStream);
        } catch (Exception exception) {
            log.error("解析Mapper.xml文件失败 | path={}", absolutePath);
        }
    }
}
