package com.monta.library.ocpp.v16.core

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest

object ChangeAvailabilityFeature : Feature {
    override val name: String = "ChangeAvailability"
    override val requestType: Class<out OcppRequest> = ChangeAvailabilityRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = ChangeAvailabilityConfirmation::class.java
}

enum class AvailabilityType {
    /**
     * Charge point is not available for charging.
     */
    Inoperative,

    /**
     * Charge point is available for charging.
     */
    Operative
}

data class ChangeAvailabilityRequest(
    /**
     * Required
     *
     * The id of the connector for which availability needs to change. Id '0' (zero) is used if the availability of the Charge Point and all its connectors needs to change.
     */
    val connectorId: Int,
    /**
     * Required
     *
     * This contains the type of availability change that the Charge Point should perform.
     */
    val type: AvailabilityType
) : OcppRequest

enum class AvailabilityStatus {
    /**
     * Request has been accepted and will be executed.
     */
    Accepted,

    /**
     * Request has not been accepted and will not be executed
     */
    Rejected,

    /**
     * Request has been accepted and will be executed when transaction(s) in progress have finished.
     */
    Scheduled
}

data class ChangeAvailabilityConfirmation(
    /**
     * Required
     *
     * This indicates whether the Charge Point is able to perform the availability change.
     */
    val status: AvailabilityStatus
) : OcppConfirmation
