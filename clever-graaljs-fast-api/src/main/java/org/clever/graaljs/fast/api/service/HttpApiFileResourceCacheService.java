package org.clever.graaljs.fast.api.service;

import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.fast.api.config.FastApiConfig;
import org.clever.graaljs.fast.api.entity.EnumConstant;
import org.clever.graaljs.fast.api.model.HttpApiFileResource;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/14 11:52 <br/>
 */
@Service
public class HttpApiFileResourceCacheService {
    private final static String BASE_SQL = "" +
            "select " +
            "    a.id as httpApiId, " +
            "    b.id as fileResourceId, " +
            "    a.namespace as namespace, " +
            "    a.request_mapping as requestMapping, " +
            "    a.request_method as requestMethod, " +
            "    a.disable_request as disableRequest, " +
            "    b.path as path, " +
            "    b.name as name, " +
            "    b.content as content, " +
            "    if(ifnull(a.update_at, a.create_at) > ifnull(b.update_at, b.create_at), ifnull(a.update_at, a.create_at), ifnull(b.update_at, b.create_at)) as lastModifiedTime " +
            "from http_api a left join file_resource b on (a.file_resource_id = b.id) " +
            "where b.module=3 and b.is_file=1 and lower(b.name) like '%%.js' and a.namespace=? and b.namespace=? " +
            "%s " +
            "order by a.update_at desc, a.id desc, b.update_at desc, b.id desc";
    private final static String RELOAD_SQL = String.format(BASE_SQL, "and a.disable_request=0");
    private final static String UPDATE_CACHE_SQL = String.format(BASE_SQL, "and (a.create_at>? or a.update_at>? or b.create_at>? or b.update_at>?)");

    private final JdbcTemplate jdbcTemplate;
    /**
     * FileResource 命名空间
     */
    private final String namespace;
    /**
     * HttpApiFileResource缓存 {@code Map<requestMapping, HttpApiFileResource>}
     */
    private volatile ConcurrentMap<String, HttpApiFileResource> httpApiCache = new ConcurrentHashMap<>();
    /**
     * 文件资源最后修改时间
     */
    private volatile Date lastModifiedTime;
    /**
     * 操作缓存锁
     */
    private final Object lock = new Object();

    public HttpApiFileResourceCacheService(JdbcTemplate jdbcTemplate, FastApiConfig fastApiConfig) {
        this.jdbcTemplate = jdbcTemplate;
        this.namespace = fastApiConfig.getNamespace();
    }

    /**
     * 全量加载缓存
     */
    public void reload() {
        synchronized (lock) {
            List<HttpApiFileResource> list = jdbcTemplate.query(RELOAD_SQL, DataClassRowMapper.newInstance(HttpApiFileResource.class), namespace, namespace);
            ConcurrentMap<String, HttpApiFileResource> newCache = new ConcurrentHashMap<>(list.size());
            for (HttpApiFileResource resource : list) {
                if (resource.getLastModifiedTime() != null) {
                    if (lastModifiedTime == null || resource.getLastModifiedTime().compareTo(lastModifiedTime) > 0) {
                        lastModifiedTime = resource.getLastModifiedTime();
                    }
                }
                newCache.put(resource.getRequestMapping(), resource);
            }
            httpApiCache = newCache;
        }
    }

    /**
     * 增量更新缓存(被删除的文件无法增量更新)
     */
    public void updateCache() {
        // 全量加载
        if (lastModifiedTime == null) {
            reload();
            return;
        }
        // 增量更新
        synchronized (lock) {
            List<HttpApiFileResource> list = jdbcTemplate.query(
                    UPDATE_CACHE_SQL, DataClassRowMapper.newInstance(HttpApiFileResource.class),
                    namespace, namespace, lastModifiedTime, lastModifiedTime, lastModifiedTime, lastModifiedTime
            );
            Set<Long> removeHttpApiIds = new HashSet<>();
            for (HttpApiFileResource resource : list) {
                // 更新lastModifiedTime
                if (resource.getLastModifiedTime() != null && resource.getLastModifiedTime().compareTo(lastModifiedTime) > 0) {
                    lastModifiedTime = resource.getLastModifiedTime();
                }
                // 禁用的需要移除(标记)
                if (!Objects.equals(resource.getDisableRequest(), EnumConstant.DISABLE_REQUEST_0)) {
                    removeHttpApiIds.add(resource.getHttpApiId());
                    continue;
                }
                // 变化的需要更新(先移除,再加入)
                List<HttpApiFileResource> oldList = httpApiCache.values().stream()
                        .filter(tmp -> Objects.equals(tmp.getHttpApiId(), resource.getHttpApiId()))
                        .collect(Collectors.toList());
                boolean requestMappingChange = false;
                for (HttpApiFileResource tmp : oldList) {
                    if (!Objects.equals(tmp.getRequestMapping(), resource.getRequestMapping())) {
                        requestMappingChange = true;
                        break;
                    }
                }
                if (requestMappingChange) {
                    oldList.forEach(tmp -> httpApiCache.remove(tmp.getRequestMapping()));
                }
                httpApiCache.put(resource.getRequestMapping(), resource);
            }
            // 禁用的需要移除(执行)
            httpApiCache.entrySet().removeIf(entry -> removeHttpApiIds.contains(entry.getValue().getHttpApiId()));
        }
    }

    /**
     * 删除
     */
    public void delCache(String requestMapping) {
        httpApiCache.remove(requestMapping);
    }

    public HttpApiFileResource getScriptFileResource(String requestMapping, String requestMethod) {
        HttpApiFileResource httpApiFileResource = httpApiCache.get(requestMapping);
        if (httpApiFileResource == null) {
            return null;
        }
        if (StringUtils.equalsIgnoreCase(httpApiFileResource.getRequestMethod(), "ALL")) {
            return httpApiFileResource;
        }
        if (StringUtils.equalsIgnoreCase(httpApiFileResource.getRequestMethod(), requestMethod)) {
            return httpApiFileResource;
        }
        return null;
    }

    public List<HttpApiFileResource> getAllHttpApiFileResource() {
        return new ArrayList<>(httpApiCache.values());
    }
}
