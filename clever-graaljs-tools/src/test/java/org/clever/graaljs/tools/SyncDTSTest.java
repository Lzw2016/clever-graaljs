package org.clever.graaljs.tools;

import com.zaxxer.hikari.HikariConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.clever.graaljs.core.builtin.CommonUtils;
import org.clever.graaljs.core.utils.HttpUtils;
import org.clever.graaljs.core.utils.mapper.JacksonMapper;
import org.clever.graaljs.data.jdbc.builtin.adapter.JdbcDataSource;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/28 09:21 <br/>
 */
@Slf4j
public class SyncDTSTest {
    public HikariConfig newHikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mysql://192.168.1.201:12000/fast-api");
        hikariConfig.setUsername("fast-api");
        hikariConfig.setPassword("Aa123456!");
        hikariConfig.setAutoCommit(false);
        hikariConfig.setMinimumIdle(1);
        hikariConfig.setMaximumPoolSize(10);
        return hikariConfig;
    }

    public void db2File(final String basePath) {
        // 先删除 delete from file_resource where module=0 and namespace='default';
        JdbcDataSource jdbc = new JdbcDataSource(newHikariConfig());
        jdbc.queryList(
                "select path, name, content, is_file from file_resource where module=0 and namespace='default'",
                false
        ).forEach(row -> {
            try {
                String path = String.valueOf(row.get("path"));
                String name = String.valueOf(row.get("name"));
                String content = CommonUtils.Instance.toString(row.get("content"), "").replace("\r\n", "\n");
                String is_file = String.valueOf(row.get("is_file"));
                File file = new File(basePath + path + name);
                if (Objects.equals("0", is_file) && !file.exists()) {
                    FileUtils.forceMkdir(file);
                }
                if (Objects.equals("1", is_file)) {
                    FileUtils.writeStringToFile(file, content, StandardCharsets.UTF_8);
                }
            } catch (Exception e) {
                log.error("", e);
            }
        });
        jdbc.close();
    }

    public void file2db(final String basePath) {
        final String baseAbsolutePath = FilenameUtils.normalize(new File(basePath).getAbsolutePath());
        Collection<File> files = FileUtils.listFiles(new File(basePath), new String[]{"d.ts"}, true);
        files.forEach(file -> {
            try {
                final String absolutePath = FilenameUtils.normalize(file.getAbsolutePath());
                final String fullPath = FilenameUtils.separatorsToUnix(absolutePath.substring(baseAbsolutePath.length()));
                log.info("fullPath -> {}", fullPath);
                String url = "http://127.0.0.1:18081/fast_api/file_resource_manage/add_dir";
                Map<String, Object> body = new HashMap<>();
                body.put("module", "0");
                if (file.isFile()) {
                    url = "http://127.0.0.1:18081/fast_api/file_resource_manage/add_file";
                    body.put("path", "/" + FilenameUtils.getPath(fullPath));
                    body.put("name", FilenameUtils.getName(fullPath));
                    body.put("content", FileUtils.readFileToString(file, StandardCharsets.UTF_8));
                } else {
                    body.put("fullPath", fullPath);
                }
                String res = HttpUtils.getInner().postStr(url, JacksonMapper.getInstance().toJson(body));
                log.info("res -> {}", res);
            } catch (Exception e) {
                log.error("", e);
            }
        });
    }

    @Test
    public void t01() {
        final String basePath = "../clever-graaljs-types/src";
//        db2File(basePath);
        file2db(basePath);
    }
}
