/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/28 22:33 <br/>
 */
interface EncodeDecodeUtils extends JObject {
    // Hex 编码/解密
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Hex编码.
     *
     * @param input 编码字节数组
     * @return Hex编码编码之后的字符串
     */
    encodeHex(input: JByte[]): JString;

    /**
     * Hex解码.
     *
     * @param input 编码字符串
     * @return Hex解码之后的字节数组
     */
    decodeHex(input: JString): JByte[];

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
    isHexCode(input: JString): JBoolean;

    // Base64 编码/解密
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Base64编码.
     *
     * @param input Base64编码数据
     * @return Base64编码后的字符串
     */
    encodeBase64(input: JByte[]): JString;

    /**
     * Base64编码, URL安全(将Base64中的URL非法字符'+'和'/'转为'-'和'_', 见RFC3548).
     *
     * @param input Base64编码数据
     * @return Base64编码后的字符串
     */
    encodeUrlSafeBase64(input: JByte[]): JString;

    /**
     * Base64解码.
     *
     * @param input 待Base64解码的字符串
     * @return Base64数据
     */
    decodeBase64(input: JString): JByte[];

    // Base62 编码/解密
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Base62编码。
     *
     * @param input Base62编码数据
     * @return Base62编码后的字符串
     */
    encodeBase62(input: JByte[]): JString;

    // HTML 编码/解密
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Html 转码.
     *
     * @param html html字符串
     * @return 转码之后的字符串
     */
    escapeHtml(html: JString): JString;

    /**
     * Html 解码.
     *
     * @param htmlEscaped html转码之后的字符串
     * @return html字符串
     */
    unescapeHtml(htmlEscaped: JString): JString;

    // XML 编码/解密
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Xml 转码.
     *
     * @param xml xml字符串
     * @return Xml转码字符串
     */
    escapeXml(xml: JString): JString;

    /**
     * Xml 解码.
     *
     * @param xmlEscaped Xml转码字符串
     * @return xml字符串
     */
    unescapeXml(xmlEscaped: JString): JString;

    // URL 编码/解密
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * URL 编码, Encode默认为UTF-8.
     *
     * @param url url字符串
     * @return url编码字符串
     */
    urlEncode(url: JString): JString;

    /**
     * URL 解码, Encode默认为UTF-8.
     *
     * @param url url解码字符串
     * @return url解码字符串
     */
    urlDecode(url: JString): JString;

    // browser
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 处理浏览器下载文件的文件编码问题
     *
     * @param userAgent 浏览器标识:request.getHeader("User-Agent")
     * @param fileName  文件名称
     * @return 处理之后的文件名称
     */
    browserDownloadFileName(userAgent: JString, fileName: JString): JString;
}

declare const EncodeDecodeUtils: EncodeDecodeUtils;
