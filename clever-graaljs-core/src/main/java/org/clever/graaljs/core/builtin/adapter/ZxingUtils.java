package org.clever.graaljs.core.builtin.adapter;

import com.google.zxing.BarcodeFormat;
import org.clever.graaljs.core.utils.zxing.ZxingCreateImageUtils;
import org.clever.graaljs.core.utils.zxing.ZxingReaderImageUtils;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/28 22:34 <br/>
 */
public class ZxingUtils {
    public static final ZxingUtils Instance = new ZxingUtils();

    private ZxingUtils() {
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
    public boolean createImageStream(String contents, BarcodeFormat format, int width, int height, OutputStream outputStream) {
        return ZxingCreateImageUtils.createImageStream(contents, format, width, height, outputStream);
    }

    /**
     * 生成二维码、条形码
     *
     * @param contents     二维码、条形码内容
     * @param format       二维码、条形码的类型
     * @param outputStream 图片数据输出目标流
     * @return 成功返回true，失败返回false
     */
    public boolean createImageStream(String contents, BarcodeFormat format, OutputStream outputStream) {
        return ZxingCreateImageUtils.createImageStream(contents, format, outputStream);
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
    public byte[] createImage(String contents, BarcodeFormat format, int width, int height) {
        return ZxingCreateImageUtils.createImage(contents, format, width, height);
    }

    /**
     * 生成二维码、条形码
     *
     * @param contents 二维码、条形码内容
     * @param format   二维码、条形码的类型
     * @return 成功返回图片数据
     */
    public byte[] createImage(String contents, BarcodeFormat format) {
        return ZxingCreateImageUtils.createImage(contents, format);
    }


    /**
     * 读取条形码、二维码图片里的数据
     *
     * @param bufferedImage 条形码、二维码图片对象
     * @return 返回读取结果
     */
    public String readerImage(BufferedImage bufferedImage) {
        return ZxingReaderImageUtils.readerImage(bufferedImage);
    }

    /**
     * 读取条形码、二维码图片里的数据
     *
     * @param inputStream 条形码、二维码图片输入流
     * @return 返回读取结果
     */
    public String readerImage(InputStream inputStream) {
        return ZxingReaderImageUtils.readerImage(inputStream);
    }

    /**
     * 读取条形码、二维码图片里的数据
     *
     * @param filePath 条形码、二维码图片路径
     * @return 返回读取结果
     */
    public String readerImage(String filePath) {
        return ZxingReaderImageUtils.readerImage(filePath);
    }

    /**
     * 读取条形码、二维码图片里的数据
     *
     * @param imageData 条形码、二维码图片数据
     * @return 返回读取结果
     */
    public String readerImage(byte[] imageData) {
        return ZxingReaderImageUtils.readerImage(imageData);
    }

    /**
     * 读取条形码、二维码图片里的数据
     *
     * @param uriAddress 条形码、二维码图片URI地址
     * @return 返回读取结果
     */
    public String readerImageByUri(String uriAddress) {
        return ZxingReaderImageUtils.readerImage(uriAddress);
    }
}
