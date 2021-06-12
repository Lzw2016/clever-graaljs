package org.clever.graaljs.data.jdbc.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.util.Assert;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/09/02 15:56 <br/>
 */
@Slf4j
public class FileSystemMyBatisMapperSql extends AbstractMyBatisMapperSql {
    /**
     * 文件根路径
     */
    protected final File rootPath;

    public FileSystemMyBatisMapperSql(String absolutePath) {
        this.rootPath = new File(absolutePath);
        Assert.isTrue(rootPath.exists() && rootPath.isDirectory(), "路径：" + rootPath.getAbsolutePath() + "不存在或者不是一个文件夹");
        initLoad();
    }

    @Override
    public void reloadAll() {
        mapperFiles.clear();
        sqlSourceMap.clear();
        Collection<File> files = FileUtils.listFiles(rootPath, new String[]{"xml"}, true);
        for (File file : files) {
            final String absolutePath = file.getAbsolutePath();
            log.info("# 解析文件: {}", absolutePath);
            try {
                loadSqlSource(file);
            } catch (Exception e) {
                log.error("解析Mapper.xml文件失败 | path={}", absolutePath);
            }
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void reloadFile(String absolutePath) {
        if (absolutePath == null || !absolutePath.endsWith(".xml")) {
            return;
        }
        final boolean preExists = mapperFiles.containsKey(absolutePath);
        final File file = new File(absolutePath);
        final boolean nowExists = file.exists() && file.isFile();
        // 删除之前的 SqlId
        if (preExists) {
            List<String> sqlIds = mapperFiles.get(absolutePath);
            if (sqlIds != null) {
                sqlIds.forEach(sqlSourceMap::remove);
            }
            mapperFiles.remove(absolutePath);
        }
        // 解析新的文件
        if (nowExists) {
            long startTime = System.currentTimeMillis();
            loadSqlSource(file);
            long endTime = System.currentTimeMillis();
            log.info("# 重新解析文件成功 | 耗时: {}ms | path={}", (endTime - startTime), absolutePath);
        } else {
            log.info("# 清除文件SQL | path={}", absolutePath);
        }
    }

    protected void loadSqlSource(File file) {
        final String absolutePath = file.getAbsolutePath();
        try (InputStream inputStream = FileUtils.openInputStream(file)) {
            loadSqlSource(absolutePath, inputStream);
        } catch (Exception exception) {
            log.error("解析Mapper.xml文件失败 | path={}", absolutePath);
        }
    }
}
