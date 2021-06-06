package org.clever.graaljs.core.internal;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.*;

/**
 * 方法命名规则 <br/>
 * 1. as***: 把对象转成Java对象 <br/>
 * 2. from***: 把Java对象转成Script对象 <br/>
 * <p>
 * 作者：lizw <br/>
 * 创建时间：2020/07/31 10:43 <br/>
 *
 * @param <T> script引擎对象类型
 */
public abstract class Interop<T> {
    private static final String[] Date_Patterns = {
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd HH:mm:ss.SSS",
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            "EEE MMM dd HH:mm:ss zzz yyyy",
            "yyyy-MM-dd", "HH:mm:ss",
    };

    protected Interop() {
    }

    public Class<?> getClass(String className) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException ignored) {
        }
        return clazz;
    }

    @SneakyThrows
    public Object newJObject(String className) {
        Class<?> clazz = Class.forName(className);
        return clazz.getDeclaredConstructor().newInstance();
    }

    @SneakyThrows
    public Object newJObject(Class<?> clazz) {
        return clazz.getDeclaredConstructor().newInstance();
    }

    // --------------------------------------------------------------------------------------------------- Byte

    public byte asJByte(byte b) {
        return b;
    }

    public byte asJByte(String b) {
        return Byte.parseByte(b);
    }

    // --------------------------------------------------------------------------------------------------- Short

    public short asJShort(short s) {
        return s;
    }

    public short asJShort(String s) {
        return Short.parseShort(s);
    }

    // --------------------------------------------------------------------------------------------------- Int

    public int asJInt(int i) {
        return i;
    }

    public int asJInt(String i) {
        return Integer.parseInt(i);
    }

    public Integer asJInt(BigInteger arg) {
        if (arg == null) {
            return null;
        }
        return arg.intValue();
    }

    // --------------------------------------------------------------------------------------------------- Long

    public long asJLong(long l) {
        return l;
    }

    public long asJLong(String l) {
        return Long.parseLong(l);
    }

    public float asJLong(float f) {
        return Float.valueOf(f).longValue();
    }

    // --------------------------------------------------------------------------------------------------- Double

    public double asJDouble(double d) {
        return d;
    }

    public double asJDouble(String d) {
        return Double.parseDouble(d);
    }

    // --------------------------------------------------------------------------------------------------- Float

    public float asJFloat(double d) {
        return new Double(d).floatValue();
    }

    public float asJFloat(String d) {
        return Float.parseFloat(d);
    }

    // --------------------------------------------------------------------------------------------------- Boolean

    public boolean asJBoolean(boolean b) {
        return b;
    }

    public boolean asJBoolean(String b) {
        return Boolean.parseBoolean(b);
    }

    // --------------------------------------------------------------------------------------------------- Char

    public char asJChar(char c) {
        return c;
    }

    // --------------------------------------------------------------------------------------------------- String

    public String asJString(String str) {
        return str;
    }

    // --------------------------------------------------------------------------------------------------- Date

    /**
     * 创建Java java.util.Date 对象,支持以下格式<br />
     * "yyyy-MM-dd HH:mm:ss" <br />
     * "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" <br />
     * "EEE MMM dd HH:mm:ss zzz yyyy" <br />
     * "yyyy-MM-dd" <br />
     * "HH:mm:ss" <br />
     */
    @SneakyThrows
    public Date asJDate(String arg) {
        if (StringUtils.isBlank(arg)) {
            return null;
        }
        return DateUtils.parseDate(arg, Date_Patterns);
    }

    public Date asJDate(long arg) {
        return new Date(arg);
    }

    public Date asJDate(Timestamp arg) {
        if (arg == null) {
            return null;
        }
        return new Date(arg.getTime());
    }

    public Date asJDate(Time arg) {
        if (arg == null) {
            return null;
        }
        return new Date(arg.getTime());
    }

    public Date asJDate(java.sql.Date arg) {
        if (arg == null) {
            return null;
        }
        return new Date(arg.getTime());
    }

    public abstract Date asJDate(T arg);

    // --------------------------------------------------------------------------------------------------- BigDecimal

    public BigDecimal asJBigDecimal(String arg) {
        return new BigDecimal(arg);
    }

    // --------------------------------------------------------------------------------------------------- BigInteger

    public BigInteger asJBigInteger(String arg) {
        return new BigInteger(arg);
    }

    // --------------------------------------------------------------------------------------------------- List

    public List<Object> asJList(Object... args) {
        if (args == null) {
            return new ArrayList<>();
        }
        List<Object> list = new ArrayList<>(args.length);
        Collections.addAll(list, args);
        return list;
    }

    public List<Object> asJList(Object arg) {
        if (arg == null) {
            return new ArrayList<>();
        }
        if (arg.getClass().isArray()) {
            Object[] array = (Object[]) arg;
            List<Object> list = new ArrayList<>(array.length);
            Collections.addAll(list, array);
            return list;
        } else {
            List<Object> list = new ArrayList<>(1);
            list.add(arg);
            return list;
        }
    }

    // --------------------------------------------------------------------------------------------------- Set

    public Set<Object> asJSet(Object... args) {
        if (args == null) {
            return new HashSet<>();
        }
        Set<Object> set = new HashSet<>(args.length);
        Collections.addAll(set, args);
        return set;
    }

    public Set<Object> asJSet(Object arg) {
        if (arg == null) {
            return new HashSet<>();
        }
        if (arg.getClass().isArray()) {
            Object[] array = (Object[]) arg;
            Set<Object> set = new HashSet<>(array.length);
            Collections.addAll(set, array);
            return set;
        } else {
            Set<Object> set = new HashSet<>(1);
            set.add(arg);
            return set;
        }
    }

    // --------------------------------------------------------------------------------------------------- Map

    public abstract Map<Object, Object> asJMap(T arg);

    // ---------------------------------------------------------------------------------------------------

    // TODO 补充常用类型 asJava对象

    public String toJString(Object obj) {
        return String.valueOf(obj);
    }

    // --------------------------------------------------------------------------------------------------- fromJDate

    public Object fromJDate(LocalDate date) {
        return date;
    }

    public Object fromJDate(Date date) {
        return date;
    }

    public Object fromJDate(java.sql.Date date) {
        return date;
    }

    public Object fromJDate(Duration duration) {
        return duration;
    }

    public Object fromJDate(Instant instant) {
        return instant;
    }

    public Object fromJDate(LocalTime localTime) {
        return localTime;
    }

    public Object fromJDate(ZoneId zoneId) {
        return zoneId;
    }

    // --------------------------------------------------------------------------------------------------- fromJList

    public Object fromJList(List<Object> values) {
        return values;
    }

    public Object fromJList(Object... values) {
        if (values == null) {
            return new ArrayList<>();
        }
        List<Object> list = new ArrayList<>(values.length);
        Collections.addAll(list, values);
        return list;
    }

    // --------------------------------------------------------------------------------------------------- fromJArray

    public Object fromJArray(List<Object> values) {
        if (values == null) {
            return null;
        }
        return values.toArray(new Object[0]);
    }

    public Object fromJArray(Object... values) {
        return values;
    }

    // --------------------------------------------------------------------------------------------------- fromJMap

    public Object fromJMap(Map<String, Object> map) {
        return map;
    }

    // ---------------------------------------------------------------------------------------------------

    // TODO 补充常用类型 fromJava对象
}
