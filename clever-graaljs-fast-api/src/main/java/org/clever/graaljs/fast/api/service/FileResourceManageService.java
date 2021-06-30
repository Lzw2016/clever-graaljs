package org.clever.graaljs.fast.api.service;

import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.core.exception.BusinessException;
import org.clever.graaljs.core.utils.ScriptCodeUtils;
import org.clever.graaljs.fast.api.config.FastApiConfig;
import org.clever.graaljs.fast.api.dto.request.AddDirReq;
import org.clever.graaljs.fast.api.dto.request.SaveFileContentReq;
import org.clever.graaljs.fast.api.entity.EnumConstant;
import org.clever.graaljs.fast.api.entity.FileResource;
import org.clever.graaljs.fast.api.entity.FileResourceHistory;
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

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/26 21:28 <br/>
 */
@Service
public class FileResourceManageService {
    private static final String GET_FILE_RESOURCE = "select * from file_resource where namespace=? and id=?";
    private static final String SAVE_FILE_CONTENT = "update file_resource set content=? where namespace=? and id=?";
    private static final String INSERT_HISTORY = "" +
            "insert into file_resource_history " +
            "  (namespace, module, path, name, content) " +
            "values  " +
            "  (:namespace, :module, :path, :name, :content)";
    private static final String FILE_EXISTS = "select count(1) from file_resource where module=? and namespace=? and path=? and name=? limit 1";
    private static final String INSERT_FILE_RESOURCE = "" +
            "insert into file_resource " +
            "(namespace, module, path, name, content, is_file, `read_only`, description) " +
            "VALUES " +
            "(:namespace, :module, :path, :name, :content, :isFile, :readOnly, :description)";

    /**
     * FileResource 命名空间
     */
    private final String namespace;
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public FileResourceManageService(FastApiConfig fastApiConfig) {
        this.namespace = fastApiConfig.getNamespace();
    }

    private void addHistory(FileResource file) {
        FileResourceHistory history = new FileResourceHistory();
        history.setNamespace(file.getNamespace());
        history.setModule(file.getModule());
        history.setPath(file.getPath());
        history.setName(file.getName());
        history.setContent(file.getContent());
        namedParameterJdbcTemplate.update(INSERT_HISTORY, new BeanPropertySqlParameterSource(history));
    }

    public FileResource getFileResource(Long id) {
        return jdbcTemplate.queryForObject(GET_FILE_RESOURCE, DataClassRowMapper.newInstance(FileResource.class), namespace, id);
    }

    @Transactional
    public FileResource saveFileContent(SaveFileContentReq req) {
        FileResource fileResource = getFileResource(req.getId());
        if (fileResource == null) {
            throw new BusinessException("文件不存在");
        }
        if (!Objects.equals(EnumConstant.IS_FILE_1, fileResource.getIsFile())) {
            throw new BusinessException("文件夹不能保存内容");
        }
        if (!Objects.equals(EnumConstant.READ_ONLY_0, fileResource.getReadOnly())) {
            throw new BusinessException("当前文件内容不能更改(只读)");
        }
        int count = jdbcTemplate.update(SAVE_FILE_CONTENT, req.getContent(), namespace, req.getId());
        if (count <= 0) {
            throw new BusinessException("文件不存在");
        }
        // 保存历史记录
        String oldCode = ScriptCodeUtils.compressCode(fileResource.getContent(), false);
        String newCode = ScriptCodeUtils.compressCode(req.getContent(), false);
        if (!Objects.equals(oldCode, newCode)) {
            addHistory(fileResource);
        }
        return getFileResource(req.getId());
    }

    @Transactional
    public List<FileResource> addDir(AddDirReq req) {
        List<FileResource> list = new ArrayList<>();
        String[] paths = req.getFullPath().split("/");
        if (paths.length <= 0) {
            throw new BusinessException("目录全路径格式错误");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < paths.length; i++) {
            if (i == 0) {
                continue;
            }
            String path = sb + "/";
            String name = paths[i];
            if (StringUtils.isBlank(name)) {
                if ((i + 1) == paths.length) {
                    continue;
                } else {
                    throw new BusinessException("目录全路径格式错误");
                }
            }
            Integer count = jdbcTemplate.queryForObject(FILE_EXISTS, Integer.class, req.getModule(), namespace, path, name);
            if (count == null || count <= 0) {
                FileResource dir = new FileResource();
                dir.setNamespace(namespace);
                dir.setModule(req.getModule());
                dir.setPath(path);
                dir.setName(name);
                dir.setIsFile(EnumConstant.IS_FILE_0);
                dir.setReadOnly(EnumConstant.READ_ONLY_0);
                dir.setDescription("");
                namedParameterJdbcTemplate.update(INSERT_FILE_RESOURCE, new BeanPropertySqlParameterSource(dir));
            }
            sb.append("/").append(name);
        }
        return list;
    }

    public void addFileResource(FileResource file) {
        Integer count = jdbcTemplate.queryForObject(
                FILE_EXISTS, Integer.class,
                file.getModule(), file.getNamespace(), file.getPath(), file.getName()
        );
        if (count != null && count > 0) {
            throw new BusinessException("文件已存在");
        }
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT_FILE_RESOURCE, new BeanPropertySqlParameterSource(file), keyHolder);
        file.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        addHistory(file);
    }
}
