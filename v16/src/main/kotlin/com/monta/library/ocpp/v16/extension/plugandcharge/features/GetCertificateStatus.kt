package com.monta.library.ocpp.v16.extension.plugandcharge.features

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v16.extension.plugandcharge.model.OCSPRequestData

object GetCertificateStatusFeature : Feature {
    override val name: String = "GetCertificateStatus"
    override val requestType: Class<out OcppRequest> = GetCertificateStatusRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = GetCertificateStatusConfirmation::class.java
}

data class GetCertificateStatusRequest(
    // Required
    // Indicates the certificate of which the status is requested
    val ocspRequestData: OCSPRequestData
) : OcppRequest

data class GetCertificateStatusConfirmation(
    // Required
    // This indicates whether the charging station was able to retrieve the OCSP certificate status.
    val status: Status,
    // string[0..5500] Optional
    // OCSPResponse class as defined in IETF RFC 6960.
    // DER encoded (as defined in IETF RFC 6960), and then base64 encoded.
    // MAY only be omitted when status is not Accepted.
    val ocspResult: String?
) : OcppConfirmation {
    enum class Status {
        // Successfully retrieved the OCSP certificate status
        Accepted,

        // Failed to retrieve the OCSP certificate status
        Failed
    }
}
