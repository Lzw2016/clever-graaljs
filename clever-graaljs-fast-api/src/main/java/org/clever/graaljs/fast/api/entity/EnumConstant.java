package org.clever.graaljs.fast.api.entity;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/14 13:25 <br/>
 */
public interface EnumConstant {
    /**
     * 禁用http请求：0-启用，1-禁用
     */
    Integer DISABLE_REQUEST_0 = 0;
    /**
     * 禁用http请求：0-启用，1-禁用
     */
    Integer DISABLE_REQUEST_1 = 1;

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
}
