package com.monta.library.ocpp.v16.core

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import java.time.ZonedDateTime

/**
 * To let the Central System know that a Charge Point is still connected,
 * a Charge Point sends a heartbeat after a configurable time interval.
 *
 * The Charge Point SHALL send a [HeartbeatRequest] PDU for ensuring that
 * the Central System knows that a Charge Point is still alive.
 *
 * Upon receipt of a [HeartbeatRequest] PDU, the Central System SHALL respond with a [HeartbeatConfirmation].
 * The response PDU SHALL contain the current time of the Central System,
 * which is RECOMMENDED to be used by the Charge Point to synchronize its internal clock.
 *
 * The Charge Point MAY skip sending a [HeartbeatRequest] PDU when another PDU has been sent
 * to the Central System within the configured heartbeat interval.
 * This implies that a Central System SHOULD assume availability of a Charge Point whenever a PDU has been received,
 * the same way as it would have, when it received a [HeartbeatRequest] PDU.
 *
 * NOTE: With JSON over WebSocket, sending heartbeats is not mandatory.
 * However, for time synchronization it is advised to at least send one heartbeat per 24 hour.
 */
object HeartbeatFeature : Feature {
    override val name: String = "Heartbeat"
    override val requestType: Class<out OcppRequest> = HeartbeatRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = HeartbeatConfirmation::class.java
}

object HeartbeatRequest : OcppRequest

/**
 * Response to a [HeartbeatRequest]
 */
data class HeartbeatConfirmation(
    val currentTime: ZonedDateTime
) : OcppConfirmation
