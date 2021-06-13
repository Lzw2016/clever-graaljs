package org.clever.graaljs.spring.core.utils.validator;

import org.clever.graaljs.spring.core.utils.validator.annotation.ValidIntegerStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

/**
 * 作者： lzw<br/>
 * 创建时间：2018-11-09 21:31 <br/>
 */
public class StatusIntegerValidator implements ConstraintValidator<ValidIntegerStatus, Integer> {

    private Integer[] validStatus;

    @Override
    public void initialize(ValidIntegerStatus validIntegerStatus) {
        int[] ints = validIntegerStatus.value();
        int n = ints.length;
        Integer[] integers = new Integer[n];
        for (int i = 0; i < n; i++) {
            integers[i] = ints[i];
        }
        this.validStatus = integers;
    }

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        if (integer == null) {
            return true;
        }
        List<Integer> status = Arrays.asList(validStatus);
        return status.contains(integer);
    }
}
