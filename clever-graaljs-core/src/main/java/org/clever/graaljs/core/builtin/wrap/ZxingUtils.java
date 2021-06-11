package org.clever.graaljs.core.builtin.wrap;

import com.google.zxing.BarcodeFormat;
import org.clever.graaljs.core.utils.Assert;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/28 22:34 <br/>
 */
public class ZxingUtils {
    public static final ZxingUtils Instance = new ZxingUtils();

    private final org.clever.graaljs.core.builtin.adapter.ZxingUtils delegate;

    private ZxingUtils() {
        delegate = org.clever.graaljs.core.builtin.adapter.ZxingUtils.Instance;
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
    public boolean createImageStream(String contents, String format, int width, int height, OutputStream outputStream) {
        return delegate.createImageStream(contents, getFormat(format), width, height, outputStream);
    }

    /**
     * 生成二维码、条形码
     *
     * @param contents     二维码、条形码内容
     * @param format       二维码、条形码的类型
     * @param outputStream 图片数据输出目标流
     * @return 成功返回true，失败返回false
     */
    public boolean createImageStream(String contents, String format, OutputStream outputStream) {
        return delegate.createImageStream(contents, getFormat(format), outputStream);
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
    public byte[] createImage(String contents, String format, int width, int height) {
        return delegate.createImage(contents, getFormat(format), width, height);
    }

    /**
     * 生成二维码、条形码
     *
     * @param contents 二维码、条形码内容
     * @param format   二维码、条形码的类型
     * @return 成功返回图片数据
     */
    public byte[] createImage(String contents, String format) {
        return delegate.createImage(contents, getFormat(format));
    }


    /**
     * 读取条形码、二维码图片里的数据
     *
     * @param bufferedImage 条形码、二维码图片对象
     * @return 返回读取结果
     */
    public String readerImage(BufferedImage bufferedImage) {
        return delegate.readerImage(bufferedImage);
    }

    /**
     * 读取条形码、二维码图片里的数据
     *
     * @param inputStream 条形码、二维码图片输入流
     * @return 返回读取结果
     */
    public String readerImage(InputStream inputStream) {
        return delegate.readerImage(inputStream);
    }

    /**
     * 读取条形码、二维码图片里的数据
     *
     * @param filePath 条形码、二维码图片路径
     * @return 返回读取结果
     */
    public String readerImage(String filePath) {
        return delegate.readerImage(filePath);
    }

    /**
     * 读取条形码、二维码图片里的数据
     *
     * @param imageData 条形码、二维码图片数据
     * @return 返回读取结果
     */
    public String readerImage(byte[] imageData) {
        return delegate.readerImage(imageData);
    }

    /**
     * 读取条形码、二维码图片里的数据
     *
     * @param uriAddress 条形码、二维码图片URI地址
     * @return 返回读取结果
     */
    public String readerImageByUri(String uriAddress) {
        return delegate.readerImage(uriAddress);
    }


    private BarcodeFormat getFormat(String format) {
        Assert.hasText(format, "参数format不能为空");
        format = format.toUpperCase();
        switch (format) {
            case "AZTEC":
                return BarcodeFormat.AZTEC;
            case "CODABAR":
                return BarcodeFormat.CODABAR;
            case "CODE_39":
                return BarcodeFormat.CODE_39;
            case "CODE_93":
                return BarcodeFormat.CODE_93;
            case "CODE_128":
                return BarcodeFormat.CODE_128;
            case "DATA_MATRIX":
                return BarcodeFormat.DATA_MATRIX;
            case "EAN_8":
                return BarcodeFormat.EAN_8;
            case "EAN_13":
                return BarcodeFormat.EAN_13;
            case "ITF":
                return BarcodeFormat.ITF;
            case "MAXICODE":
                return BarcodeFormat.MAXICODE;
            case "PDF_417":
                return BarcodeFormat.PDF_417;
            case "QR_CODE":
                return BarcodeFormat.QR_CODE;
            case "RSS_14":
                return BarcodeFormat.RSS_14;
            case "RSS_EXPANDED":
                return BarcodeFormat.RSS_EXPANDED;
            case "UPC_A":
                return BarcodeFormat.UPC_A;
            case "UPC_E":
                return BarcodeFormat.UPC_E;
            case "UPC_EAN_EXTENSION":
                return BarcodeFormat.UPC_EAN_EXTENSION;
            default:
                return BarcodeFormat.valueOf(format);
        }
    }
}
