package org.clever.graaljs.fast.api.service;

import lombok.extern.slf4j.Slf4j;
import org.clever.graaljs.data.jdbc.mybatis.AbstractMyBatisMapperSql;
import org.clever.graaljs.fast.api.config.FastApiConfig;
import org.clever.graaljs.fast.api.model.MapperFileResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/14 14:42 <br/>
 */
@Component
@Slf4j
public class FileResourceMyBatisMapperSqlService extends AbstractMyBatisMapperSql {
    private final static String BASE_SQL = "" +
            "select " +
            "    id as fileResourceId, " +
            "    namespace, " +
            "    path, " +
            "    name, " +
            "    content, " +
            "    ifnull(update_at, create_at) as lastModifiedTime " +
            "from file_resource " +
            "where is_file=1 and namespace=? " +
            "%s " +
            "order by update_at desc, id desc";

    private final JdbcTemplate jdbcTemplate;
    /**
     * FileResource 命名空间
     */
    private final String namespace;
    /**
     * MyBatisMapper {@code Map<文件全路径, MapperFileResource>}
     */
    private volatile ConcurrentMap<String, MapperFileResource> mapperCache = new ConcurrentHashMap<>();
    /**
     * 文件资源最后修改时间
     */
    private volatile Date lastModifiedTime;
    /**
     * 操作缓存锁
     */
    private final Object lock = new Object();

    public FileResourceMyBatisMapperSqlService(JdbcTemplate jdbcTemplate, FastApiConfig fastApiConfig) {
        this.jdbcTemplate = jdbcTemplate;
        this.namespace = fastApiConfig.getNamespace();
    }

    /**
     * 全量加载缓存
     */
    @Override
    public void reloadAll() {
        String sql = String.format(BASE_SQL, "and lower(name) like '%.xml'");
        synchronized (lock) {
            List<MapperFileResource> list = jdbcTemplate.queryForList(sql, MapperFileResource.class, namespace);
            ConcurrentMap<String, MapperFileResource> newCache = new ConcurrentHashMap<>(list.size());
            for (MapperFileResource resource : list) {
                if (resource.getLastModifiedTime() != null) {
                    if (lastModifiedTime == null || resource.getLastModifiedTime().compareTo(lastModifiedTime) > 0) {
                        lastModifiedTime = resource.getLastModifiedTime();
                    }
                }
                final String fullPath = resource.getPath() + resource.getName();
                newCache.put(fullPath, resource);
                log.info("# 解析文件: {}", fullPath);
                try (ByteArrayInputStream inputStream = new ByteArrayInputStream(resource.getContent().getBytes(StandardCharsets.UTF_8))) {
                    loadSqlSource(fullPath, inputStream);
                } catch (Exception e) {
                    log.error("解析Mapper.xml文件失败 | path={}", fullPath);
                }
            }
            mapperCache = newCache;
        }
    }

    @Override
    public void reloadFile(String absolutePath) {
    }

    /**
     * 增量更新缓存
     */
    public void updateCache() {
        // 全量加载
        if (lastModifiedTime == null) {
            reloadAll();
            return;
        }
        // 增量更新
        synchronized (lock) {

        }
    }
}
