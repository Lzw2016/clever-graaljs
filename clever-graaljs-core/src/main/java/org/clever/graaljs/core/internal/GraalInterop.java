package org.clever.graaljs.core.internal;

import org.clever.graaljs.core.internal.proxy.ArrayListProxy;
import org.clever.graaljs.core.internal.proxy.HashMapProxy;
import org.clever.graaljs.core.internal.utils.InteropScriptToJavaUtils;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.*;

import java.time.*;
import java.util.*;

public class GraalInterop extends Interop<Value> {
    public static final GraalInterop Instance = new GraalInterop();

    private GraalInterop() {
    }

    // --------------------------------------------------------------------------------------------------- Date

    @Override
    public Date asJDate(Value arg) {
        if (arg == null) {
            return null;
        }
        final boolean isDate = arg.isDate();
        final boolean isTime = arg.isTime();
        final boolean isInstant = arg.isInstant();
        if (isInstant) {
            return Date.from(arg.asInstant());
        }
        if (isDate && isTime) {
            LocalDateTime localDateTime = LocalDateTime.of(arg.asDate(), arg.asTime());
            return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        }
        throw new ClassCastException("参数 arg=:" + arg.toString() + "不能转换成Date类型");
    }

    // --------------------------------------------------------------------------------------------------- Map

    @Override
    public Map<Object, Object> asJMap(Value arg) {
        if (arg == null) {
            return new HashMap<>();
        }
        Map<Object, Object> map;
        if (arg.hasArrayElements()) {
            long size = arg.getArraySize();
            if (size > Integer.MAX_VALUE) {
                throw new ClassCastException("数组 arg.length=" + size + " 太长无法转换成Map");
            }
            map = new HashMap<>((int) size);
            for (int i = 0; i < size; i++) {
                map.put(i, InteropScriptToJavaUtils.Instance.deepToJavaObject(arg.getArrayElement(i)));
            }
        } else {
            Set<String> keys = arg.getMemberKeys();
            map = new HashMap<>(keys.size());
            for (String key : keys) {
                map.put(key, InteropScriptToJavaUtils.Instance.deepToJavaObject(arg.getMember(key)));
            }
        }
        return map;
    }

    // --------------------------------------------------------------------------------------------------- fromJDate

    @Override
    public ProxyDate fromJDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return ProxyDate.from(date);
    }

    @Override
    public ProxyDate fromJDate(Date date) {
        if (date == null) {
            return null;
        }
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        return ProxyDate.from(localDate);
    }

    @Override
    public ProxyDate fromJDate(java.sql.Date date) {
        if (date == null) {
            return null;
        }
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        return fromJDate(localDate);
    }

    @Override
    public ProxyDuration fromJDate(Duration duration) {
        if (duration == null) {
            return null;
        }
        return ProxyDuration.from(duration);
    }

    @Override
    public ProxyInstant fromJDate(Instant instant) {
        if (instant == null) {
            return null;
        }
        return ProxyInstant.from(instant);
    }

    @Override
    public ProxyTime fromJDate(LocalTime localTime) {
        if (localTime == null) {
            return null;
        }
        return ProxyTime.from(localTime);
    }

    @Override
    public ProxyTimeZone fromJDate(ZoneId zoneId) {
        if (zoneId == null) {
            return null;
        }
        return ProxyTimeZone.from(zoneId);
    }

    // --------------------------------------------------------------------------------------------------- fromJList

    @Override
    public ArrayListProxy fromJList(List<Object> values) {
        if (values == null) {
            return null;
        }
        return new ArrayListProxy(values);
    }

    @Override
    public ArrayListProxy fromJList(Object... values) {
        List<Object> list;
        if (values == null) {
            list = new ArrayList<>();
        } else {
            list = new ArrayList<>(values.length);
            Collections.addAll(list, values);
        }
        return new ArrayListProxy(list);
    }

    // --------------------------------------------------------------------------------------------------- fromJArray

    @Override
    public ArrayListProxy fromJArray(List<Object> values) {
        return new ArrayListProxy(values);
    }

    @Override
    public ProxyArray fromJArray(Object... values) {
        if (values == null) {
            return null;
        }
        return ProxyArray.fromArray(values);
    }

    // --------------------------------------------------------------------------------------------------- fromJMap

    @Override
    public HashMapProxy fromJMap(Map<String, Object> values) {
        if (values == null) {
            return null;
        }
        return new HashMapProxy(values);
    }

//    ProxyInstantiable
//    ProxyExecutable
}
