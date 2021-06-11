package org.clever.graaljs.core.builtin;


import org.clever.graaljs.core.utils.mapper.JsonXmlConverter;
import org.clever.graaljs.core.utils.mapper.XStreamMapper;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/08/12 09:07 <br/>
 */
public class XmlUtils {

    public static final XmlUtils Instance = new XmlUtils();

    private final XStreamMapper mapper;

    private XmlUtils() {
        mapper = XStreamMapper.getDom4jXStream();
    }

    /**
     * 对象序列化成XML字符串
     *
     * @param object 需要序列化xml的对象
     * @return 返回xml
     */
    public String toXml(Object object) {
        return mapper.toXml(object);
    }

    /**
     * XML字符串反序列化成对象
     *
     * @param xml XML字符串
     * @param <T> 返回的对象类型
     * @return 返回的对象
     */
    public <T> T fromXml(String xml) {
        return mapper.fromXml(xml);
    }

    /**
     * 当XML里只含有Bean的部分属性时，更新一个已存在Bean，只覆盖该部分的属性
     *
     * @param xml    XML字符串
     * @param object 需要更新的对象
     * @return 操作成功返回true
     */
    public boolean update(String xml, Object object) {
        return mapper.update(xml, object);
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
