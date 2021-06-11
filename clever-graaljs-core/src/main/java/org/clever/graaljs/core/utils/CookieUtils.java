package org.clever.graaljs.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-5-8 16:31 <br/>
 */
@Slf4j
public class CookieUtils {
    /**
     * Cookie的默认编码格式
     */
    private final static String Default_Cookie_Encode = "UTF-8";

    /**
     * 设置Cookie
     *
     * @param response HTTP响应
     * @param path     Cookie的Path
     * @param name     名称
     * @param value    值
     * @param maxAge   Cookie生存时间，单位：秒。负数表示Cookie永不过期，0表示删除Cookie
     */
    public static void setCookie(HttpServletResponse response, String path, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        if (StringUtils.isNotBlank(path)) {
            cookie.setPath(path);
        }
        if (maxAge >= 0) {
            cookie.setMaxAge(maxAge);
        }
        try {
            cookie.setValue(URLEncoder.encode(value, Default_Cookie_Encode));
        } catch (Throwable e) {
            throw ExceptionUtils.unchecked(e);
        }
        response.addCookie(cookie);
    }

    /**
     * 设置Cookie
     *
     * @param response HTTP响应
     * @param path     Cookie的Path
     * @param name     名称
     * @param value    值
     */
    public static void setCookie(HttpServletResponse response, String path, String name, String value) {
        setCookie(response, path, name, value, -1);
    }

    /**
     * 设置Cookie(当前路径)
     *
     * @param name  名称
     * @param value 值
     */
    public static void setCookieForCurrentPath(HttpServletResponse response, String name, String value) {
        setCookie(response, null, name, value, -1);
    }

    /**
     * 设置Cookie(根路径)
     *
     * @param name  名称
     * @param value 值
     */
    public static void setCookieForRooPath(HttpServletResponse response, String name, String value) {
        setCookie(response, "/", name, value, -1);
    }

    /**
     * 获得指定Cookie的值
     *
     * @param request 请求对象
     * @param name    名字
     * @return Cookie值，不存在返回null
     */
    public static String getCookie(HttpServletRequest request, String name) {
        String value = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    try {
                        value = URLDecoder.decode(cookie.getValue(), Default_Cookie_Encode);
                    } catch (Throwable e) {
                        log.error("Cookie的值解码失败", e);
                        value = cookie.getValue();
                    }
                    break;
                }
            }
        }
        return value;
    }

    /**
     * 删除指定Cookie
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param name     名称
     * @param path     Cookie的Path
     */
    public static void delCookie(HttpServletRequest request, HttpServletResponse response, String name, String path) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    cookie.setMaxAge(0);
                    cookie.setPath(path);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }

    /**
     * 删除指定Cookie(当前路径)
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param name     名称
     */
    public static void delCookieForCurrentPath(HttpServletRequest request, HttpServletResponse response, String name) {
        delCookie(request, response, name, null);
    }

    /**
     * 删除指定Cookie(根路径)
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param name     名称
     */
    public static void delCookieForRooPath(HttpServletRequest request, HttpServletResponse response, String name) {
        delCookie(request, response, name, "/");
    }
}
