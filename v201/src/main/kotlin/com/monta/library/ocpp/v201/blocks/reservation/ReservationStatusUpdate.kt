package com.monta.library.ocpp.v201.blocks.reservation

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData

object ReservationStatusUpdateFeature : Feature {
    override val name: String = "ReservationStatusUpdate"
    override val requestType: Class<out OcppRequest> = ReservationStatusUpdateRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = ReservationStatusUpdateResponse::class.java
}

data class ReservationStatusUpdateRequest(
    /** The ID of the reservation. */
    val reservationId: Long,
    val reservationUpdateStatus: ReservationUpdateStatus,
    val customData: CustomData? = null
) : OcppRequest {

    enum class ReservationUpdateStatus {
        Expired,
        Removed
    }
}

data class ReservationStatusUpdateResponse(
    val customData: CustomData? = null
) : OcppConfirmation
