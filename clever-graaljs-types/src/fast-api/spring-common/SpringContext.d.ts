interface SpringContext {
    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     *
     * @param name Bean名称
     * @return 返回Bean对象
     */
    getBean<T extends object>(name: JString): T;

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     *
     * @param require 是否等待获取ApplicationContext
     * @param name    Bean名称
     * @return 返回Bean对象
     */
    getBean<T extends object>(require: JBoolean, name: JString): T;

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     *
     * @param requiredType Bean类型
     * @return 返回Bean对象
     */
    getBean<T extends object>(requiredType: JClass): T;

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     *
     * @param require      是否等待获取ApplicationContext
     * @param requiredType Bean类型
     * @return 返回Bean对象
     */
    getBean<T extends object>(require: JBoolean, requiredType: JClass): T;
}

declare const SpringContext: SpringContext;
