package org.clever.graaljs.core.builtin.adapter;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/28 22:34 <br/>
 */
public class ThreadUtils {
    public static final ThreadUtils Instance = new ThreadUtils();

    private ThreadUtils() {
    }

    /**
     * 线程栈信息
     *
     * @return 线程栈信息字符串
     */
    public String track(Thread thread) {
        return org.clever.graaljs.core.utils.ThreadUtils.track(thread);
    }

    /**
     * 当前线程栈信息
     *
     * @return 线程栈信息字符串
     */
    public String track() {
        return org.clever.graaljs.core.utils.ThreadUtils.track();
    }

    /**
     * 打印线程栈信息
     */
    public void printTrack(Thread thread) {
        org.clever.graaljs.core.utils.ThreadUtils.printTrack(thread);
    }

    /**
     * 打印当前线程栈信息
     */
    public void printTrack() {
        org.clever.graaljs.core.utils.ThreadUtils.printTrack();
    }

    /**
     * 休眠一段时间
     *
     * @param millis 毫秒
     */
    public void sleep(Number millis) throws InterruptedException {
        Thread.sleep(millis.longValue());
    }

    /**
     * 放弃当前CPU使用权(当前线程放弃本次CPU时间)
     */
    public void yield(Number millis) {
        Thread.yield();
    }

    /**
     * 获取当前线程
     */
    public Thread currentThread() {
        return Thread.currentThread();
    }
}
