package com.monta.library.ocpp.v16.remotetrigger

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.common.profile.ProfileDispatcher
import com.monta.library.ocpp.common.session.OcppSession
import com.monta.library.ocpp.common.transport.OcppCallException
import com.monta.library.ocpp.v16.error.MessageErrorCodeV16

object TriggerMessageFeature : Feature {
    override val name: String = "TriggerMessage"
    override val requestType: Class<out OcppRequest> = TriggerMessageRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = TriggerMessageConfirmation::class.java
}

enum class TriggerMessageRequestType {
    /**
     * To trigger a [BootNotification] request
     */
    BootNotification,

    /**
     * To trigger a [DiagnosticsStatusNotification] request
     */
    DiagnosticsStatusNotification,

    /**
     * To trigger a [FirmwareStatusNotification] request
     */
    FirmwareStatusNotification,

    /**
     * To trigger a [Heartbeat] request
     */
    Heartbeat,

    /**
     * To trigger a [MeterValues] request
     */
    MeterValues,

    /**
     * To trigger a [StatusNotification] request
     */
    StatusNotification
}

enum class TriggerMessageStatus {
    /**
     * Requested notification will be sent.
     */
    Accepted,

    /**
     * Requested notification will not be sent.
     */
    Rejected,

    /**
     * Requested notification cannot be sent because it is either not implemented or unknown.
     */
    NotImplemented
}

data class TriggerMessageRequest(
    /**
     * Required
     */
    val requestedMessage: TriggerMessageRequestType,
    /**
     * Optional
     * Only filled in when request applies to a specific connector.
     */
    val connectorId: Int? = null
) : OcppRequest

data class TriggerMessageConfirmation(
    /**
     * Required
     * Indicates whether the Charge Point will send the requested notification or not.
     */
    val status: TriggerMessageStatus
) : OcppConfirmation

class TriggerMessageServerProfile : ProfileDispatcher {
    override val featureList: List<Feature> = listOf(
        TriggerMessageFeature
    )

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        throw OcppCallException(MessageErrorCodeV16.NotSupported, "Requested Action [${request.actionName()}] is recognized but not supported by the receiver")
    }

    interface Sender {
        suspend fun triggerMessage(
            request: TriggerMessageRequest
        ): TriggerMessageConfirmation
    }
}

class TriggerMessageClientProfile(
    private val listener: Listener
) : ProfileDispatcher {

    override val featureList: List<Feature> = listOf(
        TriggerMessageFeature
    )

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is TriggerMessageRequest -> listener.triggerMessage(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV16.NotSupported, "Requested Action [${request.actionName()}] is recognized but not supported by the receiver")
        }
    }

    interface Listener {
        suspend fun triggerMessage(
            ocppSessionInfo: OcppSession.Info,
            request: TriggerMessageRequest
        ): TriggerMessageConfirmation
    }
}
