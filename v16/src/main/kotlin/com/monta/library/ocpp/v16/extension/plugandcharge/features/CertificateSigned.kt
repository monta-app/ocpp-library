package com.monta.library.ocpp.v16.extension.plugandcharge.features

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest

object CertificateSignedFeature : Feature {
    override val name: String = "CertificateSigned"
    override val requestType: Class<out OcppRequest> = CertificateSignedRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = CertificateSignedConfirmation::class.java
}

data class CertificateSignedRequest(
    // string[0..10000] Required
    // The signed PEM encoded X.509 certificate.
    // This can also contain the necessary sub CA certificates.
    // In that case, the order of the bundle should follow the certificate chain, starting from the leaf certificate.
    // The Configuration Variable MaxCertificateChainSize can be used to limit the maximum size of this field.
    val certificateChain: String
) : OcppRequest

data class CertificateSignedConfirmation(
    // Required
    // Returns whether certificate signing has been accepted, otherwise rejected.
    val status: Status
) : OcppConfirmation {
    enum class Status {
        // Signed certificate is valid.
        Accepted,

        // Signed certificate is invalid.
        Rejected
    }
}
