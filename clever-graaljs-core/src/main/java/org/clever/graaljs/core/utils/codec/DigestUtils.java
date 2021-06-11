package org.clever.graaljs.core.utils.codec;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.clever.graaljs.core.utils.ExceptionUtils;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;

/**
 * 实现各种摘要算法的工具类，支持：<br/>
 * 1.MD5散列<br/>
 * 2.SHA-1散列<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-8 11:06 <br/>
 */
@Slf4j
public class DigestUtils {

    public static final String SHA1 = "SHA-1";
    public static final String MD5 = "MD5";

    /**
     * 对字符串进行散列, 支持md5与sha1算法<br/>
     *
     * @param input      输入数据
     * @param algorithm  散列算法
     * @param salt       散列盐
     * @param iterations 迭代次数
     * @return 散列后的数据
     */
    private static byte[] digest(byte[] input, String algorithm, byte[] salt, int iterations) {
        byte[] result;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            if (salt != null) {
                messageDigest.update(salt);
            }
            result = messageDigest.digest(input);
            for (int i = 1; i < iterations; i++) {
                messageDigest.reset();
                result = messageDigest.digest(result);
            }
        } catch (Throwable e) {
            throw ExceptionUtils.unchecked(e);
        }
        return result;
    }

    /**
     * 对文件进行散列<br/>
     *
     * @param input     输入流
     * @param algorithm 散列算法
     * @return 散列值
     */
    private static byte[] digest(InputStream input, String algorithm) {
        byte[] result;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            int bufferLength = 8 * 1024;
            byte[] buffer = new byte[bufferLength];
            int read = input.read(buffer, 0, bufferLength);
            while (read > -1) {
                messageDigest.update(buffer, 0, read);
                read = input.read(buffer, 0, bufferLength);
            }
            result = messageDigest.digest();
        } catch (Throwable e) {
            throw ExceptionUtils.unchecked(e);
        }
        return result;
    }

    /**
     * 生成随机的Byte[]作为salt<br/>
     *
     * @param numBytes byte数组的大小
     */
    public static byte[] generateSalt(int numBytes) {
        Validate.isTrue(numBytes > 0, "numBytes argument must be a positive integer (1 or larger)", numBytes);
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[numBytes];
        random.nextBytes(bytes);
        return bytes;
    }

    /**
     * 对数据进行sha1散列<br/>
     *
     * @param data 数据
     * @return sha1散列后的数据
     */
    public static byte[] sha1(byte[] data) {
        return digest(data, SHA1, null, 1);
    }

    /**
     * 对数据进行sha1散列<br/>
     *
     * @param data 数据
     * @param salt 散列盐
     * @return sha1散列后的数据
     */
    public static byte[] sha1(byte[] data, byte[] salt) {
        return digest(data, SHA1, salt, 1);
    }

    /**
     * 对数据进行sha1散列<br/>
     *
     * @param data       数据
     * @param salt       散列盐
     * @param iterations 迭代次数
     * @return sha1散列后的数据
     */
    public static byte[] sha1(byte[] data, byte[] salt, int iterations) {
        return digest(data, SHA1, salt, iterations);
    }

    /**
     * 对文件进行sha1散列<br/>
     *
     * @param input 输入流
     * @return sha1散列值
     */
    public static byte[] sha1(InputStream input) {
        return digest(input, SHA1);
    }

    /**
     * 对数据进行md5散列<br/>
     *
     * @param data 数据
     * @return sha1散列后的数据
     */
    public static byte[] md5(byte[] data) {
        return digest(data, MD5, null, 1);
    }

    /**
     * 对数据进行md5散列<br/>
     *
     * @param data 数据
     * @param salt 散列盐
     * @return sha1散列后的数据
     */
    public static byte[] md5(byte[] data, byte[] salt) {
        return digest(data, MD5, salt, 1);
    }

    /**
     * 对数据进行md5散列<br/>
     *
     * @param data       数据
     * @param salt       散列盐
     * @param iterations 迭代次数
     * @return sha1散列后的数据
     */
    public static byte[] md5(byte[] data, byte[] salt, int iterations) {
        return digest(data, MD5, salt, iterations);
    }

    /**
     * 对文件进行md5散列<br/>
     *
     * @param input 输入流
     * @return md5散列值
     */
    public static byte[] md5(InputStream input) {
        return digest(input, MD5);
    }
}
