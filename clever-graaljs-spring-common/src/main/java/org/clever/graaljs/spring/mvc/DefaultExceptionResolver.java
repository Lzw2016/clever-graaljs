package org.clever.graaljs.spring.mvc;

import com.alibaba.excel.exception.ExcelAnalysisException;
import lombok.extern.slf4j.Slf4j;
import org.clever.graaljs.core.exception.BusinessException;
import org.clever.graaljs.spring.mvc.model.ValidMessage;
import org.clever.graaljs.spring.mvc.model.response.ErrorResponse;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/09/21 12:01 <br/>
 */
@Slf4j
public class DefaultExceptionResolver implements ExceptionResolver {
    public static final DefaultExceptionResolver Instance = new DefaultExceptionResolver();

    private DefaultExceptionResolver() {
    }

    /**
     * 获取请求参数校验错误信息
     *
     * @return 请求参数校验没有错误返回null
     */
    protected List<ValidMessage> getValidMessages(BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            return null;
        }
        List<ValidMessage> validMessageList = new ArrayList<>();
        List<FieldError> validError = bindingResult.getFieldErrors();
        for (FieldError fieldError : validError) {
            validMessageList.add(new ValidMessage(fieldError));
        }
        return validMessageList;
    }

    protected List<ValidMessage> getValidMessages(Set<? extends ConstraintViolation<?>> constraintViolations) {
        List<ValidMessage> validMessageList = new ArrayList<>();
        for (ConstraintViolation<?> violation : constraintViolations) {
            ValidMessage validMessage = new ValidMessage();
            validMessage.setEntityName(violation.getRootBeanClass().getSimpleName());
            validMessage.setFiled(violation.getPropertyPath().toString());
            validMessage.setValue(violation.getInvalidValue() == null ? "null" : violation.getInvalidValue().toString());
            validMessage.setErrorMessage(violation.getMessage());
            validMessage.setCode(violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName());
            validMessageList.add(validMessage);
        }
        return validMessageList;
    }

    protected ErrorResponse newErrorResponse(HttpServletRequest request, HttpServletResponse response, Throwable e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setException(e.getClass().getName());
        errorResponse.setError(e.getMessage());
        errorResponse.setMessage("服务器内部错误");
        errorResponse.setStatus(response.getStatus());
        errorResponse.setTimestamp(new Date());
        return errorResponse;
    }

    /**
     * 数据校验异常
     */
    protected ErrorResponse dataBindError(HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException e) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        ErrorResponse errorResponse = newErrorResponse(request, response, e);
        errorResponse.setValidMessageList(getValidMessages(e.getBindingResult()));
        errorResponse.setMessage("请求参数校验失败");
        return errorResponse;
    }

    /**
     * 数据校验异常
     */
    protected ErrorResponse dataBindError(HttpServletRequest request, HttpServletResponse response, BindException e) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        ErrorResponse errorResponse = newErrorResponse(request, response, e);
        errorResponse.setValidMessageList(getValidMessages(e.getBindingResult()));
        errorResponse.setMessage("请求参数校验失败");
        return errorResponse;
    }

    /**
     * 请求参数转换异常
     */
    protected ErrorResponse dataBindError(HttpServletRequest request, HttpServletResponse response, HttpMessageConversionException e) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        ErrorResponse errorResponse = newErrorResponse(request, response, e);
        errorResponse.setMessage("请求参数转换异常");
        return errorResponse;
    }

    /**
     * 请求参数校验异常
     */
    protected ErrorResponse dataBindError(HttpServletRequest request, HttpServletResponse response, ValidationException e) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        ErrorResponse errorResponse = newErrorResponse(request, response, e);
        errorResponse.setMessage("请求参数校验异常");
        return errorResponse;
    }

    /**
     * 业务异常处理方法<br/>
     */
    protected ErrorResponse businessError(HttpServletRequest request, HttpServletResponse response, BusinessException e) {
        if (e.getStatus() != null) {
            response.setStatus(e.getStatus());
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        ErrorResponse errorResponse = newErrorResponse(request, response, e);
        errorResponse.setError("业务异常");
        errorResponse.setMessage(e.getMessage());
        return errorResponse;
    }

    /**
     * 数据主键重复
     */
    protected ErrorResponse defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, DuplicateKeyException e) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        ErrorResponse errorResponse = newErrorResponse(request, response, e);
        errorResponse.setMessage("保存数据失败，数据已经存在");
        return errorResponse;
    }

    /**
     * 文件上传大小超过配置的最大值
     */
    protected ErrorResponse defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, MaxUploadSizeExceededException e) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        ErrorResponse errorResponse = newErrorResponse(request, response, e);
        errorResponse.setMessage("上传文件大小超限");
        return errorResponse;
    }

    /**
     * 解析Excel文件异常
     */
    protected ErrorResponse defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, ExcelAnalysisException e) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        ErrorResponse errorResponse = newErrorResponse(request, response, e);
        errorResponse.setMessage(e.getCause().getMessage());
        return errorResponse;
    }

    @Override
    public Object resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex == null) {
            return null;
        }
        ErrorResponse errorResponse;
        if (ex instanceof MethodArgumentNotValidException) {
            errorResponse = dataBindError(request, response, (MethodArgumentNotValidException) ex);
        } else if (ex instanceof BindException) {
            errorResponse = dataBindError(request, response, (BindException) ex);
        } else if (ex instanceof HttpMessageConversionException) {
            errorResponse = dataBindError(request, response, (HttpMessageConversionException) ex);
        } else if (ex instanceof ValidationException) {
            errorResponse = dataBindError(request, response, (ValidationException) ex);
        } else if (ex instanceof BusinessException) {
            errorResponse = businessError(request, response, (BusinessException) ex);
        } else if (ex instanceof DuplicateKeyException) {
            errorResponse = defaultErrorHandler(request, response, (DuplicateKeyException) ex);
        } else if (ex instanceof MaxUploadSizeExceededException) {
            errorResponse = defaultErrorHandler(request, response, (MaxUploadSizeExceededException) ex);
        } else if (ex instanceof ExcelAnalysisException) {
            errorResponse = defaultErrorHandler(request, response, (ExcelAnalysisException) ex);
        } else if (ex instanceof HttpRequestMethodNotSupportedException
                || ex instanceof HttpMediaTypeNotSupportedException
                || ex instanceof HttpMediaTypeNotAcceptableException
                || ex instanceof ServletRequestBindingException
                || ex instanceof TypeMismatchException
                || ex instanceof MissingServletRequestPartException
                || ex instanceof NoHandlerFoundException
                || ex instanceof AsyncRequestTimeoutException) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            errorResponse = newErrorResponse(request, response, ex);
            errorResponse.setMessage("客户端请求错误");
        } else {
            log.warn("[ScriptHandler]-全局的异常处理  ", ex);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse = newErrorResponse(request, response, ex);
        }
        return errorResponse;
    }
}
