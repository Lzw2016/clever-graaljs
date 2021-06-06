package org.clever.graaljs.core.internal.proxy;

import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/10/10 17:06 <br/>
 */
public class HashMapProxy extends HashMap<String, Object> implements ProxyObject {
    protected final MapProxy mapProxy = new MapProxy(this);

    public HashMapProxy() {
    }

    public HashMapProxy(int initialCapacity) {
        super(initialCapacity);
    }

    public HashMapProxy(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public HashMapProxy(Map<? extends String, ?> m) {
        super(m);
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
