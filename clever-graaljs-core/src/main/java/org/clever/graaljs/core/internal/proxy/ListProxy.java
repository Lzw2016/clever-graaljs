package org.clever.graaljs.core.internal.proxy;

import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyArray;

import java.util.List;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/10/10 17:12 <br/>
 */
public class ListProxy implements ProxyArray {
    protected final List<Object> list;

    public ListProxy(List<Object> list) {
        this.list = list;
    }

    @Override
    public Object get(long index) {
        // TODO 嵌套List Map
        checkIndex(index);
        return list.get((int) index);
    }

    @Override
    public void set(long index, Value value) {
        // TODO 嵌套List Map
        checkIndex(index);
        Object element = value.isHostObject() ? value.asHostObject() : value;
        list.set((int) index, element);
    }

    @Override
    public boolean remove(long index) {
        checkIndex(index);
        list.remove((int) index);
        return true;
    }

    @Override
    public long getSize() {
        return list.size();
    }

    protected void checkIndex(long index) {
        if (index > Integer.MAX_VALUE || index < 0) {
            throw new ArrayIndexOutOfBoundsException("invalid index. index=" + index);
        }
    }
}
