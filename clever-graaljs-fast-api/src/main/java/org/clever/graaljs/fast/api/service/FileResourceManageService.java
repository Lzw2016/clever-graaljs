package org.clever.graaljs.fast.api.service;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.core.exception.BusinessException;
import org.clever.graaljs.core.utils.ScriptCodeUtils;
import org.clever.graaljs.fast.api.config.FastApiConfig;
import org.clever.graaljs.fast.api.dto.request.AddDirReq;
import org.clever.graaljs.fast.api.dto.request.AddFileReq;
import org.clever.graaljs.fast.api.dto.request.FileRenameReq;
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
import java.util.stream.Collectors;

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
            "  (namespace, module, file_resource_id, path, name, content) " +
            "values  " +
            "  (:namespace, :module, :fileResourceId, :path, :name, :content)";
    private static final String FILE_EXISTS = "select count(1) from file_resource where module=? and namespace=? and path=? and name=? limit 1";
    private static final String FILE_EXISTS_2 = "select * from file_resource where module=? and namespace=? and path=? and name=? limit 1";
    private static final String FILE_EXISTS_3 = "select count(1) from file_resource where module=? and namespace=? and path=? and name=? limit 1";
    private static final String INSERT_FILE_RESOURCE = "" +
            "insert into file_resource " +
            "(namespace, module, path, name, content, is_file, `read_only`, description) " +
            "VALUES " +
            "(:namespace, :module, :path, :name, :content, :isFile, :readOnly, :description)";
    private static final String DEL_FILES = "delete from file_resource where module=? and namespace=? and id in (%s)";
    private static final String QUERY_FILES = "select * from file_resource where module=? and namespace=? and path like concat(?,'%')";
    private static final String SET_FILE_NAME = "update file_resource set path=?, name=? where module=? and namespace=? and id=?";

    /**
     * FileResource 命名空间
     */
    private final String namespace;
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Resource
    private FileResourceMyBatisMapperSqlService fileResourceMyBatisMapperSqlService;

    public FileResourceManageService(FastApiConfig fastApiConfig) {
        this.namespace = fastApiConfig.getNamespace();
    }

    private void addHistory(FileResource file) {
        FileResourceHistory history = new FileResourceHistory();
        history.setNamespace(file.getNamespace());
        history.setModule(file.getModule());
        history.setFileResourceId(file.getId());
        history.setPath(file.getPath());
        history.setName(file.getName());
        history.setContent(file.getContent());
        namedParameterJdbcTemplate.update(INSERT_HISTORY, new BeanPropertySqlParameterSource(history));
    }

    public FileResource getFileResource(Long id) {
        List<FileResource> list = jdbcTemplate.query(GET_FILE_RESOURCE, DataClassRowMapper.newInstance(FileResource.class), namespace, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
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
            return new ArrayList<>();
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
            List<FileResource> exists = jdbcTemplate.query(
                    FILE_EXISTS_2, DataClassRowMapper.newInstance(FileResource.class),
                    req.getModule(), namespace, path, name
            );
            if (exists.isEmpty()) {
                FileResource dir = new FileResource();
                dir.setNamespace(namespace);
                dir.setModule(req.getModule());
                dir.setPath(path);
                dir.setName(name);
                dir.setIsFile(EnumConstant.IS_FILE_0);
                dir.setReadOnly(EnumConstant.READ_ONLY_0);
                dir.setDescription("");
                GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
                namedParameterJdbcTemplate.update(INSERT_FILE_RESOURCE, new BeanPropertySqlParameterSource(dir), keyHolder);
                dir.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
                list.add(dir);
            } else {
                list.addAll(exists);
            }
            sb.append("/").append(name);
        }
        return list;
    }

    @Transactional
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

    @SuppressWarnings("DuplicatedCode")
    @Transactional
    public List<FileResource> delFileResource(Long id) {
        List<FileResource> list = jdbcTemplate.query(GET_FILE_RESOURCE, DataClassRowMapper.newInstance(FileResource.class), namespace, id);
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        FileResource root = list.get(0);
        // 删除文件
        if (Objects.equals(root.getIsFile(), EnumConstant.IS_FILE_1)) {
            jdbcTemplate.update(String.format(DEL_FILES, "?"), root.getModule(), namespace, root.getId());
            return list;
        }
        // 删除文件夹
        final String path = root.getPath() + root.getName() + "/";
        List<FileResource> children = jdbcTemplate.query(
                QUERY_FILES, DataClassRowMapper.newInstance(FileResource.class),
                root.getModule(), namespace, path
        );
        list.addAll(children);
        StringBuilder ids = new StringBuilder();
        list.forEach(item -> ids.append("?,"));
        if (ids.toString().endsWith(",")) {
            ids.deleteCharAt(ids.length() - 1);
        }
        List<Object> params = new ArrayList<>();
        params.add(root.getModule());
        params.add(namespace);
        params.addAll(list.stream().map(FileResource::getId).collect(Collectors.toList()));
        jdbcTemplate.update(String.format(DEL_FILES, ids), params.toArray());
        // 删除缓存
        list.forEach(file -> {
            if (Objects.equals(file.getIsFile(), EnumConstant.IS_FILE_1)) {
                fileResourceMyBatisMapperSqlService.delCache(file.getPath() + file.getName());
            }
        });
        return list;
    }

    @Transactional
    public List<FileResource> rename(FileRenameReq req) {
        List<FileResource> list = jdbcTemplate.query(GET_FILE_RESOURCE, DataClassRowMapper.newInstance(FileResource.class), namespace, req.getId());
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        FileResource root = list.get(0);
        // 重命名文件
        if (Objects.equals(root.getIsFile(), EnumConstant.IS_FILE_1)) {
            root.setName(req.getNewName());
            Integer count = jdbcTemplate.queryForObject(FILE_EXISTS_3, Integer.class, root.getModule(), root.getNamespace(), root.getPath(), root.getName());
            if (count != null && count > 0) {
                throw new BusinessException("文件已存在");
            }
            jdbcTemplate.update(SET_FILE_NAME, root.getPath(), root.getName(), root.getModule(), root.getNamespace(), root.getId());
            addHistory(root);
            return list;
        }
        // 重命名文件夹
        final String path = root.getPath() + root.getName() + "/";
        List<FileResource> children = jdbcTemplate.query(
                QUERY_FILES, DataClassRowMapper.newInstance(FileResource.class),
                root.getModule(), namespace, path
        );
        root.setName(req.getNewName());
        final String rootPath = root.getPath();
        for (FileResource child : children) {
            String lastPath = child.getPath().substring(rootPath.length());
            String suffix = lastPath.substring(lastPath.indexOf("/"));
            child.setPath(rootPath + req.getNewName() + suffix);
        }
        list.addAll(children);
        for (FileResource file : list) {
            Integer count = jdbcTemplate.queryForObject(FILE_EXISTS_3, Integer.class, file.getModule(), file.getNamespace(), file.getPath(), file.getName());
            if (count != null && count > 0) {
                throw new BusinessException("文件已存在");
            }
        }
        for (FileResource file : list) {
            jdbcTemplate.update(SET_FILE_NAME, file.getPath(), file.getName(), file.getModule(), file.getNamespace(), file.getId());
            if (Objects.equals(file.getIsFile(), EnumConstant.IS_FILE_1)) {
                addHistory(file);
            }
        }
        return list;
    }

    @Transactional
    public List<FileResource> addFile(AddFileReq req) {
        if (!req.getPath().endsWith("/")) {
            req.setPath(req.getPath() + "/");
        }
        String ext = FilenameUtils.getExtension(req.getName());
        if (StringUtils.isBlank(ext)) {
            req.setName(req.getName() + ".d.ts");
        } else {
            String name = FilenameUtils.removeExtension(req.getName());
            req.setName(name + "." + ext.toLowerCase());
        }
        List<FileResource> fileList = new ArrayList<>();
        // 新增文件
        FileResource file = new FileResource();
        file.setNamespace(namespace);
        file.setModule(req.getModule());
        file.setPath(req.getPath());
        file.setName(req.getName());
        file.setContent(req.getContent());
        file.setIsFile(EnumConstant.IS_FILE_1);
        file.setReadOnly(EnumConstant.READ_ONLY_0);
        file.setDescription("");
        addFileResource(file);
        // 新增文件夹
        if (!Objects.equals(req.getPath(), "/")) {
            AddDirReq addDirReq = new AddDirReq();
            addDirReq.setModule(req.getModule());
            addDirReq.setFullPath(req.getPath());
            fileList.addAll(addDir(addDirReq));
        }
        // 返回数据
        fileList.add(file);
        return fileList;
    }
}
