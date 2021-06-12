package org.clever.graaljs.data.redis.support;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/11 16:30 <br/>
 */
@FunctionalInterface
public interface ScanCallback<T> {

    /**
     * 数据迭代回调处理
     *
     * @param item 数据项
     * @return 是否需要中断迭代
     */
    boolean next(T item);
}
