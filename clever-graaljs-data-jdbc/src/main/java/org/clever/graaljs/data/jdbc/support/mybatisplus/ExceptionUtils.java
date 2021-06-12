package org.clever.graaljs.data.jdbc.support.mybatisplus;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/12 11:28 <br/>
 */
@SuppressWarnings("ALL")
public final class ExceptionUtils {

    private ExceptionUtils() {
    }

    /**
     * 返回一个新的异常，统一构建，方便统一处理
     *
     * @param msg 消息
     * @param t   异常信息
     * @return 返回异常
     */
    public static MybatisPlusException mpe(String msg, Throwable t, Object... params) {
        return new MybatisPlusException(String.format(msg, params), t);
    }

    /**
     * 重载的方法
     *
     * @param msg 消息
     * @return 返回异常
     */
    public static MybatisPlusException mpe(String msg, Object... params) {
        return new MybatisPlusException(String.format(msg, params));
    }

    /**
     * 重载的方法
     *
     * @param t 异常
     * @return 返回异常
     */
    public static MybatisPlusException mpe(Throwable t) {
        return new MybatisPlusException(t);
    }
}