package com.monta.library.ocpp.v201.blocks.security

import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.common.profile.ProfileDispatcher
import com.monta.library.ocpp.common.session.OcppSession
import com.monta.library.ocpp.common.transport.OcppCallException
import com.monta.library.ocpp.v201.error.MessageErrorCodeV201

val securityFeatures = listOf(
    // CP to CSMS
    SecurityEventNotificationFeature
)

class SecurityClientDispatcher : ProfileDispatcher {
    override val featureList = securityFeatures

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        throw OcppCallException(MessageErrorCodeV201.NotSupported)
    }

    interface Sender {
        suspend fun securityEventNotification(
            request: SecurityEventNotificationRequest
        ): SecurityEventNotificationResponse
    }
}

class SecurityServerDispatcher(
    private val listener: Listener
) : ProfileDispatcher {
    override val featureList = securityFeatures

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is SecurityEventNotificationRequest -> listener.securityEventNotification(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV201.NotSupported)
        }
    }

    interface Listener {
        suspend fun securityEventNotification(
            ocppSessionInfo: OcppSession.Info,
            request: SecurityEventNotificationRequest
        ): SecurityEventNotificationResponse
    }
}
