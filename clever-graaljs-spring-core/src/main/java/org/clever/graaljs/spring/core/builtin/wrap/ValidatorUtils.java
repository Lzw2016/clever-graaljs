package org.clever.graaljs.spring.core.builtin.wrap;

import org.clever.graaljs.core.internal.utils.InteropScriptToJavaUtils;
import org.graalvm.polyglot.Value;
import org.springframework.validation.BindException;

import java.util.*;
import java.util.function.Function;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/09/05 19:26 <br/>
 */
public class ValidatorUtils {
    public static final ValidatorUtils Instance = new ValidatorUtils();

    private final org.clever.graaljs.spring.core.builtin.adapter.ValidatorUtils delegate;

    private ValidatorUtils() {
        delegate = org.clever.graaljs.spring.core.builtin.adapter.ValidatorUtils.Instance;
    }

    /**
     * 对象数据校验(数据校验错误抛出异常)
     *
     * @param bean 数据对象
     * @param rule 校验规则
     * @param fast 快速验证(只要有一个错误就返回)
     */
    public void validated(final Map<String, Object> bean, final Map<String, Object> rule, final boolean fast) throws BindException {
        Map<String, Object> ruleMap = new HashMap<>();
        if (bean != null && rule != null) {
            convertRule(bean, rule, ruleMap);
        }
        delegate.validated(bean, ruleMap, fast);
    }

    /**
     * 对象数据校验(数据校验错误抛出异常)
     *
     * @param bean 数据对象
     * @param rule 校验规则
     */
    public void validated(final Map<String, Object> bean, final Map<String, Object> rule) throws BindException {
        Map<String, Object> ruleMap = new HashMap<>();
        if (bean != null && rule != null) {
            convertRule(bean, rule, ruleMap);
        }
        delegate.validated(bean, ruleMap);
    }

    /**
     * 对象数据校验(返回验证结果)
     *
     * @param bean 数据对象
     * @param rule 校验规则
     * @param fast 快速验证(只要有一个错误就返回)
     */
    public org.clever.graaljs.spring.core.builtin.adapter.ValidatorUtils.ValidResult valid(Map<String, Object> bean, Map<String, Object> rule, boolean fast) {
        Map<String, Object> ruleMap = new HashMap<>();
        if (bean != null && rule != null) {
            convertRule(bean, rule, ruleMap);
        }
        return delegate.valid(bean, ruleMap, fast);
    }

    /**
     * 对象数据校验(返回验证结果)
     *
     * @param bean 数据对象
     * @param rule 校验规则
     */
    public org.clever.graaljs.spring.core.builtin.adapter.ValidatorUtils.ValidResult valid(Map<String, Object> bean, Map<String, Object> rule) {
        Map<String, Object> ruleMap = new HashMap<>();
        if (bean != null && rule != null) {
            convertRule(bean, rule, ruleMap);
        }
        return delegate.valid(bean, ruleMap);
    }

    @SuppressWarnings({"unchecked"})
    protected void convertRule(Map<String, Object> bean, Map<String, Object> rule, Map<String, Object> ruleMap) {
        for (Map.Entry<String, Object> entry : rule.entrySet()) {
            final String filed = entry.getKey();                // 字段名
            final Object ruleItem = entry.getValue();           // 校验配置
            final Object value = bean.get(filed);               // 字段值
            // final boolean hasValue = bean.containsKey(filed);// 是否存在字段值
            if (ruleItem == null) {
                continue;
            }
            if (!(ruleItem instanceof Map)) {
                throw new IllegalArgumentException("字段" + filed + "校验配置错误");
            }
            // 字段值属于基本类型
            if (!(value instanceof Map)) {
                org.clever.graaljs.spring.core.builtin.adapter.ValidatorUtils.ValidatorRuleItem validatorRuleItem = doConvertRule((Map<String, Object>) ruleItem);
                ruleMap.put(filed, validatorRuleItem);
                continue;
            }
            // 字段值是Map类型 - 存在校验标识字段
            if (isRuleConfig((Map<String, Object>) ruleItem)) {
                org.clever.graaljs.spring.core.builtin.adapter.ValidatorUtils.ValidatorRuleItem validatorRuleItem = doConvertRule((Map<String, Object>) ruleItem);
                ruleMap.put(filed, validatorRuleItem);
                continue;
            }
            // 字段值是Map类型 - 需要递归
            Map<String, Object> deepRuleMap = new HashMap<>();
            ruleMap.put(filed, deepRuleMap);
            convertRule((Map<String, Object>) value, (Map<String, Object>) ruleItem, deepRuleMap);
        }
    }

    /**
     * 判断数据是否是校验配置
     */
    protected boolean isRuleConfig(Map<String, Object> ruleItem) {
        return ruleItem.containsKey("__valid_flag");
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected org.clever.graaljs.spring.core.builtin.adapter.ValidatorUtils.ValidatorRuleItem doConvertRule(Map<String, Object> ruleItem) {
        org.clever.graaljs.spring.core.builtin.adapter.ValidatorUtils.ValidatorRuleItem validatorRuleItem = new org.clever.graaljs.spring.core.builtin.adapter.ValidatorUtils.ValidatorRuleItem();
        Object message = ruleItem.get("message");
        if (message instanceof CharSequence) {
            validatorRuleItem.setMessage(message.toString());
        }
        Object isNull = ruleItem.get("isNull");
        if (isNull instanceof Boolean) {
            validatorRuleItem.setIsNull((Boolean) isNull);
        }
        Object notNull = ruleItem.get("notNull");
        if (notNull instanceof Boolean) {
            validatorRuleItem.setNotNull((Boolean) notNull);
        }
        Object notEmpty = ruleItem.get("notEmpty");
        if (notEmpty instanceof Boolean) {
            validatorRuleItem.setNotEmpty((Boolean) notEmpty);
        }
        Object equals = ruleItem.get("equals");
        validatorRuleItem.setEquals(equals);

        Object equalsIn = ruleItem.get("equalsIn");
        if (equalsIn instanceof Collection) {
            validatorRuleItem.setEqualsIn(new HashSet<Object>((Collection) equalsIn));
        } else if (equalsIn instanceof Map) {
            Value arrayTmp = Value.asValue(equalsIn);
            if (arrayTmp.hasArrayElements()) {
                Set<Object> equalsInTmp = new HashSet<>((int) arrayTmp.getArraySize());
                for (int i = 0; i < arrayTmp.getArraySize(); i++) {
                    Value item = arrayTmp.getArrayElement(i);
                    equalsInTmp.add(InteropScriptToJavaUtils.Instance.toJavaObject(item));
                }
                validatorRuleItem.setEqualsIn(equalsInTmp);
            }
        }
        Object range = ruleItem.get("range");
        if (range instanceof Map) {
            validatorRuleItem.setRange(convertRange((Map<String, Object>) range));
        }
        Object notBlank = ruleItem.get("notBlank");
        if (notBlank instanceof Boolean) {
            validatorRuleItem.setNotBlank((Boolean) notBlank);
        }
        Object length = ruleItem.get("length");
        if (length instanceof Map) {
            validatorRuleItem.setLength(convertLength((Map<String, Object>) length));
        }
        Object pattern = ruleItem.get("pattern");
        if (pattern instanceof CharSequence) {
            validatorRuleItem.setPattern(pattern.toString());
        }
        Object digits = ruleItem.get("digits");
        if (digits instanceof Map) {
            validatorRuleItem.setDigits(convertDigits((Map<String, Object>) digits));
        }
        Object pastDate = ruleItem.get("pastDate");
        if (pastDate instanceof Boolean) {
            validatorRuleItem.setPastDate((Boolean) pastDate);
        }
        Object futureDate = ruleItem.get("futureDate");
        if (futureDate instanceof Boolean) {
            validatorRuleItem.setFutureDate((Boolean) futureDate);
        }
        Object size = ruleItem.get("size");
        if (size instanceof Map) {
            validatorRuleItem.setSize(convertSize((Map<String, Object>) size));
        }
        Object email = ruleItem.get("email");
        if (email instanceof Boolean) {
            validatorRuleItem.setEmail((Boolean) email);
        }
        Object validator = ruleItem.get("validator");
        if (validator != null && validator.getClass().getName().startsWith("com.oracle.truffle.polyglot.")) {
            Value fucTmp = Value.asValue(validator);
            if (fucTmp.canExecute()) {
                Function<org.clever.graaljs.spring.core.builtin.adapter.ValidatorUtils.ValidatorContext, Boolean> validatorFuc = context -> {
                    Value success = fucTmp.execute(context);
                    if (success.isBoolean()) {
                        return success.asBoolean();
                    }
                    return true;
                };
                validatorRuleItem.setValidator(validatorFuc);
            }
        }
        return validatorRuleItem;
    }

    protected org.clever.graaljs.spring.core.builtin.adapter.ValidatorUtils.RuleItemRange convertRange(Map<String, Object> rangeMap) {
        org.clever.graaljs.spring.core.builtin.adapter.ValidatorUtils.RuleItemRange range = new org.clever.graaljs.spring.core.builtin.adapter.ValidatorUtils.RuleItemRange();
        Object min = rangeMap.get("min");
        if (min != null) {
            range.setMin(min);
        }
        Object max = rangeMap.get("max");
        if (max != null) {
            range.setMax(max);
        }
        Object inclusiveMin = rangeMap.get("inclusiveMin");
        if (inclusiveMin instanceof Boolean) {
            range.setInclusiveMin((Boolean) inclusiveMin);
        }
        Object inclusiveMax = rangeMap.get("inclusiveMax");
        if (inclusiveMax instanceof Boolean) {
            range.setInclusiveMax((Boolean) inclusiveMax);
        }
        return range;
    }

    @SuppressWarnings("DuplicatedCode")
    protected org.clever.graaljs.spring.core.builtin.adapter.ValidatorUtils.RuleItemLength convertLength(Map<String, Object> lengthMap) {
        org.clever.graaljs.spring.core.builtin.adapter.ValidatorUtils.RuleItemLength length = new org.clever.graaljs.spring.core.builtin.adapter.ValidatorUtils.RuleItemLength();
        Object min = lengthMap.get("min");
        if (min instanceof Number) {
            length.setMin(((Number) min).intValue());
        }
        Object max = lengthMap.get("max");
        if (max instanceof Number) {
            length.setMax(((Number) max).intValue());
        }
        return length;
    }

    protected org.clever.graaljs.spring.core.builtin.adapter.ValidatorUtils.RuleItemDigits convertDigits(Map<String, Object> digitsMap) {
        org.clever.graaljs.spring.core.builtin.adapter.ValidatorUtils.RuleItemDigits digits = new org.clever.graaljs.spring.core.builtin.adapter.ValidatorUtils.RuleItemDigits();
        Object integer = digitsMap.get("integer");
        if (integer instanceof Number) {
            digits.setInteger(((Number) integer).intValue());
        }
        Object fraction = digitsMap.get("fraction");
        if (fraction instanceof Number) {
            digits.setFraction(((Number) fraction).intValue());
        }
        return digits;
    }

    @SuppressWarnings("DuplicatedCode")
    protected org.clever.graaljs.spring.core.builtin.adapter.ValidatorUtils.RuleItemSize convertSize(Map<String, Object> sizeMap) {
        org.clever.graaljs.spring.core.builtin.adapter.ValidatorUtils.RuleItemSize size = new org.clever.graaljs.spring.core.builtin.adapter.ValidatorUtils.RuleItemSize();
        Object min = sizeMap.get("min");
        if (min instanceof Number) {
            size.setMin(((Number) min).intValue());
        }
        Object max = sizeMap.get("max");
        if (max instanceof Number) {
            size.setMax(((Number) max).intValue());
        }
        return size;
    }
}
