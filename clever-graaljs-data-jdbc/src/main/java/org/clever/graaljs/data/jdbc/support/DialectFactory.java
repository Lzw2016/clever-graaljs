package org.clever.graaljs.data.jdbc.support;

import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.core.utils.Assert;
import org.clever.graaljs.data.common.support.mybatisplus.IPage;
import org.clever.graaljs.data.jdbc.dialects.*;
import org.clever.graaljs.data.jdbc.support.mybatisplus.DbType;
import org.clever.graaljs.data.jdbc.support.mybatisplus.ExceptionUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 作者： lzw<br/>
 * 创建时间：2019-10-03 12:20 <br/>
 */
public class DialectFactory {
    /**
     * 方言缓存
     */
    private static final Map<String, IDialect> DIALECT_CACHE = new ConcurrentHashMap<>();

    /**
     * Physical Page Interceptor for all the queries with parameter
     *
     * @param page         翻页对象
     * @param buildSql     编译 SQL
     * @param paramMap     Sql参数
     * @param dbType       数据类型
     * @param dialectClazz 数据库方言
     * @return 分页模型
     */
    public static String buildPaginationSql(IPage<?> page, String buildSql, Map<String, Object> paramMap, DbType dbType, String dialectClazz) {
        return getDialect(dbType, dialectClazz).buildPaginationSql(buildSql, page.offset(), page.getSize(), paramMap);
    }

    /**
     * Physical Page Interceptor for all the queries with parameter
     *
     * @param page         翻页对象
     * @param buildSql     编译 SQL
     * @param dbType       数据类型
     * @param dialectClazz 数据库方言
     */
    public static String buildPaginationSql(IPage<?> page, String buildSql, DbType dbType, String dialectClazz) {
        return getDialect(dbType, dialectClazz).buildPaginationSql(buildSql, page.offset(), page.getSize());
    }

    /**
     * 获取数据库方言
     *
     * @param dbType       数据库类型
     * @param dialectClazz 自定义方言实现类
     * @return ignore
     */
    public static IDialect getDialect(DbType dbType, String dialectClazz) {
        IDialect dialect = DIALECT_CACHE.get(dbType.getDb());
        if (null == dialect) {
            // 自定义方言
            if (StringUtils.isNotBlank(dialectClazz)) {
                dialect = DIALECT_CACHE.get(dialectClazz);
                if (null != dialect) {
                    return dialect;
                }
                try {
                    Class<?> clazz = Class.forName(dialectClazz);
                    if (IDialect.class.isAssignableFrom(clazz)) {
                        dialect = (IDialect) clazz.newInstance();
                        DIALECT_CACHE.put(dialectClazz, dialect);
                    }
                } catch (ClassNotFoundException e) {
                    throw ExceptionUtils.mpe("Class : %s is not found", dialectClazz);
                } catch (IllegalAccessException | InstantiationException e) {
                    throw ExceptionUtils.mpe("Class : %s can not be instance", dialectClazz);
                }
            } else {
                // 缓存方言
                dialect = getDialectByDbType(dbType);
                DIALECT_CACHE.put(dbType.getDb(), dialect);
            }
            /* 未配置方言则抛出异常 */
            Assert.notNull(dialect, "The value of the dialect property in mybatis configuration.xml is not defined.");
        }
        return dialect;
    }

    /**
     * 根据数据库类型选择不同分页方言
     *
     * @param dbType 数据库类型
     * @return 分页语句组装类
     */
    private static IDialect getDialectByDbType(DbType dbType) {
        switch (dbType) {
            case MYSQL:
                return new MySqlDialect();
            case MARIADB:
                return new MariaDBDialect();
            case ORACLE:
                return new OracleDialect();
            case DB2:
                return new DB2Dialect();
            case H2:
                return new H2Dialect();
            case SQL_SERVER:
                return new SQLServerDialect();
            case SQL_SERVER2005:
                return new SQLServer2005Dialect();
            case POSTGRE_SQL:
                return new PostgreDialect();
            case HSQL:
                return new HSQLDialect();
            case SQLITE:
                return new SQLiteDialect();
            case DM:
                return new DmDialect();
            default:
                throw ExceptionUtils.mpe("The Database's IDialect Not Supported!");
        }
    }
}
