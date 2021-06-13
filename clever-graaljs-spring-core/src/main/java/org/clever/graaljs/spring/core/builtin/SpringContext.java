package org.clever.graaljs.spring.core.builtin;

import org.clever.graaljs.spring.core.utils.spring.SpringContextHolder;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/28 22:33 <br/>
 */
public class SpringContext {
    public static final SpringContext Instance = new SpringContext();

    private SpringContext() {
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     *
     * @param name Bean名称
     * @return 返回Bean对象
     */
    public <T> T getBean(String name) {
        return SpringContextHolder.getBean(name);
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     *
     * @param require 是否等待获取ApplicationContext
     * @param name    Bean名称
     * @return 返回Bean对象
     */
    public <T> T getBean(boolean require, String name) {
        return SpringContextHolder.getBean(require, name);
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     *
     * @param requiredType Bean类型
     * @return 返回Bean对象
     */
    public <T> T getBean(Class<T> requiredType) {
        return SpringContextHolder.getBean(requiredType);
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     *
     * @param require      是否等待获取ApplicationContext
     * @param requiredType Bean类型
     * @return 返回Bean对象
     */
    public <T> T getBean(boolean require, Class<T> requiredType) {
        return SpringContextHolder.getBean(require, requiredType);
    }
}
