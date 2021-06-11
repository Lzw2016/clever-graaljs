package org.clever.graaljs.core.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Json类型工具类，用于判断一个Json字符串属于哪一种类型：<br/>
 * 1.一个Json对象<br/>
 * 2.一个Json数组<br/>
 * 3.不是Json字符串<br/>
 * <b>使用org.json实现</b>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-1 19:59 <br/>
 */
@Slf4j
public class JsonTypeUtils {
    /**
     * 一个Json对象
     */
    public static final String JSON_OBJECT = "JSONObject";

    /**
     * 一个Json数组
     */
    public static final String JSON_ARRAY = "JSONArray";

    /**
     * 不是Json字符串
     */
    public static final String JSON_STR_ERROR = "JSONError";

    /**
     * 获取JSON类型
     *
     * @param json json字符串
     * @return 返回json类型
     */
    public static String getJSONType(String json) {
        if (StringUtils.isBlank(json)) {
            return JSON_STR_ERROR;
        }
        try {
            Object object = new JSONTokener(json).nextValue();
            if (object instanceof JSONObject) {
                //是object类型
                return JSON_OBJECT;
            } else if (object instanceof JSONArray) {
                //是array类型
                return JSON_ARRAY;
            } else {
                return JSON_STR_ERROR;
            }
        } catch (Throwable e) {
            return JSON_STR_ERROR;
        }
    }
}
