package org.clever.graaljs.core.utils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

/**
 * 环型队列缓冲区
 * 作者：lizw <br/>
 * 创建时间：2021/07/04 20:16 <br/>
 */
@Slf4j
public class RingBuffer<T> implements Serializable {
    public static final int Normal_Buffer_Size = 512;
    public static final int Max_Buffer_Size = 10240;

    /**
     * 创建时间
     */
    @Getter
    private final long createTime = System.currentTimeMillis();
    private final Lock lock = new ReentrantLock();
    /**
     * 数据缓冲区大小(数据条目数量)
     */
    @Getter
    private int bufferSize = Normal_Buffer_Size;
    /**
     * 数据缓冲区
     */
    private final T[] buffer;
    /**
     * 缓冲区第一个元素的索引位置
     */
    private final AtomicLong firstIndex = new AtomicLong(0);
    /**
     * 缓冲区最后一个元素的索引位置
     */
    private final AtomicLong lastIndex = new AtomicLong(-1);

    @SuppressWarnings("unchecked")
    public RingBuffer() {
        buffer = (T[]) new Object[bufferSize];
    }

    @SuppressWarnings("unchecked")
    public RingBuffer(int bufferSize) {
        Assert.isTrue(bufferSize > 0 && bufferSize <= Max_Buffer_Size, String.format("bufferSize 取值范围 1 ~ %d", Max_Buffer_Size));
        this.bufferSize = bufferSize;
        buffer = (T[]) new Object[bufferSize];
    }

    private <R> R exclusiveOperation(Function<Void, R> operation) {
        lock.lock();
        try {
            return operation.apply(null);
        } catch (Throwable e) {
            log.error("[JobLogsBuffer] 缓冲区操作异常", e);
            return null;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 加入元素到缓冲区
     */
    public boolean add(T item) {
        if (item == null) {
            return false;
        }
        Boolean res = exclusiveOperation(param -> {
            long last = lastIndex.incrementAndGet();
            if (last >= bufferSize) {
                firstIndex.incrementAndGet();
            }
            buffer[(int) last % bufferSize] = item;
            return true;
        });
        if (res == null) {
            res = false;
        }
        return res;
    }

    /**
     * 获取数据
     */
    @SuppressWarnings("DuplicatedCode")
    public BufferContent<T> getBuffer() {
        return exclusiveOperation(param -> {
            final long first = firstIndex.get();
            final long last = lastIndex.get();
            if (last < 0) {
                return new BufferContent<>(first, last, Collections.emptyList());
            }
            int size = (int) (last - first + 1);
            if (size > bufferSize) {
                log.warn("[JobLogsBuffer] 缓冲区 size > bufferSize | bufferSize={}, size = {}", bufferSize, size);
            }
            List<T> logList = new ArrayList<>(size);
            int start = (int) first % bufferSize;
            int end = (int) last % bufferSize;
            if (end >= start) {
                // 正常读取
                logList.addAll(Arrays.asList(buffer).subList(start, end + 1));
            } else {
                // 环形读取
                end = end + bufferSize;
                for (int index = start; index <= end; index++) {
                    logList.add(buffer[index >= bufferSize ? index % bufferSize : index]);
                }
            }
            return new BufferContent<>(first, last, logList);
        });
    }

    /**
     * 获取数据
     */
    @SuppressWarnings("DuplicatedCode")
    public BufferContent<T> getBuffer(final long startIndex, long size) {
        Assert.isTrue(startIndex >= 0, "startIndex 必须大于等于 0");
        Assert.isTrue(size > 0, "size 必须大于 0");
        return exclusiveOperation(param -> {
            long first = firstIndex.get();
            long last = lastIndex.get();
            if (startIndex > last) {
                return new BufferContent<>(last + 1, last + 1, Collections.emptyList());
            }
            if (startIndex > first) {
                first = startIndex;
            }
            if ((first + size) < last) {
                last = first + size;
            }
            int realSize = (int) (last - first + 1);
            if (realSize > bufferSize) {
                log.warn("[JobLogsBuffer] 缓冲区 realSize > bufferSize | bufferSize={}, size = {}", bufferSize, realSize);
            }
            List<T> logList = new ArrayList<>(realSize);
            int start = (int) first % bufferSize;
            int end = (int) last % bufferSize;
            if (end >= start) {
                // 正常读取
                logList.addAll(Arrays.asList(buffer).subList(start, end + 1));
            } else {
                // 环形读取
                end = end + bufferSize;
                for (int index = start; index <= end; index++) {
                    logList.add(buffer[index >= bufferSize ? index % bufferSize : index]);
                }
            }
            return new BufferContent<>(first, last, logList);
        });
    }

    public long getFirstIndex() {
        return firstIndex.get();
    }

    public long getLastIndex() {
        return lastIndex.get();
    }

    public void reset() {
        exclusiveOperation(param -> {
            firstIndex.set(0);
            lastIndex.set(-1);
            Arrays.fill(buffer, null);
            return null;
        });
    }

    @EqualsAndHashCode
    @Getter
    public static class BufferContent<T> implements Serializable {
        /**
         * 返回的第一条数据索引位置
         */
        private final long firstIndex;

        /**
         * 当前最后一条数据索引位置
         */
        private final long lastIndex;

        private final List<T> content;

        public BufferContent(long firstIndex, long lastIndex, List<T> content) {
            this.firstIndex = firstIndex;
            this.lastIndex = lastIndex;
            this.content = content;
        }
    }

}
