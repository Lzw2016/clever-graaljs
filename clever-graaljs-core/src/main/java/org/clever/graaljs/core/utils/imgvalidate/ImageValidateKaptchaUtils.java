package org.clever.graaljs.core.utils.imgvalidate;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.core.utils.ExceptionUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * 使用 Kaptcha 生成图片验证码<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-5 18:45 <br/>
 */
@SuppressWarnings("Duplicates")
@Slf4j
public class ImageValidateKaptchaUtils {
    /**
     * 图片验证码类型
     */
    private static final String TYPE = "JPEG";

    /**
     * 验证码默认字符数长度
     */
    private static final int LENGTH = 4;

    /**
     * Kaptcha 配置信息
     */
    private static final Properties PROPERTIES;

    /**
     * Kaptcha 配置类
     */
    private static final Config CONFIG;

    /**
     * 生成图片的对象
     */
    private static final DefaultKaptcha DEFAULT_KAPTCHA;

    static {
        PROPERTIES = new Properties();
        // 设置是否有边框
        PROPERTIES.put("kaptcha.border", "yes");
        // 设置边框颜色
        PROPERTIES.put("kaptcha.border.color", "0,0,0");
        // 获取中文
        PROPERTIES.put("kaptcha.textproducer.impl", "com.google.code.kaptcha.text.impl.DefaultTextCreator");
        // 设置字体颜色
        PROPERTIES.put("kaptcha.textproducer.font.color", "0,0,0");
        // 设置字体大小
        PROPERTIES.put("kaptcha.textproducer.font.size", "40");
        // 设置字体个数
        PROPERTIES.put("kaptcha.textproducer.char.length", "" + LENGTH);
        // 设置字体样式
        PROPERTIES.put("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        // 设置验证码宽度
        PROPERTIES.put("kaptcha.image.width", "200");
        // 设置验证码高度
        PROPERTIES.put("kaptcha.image.height", "50");
        // 设置验证吗的Session key
        PROPERTIES.put("kaptcha.session.key", "ImageValidateKaptchaUtils_Code");

        CONFIG = new Config(PROPERTIES);
        DEFAULT_KAPTCHA = new DefaultKaptcha();
        DEFAULT_KAPTCHA.setConfig(CONFIG);
    }

    /**
     * 创建验证码图片，并把图片数据写到输出流中
     *
     * @param code         要生成的验证码字符串
     * @param outputStream 输出流
     * @return 成功返回true，失败返回false
     */
    public static boolean createImageStream(String code, OutputStream outputStream) {
        if (StringUtils.isBlank(code) || null == outputStream) {
            log.error("生成图片验证码失败 -- 参数错误");
            return false;
        }
        try {
            BufferedImage image = DEFAULT_KAPTCHA.createImage(code);
            ImageIO.write(image, TYPE, outputStream);
        } catch (Throwable e) {
            log.error("Kaptcha 生成图片验证码失败", e);
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
        String code = ValidateCodeSourceUtils.getRandString(LENGTH);
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
        String code = ValidateCodeSourceUtils.getRandString(LENGTH);
        return createImage(code);
    }
}
