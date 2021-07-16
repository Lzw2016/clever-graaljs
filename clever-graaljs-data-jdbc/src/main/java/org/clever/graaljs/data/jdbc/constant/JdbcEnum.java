package org.clever.graaljs.data.jdbc.constant;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/16 17:27 <br/>
 */
public interface JdbcEnum {
    final class DbType {
        public static final DbType Instance = new DbType();

        private DbType() {
        }

        public final String MYSQL = "mysql";
        public final String MARIADB = "mariadb";
        public final String ORACLE = "oracle";
        public final String DB2 = "db2";
        public final String H2 = "h2";
        public final String HSQL = "hsql";
        public final String SQLITE = "sqlite";
        public final String POSTGRE_SQL = "postgresql";
        public final String SQL_SERVER2005 = "sqlserver2005";
        public final String SQL_SERVER = "sqlserver";
        public final String DM = "dm";
        public final String OTHER = "other";
    }

    /**
     * 排序类型
     */
    final class SortType {
        public static final SortType Instance = new SortType();

        private SortType() {
        }

        /**
         * 由小到大排序
         */
        public final String ASC = "ASC";
        /**
         * 由大到小排序
         */
        public final String DESC = "DESC";
    }

    /**
     * 事务隔离级别
     */
    final class IsolationLevel {
        public static final IsolationLevel Instance = new IsolationLevel();

        private IsolationLevel() {
        }

        /**
         * 使用底层数据存储的默认隔离级别
         */
        public final byte DEFAULT = -1;
        /**
         * 未提交读<br />
         * 可能发生脏读、不可重复读和幻象读
         */
        public final byte READ_UNCOMMITTED = 1;
        /**
         * 提交读<br />
         * 可能发生不可重复读和幻象读
         */
        public final byte READ_COMMITTED = 2;
        /**
         * 可重读读<br />
         * 可能发生虚读
         */
        public final byte REPEATABLE_READ = 4;
        /**
         * 串行化
         */
        public final byte SERIALIZABLE = 8;
    }

    /**
     * 事务传递特性
     */
    final class Propagation {
        public static final Propagation Instance = new Propagation();

        private Propagation() {
        }

        /**
         * 如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中。这是最常见的选择
         */
        public final byte REQUIRED = 0;
        /**
         * 支持当前事务，如果当前没有事务，就以非事务方式执行
         */
        public final byte SUPPORTS = 1;
        /**
         * 使用当前的事务，如果当前没有事务，就抛出异常
         */
        public final byte MANDATORY = 2;
        /**
         * 新建事务，如果当前存在事务，把当前事务挂起
         */
        public final byte REQUIRES_NEW = 3;
        /**
         * 以非事务方式执行操作，如果当前存在事务，就把当前事务挂起
         */
        public final byte NOT_SUPPORTED = 4;
        /**
         * 以非事务方式执行，如果当前存在事务，则抛出异常
         */
        public final byte NEVER = 5;
        /**
         * 如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行与PROPAGATION_REQUIRED类 似的操作
         */
        public final byte NESTED = 6;
    }
}
