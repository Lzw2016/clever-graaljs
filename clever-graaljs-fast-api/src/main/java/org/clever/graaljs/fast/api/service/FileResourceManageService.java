package org.clever.graaljs.fast.api.service;

import org.clever.graaljs.core.exception.BusinessException;
import org.clever.graaljs.fast.api.config.FastApiConfig;
import org.clever.graaljs.fast.api.dto.request.SaveFileContentReq;
import org.clever.graaljs.fast.api.entity.EnumConstant;
import org.clever.graaljs.fast.api.entity.FileResource;
import org.clever.graaljs.fast.api.entity.FileResourceHistory;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
        FileResourceHistory history = new FileResourceHistory();
        history.setNamespace(fileResource.getNamespace());
        history.setModule(fileResource.getModule());
        history.setPath(fileResource.getPath());
        history.setName(fileResource.getName());
        history.setContent(req.getContent());
        namedParameterJdbcTemplate.update(INSERT_HISTORY, new BeanPropertySqlParameterSource(history));
        return getFileResource(req.getId());
    }
}
