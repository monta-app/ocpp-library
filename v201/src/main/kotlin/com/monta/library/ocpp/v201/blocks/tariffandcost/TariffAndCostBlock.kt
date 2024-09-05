package com.monta.library.ocpp.v201.blocks.tariffandcost

import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.common.profile.ProfileDispatcher
import com.monta.library.ocpp.common.session.OcppSession
import com.monta.library.ocpp.common.transport.OcppCallException
import com.monta.library.ocpp.v201.error.MessageErrorCodeV201

class TariffAndCostClientDispatcher(
    private val listener: Listener
) : ProfileDispatcher {

    override val featureList = listOf(CostUpdatedFeature)

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is CostUpdatedRequest -> listener.costUpdated(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV201.NotSupported)
        }
    }

    interface Listener {
        suspend fun costUpdated(
            ocppSessionInfo: OcppSession.Info,
            request: CostUpdatedRequest
        ): CostUpdatedResponse
    }
}

class TariffAndCostServerDispatcher : ProfileDispatcher {
    override val featureList = listOf(CostUpdatedFeature)

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        throw OcppCallException(MessageErrorCodeV201.NotSupported)
    }

    interface Sender {
        suspend fun costUpdated(
            request: CostUpdatedRequest
        ): CostUpdatedResponse
    }
}
