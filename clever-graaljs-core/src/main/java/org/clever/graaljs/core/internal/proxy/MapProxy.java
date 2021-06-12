package org.clever.graaljs.core.internal.proxy;

import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyObject;

import java.util.Map;
import java.util.Set;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/10/10 14:51 <br/>
 */
// TODO 删除
public class MapProxy implements ProxyObject {
    protected final Map<String, Object> map;

    public MapProxy(Map<String, Object> map) {
        this.map = map;
    }

    @Override
    public Object getMember(String key) {
        // TODO 嵌套Map List
        return map.get(key);
    }

    @Override
    public Object getMemberKeys() {
        final Set<String> keyArray = map.keySet();
        return new ArrayListProxy(keyArray);
    }

    @Override
    public boolean hasMember(String key) {
        return map.containsKey(key);
    }

    @Override
    public void putMember(String key, Value value) {
        // TODO 嵌套Map List
        map.put(key, value.isHostObject() ? value.asHostObject() : value);
    }

    @Override
    public boolean removeMember(String key) {
        if (map.containsKey(key)) {
            map.remove(key);
            return true;
        } else {
            return false;
        }
    }
}
