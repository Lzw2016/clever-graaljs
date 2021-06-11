package org.clever.graaljs.core.internal.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.oracle.truffle.api.interop.TruffleObject;
import lombok.extern.slf4j.Slf4j;
import org.clever.graaljs.core.utils.mapper.JacksonMapper;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.Proxy;

import java.math.BigInteger;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/10/19 11:25 <br/>
 */
@Slf4j
public class JacksonMapperSupport {
    private static volatile boolean initialized = false;
    private static JacksonMapper Http_Api_Jackson_Mapper;
    private static JacksonMapper Redis_Jackson_Mapper;

    /**
     * 初始化内部使用的 JacksonMapper
     */
    public static synchronized void initGraalModule() {
        if (initialized) {
            return;
        }
        initialized = true;
        SimpleModule module = new SimpleModule();
        module.addSerializer(Value.class, ValueSerializer.instance);
        module.addSerializer(TruffleObject.class, ToStringSerializer.instance);
        module.addSerializer(Proxy.class, HostWrapperSerializer.instance);
        try {
            Class<?> clazz = Class.forName("com.oracle.truffle.polyglot.HostWrapper");
            module.addSerializer(clazz, HostWrapperSerializer.instance);
        } catch (ClassNotFoundException e) {
            log.warn("类型com.oracle.truffle.polyglot.HostWrapper加载失败", e);
        }
        JacksonMapper.getInstance().getMapper().registerModules(module);
    }

    /**
     * HTTP API 数据序列化使用的JacksonMapper
     */
    public static synchronized JacksonMapper getHttpApiJacksonMapper() {
        if (Http_Api_Jackson_Mapper != null) {
            return Http_Api_Jackson_Mapper;
        }
        initGraalModule();
        ObjectMapper objectMapper = JacksonMapper.getInstance().getMapper().copy();
        SimpleModule module = new SimpleModule();
        module.addSerializer(BigInteger.class, ToStringSerializer.instance);
        module.addSerializer(Long.class, ToStringSerializer.instance);
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModules(module);
        Http_Api_Jackson_Mapper = new JacksonMapper(objectMapper);
        return Http_Api_Jackson_Mapper;
    }

    /**
     * Redis客户端数据序列化使用的JacksonMapper
     */
    public static synchronized JacksonMapper getRedisJacksonMapper() {
        if (Redis_Jackson_Mapper != null) {
            return Redis_Jackson_Mapper;
        }
        initGraalModule();
        ObjectMapper objectMapper = JacksonMapper.getInstance().getMapper().copy();
        // SimpleModule module = new SimpleModule();
        // module.addSerializer(BigInteger.class, ToStringSerializer.instance);
        // module.addSerializer(Long.class, ToStringSerializer.instance);
        // module.addSerializer(Long.TYPE, ToStringSerializer.instance);
        // objectMapper.registerModules(module);
        Redis_Jackson_Mapper = new JacksonMapper(objectMapper);
        return Redis_Jackson_Mapper;
    }
}