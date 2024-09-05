package com.monta.library.ocpp.v201.common.chargingprofile

import com.monta.library.ocpp.v201.common.CustomData
import java.math.BigDecimal

data class ConsumptionCost(
    /**
     * The lowest level of consumption that defines the starting point of this consumption block.
     * The block interval extends to the start of the next interval.
     **/
    val startValue: BigDecimal,
    val cost: List<Cost>,
    val customData: CustomData? = null
) {

    init {
        require(cost.size in 1..3) {
            "cost length not in range 1..3 - ${cost.size}"
        }
    }
}
