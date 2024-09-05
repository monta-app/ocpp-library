package com.monta.library.ocpp.v201.blocks.certificatemanagement

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.GenericStatus
import com.monta.library.ocpp.v201.common.StatusInfo

object SignCertificateFeature : Feature {
    override val name: String = "SignCertificate"
    override val requestType: Class<out OcppRequest> = SignCertificateRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = SignCertificateResponse::class.java
}

data class SignCertificateRequest(
    /**
     * The Charging Station SHALL send the public key in form of a Certificate Signing Request (CSR) as described in RFC 2986 [22] and then PEM encoded, using the "signcertificaterequest,SignCertificateRequest" message.
     **/
    val csr: String,
    val certificateType: CertificateSigningUse? = null,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        require(csr.length <= 5500) {
            "csr length > maximum 5500 - ${csr.length}"
        }
    }

    enum class CertificateSigningUse {
        ChargingStationCertificate,
        V2GCertificate
    }
}

data class SignCertificateResponse(
    val status: GenericStatus,
    val statusInfo: StatusInfo? = null,
    val customData: CustomData? = null
) : OcppConfirmation
