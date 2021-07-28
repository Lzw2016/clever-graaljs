/**
 * 基础类型
 */
type BaseType = undefined | null | Date
    | JByte | JShort | JInt | JLong | JFloat | JDouble | JBoolean | JChar | JString
    | JBigDecimal | JBigInteger | JDate | JCharSequence;

/**
 * 验证的实体类型
 */
interface ValidatorBean {
    [name: string]: BaseType | JCollection<BaseType> | JMap<JString, BaseType> | ValidatorBean;
}

/**
 * 自定义验证的回调函数参数类型
 */
interface ValidatorContext<T, V> {
    /** 当前验证的对象 */
    readonly bean: T;
    /** 当前验证的字段值 */
    readonly value: V;
    /** 验证失败时设置的错误消息 */
    message: JString;
}

/**
 * 校验项
 */
interface ValidatorRuleItem<T, V> {
    /** 校验配置标识(校验JMap类型的字段值时需要设置此标识) */
    __valid_flag?: true;
    /** 错误信息 */
    message?: JString;
    // ------------------------------------------------------------------------------------------- 常规
    /** 必须为 null */
    isNull?: true;
    /** 必须不为 null */
    notNull?: true;
    /** 可以为null，但需要满足：非空字符串、非空集合、非空数组 */
    notEmpty?: true;
    /** 值必须等于给定值 */
    equals?: any;
    /** 值必须在给定值列表内 */
    equalsIn?: any[] | JCollection<any>;
    /** 数值或者时间必须在指定的范围内 */
    range?: {
        /** 数值或者时间的最小值 */
        min?: JByte | JShort | JInt | JLong | JFloat | JDouble | JBigDecimal | JBigInteger | JDate | Date;
        /** 数值或者时间的最大值 */
        max?: JByte | JShort | JInt | JLong | JFloat | JDouble | JBigDecimal | JBigInteger | JDate | Date;
        /** 是否包含最小值(默认：true) */
        inclusiveMin?: boolean;
        /** 是否包含最大值(默认：true) */
        inclusiveMax?: boolean;
    }
    // ------------------------------------------------------------------------------------------- 字符串
    /** 非空字符串 */
    notBlank?: true;
    /** 字符串长度范围 */
    length?: {
        /** 最小长度 */
        min?: JInt;
        /** 最大长度 */
        max?: JInt;
    };
    /*** 必须符合指定的正则表达式 */
    pattern?: JString;
    // ------------------------------------------------------------------------------------------- 数字
    /** 数字位数取值范围 */
    digits?: {
        /** 最大整数位数 */
        integer?: JInt;
        /** 最大小数位数 */
        fraction?: JInt;
    },
    // ------------------------------------------------------------------------------------------- 时间
    /** 必须是一个过去的日期 */
    pastDate?: true;
    /** 必须是一个将来的日期 */
    futureDate?: true;
    // ------------------------------------------------------------------------------------------- 集合
    /** 数组、集合大小 */
    size?: {
        /** 最小元素数量 */
        min?: JInt;
        /** 最大元素数量 */
        max?: JInt;
    };
    // ------------------------------------------------------------------------------------------- 特定场景
    /** 必须是电子邮箱地址 */
    email?: true;
    // ------------------------------------------------------------------------------------------- 自定义校验
    /** 自定义校验(校验失败返回false) */
    validator?: (ctx: ValidatorContext<T, V>) => boolean;
}

/**
 * 校验规则
 */
type ValidatorRule<T extends object = ValidatorBean> = {
    [name in keyof T]?: T[name] extends ValidatorBean ? ValidatorRule<T[name]> : ValidatorRuleItem<T, T[name]>;
}

interface ValidFieldError {
    /** 验证的字段名 */
    getFiled(): JString;

    /** 验证的字段值 */
    getValue(): BaseType;

    /** 验证的错误消息 */
    getMessage(): JString;

    /** 验证所使用的验证配置(如：isNull、notEmpty、notBlank、range.min) */
    getCode(): JString;
}

/**
 * 验证的返回消息
 */
interface ValidResult {
    /** 是否存在数据错误 */
    hasError(): boolean;

    /** 数据错误信息 */
    getErrors(): JList<ValidFieldError>;
}

/**
 * 校验工具类
 */
interface ValidatorUtils {
    /**
     * 对象数据校验(返回验证结果)
     * @param bean 数据对象
     * @param rule 校验规则
     * @param fast 快速验证(只要有一个错误就返回)
     */
    valid<T extends object = ValidatorBean>(bean: T, rule: ValidatorRule<T>, fast: JBoolean): ValidResult;

    /**
     * 对象数据校验(返回验证结果)
     * @param bean 数据对象
     * @param rule 校验规则
     */
    valid<T extends object = ValidatorBean>(bean: T, rule: ValidatorRule<T>): ValidResult;

    /**
     * 对象数据校验(验证通不过则抛出异常)
     * @param bean 数据对象
     * @param rule 校验规则
     * @param fast 快速验证(只要有一个错误就返回)
     */
    validated<T extends object = ValidatorBean>(bean: T, rule: ValidatorRule<T>, fast: JBoolean): void;

    /**
     * 对象数据校验(验证通不过则抛出异常)
     * @param bean 数据对象
     * @param rule 校验规则
     */
    validated<T extends object = ValidatorBean>(bean: T, rule: ValidatorRule<T>): void;
}

declare const ValidatorUtils: ValidatorUtils;
