package org.clever.graaljs.data.redis.support;

import lombok.Getter;

import java.io.Serializable;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/11 16:59 <br/>
 */
@Getter
public class ZSetValue implements Serializable {
    private Object value;

    private double score;

    public ZSetValue() {
    }

    public ZSetValue(Object value, Double score) {
        this.value = value;
        this.score = score;
    }
}
