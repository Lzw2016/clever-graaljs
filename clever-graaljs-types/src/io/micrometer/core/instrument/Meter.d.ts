interface Meter extends JObject {
    io_micrometer_core_instrument_Meter: "io.micrometer.core.instrument.Meter";

    close(): void;

    getId(): MeterId;
}

interface MeterType extends JObject {
    io_micrometer_core_instrument_Meter_Type: "io.micrometer.core.instrument.Meter.Type";

    name(): JString;

    ordinal(): JInt;
}

interface MeterTag extends JObject {
    io_micrometer_core_instrument_Tag: "io.micrometer.core.instrument.Tag";

    getKey(): JString;

    getValue(): JString;
}

interface MeterId extends JObject {
    io_micrometer_core_instrument_Meter_Id: "io.micrometer.core.instrument.Meter.Id";

    getName(): JString;

    getType(): MeterType;

    getTag(key: JString): JString;

    getTags(): JList<MeterTag>;

    getBaseUnit(): JString;

    getDescription(): JString;
}
