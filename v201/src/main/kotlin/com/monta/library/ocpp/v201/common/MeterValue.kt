package com.monta.library.ocpp.v201.common

import java.time.ZonedDateTime

data class MeterValue(
    val sampledValue: List<SampledValue>,
    val timestamp: ZonedDateTime,
    val customData: CustomData? = null
) {
    init {
        require(sampledValue.isNotEmpty()) {
            "sampledValue length < minimum 1 - ${sampledValue.size}"
        }
    }
}
