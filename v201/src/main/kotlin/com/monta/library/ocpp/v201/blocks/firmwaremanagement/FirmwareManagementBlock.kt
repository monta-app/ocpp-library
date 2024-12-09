package com.monta.library.ocpp.v201.blocks.firmwaremanagement

import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.common.profile.ProfileDispatcher
import com.monta.library.ocpp.common.session.OcppSession
import com.monta.library.ocpp.common.transport.OcppCallException
import com.monta.library.ocpp.v201.error.MessageErrorCodeV201

val firmwareManagementFeatures = listOf(
    // CP to CSMS
    FirmwareStatusNotificationFeature,
    PublishFirmwareStatusNotificationFeature,
    // CSMS to CP
    PublishFirmwareFeature,
    UnpublishFirmwareFeature,
    UpdateFirmwareFeature
)

class FirmwareManagementClientDispatcher(
    private val listener: Listener
) : ProfileDispatcher {

    override val featureList = firmwareManagementFeatures

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is PublishFirmwareRequest -> listener.publishFirmware(ocppSessionInfo, request)
            is UnpublishFirmwareRequest -> listener.unpublishFirmware(ocppSessionInfo, request)
            is UpdateFirmwareRequest -> listener.updateFirmware(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV201.NotSupported, "Requested Action [${request.actionName()}] is recognized but not supported by the receiver")
        }
    }

    interface Listener {
        suspend fun publishFirmware(
            ocppSessionInfo: OcppSession.Info,
            request: PublishFirmwareRequest
        ): PublishFirmwareResponse

        suspend fun unpublishFirmware(
            ocppSessionInfo: OcppSession.Info,
            request: UnpublishFirmwareRequest
        ): UnpublishFirmwareResponse

        suspend fun updateFirmware(
            ocppSessionInfo: OcppSession.Info,
            request: UpdateFirmwareRequest
        ): UpdateFirmwareResponse
    }

    interface Sender {
        suspend fun firmwareStatusNotification(
            request: FirmwareStatusNotificationRequest
        ): FirmwareStatusNotificationResponse

        suspend fun publishFirmwareStatusNotification(
            request: PublishFirmwareStatusNotificationRequest
        ): PublishFirmwareStatusNotificationResponse
    }
}

class FirmwareManagementServerDispatcher(
    private val listener: Listener
) : ProfileDispatcher {

    override val featureList = firmwareManagementFeatures

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is FirmwareStatusNotificationRequest -> listener.firmwareStatusNotification(ocppSessionInfo, request)
            is PublishFirmwareStatusNotificationRequest -> listener.publishFirmwareStatusNotification(
                ocppSessionInfo,
                request
            )

            else -> throw OcppCallException(MessageErrorCodeV201.NotSupported, "Requested Action [${request.actionName()}] is recognized but not supported by the receiver")
        }
    }

    interface Listener {
        suspend fun firmwareStatusNotification(
            ocppSessionInfo: OcppSession.Info,
            request: FirmwareStatusNotificationRequest
        ): FirmwareStatusNotificationResponse

        suspend fun publishFirmwareStatusNotification(
            ocppSessionInfo: OcppSession.Info,
            request: PublishFirmwareStatusNotificationRequest
        ): PublishFirmwareStatusNotificationResponse
    }

    interface Sender {
        suspend fun publishFirmware(
            request: PublishFirmwareRequest
        ): PublishFirmwareResponse

        suspend fun unpublishFirmware(
            request: UnpublishFirmwareRequest
        ): UnpublishFirmwareResponse

        suspend fun updateFirmware(
            request: UpdateFirmwareRequest
        ): UpdateFirmwareResponse
    }
}
