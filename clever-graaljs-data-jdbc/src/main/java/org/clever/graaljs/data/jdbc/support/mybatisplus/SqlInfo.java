package org.clever.graaljs.data.jdbc.support.mybatisplus;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/12 11:05 <br/>
 */
@SuppressWarnings("ALL")
@Data
@Accessors(chain = true)
public class SqlInfo {
    /**
     * SQL 内容
     */
    private String sql;
    /**
     * 是否排序
     */
    private boolean orderBy = true;

    public static SqlInfo newInstance() {
        return new SqlInfo();
    }
}