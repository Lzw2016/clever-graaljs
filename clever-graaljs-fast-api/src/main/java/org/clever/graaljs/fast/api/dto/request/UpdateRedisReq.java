package org.clever.graaljs.fast.api.dto.request;

import lombok.Data;
import org.clever.graaljs.data.redis.builtin.wrap.support.RedisConfig;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/08 12:48 <br/>
 */
@Data
public class UpdateRedisReq implements Serializable {
    @NotBlank
    private String name;

    @NotNull
    private RedisConfig redisConfig;
}
