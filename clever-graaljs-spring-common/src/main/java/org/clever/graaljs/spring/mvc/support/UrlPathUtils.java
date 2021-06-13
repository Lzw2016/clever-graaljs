package org.clever.graaljs.spring.mvc.support;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/06/19 15:32 <br/>
 */
public class UrlPathUtils {
    private static final char SEPARATE = '/';

    public static String connectPath(String... paths) {
        StringBuilder sb = new StringBuilder(64);
        if (paths != null) {
            for (String s : paths) {
                String path = StringUtils.trim(s);
                if (StringUtils.isBlank(path)) {
                    continue;
                }
                // 第一个
                if (sb.length() <= 0) {
                    sb.append(path);
                    continue;
                }
                boolean before = Objects.equals(sb.charAt(sb.length() - 1), SEPARATE);
                boolean after = Objects.equals(path.charAt(0), SEPARATE);
                if (!Objects.equals(before, after)) {
                    sb.append(path);
                } else if (before && after) {
                    sb.append(path.substring(1));
                } else {
                    sb.append(SEPARATE).append(path);
                }
            }
        }
        return sb.toString();
    }
}
