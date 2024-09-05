package com.monta.library.ocpp.v16.extension.plugandcharge.features

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest

object InstallCertificateFeature : Feature {
    override val name: String = "InstallCertificate"
    override val requestType: Class<out OcppRequest> = InstallCertificateRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = InstallCertificateConfirmation::class.java
}

data class InstallCertificateRequest(
    // Required
    // Indicates the certificate type that is sent
    val certificateType: InstallCertificateUse,
    // string[0..5500] Required
    // A PEM encoded X.509 certificate
    val certificate: String
) : OcppRequest

enum class InstallCertificateUse {
    // Use for certificate of the V2G Root, a V2G Charge Point Certificate MUST be derived from one of the installed V2GRootCertificate certificates.
    V2GRootCertificate,

    // Use for certificate from an eMobility Service provider.
    // To support PnC charging with contracts from service providers that not derived their certificates from the V2G root.
    MORootCertificate
}

data class InstallCertificateConfirmation(
    // Required
    // Charge Point indicates if installation was successful
    val status: Status
) : OcppConfirmation {
    enum class Status {
        // The installation of the certificate succeeded
        Accepted,

        // The certificate is invalid and/or incorrect OR the CSO tries to install more certificates than allowed
        Rejected,

        // The certificate is valid and correct, but there is another reason the installation did not succeed
        Failed
    }
}
