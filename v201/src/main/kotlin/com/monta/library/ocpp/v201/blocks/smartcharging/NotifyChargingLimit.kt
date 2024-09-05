package com.monta.library.ocpp.v201.blocks.smartcharging

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.chargingprofile.ChargingLimitSource
import com.monta.library.ocpp.v201.common.chargingprofile.ChargingSchedule

object NotifyChargingLimitFeature : Feature {
    override val name: String = "NotifyChargingLimit"
    override val requestType: Class<out OcppRequest> = NotifyChargingLimitRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = NotifyChargingLimitResponse::class.java
}

data class NotifyChargingLimitRequest(
    val chargingLimit: ChargingLimit,
    val chargingSchedule: List<ChargingSchedule>? = null,
    /**
     * The charging schedule contained in this notification applies to an EVSE.
     * evseId must be > 0.
     **/
    val evseId: Long? = null,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        if (chargingSchedule != null) {
            require(chargingSchedule.isNotEmpty()) {
                "chargingSchedule length < minimum 1 - ${chargingSchedule.size}"
            }
        }
    }

    data class ChargingLimit(
        val chargingLimitSource: ChargingLimitSource,
        /**
         * Indicates whether the charging limit is critical for the grid.
         **/
        val isGridCritical: Boolean? = null,
        val customData: CustomData? = null
    )
}

data class NotifyChargingLimitResponse(
    val customData: CustomData? = null
) : OcppConfirmation
