package com.monta.library.ocpp.v201.blocks.transactions

import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.common.profile.ProfileDispatcher
import com.monta.library.ocpp.common.session.OcppSession
import com.monta.library.ocpp.common.transport.OcppCallException
import com.monta.library.ocpp.v201.error.MessageErrorCodeV201

val transactionsFeatures = listOf(
    // CP to CSMS
    TransactionEventFeature,
    // CSMS to CP
    GetTransactionStatusFeature
)

class TransactionsClientDispatcher(
    private val listener: Listener
) : ProfileDispatcher {

    override val featureList = transactionsFeatures

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is GetTransactionStatusRequest -> listener.getTransactionStatus(request)
            else -> throw OcppCallException(MessageErrorCodeV201.NotSupported, "Requested Action [${request.actionName()}] is recognized but not supported by the receiver")
        }
    }

    interface Listener {
        suspend fun getTransactionStatus(
            request: GetTransactionStatusRequest
        ): GetTransactionStatusResponse
    }

    interface Sender {
        suspend fun transactionEvent(
            request: TransactionEventRequest
        ): TransactionEventResponse
    }
}

class TransactionsServerDispatcher(
    private val listener: Listener
) : ProfileDispatcher {

    override val featureList = transactionsFeatures

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is TransactionEventRequest -> listener.transactionEvent(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV201.NotSupported, "Requested Action [${request.actionName()}] is recognized but not supported by the receiver")
        }
    }

    interface Listener {
        suspend fun transactionEvent(
            ocppSessionInfo: OcppSession.Info,
            request: TransactionEventRequest
        ): TransactionEventResponse
    }

    interface Sender {
        suspend fun getTransactionStatus(
            request: GetTransactionStatusRequest
        ): GetTransactionStatusResponse
    }
}
