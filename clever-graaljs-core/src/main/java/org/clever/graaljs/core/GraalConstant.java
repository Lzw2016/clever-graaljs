package org.clever.graaljs.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/21 09:09 <br/>
 */
public interface GraalConstant {
    /**
     * JS时间默认格式
     */
    String JS_Default_Format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    /**
     * 共享的全局变量
     */
    String Engine_Global = "global";

    /**
     * 错误的引擎名字(没有使用GraalVM compiler功能)
     */
    String Error_Engine_Name = "Interpreted";

    /**
     * ECMAScript Version: https://github.com/oracle/graaljs/blob/master/CHANGELOG.md
     */
    String ECMAScript_Version = "2021";

    /**
     * JS 语言ID
     */
    String Js_Language_Id = "js";

    /**
     * 默认不允许访问的Class
     */
    Set<Class<?>> Default_Deny_Access_Class = Collections.unmodifiableSet(
            new HashSet<>(
                    Arrays.asList(
                            System.class,
                            Thread.class
                    )
            )
    );

    /**
     * graaljs引擎每个context执行语句的最大限制
     */
    String ENGINE_EXECUTED_LIMIT = "graaljs_engine_executed_limit";
}
