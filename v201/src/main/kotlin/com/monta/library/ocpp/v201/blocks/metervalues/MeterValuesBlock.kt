package com.monta.library.ocpp.v201.blocks.metervalues

import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.common.profile.ProfileDispatcher
import com.monta.library.ocpp.common.session.OcppSession
import com.monta.library.ocpp.common.transport.OcppCallException
import com.monta.library.ocpp.v201.error.MessageErrorCodeV201

class MeterValuesClientDispatcher : ProfileDispatcher {
    override val featureList = listOf(MeterValuesFeature)

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        throw OcppCallException(MessageErrorCodeV201.NotSupported, "Requested Action [${request.actionName()}] is recognized but not supported by the receiver")
    }

    interface Sender {
        suspend fun meterValues(
            request: MeterValuesRequest
        ): MeterValuesResponse
    }
}

class MeterValuesServerDispatcher(
    private val listener: Listener
) : ProfileDispatcher {

    override val featureList = listOf(MeterValuesFeature)

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is MeterValuesRequest -> listener.meterValues(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV201.NotSupported, "Requested Action [${request.actionName()}] is recognized but not supported by the receiver")
        }
    }

    interface Listener {
        suspend fun meterValues(
            ocppSessionInfo: OcppSession.Info,
            request: MeterValuesRequest
        ): MeterValuesResponse
    }
}
