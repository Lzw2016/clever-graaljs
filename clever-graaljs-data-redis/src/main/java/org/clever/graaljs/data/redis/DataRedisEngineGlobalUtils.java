package org.clever.graaljs.data.redis;

import org.clever.graaljs.data.redis.builtin.constant.NodeTypeEnum;
import org.clever.graaljs.data.redis.builtin.constant.RedisDataTypeEnum;
import org.clever.graaljs.data.redis.builtin.wrap.RedisDatabase;

import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/16 17:36 <br/>
 */
public class DataRedisEngineGlobalUtils {
    /**
     * 设置引擎默认的全局对象
     */
    public static void putGlobalObjects(Map<String, Object> contextMap) {
        // 枚举值
        contextMap.put("RedisDataType", RedisDataTypeEnum.RedisDataType.Instance);
        contextMap.put("NodeType", NodeTypeEnum.NodeType.Instance);
        // redis
        contextMap.put("RedisDatabase", RedisDatabase.Instance);
    }
}
