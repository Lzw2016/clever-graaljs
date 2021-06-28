package org.clever.graaljs.fast.api.service;

import lombok.extern.slf4j.Slf4j;
import org.clever.graaljs.data.jdbc.mybatis.AbstractMyBatisMapperSql;
import org.clever.graaljs.fast.api.config.FastApiConfig;
import org.clever.graaljs.fast.api.model.MapperFileResource;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/14 14:42 <br/>
 */
@Service
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
    private final static String RELOAD_ALL_SQL = String.format(BASE_SQL, "and lower(name) like '%.xml'");
    private final static String UPDATE_CACHE_SQL = String.format(BASE_SQL, "and (create_at>? or update_at>?)");

    private final JdbcTemplate jdbcTemplate;
    /**
     * FileResource 命名空间
     */
    private final String namespace;
    /**
     * MyBatisMapper FileResource缓存 {@code Map<文件全路径, MapperFileResource>}
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

    @Override
    public void reloadAll() {
        synchronized (lock) {
            List<MapperFileResource> list = jdbcTemplate.query(RELOAD_ALL_SQL, DataClassRowMapper.newInstance(MapperFileResource.class), namespace);
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
        MapperFileResource resource = mapperCache.get(absolutePath);
        final boolean preExists = mapperFiles.containsKey(absolutePath);
        final boolean nowExists = resource != null;
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
            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(resource.getContent().getBytes(StandardCharsets.UTF_8))) {
                long startTime = System.currentTimeMillis();
                loadSqlSource(absolutePath, inputStream);
                long endTime = System.currentTimeMillis();
                log.info("# 重新解析文件成功 | 耗时: {}ms | path={}", (endTime - startTime), absolutePath);
            } catch (Exception e) {
                log.error("解析Mapper.xml文件失败 | path={}", absolutePath);
            }
        } else {
            log.info("# 清除文件SQL | path={}", absolutePath);
        }
    }

    /**
     * 全量加载缓存
     */
    public void reload() {
        reloadAll();
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
            List<MapperFileResource> list = jdbcTemplate.query(
                    UPDATE_CACHE_SQL, DataClassRowMapper.newInstance(MapperFileResource.class),
                    namespace, lastModifiedTime, lastModifiedTime
            );
            for (MapperFileResource resource : list) {
                // 更新lastModifiedTime
                if (resource.getLastModifiedTime() != null && resource.getLastModifiedTime().compareTo(lastModifiedTime) > 0) {
                    lastModifiedTime = resource.getLastModifiedTime();
                }
                // 变化的需要更新(先移除,再加入)
                List<MapperFileResource> oldList = mapperCache.values().stream()
                        .filter(tmp -> Objects.equals(tmp.getFileResourceId(), resource.getFileResourceId()))
                        .collect(Collectors.toList());
                boolean requestMappingChange = false;
                for (MapperFileResource tmp : oldList) {
                    if (!Objects.equals(tmp.getPath(), resource.getPath()) || !Objects.equals(tmp.getName(), resource.getName())) {
                        requestMappingChange = true;
                        break;
                    }
                }
                if (requestMappingChange) {
                    oldList.forEach(tmp -> {
                        final String fullPath = tmp.getPath() + tmp.getName();
                        mapperCache.remove(fullPath);
                    });
                }
                final String fullPath = resource.getPath() + resource.getName();
                reloadFile(fullPath);
                mapperCache.put(fullPath, resource);
            }
        }
    }
}
