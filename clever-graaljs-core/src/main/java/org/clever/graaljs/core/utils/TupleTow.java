package org.clever.graaljs.core.utils;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/25 21:16 <br/>
 */
public final class TupleTow<A, B> {
    private static final int SIZE = 2;

    private A value1;
    private B value2;

    public static <A, B> TupleTow<A, B> creat(final A value1, final B value2) {
        return new TupleTow<>(value1, value2);
    }

    public TupleTow(final A value1, final B value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public int getSize() {
        return SIZE;
    }

    public B getValue2() {
        return value2;
    }

    public void setValue2(B val) {
        value2 = val;
    }

    public A getValue1() {
        return value1;
    }

    public void setValue1(A val) {
        value1 = val;
    }

    @Override
    public String toString() {
        return "TupleTow{" + "value1=" + value1 + ", value2=" + value2 + '}';
    }
}
