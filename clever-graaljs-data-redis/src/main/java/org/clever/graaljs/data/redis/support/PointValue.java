package org.clever.graaljs.data.redis.support;

import lombok.Data;

import java.io.Serializable;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/11 17:16 <br/>
 */
@Data
public class PointValue implements Serializable {
    /**
     * x轴位置(经度)
     */
    private double x;
    /**
     * y轴位置(维度)
     */
    private double y;

    /**
     * 数据值
     */
    private Object value;

    public PointValue() {
    }

    public PointValue(double x, double y, Object value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }
}
