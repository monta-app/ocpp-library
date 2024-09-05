package com.monta.library.ocpp.v201.common

import com.fasterxml.jackson.annotation.JsonValue
import java.math.BigDecimal

data class SampledValue(
    val value: BigDecimal,
    val context: Context = Context.SamplePeriodic,
    val measurand: Measurand = Measurand.EnergyActiveImportRegister,
    val phase: Phase? = null,
    val location: Location = Location.Outlet,
    val signedMeterValue: SignedMeterValue? = null,
    val unitOfMeasure: UnitOfMeasure? = null,
    val customData: CustomData? = null
) {
    data class SignedMeterValue(
        /** Base64 encoded, contains the signed data which might contain more then just the meter value. It can contain information like timestamps, reference to a customer etc. */
        val signedMeterData: String,
        /** Method used to create the digital signature. */
        val signingMethod: String,
        /** Method used to encode the meter values before applying the digital signature algorithm. */
        val encodingMethod: String,
        /** Base64 encoded, sending depends on configuration variable _PublicKeyWithSignedMeterValue_. */
        val publicKey: String,
        val customData: CustomData? = null
    ) {

        init {
            require(signedMeterData.length <= 2500) {
                "signedMeterData length > maximum 2500 - ${signedMeterData.length}"
            }
            require(signingMethod.length <= 50) {
                "signingMethod length > maximum 50 - ${signingMethod.length}"
            }
            require(encodingMethod.length <= 50) {
                "encodingMethod length > maximum 50 - ${encodingMethod.length}"
            }
            require(publicKey.length <= 2500) {
                "publicKey length > maximum 2500 - ${publicKey.length}"
            }
        }
    }

    data class UnitOfMeasure(
        /**
         * Unit of the value. Default = "Wh" if the (default) measurand is an "Energy" type.
         * This field SHALL use a value from the list Standardized Units of Measurements in Part 2 Appendices.
         * If an applicable unit is available in that list, otherwise a "custom" unit might be used.
         */
        val unit: String = "Wh",
        /** Multiplier, this value represents the exponent to base 10. I.e. multiplier 3 means 10 raised to the 3rd power. Default is 0. */
        val multiplier: Long = 0,
        val customData: CustomData? = null
    ) {

        init {
            require(unit.length <= 20) {
                "unit length > maximum 20 - ${unit.length}"
            }
        }
    }
}

enum class Context(
    @JsonValue
    val stringValue: String
) {
    InterruptionBegin("Interruption.Begin"),
    InterruptionEnd("Interruption.End"),
    Other("Other"),
    SampleClock("Sample.Clock"),
    SamplePeriodic("Sample.Periodic"),
    TransactionBegin("Transaction.Begin"),
    TransactionEnd("Transaction.End"),
    Trigger("Trigger")
}

enum class Measurand(
    @JsonValue
    val stringValue: String
) {
    CurrentExport("Current.Export"),
    CurrentImport("Current.Import"),
    CurrentOffered("Current.Offered"),
    EnergyActiveExportRegister("Energy.Active.Export.Register"),
    EnergyActiveImportRegister("Energy.Active.Import.Register"),
    EnergyReactiveExportRegister("Energy.Reactive.Export.Register"),
    EnergyReactiveImportRegister("Energy.Reactive.Import.Register"),
    EnergyActiveExportInterval("Energy.Active.Export.Interval"),
    EnergyActiveImportInterval("Energy.Active.Import.Interval"),
    EnergyActiveNet("Energy.Active.Net"),
    EnergyReactiveExportInterval("Energy.Reactive.Export.Interval"),
    EnergyReactiveImportInterval("Energy.Reactive.Import.Interval"),
    EnergyReactiveNet("Energy.Reactive.Net"),
    EnergyApparentNet("Energy.Apparent.Net"),
    EnergyApparentImport("Energy.Apparent.Import"),
    EnergyApparentExport("Energy.Apparent.Export"),
    Frequency("Frequency"),
    PowerActiveExport("Power.Active.Export"),
    PowerActiveImport("Power.Active.Import"),
    PowerFactor("Power.Factor"),
    PowerOffered("Power.Offered"),
    PowerReactiveExport("Power.Reactive.Export"),
    PowerReactiveImport("Power.Reactive.Import"),
    SoC("SoC"),
    Voltage("Voltage")
}

enum class Phase(
    @JsonValue
    val stringValue: String
) {
    L1("L1"),
    L2("L2"),
    L3("L3"),
    N("N"),
    L1N("L1-N"),
    L2N("L2-N"),
    L3N("L3-N"),
    L1L2("L1-L2"),
    L2L3("L2-L3"),
    L3L1("L3-L1")
}

enum class Location {
    Body,
    Cable,
    EV,
    Inlet,
    Outlet
}
