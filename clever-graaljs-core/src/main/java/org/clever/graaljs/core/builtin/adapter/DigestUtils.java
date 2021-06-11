package org.clever.graaljs.core.builtin.adapter;

import org.clever.graaljs.core.utils.codec.CryptoUtils;

import java.io.InputStream;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/28 22:33 <br/>
 */
public class DigestUtils {
    public static final DigestUtils Instance = new DigestUtils();

    private DigestUtils() {
    }

    // Salt 盐
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 生成随机的Byte[]作为salt<br/>
     *
     * @param numBytes byte数组的大小
     */
    public byte[] generateSalt(int numBytes) {
        return org.clever.graaljs.core.utils.codec.DigestUtils.generateSalt(numBytes);
    }

    // HMAC-SHA1 签名
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.<br/>
     *
     * @param input 原始输入字符数组
     * @param key   HMAC-SHA1密钥
     * @return 返回字节数组, 长度为20字节
     */
    public byte[] hmacSha1(byte[] input, byte[] key) {
        return CryptoUtils.hmacSha1(input, key);
    }

    /**
     * 校验HMAC-SHA1签名是否正确.<br/>
     *
     * @param expected 已存在的签名
     * @param input    原始输入字符串
     * @param key      密钥
     * @return 正确返回true，错误返回false
     */
    public boolean isHmacSha1Valid(byte[] expected, byte[] input, byte[] key) {
        return CryptoUtils.isHmacSha1Valid(expected, input, key);
    }

    /**
     * 生成HMAC-SHA1密钥,返回字节数组,长度为160位(20字节).<br/>
     * HMAC-SHA1算法对密钥无特殊要求,RFC2401建议最少长度为160位(20字节).<br/>
     *
     * @return HMAC-SHA1密钥,长度为160位(20字节)
     */
    public byte[] generateHmacSha1Key() {
        return CryptoUtils.generateHmacSha1Key();
    }

    // SHA1 签名
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 对数据进行sha1散列<br/>
     *
     * @param data 数据
     * @return sha1散列后的数据
     */
    public byte[] sha1(byte[] data) {
        return org.clever.graaljs.core.utils.codec.DigestUtils.sha1(data);
    }

    /**
     * 对数据进行sha1散列<br/>
     *
     * @param data 数据
     * @param salt 散列盐
     * @return sha1散列后的数据
     */
    public byte[] sha1(byte[] data, byte[] salt) {
        return org.clever.graaljs.core.utils.codec.DigestUtils.sha1(data, salt);
    }

    /**
     * 对数据进行sha1散列<br/>
     *
     * @param data       数据
     * @param salt       散列盐
     * @param iterations 迭代次数
     * @return sha1散列后的数据
     */
    public byte[] sha1(byte[] data, byte[] salt, int iterations) {
        return org.clever.graaljs.core.utils.codec.DigestUtils.sha1(data, salt, iterations);
    }

    /**
     * 对文件进行sha1散列<br/>
     *
     * @param input 输入流
     * @return sha1散列值
     */
    public byte[] sha1(InputStream input) {
        return org.clever.graaljs.core.utils.codec.DigestUtils.sha1(input);
    }

    // MD5 签名
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 对数据进行md5散列<br/>
     *
     * @param data 数据
     * @return sha1散列后的数据
     */
    public byte[] md5(byte[] data) {
        return org.clever.graaljs.core.utils.codec.DigestUtils.md5(data);
    }

    /**
     * 对数据进行md5散列<br/>
     *
     * @param data 数据
     * @param salt 散列盐
     * @return sha1散列后的数据
     */
    public byte[] md5(byte[] data, byte[] salt) {
        return org.clever.graaljs.core.utils.codec.DigestUtils.md5(data, salt);
    }

    /**
     * 对数据进行md5散列<br/>
     *
     * @param data       数据
     * @param salt       散列盐
     * @param iterations 迭代次数
     * @return sha1散列后的数据
     */
    public byte[] md5(byte[] data, byte[] salt, int iterations) {
        return org.clever.graaljs.core.utils.codec.DigestUtils.md5(data, salt, iterations);
    }

    /**
     * 对文件进行md5散列<br/>
     *
     * @param input 输入流
     * @return md5散列值
     */
    public byte[] md5(InputStream input) {
        return org.clever.graaljs.core.utils.codec.DigestUtils.md5(input);
    }
}
