package com.monta.library.ocpp.v16.extension.plugandcharge.features

import com.fasterxml.jackson.annotation.JsonIgnore
import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v16.IdTagInfo
import com.monta.library.ocpp.v16.extension.plugandcharge.model.OCSPRequestData
import com.monta.library.ocpp.v16.extension.plugandcharge.util.CertificateUtil
import java.security.cert.X509Certificate

/**
 * Basically an enriched response for Authorize where we check if the certificate is still valid
 */
object PncAuthorizeFeature : Feature {
    override val name: String = "Authorize"
    override val requestType: Class<out OcppRequest> = PncAuthorizeRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = PncAuthorizeConfirmation::class.java
}

data class PncAuthorizeRequest(
    // string[0..5500] Optional
    // The X.509 certificated presented by EV and encoded in PEM format
    val certificate: String?,
    // identifierString[0..20] Required
    // This contains the identifier that needs to be authorized
    val idToken: String,
    // Optional
    // Contains the information needed to verify the EV Contract Certificate via OCSP.
    // A list of up to 4 certificates
    val iso15118CertificateHashData: List<OCSPRequestData>?
) : OcppRequest {

    @get:JsonIgnore
    val evX509Cert: X509Certificate? by lazy {
        CertificateUtil.stringToX509Cert(certificate)
    }
}

data class PncAuthorizeConfirmation(
    // Required.
    // This contains information about authorization status, expiry and group id.
    val idTokenInfo: IdTagInfo,
    // Optional
    // Certificate status information
    // - if all certificates are valid: return 'Accepted'.
    // - if one of the certificates was revoked, return 'CertificateRevoked'.
    val certificateStatus: Status?
) : OcppConfirmation {
    enum class Status {
        // Positive response
        Accepted,

        // If the validation of the Security element in the message header failed.
        SignatureError,

        // If the OEMProvisioningCert in the CertificateInstallationReq, the Contract Certificate in the CertificateUpdateReq, or the
        // ContractCertificate in the PaymentDetailsReq is expired.
        CertificateExpired,

        // Used when the SECC or Central System matches the ContractCertificate contained in a CertificateUpdateReq or PaymentDetailsReq
        // with a CRL and the Contract Certificate is marked as revoked, OR when the SECC or Central System matches the OEM Provisioning
        // Certificate contained in a CertificateInstallationReq with a CRL and the OEM Provisioning Certificate is marked as revoked.
        // The revocation status can alternatively be obtained through an OCSP responder.
        CertificateRevoked,

        // If the new certificate cannot be retrieved from secondary actor within the specified timeout
        NoCertificateAvailable,

        // If the ContractSignatureCertChain contained in the CertificateInstallationReq message is not valid.
        CertChainError,

        // If the EMAID provided by EVCC during CertificateUpdateReq is not accepted by secondary actor
        ContractCancelled
    }
}
