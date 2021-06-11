package org.clever.graaljs.core.utils.imgvalidate;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.core.utils.ExceptionUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Random;

/**
 * 图片验证码生成工具<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-3 23:59 <br/>
 */
@SuppressWarnings("Duplicates")
@Slf4j
public class ImageValidateUtils {
    /**
     * 验证码字体类型
     */
    private static final String[] FONT_TYPES = {"\u5b8b\u4f53", "\u65b0\u5b8b\u4f53", "\u9ed1\u4f53", "\u6977\u4f53", "\u96b6\u4e66"};

    /**
     * 验证码图片默认宽度
     */
    private static final int WIDTH = 70;
    /**
     * 验证码图片默认高度
     */
    private static final int HEIGHT = 26;

    /**
     * 图片验证码类型
     */
    private static final String TYPE = "JPEG";

    /**
     * 验证码默认字符数长度
     */
    private static final int LENGTH = 4;

    /**
     * 在Graphics对象上画出验证码字符串
     *
     * @param code 验证码字符串
     * @param g    Graphics对象
     */
    private static void createCharacter(String code, Graphics g) {
        Random random = new Random();
        for (int i = 0; i < code.length(); i++) {
            String r = code.charAt(i) + "";
            g.setColor(new Color(50 + random.nextInt(100), 50 + random.nextInt(100), 50 + random.nextInt(100)));
            g.setFont(new Font(FONT_TYPES[random.nextInt(FONT_TYPES.length)], Font.BOLD, 26));
            g.drawString(r, 15 * i + 5, 19 + random.nextInt(8));
            // g.drawString(r, i*w/LENGTH, h-5);
        }
    }

    /**
     * 获取随机的颜色
     *
     * @param fc 随机参数
     * @param bc 随机参数
     * @return 随机的颜色
     */
    private static Color getRandColor(int fc, int bc) {
        int f = fc;
        int b = bc;
        Random random = new Random();
        if (f > 255) {
            f = 255;
        }
        if (b > 255) {
            b = 255;
        }
        return new Color(f + random.nextInt(b - f), f + random.nextInt(b - f), f + random.nextInt(b - f));
    }

    /**
     * 创建验证码背景图案
     *
     * @param g Graphics对象
     * @param w 图片宽度
     * @param h 图片高度
     */
    @SuppressWarnings("SameParameterValue")
    private static void createBackground(Graphics g, int w, int h) {
        // 填充背景
        g.setColor(getRandColor(220, 250));
        g.fillRect(0, 0, w, h);
        // 加入干扰线条
        for (int i = 0; i < 10; i++) {
            g.setColor(getRandColor(40, 150));
            Random random = new Random();
            int x = random.nextInt(w);
            int y = random.nextInt(h);
            int x1 = random.nextInt(w);
            int y1 = random.nextInt(h);
            g.drawLine(x, y, x1, y1);
        }
    }

    /**
     * 创建验证码图片，并把图片数据写到输出流中
     * 验证码图片高度：26px，宽度根据验证码长度计算<br/>
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
            int width = WIDTH / LENGTH * code.length();
            // 得到参数高，宽，都为数字时，则使用设置高宽，否则使用默认值
            BufferedImage image = new BufferedImage(width, HEIGHT, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics();
            // 生成背景
            createBackground(g, width, HEIGHT);
            // 生成验证码字符串
            createCharacter(code, g);
            g.dispose();
            ImageIO.write(image, TYPE, outputStream);
            return true;
        } catch (Throwable e) {
            log.error("生成图片验证码失败", e);
            return false;
        }
    }

    /**
     * 创建验证码图片，并把图片数据写到输出流中<br/>
     * 验证码图片高度：26px，宽度根据验证码长度计算<br/>
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
     * 验证码图片高度：26px，宽度根据验证码长度计算<br/>
     *
     * @param code 要生成的验证码字符串
     * @return 成功返回图片数据
     */
    public static byte[] createImage(String code) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] data;
        try {
            int width = WIDTH / LENGTH * code.length();
            // 得到参数高，宽，都为数字时，则使用设置高宽，否则使用默认值
            BufferedImage image = new BufferedImage(width, HEIGHT, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics();
            // 生成背景
            createBackground(g, width, HEIGHT);
            // 生成验证码字符串
            createCharacter(code, g);
            g.dispose();
            ImageIO.write(image, TYPE, out);
            data = out.toByteArray();
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
     * 验证码图片高度：26px，宽度根据验证码长度计算<br/>
     * 图片验证码随机生成，长度是4位<br/>
     *
     * @return 成功返回图片数据
     */
    public static byte[] createImage() {
        String code = ValidateCodeSourceUtils.getRandString(LENGTH);
        return createImage(code);
    }
}
