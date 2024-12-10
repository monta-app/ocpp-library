package com.monta.library.ocpp.v201.blocks.certificatemanagement

import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.common.profile.ProfileDispatcher
import com.monta.library.ocpp.common.session.OcppSession
import com.monta.library.ocpp.common.transport.OcppCallException
import com.monta.library.ocpp.v201.error.MessageErrorCodeV201

val certificateManagementFeatures = listOf(
    // CP to CSMS
    Get15118EVCertificateFeature,
    GetCertificateStatusFeature,
    SignCertificateFeature,
    // CSMS to CP
    CertificateSignedFeature,
    DeleteCertificateFeature,
    GetInstalledCertificateIdsFeature,
    InstallCertificateFeature
)

class CertificateManagementClientDispatcher(
    private val listener: Listener
) : ProfileDispatcher {
    override val featureList = certificateManagementFeatures

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is CertificateSignedRequest -> listener.certificateSigned(ocppSessionInfo, request)
            is DeleteCertificateRequest -> listener.deleteCertificate(ocppSessionInfo, request)
            is GetInstalledCertificateIdsRequest -> listener.getInstalledCertificateIds(ocppSessionInfo, request)
            is InstallCertificateRequest -> listener.installCertificate(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV201.NotSupported, "Requested Action [${request.actionName()}] is recognized but not supported by the receiver")
        }
    }

    interface Listener {
        suspend fun certificateSigned(
            ocppSessionInfo: OcppSession.Info,
            request: CertificateSignedRequest
        ): CertificateSignedResponse

        suspend fun deleteCertificate(
            ocppSessionInfo: OcppSession.Info,
            request: DeleteCertificateRequest
        ): DeleteCertificateResponse

        suspend fun getInstalledCertificateIds(
            ocppSessionInfo: OcppSession.Info,
            request: GetInstalledCertificateIdsRequest
        ): GetInstalledCertificateIdsResponse

        suspend fun installCertificate(
            ocppSessionInfo: OcppSession.Info,
            request: InstallCertificateRequest
        ): InstallCertificateResponse
    }

    interface Sender {
        suspend fun get15118EVCertificate(
            request: Get15118EVCertificateRequest
        ): Get15118EVCertificateResponse

        suspend fun getCertificateStatus(
            request: GetCertificateStatusRequest
        ): GetCertificateStatusResponse

        suspend fun signCertificate(
            request: SignCertificateRequest
        ): SignCertificateResponse
    }
}

class CertificateManagementServerDispatcher(
    private val listener: Listener
) : ProfileDispatcher {
    override val featureList = certificateManagementFeatures

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is Get15118EVCertificateRequest -> listener.get15118EVCertificate(ocppSessionInfo, request)
            is GetCertificateStatusRequest -> listener.getCertificateStatus(ocppSessionInfo, request)
            is SignCertificateRequest -> listener.signCertificate(ocppSessionInfo, request)
            else -> throw OcppCallException(MessageErrorCodeV201.NotSupported, "Requested Action [${request.actionName()}] is recognized but not supported by the receiver")
        }
    }

    interface Listener {
        suspend fun get15118EVCertificate(
            ocppSessionInfo: OcppSession.Info,
            request: Get15118EVCertificateRequest
        ): Get15118EVCertificateResponse

        suspend fun getCertificateStatus(
            ocppSessionInfo: OcppSession.Info,
            request: GetCertificateStatusRequest
        ): GetCertificateStatusResponse

        suspend fun signCertificate(
            ocppSessionInfo: OcppSession.Info,
            request: SignCertificateRequest
        ): SignCertificateResponse
    }

    interface Sender {
        suspend fun certificateSigned(
            request: CertificateSignedRequest
        ): CertificateSignedResponse

        suspend fun deleteCertificate(
            request: DeleteCertificateRequest
        ): DeleteCertificateResponse

        suspend fun getInstalledCertificateIds(
            request: GetInstalledCertificateIdsRequest
        ): GetInstalledCertificateIdsResponse

        suspend fun installCertificate(
            request: InstallCertificateRequest
        ): InstallCertificateResponse
    }
}
