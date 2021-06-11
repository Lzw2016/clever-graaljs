package org.clever.graaljs.core.utils.zxing;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import lombok.extern.slf4j.Slf4j;
import org.clever.graaljs.core.utils.ExceptionUtils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Zxing 实现的各种二维码条形码生成工具<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-6 16:16 <br/>
 */
@Slf4j
public class ZxingCreateImageUtils {
    /**
     * 验证码默认宽度
     */
    private static final int WIDTH = 350;

    /**
     * 验证码默认高度
     */
    private static final int HEIGHT = 350;

    /**
     * 图片验证码类型
     */
    private static final String TYPE = "jpeg";

    /**
     * 二维码生成配置
     */
    private static final Map<EncodeHintType, Object> HINTS;

    /**
     * 二维码图片写出配置
     */
    private static final MatrixToImageConfig MATRIX_TO_IMAGE_CONFIG;

    /**
     * 二维码生成工具类
     */
    private static final MultiFormatWriter MULTI_FORMAT_WRITER;

    static {
        HINTS = new HashMap<>();
        // 指定纠错等级 -- PDF_417 不支持
        // HINTS.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        // 指定编码格式
        HINTS.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        int black = 0xFF000000; // 设置二维码、条形码颜色
        int white = 0xFFFFFFFF; // 设置二维码、条形码背景颜色
        MATRIX_TO_IMAGE_CONFIG = new MatrixToImageConfig(black, white);

        MULTI_FORMAT_WRITER = new MultiFormatWriter();
    }

    /**
     * 生成二维码、条形码
     *
     * @param contents 二维码、条形码内容
     * @param format   二维码、条形码的类型
     * @param width    图片宽
     * @param height   图片高
     * @return 图片对象BitMatrix
     */
    private static BitMatrix createImageBitMatrix(String contents, BarcodeFormat format, int width, int height) {
        BitMatrix matrix;
        try {
            matrix = MULTI_FORMAT_WRITER.encode(contents, format, width, height, HINTS);
        } catch (Throwable e) {
            throw ExceptionUtils.unchecked(e);
        }
        return matrix;
    }

    /**
     * 生成二维码、条形码
     *
     * @param contents     二维码、条形码内容
     * @param format       二维码、条形码的类型
     * @param width        图片宽
     * @param height       图片高
     * @param outputStream 图片数据输出目标流
     * @return 成功返回true，失败返回false
     */
    public static boolean createImageStream(String contents, BarcodeFormat format, int width, int height, OutputStream outputStream) {
        BitMatrix matrix = createImageBitMatrix(contents, format, width, height);
        if (null != matrix) {
            try {
                MatrixToImageWriter.writeToStream(matrix, TYPE, outputStream, MATRIX_TO_IMAGE_CONFIG);
                return true;
            } catch (Throwable e) {
                throw ExceptionUtils.unchecked(e);
            }
        }
        return false;
    }

    /**
     * 生成二维码、条形码
     *
     * @param contents     二维码、条形码内容
     * @param format       二维码、条形码的类型
     * @param outputStream 图片数据输出目标流
     * @return 成功返回true，失败返回false
     */
    public static boolean createImageStream(String contents, BarcodeFormat format, OutputStream outputStream) {
        return createImageStream(contents, format, WIDTH, HEIGHT, outputStream);
    }

    /**
     * 生成二维码、条形码
     *
     * @param contents 二维码、条形码内容
     * @param format   二维码、条形码的类型
     * @param width    图片宽
     * @param height   图片高
     * @return 成功返回图片数据
     */
    public static byte[] createImage(String contents, BarcodeFormat format, int width, int height) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] data = null;
        try {
            if (createImageStream(contents, format, width, height, out)) {
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
     * 生成二维码、条形码
     *
     * @param contents 二维码、条形码内容
     * @param format   二维码、条形码的类型
     * @return 成功返回图片数据
     */
    public static byte[] createImage(String contents, BarcodeFormat format) {
        return createImage(contents, format, WIDTH, HEIGHT);
    }
}
