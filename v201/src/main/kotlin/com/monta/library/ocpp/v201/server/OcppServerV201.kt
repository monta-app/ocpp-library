package com.monta.library.ocpp.v201.server

import com.monta.library.ocpp.common.OcppMessageListener
import com.monta.library.ocpp.common.OcppServerEventListener
import com.monta.library.ocpp.common.profile.ProfileDispatcher
import com.monta.library.ocpp.common.serialization.Message
import com.monta.library.ocpp.common.serialization.SerializationMode
import com.monta.library.ocpp.common.session.OcppSession
import com.monta.library.ocpp.common.session.OcppSessionRepository
import com.monta.library.ocpp.common.transport.OcppSettings
import com.monta.library.ocpp.server.OcppServer
import com.monta.library.ocpp.v201.blocks.availability.AvailabilityServerDispatcher
import com.monta.library.ocpp.v201.blocks.availability.ChangeAvailabilityRequest
import com.monta.library.ocpp.v201.blocks.availability.ChangeAvailabilityResponse
import com.monta.library.ocpp.v201.blocks.certificatemanagement.CertificateManagementServerDispatcher
import com.monta.library.ocpp.v201.blocks.certificatemanagement.CertificateSignedRequest
import com.monta.library.ocpp.v201.blocks.certificatemanagement.CertificateSignedResponse
import com.monta.library.ocpp.v201.blocks.certificatemanagement.DeleteCertificateRequest
import com.monta.library.ocpp.v201.blocks.certificatemanagement.DeleteCertificateResponse
import com.monta.library.ocpp.v201.blocks.certificatemanagement.GetInstalledCertificateIdsRequest
import com.monta.library.ocpp.v201.blocks.certificatemanagement.GetInstalledCertificateIdsResponse
import com.monta.library.ocpp.v201.blocks.certificatemanagement.InstallCertificateRequest
import com.monta.library.ocpp.v201.blocks.certificatemanagement.InstallCertificateResponse
import com.monta.library.ocpp.v201.blocks.datatransfer.DataTransferRequest
import com.monta.library.ocpp.v201.blocks.datatransfer.DataTransferResponse
import com.monta.library.ocpp.v201.blocks.datatransfer.DataTransferServerDispatcher
import com.monta.library.ocpp.v201.blocks.diagnostics.ClearVariableMonitoringRequest
import com.monta.library.ocpp.v201.blocks.diagnostics.ClearVariableMonitoringResponse
import com.monta.library.ocpp.v201.blocks.diagnostics.CustomerInformationRequest
import com.monta.library.ocpp.v201.blocks.diagnostics.CustomerInformationResponse
import com.monta.library.ocpp.v201.blocks.diagnostics.DiagnosticsServerDispatcher
import com.monta.library.ocpp.v201.blocks.diagnostics.GetLogRequest
import com.monta.library.ocpp.v201.blocks.diagnostics.GetLogResponse
import com.monta.library.ocpp.v201.blocks.diagnostics.GetMonitoringReportRequest
import com.monta.library.ocpp.v201.blocks.diagnostics.GetMonitoringReportResponse
import com.monta.library.ocpp.v201.blocks.diagnostics.SetMonitoringBaseRequest
import com.monta.library.ocpp.v201.blocks.diagnostics.SetMonitoringBaseResponse
import com.monta.library.ocpp.v201.blocks.diagnostics.SetMonitoringLevelRequest
import com.monta.library.ocpp.v201.blocks.diagnostics.SetMonitoringLevelResponse
import com.monta.library.ocpp.v201.blocks.diagnostics.SetVariableMonitoringRequest
import com.monta.library.ocpp.v201.blocks.diagnostics.SetVariableMonitoringResponse
import com.monta.library.ocpp.v201.blocks.displaymessage.ClearDisplayMessageRequest
import com.monta.library.ocpp.v201.blocks.displaymessage.ClearDisplayMessageResponse
import com.monta.library.ocpp.v201.blocks.displaymessage.DisplayMessageServerDispatcher
import com.monta.library.ocpp.v201.blocks.displaymessage.GetDisplayMessagesRequest
import com.monta.library.ocpp.v201.blocks.displaymessage.GetDisplayMessagesResponse
import com.monta.library.ocpp.v201.blocks.displaymessage.SetDisplayMessageRequest
import com.monta.library.ocpp.v201.blocks.displaymessage.SetDisplayMessageResponse
import com.monta.library.ocpp.v201.blocks.firmwaremanagement.FirmwareManagementServerDispatcher
import com.monta.library.ocpp.v201.blocks.firmwaremanagement.PublishFirmwareRequest
import com.monta.library.ocpp.v201.blocks.firmwaremanagement.PublishFirmwareResponse
import com.monta.library.ocpp.v201.blocks.firmwaremanagement.UnpublishFirmwareRequest
import com.monta.library.ocpp.v201.blocks.firmwaremanagement.UnpublishFirmwareResponse
import com.monta.library.ocpp.v201.blocks.firmwaremanagement.UpdateFirmwareRequest
import com.monta.library.ocpp.v201.blocks.firmwaremanagement.UpdateFirmwareResponse
import com.monta.library.ocpp.v201.blocks.localauthorizationlistmanagement.ClearCacheRequest
import com.monta.library.ocpp.v201.blocks.localauthorizationlistmanagement.ClearCacheResponse
import com.monta.library.ocpp.v201.blocks.localauthorizationlistmanagement.GetLocalListVersionRequest
import com.monta.library.ocpp.v201.blocks.localauthorizationlistmanagement.GetLocalListVersionResponse
import com.monta.library.ocpp.v201.blocks.localauthorizationlistmanagement.LocalAuthorizationListManagementServerDispatcher
import com.monta.library.ocpp.v201.blocks.localauthorizationlistmanagement.SendLocalListRequest
import com.monta.library.ocpp.v201.blocks.localauthorizationlistmanagement.SendLocalListResponse
import com.monta.library.ocpp.v201.blocks.provisioning.GetBaseReportRequest
import com.monta.library.ocpp.v201.blocks.provisioning.GetBaseReportResponse
import com.monta.library.ocpp.v201.blocks.provisioning.GetReportRequest
import com.monta.library.ocpp.v201.blocks.provisioning.GetReportResponse
import com.monta.library.ocpp.v201.blocks.provisioning.GetVariablesRequest
import com.monta.library.ocpp.v201.blocks.provisioning.GetVariablesResponse
import com.monta.library.ocpp.v201.blocks.provisioning.ProvisioningServerDispatcher
import com.monta.library.ocpp.v201.blocks.provisioning.ResetRequest
import com.monta.library.ocpp.v201.blocks.provisioning.ResetResponse
import com.monta.library.ocpp.v201.blocks.provisioning.SetNetworkProfileRequest
import com.monta.library.ocpp.v201.blocks.provisioning.SetNetworkProfileResponse
import com.monta.library.ocpp.v201.blocks.provisioning.SetVariablesRequest
import com.monta.library.ocpp.v201.blocks.provisioning.SetVariablesResponse
import com.monta.library.ocpp.v201.blocks.remotecontrol.RemoteControlServerDispatcher
import com.monta.library.ocpp.v201.blocks.remotecontrol.RequestStartTransactionRequest
import com.monta.library.ocpp.v201.blocks.remotecontrol.RequestStartTransactionResponse
import com.monta.library.ocpp.v201.blocks.remotecontrol.RequestStopTransactionRequest
import com.monta.library.ocpp.v201.blocks.remotecontrol.RequestStopTransactionResponse
import com.monta.library.ocpp.v201.blocks.remotecontrol.TriggerMessageRequest
import com.monta.library.ocpp.v201.blocks.remotecontrol.TriggerMessageResponse
import com.monta.library.ocpp.v201.blocks.remotecontrol.UnlockConnectorRequest
import com.monta.library.ocpp.v201.blocks.remotecontrol.UnlockConnectorResponse
import com.monta.library.ocpp.v201.blocks.reservation.CancelReservationRequest
import com.monta.library.ocpp.v201.blocks.reservation.CancelReservationResponse
import com.monta.library.ocpp.v201.blocks.reservation.ReservationServerDispatcher
import com.monta.library.ocpp.v201.blocks.reservation.ReserveNowRequest
import com.monta.library.ocpp.v201.blocks.reservation.ReserveNowResponse
import com.monta.library.ocpp.v201.blocks.smartcharging.ClearChargingProfileRequest
import com.monta.library.ocpp.v201.blocks.smartcharging.ClearChargingProfileResponse
import com.monta.library.ocpp.v201.blocks.smartcharging.GetChargingProfilesRequest
import com.monta.library.ocpp.v201.blocks.smartcharging.GetChargingProfilesResponse
import com.monta.library.ocpp.v201.blocks.smartcharging.GetCompositeScheduleRequest
import com.monta.library.ocpp.v201.blocks.smartcharging.GetCompositeScheduleResponse
import com.monta.library.ocpp.v201.blocks.smartcharging.SetChargingProfileRequest
import com.monta.library.ocpp.v201.blocks.smartcharging.SetChargingProfileResponse
import com.monta.library.ocpp.v201.blocks.smartcharging.SmartChargingServerDispatcher
import com.monta.library.ocpp.v201.blocks.tariffandcost.CostUpdatedRequest
import com.monta.library.ocpp.v201.blocks.tariffandcost.CostUpdatedResponse
import com.monta.library.ocpp.v201.blocks.tariffandcost.TariffAndCostServerDispatcher
import com.monta.library.ocpp.v201.blocks.transactions.GetTransactionStatusRequest
import com.monta.library.ocpp.v201.blocks.transactions.GetTransactionStatusResponse
import com.monta.library.ocpp.v201.blocks.transactions.TransactionsServerDispatcher
import com.monta.library.ocpp.v201.error.OcppErrorResponderV201

class OcppServerV201 internal constructor(
    onConnect: OcppServerEventListener,
    onDisconnect: OcppServerEventListener,
    sendMessage: OcppMessageListener<Message>?,
    ocppSessionRepository: OcppSessionRepository?,
    settings: OcppSettings,
    profiles: Set<ProfileDispatcher>
) : OcppServer(
    onConnect = onConnect,
    onDisconnect = onDisconnect,
    sendMessage = sendMessage,
    ocppSessionRepository = ocppSessionRepository,
    serializationMode = SerializationMode.OCPP_2,
    ocppErrorResponder = OcppErrorResponderV201,
    settings = settings,
    profiles = profiles
) {

    // Authorization: no CSMS to CP messages

    fun asAvailabilityBlock(
        ocppSessionInfo: OcppSession.Info
    ): AvailabilityServerDispatcher.Sender {
        return object : AvailabilityServerDispatcher.Sender {
            override suspend fun changeAvailability(
                request: ChangeAvailabilityRequest
            ) = send(ocppSessionInfo, request) as ChangeAvailabilityResponse
        }
    }

    fun asCertificateManagementBlock(
        ocppSessionInfo: OcppSession.Info
    ): CertificateManagementServerDispatcher.Sender {
        return object : CertificateManagementServerDispatcher.Sender {
            override suspend fun certificateSigned(
                request: CertificateSignedRequest
            ) = send(ocppSessionInfo, request) as CertificateSignedResponse

            override suspend fun deleteCertificate(
                request: DeleteCertificateRequest
            ) = send(ocppSessionInfo, request) as DeleteCertificateResponse

            override suspend fun getInstalledCertificateIds(
                request: GetInstalledCertificateIdsRequest
            ) = send(ocppSessionInfo, request) as GetInstalledCertificateIdsResponse

            override suspend fun installCertificate(
                request: InstallCertificateRequest
            ) = send(ocppSessionInfo, request) as InstallCertificateResponse
        }
    }

    fun asLocalAuthorizationListManagementBlock(
        ocppSessionInfo: OcppSession.Info
    ): LocalAuthorizationListManagementServerDispatcher.Sender {
        return object : LocalAuthorizationListManagementServerDispatcher.Sender {
            override suspend fun clearCache(
                request: ClearCacheRequest
            ) = send(ocppSessionInfo, request) as ClearCacheResponse

            override suspend fun getLocalListVersion(
                request: GetLocalListVersionRequest
            ) = send(ocppSessionInfo, request) as GetLocalListVersionResponse

            override suspend fun sendLocalList(
                request: SendLocalListRequest
            ) = send(ocppSessionInfo, request) as SendLocalListResponse
        }
    }

    fun asDataTransferBlock(
        ocppSessionInfo: OcppSession.Info
    ): DataTransferServerDispatcher.Sender {
        return object : DataTransferServerDispatcher.Sender {
            override suspend fun dataTransfer(
                request: DataTransferRequest
            ) = send(ocppSessionInfo, request) as DataTransferResponse
        }
    }

    fun asDiagnosticsBlock(
        ocppSessionInfo: OcppSession.Info
    ): DiagnosticsServerDispatcher.Sender {
        return object : DiagnosticsServerDispatcher.Sender {
            override suspend fun clearVariableMonitoring(
                request: ClearVariableMonitoringRequest
            ) = send(ocppSessionInfo, request) as ClearVariableMonitoringResponse

            override suspend fun customerInformation(
                request: CustomerInformationRequest
            ) = send(ocppSessionInfo, request) as CustomerInformationResponse

            override suspend fun getLog(
                request: GetLogRequest
            ) = send(ocppSessionInfo, request) as GetLogResponse

            override suspend fun getMonitoringReport(
                request: GetMonitoringReportRequest
            ) = send(ocppSessionInfo, request) as GetMonitoringReportResponse

            override suspend fun setMonitoringBase(
                request: SetMonitoringBaseRequest
            ) = send(ocppSessionInfo, request) as SetMonitoringBaseResponse

            override suspend fun setMonitoringLevel(
                request: SetMonitoringLevelRequest
            ) = send(ocppSessionInfo, request) as SetMonitoringLevelResponse

            override suspend fun setVariableMonitoring(
                request: SetVariableMonitoringRequest
            ) = send(ocppSessionInfo, request) as SetVariableMonitoringResponse
        }
    }

    fun asDisplayMessageBlock(
        ocppSessionInfo: OcppSession.Info
    ): DisplayMessageServerDispatcher.Sender {
        return object : DisplayMessageServerDispatcher.Sender {
            override suspend fun clearDisplayMessage(
                request: ClearDisplayMessageRequest
            ) = send(ocppSessionInfo, request) as ClearDisplayMessageResponse

            override suspend fun getDisplayMessages(
                request: GetDisplayMessagesRequest
            ) = send(ocppSessionInfo, request) as GetDisplayMessagesResponse

            override suspend fun setDisplayMessage(
                request: SetDisplayMessageRequest
            ) = send(ocppSessionInfo, request) as SetDisplayMessageResponse
        }
    }

    fun asFirmwareManagementBlock(
        ocppSessionInfo: OcppSession.Info
    ): FirmwareManagementServerDispatcher.Sender {
        return object : FirmwareManagementServerDispatcher.Sender {
            override suspend fun publishFirmware(
                request: PublishFirmwareRequest
            ) = send(ocppSessionInfo, request) as PublishFirmwareResponse

            override suspend fun unpublishFirmware(
                request: UnpublishFirmwareRequest
            ) = send(ocppSessionInfo, request) as UnpublishFirmwareResponse

            override suspend fun updateFirmware(
                request: UpdateFirmwareRequest
            ) = send(ocppSessionInfo, request) as UpdateFirmwareResponse
        }
    }

    // MeterValues: no CSMS to CP messages

    fun asProvisioningBlock(
        ocppSessionInfo: OcppSession.Info
    ): ProvisioningServerDispatcher.Sender {
        return object : ProvisioningServerDispatcher.Sender {
            override suspend fun getBaseReport(
                request: GetBaseReportRequest
            ) = send(ocppSessionInfo, request) as GetBaseReportResponse

            override suspend fun getReport(
                request: GetReportRequest
            ) = send(ocppSessionInfo, request) as GetReportResponse

            override suspend fun getVariables(
                request: GetVariablesRequest
            ) = send(ocppSessionInfo, request) as GetVariablesResponse

            override suspend fun reset(
                request: ResetRequest
            ) = send(ocppSessionInfo, request) as ResetResponse

            override suspend fun setNetworkProfile(
                request: SetNetworkProfileRequest
            ) = send(ocppSessionInfo, request) as SetNetworkProfileResponse

            override suspend fun setVariables(
                request: SetVariablesRequest
            ) = send(ocppSessionInfo, request) as SetVariablesResponse
        }
    }

    fun asRemoteControlBlock(
        ocppSessionInfo: OcppSession.Info
    ): RemoteControlServerDispatcher.Sender {
        return object : RemoteControlServerDispatcher.Sender {
            override suspend fun requestStartTransaction(
                request: RequestStartTransactionRequest
            ) = send(ocppSessionInfo, request) as RequestStartTransactionResponse

            override suspend fun requestStopTransaction(
                request: RequestStopTransactionRequest
            ) = send(ocppSessionInfo, request) as RequestStopTransactionResponse

            override suspend fun triggerMessage(
                request: TriggerMessageRequest
            ) = send(ocppSessionInfo, request) as TriggerMessageResponse

            override suspend fun unlockConnector(
                request: UnlockConnectorRequest
            ) = send(ocppSessionInfo, request) as UnlockConnectorResponse
        }
    }

    fun asReservationBlock(
        ocppSessionInfo: OcppSession.Info
    ): ReservationServerDispatcher.Sender {
        return object : ReservationServerDispatcher.Sender {
            override suspend fun cancelReservation(
                request: CancelReservationRequest
            ) = send(ocppSessionInfo, request) as CancelReservationResponse

            override suspend fun reserveNow(
                request: ReserveNowRequest
            ) = send(ocppSessionInfo, request) as ReserveNowResponse
        }
    }

    // Security: no CSMS to CP messages

    fun asSmartChargingBlock(
        ocppSessionInfo: OcppSession.Info
    ): SmartChargingServerDispatcher.Sender {
        return object : SmartChargingServerDispatcher.Sender {
            override suspend fun clearChargingProfile(
                request: ClearChargingProfileRequest
            ) = send(ocppSessionInfo, request) as ClearChargingProfileResponse

            override suspend fun getChargingProfiles(
                request: GetChargingProfilesRequest
            ) = send(ocppSessionInfo, request) as GetChargingProfilesResponse

            override suspend fun getCompositeSchedule(
                request: GetCompositeScheduleRequest
            ) = send(ocppSessionInfo, request) as GetCompositeScheduleResponse

            override suspend fun setChargingProfile(
                request: SetChargingProfileRequest
            ) = send(ocppSessionInfo, request) as SetChargingProfileResponse
        }
    }

    fun asTariffAndCostBlock(
        ocppSessionInfo: OcppSession.Info
    ): TariffAndCostServerDispatcher.Sender {
        return object : TariffAndCostServerDispatcher.Sender {
            override suspend fun costUpdated(
                request: CostUpdatedRequest
            ) = send(ocppSessionInfo, request) as CostUpdatedResponse
        }
    }

    fun asTransactionsBlock(
        ocppSessionInfo: OcppSession.Info
    ): TransactionsServerDispatcher.Sender {
        return object : TransactionsServerDispatcher.Sender {
            override suspend fun getTransactionStatus(
                request: GetTransactionStatusRequest
            ) = send(ocppSessionInfo, request) as GetTransactionStatusResponse
        }
    }
}
