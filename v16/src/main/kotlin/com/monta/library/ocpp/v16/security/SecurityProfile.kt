package com.monta.library.ocpp.v16.security

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.common.profile.ProfileDispatcher
import com.monta.library.ocpp.common.session.OcppSession
import com.monta.library.ocpp.common.transport.OcppCallException
import com.monta.library.ocpp.v16.error.MessageErrorCodeV16

/**
 * The security profile from 'Improved security for OCPP 1.6-J, ed. 3.'.
 *
 * Currently only the security events and logs are supported.
 */
class SecurityServerProfile(
    private val listener: Listener
) : ProfileDispatcher {

    override val featureList: List<Feature> = listOf(
        LogStatusNotificationFeature,
        SecurityEventNotificationFeature,
        SignCertificateFeature,
        SignedFirmwareStatusNotificationFeature,
        // Command
        GetLogFeature,
        ExtendedTriggerMessageFeature,
        CertificateSignedFeature,
        DeleteCertificateFeature,
        GetInstalledCertificateIdsFeature,
        InstallCertificateFeature,
        SignedUpdateFirmwareFeature
    )

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is LogStatusNotificationRequest -> listener.logStatusNotification(ocppSessionInfo, request)
            is SecurityEventNotificationRequest -> listener.securityEventNotification(ocppSessionInfo, request)
            is SignCertificateRequest -> listener.signCertificate(ocppSessionInfo, request)
            is SignedFirmwareStatusNotificationRequest -> listener.signedFirmwareStatusNotification(
                ocppSessionInfo,
                request
            )

            else -> throw OcppCallException(MessageErrorCodeV16.NotSupported, "Requested Action [${request.actionName()}] is recognized but not supported by the receiver")
        }
    }

    interface Sender {
        suspend fun getLog(
            request: GetLogRequest
        ): GetLogConfirmation

        suspend fun extendedTriggerMessage(
            request: ExtendedTriggerMessageRequest
        ): ExtendedTriggerMessageConfirmation

        suspend fun certificateSigned(
            request: CertificateSignedRequest
        ): CertificateSignedConfirmation

        suspend fun deleteCertificate(
            request: DeleteCertificateRequest
        ): DeleteCertificateConfirmation

        suspend fun getInstalledCertificateIds(
            request: GetInstalledCertificateIdsRequest
        ): GetInstalledCertificateIdsConfirmation

        suspend fun installCertificate(
            request: InstallCertificateRequest
        ): InstallCertificateConfirmation

        suspend fun signedUpdateFirmware(
            request: SignedUpdateFirmwareRequest
        ): SignedUpdateFirmwareConfirmation
    }

    interface Listener {
        suspend fun logStatusNotification(
            ocppSessionInfo: OcppSession.Info,
            request: LogStatusNotificationRequest
        ): LogStatusNotificationConfirmation

        suspend fun securityEventNotification(
            ocppSessionInfo: OcppSession.Info,
            request: SecurityEventNotificationRequest
        ): SecurityEventNotificationConfirmation

        suspend fun signCertificate(
            ocppSessionInfo: OcppSession.Info,
            request: SignCertificateRequest
        ): SignCertificateConfirmation

        suspend fun signedFirmwareStatusNotification(
            ocppSessionInfo: OcppSession.Info,
            request: SignedFirmwareStatusNotificationRequest
        ): SignedFirmwareStatusNotificationConfirmation
    }
}

class SecurityClientProfile(
    private val listener: Listener
) : ProfileDispatcher {

    override val featureList: List<Feature> = listOf(
        GetLogFeature,
        ExtendedTriggerMessageFeature,
        CertificateSignedFeature,
        DeleteCertificateFeature,
        GetInstalledCertificateIdsFeature,
        InstallCertificateFeature,
        SignedUpdateFirmwareFeature,
        SignCertificateFeature,
        LogStatusNotificationFeature,
        SecurityEventNotificationFeature,
        SignedFirmwareStatusNotificationFeature
    )

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is GetLogRequest -> listener.getLog(ocppSessionInfo, request)
            is ExtendedTriggerMessageRequest -> listener.extendedTriggerMessage(ocppSessionInfo, request)
            is CertificateSignedRequest -> listener.certificateSigned(ocppSessionInfo, request)
            is DeleteCertificateRequest -> listener.deleteCertificate(ocppSessionInfo, request)
            is GetInstalledCertificateIdsRequest -> listener.getInstalledCertificateIds(ocppSessionInfo, request)
            is InstallCertificateRequest -> listener.installCertificate(ocppSessionInfo, request)
            is SignedUpdateFirmwareRequest -> listener.signedUpdateFirmware(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV16.NotSupported, "Requested Action [${request.actionName()}] is recognized but not supported by the receiver")
        }
    }

    interface Listener {
        suspend fun getLog(
            ocppSessionInfo: OcppSession.Info,
            request: GetLogRequest
        ): GetLogConfirmation

        suspend fun extendedTriggerMessage(
            ocppSessionInfo: OcppSession.Info,
            request: ExtendedTriggerMessageRequest
        ): ExtendedTriggerMessageConfirmation

        suspend fun certificateSigned(
            ocppSessionInfo: OcppSession.Info,
            request: CertificateSignedRequest
        ): CertificateSignedConfirmation

        suspend fun deleteCertificate(
            ocppSessionInfo: OcppSession.Info,
            request: DeleteCertificateRequest
        ): DeleteCertificateConfirmation

        suspend fun getInstalledCertificateIds(
            ocppSessionInfo: OcppSession.Info,
            request: GetInstalledCertificateIdsRequest
        ): GetInstalledCertificateIdsConfirmation

        suspend fun installCertificate(
            ocppSessionInfo: OcppSession.Info,
            request: InstallCertificateRequest
        ): InstallCertificateConfirmation

        suspend fun signedUpdateFirmware(
            ocppSessionInfo: OcppSession.Info,
            request: SignedUpdateFirmwareRequest
        ): SignedUpdateFirmwareConfirmation
    }

    interface Sender {
        suspend fun logStatusNotification(
            request: LogStatusNotificationRequest
        ): LogStatusNotificationConfirmation

        suspend fun securityEventNotification(
            request: SecurityEventNotificationRequest
        ): SecurityEventNotificationConfirmation

        suspend fun signCertificate(
            request: SignCertificateRequest
        ): SignCertificateConfirmation

        suspend fun signedFirmwareStatusNotification(
            request: SignedFirmwareStatusNotificationRequest
        ): SignedFirmwareStatusNotificationConfirmation
    }
}
