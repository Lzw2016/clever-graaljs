package org.clever.graaljs.data.jdbc;

import org.clever.graaljs.data.jdbc.builtin.wrap.JdbcDatabase;
import org.clever.graaljs.data.jdbc.builtin.wrap.MyBatisJdbcDatabase;
import org.clever.graaljs.data.jdbc.builtin.wrap.SQLBuilder;
import org.clever.graaljs.data.jdbc.constant.JdbcEnum;

import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/16 17:26 <br/>
 */
public class DataJdbcEngineGlobalUtils {
    /**
     * 设置引擎默认的全局对象
     */
    public static void putGlobalObjects(Map<String, Object> contextMap) {
        // 枚举值
        contextMap.put("DbType", JdbcEnum.DbType.Instance);
        contextMap.put("SortType", JdbcEnum.SortType.Instance);
        contextMap.put("IsolationLevel", JdbcEnum.IsolationLevel.Instance);
        contextMap.put("Propagation", JdbcEnum.Propagation.Instance);
        // 基础
        contextMap.put("SQLBuilder", SQLBuilder.Instance);
        // jdbc
        contextMap.put("JdbcDatabase", JdbcDatabase.Instance);
        // mybatis
        contextMap.put("MyBatisJdbcDatabase", MyBatisJdbcDatabase.Instance);
    }
}
