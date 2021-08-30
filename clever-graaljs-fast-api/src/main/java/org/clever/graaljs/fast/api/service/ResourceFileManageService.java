package org.clever.graaljs.fast.api.service;

import org.clever.graaljs.core.utils.tree.BuildTreeUtils;
import org.clever.graaljs.core.utils.tree.SimpleTreeNode;
import org.clever.graaljs.fast.api.config.FastApiConfig;
import org.clever.graaljs.fast.api.dto.response.FileResourceTreeNodeRes;
import org.clever.graaljs.fast.api.entity.EnumConstant;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/08/30 09:41 <br/>
 */
@Service
public class ResourceFileManageService {
    private static final String QUERY_ALL_DIR = "" +
            "select id, namespace, module, path, name, is_file,`read_only` " +
            "from file_resource " +
            "where is_file=0 and namespace=? and module=? " +
            "order by name";
    private static final String QUERY_ALL_RESOURCE_FILE = "" +
            "select id, namespace, module, path, name, is_file,`read_only` " +
            "from file_resource " +
            "where is_file=1 and namespace=? and module=? " +
            "order by name";

    /**
     * FileResource 命名空间
     */
    private final String namespace;
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ResourceFileManageService(FastApiConfig fastApiConfig) {
        this.namespace = fastApiConfig.getNamespace();
    }

    @Transactional(readOnly = true)
    public List<SimpleTreeNode<FileResourceTreeNodeRes>> getFileTree(Integer module) {
        // 查询所有文件夹
        List<FileResourceTreeNodeRes> allDir = jdbcTemplate.query(
                QUERY_ALL_DIR,
                DataClassRowMapper.newInstance(FileResourceTreeNodeRes.class),
                namespace,
                module
        );
        // 查询所有接口文件
        List<FileResourceTreeNodeRes> list = jdbcTemplate.query(
                QUERY_ALL_RESOURCE_FILE,
                DataClassRowMapper.newInstance(FileResourceTreeNodeRes.class),
                namespace,
                module
        );
        // noinspection DuplicatedCode
        list.addAll(allDir);
        List<SimpleTreeNode<FileResourceTreeNodeRes>> tree = new ArrayList<>(list.size());
        for (FileResourceTreeNodeRes fileResource : list) {
            allDir.stream()
                    .filter(dir -> {
                        if (!Objects.equals(EnumConstant.IS_FILE_0, dir.getIsFile())) {
                            return false;
                        }
                        String path = dir.getPath() + dir.getName() + "/";
                        return Objects.equals(fileResource.getPath(), path);
                    })
                    .findFirst()
                    .ifPresent(dir -> fileResource.setParentFileResourceId(dir.getId()));
            SimpleTreeNode<FileResourceTreeNodeRes> node = new SimpleTreeNode<>(
                    fileResource.getParentFileResourceId(),
                    fileResource.getId(),
                    fileResource
            );
            tree.add(node);
        }
        return BuildTreeUtils.buildTree(tree);
    }
}
