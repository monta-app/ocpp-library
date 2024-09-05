package com.monta.library.ocpp.v201.blocks.provisioning

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.StatusInfo

object ResetFeature : Feature {
    override val name: String = "Reset"
    override val requestType: Class<out OcppRequest> = ResetRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = ResetResponse::class.java
}

data class ResetRequest(
    val type: Type,
    /**
     * This contains the ID of a specific EVSE that needs to be reset, instead of the entire Charging Station.
     */
    val evseId: Long? = null,
    /**
     * Any custom data that should be included in the request
     */
    val customData: CustomData? = null
) : OcppRequest {
    enum class Type {
        Immediate,
        OnIdle
    }
}

data class ResetResponse(
    val status: Status,
    val statusInfo: StatusInfo? = null,
    val customData: CustomData? = null
) : OcppConfirmation {
    enum class Status {
        Accepted,
        Rejected,
        Scheduled
    }
}
