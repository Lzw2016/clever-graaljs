package org.clever.graaljs.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/19 11:13 <br/>
 */
public class Assert {
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isTrue(boolean expression, Supplier<String> messageSupplier) {
        if (!expression) {
            throw new IllegalArgumentException(nullSafeGet(messageSupplier));
        }
    }

    public static void isFalse(boolean expression, String message) {
        if (expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isFalse(boolean expression, Supplier<String> messageSupplier) {
        if (expression) {
            throw new IllegalArgumentException(nullSafeGet(messageSupplier));
        }
    }

    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isNull(Object object, Supplier<String> messageSupplier) {
        if (object != null) {
            throw new IllegalArgumentException(nullSafeGet(messageSupplier));
        }
    }

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notNull(Object object, Supplier<String> messageSupplier) {
        if (object == null) {
            throw new IllegalArgumentException(nullSafeGet(messageSupplier));
        }
    }

    public static void isNotBlank(String text, String message) {
        if (StringUtils.isBlank(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isNotBlank(String text, Supplier<String> messageSupplier) {
        if (StringUtils.isBlank(text)) {
            throw new IllegalArgumentException(nullSafeGet(messageSupplier));
        }
    }

    public static void isBlank(String text, String message) {
        if (StringUtils.isNotBlank(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isBlank(String text, Supplier<String> messageSupplier) {
        if (StringUtils.isNotBlank(text)) {
            throw new IllegalArgumentException(nullSafeGet(messageSupplier));
        }
    }

    private static String nullSafeGet(Supplier<String> messageSupplier) {
        return (messageSupplier != null ? messageSupplier.get() : null);
    }

    public static void hasLength(String text, String message) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void hasText(String text, String message) {
        if (StringUtils.isBlank(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void doesNotContain(String textToSearch, String substring, String message) {
        if ((textToSearch != null && !textToSearch.isEmpty()) && (substring != null && !substring.isEmpty()) && textToSearch.contains(substring)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(Object[] array, String message) {
        if ((array == null || array.length == 0)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(Collection<Object> collection, String message) {
        if (isEmpty(collection)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void noNullElements(Object[] array, String message) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new IllegalArgumentException(message);
                }
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof Optional) {
            return !((Optional) obj).isPresent();
        }
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }
        if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }
        // else
        return false;
    }
}
