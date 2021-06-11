package org.clever.graaljs.core.utils;


import org.apache.commons.text.StringEscapeUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作类工具，继承org.apache.commons.lang3.StringUtils<br/>
 * 作者：LiZW <br/>
 * 创建时间：2016-4-28 0:20 <br/>
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    /**
     * 调用对象的toString方法，如果对象为空返回默认值
     *
     * @param object     需要toString的对象
     * @param defaultStr 对象为空时返回的默认值
     * @return 返回对象的toString方法结果
     */
    public static String objectToString(Object object, String defaultStr) {
        if (null == object) {
            return defaultStr;
        } else {
            return object.toString();
        }
    }

    /**
     * 除去html标签
     *
     * @param htmlStr 含有html标签的字符串
     * @return 网页文本内容
     */
    public static String delHTMLTag(String htmlStr) {
        if (isBlank(htmlStr)) {
            return htmlStr;
        }
        htmlStr = StringEscapeUtils.unescapeHtml4(htmlStr);
        //定义script的正则表达式
        String regEx_script = "<script[^>]*?>[\\s\\S]*?</script>";
        //定义style的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?</style>";
        //定义HTML标签的正则表达式
        String regEx_html = "<[^>]+>";

        //过滤script标签
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll("");

        //过滤style标签
        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll("");

        //过滤html标签
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");

        //返回文本字符串
        return htmlStr.trim();
    }
}
