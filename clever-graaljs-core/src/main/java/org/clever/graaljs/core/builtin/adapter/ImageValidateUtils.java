package org.clever.graaljs.core.builtin.adapter;

import org.clever.graaljs.core.utils.imgvalidate.ImageValidateCageUtils;
import org.clever.graaljs.core.utils.imgvalidate.ImageValidateKaptchaUtils;
import org.clever.graaljs.core.utils.imgvalidate.ImageValidatePatchcaUtils;
import org.clever.graaljs.core.utils.imgvalidate.ValidateCodeSourceUtils;

import java.io.OutputStream;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/28 22:33 <br/>
 */
public class ImageValidateUtils {
    public static final ImageValidateUtils Instance = new ImageValidateUtils();

    private ImageValidateUtils() {
    }

    /**
     * 生成随机的字符串
     *
     * @param length 设置随机字符串的长度
     * @return 随机字符串
     */
    public String getRandString(int length) {
        return ValidateCodeSourceUtils.getRandString(length);
    }

    // 常规图片验证码生成
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 创建验证码图片，并把图片数据写到输出流中
     *
     * @param code         要生成的验证码字符串
     * @param outputStream 输出流
     * @return 成功返回true，失败返回false
     */
    public boolean createImageStream(String code, OutputStream outputStream) {
        return org.clever.graaljs.core.utils.imgvalidate.ImageValidateUtils.createImageStream(code, outputStream);
    }

    /**
     * 创建验证码图片，并把图片数据写到输出流中<br/>
     * 图片验证码随机生成，长度是4位<br/>
     *
     * @param outputStream 输出流
     * @return 成功返回图片验证码，失败返回null
     */
    public String createImageStream(OutputStream outputStream) {
        return org.clever.graaljs.core.utils.imgvalidate.ImageValidateUtils.createImageStream(outputStream);
    }

    /**
     * 创建验证码图片，并返回图片数据<br/>
     *
     * @param code 要生成的验证码字符串
     * @return 成功返回图片数据
     */
    public byte[] createImage(String code) {
        return org.clever.graaljs.core.utils.imgvalidate.ImageValidateUtils.createImage(code);
    }

    /**
     * 创建验证码图片，并返回图片数据<br/>
     * 图片验证码随机生成，长度是4位<br/>
     *
     * @return 成功返回图片数据
     */
    public byte[] createImage() {
        return org.clever.graaljs.core.utils.imgvalidate.ImageValidateUtils.createImage();
    }

    // 使用 Cage 生成图片验证码
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 创建验证码图片，并把图片数据写到输出流中
     *
     * @param code         要生成的验证码字符串
     * @param outputStream 输出流
     * @return 成功返回true，失败返回false
     */
    public boolean createImageStreamUseCage(String code, OutputStream outputStream) {
        return ImageValidateCageUtils.createImageStream(code, outputStream);
    }

    /**
     * 创建验证码图片，并把图片数据写到输出流中<br/>
     * 图片验证码随机生成，长度是4位<br/>
     *
     * @param outputStream 输出流
     * @return 成功返回图片验证码，失败返回null
     */
    public String createImageStreamUseCage(OutputStream outputStream) {
        return ImageValidateCageUtils.createImageStream(outputStream);
    }

    /**
     * 创建验证码图片，并返回图片数据<br/>
     *
     * @param code 要生成的验证码字符串
     * @return 成功返回图片数据
     */
    public byte[] createImageUseCage(String code) {
        return ImageValidateCageUtils.createImage(code);
    }

    /**
     * 创建验证码图片，并返回图片数据<br/>
     * 图片验证码随机生成，长度是4位<br/>
     *
     * @return 成功返回图片数据
     */
    public byte[] createImageUseCage() {
        return ImageValidateCageUtils.createImage();
    }

    // 使用 Kaptcha 生成图片验证码
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 创建验证码图片，并把图片数据写到输出流中
     *
     * @param code         要生成的验证码字符串
     * @param outputStream 输出流
     * @return 成功返回true，失败返回false
     */
    public boolean createImageStreamUseKaptcha(String code, OutputStream outputStream) {
        return ImageValidateKaptchaUtils.createImageStream(code, outputStream);
    }

    /**
     * 创建验证码图片，并把图片数据写到输出流中<br/>
     * 图片验证码随机生成，长度是4位<br/>
     *
     * @param outputStream 输出流
     * @return 成功返回图片验证码，失败返回null
     */
    public String createImageStreamUseKaptcha(OutputStream outputStream) {
        return ImageValidateKaptchaUtils.createImageStream(outputStream);
    }

    /**
     * 创建验证码图片，并返回图片数据<br/>
     *
     * @param code 要生成的验证码字符串
     * @return 成功返回图片数据
     */
    public byte[] createImageUseKaptcha(String code) {
        return ImageValidateKaptchaUtils.createImage(code);
    }

    /**
     * 创建验证码图片，并返回图片数据<br/>
     * 图片验证码随机生成，长度是4位<br/>
     *
     * @return 成功返回图片数据
     */
    public byte[] createImageUseKaptcha() {
        return ImageValidateKaptchaUtils.createImage();
    }

    // 使用 Patchca 生成图片验证码
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 创建验证码图片，并把图片数据写到输出流中<br/>
     * 图片验证码随机生成，长度是4位<br/>
     *
     * @param outputStream 输出流
     * @return 成功返回图片验证码，失败返回null
     */
    public String createImageStreamUsePatchca(OutputStream outputStream) {
        return ImageValidatePatchcaUtils.createImageStream(outputStream);
    }

    /**
     * 创建验证码图片，并返回图片数据<br/>
     * 图片验证码随机生成，长度是4位<br/>
     *
     * @return 成功返回图片数据
     */
    public byte[] createImageUsePatchca() {
        return ImageValidatePatchcaUtils.createImage();
    }
}
