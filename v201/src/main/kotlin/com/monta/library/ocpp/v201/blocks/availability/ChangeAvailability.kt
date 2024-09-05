package com.monta.library.ocpp.v201.blocks.availability

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.EVSE
import com.monta.library.ocpp.v201.common.StatusInfo

object ChangeAvailabilityFeature : Feature {
    override val name: String = "ChangeAvailability"
    override val requestType: Class<out OcppRequest> = ChangeAvailabilityRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = ChangeAvailabilityResponse::class.java
}

data class ChangeAvailabilityRequest(
    val operationalStatus: OperationalStatus,
    val evse: EVSE? = null,
    val customData: CustomData? = null
) : OcppRequest {
    enum class OperationalStatus {
        Inoperative,
        Operative
    }
}

data class ChangeAvailabilityResponse(
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
