package com.monta.library.ocpp.v201.blocks.reservation

import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.common.profile.ProfileDispatcher
import com.monta.library.ocpp.common.session.OcppSession
import com.monta.library.ocpp.common.transport.OcppCallException
import com.monta.library.ocpp.v201.error.MessageErrorCodeV201

val reservationFeatures = listOf(
    // CP to CSMS
    ReservationStatusUpdateFeature,
    // CSMS to CP
    CancelReservationFeature,
    ReserveNowFeature
)

class ReservationClientDispatcher(
    private val listener: Listener
) : ProfileDispatcher {

    override val featureList = reservationFeatures

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is CancelReservationRequest -> listener.cancelReservation(ocppSessionInfo, request)
            is ReserveNowRequest -> listener.reserveNow(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV201.NotSupported, "Requested Action [${request.actionName()}] is recognized but not supported by the receiver")
        }
    }

    interface Listener {
        suspend fun cancelReservation(
            ocppSessionInfo: OcppSession.Info,
            request: CancelReservationRequest
        ): CancelReservationResponse

        suspend fun reserveNow(
            ocppSessionInfo: OcppSession.Info,
            request: ReserveNowRequest
        ): ReserveNowResponse
    }

    interface Sender {
        suspend fun reservationStatusUpdate(
            request: ReservationStatusUpdateRequest
        ): ReservationStatusUpdateResponse
    }
}

class ReservationServerDispatcher(
    private val listener: Listener
) : ProfileDispatcher {

    override val featureList = reservationFeatures

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is ReservationStatusUpdateRequest -> listener.reservationStatusUpdate(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV201.NotSupported, "Requested Action [${request.actionName()}] is recognized but not supported by the receiver")
        }
    }

    interface Listener {
        suspend fun reservationStatusUpdate(
            ocppSessionInfo: OcppSession.Info,
            request: ReservationStatusUpdateRequest
        ): ReservationStatusUpdateResponse
    }

    interface Sender {
        suspend fun cancelReservation(
            request: CancelReservationRequest
        ): CancelReservationResponse

        suspend fun reserveNow(
            request: ReserveNowRequest
        ): ReserveNowResponse
    }
}
