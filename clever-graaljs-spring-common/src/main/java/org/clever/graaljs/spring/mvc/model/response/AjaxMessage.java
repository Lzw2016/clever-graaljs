package org.clever.graaljs.spring.mvc.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.clever.graaljs.spring.mvc.model.ValidMessage;
import org.springframework.validation.FieldError;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * ajax异步请求的响应消息<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-8 21:32 <br/>
 */
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
@Data
public class AjaxMessage<T> extends BaseResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 下次请求是否需要验证码
     */
    private boolean isNeedValidateCode = false;

    /**
     * 操作是否成功
     */
    private boolean success = false;

    /**
     * 请求响应返回的数据
     */
    private T result;

    /**
     * 请求成功后跳转地址
     */
    private String successUrl;

    /**
     * 请求失败后跳转地址
     */
    private String failUrl;

    /**
     * 操作成功消息
     */
    private String successMessage;

    /**
     * 操作失败消息
     */
    private String failMessage;

    /**
     * 服务器是否发生异常
     */
    private boolean hasException = false;

    /**
     * 服务端异常消息
     */
    private String exceptionMessage;

    /**
     * 服务端异常的堆栈信息
     */
    private String exceptionStack;

    /**
     * 表单数据验证的错误消息
     */
    private List<ValidMessage> validMessageList;

    /**
     * 默认构造，默认请求操作失败 success=false
     */
    public AjaxMessage() {
    }

    /**
     * 请求服务端发生异常(hasException = true)时，使用的构造方法<br/>
     *
     * @param throwable        请求的异常对象
     * @param exceptionMessage 请求的异常时的消息
     */
    public AjaxMessage(Throwable throwable, String exceptionMessage) {
        this(null, false, null, null, true, throwable, exceptionMessage);
    }

    /**
     * 服务端请求完成并且操作成功(success = true)<br/>
     *
     * @param result         请求响应数据
     * @param successMessage success=true时，请求成功时的消息
     */
    public AjaxMessage(T result, String successMessage) {
        this(result, true, successMessage, null, false, null, null);
    }

    /**
     * 服务端请求没有发生异常时，使用的构造方法<br/>
     *
     * @param success        请求结果是否成功
     * @param successMessage success=true时，请求成功时的消息
     * @param failMessage    success=false时，请求失败时的消息
     */
    public AjaxMessage(boolean success, String successMessage, String failMessage) {
        this(null, success, successMessage, failMessage, false, null, null);
    }

    /**
     * 服务端请求完成，没有发生异常时，使用的构造方法<br/>
     *
     * @param result         请求响应数据
     * @param success        请求结果是否成功
     * @param successMessage success=true时，请求成功时的消息
     * @param failMessage    success=false时，请求失败时的消息
     */
    public AjaxMessage(T result, boolean success, String successMessage, String failMessage) {
        this(result, success, successMessage, failMessage, false, null, null);
    }

    /**
     * @param result           请求响应数据
     * @param success          请求结果是否成功
     * @param successMessage   success=true时，请求成功时的消息
     * @param failMessage      success=false时，请求失败时的消息
     * @param hasException     是否发生服务器异常
     * @param throwable        请求的异常对象
     * @param exceptionMessage 请求的异常时的消息
     */
    public AjaxMessage(T result, boolean success, String successMessage, String failMessage, boolean hasException, Throwable throwable, String exceptionMessage) {
        this.result = result;
        this.success = success;
        this.successMessage = successMessage;
        this.failMessage = failMessage;
        this.hasException = hasException;
        this.exceptionStack = getStackTraceAsString(throwable);
        this.exceptionMessage = exceptionMessage;
    }

    /**
     * 增加验证错误消息<br/>
     *
     * @param validMessage 服务端数据验证错误消息
     * @return 当前对象
     */
    public AjaxMessage<?> addValidMessage(ValidMessage validMessage) {
        if (this.validMessageList == null) {
            this.validMessageList = new ArrayList<>();
        }
        this.validMessageList.add(validMessage);
        return this;
    }

    /**
     * 增加验证错误消息<br/>
     *
     * @param fieldError Spring的验证错误消息
     * @return 当前对象
     */
    public AjaxMessage<?> addValidMessage(FieldError fieldError) {
        ValidMessage validMessage = new ValidMessage(fieldError);
        return addValidMessage(validMessage);
    }

    /**
     * 设置异常信息<br/>
     * 1.请求失败 success=false<br/>
     * 2.设置 hasException = true
     * 3.给返回的异常堆栈属性赋值(exceptionStack)<br/>
     *
     * @param e 异常对象
     */
    public void setException(Throwable e) {
        if (e != null) {
            this.success = false;
            this.hasException = true;
        }
        this.exceptionStack = getStackTraceAsString(e);
    }

    /**
     * 将ErrorStack转化为String(获取异常的堆栈信息)<br/>
     *
     * @param e 异常对象
     * @return 异常的堆栈信息
     */
    private static String getStackTraceAsString(Throwable e) {
        if (e == null) {
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    /*--------------------------------------------------------------
     * 			getter、setter
     * -------------------------------------------------------------*/

    /**
     * 设置请求是否成功<br/>
     * <b>
     * 设置true,置空failMessage<br/>
     * 设置false,置空successMessage<br/>
     * </b>
     */
    public void setSuccess(boolean success) {
        this.success = success;
        if (success) {
            this.failMessage = null;
        } else {
            this.successMessage = null;
        }
    }

    /**
     * 设置请求成功返回的消息，置空failMessage
     */
    public void setSuccessMessage(String successMessage) {
        if (successMessage != null) {
            this.successMessage = successMessage;
            this.failMessage = null;
        } else {
            this.successMessage = null;
        }
    }

    /**
     * 设置请求失败返回的消息，置空successMessage
     */
    public void setFailMessage(String failMessage) {
        if (failMessage != null) {
            this.failMessage = failMessage;
            this.successMessage = null;
        } else {
            this.failMessage = null;
        }
    }
}
