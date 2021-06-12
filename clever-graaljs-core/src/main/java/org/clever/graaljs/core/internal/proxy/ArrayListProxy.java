package org.clever.graaljs.core.internal.proxy;

import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyArray;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/10/10 15:02 <br/>
 */
// TODO 删除
public class ArrayListProxy extends ArrayList<Object> implements ProxyArray {
    protected final ListProxy listProxy = new ListProxy(this);

    public ArrayListProxy() {
    }

    public ArrayListProxy(int initialCapacity) {
        super(initialCapacity);
    }

    public ArrayListProxy(Collection<?> c) {
        super(c);
    }

    @Override
    public Object get(long index) {
        return listProxy.get(index);
    }

    @Override
    public void set(long index, Value value) {
        listProxy.set(index, value);
    }

    @Override
    public boolean remove(long index) {
        return listProxy.remove(index);
    }

    @Override
    public long getSize() {
        return listProxy.getSize();
    }
}
