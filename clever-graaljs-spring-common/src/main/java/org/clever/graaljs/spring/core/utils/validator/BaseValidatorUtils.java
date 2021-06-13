package org.clever.graaljs.spring.core.utils.validator;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.*;

/**
 * JSR303 Validator工具类<br/>
 * ConstraintViolation 中包含propertyPath, message 和invalidValue等信息<br/>
 * 1.List&lt;String&gt;, String内容为message<br/>
 * 2.List&lt;String&gt;, String内容为propertyPath + separator + message<br/>
 * 3.Map&lt;propertyPath, message&gt;<br/>
 *
 * @author LiZW
 * @version 2015年5月28日 下午3:43:21
 */
public class BaseValidatorUtils {

    /**
     * 调用JSR303的validate方法, 验证失败时抛出异常
     *
     * @param validator JSR303验证类，可以使用不同的实现
     * @param bean      待验证的bean对象
     * @param groups    验证的组
     * @param <T>       待验证的bean对象类型
     * @throws ConstraintViolationException 验证失败
     */
    public static <T> void validateThrowException(Validator validator, T bean, Class<?>... groups) throws ConstraintViolationException {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(bean, groups);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    /**
     * 转换 Set&lt;ConstraintViolation&gt; 为 List&lt;message&gt;，验证结果信息转换成字符串
     *
     * @param constraintViolations 验证结果信息
     * @return 验证异常信息集合
     */
    public static List<String> extractMessage(Set<? extends ConstraintViolation<?>> constraintViolations) {
        List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<?> violation : constraintViolations) {
            errorMessages.add(violation.getMessage());
        }
        return errorMessages;
    }

    /**
     * 获取验证异常(ConstraintViolationException)中的验证错误信息<br/>
     * 获取Set&lt;ConstraintViolations&gt;转换成为List&lt;message&gt;<br/>
     *
     * @param e Bean验证异常
     * @return 验证异常信息List集合
     */
    public static List<String> extractMessage(ConstraintViolationException e) {
        return extractMessage(e.getConstraintViolations());
    }

    /**
     * 转换 Set&lt;ConstraintViolation&gt; 为 Map&lt;property, message&gt;，验证结果信息转换成字符串
     *
     * @param constraintViolations 验证结果信息
     * @return 验证异常信息集合
     */
    public static Map<String, String> extractPropertyAndMessage(Set<? extends ConstraintViolation<?>> constraintViolations) {
        Map<String, String> errorMessages = new HashMap<>();
        for (ConstraintViolation<?> violation : constraintViolations) {
            errorMessages.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return errorMessages;
    }

    /**
     * 获取验证异常(ConstraintViolationException)中的验证错误信息<br/>
     * 获取Set&lt;ConstraintViolations&gt;转换成为Map&lt;property, message&gt;<br/>
     *
     * @param e Bean验证异常
     * @return 验证异常信息Map集合
     */
    public static Map<String, String> extractPropertyAndMessage(ConstraintViolationException e) {
        return extractPropertyAndMessage(e.getConstraintViolations());
    }

    /**
     * 转换 Set&lt;ConstraintViolation&gt; 为 List&lt;propertyPath +separator+ message&gt;，验证结果信息转换成字符串
     *
     * @param constraintViolations 验证结果信息
     * @param separator            分隔符号，Bean字段与其验证失败的错误信息间的分隔符号
     * @return 验证异常信息List集合
     */
    public static List<String> extractPropertyAndMessageAsList(Set<? extends ConstraintViolation<?>> constraintViolations, String separator) {
        List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<?> violation : constraintViolations) {
            errorMessages.add(violation.getPropertyPath() + separator + violation.getMessage());
        }
        return errorMessages;
    }

    /**
     * 获取验证异常(ConstraintViolationException)中的验证错误信息<br/>
     * 获取Set&lt;ConstraintViolations&gt;转换成为List&lt;property +separator+ message&gt;<br/>
     *
     * @param e Bean验证异常
     * @return 验证异常信息Map集合
     */
    public static List<String> extractPropertyAndMessageAsList(ConstraintViolationException e, String separator) {
        return extractPropertyAndMessageAsList(e.getConstraintViolations(), separator);
    }
}
