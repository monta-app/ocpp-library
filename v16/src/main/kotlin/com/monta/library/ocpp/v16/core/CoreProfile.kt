package com.monta.library.ocpp.v16.core

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.common.profile.ProfileDispatcher
import com.monta.library.ocpp.common.session.OcppSession
import com.monta.library.ocpp.common.transport.OcppCallException
import com.monta.library.ocpp.v16.error.MessageErrorCodeV16

class CoreClientProfile(
    private val listener: Listener
) : ProfileDispatcher {

    override val featureList: List<Feature> = listOf(
        AuthorizeFeature,
        BootNotificationFeature,
        ChangeAvailabilityFeature,
        ChangeConfigurationFeature,
        ClearCacheFeature,
        DataTransferFeature,
        GetConfigurationFeature,
        StatusNotificationFeature,
        RemoteStartTransactionFeature,
        StartTransactionFeature,
        RemoteStopTransactionFeature,
        StopTransactionFeature,
        HeartbeatFeature,
        ResetFeature,
        UnlockConnectorFeature,
        MeterValuesFeature
    )

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is ChangeAvailabilityRequest -> listener.changeAvailabilityRequest(ocppSessionInfo, request)
            is ChangeConfigurationRequest -> listener.changeConfigurationRequest(ocppSessionInfo, request)
            is ClearCacheRequest -> listener.clearCacheRequest(ocppSessionInfo, request)
            is DataTransferRequest -> listener.dataTransferRequest(ocppSessionInfo, request)
            is GetConfigurationRequest -> listener.getConfigurationRequest(ocppSessionInfo, request)
            is RemoteStartTransactionRequest -> listener.remoteStartTransactionRequest(ocppSessionInfo, request)
            is RemoteStopTransactionRequest -> listener.remoteStopTransactionRequest(ocppSessionInfo, request)
            is ResetRequest -> listener.resetRequest(ocppSessionInfo, request)
            is UnlockConnectorRequest -> listener.unlockConnectorRequest(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV16.NotSupported)
        }
    }

    interface Listener {
        suspend fun changeAvailabilityRequest(
            ocppSessionInfo: OcppSession.Info,
            request: ChangeAvailabilityRequest
        ): ChangeAvailabilityConfirmation

        suspend fun getConfigurationRequest(
            ocppSessionInfo: OcppSession.Info,
            request: GetConfigurationRequest
        ): GetConfigurationConfirmation

        suspend fun changeConfigurationRequest(
            ocppSessionInfo: OcppSession.Info,
            request: ChangeConfigurationRequest
        ): ChangeConfigurationConfirmation

        suspend fun clearCacheRequest(
            ocppSessionInfo: OcppSession.Info,
            request: ClearCacheRequest
        ): ClearCacheConfirmation

        suspend fun dataTransferRequest(
            ocppSessionInfo: OcppSession.Info,
            request: DataTransferRequest
        ): DataTransferConfirmation

        suspend fun remoteStartTransactionRequest(
            ocppSessionInfo: OcppSession.Info,
            request: RemoteStartTransactionRequest
        ): RemoteStartTransactionConfirmation

        suspend fun remoteStopTransactionRequest(
            ocppSessionInfo: OcppSession.Info,
            request: RemoteStopTransactionRequest
        ): RemoteStopTransactionConfirmation

        suspend fun resetRequest(
            ocppSessionInfo: OcppSession.Info,
            request: ResetRequest
        ): ResetConfirmation

        suspend fun unlockConnectorRequest(
            ocppSessionInfo: OcppSession.Info,
            request: UnlockConnectorRequest
        ): UnlockConnectorConfirmation
    }

    interface Sender {

        suspend fun bootNotification(
            request: BootNotificationRequest
        ): BootNotificationConfirmation

        suspend fun heartbeat(): HeartbeatConfirmation

        suspend fun authorize(
            request: AuthorizeRequest
        ): AuthorizeConfirmation

        suspend fun dataTransfer(
            request: DataTransferRequest
        ): DataTransferConfirmation

        suspend fun statusNotification(
            request: StatusNotificationRequest
        ): StatusNotificationConfirmation

        suspend fun startTransaction(
            request: StartTransactionRequest
        ): StartTransactionConfirmation

        suspend fun stopTransaction(
            request: StopTransactionRequest
        ): StopTransactionConfirmation

        suspend fun meterValues(
            request: MeterValuesRequest
        ): MeterValuesConfirmation
    }
}

class CoreServerProfile(
    private val listener: Listener
) : ProfileDispatcher {

    override val featureList: List<Feature> = listOf(
        // Receiver
        AuthorizeFeature,
        BootNotificationFeature,
        DataTransferFeature,
        HeartbeatFeature,
        MeterValuesFeature,
        StartTransactionFeature,
        StopTransactionFeature,
        StatusNotificationFeature,
        // Command
        ChangeAvailabilityFeature,
        ChangeConfigurationFeature,
        ClearCacheFeature,
        GetConfigurationFeature,
        RemoteStartTransactionFeature,
        RemoteStopTransactionFeature,
        ResetFeature,
        UnlockConnectorFeature
    )

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is AuthorizeRequest -> listener.authorize(ocppSessionInfo, request)
            is BootNotificationRequest -> listener.bootNotification(ocppSessionInfo, request)
            is DataTransferRequest -> listener.dataTransfer(ocppSessionInfo, request)
            is HeartbeatRequest -> listener.heartbeat(ocppSessionInfo, request)
            is MeterValuesRequest -> listener.meterValues(ocppSessionInfo, request)
            is StartTransactionRequest -> listener.startTransaction(ocppSessionInfo, request)
            is StopTransactionRequest -> listener.stopTransaction(ocppSessionInfo, request)
            is StatusNotificationRequest -> listener.statusNotification(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV16.NotSupported)
        }
    }

    interface Sender {
        suspend fun changeAvailability(
            request: ChangeAvailabilityRequest
        ): ChangeAvailabilityConfirmation

        suspend fun changeConfiguration(
            request: ChangeConfigurationRequest
        ): ChangeConfigurationConfirmation

        suspend fun clearCache(
            request: ClearCacheRequest
        ): ClearCacheConfirmation

        suspend fun getConfiguration(
            request: GetConfigurationRequest
        ): GetConfigurationConfirmation

        suspend fun remoteStartTransaction(
            request: RemoteStartTransactionRequest
        ): RemoteStartTransactionConfirmation

        suspend fun remoteStopTransaction(
            request: RemoteStopTransactionRequest
        ): RemoteStopTransactionConfirmation

        suspend fun reset(
            request: ResetRequest
        ): ResetConfirmation

        suspend fun unlockConnector(
            request: UnlockConnectorRequest
        ): UnlockConnectorConfirmation

        suspend fun dataTransfer(
            request: DataTransferRequest
        ): DataTransferConfirmation
    }

    interface Listener {
        suspend fun authorize(
            ocppSessionInfo: OcppSession.Info,
            request: AuthorizeRequest
        ): AuthorizeConfirmation

        suspend fun bootNotification(
            ocppSessionInfo: OcppSession.Info,
            request: BootNotificationRequest
        ): BootNotificationConfirmation

        suspend fun dataTransfer(
            ocppSessionInfo: OcppSession.Info,
            request: DataTransferRequest
        ): DataTransferConfirmation

        suspend fun heartbeat(
            ocppSessionInfo: OcppSession.Info,
            request: HeartbeatRequest
        ): HeartbeatConfirmation

        suspend fun meterValues(
            ocppSessionInfo: OcppSession.Info,
            request: MeterValuesRequest
        ): MeterValuesConfirmation

        suspend fun startTransaction(
            ocppSessionInfo: OcppSession.Info,
            request: StartTransactionRequest
        ): StartTransactionConfirmation

        suspend fun stopTransaction(
            ocppSessionInfo: OcppSession.Info,
            request: StopTransactionRequest
        ): StopTransactionConfirmation

        suspend fun statusNotification(
            ocppSessionInfo: OcppSession.Info,
            request: StatusNotificationRequest
        ): StatusNotificationConfirmation
    }
}
