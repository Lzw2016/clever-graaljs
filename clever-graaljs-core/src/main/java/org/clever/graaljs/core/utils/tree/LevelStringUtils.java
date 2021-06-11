package org.clever.graaljs.core.utils.tree;

import org.apache.commons.lang3.StringUtils;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-5-8 15:36 <br/>
 */
public class LevelStringUtils {
    /**
     * 层级串使用的字符串，用于检验层级串是否合法
     */
    private static final char[] LevelChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 判断层级串是否合法<br/>
     *
     * @param levelString 层级串
     * @return 层级串是否合法返回True
     */
    public static boolean isLevelString(String levelString) {
        if (StringUtils.isBlank(levelString)) {
            return false;
        }
        for (char c : levelString.toCharArray()) {
            boolean flag = false;
            for (char tmp : LevelChar) {
                if (c == tmp) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取当前层级串的下一个层级串，如“001003005”的下一个层级串是“001003006”<br/>
     * 1.若当前层级串已是当前层级的最大层级串，如“001003FFF”，则会抛出异常<br/>
     *
     * @param levelLength 层级长度
     * @param levelString 层级串
     * @return 下一个层级串
     */
    public static String nextLevelString(int levelLength, String levelString) {
        if (!isLevelString(levelString)) {
            throw new RuntimeException("层级串：[" + levelString + "]无效");
        }
        if (levelLength <= 0) {
            throw new RuntimeException("层级长度：[" + levelLength + "]参数无效");
        }
        if ((levelString.length() % levelLength) != 0) {
            throw new RuntimeException("层级长度：[" + levelLength + "]参数错误，层级串：" + levelString);
        }
        // 前缀不变的串
        String prefixLevelStr = levelString.substring(0, levelString.length() - levelLength);
        // 最后层级的字符串
        String lastLevelStr = levelString.substring(levelString.length() - levelLength);
        int number = Integer.parseInt(lastLevelStr, 16);
        number++;
        lastLevelStr = Integer.toHexString(number).toUpperCase();
        if (lastLevelStr.length() > levelLength) {
            throw new RuntimeException("层级串：[" + levelString + "]层级串已是当前层级的最大层级串");
        }
        StringBuilder tmp = new StringBuilder(lastLevelStr);
        while (tmp.length() < levelLength) {
            tmp.insert(0, '0');
        }
        return prefixLevelStr + tmp.toString();
    }

    /**
     * 得到根层级串，如：“000”、“0000”、“000000”<br/>
     *
     * @param levelLength 层级长度
     * @return 根层级串
     */
    public static String rootLevelString(int levelLength) {
        if (levelLength <= 0) {
            throw new RuntimeException("层级长度：[" + levelLength + "]参数无效");
        }
        StringBuilder tmp = new StringBuilder();
        while (tmp.length() < (levelLength - 1)) {
            tmp.insert(0, '0');
        }
        tmp.append('0');
        return tmp.toString();
    }
}
