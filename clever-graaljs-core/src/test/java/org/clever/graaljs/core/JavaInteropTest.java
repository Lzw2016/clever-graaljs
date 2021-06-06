package org.clever.graaljs.core;

import lombok.extern.slf4j.Slf4j;
import org.clever.graaljs.core.internal.proxy.HashMapProxy;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyObject;

import java.util.*;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/30 20:40 <br/>
 */
@Slf4j
public class JavaInteropTest {
    public static final JavaInteropTest Instance = new JavaInteropTest();

    private JavaInteropTest() {
    }

    // ------------------------------------------------------------------------------------------------------------------ java类型

    public byte getByte() {
        return 1;
    }

    public short getShort() {
        return 2;
    }

    public int getInt() {
        return 123;
    }

    public long getLong() {
        return 456;
    }

    public float getFloat() {
        return 123.456F;
    }

    public double getDouble() {
        return 123.456D;
    }

    public boolean getBoolean() {
        return true;
    }

    public char getChar() {
        return 'A';
    }

    public String getString() {
        return "aaa";
    }

    public Date getDate() {
        return new Date();
    }

    public String[] getArray() {
        return new String[]{"aaa", "bbb", "ccc"};
    }

    public List<String> getList() {
        return new ArrayList<String>() {{
            add("aaa");
            add("bbb");
            add("ccc");
        }};
    }

    public Set<String> getSet() {
        return new HashSet<String>() {{
            add("aaa");
            add("bbb");
            add("ccc");
        }};
    }

    public Map<String, Object> getMap() {
        return new HashMap<String, Object>() {{
            put("int", 1);
            put("float", 1.1F);
            put("double", 1.3D);
            put("long", 123L);
            put("char", 'A');
            put("string", "aaa");
            put("boolean", false);
        }};
    }

    public ProxyObject getProxyMap() {
        return ProxyObject.fromMap(getMap());
    }

    public ProxyObject getProxyMap2() {
        return new HashMapProxy(getMap());
    }

    // ------------------------------------------------------------------------------------------------------------------ 接入JavaScript类型

    public void setByte(byte b) {
        log.info("# byte -> {}", b);
    }

    public void setShort(short s) {
        log.info("# short -> {}", s);
    }

    public void setInt(int i) {
        log.info("# int -> {}", i);
    }

    public void setLong(long l) {
        log.info("# long -> {}", l);
    }

    public void setFloat(float f) {
        log.info("# float -> {}", f);
    }

    public void setDouble(double d) {
        log.info("# double -> {}", d);
    }

    public void setBoolean(boolean b) {
        log.info("# boolean -> {}", b);
    }

    public void setChar(char c) {
        log.info("# char -> {}", c);
    }

    public void setString(String str) {
        log.info("# String -> {}", str);
    }

    public void setDate(Date date) {
        log.info("# date -> {}", date);
    }

    public void setArray(String[] array) {
        log.info("# array -> {}", Arrays.toString(array));
    }

    public void setList(List<String> list) {
        log.info("# list -> {}", list);
    }

    public void setSet(Set<String> set) {
        log.info("# set -> {}", set);
    }

    public void setMap(Map<String, Object> map) {
        log.info("# map -> {}", map);
        map.forEach((s, o) -> {
            log.info("# map -> {}={} | {}", s, o, o.getClass());
        });
        // map -> str=aaa | class java.lang.String
        // map -> int=123 | class java.lang.Integer
        // map -> b=false | class java.lang.Boolean
        // map -> d=123.456 | class java.lang.Double
    }

    public void setValue(Value value) {
        log.info("# value -> {}", value);
        value.getMemberKeys().forEach(s -> {
            log.info("# value -> {}={} | {}", s, value.getMember(s), value.getMember(s).getClass());
        });
        // value -> str=aaa | class org.graalvm.polyglot.Value
        // value -> int=123 | class org.graalvm.polyglot.Value
        // value -> b=false | class org.graalvm.polyglot.Value
        // value -> d=123.456 | class org.graalvm.polyglot.Value
    }

    public List<ProxyObject> getProxyMap3() {
        List<ProxyObject> list = new ArrayList<>();
        list.add(new HashMapProxy(getMap()));
        list.add(new HashMapProxy(getMap()));
        list.add(new HashMapProxy(getMap()));
        return list;
    }

    public void setList2(List<Object> list) {
        log.info("# list -> {}", list);
    }
}
