package org.clever.graaljs.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/03 16:04 <br/>
 */
public class ScriptCodeUtils {
    public static final String COMPRESS_CHAR_SPACE = " ";

    private static final AtomicInteger FUC_COUNTER = new AtomicInteger(0);
    private static final int CODE_MAP_CAPACITY = 10;
    private static final ConcurrentHashMap<String, Integer> CODE_MAP = new ConcurrentHashMap<>(CODE_MAP_CAPACITY);

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
        return StringUtils.trim(sb.toString());
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

    public static synchronized TupleTow<String, Integer> wrapFunction(String code) {
        Assert.isNotBlank(code, "脚本代码不能为空");
        // 控制CODE_MAP容量
        final int preCount = FUC_COUNTER.get();
        if (preCount >= Integer.MAX_VALUE) {
            CODE_MAP.clear();
            FUC_COUNTER.set(0);
        }
        if (CODE_MAP.size() >= CODE_MAP_CAPACITY) {
            // 释放10%空间
            final int max = (preCount - CODE_MAP_CAPACITY) + (CODE_MAP_CAPACITY / 10);
            CODE_MAP.entrySet().removeIf(entry -> entry.getValue() <= max);
        }
        // 获取code count
        code = compressCode(code, true);
        final int currentCount = CODE_MAP.computeIfAbsent(code, strCode -> FUC_COUNTER.incrementAndGet());
        // 把code包装成function
        code = String.format("function __fuc_autogenerate_%1$s(args) {;%2$s\n}\n__fuc_autogenerate_%1$s;", currentCount, code);
        return TupleTow.creat(code, currentCount);
    }
}
