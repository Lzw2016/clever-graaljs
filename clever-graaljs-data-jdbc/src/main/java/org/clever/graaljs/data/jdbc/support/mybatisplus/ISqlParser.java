package org.clever.graaljs.data.jdbc.support.mybatisplus;

import org.clever.dynamic.sql.reflection.MetaObject;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/12 11:08 <br/>
 */
@SuppressWarnings("ALL")
public interface ISqlParser {

    /**
     * 解析 SQL 方法
     *
     * @param metaObject 元对象
     * @param sql        SQL 语句
     * @return SQL 信息
     */
    SqlInfo parser(MetaObject metaObject, String sql);

    /**
     * <p>
     * 是否执行 SQL 解析 parser 方法
     * </p>
     *
     * @param metaObject 元对象
     * @param sql        SQL 语句
     * @return SQL 信息
     */
    default boolean doFilter(final MetaObject metaObject, final String sql) {
        // 默认 true 执行 SQL 解析, 可重写实现控制逻辑
        return true;
    }
}
