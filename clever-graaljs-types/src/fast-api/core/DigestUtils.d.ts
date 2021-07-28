interface DigestUtils {
    // Salt 盐
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 生成随机的Byte[]作为salt<br/>
     *
     * @param numBytes  JByte数组的大小
     */
    generateSalt(numBytes: JInt): JByte[]

    // HMAC-SHA1 签名
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.<br/>
     *
     * @param input 原始输入字符数组
     * @param key   HMAC-SHA1密钥
     * @return 返回字节数组, 长度为20字节
     */
    hmacSha1(input: JByte[], key: JByte[]): JByte[]

    /**
     * 校验HMAC-SHA1签名是否正确.<br/>
     *
     * @param expected 已存在的签名
     * @param input    原始输入字符串
     * @param key      密钥
     * @return 正确返回true，错误返回false
     */
    isHmacSha1Valid(expected: JByte[], input: JByte[], key: JByte[]): JBoolean

    /**
     * 生成HMAC-SHA1密钥,返回字节数组,长度为160位(20字节).<br/>
     * HMAC-SHA1算法对密钥无特殊要求,RFC2401建议最少长度为160位(20字节).<br/>
     *
     * @return HMAC-SHA1密钥,长度为160位(20字节)
     */
    generateHmacSha1Key(): JByte[]

    // SHA1 签名
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 对数据进行sha1散列<br/>
     *
     * @param data 数据
     * @return sha1散列后的数据
     */
    sha1(data: JByte[]): JByte[]

    /**
     * 对数据进行sha1散列<br/>
     *
     * @param data 数据
     * @param salt 散列盐
     * @return sha1散列后的数据
     */
    sha1(data: JByte[], salt: JByte[]): JByte[]

    /**
     * 对数据进行sha1散列<br/>
     *
     * @param data       数据
     * @param salt       散列盐
     * @param iterations 迭代次数
     * @return sha1散列后的数据
     */
    sha1(data: JByte[], salt: JByte[], iterations: JInt): JByte[]

    /**
     * 对文件进行sha1散列<br/>
     *
     * @param input 输入流
     * @return sha1散列值
     */
    sha1(input: JInputStream): JByte[]

    // MD5 签名
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 对数据进行md5散列<br/>
     *
     * @param data 数据
     * @return sha1散列后的数据
     */
    md5(data: JByte[]): JByte[]

    /**
     * 对数据进行md5散列<br/>
     *
     * @param data 数据
     * @param salt 散列盐
     * @return sha1散列后的数据
     */
    md5(data: JByte[], salt: JByte[]): JByte[]

    /**
     * 对数据进行md5散列<br/>
     *
     * @param data       数据
     * @param salt       散列盐
     * @param iterations 迭代次数
     * @return sha1散列后的数据
     */
    md5(data: JByte[], salt: JByte[], iterations: JInt): JByte[]

    /**
     * 对文件进行md5散列<br/>
     *
     * @param input 输入流
     * @return md5散列值
     */
    md5(input: JInputStream): JByte[]
}

declare const DigestUtils: DigestUtils;
