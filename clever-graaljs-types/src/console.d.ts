/**
 * 控制台<br/>
 * 1.变量打印输出<br/>
 * 2.操作计数<br/>
 * 3.操作计时<br/>
 */
interface Console {
    /**
     * 维护一个特定于 label 的内部计数器，并将用给定 label 调用 console.count() 的次数输出 (label = “default”)
     */
    count(): void;

    /**
     * 维护一个特定于 label 的内部计数器，并将用给定 label 调用 console.count() 的次数输出
     * @param label label label值，默认“default”
     */
    count(label: string): void;

    /**
     * 重置特定于 label 的内部计数器 (label = “default”)
     */
    countReset(): void;

    /**
     * 重置特定于 label 的内部计数器
     *
     * @param label label值，默认“default”
     */
    countReset(label: string): void;

    /**
     * 启动一个计时器，用以计算一个操作的持续时间。 计时器由一个唯一的 label 标识。 当调用 console.timeEnd() 时，可以使用相同的 label 来停止计时器，并以毫秒为单位将持续时间输出到 stdout (label = “default”)
     */
    time(): void;

    /**
     * 启动一个计时器，用以计算一个操作的持续时间。 计时器由一个唯一的 label 标识。 当调用 console.timeEnd() 时，可以使用相同的 label 来停止计时器，并以毫秒为单位将持续时间输出到 stdout
     *
     * @param label label值，默认“default”
     */
    time(label: string): void;

    /**
     * 对于先前通过调用 console.time() 启动的计时器，将经过时间和其他 data 参数打印到 stdout
     *
     * @param label     label值，默认“default”
     * @param message   输出数据
     */
    timeLog(label: string, ...message: any[]): void;

    /**
     * 停止先前通过调用 console.time() 启动的计时器，并打印结果到 stdout (label = “default”)
     */
    timeEnd(): void;

    /**
     * 停止先前通过调用 console.time() 启动的计时器，并打印结果到 stdout
     *
     * @param label label值，默认“default”
     */
    timeEnd(label: string): void;

    /**
     * trace打印输出
     *
     * @param message 输出数据
     */
    trace(...message: any[]): void;

    /**
     * debug打印输出
     *
     * @param message 输出数据
     */
    log(...message: any[]): void;

    /**
     * debug打印输出
     *
     * @param message 输出数据
     */
    debug(...message: any[]): void;

    /**
     * info打印输出
     *
     * @param message 输出数据
     */
    info(...message: any[]): void;

    /**
     * warn打印输出
     *
     * @param message 输出数据
     */
    warn(...message: any[]): void;

    /**
     * error打印输出
     *
     * @param message 输出数据
     */
    error(...message: any[]): void;
}

/**
 * 控制台对象
 */
declare const console: Console;
