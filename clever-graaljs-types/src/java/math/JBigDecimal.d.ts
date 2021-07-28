/**
 * java.math.BigDecimal 类型，不要在JavaScript/TypeScript环境中创建该类型对象<br />
 * 请使用内置对象“Interop”创建该类型实例<br />
 * @see Interop
 */
interface JBigDecimal extends JObject, JComparable<JBigDecimal> {
    java_math_BigDecimal: "java.math.BigDecimal";

    /**
     * 返回BigDecimal，其值是(this + augend) <br />
     * 其标度为 max(this.scale(), augend.scale())
     */
    add(augend: JBigDecimal): JBigDecimal;

    /**
     * 返回 BigDecimal ，其值是 (this - subtrahend) <br />
     * 其标度为 max(this.scale(), subtrahend.scale())
     */
    subtract(subtrahend: JBigDecimal): JBigDecimal;

    /**
     * 返回 BigDecimal ，其值是 (this × multiplicand) <br />
     * 其标度为 (this.scale() + multiplicand.scale())
     */
    multiply(multiplicand: JBigDecimal): JBigDecimal;

    /**
     * 返回BigDecimal，其值为(this / divisor) <br />
     * 标度优先级为(this.scale() - divisor.scale()) <br />
     * 如果不能表示确切的商（因为它具有非终止的十进制扩展），则抛出一个ArithmeticException
     */
    divide(divisor: JBigDecimal): JBigDecimal;

    /**
     * 返回BigDecimal ，其值是(this / divisor) <br />
     * 其标度为this.scale()
     */
    divide(divisor: JBigDecimal, roundingMode: JRoundingMode): JBigDecimal;

    /**
     * 返回一个 BigDecimal ，其值为 (this / divisor)
     */
    divide(divisor: JBigDecimal, scale: JInt, roundingMode: JRoundingMode): JBigDecimal;

    /**
     * 返回 BigDecimal，其值为 (this / divisor) 的整数部分
     */
    divideToIntegralValue(divisor: JBigDecimal): JBigDecimal;

    /**
     * 返回其值为 (this % divisor) 的 BigDecimal
     */
    remainder(divisor: JBigDecimal): JBigDecimal;

    /**
     * 返回由两个元素组成的 BigDecimal 数组，该数组包含 divideToIntegralValue 的结果 和 对两个操作数计算所得到的 remainder <br />
     * 注意，如果同时需要整数商和余数，则此方法比分别使用 divideToIntegralValue 和 remainder 方法更快速，因为相除仅需执行一次
     */
    divideAndRemainder(divisor: JBigDecimal): JBigDecimal[];

    /**
     * 将此 BigDecimal转换为 double
     */
    doubleValue(): JDouble;

    /**
     * 将此 BigDecimal转换为 float
     */
    floatValue(): JFloat;

    /**
     * 将此 BigDecimal转换为 long
     */
    longValue(): JLong;

    /**
     * 将此 BigDecimal转换为 long，以检查丢失的信息 <br />
     * 如果此 BigDecimal 具有非零小数部分，则抛出一个异常
     */
    longValueExact(): JLong;

    /**
     * 将此 BigDecimal转换为 int
     */
    intValue(): JInt;

    /**
     * 将此 BigDecimal 转换为 int，以检查丢失的信息 <br />
     * 如果此 BigDecimal 具有非零小数部分，则抛出一个异常
     */
    intValueExact(): JInt;

    /**
     * 将此 BigDecimal 转换为 short
     */
    shortValue(): JShort;

    /**
     * 将此 BigDecimal 转换为 short，以检查丢失的信息 <br />
     * 如果此 BigDecimal 具有非零小数部分，或者超出 short 结果的可能范围，则抛出 ArithmeticException
     */
    shortValueExact(): JShort;

    /**
     * 将此 BigDecimal转换为 BigInteger
     */
    toBigInteger(): JBigInteger;

    /**
     * 将此 BigDecimal 转换为 BigInteger，以检查丢失的信息 <br />
     * 如果此 BigDecimal 具有非零小数部分，则抛出一个异常
     */
    toBigIntegerExact(): JBigInteger;

    /**
     * 返回一个 BigInteger ，其值是此 BigDecimal的 未缩放值
     */
    unscaledValue(): JBigInteger;

    /**
     * 返回此 BigDecimal 的精度
     */
    precision(): JInt;

    /**
     * 返回此 BigDecimal 的标度
     */
    scale(): JInt;

    /**
     * 返回一个 BigDecimal，其标度为指定值，其值在数值上等于此 BigDecimal 的值
     */
    setScale(newScale: JInt): JBigDecimal;

    /**
     * 返回一个 BigDecimal，其标度为指定值，其非标度值通过此 BigDecimal 的非标度值乘以或除以十的适当次幂来确定，以维护其总值
     */
    setScale(newScale: JInt, roundingMode: JRoundingMode): JBigDecimal;

    /**
     * 此方法返回-1，0，或1，此BigDecimal的值分类为负，零或正值
     */
    signum(): JBigDecimal;

    /**
     * 返回此 BigDecimal的最后一个位置的ulp（一个单位）的大小
     */
    ulp(): JBigDecimal;

    /**
     * 返回此 BigDecimal 的字符串表示形式，需要指数时，则使用工程计数法
     */
    toEngineeringString(): JString;

    /**
     * 返回不带指数字段的此 BigDecimal 的字符串表示形式
     */
    toPlainString(): JString;

    /**
     * 用于去除末尾多余的0的
     */
    stripTrailingZeros(): JBigDecimal;

    /**
     * 返回一个 BigDecimal ，其值为此 BigDecimal的绝对值，其缩放比例为 this.scale()
     */
    abs(): JBigDecimal;

    /**
     * 返回最大值
     */
    max(val: JBigDecimal): JBigDecimal;

    /**
     * 返回最小值
     */
    min(val: JBigDecimal): JBigDecimal;

    /**
     *  返回一个 BigDecimal，它等效于将该值的小数点向左移动 n 位
     */
    movePointLeft(n: JInt): JBigDecimal;

    /**
     * 返回一个 BigDecimal，它等效于将该值的小数点向右移动 n 位
     */
    movePointRight(n: JInt): JBigDecimal;

    /**
     * 返回 BigDecimal，其值为 (-this)，其标度为 this.scale()
     */
    negate(): JBigDecimal;

    /**
     * 返回 BigDecimal，其值为 (+this)，其标度为 this.scale()
     */
    plus(): JBigDecimal;

    /**
     * 返回其值为 (this^n) 的 BigDecimal，准确计算该幂，使其具有无限精度
     */
    pow(n: JInt): JBigDecimal;
}
