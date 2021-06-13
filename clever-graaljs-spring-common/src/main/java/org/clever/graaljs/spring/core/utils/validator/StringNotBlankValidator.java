package org.clever.graaljs.spring.core.utils.validator;

import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.spring.core.utils.validator.annotation.StringNotBlank;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 作者： lzw<br/>
 * 创建时间：2018-11-09 21:49 <br/>
 */
public class StringNotBlankValidator implements ConstraintValidator<StringNotBlank, String> {

    @Override
    public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
        if (str == null) {
            return true;
        }
        return StringUtils.isNotBlank(str);
    }
}
