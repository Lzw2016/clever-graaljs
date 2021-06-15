package org.clever.graaljs.data.jdbc.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.clever.dynamic.sql.BoundSql;
import org.clever.dynamic.sql.DynamicSqlParser;
import org.clever.dynamic.sql.builder.SqlSource;
import org.clever.dynamic.sql.parsing.XNode;
import org.clever.dynamic.sql.parsing.XPathParser;
import org.clever.dynamic.sql.parsing.xml.XMLMapperEntityResolver;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/09/30 15:37 <br/>
 */
@Slf4j
public abstract class AbstractMyBatisMapperSql implements MyBatisMapperSql {
    /**
     * 所有的Mapper文件 {@code MultiValueMap<Mapper文件绝对路径, SqlId>}
     */
    protected final MultiValueMap<String, String> mapperFiles = new LinkedMultiValueMap<>(128);
    /**
     * SqlSource对象 {@code Map<SqlId, SqlSource对象>}
     */
    protected final ConcurrentHashMap<String, SqlSource> sqlSourceMap = new ConcurrentHashMap<>(512);

    /**
     * 获取 SqlSource
     */
    public SqlSource getSqlSource(String sqlId) {
        return sqlSourceMap.get(sqlId);
    }

    /**
     * 获取 BoundSql
     */
    public BoundSql getBoundSql(String sqlId, Object parameter) {
        SqlSource sqlSource = sqlSourceMap.get(sqlId);
        if (sqlSource == null) {
            return null;
        }
        return sqlSource.getBoundSql(parameter);
    }

    /**
     * 加载指定文件
     *
     * @param absolutePath 文件绝对路径
     * @param inputStream  文件输入流
     */
    protected void loadSqlSource(String absolutePath, InputStream inputStream) throws Exception {
        final Properties variables = new Properties();
        final String xml = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        final XPathParser parser = new XPathParser(xml, false, variables, new XMLMapperEntityResolver());
        final XNode mapper = parser.evalNode("/mapper");
        if (mapper == null) {
            return;
        }
        final String namespace = mapper.getStringAttribute("namespace", "");
        if (StringUtils.isBlank(namespace)) {
            log.warn("# Mapper.xml文件未设置namespace属性 | path={}", absolutePath);
        }
        final List<XNode> nodes = mapper.evalNodes("sql|select|insert|update|delete");
        if (nodes == null) {
            return;
        }
        for (XNode node : nodes) {
            final String name = node.getName();
            final String id = node.getStringAttribute("id", "");
            if (StringUtils.isBlank(namespace)) {
                log.warn("# Mapper.xml文件<{}>未设置id属性 | path={}", name, absolutePath);
            }
            if (StringUtils.isBlank(namespace) && StringUtils.isBlank(id)) {
                log.warn("# Mapper.xml文件<{}> SqlId为空忽略该SQL | path={}", name, absolutePath);
                continue;
            }
            final StringBuilder sqlIdSB = new StringBuilder(namespace.length() + id.length());
            if (StringUtils.isNotBlank(namespace)) {
                sqlIdSB.append(StringUtils.trim(namespace)).append(".");
            }
            if (StringUtils.isNotBlank(id)) {
                sqlIdSB.append(StringUtils.trim(id));
            }
            final String sqlId = sqlIdSB.toString();
            SqlSource sqlSource = DynamicSqlParser.parserSql(node);
            if (sqlSourceMap.containsKey(sqlId)) {
                log.warn("# SQL出现冲突(覆盖) | SqlId={} | path={}", sqlId, absolutePath);
            }
            mapperFiles.add(absolutePath, sqlId);
            sqlSourceMap.put(sqlId, sqlSource);
            log.info("# SQL读取成功,SqlId: {}", sqlId);
        }
    }

    /**
     * 加载所有文件
     */
    public abstract void reloadAll();

    /**
     * 重新加载指定文件
     *
     * @param absolutePath 文件绝对路径
     */
    public abstract void reloadFile(String absolutePath);

    /**
     * 初始化加载所有配置
     */
    protected void initLoad() {
        log.info("# ==================================================================================================================================");
        log.info("# === 初始化读取Mapper.xml文件 ===");
        long startTime = System.currentTimeMillis();
        reloadAll();
        long endTime = System.currentTimeMillis();
        log.info("# === 读取Mapper.xml文件完成 | 耗时: {}ms ===", (endTime - startTime));
        log.info("# ==================================================================================================================================");
    }
}
