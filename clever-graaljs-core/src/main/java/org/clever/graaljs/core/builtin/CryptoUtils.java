package org.clever.graaljs.core.builtin;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/28 22:33 <br/>
 */
public class CryptoUtils {
    public static final CryptoUtils Instance = new CryptoUtils();

    private CryptoUtils() {
    }

    // 生成向量(IV)
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 生成随机向量
     *
     * @param ivSize 向量长度(默认16)
     * @return 向量数据
     */
    public byte[] generateIV(int ivSize) {
        return org.clever.graaljs.core.utils.codec.CryptoUtils.generateIV(ivSize);
    }

    /**
     * 生成随机向量,默认大小为cipher.getBlockSize(), 16字节.
     *
     * @return 向量数据
     */
    public byte[] generateIV() {
        return org.clever.graaljs.core.utils.codec.CryptoUtils.generateIV();
    }

    // AES 加密/解密
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 使用AES加密或解密无编码的原始字节数组, 返回无编码的字节数组结果.
     *
     * @param input 原始字节数组
     * @param key   符合AES要求的密钥
     * @param mode  Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
     * @return 返回无编码的字节数组结果
     */
    public byte[] aes(byte[] input, byte[] key, int mode) {
        return org.clever.graaljs.core.utils.codec.CryptoUtils.aes(input, key, mode);
    }

    /**
     * 使用AES加密原始字符串.
     *
     * @param input 原始输入字符数组
     * @param key   符合AES要求的密钥
     * @return 字节数组
     */
    public byte[] aesEncrypt(byte[] input, byte[] key) {
        return org.clever.graaljs.core.utils.codec.CryptoUtils.aesEncrypt(input, key);
    }

    /**
     * 使用AES解密字符串, 返回原始字符串.
     *
     * @param input Hex编码的加密字符串
     * @param key   符合AES要求的密钥
     * @return 原始字符串
     */
    public String aesDecrypt(byte[] input, byte[] key) {
        return org.clever.graaljs.core.utils.codec.CryptoUtils.aesDecrypt(input, key);
    }


    /**
     * 使用AES加密原始字符串.
     *
     * @param input 原始输入字符数组
     * @param key   符合AES要求的密钥
     * @param iv    初始向量
     * @return 字节数组
     */
    public byte[] aesEncrypt(byte[] input, byte[] key, byte[] iv) {
        return org.clever.graaljs.core.utils.codec.CryptoUtils.aesEncrypt(input, key, iv);
    }

    /**
     * 使用AES解密字符串, 返回原始字符串.
     *
     * @param input Hex编码的加密字符串
     * @param key   符合AES要求的密钥
     * @param iv    初始向量
     * @return 原始字符串
     */
    public String aesDecrypt(byte[] input, byte[] key, byte[] iv) {
        return org.clever.graaljs.core.utils.codec.CryptoUtils.aesDecrypt(input, key, iv);
    }

    /**
     * 生成AES密钥,可选长度为128,192,256位.
     *
     * @param keySize 可选长度为128,192,256位
     */
    public byte[] generateAesKey(int keySize) {
        return org.clever.graaljs.core.utils.codec.CryptoUtils.generateAesKey(keySize);
    }

    /**
     * 生成AES密钥,返回字节数组, 默认长度为128位(16字节).
     */
    public byte[] generateAesKey() {
        return org.clever.graaljs.core.utils.codec.CryptoUtils.generateAesKey();
    }
}
