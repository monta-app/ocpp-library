@file:Suppress("unused")

package com.monta.library.ocpp.v16

import com.fasterxml.jackson.annotation.JsonIgnore

enum class Phase(
    val value: String
) {
    L1("L1"),
    L2("L2"),
    L3("L3"),
    N("N"),
    L1_N("L1-N"),
    L2_N("L2-N"),
    L3_N("L3-N"),
    L1_L2("L1-L2"),
    L2_L3("L2-L3"),
    L3_L1("L3-L1")
}

enum class Context(
    val value: String
) {
    /**
     * Value taken at start of interruption
     */
    InterruptionBegin("Interruption.Begin"),

    /**
     * Value taken when resuming after interruption.
     */
    InterruptionEnd("Interruption.End"),

    /**
     * Value for any other situations.
     */
    Other("Other"),

    /**
     * Value taken at clock aligned interval.
     */
    SampleClock("Sample.Clock"),

    /**
     * Value taken as periodic sample relative to start time of transaction.
     */
    SamplePeriodic("Sample.Periodic"),

    /**
     * Value taken at end of transaction
     */
    TransactionBegin("Transaction.Begin"),

    /**
     * Value taken at start of transaction.
     */
    TransactionEnd("Transaction.End"),

    /**
     * Value taken in response to a TriggerMessage.req
     */
    Trigger("Trigger")
    ;

    companion object {
        private val valueMap: Map<String, Context> = entries.associateBy { it.value }
        fun parse(stringValue: String?): Context {
            return valueMap[stringValue] ?: SamplePeriodic
        }
    }
}

enum class Measurand(
    val value: String
) {
    /**
     * Instantaneous current flow from EV
     */
    CurrentExport("Current.Export"),

    /**
     * Instantaneous current flow to EV
     */
    CurrentImport("Current.Import"),

    /**
     * Maximum current offered to EV
     */
    CurrentOffered("Current.Offered"),

    /**
     * Energy exported by EV (Wh or kWh)
     */
    EnergyActiveExportRegister("Energy.Active.Export.Register"),

    /**
     * Energy imported by EV (Wh or kWh)
     */
    EnergyActiveImportRegister("Energy.Active.Import.Register"),

    /**
     * Reactive energy exported by EV (varh or kvarh)
     */
    EnergyReactiveExportRegister("Energy.Reactive.Export.Register"),

    /**
     * Reactive energy imported by EV (varh or kvarh)
     */
    EnergyReactiveImportRegister("Energy.Reactive.Import.Register"),

    /**
     * Energy exported by EV (Wh or kWh)
     */
    EnergyActiveExportInterval("Energy.Active.Export.Interval"),

    /**
     * Energy imported by EV (Wh or kWh)
     */
    EnergyActiveImportInterval("Energy.Active.Import.Interval"),

    /**
     * Reactive energy exported by EV. (varh or kvarh)
     */
    EnergyReactiveExportInterval("Energy.Reactive.Export.Interval"),

    /**
     * Reactive energy imported by EV. (varh or kvarh)
     */
    EnergyReactiveImportInterval("Energy.Reactive.Import.Interval"),

    /**
     * Instantaneous reading of powerline frequency
     */
    Frequency("Frequency"),

    /**
     * Instantaneous active power exported by EV. (W or kW)
     */
    PowerActiveExport("Power.Active.Export"),

    /**
     * Instantaneous active power imported by EV. (W or kW)
     */
    PowerActiveImport("Power.Active.Import"),

    /**
     * Instantaneous power factor of total energy flow
     */
    PowerFactor("Power.Factor"),

    /**
     * Maximum power offered to EV
     */
    PowerOffered("Power.Offered"),

    /**
     * Instantaneous reactive power exported by EV. (var or kvar)
     */
    PowerReactiveExport("Power.Reactive.Export"),

    /**
     * Instantaneous reactive power imported by EV. (var or kvar)
     */
    PowerReactiveImport("Power.Reactive.Import"),

    /**
     * Fan speed in RPM
     */
    RPM("RPM"),

    /**
     * State of charge of charging vehicle in percentage
     */
    SoC("SoC"),

    /**
     * Temperature reading inside Charge Point.
     */
    Temperature("Temperature"),

    /**
     * Instantaneous AC RMS supply voltage
     */
    Voltage("Voltage")
    ;

    companion object {
        private val valueMap: Map<String, Measurand> = entries.associateBy { it.value }
        fun parse(stringValue: String?): Measurand {
            return valueMap[stringValue] ?: EnergyActiveImportRegister
        }
    }
}

enum class Unit(
    val value: String
) {
    WattHour("Wh"),
    KiloWattHour("kWh"),
    VoltAmpereReactiveHour("varh"),
    KiloVoltAmpereReactiveHour("kvarh"),
    Watt("W"),
    KiloWatt("kW"),
    VoltAmpere("VA"),
    KiloVoltAmpere("kVA"),
    VoltAmpereReactive("var"),
    KiloVoltAmpereReactive("kvar"),
    Amp("A"),
    Volt("V"),
    Celsius("Celsius"),
    Fahrenheit("Fahrenheit"),
    Kelvin("K"),
    Percent("Percent")
    ;

    companion object {
        private val valueMap: Map<String, Unit> = entries.associateBy { it.value }
        fun parseNullable(stringValue: String?): Unit? {
            return valueMap[stringValue]
        }
    }
}

/**
 * Format that specifies how the value element in [SampledValue] is to be interpreted.
 */
enum class ValueFormat {
    /**
     * Data is to be interpreted as integer/decimal numeric data.
     */
    Raw,

    /**
     * Data is represented as a signed binary data block, encoded as hex data.
     */
    SignedData

    ;

    companion object {
        private val valueMap: Map<String, ValueFormat> = entries.associateBy { it.name }
        fun parse(stringValue: String?): ValueFormat {
            return valueMap[stringValue] ?: Raw
        }
    }
}

/**
 * Allowable values of the optional "location" field of a value element in [SampledValue].
 */
enum class Location {
    /**
     * Measurement inside body of Charge Point (e.g. Temperature)
     */
    Body,

    /**
     * Measurement taken from cable between EV and Charge Point
     */
    Cable,

    /**
     * Measurement taken by EV
     */
    EV,

    /**
     * Measurement at network (“grid”) inlet connection
     */
    Inlet,

    /**
     * Measurement at a Connector. Default value
     */
    Outlet

    ;

    companion object {
        private val valueMap: Map<String, Location> = entries.associateBy { it.name }
        fun parse(stringValue: String?): Location {
            return valueMap[stringValue] ?: Outlet
        }
    }
}

/**
 * Single sampled value in [MeterValuesRequest].
 * Each value can be accompanied by optional fields.
 */
data class SampledValue(
    /**
     * Required
     *
     * Value as a “Raw” (decimal) number or “SignedData”.
     * Field Type is “string” to allow for digitally signed data readings.
     * Decimal numeric values are also acceptable to allow fractional values for measurands such as Temperature and Current.
     **/
    var value: String,
    /**
     * Optional
     *
     * Type of detail value: start, end or sample. Default = “Sample.Periodic”
     **/
    var context: String? = null,
    /**
     * Optional
     *
     * Raw or signed data. Default = “Raw”
     **/
    var format: String? = null,
    /**
     * Optional
     *
     * Type of measurement. Default = “Energy.Active.Import.Register”
     **/
    var measurand: String? = null,
    /**
     * Optional
     *
     * indicates how the measured value is to be interpreted.
     * For instance between L1 and neutral (L1-N)
     * Please note that not all values of phase are applicable to all Measurands.
     * When phase is absent, the measured value is interpreted as an overall value.
     **/
    var phase: String? = null,
    /**
     * Optional
     *
     * Location of measurement. Default=”Outlet”
     **/
    var location: String? = null,
    /**
     * Optional
     *
     * Unit of the value. Default = “Wh” if the (default) measurand is an “Energy” type.
     **/
    var unit: String? = null
) {

    @get:JsonIgnore
    val contextType by lazy {
        Context.parse(context)
    }

    @get:JsonIgnore
    val formatType by lazy {
        ValueFormat.parse(format)
    }

    @get:JsonIgnore
    val measurandType by lazy {
        Measurand.parse(measurand)
    }

    @get:JsonIgnore
    val locationType by lazy {
        Location.parse(location)
    }

    @get:JsonIgnore
    val unitType by lazy {
        Unit.parseNullable(unit)
    }

    constructor(value: String) : this(
        value = value,
        context = Context.SamplePeriodic.value,
        format = ValueFormat.Raw.name,
        measurand = Measurand.EnergyActiveImportRegister.value,
        location = Location.Outlet.name,
        unit = Unit.WattHour.value
    )
}
