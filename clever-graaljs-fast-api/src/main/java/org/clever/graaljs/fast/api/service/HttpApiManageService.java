package org.clever.graaljs.fast.api.service;

import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.core.exception.BusinessException;
import org.clever.graaljs.core.utils.tree.BuildTreeUtils;
import org.clever.graaljs.core.utils.tree.SimpleTreeNode;
import org.clever.graaljs.fast.api.config.FastApiConfig;
import org.clever.graaljs.fast.api.dto.request.AddDirReq;
import org.clever.graaljs.fast.api.dto.request.AddHttpApiReq;
import org.clever.graaljs.fast.api.dto.response.AddHttpApiRes;
import org.clever.graaljs.fast.api.dto.response.ApiFileResourceRes;
import org.clever.graaljs.fast.api.dto.response.DelHttpApiRes;
import org.clever.graaljs.fast.api.entity.EnumConstant;
import org.clever.graaljs.fast.api.entity.FileResource;
import org.clever.graaljs.fast.api.entity.HttpApi;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/23 21:15 <br/>
 */
@Service
public class HttpApiManageService {
    private static final String QUERY_ALL_HTTP_API = "" +
            "select " +
            "    a.id as httpApiId, " +
            "    b.id as fileResourceId, " +
            "    a.namespace as namespace, " +
            "    b.path as path, " +
            "    b.name as name, " +
            "    b.is_file as isFile, " +
            "    b.`read_only` as readOnly, " +
            "    a.request_mapping as requestMapping, " +
            "    a.request_method as requestMethod, " +
            "    a.disable_request as disableRequest " +
            "from http_api a left join file_resource b on (a.file_resource_id = b.id) " +
            "where b.is_file=1 and lower(b.name) like '%%.js' and b.module=3 and a.namespace=b.namespace and a.namespace=? " +
            "order by b.name";
    private static final String QUERY_ALL_DIR = "" +
            "select " +
            "   id as fileResourceId, " +
            "   path as path, " +
            "   name as name, " +
            "   is_file as isFile, " +
            "   `read_only` as readOnly " +
            "from file_resource " +
            "where is_file=0 and module=3 and namespace=? " +
            "order by name";
    private static final String GET_HTTP_API = "select * from http_api where namespace=? and id=?";
    private static final String HTTP_API_EXISTS = "" +
            "select count(1) from http_api " +
            "where namespace=? and (request_method='ALL' or request_method=?) and request_mapping=? limit 1";
    private static final String INSERT_HTTP_API = "" +
            "insert into http_api " +
            "(namespace, file_resource_id, request_mapping, request_method, disable_request) " +
            "values " +
            "(:namespace, :fileResourceId, :requestMapping, :requestMethod, :disableRequest)";
    private static final String DEL_HTTP_API = "delete from http_api where namespace=? and file_resource_id in (%s)";
    private static final String QUERY_HTTP_API = "select * from http_api where namespace=? and file_resource_id in (%s)";

    /**
     * FileResource 命名空间
     */
    private final String namespace;
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Resource
    private FileResourceManageService fileResourceManageService;
    @Resource
    private HttpApiFileResourceCacheService httpApiFileResourceCacheService;

    public HttpApiManageService(FastApiConfig fastApiConfig) {
        this.namespace = fastApiConfig.getNamespace();
    }

    public List<SimpleTreeNode<ApiFileResourceRes>> getHttpApiTree() {
        // 查询所有文件夹
        List<ApiFileResourceRes> allDir = jdbcTemplate.query(
                QUERY_ALL_DIR,
                DataClassRowMapper.newInstance(ApiFileResourceRes.class),
                namespace
        );
        // 查询所有接口文件
        List<ApiFileResourceRes> list = jdbcTemplate.query(
                QUERY_ALL_HTTP_API,
                DataClassRowMapper.newInstance(ApiFileResourceRes.class),
                namespace
        );
        list.addAll(allDir);
        List<SimpleTreeNode<ApiFileResourceRes>> tree = new ArrayList<>(list.size());
        for (ApiFileResourceRes apiFileResourceRes : list) {
            allDir.stream()
                    .filter(dir -> {
                        if (!Objects.equals(EnumConstant.IS_FILE_0, dir.getIsFile())) {
                            return false;
                        }
                        String path = dir.getPath() + dir.getName() + "/";
                        return Objects.equals(apiFileResourceRes.getPath(), path);
                    })
                    .findFirst()
                    .ifPresent(dir -> apiFileResourceRes.setParentFileResourceId(dir.getFileResourceId()));
            SimpleTreeNode<ApiFileResourceRes> node = new SimpleTreeNode<>(
                    apiFileResourceRes.getParentFileResourceId(),
                    apiFileResourceRes.getFileResourceId(),
                    apiFileResourceRes
            );
            tree.add(node);
        }
        return BuildTreeUtils.buildTree(tree);
    }

    public HttpApi getHttpApi(Long id) {
        List<HttpApi> list = jdbcTemplate.query(GET_HTTP_API, DataClassRowMapper.newInstance(HttpApi.class), namespace, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Transactional
    public AddHttpApiRes addHttpApi(AddHttpApiReq req) {
        AddHttpApiRes res = new AddHttpApiRes();
        if (!req.getPath().endsWith("/")) {
            req.setPath(req.getPath() + "/");
        }
        if (!req.getName().toLowerCase().endsWith(".js")) {
            req.setName(req.getName() + ".js");
        }
        final String name = req.getName().substring(0, req.getName().length() - 3);
        req.setName(name + ".js");
        if (StringUtils.isBlank(req.getRequestMapping())) {
            req.setRequestMapping(req.getPath() + name);
        }
        if (StringUtils.isBlank(req.getContent())) {
            req.setContent("//default code \nreturn { ok: true };");
        }
        // 校验接口是否存在
        Integer count = jdbcTemplate.queryForObject(HTTP_API_EXISTS, Integer.class, namespace, req.getRequestMethod(), req.getRequestMapping());
        if (count != null && count > 0) {
            throw new BusinessException("接口路径冲突");
        }
        // 新增文件
        FileResource file = new FileResource();
        file.setNamespace(namespace);
        file.setModule(EnumConstant.MODULE_3);
        file.setPath(req.getPath());
        file.setName(req.getName());
        file.setContent(req.getContent());
        file.setIsFile(EnumConstant.IS_FILE_1);
        file.setReadOnly(EnumConstant.READ_ONLY_0);
        file.setDescription("");
        fileResourceManageService.addFileResource(file);
        // 新增文件夹
        AddDirReq addDirReq = new AddDirReq();
        addDirReq.setModule(EnumConstant.MODULE_3);
        addDirReq.setFullPath(req.getPath());
        res.getFileList().addAll(fileResourceManageService.addDir(addDirReq));
        // 新增HTTP API
        HttpApi httpApi = new HttpApi();
        httpApi.setNamespace(namespace);
        httpApi.setFileResourceId(file.getId());
        httpApi.setRequestMapping(req.getRequestMapping());
        httpApi.setRequestMethod(req.getRequestMethod());
        httpApi.setDisableRequest(EnumConstant.DISABLE_REQUEST_0);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT_HTTP_API, new BeanPropertySqlParameterSource(httpApi), keyHolder);
        httpApi.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        // 返回数据
        res.getFileList().add(file);
        res.setHttpApi(httpApi);
        return res;
    }

    @SuppressWarnings("DuplicatedCode")
    @Transactional
    public DelHttpApiRes delHttpApi(Long fileResourceId) {
        DelHttpApiRes res = new DelHttpApiRes();
        // 删除文件资源
        List<FileResource> fileList = fileResourceManageService.delFileResource(fileResourceId);
        res.setFileList(fileList);
        if (fileList.isEmpty()) {
            return res;
        }
        // 删除HTTP API
        StringBuilder ids = new StringBuilder();
        fileList.forEach(item -> ids.append("?,"));
        if (ids.toString().endsWith(",")) {
            ids.deleteCharAt(ids.length() - 1);
        }
        List<Object> params = new ArrayList<>();
        params.add(namespace);
        params.addAll(fileList.stream().map(FileResource::getId).collect(Collectors.toList()));
        List<HttpApi> httpApiList = jdbcTemplate.query(String.format(QUERY_HTTP_API, ids), DataClassRowMapper.newInstance(HttpApi.class), params.toArray());
        res.setHttpApiList(httpApiList);
        jdbcTemplate.update(String.format(DEL_HTTP_API, ids), params.toArray());
        // 删除缓存
        httpApiList.forEach(httpApi -> httpApiFileResourceCacheService.delCache(httpApi.getRequestMapping()));
        return res;
    }
}
