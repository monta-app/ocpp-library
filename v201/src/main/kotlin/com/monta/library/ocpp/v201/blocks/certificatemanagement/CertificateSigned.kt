package com.monta.library.ocpp.v201.blocks.certificatemanagement

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.StatusInfo

object CertificateSignedFeature : Feature {
    override val name: String = "CertificateSigned"
    override val requestType: Class<out OcppRequest> = CertificateSignedRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = CertificateSignedResponse::class.java
}

data class CertificateSignedRequest(
    /**
     * The signed PEM encoded X.509 certificate. This can also contain the necessary sub CA certificates. In that case, the order of the bundle should follow the certificate chain, starting from the leaf certificate.
     * The Configuration Variable "configkey-max-certificate-chain-size,MaxCertificateChainSize" can be used to limit the maximum size of this field.
     **/
    val certificateChain: String,
    val certificateType: CertificateSigningUse? = null,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        require(certificateChain.length <= 10000) {
            "certificateChain length > maximum 10000 - ${certificateChain.length}"
        }
    }

    enum class CertificateSigningUse {
        ChargingStationCertificate,
        V2GCertificate
    }
}

data class CertificateSignedResponse(
    val status: Status,
    val statusInfo: StatusInfo? = null,
    val customData: CustomData? = null
) : OcppConfirmation {
    enum class Status {
        Accepted,
        Rejected
    }
}
