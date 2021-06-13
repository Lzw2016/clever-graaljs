package org.clever.graaljs.core.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统业务异常
 * <p>
 * 作者：lzw <br/>
 * 创建时间：2017-09-03 11:45 <br/>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 请求响应状态吗
     */
    private Integer status;

    public BusinessException() {
        super();
    }

    /**
     * @param message 异常信息
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * @param message 异常信息
     * @param sc      请求响应状态码(HttpStatus)
     */
    public BusinessException(String message, int sc) {
        super(message);
        this.status = sc;
    }

    /**
     * @param message 异常信息
     * @param cause   异常
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message 异常信息
     * @param sc      请求响应状态码
     * @param cause   异常
     */
    public BusinessException(String message, int sc, Throwable cause) {
        super(message, cause);
        this.status = sc;
    }

    /**
     * @param cause 异常信息
     */
    public BusinessException(Throwable cause) {
        super(cause);
    }

    /**
     * @param sc    请求响应状态码
     * @param cause 异常
     */
    public BusinessException(int sc, Throwable cause) {
        super(cause);
        this.status = sc;
    }
}
