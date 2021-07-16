package org.clever.graaljs.data.redis.builtin.constant;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/16 17:35 <br/>
 */
public interface RedisDataTypeEnum {
    final class RedisDataType {
        public static final RedisDataType Instance = new RedisDataType();

        private RedisDataType() {
        }

        /**
         * key不存在
         */
        public final String None = "none";
        /**
         * String结构
         */
        public final String String = "string";
        /**
         * List结构
         */
        public final String List = "list";
        /**
         * Set结构
         */
        public final String Set = "set";
        /**
         * ZSet结构
         */
        public final String ZSet = "zset";
        /**
         * Hash结构
         */
        public final String Hash = "hash";
    }
}
