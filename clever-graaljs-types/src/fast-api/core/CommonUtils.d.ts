/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/28 22:32 <br/>
 */
interface CommonUtils extends JObject {
    /**
     * 休眠一段时间
     *
     * @param millis 毫秒
     */
    sleep(millis: number): void;

    /**
     * 放弃当前CPU使用权(当前线程放弃本次CPU时间)
     */
    yield(): void;

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

    /**
     * 返回对象的字符串表形式
     */
    toString(obj: any): JString;

    /**
     * 返回对象的字符串表形式
     *
     * @param obj 指定对象
     * @param def 默认字符串
     */
    toString(obj: any, def: JString): JString;
}

declare const CommonUtils: CommonUtils;
