package com.monta.library.ocpp.v201.blocks.availability

import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.common.profile.ProfileDispatcher
import com.monta.library.ocpp.common.session.OcppSession
import com.monta.library.ocpp.common.transport.OcppCallException
import com.monta.library.ocpp.v201.error.MessageErrorCodeV201

val availabilityFeatures = listOf(
    // CP to CSMS
    HeartbeatFeature,
    StatusNotificationFeature,
    // CSMS to CP
    ChangeAvailabilityFeature
)

class AvailabilityClientDispatcher(
    private val listener: Listener
) : ProfileDispatcher {

    override val featureList = availabilityFeatures

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is ChangeAvailabilityRequest -> listener.changeAvailability(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV201.NotSupported, "Requested Action [${request.actionName()}] is recognized but not supported by the receiver")
        }
    }

    interface Listener {
        suspend fun changeAvailability(
            ocppSessionInfo: OcppSession.Info,
            request: ChangeAvailabilityRequest
        ): ChangeAvailabilityResponse
    }

    interface Sender {
        suspend fun heartbeat(
            request: HeartbeatRequest
        ): HeartbeatResponse

        suspend fun statusNotification(
            request: StatusNotificationRequest
        ): StatusNotificationResponse
    }
}

class AvailabilityServerDispatcher(
    private val listener: Listener
) : ProfileDispatcher {

    override val featureList = availabilityFeatures

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is HeartbeatRequest -> listener.heartbeat(ocppSessionInfo, request)
            is StatusNotificationRequest -> listener.statusNotification(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV201.NotSupported, "Requested Action [${request.actionName()}] is recognized but not supported by the receiver")
        }
    }

    interface Listener {
        suspend fun heartbeat(
            ocppSessionInfo: OcppSession.Info,
            request: HeartbeatRequest
        ): HeartbeatResponse

        suspend fun statusNotification(
            ocppSessionInfo: OcppSession.Info,
            request: StatusNotificationRequest
        ): StatusNotificationResponse
    }

    interface Sender {
        suspend fun changeAvailability(
            request: ChangeAvailabilityRequest
        ): ChangeAvailabilityResponse
    }
}
