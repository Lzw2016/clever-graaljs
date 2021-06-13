package org.clever.graaljs.spring.mvc.model;

import lombok.Data;
import org.springframework.validation.FieldError;

import java.io.Serializable;

/**
 * 服务端数据验证消息封装<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-8 21:32 <br/>
 */
@Data
public class ValidMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 验证的对象名称
     */
    private String entityName;

    /**
     * 验证的字段名
     */
    private String filed;

    /**
     * 验证的字段值
     */
    private String value;

    /**
     * 验证的错误消息
     */
    private String errorMessage;

    /**
     * 验证所使用的JSR 303注解
     */
    private String code;

    public ValidMessage() {

    }

    /**
     * @param fieldError Spring的验证错误消息
     */
    public ValidMessage(FieldError fieldError) {
        this.entityName = fieldError.getObjectName();
        this.filed = fieldError.getField();
        this.errorMessage = fieldError.getDefaultMessage();
        this.code = fieldError.getCode();
        this.value = fieldError.getRejectedValue() == null ? "null" : fieldError.getRejectedValue().toString();
    }

    /**
     * @param entityName   验证对象名称
     * @param filed        验证字段名
     * @param value        验证字段值
     * @param errorMessage 验证错误消息
     * @param code         验证所使用的JSR 303注解
     */
    public ValidMessage(String entityName, String filed, String value, String errorMessage, String code) {
        this.entityName = entityName;
        this.filed = filed;
        this.value = value;
        this.errorMessage = errorMessage;
        this.code = code;
    }
}
