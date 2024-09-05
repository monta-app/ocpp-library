package com.monta.library.ocpp.v16.firmware

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.common.profile.ProfileDispatcher
import com.monta.library.ocpp.common.session.OcppSession
import com.monta.library.ocpp.common.transport.OcppCallException
import com.monta.library.ocpp.v16.error.MessageErrorCodeV16

class FirmwareManagementServerProfile(
    private val listener: Listener
) : ProfileDispatcher {

    override val featureList: List<Feature> = listOf(
        DiagnosticsStatusNotificationFeature,
        FirmwareStatusNotificationFeature,
        GetDiagnosticsFeature,
        UpdateFirmwareFeature
    )

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is DiagnosticsStatusNotificationRequest -> listener.diagnosticsStatusNotification(
                ocppSessionInfo,
                request
            )

            is FirmwareStatusNotificationRequest -> listener.firmwareStatusNotification(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV16.NotSupported)
        }
    }

    interface Sender {
        suspend fun getDiagnostics(
            request: GetDiagnosticsRequest
        ): GetDiagnosticsConfirmation

        suspend fun updateFirmware(
            request: UpdateFirmwareRequest
        ): UpdateFirmwareConfirmation
    }

    interface Listener {
        suspend fun diagnosticsStatusNotification(
            ocppSessionInfo: OcppSession.Info,
            request: DiagnosticsStatusNotificationRequest
        ): DiagnosticsStatusNotificationConfirmation

        suspend fun firmwareStatusNotification(
            ocppSessionInfo: OcppSession.Info,
            request: FirmwareStatusNotificationRequest
        ): FirmwareStatusNotificationConfirmation
    }
}

class FirmwareManagementClientProfile(
    private val listener: Listener
) : ProfileDispatcher {

    override val featureList: List<Feature> = listOf(
        DiagnosticsStatusNotificationFeature,
        FirmwareStatusNotificationFeature,
        GetDiagnosticsFeature,
        UpdateFirmwareFeature
    )

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is GetDiagnosticsRequest -> listener.getDiagnostics(ocppSessionInfo, request)
            is UpdateFirmwareRequest -> listener.updateFirmware(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV16.NotSupported)
        }
    }

    interface Sender {
        suspend fun firmwareStatusNotification(
            request: FirmwareStatusNotificationRequest
        ): FirmwareStatusNotificationConfirmation

        suspend fun diagnosticsStatusNotification(
            request: DiagnosticsStatusNotificationRequest
        ): DiagnosticsStatusNotificationConfirmation
    }

    interface Listener {
        suspend fun getDiagnostics(
            ocppSessionInfo: OcppSession.Info,
            request: GetDiagnosticsRequest
        ): GetDiagnosticsConfirmation

        suspend fun updateFirmware(
            ocppSessionInfo: OcppSession.Info,
            request: UpdateFirmwareRequest
        ): UpdateFirmwareConfirmation
    }
}
