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
}
