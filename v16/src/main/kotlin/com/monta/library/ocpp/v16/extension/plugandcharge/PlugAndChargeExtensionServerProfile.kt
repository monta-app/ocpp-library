package com.monta.library.ocpp.v16.extension.plugandcharge

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.common.session.OcppSession
import com.monta.library.ocpp.v16.extension.ExtensionProfileDispatcher
import com.monta.library.ocpp.v16.extension.MessageNotApplicable
import com.monta.library.ocpp.v16.extension.plugandcharge.features.CertificateSignedConfirmation
import com.monta.library.ocpp.v16.extension.plugandcharge.features.CertificateSignedFeature
import com.monta.library.ocpp.v16.extension.plugandcharge.features.CertificateSignedRequest
import com.monta.library.ocpp.v16.extension.plugandcharge.features.DeleteCertificateConfirmation
import com.monta.library.ocpp.v16.extension.plugandcharge.features.DeleteCertificateFeature
import com.monta.library.ocpp.v16.extension.plugandcharge.features.DeleteCertificateRequest
import com.monta.library.ocpp.v16.extension.plugandcharge.features.Get15118EVCertificateConfirmation
import com.monta.library.ocpp.v16.extension.plugandcharge.features.Get15118EVCertificateFeature
import com.monta.library.ocpp.v16.extension.plugandcharge.features.Get15118EVCertificateRequest
import com.monta.library.ocpp.v16.extension.plugandcharge.features.GetCertificateStatusConfirmation
import com.monta.library.ocpp.v16.extension.plugandcharge.features.GetCertificateStatusFeature
import com.monta.library.ocpp.v16.extension.plugandcharge.features.GetCertificateStatusRequest
import com.monta.library.ocpp.v16.extension.plugandcharge.features.GetInstalledCertificateIdsConfirmation
import com.monta.library.ocpp.v16.extension.plugandcharge.features.GetInstalledCertificateIdsFeature
import com.monta.library.ocpp.v16.extension.plugandcharge.features.GetInstalledCertificateIdsRequest
import com.monta.library.ocpp.v16.extension.plugandcharge.features.InstallCertificateConfirmation
import com.monta.library.ocpp.v16.extension.plugandcharge.features.InstallCertificateFeature
import com.monta.library.ocpp.v16.extension.plugandcharge.features.InstallCertificateRequest
import com.monta.library.ocpp.v16.extension.plugandcharge.features.PncAuthorizeConfirmation
import com.monta.library.ocpp.v16.extension.plugandcharge.features.PncAuthorizeFeature
import com.monta.library.ocpp.v16.extension.plugandcharge.features.PncAuthorizeRequest
import com.monta.library.ocpp.v16.extension.plugandcharge.features.PncTriggerMessageFeature
import com.monta.library.ocpp.v16.extension.plugandcharge.features.PncTriggerMessageRequest
import com.monta.library.ocpp.v16.extension.plugandcharge.features.SignCertificateConfirmation
import com.monta.library.ocpp.v16.extension.plugandcharge.features.SignCertificateFeature
import com.monta.library.ocpp.v16.extension.plugandcharge.features.SignCertificateRequest
import com.monta.library.ocpp.v16.remotetrigger.TriggerMessageConfirmation

class PlugAndChargeExtensionServerProfile(
    private val listener: Listener
) : ExtensionProfileDispatcher {
    companion object {
        const val PNC_VENDOR_ID = "org.openchargealliance.iso15118pnc"
        val FEATURE_NAME_MAP: Map<String, Feature> = setOf(
            // CP to CSMS
            PncAuthorizeFeature,
            Get15118EVCertificateFeature,
            GetCertificateStatusFeature,
            SignCertificateFeature,
            // CSMS to CP
            CertificateSignedFeature,
            DeleteCertificateFeature,
            GetInstalledCertificateIdsFeature,
            InstallCertificateFeature,
            PncTriggerMessageFeature
        ).associateBy { it.name }
    }

    override val vendorId: String = PNC_VENDOR_ID

    override val featureNameMap: Map<String, Feature> = FEATURE_NAME_MAP

    override suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: OcppRequest
    ): OcppConfirmation {
        return when (request) {
            is PncAuthorizeRequest -> listener.authorize(ocppSessionInfo, request)
            is Get15118EVCertificateRequest -> listener.get15118EVCertificate(ocppSessionInfo, request)
            is GetCertificateStatusRequest -> listener.getCertificateStatus(ocppSessionInfo, request)
            is SignCertificateRequest -> listener.signCertificate(ocppSessionInfo, request)
            else -> {
                throw MessageNotApplicable("Request $request is not an accepted CSMS request")
            }
        }
    }

    override fun getRequestType(name: String): Class<out OcppRequest>? {
        return featureNameMap[name]?.requestType
    }

    override fun getConfirmationType(name: String): Class<out OcppConfirmation>? {
        return featureNameMap[name]?.confirmationType
    }

    interface Listener {
        suspend fun authorize(
            ocppSessionInfo: OcppSession.Info,
            request: PncAuthorizeRequest
        ): PncAuthorizeConfirmation

        suspend fun get15118EVCertificate(
            ocppSessionInfo: OcppSession.Info,
            request: Get15118EVCertificateRequest
        ): Get15118EVCertificateConfirmation

        suspend fun getCertificateStatus(
            ocppSessionInfo: OcppSession.Info,
            request: GetCertificateStatusRequest
        ): GetCertificateStatusConfirmation

        suspend fun signCertificate(
            ocppSessionInfo: OcppSession.Info,
            request: SignCertificateRequest
        ): SignCertificateConfirmation
    }

    interface Sender {
        val vendorId: String
            get() = PNC_VENDOR_ID

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

        suspend fun triggerMessage(
            request: PncTriggerMessageRequest
        ): TriggerMessageConfirmation
    }
}
