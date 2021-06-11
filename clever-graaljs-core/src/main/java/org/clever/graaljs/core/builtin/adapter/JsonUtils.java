package org.clever.graaljs.core.builtin.adapter;

import org.clever.graaljs.core.utils.mapper.JacksonMapper;
import org.clever.graaljs.core.utils.mapper.JsonXmlConverter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/08/12 09:07 <br/>
 */
public class JsonUtils {
    public static final JsonUtils Instance = new JsonUtils();

    private final JacksonMapper mapper;

    private JsonUtils() {
        mapper = JacksonMapper.getInstance();
    }

    /**
     * Object可以是POJO，也可以是Collection或数组。 如果对象为Null, 返回"null". 如果集合为空集合, 返回"[]".<br/>
     *
     * @param object 需要序列化的对象
     * @return 序列化后的Json字符串
     */
    public String toJson(Object object) {
        return mapper.toJson(object);
    }

    /**
     * 输出JSON格式数据.
     */
    public String toJsonP(String functionName, Object object) {
        return mapper.toJsonP(functionName, object);
    }

    /**
     * 把Json字符串转换成Map对象
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> toMap(String json) {
        return mapper.fromJson(json, LinkedHashMap.class);
    }

    /**
     * 当JSON里只含有Bean的部分属性时，更新一个已存在Bean，只覆盖该部分的属性.
     *
     * @param json   Json字符串
     * @param object 需要更新的对象
     * @return 操作成功返回true
     */
    public boolean update(String json, Object object) {
        return mapper.update(json, object);
    }

    /**
     * XML字符串转换成Json字符串<br/>
     *
     * @param xml XML字符串
     * @return Json字符串
     */
    public String xmlToJson(String xml) {
        return JsonXmlConverter.xmlToJson(xml);
    }

    /**
     * Json字符串转换成XML字符串<br/>
     *
     * @param json Json字符串
     * @return XML字符串
     */
    public String jsonToXml(String json) {
        return JsonXmlConverter.jsonToXml(json);
    }
}
