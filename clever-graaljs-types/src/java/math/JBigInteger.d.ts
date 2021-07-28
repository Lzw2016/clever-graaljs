/**
 * java.math.BigInteger 类型，不要在JavaScript/TypeScript环境中创建该类型对象<br />
 * 请使用内置对象“Interop”创建该类型实例<br />
 * @see Interop
 */
interface JBigInteger extends JObject, JComparable<JBigInteger> {
    java_math_BigInteger: "java.math.BigInteger";

    /**
     * 返回其值是此 BigInteger 的绝对值的 BigInteger
     */
    abs(): JBigInteger;

    /**
     * 返回其值为 (this + val) 的 BigInteger
     */
    add(val: JBigInteger): JBigInteger;

    /**
     * 返回其值为 (this & val) 的 BigInteger
     */
    and(val: JBigInteger): JBigInteger;

    /**
     * 返回其值为 (this & ~val) 的 BigInteger
     */
    andNot(val: JBigInteger): JBigInteger;

    /**
     * 返回此 BigInteger 的二进制补码表示形式中与符号不同的位的数量
     */
    bitCount(): JInt;

    /**
     * 返回此 BigInteger 的最小的二进制补码表示形式的位数，不包括符号位
     */
    bitLength(): JInt;

    /**
     * 返回其值与清除了指定位的此 BigInteger 等效的 BigInteger
     */
    clearBit(n: JInt): JBigInteger;

    /**
     * 返回其值为 (this / val) 的 BigInteger
     */
    divide(val: JBigInteger): JBigInteger;

    /**
     * 返回包含 (this / val) 后跟 (this % val) 的两个 BigInteger 的数组
     */
    divideAndRemainder(val: JBigInteger): JBigInteger[];

    /**
     * 将此 BigInteger 转换为 double
     */
    doubleValue(): JBigInteger;

    /**
     * 返回其值与对此 BigInteger 进行指定位翻转后的值等效的 BigInteger
     */
    flipBit(n: JInt): JBigInteger;

    /**
     * 将此 BigInteger 转换为 float
     */
    floatValue(): JFloat;

    /**
     * 返回一个 BigInteger，其值是 abs(this) 和 abs(val) 的最大公约数
     */
    gcd(val: JBigInteger): JBigInteger;

    /**
     * 返回此 BigInteger 最右端（最低位）1 比特的索引（即从此字节的右端开始到本字节中最右端 1 比特之间的 0 比特的位数）
     */
    getLowestSetBit(val: JBigInteger): JInt;

    /**
     * 将此 BigInteger 转换为 int
     */
    intValue(): JInt;

    /**
     * 如果此 BigInteger 可能为素数，则返回 true，如果它一定为合数，则返回 false
     */
    isProbablePrime(certainty: JInt): JBoolean;

    /**
     * 将此 BigInteger 转换为 long
     */
    longValue(): JLong;

    /**
     * 返回此 BigInteger 和 val 的最大值
     */
    max(val: JBigInteger): JBigInteger;

    /**
     * 返回此 BigInteger 和 val 的最小值
     */
    min(val: JBigInteger): JBigInteger;

    /**
     * 返回其值为 (this mod m) 的 BigInteger
     */
    mod(m: JBigInteger): JBigInteger;

    /**
     * 返回其值为 (this-1 mod m) 的 BigInteger
     */
    modInverse(m: JBigInteger): JBigInteger;

    /**
     * 返回其值为 (this exponent mod m) 的 BigInteger。
     */
    modPow(exponent: JBigInteger, m: JBigInteger): JBigInteger;

    /**
     * 返回其值为 (this * val) 的 BigInteger
     */
    multiply(val: JBigInteger): JBigInteger;

    /**
     * 返回其值是 (-this) 的 BigInteger
     */
    negate(): JBigInteger;

    /**
     * 返回大于此 BigInteger 的可能为素数的第一个整数
     */
    nextProbablePrime(): JBigInteger;

    /**
     * 返回其值为 (~this) 的 BigInteger
     */
    not(): JBigInteger;

    /**
     * 返回其值为 (this | val) 的 BigInteger
     */
    or(val: JBigInteger): JBigInteger;

    /**
     * 返回其值为 (this^exponent) 的 BigInteger
     */
    pow(exponent: JInt): JBigInteger;

    /**
     * 返回其值为 (this % val) 的 BigInteger
     */
    remainder(val: JBigInteger): JBigInteger;

    /**
     * 返回其值与设置了指定位的此 BigInteger 等效的 BigInteger
     */
    setBit(n: JInt): JBigInteger;

    /**
     * 返回其值为 (this << n) 的 BigInteger
     */
    shiftLeft(n: JInt): JBigInteger;

    /**
     * 返回其值为 (this >> n) 的 BigInteger
     */
    shiftRight(n: JInt): JBigInteger;

    /**
     * 返回此 BigInteger 的正负号函数
     */
    signum(): JInt;

    /**
     * 返回其值为 (this - val) 的 BigInteger
     */
    subtract(val: JBigInteger): JBigInteger;

    /**
     * 当且仅当设置了指定的位时，返回 true
     */
    testBit(): JBoolean;

    /**
     * 返回一个字节数组，该数组包含此 BigInteger 的二进制补码表示形式
     */
    toByteArray(): JByte[];

    /**
     * 返回此 BigInteger 的给定基数的字符串表示形式
     */
    toString(radix: JInt): JString;

    /**
     * 返回其值为 (this ^ val) 的 BigInteger
     */
    xor(val: JBigInteger): JString;
}