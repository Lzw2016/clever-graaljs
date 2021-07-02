import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.clever.graaljs.fast.api.dto.request.AddFileReq;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
}
