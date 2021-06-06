package org.clever.graaljs.core;

import org.clever.graaljs.core.utils.Assert;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/03 11:25 <br/>
 */
public class ScriptObject {
    /**
     * Script引擎对应的对象值
     */
    private final Value original;

    public ScriptObject(Value original) {
        Assert.notNull(original, "参数original不能为空");
        this.original = original;
    }

    /**
     * 获取原始script引擎对象
     */
    public Value originalValue() {
        return original;
    }

    /**
     * 获取ScriptContext
     */
    public Context getContext() {
        return original.getContext();
    }

    /**
     * 获取script对象所有成员名称
     */
    public Collection<String> getMemberNames() {
        return original.getMemberKeys();
    }

    /**
     * 获取script对象成员值
     *
     * @param name 成员名称
     */
    public Object getMember(String name) {
        return original.getMember(name);
    }

    /**
     * 是否存在成员
     *
     * @param name 成员名称
     */
    public boolean hasMember(String name) {
        return original.hasMember(name);
    }

    /**
     * 获取script对象所有成员值
     */
    public Collection<Object> getMembers() {
        List<Object> list = new ArrayList<>(size());
        if (original.hasArrayElements()) {
            for (int i = 0; i < original.getArraySize(); i++) {
                list.add(original.getArrayElement(i));
            }
        } else {
            for (String memberKey : original.getMemberKeys()) {
                list.add(original.getMember(memberKey));
            }
        }
        return list;
    }

    /**
     * 调用成员函数
     *
     * @param functionName 成员函数名称
     * @param args         参数
     */
    public Object callMember(String functionName, Object... args) {
        Context context = getContext();
        Object res;
        try {
            context.enter();
            res = original.invokeMember(functionName, args);
        } finally {
            if (context != null) {
                context.leave();
            }
        }
        return res;
    }

    /**
     * 删除script对象成员属性
     *
     * @param name 成员名称
     */
    public void delMember(String name) {
        original.removeMember(name);
    }

    /**
     * 设置script对象成员值
     *
     * @param name  成员名称
     * @param value 成员属值
     */
    public void setMember(String name, Object value) {
        original.putMember(name, value);
    }

    /**
     * script对象集合大小或者属性成员数量
     */
    public int size() {
        if (original.hasArrayElements()) {
            long size = original.getArraySize();
            if (size > Integer.MAX_VALUE) {
                throw new ClassCastException("数组 length=" + size + " 太长(超出范围)");
            }
            return (int) size;
        }
        return original.getMemberKeys().size();
    }

    /**
     * 判断当前脚本对象能否执行
     */
    public boolean canExecute() {
        return original.canExecute();
    }

    /**
     * 执行当前脚本对象
     *
     * @param arguments 参数
     */
    public Value execute(Object... arguments) {
        Assert.isTrue(original.canExecute(), "当前脚本对象不能执行");
        return original.execute(arguments);
    }
}
