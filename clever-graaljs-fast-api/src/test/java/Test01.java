import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

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
}
