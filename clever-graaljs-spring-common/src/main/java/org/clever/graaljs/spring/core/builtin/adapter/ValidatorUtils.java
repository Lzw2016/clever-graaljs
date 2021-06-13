package org.clever.graaljs.spring.core.builtin.adapter;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.MapBindingResult;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/28 22:34 <br/>
 */
public class ValidatorUtils {
    public final static Pattern EMAIL = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])", Pattern.CASE_INSENSITIVE);

    public static final ValidatorUtils Instance = new ValidatorUtils();

    private ValidatorUtils() {
    }

    /**
     * 对象数据校验(数据校验错误抛出异常)
     *
     * @param bean 数据对象
     * @param rule 校验规则
     * @param fast 快速验证(只要有一个错误就抛出异常)
     */
    public void validated(final Map<String, Object> bean, final Map<String, Object> rule, final boolean fast) throws BindException {
        ValidResult result = valid(bean, rule, fast);
        if (result != null && result.hasError()) {
            MapBindingResult mapBindingResult = new MapBindingResult(bean, bean.getClass().getName());
            for (ValidFieldError error : result.getErrors()) {
                FieldError fieldError = new FieldError(
                        bean.getClass().getName(),
                        error.filed,
                        error.value,
                        true,
                        new String[]{error.code},
                        null,
                        error.message
                );
                mapBindingResult.addError(fieldError);
            }
            throw new BindException(mapBindingResult);
        }
    }

    /**
     * 对象数据校验(数据校验错误抛出异常)
     *
     * @param bean 数据对象
     * @param rule 校验规则
     */
    public void validated(final Map<String, Object> bean, final Map<String, Object> rule) throws BindException {
        validated(bean, rule, false);
    }

    /**
     * 对象数据校验(返回验证结果)
     *
     * @param bean 数据对象
     * @param rule 校验规则
     * @param fast 快速验证(只要有一个错误就返回)
     */
    public ValidResult valid(final Map<String, Object> bean, final Map<String, Object> rule, final boolean fast) {
        final ValidResult result = new ValidResult(false);
        if (bean == null || rule == null) {
            return result;
        }
        doValid(result, null, bean, rule, fast);
        if (result.getErrors().size() > 0) {
            result.setError(true);
        }
        return result;
    }

    /**
     * 对象数据校验(返回验证结果)
     *
     * @param bean 数据对象
     * @param rule 校验规则
     */
    public ValidResult valid(final Map<String, Object> bean, final Map<String, Object> rule) {
        return valid(bean, rule, false);
    }

    /**
     * @param result    校验返回值
     * @param filedPath 校验字段上层路径
     * @param bean      当前层级校验数据
     * @param rule      当前层级校验配置
     * @param fast      快速验证(只要有一个错误就返回)
     */
    @SuppressWarnings("unchecked")
    protected void doValid(final ValidResult result, final String filedPath, final Map<String, Object> bean, final Map<String, Object> rule, final boolean fast) {
        if (fast && result.getErrors().size() > 0) {
            return;
        }
        for (Map.Entry<String, Object> entry : rule.entrySet()) {
            final String filed = entry.getKey();                                                                // 字段名
            final String filedCurrentPath = StringUtils.isBlank(filedPath) ? filed : filedPath + "." + filed;   // 字段路径
            final Object ruleItem = entry.getValue();                                                           // 校验规则
            final Object value = bean.get(filed);                                                               // 字段值
            if (ruleItem == null) {
                continue;
            }
            if (ruleItem instanceof ValidatorRuleItem) {
                // 当前层级校验
                List<ValidFieldError> fieldErrorList = doValid(bean, filed, value, (ValidatorRuleItem) ruleItem, fast);
                result.getErrors().addAll(fieldErrorList);
            } else if (ruleItem instanceof Map && value instanceof Map) {
                // 递归校验
                doValid(result, filedCurrentPath, (Map<String, Object>) value, (Map<String, Object>) ruleItem, fast);
            } else {
                throw new IllegalArgumentException("校验配置错误：" + filedCurrentPath);
            }
            if (fast && result.getErrors().size() > 0) {
                return;
            }
        }
    }

    /**
     * 校验单个字段值
     *
     * @param bean  数据对象
     * @param filed 字段名
     * @param value 字段值
     * @param rule  校验规则
     * @param fast  快速验证(只要有一个错误就返回)
     * @return 数据校验成功返回空集合，失败返回错误信息
     */
    protected List<ValidFieldError> doValid(final Map<String, Object> bean, final String filed, final Object value, final ValidatorRuleItem rule, final boolean fast) {
        final List<ValidFieldError> fieldErrorList = new ArrayList<>();
        if (rule == null) {
            return fieldErrorList;
        }
        final String valueClass = value == null ? "" : value.getClass().getName();
        String message;
        String code;
        // 必须为 null
        if (rule.isNull != null && rule.isNull && value != null) {
            message = rule.message == null ? "必须为null" : rule.message;
            code = "isNull";
            fieldErrorList.add(new ValidFieldError(filed, value, message, code));
        }
        // 必须不为 null
        if (rule.notNull != null && rule.notNull && value == null) {
            message = rule.message == null ? "不能为null" : rule.message;
            code = "notNull";
            fieldErrorList.add(new ValidFieldError(filed, null, message, code));
        }
        // 必须不为null，且满足：非空字符串、非空集合、非空数组
        if (rule.notEmpty != null && rule.notEmpty && value != null && !notEmpty(value)) {
            message = rule.message == null ? "不能为空" : rule.message;
            code = "notEmpty";
            fieldErrorList.add(new ValidFieldError(filed, value, message, code));
            if (fast) {
                return fieldErrorList;
            }
        }
        // 值必须等于给定值
        if (rule.equals != null && value != null && !Objects.equals(rule.equals, value)) {
            message = rule.message == null ? String.format("必须等于“%s”", rule.equals) : rule.message;
            code = "equals";
            fieldErrorList.add(new ValidFieldError(filed, value, message, code));
            if (fast) {
                return fieldErrorList;
            }
        }
        // 值必须在给定值列表内
        if (rule.equalsIn != null && value != null && !rule.equalsIn.contains(value)) {
            message = rule.message == null ? String.format("必须在%s集合内", rule.equalsIn) : rule.message;
            code = "equalsIn";
            fieldErrorList.add(new ValidFieldError(filed, value, message, code));
            if (fast) {
                return fieldErrorList;
            }
        }
        // 数值或者时间必须在指定的范围内
        if (rule.range != null && value != null && !validRange(filed, value, rule.range)) {
            if (rule.message == null) {
                StringBuilder sb = new StringBuilder("必须");
                if (rule.range.min != null) {
                    sb.append("大于");
                    if (rule.range.inclusiveMin) {
                        sb.append("等于");
                    }
                    sb.append("“").append(rule.range.min).append("”");
                }
                if (rule.range.max != null) {
                    if (sb.length() > 2) {
                        sb.append("，且");
                    }
                    sb.append("小于");
                    if (rule.range.inclusiveMax) {
                        sb.append("等于");
                    }
                    sb.append("“").append(rule.range.max).append("”");
                }
                message = sb.toString();
            } else {
                message = rule.message;
            }
            code = "range";
            fieldErrorList.add(new ValidFieldError(filed, value, message, code));
            if (fast) {
                return fieldErrorList;
            }
        }
        // 非空字符串
        if (rule.notBlank != null && rule.notBlank) {
            boolean success;
            if (value == null) {
                success = false;
            } else if (value instanceof CharSequence) {
                success = StringUtils.isNotBlank((CharSequence) value);
            } else {
                throw new IllegalArgumentException("字段" + filed + "类型(" + valueClass + ")不支持notBlank配置");
            }
            if (!success) {
                message = rule.message == null ? "不能为空" : rule.message;
                code = "notBlank";
                fieldErrorList.add(new ValidFieldError(filed, value, message, code));
                if (fast) {
                    return fieldErrorList;
                }
            }
        }
        // 字符串长度范围
        if (rule.length != null && value != null) {
            boolean success = true;
            if (value instanceof CharSequence) {
                int length = ((CharSequence) value).length();
                // 最小长度
                if (rule.length.min != null && length < rule.length.min) {
                    success = false;
                }
                // 最大长度
                if (success && rule.length.max != null && length > rule.length.max) {
                    success = false;
                }
            } else {
                throw new IllegalArgumentException("字段" + filed + "类型(" + valueClass + ")不支持length配置");
            }
            if (!success) {
                if (rule.message == null) {
                    StringBuilder sb = new StringBuilder("长度需要");
                    if (rule.length.min != null) {
                        sb.append("大于等于").append(rule.length.min);
                    }
                    if (rule.length.max != null) {
                        if (sb.length() > 4) {
                            sb.append("，且");
                        }
                        sb.append("小于等于").append(rule.length.max);
                    }
                    message = sb.toString();
                } else {
                    message = rule.message;
                }
                code = "length";
                fieldErrorList.add(new ValidFieldError(filed, value, message, code));
                if (fast) {
                    return fieldErrorList;
                }
            }
        }
        // 必须符合指定的正则表达式
        if (rule.pattern != null && value != null) {
            boolean success;
            if (value instanceof CharSequence) {
                String str = value.toString();
                success = str.matches(rule.pattern);
            } else {
                throw new IllegalArgumentException("字段" + filed + "类型(" + valueClass + ")不支持pattern配置");
            }
            if (!success) {
                message = rule.message == null ? String.format("需要匹配正则表达式[%s]", rule.pattern) : rule.message;
                code = "pattern";
                fieldErrorList.add(new ValidFieldError(filed, value, message, code));
                if (fast) {
                    return fieldErrorList;
                }
            }
        }
        // 数字位数取值范围
        if (rule.digits != null && value != null) {
            boolean success = true;
            if (value instanceof Number) {
                Number num = (Number) value;
                BigDecimal bigNum;
                if (num instanceof BigDecimal) {
                    bigNum = (BigDecimal) num;
                } else {
                    bigNum = new BigDecimal(num.toString()).stripTrailingZeros();
                }
                int integerPartLength = bigNum.precision() - bigNum.scale();
                int fractionPartLength = Math.max(bigNum.scale(), 0);
                // 最大整数位数
                if (rule.digits.integer != null && rule.digits.integer < integerPartLength) {
                    success = false;
                }
                // 最大小数位数
                if (success && rule.digits.fraction != null && rule.digits.fraction < fractionPartLength) {
                    success = false;
                }
            } else {
                throw new IllegalArgumentException("字段" + filed + "类型(" + valueClass + ")不支持digits配置");
            }
            if (!success) {
                if (rule.message == null) {
                    StringBuilder sb = new StringBuilder("数字的值超出了允许范围(");
                    if (rule.digits.integer != null) {
                        sb.append("最大整数位数").append(rule.digits.integer);
                    }
                    if (rule.digits.fraction != null) {
                        if (sb.length() > 12) {
                            sb.append("，");
                        }
                        sb.append("最大小数位数").append(rule.digits.fraction);
                    }
                    sb.append(")");
                    message = sb.toString();
                } else {
                    message = rule.message;
                }
                code = "digits";
                fieldErrorList.add(new ValidFieldError(filed, value, message, code));
            }
        }
        // 必须是一个过去的日期
        if (rule.pastDate != null && rule.pastDate && value != null) {
            boolean success;
            if (value instanceof Date) {
                success = new Date().compareTo((Date) value) >= 0;
            } else {
                throw new IllegalArgumentException("字段" + filed + "类型(" + valueClass + ")不支持pastDate配置");
            }
            if (!success) {
                message = rule.message == null ? "需要是一个过去的时间" : rule.message;
                code = "pastDate";
                fieldErrorList.add(new ValidFieldError(filed, value, message, code));
            }
        }
        // 必须是一个将来的日期
        if (rule.futureDate != null && rule.futureDate && value != null) {
            boolean success;
            if (value instanceof Date) {
                success = new Date().compareTo((Date) value) <= 0;
            } else {
                throw new IllegalArgumentException("字段" + filed + "类型(" + valueClass + ")不支持futureDate配置");
            }
            if (!success) {
                message = rule.message == null ? "需要是一个将来的时间" : rule.message;
                code = "futureDate";
                fieldErrorList.add(new ValidFieldError(filed, value, message, code));
            }
        }
        // 数组、集合大小
        if (rule.size != null && value != null && !validSize(filed, value, rule.size)) {
            message = rule.message == null ? "数组或集合元素个数必须在{}-{}内" : rule.message;
            code = "size";
            fieldErrorList.add(new ValidFieldError(filed, value, message, code));
            if (fast) {
                return fieldErrorList;
            }
        }
        // 必须是电子邮箱地址
        if (rule.email != null && rule.email && value != null) {
            boolean success;
            if (value instanceof CharSequence) {
                success = EMAIL.matcher((CharSequence) value).matches();
            } else {
                throw new IllegalArgumentException("字段" + filed + "类型(" + valueClass + ")不支持email配置");
            }
            if (!success) {
                message = rule.message == null ? "不是一个合法的电子邮件地址" : rule.message;
                code = "email";
                fieldErrorList.add(new ValidFieldError(filed, value, message, code));
                if (fast) {
                    return fieldErrorList;
                }
            }
        }
        // 自定义校验(校验失败返回false)
        if (rule.validator != null && value != null) {
            ValidatorContext vc = new ValidatorContext(bean, value, null);
            boolean success = rule.validator.apply(vc);
            if (!success) {
                message = rule.message != null ? rule.message : vc.message == null ? "自定义校验失败" : vc.message;
                code = "validator";
                fieldErrorList.add(new ValidFieldError(filed, value, message, code));
                if (fast) {
                    return fieldErrorList;
                }
            }
        }
        return fieldErrorList;
    }

    @SuppressWarnings("rawtypes")
    protected boolean validSize(String filed, Object value, RuleItemSize size) {
        if (value == null) {
            return true;
        }
        if (size.min == null && size.max == null) {
            return true;
        }
        final String valueClass = value.getClass().getName();
        int sizeInt;
        if (value instanceof Collection) {
            sizeInt = ((Collection) value).size();
        } else if (value instanceof Map) {
            sizeInt = ((Map) value).size();
        } else if (value instanceof char[]) {
            sizeInt = ((char[]) value).length;
        } else if (value instanceof byte[]) {
            sizeInt = ((byte[]) value).length;
        } else if (value instanceof short[]) {
            sizeInt = ((short[]) value).length;
        } else if (value instanceof int[]) {
            sizeInt = ((int[]) value).length;
        } else if (value instanceof float[]) {
            sizeInt = ((float[]) value).length;
        } else if (value instanceof double[]) {
            sizeInt = ((double[]) value).length;
        } else if (value instanceof long[]) {
            sizeInt = ((long[]) value).length;
        } else if (value instanceof boolean[]) {
            sizeInt = ((boolean[]) value).length;
        } else if (value.getClass().isArray()) {
            sizeInt = ((Object[]) value).length;
        } else {
            throw new IllegalArgumentException("字段" + filed + "类型(" + valueClass + ")不支持size配置");
        }
        // 最小元素数量
        if (size.min != null && sizeInt < size.min) {
            return false;
        }
        // 最大元素数量
        return size.max == null || sizeInt <= size.max;
    }

    @SuppressWarnings({"DuplicatedCode", "rawtypes", "unchecked"})
    protected boolean validRange(String filed, Object value, RuleItemRange range) {
        if (value == null) {
            return true;
        }
        final Object min = range.min;
        final Object max = range.max;
        if (min == null && max == null) {
            return true;
        }
        final String valueClass = value.getClass().getName();
        final String minClass = min == null ? "" : min.getClass().getName();
        final String maxClass = max == null ? "" : max.getClass().getName();
        if (value instanceof Byte || value instanceof Short || value instanceof Integer || value instanceof Long) {
            long v1 = ((Number) value).longValue();
            // 最小值
            if (min instanceof Byte || min instanceof Short || min instanceof Integer || min instanceof Long) {
                long v2 = ((Number) min).longValue();
                if (range.inclusiveMin) {
                    // 大于等于最小值
                    if (v1 < v2) {
                        return false;
                    }
                } else {
                    // 大于最小值
                    if (v1 <= v2) {
                        return false;
                    }
                }
            } else if (min != null) {
                throw new IllegalArgumentException("字段" + filed + "类型(" + valueClass + ")与配置range.min值类型(" + minClass + ")不一致");
            }
            // 最大值
            if (max instanceof Byte || max instanceof Short || max instanceof Integer || max instanceof Long) {
                long v2 = ((Number) max).longValue();
                if (range.inclusiveMax) {
                    // 小于等于最大值
                    return v1 <= v2;
                } else {
                    // 小于最大值
                    return v1 < v2;
                }
            } else if (max != null) {
                throw new IllegalArgumentException("字段" + filed + "类型(" + valueClass + ")与配置range.max值类型(" + maxClass + ")不一致");
            }
        } else if (value instanceof Float || value instanceof Double) {
            double v1 = ((Number) value).doubleValue();
            // 最小值
            if (min instanceof Float || min instanceof Double) {
                double v2 = ((Number) min).doubleValue();
                if (range.inclusiveMin) {
                    // 大于等于最小值
                    if (v1 < v2) {
                        return false;
                    }
                } else {
                    // 大于最小值
                    if (v1 <= v2) {
                        return false;
                    }
                }
            } else if (min != null) {
                throw new IllegalArgumentException("字段" + filed + "类型(" + valueClass + ")与配置range.min值类型(" + minClass + ")不一致");
            }
            // 最大值
            if (max instanceof Float || max instanceof Double) {
                double v2 = ((Number) max).doubleValue();
                if (range.inclusiveMax) {
                    // 小于等于最大值
                    return v1 <= v2;
                } else {
                    // 小于最大值
                    return v1 < v2;
                }
            } else if (max != null) {
                throw new IllegalArgumentException("字段" + filed + "类型(" + valueClass + ")与配置range.max值类型(" + maxClass + ")不一致");
            }
        } else if (value instanceof BigDecimal) {
            BigDecimal v1 = (BigDecimal) value;
            // 最小值
            if (min instanceof BigDecimal) {
                BigDecimal v2 = (BigDecimal) min;
                if (range.inclusiveMin) {
                    // 大于等于最小值
                    if (v1.compareTo(v2) < 0) {
                        return false;
                    }
                } else {
                    // 大于最小值
                    if (v1.compareTo(v2) <= 0) {
                        return false;
                    }
                }
            } else if (min != null) {
                throw new IllegalArgumentException("字段" + filed + "类型(" + valueClass + ")与配置range.min值类型(" + minClass + ")不一致");
            }
            // 最大值
            if (max instanceof BigDecimal) {
                BigDecimal v2 = (BigDecimal) max;
                if (range.inclusiveMax) {
                    // 小于等于最大值
                    return v1.compareTo(v2) <= 0;
                } else {
                    // 小于最大值
                    return v1.compareTo(v2) < 0;
                }
            } else if (max != null) {
                throw new IllegalArgumentException("字段" + filed + "类型(" + valueClass + ")与配置range.max值类型(" + maxClass + ")不一致");
            }
        } else if (value instanceof BigInteger) {
            BigInteger v1 = (BigInteger) value;
            // 最小值
            if (min instanceof BigInteger) {
                BigInteger v2 = (BigInteger) min;
                if (range.inclusiveMin) {
                    // 大于等于最小值
                    if (v1.compareTo(v2) < 0) {
                        return false;
                    }
                } else {
                    // 大于最小值
                    if (v1.compareTo(v2) <= 0) {
                        return false;
                    }
                }
            } else if (min != null) {
                throw new IllegalArgumentException("字段" + filed + "类型(" + valueClass + ")与配置range.min值类型(" + minClass + ")不一致");
            }
            // 最大值
            if (max instanceof BigInteger) {
                BigInteger v2 = (BigInteger) max;
                if (range.inclusiveMax) {
                    // 小于等于最大值
                    return v1.compareTo(v2) <= 0;
                } else {
                    // 小于最大值
                    return v1.compareTo(v2) < 0;
                }
            } else if (max != null) {
                throw new IllegalArgumentException("字段" + filed + "类型(" + valueClass + ")与配置range.max值类型(" + maxClass + ")不一致");
            }
        } else if (value instanceof Date) {
            Date v1 = (Date) value;
            // 最小值
            if (min instanceof Date) {
                Date v2 = (Date) min;
                if (range.inclusiveMin) {
                    // 大于等于最小值
                    if (v1.compareTo(v2) < 0) {
                        return false;
                    }
                } else {
                    // 大于最小值
                    if (v1.compareTo(v2) <= 0) {
                        return false;
                    }
                }
            } else if (min != null) {
                throw new IllegalArgumentException("字段" + filed + "类型(" + valueClass + ")与配置range.min值类型(" + minClass + ")不一致");
            }
            // 最大值
            if (max instanceof Date) {
                Date v2 = (Date) max;
                if (range.inclusiveMax) {
                    // 小于等于最大值
                    return v1.compareTo(v2) <= 0;
                } else {
                    // 小于最大值
                    return v1.compareTo(v2) < 0;
                }
            } else if (max != null) {
                throw new IllegalArgumentException("字段" + filed + "类型(" + valueClass + ")与配置range.max值类型(" + maxClass + ")不一致");
            }
        } else if (value instanceof Comparable) {
            Comparable v1 = (Comparable) value;
            // 最小值
            if (min != null && !Objects.equals(valueClass, minClass)) {
                throw new IllegalArgumentException("字段" + filed + "类型(" + valueClass + ")与配置range.min值类型(" + minClass + ")不一致");
            } else if (min != null) {
                Comparable v2 = (Comparable) min;
                if (range.inclusiveMin) {
                    // 大于等于最小值
                    if (v1.compareTo(v2) < 0) {
                        return false;
                    }
                } else {
                    // 大于最小值
                    if (v1.compareTo(v2) <= 0) {
                        return false;
                    }
                }
            }
            // 最大值
            if (max != null && !Objects.equals(valueClass, maxClass)) {
                throw new IllegalArgumentException("字段" + filed + "类型(" + valueClass + ")与配置range.max值类型(" + maxClass + ")不一致");
            } else if (max != null) {
                Comparable v2 = (Comparable) max;
                if (range.inclusiveMax) {
                    // 小于等于最大值
                    return v1.compareTo(v2) <= 0;
                } else {
                    // 小于最大值
                    return v1.compareTo(v2) < 0;
                }
            }
        }
        return true;
    }

    @SuppressWarnings("rawtypes")
    protected boolean notEmpty(final Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof CharSequence) {
            return StringUtils.isNotBlank((CharSequence) obj);
        } else if (obj instanceof Collection) {
            return !((Collection) obj).isEmpty();
        } else if (obj instanceof Map) {
            return !((Map) obj).isEmpty();
        } else if (obj instanceof char[]) {
            return ((char[]) obj).length > 0;
        } else if (obj instanceof byte[]) {
            return ((byte[]) obj).length > 0;
        } else if (obj instanceof short[]) {
            return ((short[]) obj).length > 0;
        } else if (obj instanceof int[]) {
            return ((int[]) obj).length > 0;
        } else if (obj instanceof float[]) {
            return ((float[]) obj).length > 0;
        } else if (obj instanceof double[]) {
            return ((double[]) obj).length > 0;
        } else if (obj instanceof long[]) {
            return ((long[]) obj).length > 0;
        } else if (obj instanceof boolean[]) {
            return ((boolean[]) obj).length > 0;
        } else if (obj.getClass().isArray()) {
            return ((Object[]) obj).length > 0;
        }
        return true;
    }

    @Data
    public static class ValidResult implements Serializable {
        /**
         * 是否存在数据错误
         */
        private boolean error;
        /**
         * 数据错误信息
         */
        private List<ValidFieldError> errors = new ArrayList<>();

        public ValidResult(boolean error) {
            this.error = error;
        }

        /**
         * 是否存在数据错误
         */
        public boolean hasError() {
            return error;
        }
    }

    @Data
    public static class ValidFieldError implements Serializable {
        /**
         * 验证的字段名
         */
        private String filed;
        /**
         * 验证的字段值
         */
        private Object value;
        /**
         * 验证的错误消息
         */
        private String message;
        /**
         * 验证所使用的验证配置(如：isNull、notEmpty、notBlank、range.min)
         */
        private String code;

        public ValidFieldError(String filed, Object value, String message, String code) {
            this.filed = filed;
            this.value = value;
            this.message = message;
            this.code = code;
        }
    }

    @Data
    public static class RuleItemRange implements Serializable {
        /**
         * 数值或者时间的最小值 <br />
         * JByte | JShort | JInt | JLong | JFloat | JDouble | JBigDecimal | JBigInteger | JDate | Date;
         */
        private Object min;
        /**
         * 数值或者时间的最大值 <br />
         * JByte | JShort | JInt | JLong | JFloat | JDouble | JBigDecimal | JBigInteger | JDate | Date
         */
        private Object max;
        /**
         * 是否包含最小值(默认：true)
         */
        private boolean inclusiveMin = true;
        /**
         * 是否包含最大值(默认：true)
         */
        private boolean inclusiveMax = true;
    }

    @Data
    public static class RuleItemLength implements Serializable {
        /**
         * 最小长度
         */
        private Integer min;
        /**
         * 最大长度
         */
        private Integer max;
    }

    @Data
    public static class RuleItemDigits implements Serializable {
        /**
         * 最大整数位数
         */
        private Integer integer;
        /**
         * 最大小数位数
         */
        private Integer fraction;
    }

    @Data
    public static class RuleItemSize implements Serializable {
        /**
         * 最小元素数量
         */
        private Integer min;
        /**
         * 最大元素数量
         */
        private Integer max;
    }

    public static class ValidatorContext implements Serializable {
        /**
         * 当前验证的对象
         */
        public Map<String, Object> bean;
        /**
         * 当前验证的字段值
         */
        public Object value;
        /**
         * 验证失败时设置的错误消息
         */
        public String message;

        public ValidatorContext(Map<String, Object> bean, Object value, String message) {
            this.bean = bean;
            this.value = value;
            this.message = message;
        }
    }

    @Data
    public static class ValidatorRuleItem implements Serializable {
        /**
         * 错误信息
         */
        private String message;
        // ------------------------------------------------------------------------------------------- 常规
        /**
         * 必须为 null
         */
        private Boolean isNull;
        /**
         * 必须不为 null
         */
        private Boolean notNull;
        /**
         * 必须不为null，且满足：非空字符串、非空集合、非空数组
         */
        private Boolean notEmpty;
        /**
         * 值必须等于给定值
         */
        private Object equals;
        /**
         * 值必须在给定值列表内
         */
        private Set<Object> equalsIn;
        /**
         * 数值或者时间必须在指定的范围内
         */
        private RuleItemRange range;
        // ------------------------------------------------------------------------------------------- 字符串
        /**
         * 非空字符串
         */
        private Boolean notBlank;
        /**
         * 字符串长度范围
         */
        private RuleItemLength length;
        /**
         * 必须符合指定的正则表达式
         */
        private String pattern;
        // ------------------------------------------------------------------------------------------- 数字
        /**
         * 数字位数取值范围
         */
        private RuleItemDigits digits;
        // ------------------------------------------------------------------------------------------- 时间
        /**
         * 必须是一个过去的日期
         */
        private Boolean pastDate;
        /**
         * 必须是一个将来的日期
         */
        private Boolean futureDate;
        // ------------------------------------------------------------------------------------------- 集合
        /**
         * 数组、集合大小
         */
        private RuleItemSize size;
        // ------------------------------------------------------------------------------------------- 特定场景
        /**
         * 必须是电子邮箱地址
         */
        private Boolean email;
        // ------------------------------------------------------------------------------------------- 自定义校验
        /**
         * 自定义校验(校验失败返回false)
         */
        private Function<ValidatorContext, Boolean> validator;
    }
}
