package org.clever.graaljs.core.utils.codec;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.clever.graaljs.core.utils.ExceptionUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 各种格式的编码、解码工具类：<br/>
 * 1.Hex编码<br/>
 * 2.Base64编码<br/>
 * 3.Base62编码<br/>
 * 4.Html 转码<br/>
 * 5.Xml 解码<br/>
 * 6.URL 编码<br/>
 * 7.处理浏览器下载文件的文件编码问题<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-7 16:49 <br/>
 */
@Slf4j
public class EncodeDecodeUtils {

    /**
     * url默认的编码格式
     */
    private static final String DEFAULT_URL_ENCODING = "UTF-8";

    /**
     * BASE62字符
     */
    private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    /**
     * Hex编码.
     *
     * @param input 编码字节数组
     * @return Hex编码编码之后的字符串
     */
    public static String encodeHex(byte[] input) {
        return Hex.encodeHexString(input);
    }

    /**
     * Hex解码.
     *
     * @param input 编码字符串
     * @return Hex解码之后的字节数组
     */
    public static byte[] decodeHex(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (Throwable e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

    /**
     * 判断字符串是否是Hex编码，传入空值或null返回true<br/>
     * <pre>
     * ""           --->    true
     * null         --->    true
     * "0164abf"    --->    true
     * "asdfwae"    --->    false
     * </pre>
     *
     * @param input 待判断的Hex字符串
     * @return 是Hex编码返回true，否则返回false
     */
    public static boolean isHexCode(String input) {
        if (StringUtils.isBlank(input)) {
            return true;
        }
        boolean flag = true;
        for (char c : input.toCharArray()) {
            if (!((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F'))) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    /**
     * Base64编码.
     *
     * @param input Base64编码数据
     * @return Base64编码后的字符串
     */
    public static String encodeBase64(byte[] input) {
        return Base64.encodeBase64String(input);
    }

    /**
     * Base64编码, URL安全(将Base64中的URL非法字符'+'和'/'转为'-'和'_', 见RFC3548).
     *
     * @param input Base64编码数据
     * @return Base64编码后的字符串
     */
    public static String encodeUrlSafeBase64(byte[] input) {
        return Base64.encodeBase64URLSafeString(input);
    }

    /**
     * Base64解码.
     *
     * @param input 待Base64解码的字符串
     * @return Base64数据
     */
    public static byte[] decodeBase64(String input) {
        return Base64.decodeBase64(input);
    }

    /**
     * Base62编码。
     *
     * @param input Base62编码数据
     * @return Base62编码后的字符串
     */
    public static String encodeBase62(byte[] input) {
        char[] chars = new char[input.length];
        for (int i = 0; i < input.length; i++) {
            chars[i] = BASE62[(input[i] & 0xFF) % BASE62.length];
        }
        return new String(chars);
    }

    /**
     * Html 转码.
     *
     * @param html html字符串
     * @return 转码之后的字符串
     */
    public static String escapeHtml(String html) {
        return StringEscapeUtils.escapeHtml4(html);
    }

    /**
     * Html 解码.
     *
     * @param htmlEscaped html转码之后的字符串
     * @return html字符串
     */
    public static String unescapeHtml(String htmlEscaped) {
        return StringEscapeUtils.unescapeHtml4(htmlEscaped);
    }

    /**
     * Xml 转码.
     *
     * @param xml xml字符串
     * @return Xml转码字符串
     */
    public static String escapeXml(String xml) {
        return StringEscapeUtils.escapeXml11(xml);
    }

    /**
     * Xml 解码.
     *
     * @param xmlEscaped Xml转码字符串
     * @return xml字符串
     */
    public static String unescapeXml(String xmlEscaped) {
        return StringEscapeUtils.unescapeXml(xmlEscaped);
    }

    /**
     * URL 编码, Encode默认为UTF-8.
     *
     * @param url url字符串
     * @return url编码字符串
     */
    public static String urlEncode(String url) {
        try {
            return URLEncoder.encode(url, DEFAULT_URL_ENCODING);
        } catch (Throwable e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

    /**
     * URL 解码, Encode默认为UTF-8.
     *
     * @param url url解码字符串
     * @return url解码字符串
     */
    public static String urlDecode(String url) {
        try {
            return URLDecoder.decode(url, DEFAULT_URL_ENCODING);
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            throw ExceptionUtils.unchecked(e);
        }
    }
}
