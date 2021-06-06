package org.clever.graaljs.core.internal;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/25 18:58 <br/>
 */
public interface Console {
    /**
     * 维护一个特定于 label 的内部计数器，并将用给定 label 调用 console.count() 的次数输出 (label = “default”)
     */
    void count();

    /**
     * 维护一个特定于 label 的内部计数器，并将用给定 label 调用 console.count() 的次数输出
     *
     * @param label label值，默认“default”
     */
    void count(String label);

    /**
     * 重置特定于 label 的内部计数器 (label = “default”)
     */
    void countReset();

    /**
     * 重置特定于 label 的内部计数器
     *
     * @param label label值，默认“default”
     */
    void countReset(String label);

    /**
     * 启动一个计时器，用以计算一个操作的持续时间。 计时器由一个唯一的 label 标识。 当调用 console.timeEnd() 时，可以使用相同的 label 来停止计时器，并以毫秒为单位将持续时间输出到 stdout (label = “default”)
     */
    void time();

    /**
     * 启动一个计时器，用以计算一个操作的持续时间。 计时器由一个唯一的 label 标识。 当调用 console.timeEnd() 时，可以使用相同的 label 来停止计时器，并以毫秒为单位将持续时间输出到 stdout
     *
     * @param label label值，默认“default”
     */
    void time(String label);

    /**
     * 对于先前通过调用 console.time() 启动的计时器，将经过时间和其他 data 参数打印到 stdout
     *
     * @param label label值，默认“default”
     * @param args  输出数据
     */
    void timeLog(String label, Object... args);

    /**
     * 停止先前通过调用 console.time() 启动的计时器，并打印结果到 stdout (label = “default”)
     */
    void timeEnd();

    /**
     * 停止先前通过调用 console.time() 启动的计时器，并打印结果到 stdout
     *
     * @param label label值，默认“default”
     */
    void timeEnd(String label);

    /**
     * 打印输出
     *
     * @param args 输出数据
     */
    void trace(Object... args);

    /**
     * Debug打印输出
     *
     * @param args 输出数据
     */
    void log(Object... args);

    /**
     * debug打印输出
     *
     * @param args 输出数据
     */
    void debug(Object... args);

    /**
     * info打印输出
     *
     * @param args 输出数据
     */
    void info(Object... args);

    /**
     * warn打印输出
     *
     * @param args 输出数据
     */
    void warn(Object... args);

    /**
     * error打印输出
     *
     * @param args 输出数据
     */
    void error(Object... args);
}
