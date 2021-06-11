package org.clever.graaljs.core.builtin.adapter;

import org.apache.commons.lang3.ObjectUtils;
import org.clever.graaljs.core.utils.Assert;

import java.util.Collection;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/08/08 23:49 <br/>
 */
public class AssertUtils {
    public static final AssertUtils Instance = new AssertUtils();

    private AssertUtils() {
    }

    /**
     * 断言表达式的值是 true
     */
    public void isTrue(boolean expression, String message) {
        Assert.isTrue(expression, message);
    }

    /**
     * 断言表达式的值是 false
     */
    public void isFalse(boolean expression, String message) {
        if (expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言object是null值
     */
    public void isNull(Object object, String message) {
        Assert.isNull(object, message);
    }

    /**
     * 断言object非null
     */
    public void notNull(Object object, String message) {
        Assert.notNull(object, message);
    }

    /**
     * 断言给定字符串不为空；也就是说，它不能为null，也不能为空字符串
     */
    public void hasLength(String text, String message) {
        Assert.hasLength(text, message);
    }

    /**
     * 断言给定的字符串包含有效的文本内容；也就是说，它不能为null，并且必须至少包含一个非空白字符
     */
    public void hasText(String text, String message) {
        Assert.hasText(text, message);
    }

    /**
     * 断言给定的文本不包含给定的子字符串
     */
    public void doesNotContain(String textToSearch, String substring, String message) {
        Assert.doesNotContain(textToSearch, substring, message);
    }

    /**
     * 断言数组包含元素；也就是说，它不能为null并且必须至少包含一个元素
     */
    public void notEmpty(Object[] array, String message) {
        Assert.notEmpty(array, message);
    }

    /**
     * 断言集合包含元素；也就是说，它不能为null并且必须至少包含一个元素
     */
    public void notEmpty(Collection<Object> collection, String message) {
        if (ObjectUtils.isEmpty(collection)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言数组不包含null元素，注意：如果数组为空，则不会抛出异常
     */
    public void noNullElements(Object[] array, String message) {
        Assert.noNullElements(array, message);
    }

    /**
     * 断言集合不包含null元素，注意：如果集合为空，则不会抛出异常
     */
    public void noNullElements(Collection<Object> collection, String message) {
        if (collection != null) {
            for (Object element : collection) {
                if (element == null) {
                    throw new IllegalArgumentException(message);
                }
            }
        }
    }
}
