package org.clever.graaljs.fast.api.entity;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/14 13:25 <br/>
 */
public interface EnumConstant {
    /**
     * 禁用http请求：0-启用，1-禁用
     */
    int DISABLE_REQUEST_0 = 0;
    /**
     * 禁用http请求：0-启用，1-禁用
     */
    int DISABLE_REQUEST_1 = 1;

    /**
     * 数据类型：0-文件夹，1-文件
     */
    Integer IS_FILE_0 = 0;
    /**
     * 数据类型：0-文件夹，1-文件
     */
    Integer IS_FILE_1 = 1;

    /**
     * 读写权限：0-可读可写，1-只读
     */
    Integer READ_ONLY_0 = 0;
    /**
     * 读写权限：0-可读可写，1-只读
     */
    Integer READ_ONLY_1 = 1;

    /**
     * 所属模块：0-自定义扩展，1-资源文件，2-初始化脚本，3-HTTP API，4-定时任务
     */
    int MODULE_0 = 0;
    /**
     * 所属模块：0-自定义扩展，1-资源文件，2-初始化脚本，3-HTTP API，4-定时任务
     */
    int MODULE_1 = 1;
    /**
     * 所属模块：0-自定义扩展，1-资源文件，2-初始化脚本，3-HTTP API，4-定时任务
     */
    int MODULE_2 = 2;
    /**
     * 所属模块：0-自定义扩展，1-资源文件，2-初始化脚本，3-HTTP API，4-定时任务
     */
    int MODULE_3 = 3;
    /**
     * 所属模块：0-自定义扩展，1-资源文件，2-初始化脚本，3-HTTP API，4-定时任务
     */
    int MODULE_4 = 4;

    /**
     * http请求method，ALL GET HEAD POST PUT DELETE CONNECT OPTIONS TRACE PATCH
     */
    String REQUEST_METHOD_ALL = "ALL";
    /**
     * http请求method，ALL GET HEAD POST PUT DELETE CONNECT OPTIONS TRACE PATCH
     */
    String REQUEST_METHOD_GET = "GET";
    /**
     * http请求method，ALL GET HEAD POST PUT DELETE CONNECT OPTIONS TRACE PATCH
     */
    String REQUEST_METHOD_HEAD = "HEAD";
    /**
     * http请求method，ALL GET HEAD POST PUT DELETE CONNECT OPTIONS TRACE PATCH
     */
    String REQUEST_METHOD_POST = "POST";
    /**
     * http请求method，ALL GET HEAD POST PUT DELETE CONNECT OPTIONS TRACE PATCH
     */
    String REQUEST_METHOD_PUT = "PUT";
    /**
     * http请求method，ALL GET HEAD POST PUT DELETE CONNECT OPTIONS TRACE PATCH
     */
    String REQUEST_METHOD_DELETE = "DELETE";
    /**
     * http请求method，ALL GET HEAD POST PUT DELETE CONNECT OPTIONS TRACE PATCH
     */
    String REQUEST_METHOD_CONNECT = "CONNECT";
    /**
     * http请求method，ALL GET HEAD POST PUT DELETE CONNECT OPTIONS TRACE PATCH
     */
    String REQUEST_METHOD_OPTIONS = "OPTIONS";
    /**
     * http请求method，ALL GET HEAD POST PUT DELETE CONNECT OPTIONS TRACE PATCH
     */
    String REQUEST_METHOD_TRACE = "TRACE";
    /**
     * http请求method，ALL GET HEAD POST PUT DELETE CONNECT OPTIONS TRACE PATCH
     */
    String REQUEST_METHOD_PATCH = "PATCH";

    /**
     * 数据源类型，jdbc redis rabbitmq rocketmq kafka elasticsearch ...
     */
    String TYPE_JDBC = "jdbc";
    /**
     * 数据源类型，jdbc redis rabbitmq rocketmq kafka elasticsearch ...
     */
    String TYPE_REDIS = "redis";
    /**
     * 数据源类型，jdbc redis rabbitmq rocketmq kafka elasticsearch ...
     */
    String TYPE_RABBITMQ = "rabbitmq";
    /**
     * 数据源类型，jdbc redis rabbitmq rocketmq kafka elasticsearch ...
     */
    String TYPE_ROCKETMQ = "rocketmq";
    /**
     * 数据源类型，jdbc redis rabbitmq rocketmq kafka elasticsearch ...
     */
    String TYPE_KAFKA = "kafka";
    /**
     * 数据源类型，jdbc redis rabbitmq rocketmq kafka elasticsearch ...
     */
    String TYPE_ELASTICSEARCH = "elasticsearch";
}
