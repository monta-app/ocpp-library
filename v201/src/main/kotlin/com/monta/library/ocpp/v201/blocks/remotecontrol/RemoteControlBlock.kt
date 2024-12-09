package com.monta.library.ocpp.v201.blocks.remotecontrol

import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.common.profile.ProfileDispatcher
import com.monta.library.ocpp.common.session.OcppSession
import com.monta.library.ocpp.common.transport.OcppCallException
import com.monta.library.ocpp.v201.error.MessageErrorCodeV201

val remoteControlFeatures = listOf(
    // CSMS to CP
    RequestStartTransactionFeature,
    RequestStopTransactionFeature,
    TriggerMessageFeature,
    UnlockConnectorFeature
)

class RemoteControlClientDispatcher(
    private val listener: Listener
) : ProfileDispatcher {

    override val featureList = remoteControlFeatures

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is RequestStartTransactionRequest -> listener.requestStartTransaction(ocppSessionInfo, request)
            is RequestStopTransactionRequest -> listener.requestStopTransaction(ocppSessionInfo, request)
            is TriggerMessageRequest -> listener.triggerMessage(ocppSessionInfo, request)
            is UnlockConnectorRequest -> listener.unlockConnector(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV201.NotSupported, "Requested Action [${request.actionName()}] is recognized but not supported by the receiver")
        }
    }

    interface Listener {
        suspend fun requestStartTransaction(
            ocppSessionInfo: OcppSession.Info,
            request: RequestStartTransactionRequest
        ): RequestStartTransactionResponse

        suspend fun requestStopTransaction(
            ocppSessionInfo: OcppSession.Info,
            request: RequestStopTransactionRequest
        ): RequestStopTransactionResponse

        suspend fun triggerMessage(
            ocppSessionInfo: OcppSession.Info,
            request: TriggerMessageRequest
        ): TriggerMessageResponse

        suspend fun unlockConnector(
            ocppSessionInfo: OcppSession.Info,
            request: UnlockConnectorRequest
        ): UnlockConnectorResponse
    }
}

class RemoteControlServerDispatcher : ProfileDispatcher {

    override val featureList = remoteControlFeatures

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        throw OcppCallException(MessageErrorCodeV201.NotSupported, "Requested Action [${request.actionName()}] is recognized but not supported by the receiver")
    }

    interface Sender {
        suspend fun requestStartTransaction(
            request: RequestStartTransactionRequest
        ): RequestStartTransactionResponse

        suspend fun requestStopTransaction(
            request: RequestStopTransactionRequest
        ): RequestStopTransactionResponse

        suspend fun triggerMessage(
            request: TriggerMessageRequest
        ): TriggerMessageResponse

        suspend fun unlockConnector(
            request: UnlockConnectorRequest
        ): UnlockConnectorResponse
    }
}
