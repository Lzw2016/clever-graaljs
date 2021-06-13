package org.clever.graaljs.spring.core.utils.validator.annotation;

import org.clever.graaljs.spring.core.utils.validator.StringNotBlankValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 校验字符串不为空字符串，但可以为null(不校验null数据)<br />
 * null -> true<br />
 * "" -> false<br />
 * "  " -> false<br />
 * "a" -> true<br />
 * "   a   " -> true<br />
 * 作者： lzw<br/>
 * 创建时间：2018-11-09 21:47 <br/>
 */
@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = StringNotBlankValidator.class)
public @interface StringNotBlank {

    String message() default "不能是空字符串";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
