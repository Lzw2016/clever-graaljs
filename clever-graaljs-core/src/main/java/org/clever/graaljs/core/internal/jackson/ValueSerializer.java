package org.clever.graaljs.core.internal.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.oracle.truffle.api.interop.TruffleObject;
import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.core.internal.utils.InteropScriptToJavaUtils;
import org.graalvm.polyglot.Value;

import java.io.IOException;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/08/29 08:05 <br/>
 */
public class ValueSerializer extends JsonSerializer<Value> {
    public final static ValueSerializer instance = new ValueSerializer();

    @Override
    public void serialize(Value value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }
        Object obj = InteropScriptToJavaUtils.Instance.toJavaObject(value);
        final String className = obj == null ? null : obj.getClass().getName();
        if (StringUtils.isNotBlank(className)
                && !HostWrapperSerializer.isSupport(className)
                && (obj instanceof TruffleObject || className.startsWith("com.oracle.truffle.") || className.startsWith("org.graalvm."))) {
            gen.writeString(String.valueOf(obj));
        } else {
            gen.writeObject(obj);
        }
    }
}
