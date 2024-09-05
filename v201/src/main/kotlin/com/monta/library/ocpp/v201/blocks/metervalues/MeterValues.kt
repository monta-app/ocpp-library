package com.monta.library.ocpp.v201.blocks.metervalues

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.MeterValue

object MeterValuesFeature : Feature {
    override val name: String = "MeterValues"
    override val requestType: Class<out OcppRequest> = MeterValuesRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = MeterValuesResponse::class.java
}

data class MeterValuesRequest(
    /**
     * This contains a number (>0) designating an EVSE of the Charging Station.
     * ‘0’ (zero) is used to designate the main power meter.
     */
    val evseId: Long,
    val meterValue: List<MeterValue>,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        require(meterValue.isNotEmpty()) {
            "meterValue length < minimum 1 - ${meterValue.size}"
        }
    }
}

data class MeterValuesResponse(
    val customData: CustomData? = null
) : OcppConfirmation
