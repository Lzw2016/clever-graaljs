package org.clever.graaljs.core.internal.proxy;

import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyObject;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/10/10 16:57 <br/>
 */
public class LinkedHashMapProxy extends LinkedHashMap<String, Object> implements ProxyObject {
    protected final MapProxy mapProxy = new MapProxy(this);

    public LinkedHashMapProxy() {
    }

    public LinkedHashMapProxy(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public LinkedHashMapProxy(int initialCapacity) {
        super(initialCapacity);
    }


    public LinkedHashMapProxy(Map<? extends String, ?> m) {
        super(m);
    }

    public LinkedHashMapProxy(int initialCapacity, float loadFactor, boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
    }

    @Override
    public Object getMember(String key) {
        return mapProxy.getMember(key);
    }

    @Override
    public Object getMemberKeys() {
        return mapProxy.getMemberKeys();
    }

    @Override
    public boolean hasMember(String key) {
        return mapProxy.hasMember(key);
    }

    @Override
    public void putMember(String key, Value value) {
        mapProxy.putMember(key, value);
    }

    @Override
    public boolean removeMember(String key) {
        return mapProxy.removeMember(key);
    }
}
