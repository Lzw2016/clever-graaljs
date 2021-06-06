package org.clever.graaljs.core.internal.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.clever.graaljs.core.internal.utils.InteropScriptToJavaUtils;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyArray;
import org.graalvm.polyglot.proxy.ProxyObject;

import java.io.IOException;
import java.util.*;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/08/26 18:37 <br/>
 */
public class HostWrapperSerializer extends JsonSerializer<Object> {
    public static final String FunctionProxyHandler_Class = "com.oracle.truffle.polyglot.FunctionProxyHandler";
    public static final String ObjectProxyHandler_Class = "com.oracle.truffle.polyglot.ObjectProxyHandler";
    public static final String PolyglotFunction_Class = "com.oracle.truffle.polyglot.PolyglotFunction";
    public static final String PolyglotList_Class = "com.oracle.truffle.polyglot.PolyglotList";
    public static final String PolyglotListAndFunction_Class = "com.oracle.truffle.polyglot.PolyglotListAndFunction";
    public static final String PolyglotMap_Class = "com.oracle.truffle.polyglot.PolyglotMap";
    public static final String PolyglotMapAndFunction_Class = "com.oracle.truffle.polyglot.PolyglotMapAndFunction";
    public static final String ProxyObject_Class = "org.graalvm.polyglot.proxy.ProxyObject";
    public static final String ProxyArray_Class = "org.graalvm.polyglot.proxy.ProxyArray";

    public static final Set<String> Support_Class = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList(
                    FunctionProxyHandler_Class,
                    ObjectProxyHandler_Class,
                    PolyglotFunction_Class,
                    PolyglotList_Class,
                    PolyglotListAndFunction_Class,
                    PolyglotMap_Class,
                    PolyglotMapAndFunction_Class
            )
    ));

    public final static HostWrapperSerializer instance = new HostWrapperSerializer();

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }
        String className = value.getClass().getName();
        if (Objects.equals(FunctionProxyHandler_Class, className)) {
            gen.writeString(String.valueOf(value));
        } else if (Objects.equals(ObjectProxyHandler_Class, className)) {
            gen.writeString(String.valueOf(value));
        } else if (Objects.equals(PolyglotFunction_Class, className)) {
            gen.writeString(String.valueOf(value));
        } else if (Objects.equals(PolyglotList_Class, className)) {
            gen.writeString(String.valueOf(value));
        } else if (Objects.equals(PolyglotListAndFunction_Class, className)) {
            gen.writeString(String.valueOf(value));
        } else if (Objects.equals(PolyglotMap_Class, className) && value instanceof Map) {
            Value val = Value.asValue(value);
            if (val.hasArrayElements() || val.canExecute() || val.isDate() || val.isTime() || val.isInstant() || val.isDuration() || val.isTimeZone()) {
                gen.writeObject(val);
            } else {
                gen.writeObject(new HashMap<>((Map) value));
            }
        } else if (Objects.equals(PolyglotMapAndFunction_Class, className) && value instanceof Map) {
            gen.writeObject(new HashMap<>((Map) value));
        } else if (value instanceof ProxyObject) {
            value = InteropScriptToJavaUtils.unWrapProxyObject((ProxyObject) value);
            gen.writeObject(value);
        } else if (value instanceof ProxyArray) {
            value = InteropScriptToJavaUtils.unWrapProxyArray((ProxyArray) value);
            gen.writeObject(value);
        } else {
            gen.writeString(String.valueOf(value));
        }
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isSupport(final String className) {
        return Support_Class.contains(className) || className.startsWith(ProxyObject_Class) || className.startsWith(ProxyArray_Class);
    }
}
