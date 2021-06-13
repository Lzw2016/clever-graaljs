package org.clever.graaljs.spring.mvc.support;

import org.clever.graaljs.core.utils.DateTimeUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/09/16 15:15 <br/>
 */
public class IntegerToDateConverter implements Converter<Integer, Date> {
    public static final IntegerToDateConverter Instance = new IntegerToDateConverter();

    private IntegerToDateConverter() {
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public Date convert(Integer source) {
        return DateTimeUtils.parseDate(source);
    }
}
