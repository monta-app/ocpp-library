package com.monta.library.ocpp.v201.common.chargingprofile

import com.monta.library.ocpp.v201.common.CustomData

data class Cost(
    val costKind: CostKind,
    /**
     * The estimated or actual cost per kWh
     **/
    val amount: Long,
    /**
     * Values: -3..3, The amountMultiplier defines the exponent to base 10 (dec).
     * The final value is determined by: amount * 10 ^ amountMultiplier
     **/
    val amountMultiplier: Long? = null,
    val customData: CustomData? = null
) {
    enum class CostKind {
        CarbonDioxideEmission,
        RelativePricePercentage,
        RenewableGenerationPercentage
    }
}
