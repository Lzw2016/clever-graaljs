package org.clever.graaljs.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 线程工具类
 * 作者： lzw<br/>
 * 创建时间：2019-01-24 16:15 <br/>
 */
@Slf4j
public class ThreadUtils {

    /**
     * 线程栈信息
     *
     * @return 线程栈信息字符串
     */
    public static String track(Thread thread) {
        if (thread == null) {
            return "";
        }
        StackTraceElement[] stackTrace = thread.getStackTrace();
        StringBuilder stringBuilder = new StringBuilder();
        for (StackTraceElement stackTraceElement : stackTrace) {
            stringBuilder.append(
                    String.format(
                            "%s\tat %s.%s(%s:%s)",
                            System.getProperty("line.separator"),
                            stackTraceElement.getClassName(),
                            stackTraceElement.getMethodName(),
                            stackTraceElement.getFileName(),
                            stackTraceElement.getLineNumber()
                    )
            );
        }
        return stringBuilder.toString();
    }

    /**
     * 当前线程栈信息
     *
     * @return 线程栈信息字符串
     */
    public static String track() {
        return track(Thread.currentThread());
    }

    /**
     * 打印线程栈信息
     */
    public static void printTrack(Thread thread) {
        String str = track(thread);
        if (StringUtils.isBlank(str)) {
            return;
        }
        log.info(str);
    }

    /**
     * 打印当前线程栈信息
     */
    public static void printTrack() {
        printTrack(Thread.currentThread());
    }
}
