package org.clever.graaljs.fast.api.dto.response;

import lombok.Data;
import org.clever.graaljs.data.redis.support.RedisDataSourceStatus;
import org.clever.graaljs.data.redis.support.RedisInfo;
import org.clever.graaljs.fast.api.entity.DataSourceConfig;

import java.io.Serializable;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/08 10:11 <br/>
 */
@Data
public class RedisInfoRes implements Serializable {
    private String name;

    private boolean def = false;

    private boolean immutable = false;

    private RedisInfo redisInfo;

    private RedisDataSourceStatus status;

    private DataSourceConfig dataSourceConfig;
}
