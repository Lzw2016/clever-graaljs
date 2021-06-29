package org.clever.graaljs.fast.api.service;

import org.clever.graaljs.core.utils.tree.BuildTreeUtils;
import org.clever.graaljs.core.utils.tree.SimpleTreeNode;
import org.clever.graaljs.fast.api.config.FastApiConfig;
import org.clever.graaljs.fast.api.dto.response.FileResourceTreeNodeRes;
import org.clever.graaljs.fast.api.entity.EnumConstant;
import org.clever.graaljs.fast.api.entity.FileResource;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/28 16:55 <br/>
 */
@Service
public class ExtendFileManageService {
    private static final String QUERY_ALL_RESOURCE = "" +
            "select id, namespace, module, path, name, is_file,`read_only` " +
            "from file_resource " +
            "where module=0 and is_file=1 and namespace=?";
    private static final String QUERY_ALL_DIR = "" +
            "select id, namespace, module, path, name, is_file,`read_only` " +
            "from file_resource " +
            "where module=0 and is_file=0 and namespace=?";
    private static final String QUERY_ALL_EXTEND_FILE = "select * from file_resource where module=0 and is_file=1 and namespace=?";

    /**
     * FileResource 命名空间
     */
    private final String namespace;
    @Resource
    private JdbcTemplate jdbcTemplate;

    public ExtendFileManageService(FastApiConfig fastApiConfig) {
        this.namespace = fastApiConfig.getNamespace();
    }

    public List<SimpleTreeNode<FileResourceTreeNodeRes>> getExtendTree() {
        // 查询所有文件夹
        List<FileResourceTreeNodeRes> allDir = jdbcTemplate.query(
                QUERY_ALL_DIR,
                DataClassRowMapper.newInstance(FileResourceTreeNodeRes.class),
                namespace
        );
        // 查询所有接口文件
        List<FileResourceTreeNodeRes> list = jdbcTemplate.query(
                QUERY_ALL_RESOURCE,
                DataClassRowMapper.newInstance(FileResourceTreeNodeRes.class),
                namespace
        );
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

    public List<FileResource> getExtendFileList() {
        return jdbcTemplate.query(
                QUERY_ALL_EXTEND_FILE,
                DataClassRowMapper.newInstance(FileResource.class),
                namespace
        );
    }
}
