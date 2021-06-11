package org.clever.graaljs.core.utils;

public class RMBUtils {

    /**
     * 数字金额大写转换，思想先写个完整的然后将如零拾替换成零 要用到正则表达式
     */
    public static String digitUppercase(double n) {
        String[] fraction = {"角", "分"};
        String[] digit = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        String[][] unit = {{"元", "万", "亿"}, {"", "拾", "佰", "仟"}};
        String head = n < 0 ? "负" : "";
        n = Math.abs(n);
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < fraction.length; i++) {
            s.append((digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", ""));
        }
        if (s.length() < 1) {
            s = new StringBuilder("整");
        }
        int integerPart = (int) Math.floor(n);
        for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
            StringBuilder p = new StringBuilder();
            for (int j = 0; j < unit[1].length && n > 0; j++) {
                p.insert(0, digit[integerPart % 10] + unit[1][j]);
                integerPart = integerPart / 10;
            }
            s.insert(0, p.toString().replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i]);
        }

        return head + s.toString().replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
    }
}
