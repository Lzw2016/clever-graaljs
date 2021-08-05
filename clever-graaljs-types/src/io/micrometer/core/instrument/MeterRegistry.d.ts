/**
 * 监控指标收集器
 */
interface MeterRegistry extends JObject {
    io_micrometer_core_instrument_MeterRegistry: "io.micrometer.core.instrument.MeterRegistry";

    close(): void;

    isClosed(): boolean;

    remove(id: MeterId): Meter;

    remove(meter: Meter): Meter
}
