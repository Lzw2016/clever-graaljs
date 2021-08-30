package org.clever.graaljs.fast.api.service;

import org.clever.graaljs.fast.api.config.FastApiConfig;
import org.clever.graaljs.fast.api.entity.FileResource;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/08/30 16:51 <br/>
 */
@Service
public class InitScriptService {
    private static final String QUERY_ALL_RESOURCE_FILE = "" +
            "select * from file_resource " +
            "where is_file=1 and namespace=? and module=? " +
            "order by name";

    /**
     * FileResource 命名空间
     */
    private final String namespace;
    @Resource
    private JdbcTemplate jdbcTemplate;

    public InitScriptService(FastApiConfig fastApiConfig) {
        this.namespace = fastApiConfig.getNamespace();
    }

    @Transactional(readOnly = true)
    public List<FileResource> getAllFileByModule(Integer module) {
        return jdbcTemplate.query(
                QUERY_ALL_RESOURCE_FILE,
                DataClassRowMapper.newInstance(FileResource.class),
                namespace,
                module
        );
    }
}
