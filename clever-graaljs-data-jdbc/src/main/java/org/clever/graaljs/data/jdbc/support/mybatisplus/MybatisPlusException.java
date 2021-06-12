package org.clever.graaljs.data.jdbc.support.mybatisplus;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/12 11:28 <br/>
 */
@SuppressWarnings("ALL")
public class MybatisPlusException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MybatisPlusException(String message) {
        super(message);
    }

    public MybatisPlusException(Throwable throwable) {
        super(throwable);
    }

    public MybatisPlusException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
