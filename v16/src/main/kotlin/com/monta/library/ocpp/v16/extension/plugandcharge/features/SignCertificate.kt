package com.monta.library.ocpp.v16.extension.plugandcharge.features

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest

object SignCertificateFeature : Feature {
    override val name: String = "SignCertificate"
    override val requestType: Class<out OcppRequest> = SignCertificateRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = SignCertificateConfirmation::class.java
}

data class SignCertificateRequest(
    // string[0..5500] Required
    // The Charge Point SHALL send the public key in form of:
    // a Certificate Signing Request (CSR) as described in RFC 2986 [22] and then PEM encoded,
    // using the SignCertificate.req message.
    val csr: String
) : OcppRequest

data class SignCertificateConfirmation(
    // Required
    // Specifies whether the Central System can process the request.
    val status: Status
) : OcppConfirmation {
    enum class Status {
        // Request has been accepted and will be executed
        Accepted,

        // Request has not been accepted and will not be executed
        Rejected
    }
}
