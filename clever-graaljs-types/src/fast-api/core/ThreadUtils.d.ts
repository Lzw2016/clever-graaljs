/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/28 22:34 <br/>
 */
interface ThreadUtils extends JObject {
    /**
     * 线程栈信息
     *
     * @return 线程栈信息字符串
     */
    track(thread: JThread): JString;

    /**
     * 当前线程栈信息
     *
     * @return 线程栈信息字符串
     */
    track(): JString;

    /**
     * 打印线程栈信息
     */
    printTrack(thread: JThread): void;

    /**
     * 打印当前线程栈信息
     */
    printTrack(): void;

    /**
     * 休眠一段时间
     *
     * @param millis 毫秒
     */
    sleep(millis: number): void;

    /**
     * 放弃当前CPU使用权(当前线程放弃本次CPU时间)
     */
    yield(): void;

    /**
     * 获取当前线程
     */
    currentThread(): JThread;
}

declare const ThreadUtils: ThreadUtils;

