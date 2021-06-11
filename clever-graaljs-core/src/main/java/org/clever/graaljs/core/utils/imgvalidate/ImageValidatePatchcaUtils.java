package org.clever.graaljs.core.utils.imgvalidate;

import com.github.bingoohuang.patchca.background.SingleColorBackgroundFactory;
import com.github.bingoohuang.patchca.color.ColorFactory;
import com.github.bingoohuang.patchca.color.SingleColorFactory;
import com.github.bingoohuang.patchca.custom.*;
import com.github.bingoohuang.patchca.filter.predefined.CurvesRippleFilterFactory;
import com.github.bingoohuang.patchca.font.CustomRandomFontFactory;
import com.github.bingoohuang.patchca.font.RandomFontFactory;
import com.github.bingoohuang.patchca.service.AbstractCaptchaService;
import com.github.bingoohuang.patchca.service.Captcha;
import com.github.bingoohuang.patchca.text.renderer.BestFitTextRenderer;
import com.github.bingoohuang.patchca.utils.encoder.EncoderHelper;
import com.github.bingoohuang.patchca.word.AdaptiveRandomWordFactory;
import com.github.bingoohuang.patchca.word.RandomWordFactory;
import com.github.bingoohuang.patchca.word.WordFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.core.utils.ExceptionUtils;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 使用 Patchca 生成图片验证码<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-5 21:10 <br/>
 */
@Slf4j
public class ImageValidatePatchcaUtils {
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
    private static final String TYPE = "PNG";

    /**
     * 生成验证码的类
     */
    private static final CustomConfigurableCaptchaService CUSTOM_CONFIGURABLE_CAPTCHA_SERVICE;

    static {
        // 验证码内容生成--- 可使用简单的RandomWordFactory类
        List<WordFactory> wordFactories = new ArrayList<>();
        // wordFactories.add(new MathExprFactory()); // 三个单数运算--有BUG
        wordFactories.add(new MathArithmeticFactory()); // 四则运算
        wordFactories.add(new ChineseIdiomFactory()); // 成语
        wordFactories.add(new ChineseIdiomGuessFactory()); // 成语猜字
        wordFactories.add(new EnglishWordFactory()); // 常见2000英语单词
        wordFactories.add(new AdaptiveRandomWordFactory()); // 宽字符只会有一个的随机
        // 随机
        RandomWordFactory randomWordFactory = new RandomWordFactory();
        randomWordFactory.setMinLength(DEFAULT_WORD_LENGTH);
        randomWordFactory.setMaxLength(DEFAULT_WORD_LENGTH);
        wordFactories.add(randomWordFactory);
        wordFactories.add(new SymbolDiffFactory()); // 符号找不同
        wordFactories.add(new KnowledgeWordFactory()); // 地理知识
        wordFactories.add(new AdaptiveRandomWordFactory());
        wordFactories.add(new RandomChineseFactory()); // 随机汉字
        wordFactories.add(new RandomChineseJianpinFactory()); // 随机汉字简拼
        wordFactories.add(new ChineseIdiomJianpingFactory()); // 随机成语简拼
        wordFactories.add(new RandomChineseQuanpinFactory()); // 随机汉字全拼
        RandomFactoryWordFactory wordFactory = new RandomFactoryWordFactory(wordFactories);

        // 字体设置：CustomRandomFontFactory 支持中文；RandomFontFactory 不支持中文
        CustomRandomFontFactory customRandomFontFactory = new CustomRandomFontFactory();
        customRandomFontFactory.setMinSize(DEFAULT_FONT_SIZE);
        customRandomFontFactory.setMaxSize(DEFAULT_FONT_SIZE);
        customRandomFontFactory.setWordFactory(wordFactory);

        // 处理器
        CUSTOM_CONFIGURABLE_CAPTCHA_SERVICE = new CustomConfigurableCaptchaService();
        CUSTOM_CONFIGURABLE_CAPTCHA_SERVICE.setFontFactory(customRandomFontFactory);
        CUSTOM_CONFIGURABLE_CAPTCHA_SERVICE.setWordFactory(wordFactory);

        // 生成图片大小（像素）
        CUSTOM_CONFIGURABLE_CAPTCHA_SERVICE.setWidth(DEFAULT_WIDTH);
        CUSTOM_CONFIGURABLE_CAPTCHA_SERVICE.setHeight(DEFAULT_HEIGHT);
    }

    /**
     * 随机变色处理类<br/>
     *
     * @see com.github.bingoohuang.patchca.custom.ConfigurableCaptchaService
     */
    private static class CustomConfigurableCaptchaService extends AbstractCaptchaService {
        /**
         * 变色的颜色集合，为了性能共用
         */
        private final List<ColorFactory> colorList = new ArrayList<>(16);

        public CustomConfigurableCaptchaService() {
            colorList.add(new SingleColorFactory(new Color(25, 60, 170)));
            colorList.add(new SingleColorFactory(Color.blue));
            colorList.add(new SingleColorFactory(Color.black));
            colorList.add(new SingleColorFactory(Color.red));
            colorList.add(new SingleColorFactory(Color.pink));
            colorList.add(new SingleColorFactory(Color.orange));
            colorList.add(new SingleColorFactory(Color.green));
            colorList.add(new SingleColorFactory(Color.magenta));
            colorList.add(new SingleColorFactory(Color.cyan));

            /* 参照 -- ConfigurableCaptchaService */
            backgroundFactory = new SingleColorBackgroundFactory();
            // 验证码内容设置
            wordFactory = new AdaptiveRandomWordFactory();
            // 字体设置
            fontFactory = new RandomFontFactory();
            textRenderer = new BestFitTextRenderer();
            // 颜色设置
            colorFactory = new SingleColorFactory();
            // 干扰线波纹
            filterFactory = new CurvesRippleFilterFactory(colorFactory);
            textRenderer.setLeftMargin(10);
            textRenderer.setRightMargin(10);
            width = 160;
            height = 70;
        }

        public ColorFactory nextColorFactory() {
            Random random = new Random();
            int index = random.nextInt(colorList.size());
            return colorList.get(index);
        }

        @Override
        public Captcha getCaptcha() {
            ColorFactory colorFactory = nextColorFactory();
            CurvesRippleFilterFactory curvesRippleFilterFactory = (CurvesRippleFilterFactory) this.getFilterFactory();
            curvesRippleFilterFactory.setColorFactory(colorFactory);
            this.setColorFactory(colorFactory);
            return super.getCaptcha();
        }
    }

    /**
     * 创建验证码图片，并把图片数据写到输出流中<br/>
     * 图片验证码随机生成，长度是4位<br/>
     *
     * @param outputStream 输出流
     * @return 成功返回图片验证码
     */
    public static String createImageStream(OutputStream outputStream) {
        String captcha;
        try {
            captcha = EncoderHelper.getChallangeAndWriteImage(CUSTOM_CONFIGURABLE_CAPTCHA_SERVICE, TYPE, outputStream);
        } catch (Throwable e) {
            throw ExceptionUtils.unchecked(e);
        }
        return captcha;
    }

    /**
     * 创建验证码图片，并返回图片数据<br/>
     * 图片验证码随机生成，长度是4位<br/>
     *
     * @return 成功返回图片数据
     */
    public static byte[] createImage() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] data = null;
        try {
            if (StringUtils.isNotBlank(createImageStream(out))) {
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
}
