package org.clever.graaljs.core.utils.mapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.core.utils.ExceptionUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Json串与Java对象的相互转换工具<br/>
 * 1.通过Jackson实现<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-4-28 0:55 <br/>
 */
@Slf4j
public class JacksonMapper {
    private static final String INDENT = "    ";
    private static final DefaultPrettyPrinter PRETTY_PRINTER;
    private static final JacksonMapper Instance;

    static {
        Instance = new JacksonMapper();
        DefaultPrettyPrinter.Indenter indenter = new DefaultIndenter(INDENT, DefaultIndenter.SYS_LF);
        PRETTY_PRINTER = new DefaultPrettyPrinter();
        PRETTY_PRINTER.indentObjectsWith(indenter);
        PRETTY_PRINTER.indentArraysWith(indenter);
        PRETTY_PRINTER.withObjectIndenter(indenter);
        PRETTY_PRINTER.withArrayIndenter(indenter);
    }

    /**
     * 对象转换器
     */
    private final ObjectMapper mapper;

    public JacksonMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 通用配置,参考 Jackson2ObjectMapperBuilder
     */
    private JacksonMapper() {
        mapper = newObjectMapper();
    }

    /**
     * 返回标准的实例
     */
    public static JacksonMapper getInstance() {
        return Instance;
    }

    /**
     * Object可以是POJO，也可以是Collection或数组。 如果对象为Null, 返回"null". 如果集合为空集合, 返回"[]".<br/>
     *
     * @param object 需要序列化的对象
     * @return 序列化后的Json字符串
     */
    public String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

    /**
     * Object可以是POJO，也可以是Collection或数组。 如果对象为Null, 返回"null". 如果集合为空集合, 返回"[]".<br/>
     *
     * @param object 需要序列化的对象
     * @return 序列化后的Json字符串
     */
    public String toPrettyJson(Object object) {
        try {
            return mapper.writer(PRETTY_PRINTER).writeValueAsString(object);
        } catch (IOException e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

    /**
     * 输出JSON格式数据.
     */
    public String toJsonP(String functionName, Object object) {
        return toJson(new JSONPObject(functionName, object));
    }

    /**
     * 反序列化POJO或简单Collection如List&lt;String&gt;<br/>
     * 如果JSON字符串为null或空字符串, 返回null. 如果JSON字符串为"[]", 返回空集合<br/>
     * 如需反序列化复杂Collection如List&lt;MyBean&gt;，请使用fromJson(String, JavaType)<br/>
     *
     * @param jsonString Json字符串
     * @param clazz      反序列化的对象类型
     * @return 反序列化的对象
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

    /**
     * 当JSON里只含有Bean的部分属性时，更新一个已存在Bean，只覆盖该部分的属性.
     *
     * @param jsonString Json字符串
     * @param object     需要更新的对象
     * @return 操作成功返回true
     */
    public boolean update(String jsonString, Object object) {
        try {
            mapper.readerForUpdating(object).readValue(jsonString);
            return true;
        } catch (IOException e) {
            log.error("update json string:" + jsonString + " to object:" + object + " error.", e);
            return false;
        }
    }

    /**
     * 返回当前 Jackson 对应的 ObjectMapper
     */
    public ObjectMapper getMapper() {
        return mapper;
    }

    public static ObjectMapper newObjectMapper() {
        final String dateTimePattern = "yyyy-MM-dd HH:mm:ss";
        final String datePattern = "yyyy-MM-dd";
        final String timePattern = "HH:mm:ss";
        final ClassLoader moduleClassLoader = JacksonMapper.class.getClassLoader();
        // 创建 ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        // 查找并注册Modules
        mapper.findAndRegisterModules();
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 允许单引号、允许不带引号的字段名称
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 使用枚举的的toString函数来读写枚举
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        // 设置时区 getTimeZone("GMT+8")
        mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        // locale: zh_CN
        mapper.setLocale(Locale.CHINA);
        // 设置时间格式
        mapper.setDateFormat(new SimpleDateFormat(dateTimePattern));
        // 注册 Module
        ObjectMapper.findModules(moduleClassLoader).forEach(mapper::registerModules);
        SimpleModule module = new SimpleModule();
        module.addSerializer(
                DateTime.class,
                new com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer(
                        new com.fasterxml.jackson.datatype.joda.cfg.JacksonJodaDateFormat(DateTimeFormat.forPattern(dateTimePattern).withZoneUTC()), 0
                )
        );
        module.addSerializer(LocalDateTime.class, new com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimePattern)));
        module.addSerializer(LocalDate.class, new com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer(DateTimeFormatter.ofPattern(datePattern)));
        module.addSerializer(LocalTime.class, new com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer(DateTimeFormatter.ofPattern(timePattern)));
        // module.addSerializer(BigInteger.class, ToStringSerializer.instance);
        // module.addSerializer(Long.class, ToStringSerializer.instance);
        // module.addSerializer(Long.TYPE, ToStringSerializer.instance);
        module.addDeserializer(LocalDateTime.class, new com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(dateTimePattern)));
        module.addDeserializer(LocalDate.class, new com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer(DateTimeFormatter.ofPattern(datePattern)));
        module.addDeserializer(LocalTime.class, new com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer(DateTimeFormatter.ofPattern(timePattern)));
        mapper.registerModules(module);
        return mapper;
    }
}
