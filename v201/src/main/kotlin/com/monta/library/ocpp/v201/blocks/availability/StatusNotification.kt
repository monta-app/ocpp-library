package com.monta.library.ocpp.v201.blocks.availability

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import java.time.ZonedDateTime

object StatusNotificationFeature : Feature {
    override val name: String = "StatusNotification"
    override val requestType: Class<out OcppRequest> = StatusNotificationRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = StatusNotificationResponse::class.java
}

data class StatusNotificationRequest(
    /** The time for which the status is reported. If absent time of receipt of the message will be assumed. */
    val timestamp: ZonedDateTime,
    val connectorStatus: ConnectorStatus,
    /** The id of the EVSE to which the connector belongs for which the the status is reported. */
    val evseId: Long,
    /** The id of the connector within the EVSE for which the status is reported. */
    val connectorId: Long,
    val customData: CustomData? = null
) : OcppRequest {

    enum class ConnectorStatus {
        Available,
        Occupied,
        Reserved,
        Unavailable,
        Faulted
    }
}

data class StatusNotificationResponse(
    val customData: CustomData? = null
) : OcppConfirmation
