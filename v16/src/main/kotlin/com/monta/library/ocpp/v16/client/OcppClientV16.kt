package com.monta.library.ocpp.v16.client

import com.monta.library.ocpp.client.OcppClient
import com.monta.library.ocpp.common.OcppClientConnectionEvent
import com.monta.library.ocpp.common.OcppClientDisconnectionEvent
import com.monta.library.ocpp.common.profile.ProfileDispatcher
import com.monta.library.ocpp.common.serialization.SerializationMode
import com.monta.library.ocpp.common.session.OcppSession
import com.monta.library.ocpp.common.session.OcppSessionRepository
import com.monta.library.ocpp.common.transport.OcppSettings
import com.monta.library.ocpp.v16.core.AuthorizeConfirmation
import com.monta.library.ocpp.v16.core.AuthorizeRequest
import com.monta.library.ocpp.v16.core.BootNotificationConfirmation
import com.monta.library.ocpp.v16.core.BootNotificationRequest
import com.monta.library.ocpp.v16.core.CoreClientProfile
import com.monta.library.ocpp.v16.core.DataTransferConfirmation
import com.monta.library.ocpp.v16.core.DataTransferRequest
import com.monta.library.ocpp.v16.core.HeartbeatConfirmation
import com.monta.library.ocpp.v16.core.HeartbeatRequest
import com.monta.library.ocpp.v16.core.MeterValuesConfirmation
import com.monta.library.ocpp.v16.core.MeterValuesRequest
import com.monta.library.ocpp.v16.core.StartTransactionConfirmation
import com.monta.library.ocpp.v16.core.StartTransactionRequest
import com.monta.library.ocpp.v16.core.StatusNotificationConfirmation
import com.monta.library.ocpp.v16.core.StatusNotificationRequest
import com.monta.library.ocpp.v16.core.StopTransactionConfirmation
import com.monta.library.ocpp.v16.core.StopTransactionRequest
import com.monta.library.ocpp.v16.error.OcppErrorResponderV16
import com.monta.library.ocpp.v16.firmware.DiagnosticsStatusNotificationConfirmation
import com.monta.library.ocpp.v16.firmware.DiagnosticsStatusNotificationRequest
import com.monta.library.ocpp.v16.firmware.FirmwareManagementClientProfile
import com.monta.library.ocpp.v16.firmware.FirmwareStatusNotificationConfirmation
import com.monta.library.ocpp.v16.firmware.FirmwareStatusNotificationRequest
import com.monta.library.ocpp.v16.security.LogStatusNotificationConfirmation
import com.monta.library.ocpp.v16.security.LogStatusNotificationRequest
import com.monta.library.ocpp.v16.security.SecurityClientProfile
import com.monta.library.ocpp.v16.security.SecurityEventNotificationConfirmation
import com.monta.library.ocpp.v16.security.SecurityEventNotificationRequest
import com.monta.library.ocpp.v16.security.SignCertificateConfirmation
import com.monta.library.ocpp.v16.security.SignCertificateRequest
import com.monta.library.ocpp.v16.security.SignedFirmwareStatusNotificationConfirmation
import com.monta.library.ocpp.v16.security.SignedFirmwareStatusNotificationRequest

class OcppClientV16(
    onConnect: OcppClientConnectionEvent,
    onDisconnect: OcppClientDisconnectionEvent,
    ocppSessionRepository: OcppSessionRepository,
    settings: OcppSettings,
    profiles: Set<ProfileDispatcher>,
    sendHook: suspend (String, String) -> String?
) : OcppClient(
    onConnect = onConnect,
    onDisconnect = onDisconnect,
    ocppSessionRepository = ocppSessionRepository,
    serializationMode = SerializationMode.OCPP_1_6,
    ocppErrorResponder = OcppErrorResponderV16,
    settings = settings,
    profiles = profiles,
    sendHook = sendHook
) {
    fun asCoreProfile(
        ocppSessionInfo: OcppSession.Info
    ): CoreClientProfile.Sender {
        return object : CoreClientProfile.Sender {

            override suspend fun bootNotification(
                request: BootNotificationRequest
            ): BootNotificationConfirmation {
                return send(ocppSessionInfo, request) as BootNotificationConfirmation
            }

            override suspend fun heartbeat(): HeartbeatConfirmation {
                return send(ocppSessionInfo, HeartbeatRequest) as HeartbeatConfirmation
            }

            override suspend fun authorize(
                request: AuthorizeRequest
            ): AuthorizeConfirmation {
                return send(ocppSessionInfo, request) as AuthorizeConfirmation
            }

            override suspend fun dataTransfer(
                request: DataTransferRequest
            ): DataTransferConfirmation {
                return send(ocppSessionInfo, request) as DataTransferConfirmation
            }

            override suspend fun statusNotification(
                request: StatusNotificationRequest
            ): StatusNotificationConfirmation {
                return send(ocppSessionInfo, request) as StatusNotificationConfirmation
            }

            override suspend fun startTransaction(
                request: StartTransactionRequest
            ): StartTransactionConfirmation {
                return send(ocppSessionInfo, request) as StartTransactionConfirmation
            }

            override suspend fun stopTransaction(
                request: StopTransactionRequest
            ): StopTransactionConfirmation {
                return send(ocppSessionInfo, request) as StopTransactionConfirmation
            }

            override suspend fun meterValues(
                request: MeterValuesRequest
            ): MeterValuesConfirmation {
                return send(ocppSessionInfo, request) as MeterValuesConfirmation
            }
        }
    }

    fun asFirmwareProfile(
        ocppSessionInfo: OcppSession.Info
    ): FirmwareManagementClientProfile.Sender {
        return object : FirmwareManagementClientProfile.Sender {
            override suspend fun firmwareStatusNotification(
                request: FirmwareStatusNotificationRequest
            ): FirmwareStatusNotificationConfirmation {
                return send(ocppSessionInfo, request) as FirmwareStatusNotificationConfirmation
            }

            override suspend fun diagnosticsStatusNotification(
                request: DiagnosticsStatusNotificationRequest
            ): DiagnosticsStatusNotificationConfirmation {
                return send(ocppSessionInfo, request) as DiagnosticsStatusNotificationConfirmation
            }
        }
    }

    fun asSecurityProfile(
        ocppSessionInfo: OcppSession.Info
    ): SecurityClientProfile.Sender {
        return object : SecurityClientProfile.Sender {
            override suspend fun logStatusNotification(
                request: LogStatusNotificationRequest
            ): LogStatusNotificationConfirmation {
                return send(ocppSessionInfo, request) as LogStatusNotificationConfirmation
            }

            override suspend fun securityEventNotification(
                request: SecurityEventNotificationRequest
            ): SecurityEventNotificationConfirmation {
                return send(ocppSessionInfo, request) as SecurityEventNotificationConfirmation
            }

            override suspend fun signCertificate(
                request: SignCertificateRequest
            ): SignCertificateConfirmation {
                return send(ocppSessionInfo, request) as SignCertificateConfirmation
            }

            override suspend fun signedFirmwareStatusNotification(
                request: SignedFirmwareStatusNotificationRequest
            ): SignedFirmwareStatusNotificationConfirmation {
                return send(ocppSessionInfo, request) as SignedFirmwareStatusNotificationConfirmation
            }
        }
    }
}
