package org.clever.graaljs.spring.core.builtin;

import io.micrometer.core.instrument.*;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/08/05 10:21 <br/>
 */
public class MeterRegistryUtils {
    public static final ConcurrentMap<Meter.Id, Double> GAUGE_VALUE_MAP = new ConcurrentHashMap<>();
    public static final MeterRegistryUtils Instance = new MeterRegistryUtils();

    private final CompositeMeterRegistry meterRegistry = Metrics.globalRegistry;

    private MeterRegistryUtils() {
    }

    /**
     * 获取监控指标收集器
     */
    public MeterRegistry getMeterRegistry() {
        return meterRegistry;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------- gauge

    /**
     * 注册一个瞬时指标值
     *
     * @param number      指标值
     * @param name        指标名称
     * @param tags        指标tag
     * @param description 指标描述
     * @param unit        指标单位
     */
    public void gauge(Double number, String name, Map<String, Object> tags, String description, String unit) {
        Assert.isTrue(number != null, "参数number不能为空");
        Assert.hasText(name, "参数name不能为空");
        final Gauge.Builder<?> builder = Gauge.builder(name, number, Double::doubleValue)
                .tags(toTags(tags))
                .baseUnit(unit)
                .description(description);
        final Gauge gauge = builder.register(meterRegistry);
        GAUGE_VALUE_MAP.compute(gauge.getId(), (id, oldNumber) -> {
            // 指标存在且值发生变化
            if (oldNumber != null && !Objects.equals(oldNumber, number)) {
                meterRegistry.remove(id);
                builder.register(meterRegistry);
            }
            return number;
        });
    }

    /**
     * 注册一个瞬时指标值
     *
     * @param number      指标值
     * @param name        指标名称
     * @param tags        指标tag
     * @param description 指标描述
     */
    public void gauge(Double number, String name, Map<String, Object> tags, String description) {
        gauge(number, name, tags, description, null);
    }

    /**
     * 注册一个瞬时指标值
     *
     * @param number 指标值
     * @param name   指标名称
     * @param tags   指标tag
     */
    public void gauge(Double number, String name, Map<String, Object> tags) {
        gauge(number, name, tags, null, null);
    }

    /**
     * 注册一个瞬时指标值
     *
     * @param number 指标值
     * @param name   指标名称
     */
    public void gauge(Double number, String name) {
        gauge(number, name, null, null, null);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------- counter

    /**
     * 创建一个累计指标值构造器
     *
     * @param name 指标名称
     */
    public Counter.Builder counterBuilder(String name) {
        return Counter.builder(name);
    }

    /**
     * 创建一个累计指标值
     *
     * @param name 指标名称
     * @param tags 指标tag
     * @return 累计指标值对象
     */
    public Counter counter(String name, Map<String, Object> tags) {
        return meterRegistry.counter(name, toTags(tags));
    }

    /**
     * 创建一个累计指标值
     *
     * @param name 指标名称
     * @return 累计指标值对象
     */
    public Counter counter(String name) {
        return meterRegistry.counter(name);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------- other

    /**
     * 得到指标Tags
     */
    public List<Tag> toTags(Map<String, Object> tags) {
        List<Tag> tagList = new ArrayList<>(tags == null ? 0 : tags.size());
        if (tags != null) {
            tags.forEach((key, value) -> {
                if (StringUtils.isNotBlank(key)) {
                    tagList.add(Tag.of(key, String.valueOf(value)));
                }
            });
        }
        return tagList;
    }
}
