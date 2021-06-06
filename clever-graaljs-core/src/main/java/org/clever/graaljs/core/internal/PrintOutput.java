package org.clever.graaljs.core.internal;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/26 08:33 <br/>
 */
@FunctionalInterface
public interface PrintOutput {

    /**
     * 打印输出
     *
     * @param args 输出数据
     */
    void print(Object... args);
}
