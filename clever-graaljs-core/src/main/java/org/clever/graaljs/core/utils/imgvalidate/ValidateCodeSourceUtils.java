package org.clever.graaljs.core.utils.imgvalidate;

import java.util.Random;

/**
 * 验证码数据源获取工具<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-5 19:56 <br/>
 */
public class ValidateCodeSourceUtils {
    /**
     * 验证码可能出现的字符
     */
    private static final char[] CODE_SEQ = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /**
     * 生成随机的字符串
     *
     * @param length 设置随机字符串的长度
     * @return 随机字符串
     */
    public static String getRandString(int length) {
        return getRandString(length, CODE_SEQ);
    }

    /**
     * 生成随机的字符串
     *
     * @param length        设置随机字符串的长度
     * @param charSequences 字符序列
     * @return 随机字符串
     */
    public static String getRandString(final int length, final char[] charSequences) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char c = charSequences[random.nextInt(charSequences.length)];
            sb.append(c);
        }
        return sb.toString();
    }
}
