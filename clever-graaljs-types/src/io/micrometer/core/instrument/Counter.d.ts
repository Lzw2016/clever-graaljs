/**
 * 累计指标值
 */
interface Counter extends JObject {
    io_micrometer_core_instrument_Counter: "io.micrometer.core.instrument.Counter";

    increment(): void;

    increment(amount: JDouble): void;

    count(): JDouble;
}

/**
 * 累计指标值Builder
 */
interface CounterBuilder extends JObject {
    io_micrometer_core_instrument_Counter_Builder: "io.micrometer.core.instrument.Counter.Builder";

    tags(...tags: JString[]): CounterBuilder;

    tags(tags: JIterable<MeterTag>): CounterBuilder;

    tag(key: JString, value: JString): CounterBuilder;

    description(description: JString): CounterBuilder;

    baseUnit(unit: JString): CounterBuilder;

    register(registry: MeterRegistry): Counter;
}
