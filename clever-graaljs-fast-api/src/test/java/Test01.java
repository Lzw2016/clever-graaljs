import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.core.utils.mapper.JacksonMapper;
import org.clever.graaljs.data.jdbc.builtin.wrap.support.JdbcConfig;
import org.clever.graaljs.fast.api.dto.request.AddFileReq;
import org.clever.graaljs.fast.api.model.DebugRequestData;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/29 22:38 <br/>
 */
@Slf4j
public class Test01 {

    @Test
    public void t01() {
        String[] paths = "/a/a/sd/f".split("/");
        log.info("paths -> {}", (Object) paths);
    }

    @Test
    public void t02() {
        String path_1 = "/a/b/";
        String path_2 = "/a/b/c/d/e/";
        String str = path_2.substring(path_1.length());
        // prefix
        String suffix = str.substring(str.indexOf("/"));
        log.info("--> {}", (path_1 + "FFF" + suffix));
    }

    @Test
    public void t03() {
        FileUtils.listFiles(
                new File("D:\\SourceCode\\clever\\clever-hinny-js\\meta-data\\model"),
                new String[]{"ts"},
                false
        ).forEach(file -> {
            final String absolutePath = file.getAbsolutePath();
            if (absolutePath.startsWith("D:\\SourceCode\\clever\\clever-hinny-js\\meta-data\\dist\\")
                    || absolutePath.endsWith(".d.ts")
                    || absolutePath.equals("D:\\SourceCode\\clever\\clever-hinny-js\\meta-data\\index.ts")) {
                return;
            }
            AddFileReq addFileReq = new AddFileReq();
            addFileReq.setModule(0);
            addFileReq.setPath("/fast-api/meta-data/model/");
            addFileReq.setName(file.getName().replace(".ts", ".d.ts"));
            try {
                addFileReq.setContent(FileUtils.readFileToString(file, StandardCharsets.UTF_8).replace("export ", ""));
            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info("---> {}", addFileReq);
        });
    }

    @Test
    public void t05() {
        Pattern part_file_pattern = Pattern.compile("\\.part\\d+\\.");
        String[] arr = part_file_pattern.split("StringUtils.part01.d.ts");
        log.info("---> {}", StringUtils.joinWith(".", (Object[]) arr));
    }

    @Test
    public void t06() {
        DebugRequestData data = new DebugRequestData();
        data.getParams().add(new DebugRequestData.RequestItem());
        log.info("--> {}", JacksonMapper.getInstance().toJson(data));
    }

    @Test
    public void t07() {
        JdbcConfig jdbcConfig = new JdbcConfig();
        jdbcConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        jdbcConfig.setJdbcUrl("jdbc:mysql://192.168.1.201:12000/fast-api");
        jdbcConfig.setUsername("xxx");
        jdbcConfig.setPassword("***");
        jdbcConfig.getDataSourceProperties().put("serverTimezone", "GMT+8");
        jdbcConfig.getDataSourceProperties().put("useUnicode", "true");
        jdbcConfig.getDataSourceProperties().put("characterEncoding", "utf-8");
        jdbcConfig.getDataSourceProperties().put("zeroDateTimeBehavior", "convert_to_null");
        jdbcConfig.getDataSourceProperties().put("useSSL", "false");
        jdbcConfig.getDataSourceProperties().put("useCursorFetch", "true");
        jdbcConfig.setAutoCommit(false);
        jdbcConfig.setMaxPoolSize(1);
        jdbcConfig.setMinIdle(1);
        jdbcConfig.setMaxLifetimeMs(1800000L);
        jdbcConfig.setConnectionTestQuery("SELECT 1");
        String json = JacksonMapper.getInstance().toJson(jdbcConfig);
        log.info("--> {}", json);
        jdbcConfig = JacksonMapper.getInstance().fromJson(json, JdbcConfig.class);
        json = JacksonMapper.getInstance().toJson(jdbcConfig);
        log.info("--> {}", json);
    }
}
