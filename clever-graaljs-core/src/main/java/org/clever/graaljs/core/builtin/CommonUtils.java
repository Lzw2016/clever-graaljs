package org.clever.graaljs.core.builtin;

import java.util.Date;
import java.util.Objects;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/28 22:32 <br/>
 */
public class CommonUtils {
    public static final CommonUtils Instance = new CommonUtils();

    private CommonUtils() {
    }

    /**
     * 休眠一段时间
     *
     * @param millis 毫秒
     */
    public void sleep(Number millis) throws InterruptedException {
        Thread.sleep(millis.longValue());
    }

    /**
     * 放弃当前CPU使用权(当前线程放弃本次CPU时间)
     */
    public void yield() {
        Thread.yield();
    }

    /**
     * 获取对象的 hashcode
     */
    public Integer hashCode(Object object) {
        if (object == null) {
            return null;
        }
        return object.hashCode();
    }

    /**
     * 两个对象 equals
     */
    public boolean equals(Object a, Object b) {
        return Objects.equals(a, b);
    }

    /**
     * 判断两个对象是不是同一个对象(内存地址相同)
     */
    public boolean same(Object a, Object b) {
        return a == b;
    }

    /**
     * 获取当前时间搓(毫秒)
     */
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间 Date
     */
    public Date now() {
        return new Date();
    }

    /**
     * 获取对象的Java类型
     */
    public Class<?> getClass(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.getClass();
    }

    /**
     * 获取对象的Java类型名称
     */
    public String getClassName(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.getClass().getName();
    }

    /**
     * 返回对象的字符串表形式
     */
    public String toString(Object obj) {
        return String.valueOf(obj);
    }

    /**
     * 返回对象的字符串表形式
     *
     * @param obj 指定对象
     * @param def 默认字符串
     */
    public String toString(Object obj, String def) {
        return obj == null ? def : String.valueOf(obj);
    }
}
