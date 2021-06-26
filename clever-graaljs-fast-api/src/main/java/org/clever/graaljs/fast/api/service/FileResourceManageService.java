package org.clever.graaljs.fast.api.service;

import org.clever.graaljs.fast.api.entity.FileResource;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/26 21:28 <br/>
 */
@Service
public class FileResourceManageService {
    private static final String GET_FILE_RESOURCE = "select * from file_resource where id=?";

    @Resource
    private JdbcTemplate jdbcTemplate;

    public FileResource getFileResource(Long id) {
        return jdbcTemplate.queryForObject(GET_FILE_RESOURCE, DataClassRowMapper.newInstance(FileResource.class), id);
    }
}
