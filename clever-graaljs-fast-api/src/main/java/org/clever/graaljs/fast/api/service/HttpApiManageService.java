package org.clever.graaljs.fast.api.service;

import org.apache.commons.io.FilenameUtils;
import org.clever.graaljs.core.utils.tree.BuildTreeUtils;
import org.clever.graaljs.core.utils.tree.SimpleTreeNode;
import org.clever.graaljs.fast.api.config.FastApiConfig;
import org.clever.graaljs.fast.api.dto.response.ApiFileResourceRes;
import org.clever.graaljs.fast.api.entity.EnumConstant;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            "where a.namespace=b.namespace and a.namespace=? " +
            "order by b.name";

    @Resource
    private FastApiConfig fastApiConfig;
    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<SimpleTreeNode<ApiFileResourceRes>> getHttpApiTree() {
        List<ApiFileResourceRes> list = jdbcTemplate.query(QUERY_ALL_HTTP_API, DataClassRowMapper.newInstance(ApiFileResourceRes.class), fastApiConfig.getNamespace());
        List<SimpleTreeNode<ApiFileResourceRes>> tree = new ArrayList<>(list.size());
        for (ApiFileResourceRes apiFileResourceRes : list) {
            ApiFileResourceRes parent = list.stream()
                    .filter(api -> Objects.equals(EnumConstant.IS_FILE_0, api.getIsFile()) && Objects.equals(apiFileResourceRes.getPath(), FilenameUtils.concat(api.getPath(), api.getName())))
                    .findFirst().orElse(null);
            apiFileResourceRes.setParentFileResourceId(parent != null ? parent.getFileResourceId() : null);
            SimpleTreeNode<ApiFileResourceRes> node = new SimpleTreeNode<>(apiFileResourceRes.getParentFileResourceId(), apiFileResourceRes.getFileResourceId(), apiFileResourceRes);
            tree.add(node);
        }
        return BuildTreeUtils.buildTree(tree);
    }
}
