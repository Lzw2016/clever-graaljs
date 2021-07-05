package org.clever.graaljs.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.SourceSection;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/25 21:18 <br/>
 */
public class ExceptionUtils {
    /**
     * 将CheckedException转换为UncheckedException.<br/>
     *
     * @param e 需要try...catch...的异常
     * @return 不需要try...catch...的异常
     */
    public static RuntimeException unchecked(Throwable e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        } else {
            return new RuntimeException(e);
        }
    }

    /**
     * 返回脚本执行错误原因
     */
    public static String getErrorCodeLocation(Throwable e) {
        if (e == null) {
            return StringUtils.EMPTY;
        }
        PolyglotException ex = null;
        do {
            if (e instanceof PolyglotException) {
                ex = (PolyglotException) e;
            }
            e = e.getCause();
        } while (ex == null && e != null);
        if (ex == null) {
            return StringUtils.EMPTY;
        }
        Object obj = ReflectionsUtils.getFieldValue(ex, "impl");
        if (obj instanceof AbstractPolyglotImpl.AbstractExceptionImpl) {
            AbstractPolyglotImpl.AbstractExceptionImpl impl = (AbstractPolyglotImpl.AbstractExceptionImpl) obj;
            SourceSection section = impl.getSourceLocation();
            if (section != null && section.isAvailable() && section.getSource() != null && section.getSource().getCharacters() != null) {
                final String code = section.getSource().getCharacters().toString();
                final String[] codeLines = code.split("\n");
                final int startLine = section.getStartLine();
                final int endLine = section.getEndLine();
                final StringBuilder sb = new StringBuilder();
                sb.append(ex.getMessage()).append("\n");
                if (startLine == endLine) {
                    sb.append("错误位置: 第").append(startLine).append("行 --->\n");
                } else {
                    sb.append("错误位置: ").append(startLine).append("行 ~ ").append(endLine).append("行 --->\n");
                }
                boolean flag = false;
                for (int i = startLine - 1; i < endLine; i++) {
                    if (codeLines.length > i) {
                        flag = true;
                        sb.append(codeLines[i]).append("\n");
                    }
                }
                if (StringUtils.isNotBlank(section.getCharacters())) {
                    flag = true;
                    sb.append("<--- 调用代码: ").append(section.getCharacters());
                }
                if (flag) {
                    return sb.toString();
                }
            }
        }
        return StringUtils.EMPTY;
    }
}
