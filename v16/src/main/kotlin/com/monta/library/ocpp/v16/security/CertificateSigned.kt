package com.monta.library.ocpp.v16.security

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest

object CertificateSignedFeature : Feature {
    override val name: String = "CertificateSigned"
    override val requestType: Class<out OcppRequest> = CertificateSignedRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = CertificateSignedConfirmation::class.java
}

data class CertificateSignedRequest(
    /**
     * Required
     *
     * The signed PEM encoded X.509 certificates. This can also contain the
     * necessary sub CA certificates. The maximum size of this field is be limited by the
     * configuration key: `CertificateSignedMaxSize`
     */
    val certificateChain: String
) : OcppRequest

enum class CertificateSignedStatusEnumType {
    /**
     * Signed certificate is valid.
     */
    Accepted,

    /**
     * Signed certificate is invalid.
     */
    Rejected
}

data class CertificateSignedConfirmation(
    /**
     * Required
     *
     * Returns whether certificate signing has been accepted, otherwise rejected.
     */
    val status: CertificateSignedStatusEnumType
) : OcppConfirmation
