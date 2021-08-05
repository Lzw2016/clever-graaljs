/**
 * 瞬时值指标
 */
interface Gauge extends Meter {
    io_micrometer_core_instrument_Gauge: "io.micrometer.core.instrument.Gauge";

    builder<T = any>(name: JString, obj: T, callback: (obj: T) => JDouble): GaugeBuilder;

    builder(name: JString, callback: () => JDouble): GaugeBuilder;
}

/**
 * 瞬时值指标Builder
 */
interface GaugeBuilder extends JObject {
    io_micrometer_core_instrument_Gauge_Builder: "io.micrometer.core.instrument.Gauge.Builder";

    tags(...tags: JString[]): GaugeBuilder;

    tags(tags: JIterable<MeterTag>): GaugeBuilder;

    tag(key: JString, value: JString): GaugeBuilder;

    description(description: JString): GaugeBuilder;

    baseUnit(unit: JString): GaugeBuilder;

    strongReference(strong: JBoolean): GaugeBuilder;

    register(registry: MeterRegistry): Gauge;
}
