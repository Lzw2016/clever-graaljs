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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/28 16:55 <br/>
 */
@Service
public class ExtendFileManageService {
    // xxx.part01.d.ts
    private static final Pattern PART_FILE_PATTERN = Pattern.compile(".+\\.part\\d+\\..+");
    private static final Pattern PART_FILE_SPLIT_PATTERN = Pattern.compile("\\.part\\d+\\.");

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
        List<FileResource> list = jdbcTemplate.query(
                QUERY_ALL_EXTEND_FILE,
                DataClassRowMapper.newInstance(FileResource.class),
                namespace
        );
        MultiValueMap<String, FileResource> partFileMap = new LinkedMultiValueMap<>();
        List<FileResource> res = new ArrayList<>(list.size());
        for (FileResource resource : list) {
            final String name = resource.getName();
            if (PART_FILE_PATTERN.matcher(name).matches()) {
                String[] arr = PART_FILE_SPLIT_PATTERN.split(name);
                if (arr.length == 2) {
                    String partFileName = arr[0] + "." + arr[1];
                    partFileMap.add(partFileName, resource);
                    continue;
                }
            }
            res.add(resource);
        }
        partFileMap.forEach((partFileName, resources) -> {
            resources.sort(Comparator.comparing(FileResource::getName));
            FileResource partResource = new FileResource();
            partResource.setName(partFileName);
            StringBuilder content = new StringBuilder();
            for (FileResource resource : resources) {
                if (partResource.getNamespace() == null) {
                    partResource.setId(resource.getId());
                    partResource.setNamespace(resource.getNamespace());
                    partResource.setModule(resource.getModule());
                    partResource.setPath(resource.getPath());
                    partResource.setContent(resource.getContent());
                    partResource.setIsFile(resource.getIsFile());
                    partResource.setReadOnly(resource.getReadOnly());
                    partResource.setDescription("系统合并的文件");
                }
                if (resource.getContent() != null) {
                    content.append(resource.getContent()).append("\n");
                }
            }
            partResource.setContent(content.toString());
            res.add(partResource);
        });
        return res;
    }
}
