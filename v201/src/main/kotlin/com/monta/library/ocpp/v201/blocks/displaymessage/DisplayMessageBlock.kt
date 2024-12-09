package com.monta.library.ocpp.v201.blocks.displaymessage

import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.common.profile.ProfileDispatcher
import com.monta.library.ocpp.common.session.OcppSession
import com.monta.library.ocpp.common.transport.OcppCallException
import com.monta.library.ocpp.v201.error.MessageErrorCodeV201

val displayMessageFeatures = listOf(
    // CSMS to CP
    ClearDisplayMessageFeature,
    GetDisplayMessagesFeature,
    SetDisplayMessageFeature,
    // CP to CSMS
    NotifyDisplayMessagesFeature
)

class DisplayMessageClientDispatcher(
    private val listener: Listener
) : ProfileDispatcher {

    override val featureList = displayMessageFeatures

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is ClearDisplayMessageRequest -> listener.clearDisplayMessage(ocppSessionInfo, request)
            is GetDisplayMessagesRequest -> listener.getDisplayMessages(ocppSessionInfo, request)
            is SetDisplayMessageRequest -> listener.setDisplayMessage(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV201.NotSupported, "Requested Action [${request.actionName()}] is recognized but not supported by the receiver")
        }
    }

    interface Listener {
        suspend fun clearDisplayMessage(
            ocppSessionInfo: OcppSession.Info,
            request: ClearDisplayMessageRequest
        ): ClearDisplayMessageResponse

        suspend fun getDisplayMessages(
            ocppSessionInfo: OcppSession.Info,
            request: GetDisplayMessagesRequest
        ): GetDisplayMessagesResponse

        suspend fun setDisplayMessage(
            ocppSessionInfo: OcppSession.Info,
            request: SetDisplayMessageRequest
        ): SetDisplayMessageResponse
    }

    interface Sender {
        suspend fun notifyDisplayMessages(
            request: NotifyDisplayMessagesRequest
        ): NotifyDisplayMessagesResponse
    }
}

class DisplayMessageServerDispatcher(
    private val listener: Listener
) : ProfileDispatcher {

    override val featureList = displayMessageFeatures

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is NotifyDisplayMessagesRequest -> listener.notifyDisplayMessages(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV201.NotSupported, "Requested Action [${request.actionName()}] is recognized but not supported by the receiver")
        }
    }

    interface Listener {
        suspend fun notifyDisplayMessages(
            ocppSessionInfo: OcppSession.Info,
            request: NotifyDisplayMessagesRequest
        ): NotifyDisplayMessagesResponse
    }

    interface Sender {
        suspend fun clearDisplayMessage(
            request: ClearDisplayMessageRequest
        ): ClearDisplayMessageResponse

        suspend fun getDisplayMessages(
            request: GetDisplayMessagesRequest
        ): GetDisplayMessagesResponse

        suspend fun setDisplayMessage(
            request: SetDisplayMessageRequest
        ): SetDisplayMessageResponse
    }
}
