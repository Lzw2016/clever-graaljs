package org.clever.graaljs.spring.core.utils.validator;

import org.clever.graaljs.spring.core.utils.validator.annotation.ValidStringStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 作者： lzw<br/>
 * 创建时间：2018-11-09 21:41 <br/>
 */
public class StatusStringValidator implements ConstraintValidator<ValidStringStatus, String> {

    private Set<String> validStatus;

    @Override
    public void initialize(ValidStringStatus validStringStatus) {
        String[] values = validStringStatus.value();
        validStatus = new HashSet<>(values.length);
        validStatus.addAll(Arrays.asList(values));
    }

    @Override
    public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
        if (str == null) {
            return true;
        }
        return validStatus.contains(str);
    }
}
