package org.clever.graaljs.core.utils.codec;

import lombok.extern.slf4j.Slf4j;
import org.clever.graaljs.core.utils.ExceptionUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * 加密解密工具类，支持:<br/>
 * 1.HMAC-SHA1消息签名<br/>
 * 2.AES对称加密<br/>
 * 待支持：DES、RC4、Rabbit、TripleDes、RSA、DSA...<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-7 13:54 <br/>
 */
@Slf4j
public class CryptoUtils {

    private static final String AES = "AES";
    private static final String AES_CBC = "AES/CBC/PKCS5Padding";
    private static final String HMACSHA1 = "HmacSHA1";

    /**
     * 生成HMAC-SHA1密钥的默认长度，为了兼容RFC2401<br/>
     * HMAC-SHA1算法对密钥无特殊要求<br/>
     * RFC2401建议最少长度为160位(20字节)<br/>
     */
    private static final int DEFAULT_HMACSHA1_KEYSIZE = 160;

    /**
     * 生成AES密钥的默认长度
     */
    private static final int DEFAULT_AES_KEYSIZE = 128;

    /**
     * 默认生成的向量长度
     */
    private static final int DEFAULT_IVSIZE = 16;

    /**
     * 生成密钥
     *
     * @param encryptType 生成密钥对应的算法，如：AES、HmacSHA1
     * @param keysize     密钥的长度
     * @return 返回密钥字节数组
     */
    private static byte[] generateKey(String encryptType, int keysize) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(encryptType);
            keyGenerator.init(keysize);
            SecretKey secretKey = keyGenerator.generateKey();
            return secretKey.getEncoded();
        } catch (Throwable e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

    /**
     * 生成随机向量
     *
     * @param ivSize 向量长度
     * @return 向量数据
     */
    public static byte[] generateIV(int ivSize) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[ivSize];
        random.nextBytes(bytes);
        return bytes;
    }

    /**
     * 生成随机向量,默认大小为cipher.getBlockSize(), 16字节.
     *
     * @return 向量数据
     */
    public static byte[] generateIV() {
        return generateIV(DEFAULT_IVSIZE);
    }

    /*#==================================================== HMAC-SHA1 ====================================================#*/

    /**
     * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.<br/>
     *
     * @param input 原始输入字符数组
     * @param key   HMAC-SHA1密钥
     * @return 返回字节数组, 长度为20字节
     */
    public static byte[] hmacSha1(byte[] input, byte[] key) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, HMACSHA1);
            Mac mac = Mac.getInstance(HMACSHA1);
            mac.init(secretKey);
            return mac.doFinal(input);
        } catch (Throwable e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

    /**
     * 校验HMAC-SHA1签名是否正确.<br/>
     *
     * @param expected 已存在的签名
     * @param input    原始输入字符串
     * @param key      密钥
     * @return 正确返回true，错误返回false
     */
    public static boolean isHmacSha1Valid(byte[] expected, byte[] input, byte[] key) {
        byte[] actual = hmacSha1(input, key);
        return Arrays.equals(expected, actual);
    }

    /**
     * 生成HMAC-SHA1密钥,返回字节数组,长度为160位(20字节).<br/>
     * HMAC-SHA1算法对密钥无特殊要求,RFC2401建议最少长度为160位(20字节).<br/>
     *
     * @return HMAC-SHA1密钥,长度为160位(20字节)
     */
    public static byte[] generateHmacSha1Key() {
        return generateKey(HMACSHA1, DEFAULT_HMACSHA1_KEYSIZE);
    }

    /*#==================================================== AES ====================================================#*/

    /**
     * 使用AES加密或解密无编码的原始字节数组, 返回无编码的字节数组结果.
     *
     * @param input 原始字节数组
     * @param key   符合AES要求的密钥
     * @param mode  Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
     * @return 返回无编码的字节数组结果
     */
    public static byte[] aes(byte[] input, byte[] key, int mode) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, AES);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(mode, secretKey);
            return cipher.doFinal(input);
        } catch (Throwable e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

    /**
     * 使用AES加密原始字符串.
     *
     * @param input 原始输入字符数组
     * @param key   符合AES要求的密钥
     * @return 字节数组
     */
    public static byte[] aesEncrypt(byte[] input, byte[] key) {
        return aes(input, key, Cipher.ENCRYPT_MODE);
    }

    /**
     * 使用AES解密字符串, 返回原始字符串.
     *
     * @param input Hex编码的加密字符串
     * @param key   符合AES要求的密钥
     * @return 原始字符串
     */
    public static String aesDecrypt(byte[] input, byte[] key) {
        byte[] decryptResult = aes(input, key, Cipher.DECRYPT_MODE);
        return new String(decryptResult);
    }

    /**
     * 使用AES加密或解密无编码的原始字节数组, 返回无编码的字节数组结果.
     *
     * @param input 原始字节数组
     * @param key   符合AES要求的密钥
     * @param iv    初始向量
     * @param mode  Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
     */
    public static byte[] aes(byte[] input, byte[] key, byte[] iv, int mode) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, AES);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance(AES_CBC);
            cipher.init(mode, secretKey, ivSpec);
            return cipher.doFinal(input);
        } catch (Throwable e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

    /**
     * 使用AES加密原始字符串.
     *
     * @param input 原始输入字符数组
     * @param key   符合AES要求的密钥
     * @param iv    初始向量
     * @return 字节数组
     */
    public static byte[] aesEncrypt(byte[] input, byte[] key, byte[] iv) {
        return aes(input, key, iv, Cipher.ENCRYPT_MODE);
    }

    /**
     * 使用AES解密字符串, 返回原始字符串.
     *
     * @param input Hex编码的加密字符串
     * @param key   符合AES要求的密钥
     * @param iv    初始向量
     * @return 原始字符串
     */
    public static String aesDecrypt(byte[] input, byte[] key, byte[] iv) {
        byte[] decryptResult = aes(input, key, iv, Cipher.DECRYPT_MODE);
        return new String(decryptResult);
    }

    /**
     * 生成AES密钥,可选长度为128,192,256位.
     *
     * @param keySize 可选长度为128,192,256位
     */
    public static byte[] generateAesKey(int keySize) {
        return generateKey(AES, keySize);
    }

    /**
     * 生成AES密钥,返回字节数组, 默认长度为128位(16字节).
     */
    public static byte[] generateAesKey() {
        return generateAesKey(DEFAULT_AES_KEYSIZE);
    }

    /*#==================================================== 其他加密算法 ====================================================#*/
}
