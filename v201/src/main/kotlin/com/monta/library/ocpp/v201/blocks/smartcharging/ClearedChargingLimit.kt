package com.monta.library.ocpp.v201.blocks.smartcharging

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.chargingprofile.ChargingLimitSource

object ClearedChargingLimitFeature : Feature {
    override val name: String = "ClearedChargingLimit"
    override val requestType: Class<out OcppRequest> = ClearedChargingLimitRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = ClearedChargingLimitResponse::class.java
}

data class ClearedChargingLimitRequest(
    val chargingLimitSource: ChargingLimitSource,
    /** EVSE Identifier. */
    val evseId: Long? = null,
    val customData: CustomData? = null
) : OcppRequest

data class ClearedChargingLimitResponse(
    val customData: CustomData? = null
) : OcppConfirmation
