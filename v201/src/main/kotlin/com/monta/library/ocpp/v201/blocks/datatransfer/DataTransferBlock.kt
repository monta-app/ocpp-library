package com.monta.library.ocpp.v201.blocks.datatransfer

import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.common.profile.ProfileDispatcher
import com.monta.library.ocpp.common.session.OcppSession
import com.monta.library.ocpp.common.transport.OcppCallException
import com.monta.library.ocpp.v201.error.MessageErrorCodeV201

class DataTransferClientDispatcher(
    private val listener: Listener
) : ProfileDispatcher {

    override val featureList = listOf(DataTransferFeature)

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is DataTransferRequest -> listener.dataTransfer(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV201.NotSupported, "Requested Action [${request.actionName()}] is recognized but not supported by the receiver")
        }
    }

    interface Listener {
        suspend fun dataTransfer(
            ocppSessionInfo: OcppSession.Info,
            request: DataTransferRequest
        ): DataTransferResponse
    }

    interface Sender {
        suspend fun dataTransfer(
            request: DataTransferRequest
        ): DataTransferResponse
    }
}

class DataTransferServerDispatcher(
    private val listener: Listener
) : ProfileDispatcher {

    override val featureList = listOf(DataTransferFeature)

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is DataTransferRequest -> listener.dataTransfer(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV201.NotSupported, "Requested Action [${request.actionName()}] is recognized but not supported by the receiver")
        }
    }

    interface Listener {
        suspend fun dataTransfer(
            ocppSessionInfo: OcppSession.Info,
            request: DataTransferRequest
        ): DataTransferResponse
    }

    interface Sender {
        suspend fun dataTransfer(
            request: DataTransferRequest
        ): DataTransferResponse
    }
}
