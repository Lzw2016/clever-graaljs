package org.clever.graaljs.fast.api.demo.utils;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/08/25 11:37 <br/>
 */
public class JSUtils {
    public static final JSUtils Instance = new JSUtils();

    public long freeMemory() {
        return Runtime.getRuntime().freeMemory();
    }
}
