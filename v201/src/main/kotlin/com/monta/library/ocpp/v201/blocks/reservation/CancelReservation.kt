package com.monta.library.ocpp.v201.blocks.reservation

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.StatusInfo

object CancelReservationFeature : Feature {
    override val name: String = "CancelReservation"
    override val requestType: Class<out OcppRequest> = CancelReservationRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = CancelReservationResponse::class.java
}

data class CancelReservationRequest(
    /** Id of the reservation to cancel. */
    val reservationId: Long,
    val customData: CustomData? = null
) : OcppRequest

data class CancelReservationResponse(
    val status: Status,
    val statusInfo: StatusInfo? = null,
    val customData: CustomData? = null
) : OcppConfirmation {
    enum class Status {
        Accepted,
        Rejected
    }
}
