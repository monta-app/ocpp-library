package com.monta.library.ocpp.v201.blocks.authorization

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.IdToken
import com.monta.library.ocpp.v201.common.IdTokenInfo
import com.monta.library.ocpp.v201.common.OCSPRequestData

object AuthorizeFeature : Feature {
    override val name: String = "Authorize"
    override val requestType: Class<out OcppRequest> = AuthorizeRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = AuthorizeResponse::class.java
}

data class AuthorizeRequest(
    val idToken: IdToken,
    /**
     * The X.509 certificated presented by EV and encoded in PEM format.
     **/
    val certificate: String? = null,
    val iso15118CertificateHashData: List<OCSPRequestData>? = null,
    val customData: CustomData? = null
) : OcppRequest {
    init {
        if (certificate != null) {
            require(certificate.length <= 5500) {
                "certificate length > maximum 5500 - ${certificate.length}"
            }
        }
        if (iso15118CertificateHashData != null) {
            require(iso15118CertificateHashData.size in 1..4) {
                "iso15118CertificateHashData length not in range 1..4 - ${iso15118CertificateHashData.size}"
            }
        }
    }
}

data class AuthorizeResponse(
    val idTokenInfo: IdTokenInfo,
    val certificateStatus: CertificateStatus? = null,
    val customData: CustomData? = null
) : OcppConfirmation {
    enum class CertificateStatus {
        Accepted,
        SignatureError,
        CertificateExpired,
        CertificateRevoked,
        NoCertificateAvailable,
        CertChainError,
        ContractCancelled
    }
}
