interface AssertUtils {
    /**
     * 断言表达式的值是 true
     */
    isTrue(expression: JBoolean, message: JString): void;

    /**
     * 断言表达式的值是 false
     */
    isFalse(expression: JBoolean, message: JString): void;

    /**
     * 断言object是null值
     */
    isNull(object: any, message: JString): void;

    /**
     * 断言object非null
     */
    notNull(object: any, String: JString): void;

    /**
     * 断言给定字符串不为空；也就是说，它不能为null，也不能为空字符串
     */
    hasLength(text: JString, message: JString): void;

    /**
     * 断言给定的字符串包含有效的文本内容；也就是说，它不能为null，并且必须至少包含一个非空白字符
     */
    hasText(text: JString, message: JString): void;

    /**
     * 断言给定的文本不包含给定的子字符串
     */
    doesNotContain(textToSearch: JString, substring: JString, message: JString): void;

    /**
     * 断言数组包含元素；也就是说，它不能为null并且必须至少包含一个元素
     */
    notEmpty(array: any[], message: JString): void;

    /**
     * 断言集合包含元素；也就是说，它不能为null并且必须至少包含一个元素
     */
    notEmpty(collection: JCollection<any>, message: JString): void;

    /**
     * 断言数组不包含null元素，注意：如果数组为空，则不会抛出异常
     */
    noNullElements(array: any[], message: JString): void;

    /**
     * 断言集合不包含null元素，注意：如果集合为空，则不会抛出异常
     */
    noNullElements(collection: JCollection<any>, message: JString): void;
}

declare const AssertUtils: AssertUtils;
