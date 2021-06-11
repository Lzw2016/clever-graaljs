package org.clever.graaljs.core.builtin.adapter;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/28 22:34 <br/>
 */
public class RMBUtils {
    public static final RMBUtils Instance = new RMBUtils();

    private RMBUtils() {
    }

    /**
     * 数字金额大写转换，思想先写个完整的然后将如零拾替换成零 要用到正则表达式
     */
    public String digitUppercase(double n) {
        return org.clever.graaljs.core.utils.RMBUtils.digitUppercase(n);
    }
}
