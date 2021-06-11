package org.clever.graaljs.core.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义数字(自定义进制)
 * <p>
 * 作者： lzw<br/>
 * 创建时间：2018-10-13 10:39 <br/>
 */
public class CustomNumbers {

    /**
     * 数字字符组成 (以62进制)
     */
    private final static char[] digits = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',   // 10
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',   // 20
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',   // 30
            'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',   // 40
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',   // 50
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',   // 60
            'Y', 'Z',
//            '!', '"', '#', '$', '%', '&', '\'', '(',  // 70
//            ')', '*', '+', ',', '-', '.', '/', '0', ':', ';',   // 80
//            '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_',  // 90
//            '`', '{', '|', '}', '~',
    };

    private final static Map<Character, Integer> digitMap;

    static {
        Map<Character, Integer> tmp = new HashMap<>(digits.length);
        for (int i = 0; i < digits.length; i++) {
            tmp.put(digits[i], i);
        }
        digitMap = Collections.unmodifiableMap(tmp);
    }

    /**
     * 支持的最大进制数
     */
    public static final int MAX_RADIX = digits.length;

    /**
     * 支持的最小进制数
     */
    public static final int MIN_RADIX = 2;

    /**
     * 将长整型数值转换为指定的进制数
     *
     * @param i     输入数据
     * @param radix 进制
     * @return 指定进制数据
     */
    public static String toString(long i, int radix) {
        if (radix < MIN_RADIX || radix > MAX_RADIX) {
            radix = 10;
        }
        if (radix == 10) {
            return Long.toString(i);
        }

        final int size = 65;
        int charPos = 64;

        char[] buf = new char[size];
        boolean negative = (i < 0);

        if (!negative) {
            i = -i;
        }

        while (i <= -radix) {
            buf[charPos--] = digits[(int) (-(i % radix))];
            i = i / radix;
        }
        buf[charPos] = digits[(int) (-i)];

        if (negative) {
            buf[--charPos] = '-';
        }

        return new String(buf, charPos, (size - charPos));
    }

    private static NumberFormatException forInputString(String s) {
        return new NumberFormatException("For input string: \"" + s + "\"");
    }

    /**
     * 将字符串转换为长整型数字
     *
     * @param s     数字字符串
     * @param radix 进制数
     * @return 对应长整型数字
     */
    public static long toNumber(String s, int radix) {
        if (s == null) {
            throw new NumberFormatException("null");
        }

        if (radix < MIN_RADIX) {
            throw new NumberFormatException("radix " + radix + " less than Numbers.MIN_RADIX");
        }
        if (radix > MAX_RADIX) {
            throw new NumberFormatException("radix " + radix + " greater than Numbers.MAX_RADIX");
        }

        long result = 0;
        boolean negative = false;
        int i = 0, len = s.length();
        long limit = -Long.MAX_VALUE;
        long multmin;
        Integer digit;

        if (len > 0) {
            char firstChar = s.charAt(0);
            if (firstChar < '0') {
                if (firstChar == '-') {
                    negative = true;
                    limit = Long.MIN_VALUE;
                } else if (firstChar != '+')
                    throw forInputString(s);

                if (len == 1) {
                    throw forInputString(s);
                }
                i++;
            }
            multmin = limit / radix;
            while (i < len) {
                digit = digitMap.get(s.charAt(i++));
                if (digit == null) {
                    throw forInputString(s);
                }
                if (digit < 0) {
                    throw forInputString(s);
                }
                if (result < multmin) {
                    throw forInputString(s);
                }
                result *= radix;
                if (result < limit + digit) {
                    throw forInputString(s);
                }
                result -= digit;
            }
        } else {
            throw forInputString(s);
        }
        return negative ? result : -result;
    }
}
