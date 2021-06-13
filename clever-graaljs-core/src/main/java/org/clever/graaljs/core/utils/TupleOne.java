package org.clever.graaljs.core.utils;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/13 23:49 <br/>
 */
public final class TupleOne<A> {
    private static final int SIZE = 1;

    private A value1;

    public static <A> TupleOne<A> creat(final A value1) {
        return new TupleOne<>(value1);
    }

    public TupleOne(final A value1) {
        this.value1 = value1;
    }

    public int getSize() {
        return SIZE;
    }

    public A getValue1() {
        return value1;
    }

    public void setValue1(A val) {
        this.value1 = val;
    }
}

