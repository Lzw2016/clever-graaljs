package org.clever.graaljs.core.utils.imgvalidate;

import com.github.cage.Cage;
import com.github.cage.IGenerator;
import com.github.cage.ObjectRoulette;
import com.github.cage.image.Painter;
import lombok.extern.slf4j.Slf4j;
import org.clever.graaljs.core.utils.ExceptionUtils;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

/**
 * 使用 Cage 生成图片验证码<br/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-6 0:37 <br/>
 */
@SuppressWarnings("Duplicates")
@Slf4j
public class ImageValidateCageUtils {
    /**
     * 默认字体大小
     */
    private static final int DEFAULT_FONT_SIZE = 30;

    /**
     * 验证码默认字符数长度
     */
    private static final int DEFAULT_WORD_LENGTH = 4;

    /**
     * 验证码默认宽度
     */
    private static final int DEFAULT_WIDTH = 100;

    /**
     * 验证码默认高度
     */
    private static final int DEFAULT_HEIGHT = 35;

    /**
     * 图片验证码类型
     */
    private static final String TYPE = "jpeg";

    /**
     * 生成图片的对象
     */
    private static final Cage CAGE;

    static {
        Painter painter = new Painter(DEFAULT_WIDTH, DEFAULT_HEIGHT, null, null, null, null);
        Font font_1 = new Font(Font.SANS_SERIF, Font.PLAIN, DEFAULT_FONT_SIZE);
        Font font_2 = new Font(Font.SERIF, Font.PLAIN, DEFAULT_FONT_SIZE);
        IGenerator<Font> iGenerator = new ObjectRoulette<>(null, font_1, font_2);
        CAGE = new Cage(painter, iGenerator, null, TYPE, null, null, null);
    }

    /**
     * 创建验证码图片，并把图片数据写到输出流中
     *
     * @param code         要生成的验证码字符串
     * @param outputStream 输出流
     * @return 成功返回true，失败返回false
     */
    public static boolean createImageStream(String code, OutputStream outputStream) {
        try {
            CAGE.draw(code, outputStream);
        } catch (Throwable e) {
            log.error("Cage 生成图片验证码失败", e);
            return false;
        }
        return true;
    }

    /**
     * 创建验证码图片，并把图片数据写到输出流中<br/>
     * 图片验证码随机生成，长度是4位<br/>
     *
     * @param outputStream 输出流
     * @return 成功返回图片验证码，失败返回null
     */
    public static String createImageStream(OutputStream outputStream) {
        String code = ValidateCodeSourceUtils.getRandString(DEFAULT_WORD_LENGTH);
        if (createImageStream(code, outputStream)) {
            return code;
        } else {
            return null;
        }
    }

    /**
     * 创建验证码图片，并返回图片数据<br/>
     *
     * @param code 要生成的验证码字符串
     * @return 成功返回图片数据
     */
    public static byte[] createImage(String code) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] data = null;
        try {
            if (createImageStream(code, out)) {
                data = out.toByteArray();
            }
        } catch (Throwable e) {
            throw ExceptionUtils.unchecked(e);
        } finally {
            try {
                out.close();
            } catch (Throwable e) {
                log.error("ByteArrayOutputStream 关闭失败", e);
            }
        }
        return data;
    }

    /**
     * 创建验证码图片，并返回图片数据<br/>
     * 图片验证码随机生成，长度是4位<br/>
     *
     * @return 成功返回图片数据
     */
    public static byte[] createImage() {
        String code = ValidateCodeSourceUtils.getRandString(DEFAULT_WORD_LENGTH);
        return createImage(code);
    }
}
