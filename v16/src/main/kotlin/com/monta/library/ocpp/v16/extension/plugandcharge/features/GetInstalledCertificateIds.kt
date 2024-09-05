package com.monta.library.ocpp.v16.extension.plugandcharge.features

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v16.extension.plugandcharge.model.CertificateHashDataChain
import com.monta.library.ocpp.v16.extension.plugandcharge.model.GetCertificateIdUse

object GetInstalledCertificateIdsFeature : Feature {
    override val name: String = "GetInstalledCertificateIds"
    override val requestType: Class<out OcppRequest> = GetInstalledCertificateIdsRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = GetInstalledCertificateIdsConfirmation::class.java
}

data class GetInstalledCertificateIdsRequest(
    // Optional
    // Indicates the type of certificates requested.
    // When omitted, all certificate types are requested.
    val certificateType: List<GetCertificateIdUse>?
) : OcppRequest

data class GetInstalledCertificateIdsConfirmation(
    // Required
    // Charge Point indicates if it can process the request
    val status: Status,
    // Optional
    // The Charge Point includes the Certificate information for each available certificate.
    val certificateHashDataChain: List<CertificateHashDataChain>?
) : OcppConfirmation {
    enum class Status {
        // Normal successful completion (no errors).
        Accepted,

        // Requested resource not found.
        NotFound
    }
}
