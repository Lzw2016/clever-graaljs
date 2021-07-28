interface CommonUtils {
    /**
     * 休眠一段时间
     *
     * @param millis 毫秒
     */
    sleep(millis: JLong): void;

    /**
     * 获取对象的 hashcode
     */
    hashCode(object: any): JInt;

    /**
     * 两个对象 equals
     */
    equals(a: any, b: any): JBoolean;

    /**
     * 判断两个对象是不是同一个对象(内存地址相同)
     */
    same(a: any, b: any): JBoolean;

    /**
     * 获取当前时间搓(毫秒)
     */
    currentTimeMillis(): JLong;

    /**
     * 获取当前时间 Date
     */
    now(): JDate;

    /**
     * 获取对象的Java类型
     */
    getClass(obj: any): JClass;

    /**
     * 获取对象的Java类型名称
     */
    getClassName(obj: any): JString;
}

declare const CommonUtils: CommonUtils;
