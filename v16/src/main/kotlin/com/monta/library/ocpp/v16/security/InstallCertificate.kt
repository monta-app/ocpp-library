package com.monta.library.ocpp.v16.security

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest

object InstallCertificateFeature : Feature {
    override val name: String = "InstallCertificate"
    override val requestType: Class<out OcppRequest> = InstallCertificateRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = InstallCertificateConfirmation::class.java
}

data class InstallCertificateRequest(
    val certificateType: CertificateUseEnumType,

    /**
     * Required
     *
     * An PEM encoded X.509 certificate.
     */
    val certificate: String
) : OcppRequest

enum class CertificateStatusEnumType {
    /**
     * The installation of the certificate succeeded.
     */
    Accepted,

    /**
     * The certificate is valid and correct, but there is another reason the installation did not succeed.
     */
    Failed,

    /**
     * The certificate is invalid and/or incorrect OR the CPO tries to install more certificates than allowed.
     */
    Rejected
}

data class InstallCertificateConfirmation(
    /**
     * Required
     *
     * Charge Point indicates if installation was successful.
     */
    val status: CertificateStatusEnumType
) : OcppConfirmation
