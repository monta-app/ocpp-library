package com.monta.library.ocpp.v16.server

import com.monta.library.ocpp.common.OcppMessageListener
import com.monta.library.ocpp.common.OcppServerEventListener
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.common.profile.ProfileDispatcher
import com.monta.library.ocpp.common.serialization.Message
import com.monta.library.ocpp.common.serialization.SerializationMode
import com.monta.library.ocpp.common.session.OcppSession
import com.monta.library.ocpp.common.session.OcppSessionRepository
import com.monta.library.ocpp.common.transport.OcppSettings
import com.monta.library.ocpp.server.OcppServer
import com.monta.library.ocpp.v16.core.ChangeAvailabilityConfirmation
import com.monta.library.ocpp.v16.core.ChangeAvailabilityRequest
import com.monta.library.ocpp.v16.core.ChangeConfigurationConfirmation
import com.monta.library.ocpp.v16.core.ChangeConfigurationRequest
import com.monta.library.ocpp.v16.core.ClearCacheConfirmation
import com.monta.library.ocpp.v16.core.ClearCacheRequest
import com.monta.library.ocpp.v16.core.CoreServerProfile
import com.monta.library.ocpp.v16.core.DataTransferConfirmation
import com.monta.library.ocpp.v16.core.DataTransferRequest
import com.monta.library.ocpp.v16.core.GetConfigurationConfirmation
import com.monta.library.ocpp.v16.core.GetConfigurationRequest
import com.monta.library.ocpp.v16.core.RemoteStartTransactionConfirmation
import com.monta.library.ocpp.v16.core.RemoteStartTransactionRequest
import com.monta.library.ocpp.v16.core.RemoteStopTransactionConfirmation
import com.monta.library.ocpp.v16.core.RemoteStopTransactionRequest
import com.monta.library.ocpp.v16.core.ResetConfirmation
import com.monta.library.ocpp.v16.core.ResetRequest
import com.monta.library.ocpp.v16.core.UnlockConnectorConfirmation
import com.monta.library.ocpp.v16.core.UnlockConnectorRequest
import com.monta.library.ocpp.v16.error.OcppErrorResponderV16
import com.monta.library.ocpp.v16.extension.ExtensionProfileDispatcher
import com.monta.library.ocpp.v16.extension.OcppExtensionService
import com.monta.library.ocpp.v16.extension.plugandcharge.PlugAndChargeExtensionServerProfile
import com.monta.library.ocpp.v16.extension.plugandcharge.features.PncTriggerMessageRequest
import com.monta.library.ocpp.v16.firmware.FirmwareManagementServerProfile
import com.monta.library.ocpp.v16.firmware.GetDiagnosticsConfirmation
import com.monta.library.ocpp.v16.firmware.GetDiagnosticsRequest
import com.monta.library.ocpp.v16.firmware.UpdateFirmwareConfirmation
import com.monta.library.ocpp.v16.firmware.UpdateFirmwareRequest
import com.monta.library.ocpp.v16.localauth.GetLocalListVersionConfirmation
import com.monta.library.ocpp.v16.localauth.GetLocalListVersionRequest
import com.monta.library.ocpp.v16.localauth.LocalListServerProfile
import com.monta.library.ocpp.v16.localauth.SendLocalListConfirmation
import com.monta.library.ocpp.v16.localauth.SendLocalListRequest
import com.monta.library.ocpp.v16.remotetrigger.TriggerMessageConfirmation
import com.monta.library.ocpp.v16.remotetrigger.TriggerMessageRequest
import com.monta.library.ocpp.v16.remotetrigger.TriggerMessageServerProfile
import com.monta.library.ocpp.v16.security.CertificateSignedConfirmation
import com.monta.library.ocpp.v16.security.CertificateSignedRequest
import com.monta.library.ocpp.v16.security.DeleteCertificateConfirmation
import com.monta.library.ocpp.v16.security.DeleteCertificateRequest
import com.monta.library.ocpp.v16.security.ExtendedTriggerMessageConfirmation
import com.monta.library.ocpp.v16.security.ExtendedTriggerMessageRequest
import com.monta.library.ocpp.v16.security.GetInstalledCertificateIdsConfirmation
import com.monta.library.ocpp.v16.security.GetInstalledCertificateIdsRequest
import com.monta.library.ocpp.v16.security.GetLogConfirmation
import com.monta.library.ocpp.v16.security.GetLogRequest
import com.monta.library.ocpp.v16.security.InstallCertificateConfirmation
import com.monta.library.ocpp.v16.security.InstallCertificateRequest
import com.monta.library.ocpp.v16.security.SecurityServerProfile
import com.monta.library.ocpp.v16.security.SignedUpdateFirmwareConfirmation
import com.monta.library.ocpp.v16.security.SignedUpdateFirmwareRequest
import com.monta.library.ocpp.v16.smartcharge.ClearChargingProfileConfirmation
import com.monta.library.ocpp.v16.smartcharge.ClearChargingProfileRequest
import com.monta.library.ocpp.v16.smartcharge.GetCompositeScheduleConfirmation
import com.monta.library.ocpp.v16.smartcharge.GetCompositeScheduleRequest
import com.monta.library.ocpp.v16.smartcharge.SetChargingProfileConfirmation
import com.monta.library.ocpp.v16.smartcharge.SetChargingProfileRequest
import com.monta.library.ocpp.v16.smartcharge.SmartChargeServerProfile
import com.monta.library.ocpp.v16.extension.plugandcharge.features.CertificateSignedConfirmation as PncCertificateSignedConfirmation
import com.monta.library.ocpp.v16.extension.plugandcharge.features.CertificateSignedRequest as PncCertificateSignedRequest
import com.monta.library.ocpp.v16.extension.plugandcharge.features.DeleteCertificateConfirmation as PncDeleteCertificateConfirmation
import com.monta.library.ocpp.v16.extension.plugandcharge.features.DeleteCertificateRequest as PncDeleteCertificateRequest
import com.monta.library.ocpp.v16.extension.plugandcharge.features.GetInstalledCertificateIdsConfirmation as PncGetInstalledCertificateIdsConfirmation
import com.monta.library.ocpp.v16.extension.plugandcharge.features.GetInstalledCertificateIdsRequest as PncGetInstalledCertificateIdsRequest
import com.monta.library.ocpp.v16.extension.plugandcharge.features.InstallCertificateConfirmation as PncInstallCertificateConfirmation
import com.monta.library.ocpp.v16.extension.plugandcharge.features.InstallCertificateRequest as PncInstallCertificateRequest

class OcppServerV16 internal constructor(
    onConnect: OcppServerEventListener,
    onDisconnect: OcppServerEventListener,
    sendMessage: OcppMessageListener<Message>?,
    ocppSessionRepository: OcppSessionRepository?,
    settings: OcppSettings,
    profiles: Set<ProfileDispatcher>,
    extensionProfiles: Set<ExtensionProfileDispatcher>
) : OcppServer(
    onConnect = onConnect,
    onDisconnect = onDisconnect,
    sendMessage = sendMessage,
    ocppSessionRepository = ocppSessionRepository,
    serializationMode = SerializationMode.OCPP_1_6,
    ocppErrorResponder = OcppErrorResponderV16,
    settings = settings,
    profiles = profiles
) {
    private val extensionService = OcppExtensionService(
        messageSerializer = messageSerializer,
        extensionProfiles = extensionProfiles
    )

    override suspend fun handleParsedRequest(
        profile: ProfileDispatcher,
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest,
        message: Message.Request
    ) {
        if (request is DataTransferRequest) {
            val response = extensionService.handleRequest(ocppSessionInfo, request)
            if (response != null) {
                sendMessage(
                    ocppSessionInfo = ocppSessionInfo,
                    message = Message.Response(
                        uniqueId = message.uniqueId,
                        payload = messageSerializer.toPayload(response)
                    )
                )
            } else {
                super.handleParsedRequest(profile, ocppSessionInfo, request, message)
            }
        } else {
            super.handleParsedRequest(profile, ocppSessionInfo, request, message)
        }
    }

    fun asCoreProfile(ocppSessionInfo: OcppSession.Info): CoreServerProfile.Sender {
        return object : CoreServerProfile.Sender {
            override suspend fun changeAvailability(
                request: ChangeAvailabilityRequest
            ) = send(ocppSessionInfo, request) as ChangeAvailabilityConfirmation

            override suspend fun changeConfiguration(
                request: ChangeConfigurationRequest
            ) = send(ocppSessionInfo, request) as ChangeConfigurationConfirmation

            override suspend fun clearCache(
                request: ClearCacheRequest
            ) = send(ocppSessionInfo, request) as ClearCacheConfirmation

            override suspend fun getConfiguration(
                request: GetConfigurationRequest
            ) = send(ocppSessionInfo, request) as GetConfigurationConfirmation

            override suspend fun remoteStartTransaction(
                request: RemoteStartTransactionRequest
            ) = send(ocppSessionInfo, request) as RemoteStartTransactionConfirmation

            override suspend fun remoteStopTransaction(
                request: RemoteStopTransactionRequest
            ) = send(ocppSessionInfo, request) as RemoteStopTransactionConfirmation

            override suspend fun reset(
                request: ResetRequest
            ) = send(ocppSessionInfo, request) as ResetConfirmation

            override suspend fun unlockConnector(
                request: UnlockConnectorRequest
            ) = send(ocppSessionInfo, request) as UnlockConnectorConfirmation

            override suspend fun dataTransfer(
                request: DataTransferRequest
            ) = send(ocppSessionInfo, request) as DataTransferConfirmation
        }
    }

    fun asFirmwareProfile(
        ocppSessionInfo: OcppSession.Info
    ): FirmwareManagementServerProfile.Sender {
        // this should check that the charge point on ocppSession actually reports to support the firmware profile
        // before returning the firmware view of it.
        return object : FirmwareManagementServerProfile.Sender {
            override suspend fun getDiagnostics(
                request: GetDiagnosticsRequest
            ) = send(ocppSessionInfo, request) as GetDiagnosticsConfirmation

            override suspend fun updateFirmware(
                request: UpdateFirmwareRequest
            ) = send(ocppSessionInfo, request) as UpdateFirmwareConfirmation
        }
    }

    fun asLocalListProfile(
        ocppSessionInfo: OcppSession.Info
    ): LocalListServerProfile.Sender {
        // this should check that the charge point on ocppSession actually reports to support the local list profile
        // before returning the local list view of it.
        return object : LocalListServerProfile.Sender {
            override suspend fun getLocalList(
                request: GetLocalListVersionRequest
            ) = send(ocppSessionInfo, request) as GetLocalListVersionConfirmation

            override suspend fun sendLocalList(
                request: SendLocalListRequest
            ) = send(ocppSessionInfo, request) as SendLocalListConfirmation
        }
    }

    fun asTriggerMessageProfile(
        ocppSessionInfo: OcppSession.Info
    ): TriggerMessageServerProfile.Sender {
        // this should check that the charge point on ocppSession actually reports to support the remote trigger profile
        // before returning the local list view of it.
        return object : TriggerMessageServerProfile.Sender {
            override suspend fun triggerMessage(
                request: TriggerMessageRequest
            ) = send(ocppSessionInfo, request) as TriggerMessageConfirmation
        }
    }

    fun asSecurityProfile(
        ocppSessionInfo: OcppSession.Info
    ): SecurityServerProfile.Sender {
        // this should check that the charge point on ocppSession actually reports to support the security profile
        // before returning the local list view of it.
        return object : SecurityServerProfile.Sender {
            override suspend fun getLog(
                request: GetLogRequest
            ) = send(ocppSessionInfo, request) as GetLogConfirmation

            override suspend fun extendedTriggerMessage(
                request: ExtendedTriggerMessageRequest
            ) = send(ocppSessionInfo, request) as ExtendedTriggerMessageConfirmation

            override suspend fun certificateSigned(
                request: CertificateSignedRequest
            ) = send(ocppSessionInfo, request) as CertificateSignedConfirmation

            override suspend fun deleteCertificate(
                request: DeleteCertificateRequest
            ) = send(ocppSessionInfo, request) as DeleteCertificateConfirmation

            override suspend fun getInstalledCertificateIds(
                request: GetInstalledCertificateIdsRequest
            ) = send(ocppSessionInfo, request) as GetInstalledCertificateIdsConfirmation

            override suspend fun installCertificate(
                request: InstallCertificateRequest
            ) = send(ocppSessionInfo, request) as InstallCertificateConfirmation

            override suspend fun signedUpdateFirmware(
                request: SignedUpdateFirmwareRequest
            ) = send(ocppSessionInfo, request) as SignedUpdateFirmwareConfirmation
        }
    }

    fun asSmartChargeProfile(
        ocppSessionInfo: OcppSession.Info
    ): SmartChargeServerProfile.Sender {
        // this should check that the charge point on ocppSession actually reports to support the smart charge profile
        // before returning the local list view of it.
        return object : SmartChargeServerProfile.Sender {
            override suspend fun clearChargingProfile(
                request: ClearChargingProfileRequest
            ) = send(ocppSessionInfo, request) as ClearChargingProfileConfirmation

            override suspend fun getCompositeSchedule(
                request: GetCompositeScheduleRequest
            ) = send(ocppSessionInfo, request) as GetCompositeScheduleConfirmation

            override suspend fun setChargingProfile(
                request: SetChargingProfileRequest
            ) = send(ocppSessionInfo, request) as SetChargingProfileConfirmation
        }
    }

    fun asPlugAndChargeProfile(ocppSessionInfo: OcppSession.Info): PlugAndChargeExtensionServerProfile.Sender {
        return object : PlugAndChargeExtensionServerProfile.Sender {
            override suspend fun certificateSigned(request: PncCertificateSignedRequest): PncCertificateSignedConfirmation {
                return extensionService.send(vendorId, request) { dataTransferRequest ->
                    asCoreProfile(ocppSessionInfo).dataTransfer(
                        dataTransferRequest
                    )
                } as PncCertificateSignedConfirmation
            }

            override suspend fun deleteCertificate(request: PncDeleteCertificateRequest): PncDeleteCertificateConfirmation {
                return extensionService.send(vendorId, request) { dataTransferRequest ->
                    asCoreProfile(ocppSessionInfo).dataTransfer(
                        dataTransferRequest
                    )
                } as PncDeleteCertificateConfirmation
            }

            override suspend fun getInstalledCertificateIds(
                request: PncGetInstalledCertificateIdsRequest
            ): PncGetInstalledCertificateIdsConfirmation {
                return extensionService.send(vendorId, request) { dataTransferRequest ->
                    asCoreProfile(ocppSessionInfo).dataTransfer(
                        dataTransferRequest
                    )
                } as PncGetInstalledCertificateIdsConfirmation
            }

            override suspend fun installCertificate(request: PncInstallCertificateRequest): PncInstallCertificateConfirmation {
                return extensionService.send(vendorId, request) { dataTransferRequest ->
                    asCoreProfile(ocppSessionInfo).dataTransfer(
                        dataTransferRequest
                    )
                } as PncInstallCertificateConfirmation
            }

            override suspend fun triggerMessage(request: PncTriggerMessageRequest): TriggerMessageConfirmation {
                return extensionService.send(vendorId, request) { dataTransferRequest ->
                    asCoreProfile(ocppSessionInfo).dataTransfer(
                        dataTransferRequest
                    )
                } as TriggerMessageConfirmation
            }
        }
    }
}
