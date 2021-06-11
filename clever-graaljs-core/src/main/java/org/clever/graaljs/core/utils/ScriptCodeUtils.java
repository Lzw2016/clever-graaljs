package org.clever.graaljs.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/03 16:04 <br/>
 */
public class ScriptCodeUtils {
    public static final String COMPRESS_CHAR_SPACE = " ";

    /**
     * 压缩脚本代码字符串
     *
     * @param code         脚本代码字符串
     * @param compressChar 压缩替换字符串
     * @param keepWrap     是否保持换行符号
     */
    public static String compressCode(String code, String compressChar, boolean keepWrap) {
        if (StringUtils.isBlank(code)) {
            return "";
        }
        StringBuilder sb = new StringBuilder(code.length());
        boolean current = false;
        for (int i = 0; i < code.length(); i++) {
            char c = code.charAt(i);
            if (keepWrap && Objects.equals('\n', c)) {
                sb.append(c);
                continue;
            }
            boolean pre = current;
            current = Character.isWhitespace(c);
            if (current) {
                continue;
            }
            int length = sb.length();
            if (pre && length > 0 && !Character.isWhitespace(sb.charAt(length - 1))) {
                sb.append(compressChar);
            }
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 压缩脚本代码字符串
     *
     * @param code     脚本代码字符串
     * @param keepWrap 是否保持换行符号
     */
    public static String compressCode(String code, boolean keepWrap) {
        return compressCode(code, COMPRESS_CHAR_SPACE, keepWrap);
    }

    public static String wrapFunction(String code, long count) {
        Assert.isNotBlank(code, "脚本代码不能为空");
        code = String.format("function __fuc_autogenerate_%1$s(args) {;%2$s\n}\n__fuc_autogenerate_%1$s;", count, code);
        return compressCode(code, true);
    }
}
