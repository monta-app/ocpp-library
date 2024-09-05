package com.monta.library.ocpp.v201.client

import com.monta.library.ocpp.client.OcppClient
import com.monta.library.ocpp.common.OcppClientConnectionEvent
import com.monta.library.ocpp.common.OcppClientDisconnectionEvent
import com.monta.library.ocpp.common.profile.ProfileDispatcher
import com.monta.library.ocpp.common.serialization.SerializationMode
import com.monta.library.ocpp.common.session.OcppSession
import com.monta.library.ocpp.common.session.OcppSessionRepository
import com.monta.library.ocpp.common.transport.OcppSettings
import com.monta.library.ocpp.v201.blocks.authorization.AuthorizationClientDispatcher
import com.monta.library.ocpp.v201.blocks.authorization.AuthorizeRequest
import com.monta.library.ocpp.v201.blocks.authorization.AuthorizeResponse
import com.monta.library.ocpp.v201.blocks.availability.AvailabilityClientDispatcher
import com.monta.library.ocpp.v201.blocks.availability.HeartbeatRequest
import com.monta.library.ocpp.v201.blocks.availability.HeartbeatResponse
import com.monta.library.ocpp.v201.blocks.availability.StatusNotificationRequest
import com.monta.library.ocpp.v201.blocks.availability.StatusNotificationResponse
import com.monta.library.ocpp.v201.blocks.certificatemanagement.CertificateManagementClientDispatcher
import com.monta.library.ocpp.v201.blocks.certificatemanagement.Get15118EVCertificateRequest
import com.monta.library.ocpp.v201.blocks.certificatemanagement.Get15118EVCertificateResponse
import com.monta.library.ocpp.v201.blocks.certificatemanagement.GetCertificateStatusRequest
import com.monta.library.ocpp.v201.blocks.certificatemanagement.GetCertificateStatusResponse
import com.monta.library.ocpp.v201.blocks.certificatemanagement.SignCertificateRequest
import com.monta.library.ocpp.v201.blocks.certificatemanagement.SignCertificateResponse
import com.monta.library.ocpp.v201.blocks.datatransfer.DataTransferClientDispatcher
import com.monta.library.ocpp.v201.blocks.datatransfer.DataTransferRequest
import com.monta.library.ocpp.v201.blocks.datatransfer.DataTransferResponse
import com.monta.library.ocpp.v201.blocks.diagnostics.DiagnosticsClientDispatcher
import com.monta.library.ocpp.v201.blocks.diagnostics.LogStatusNotificationRequest
import com.monta.library.ocpp.v201.blocks.diagnostics.LogStatusNotificationResponse
import com.monta.library.ocpp.v201.blocks.diagnostics.NotifyCustomerInformationRequest
import com.monta.library.ocpp.v201.blocks.diagnostics.NotifyCustomerInformationResponse
import com.monta.library.ocpp.v201.blocks.diagnostics.NotifyEventRequest
import com.monta.library.ocpp.v201.blocks.diagnostics.NotifyEventResponse
import com.monta.library.ocpp.v201.blocks.diagnostics.NotifyMonitoringReportRequest
import com.monta.library.ocpp.v201.blocks.diagnostics.NotifyMonitoringReportResponse
import com.monta.library.ocpp.v201.blocks.firmwaremanagement.FirmwareManagementClientDispatcher
import com.monta.library.ocpp.v201.blocks.firmwaremanagement.FirmwareStatusNotificationRequest
import com.monta.library.ocpp.v201.blocks.firmwaremanagement.FirmwareStatusNotificationResponse
import com.monta.library.ocpp.v201.blocks.firmwaremanagement.PublishFirmwareStatusNotificationRequest
import com.monta.library.ocpp.v201.blocks.firmwaremanagement.PublishFirmwareStatusNotificationResponse
import com.monta.library.ocpp.v201.blocks.metervalues.MeterValuesClientDispatcher
import com.monta.library.ocpp.v201.blocks.metervalues.MeterValuesRequest
import com.monta.library.ocpp.v201.blocks.metervalues.MeterValuesResponse
import com.monta.library.ocpp.v201.blocks.provisioning.BootNotificationRequest
import com.monta.library.ocpp.v201.blocks.provisioning.BootNotificationResponse
import com.monta.library.ocpp.v201.blocks.provisioning.NotifyReportRequest
import com.monta.library.ocpp.v201.blocks.provisioning.NotifyReportResponse
import com.monta.library.ocpp.v201.blocks.provisioning.ProvisioningClientDispatcher
import com.monta.library.ocpp.v201.blocks.reservation.ReservationClientDispatcher
import com.monta.library.ocpp.v201.blocks.reservation.ReservationStatusUpdateRequest
import com.monta.library.ocpp.v201.blocks.reservation.ReservationStatusUpdateResponse
import com.monta.library.ocpp.v201.blocks.security.SecurityClientDispatcher
import com.monta.library.ocpp.v201.blocks.security.SecurityEventNotificationRequest
import com.monta.library.ocpp.v201.blocks.security.SecurityEventNotificationResponse
import com.monta.library.ocpp.v201.blocks.smartcharging.ClearedChargingLimitRequest
import com.monta.library.ocpp.v201.blocks.smartcharging.ClearedChargingLimitResponse
import com.monta.library.ocpp.v201.blocks.smartcharging.NotifyChargingLimitRequest
import com.monta.library.ocpp.v201.blocks.smartcharging.NotifyChargingLimitResponse
import com.monta.library.ocpp.v201.blocks.smartcharging.NotifyEVChargingNeedsRequest
import com.monta.library.ocpp.v201.blocks.smartcharging.NotifyEVChargingNeedsResponse
import com.monta.library.ocpp.v201.blocks.smartcharging.NotifyEVChargingScheduleRequest
import com.monta.library.ocpp.v201.blocks.smartcharging.NotifyEVChargingScheduleResponse
import com.monta.library.ocpp.v201.blocks.smartcharging.ReportChargingProfilesRequest
import com.monta.library.ocpp.v201.blocks.smartcharging.ReportChargingProfilesResponse
import com.monta.library.ocpp.v201.blocks.smartcharging.SmartChargingClientDispatcher
import com.monta.library.ocpp.v201.blocks.transactions.TransactionEventRequest
import com.monta.library.ocpp.v201.blocks.transactions.TransactionEventResponse
import com.monta.library.ocpp.v201.blocks.transactions.TransactionsClientDispatcher
import com.monta.library.ocpp.v201.error.OcppErrorResponderV201

class OcppClientV201(
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
    serializationMode = SerializationMode.OCPP_2,
    ocppErrorResponder = OcppErrorResponderV201,
    settings = settings,
    profiles = profiles,
    sendHook = sendHook
) {
    fun asAuthorization(
        ocppSessionInfo: OcppSession.Info
    ): AuthorizationClientDispatcher.Sender {
        return object : AuthorizationClientDispatcher.Sender {
            override suspend fun authorize(
                request: AuthorizeRequest
            ) = send(ocppSessionInfo, request) as AuthorizeResponse
        }
    }

    fun asAvailability(
        ocppSessionInfo: OcppSession.Info
    ): AvailabilityClientDispatcher.Sender {
        return object : AvailabilityClientDispatcher.Sender {
            override suspend fun heartbeat(
                request: HeartbeatRequest
            ) = send(ocppSessionInfo, request) as HeartbeatResponse

            override suspend fun statusNotification(
                request: StatusNotificationRequest
            ) = send(ocppSessionInfo, request) as StatusNotificationResponse
        }
    }

    fun asCertificateManagement(
        ocppSessionInfo: OcppSession.Info
    ): CertificateManagementClientDispatcher.Sender {
        return object : CertificateManagementClientDispatcher.Sender {
            override suspend fun get15118EVCertificate(
                request: Get15118EVCertificateRequest
            ) = send(ocppSessionInfo, request) as Get15118EVCertificateResponse

            override suspend fun getCertificateStatus(
                request: GetCertificateStatusRequest
            ) = send(ocppSessionInfo, request) as GetCertificateStatusResponse

            override suspend fun signCertificate(
                request: SignCertificateRequest
            ) = send(ocppSessionInfo, request) as SignCertificateResponse
        }
    }

    fun asDataTransfer(
        ocppSessionInfo: OcppSession.Info
    ): DataTransferClientDispatcher.Sender {
        return object : DataTransferClientDispatcher.Sender {
            override suspend fun dataTransfer(
                request: DataTransferRequest
            ) = send(ocppSessionInfo, request) as DataTransferResponse
        }
    }

    fun asDiagnostic(
        ocppSessionInfo: OcppSession.Info
    ): DiagnosticsClientDispatcher.Sender {
        return object : DiagnosticsClientDispatcher.Sender {
            override suspend fun logStatusNotification(
                request: LogStatusNotificationRequest
            ) = send(ocppSessionInfo, request) as LogStatusNotificationResponse

            override suspend fun notifyCustomerInformation(
                request: NotifyCustomerInformationRequest
            ) = send(ocppSessionInfo, request) as NotifyCustomerInformationResponse

            override suspend fun notifyEvent(
                request: NotifyEventRequest
            ) = send(ocppSessionInfo, request) as NotifyEventResponse

            override suspend fun notifyMonitoringReport(
                request: NotifyMonitoringReportRequest
            ) = send(ocppSessionInfo, request) as NotifyMonitoringReportResponse
        }
    }

    fun asFirmwareManagement(
        ocppSessionInfo: OcppSession.Info
    ): FirmwareManagementClientDispatcher.Sender {
        return object : FirmwareManagementClientDispatcher.Sender {
            override suspend fun firmwareStatusNotification(
                request: FirmwareStatusNotificationRequest
            ) = send(ocppSessionInfo, request) as FirmwareStatusNotificationResponse

            override suspend fun publishFirmwareStatusNotification(
                request: PublishFirmwareStatusNotificationRequest
            ) = send(ocppSessionInfo, request) as PublishFirmwareStatusNotificationResponse
        }
    }

    fun asMeterValues(
        ocppSessionInfo: OcppSession.Info
    ): MeterValuesClientDispatcher.Sender {
        return object : MeterValuesClientDispatcher.Sender {
            override suspend fun meterValues(
                request: MeterValuesRequest
            ) = send(ocppSessionInfo, request) as MeterValuesResponse
        }
    }

    fun asProvisioning(
        ocppSessionInfo: OcppSession.Info
    ): ProvisioningClientDispatcher.Sender {
        return object : ProvisioningClientDispatcher.Sender {
            override suspend fun bootNotification(
                request: BootNotificationRequest
            ) = send(ocppSessionInfo, request) as BootNotificationResponse

            override suspend fun notifyReport(
                request: NotifyReportRequest
            ) = send(ocppSessionInfo, request) as NotifyReportResponse
        }
    }

    fun asReservation(
        ocppSessionInfo: OcppSession.Info
    ): ReservationClientDispatcher.Sender {
        return object : ReservationClientDispatcher.Sender {
            override suspend fun reservationStatusUpdate(
                request: ReservationStatusUpdateRequest
            ) = send(ocppSessionInfo, request) as ReservationStatusUpdateResponse
        }
    }

    fun asSecurity(
        ocppSessionInfo: OcppSession.Info
    ): SecurityClientDispatcher.Sender {
        return object : SecurityClientDispatcher.Sender {
            override suspend fun securityEventNotification(
                request: SecurityEventNotificationRequest
            ) = send(ocppSessionInfo, request) as SecurityEventNotificationResponse
        }
    }

    fun asSmartCharging(
        ocppSessionInfo: OcppSession.Info
    ): SmartChargingClientDispatcher.Sender {
        return object : SmartChargingClientDispatcher.Sender {
            override suspend fun clearedChargingLimit(
                request: ClearedChargingLimitRequest
            ) = send(ocppSessionInfo, request) as ClearedChargingLimitResponse

            override suspend fun notifyChargingLimit(
                request: NotifyChargingLimitRequest
            ) = send(ocppSessionInfo, request) as NotifyChargingLimitResponse

            override suspend fun notifyEVChargingNeeds(
                request: NotifyEVChargingNeedsRequest
            ) = send(ocppSessionInfo, request) as NotifyEVChargingNeedsResponse

            override suspend fun notifyEVChargingSchedule(
                request: NotifyEVChargingScheduleRequest
            ) = send(ocppSessionInfo, request) as NotifyEVChargingScheduleResponse

            override suspend fun reportChargingProfiles(
                request: ReportChargingProfilesRequest
            ) = send(ocppSessionInfo, request) as ReportChargingProfilesResponse
        }
    }

    fun asTransactions(
        ocppSessionInfo: OcppSession.Info
    ): TransactionsClientDispatcher.Sender {
        return object : TransactionsClientDispatcher.Sender {
            override suspend fun transactionEvent(
                request: TransactionEventRequest
            ) = send(ocppSessionInfo, request) as TransactionEventResponse
        }
    }
}
