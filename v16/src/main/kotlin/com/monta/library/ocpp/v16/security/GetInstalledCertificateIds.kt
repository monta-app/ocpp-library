package com.monta.library.ocpp.v16.security

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest

object GetInstalledCertificateIdsFeature : Feature {
    override val name: String = "GetInstalledCertificateIds"
    override val requestType: Class<out OcppRequest> = GetInstalledCertificateIdsRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = GetInstalledCertificateIdsConfirmation::class.java
}

data class GetInstalledCertificateIdsRequest(
    /**
     * Required
     *
     * Indicates the type of certificates requested.
     */
    val certificateType: CertificateUseEnumType
) : OcppRequest

enum class GetInstalledCertificateStatusEnumType {
    /**
     * Normal successful completion (no errors).
     */
    Accepted,

    /**
     * Requested certificate not found.
     */
    NotFound
}

data class GetInstalledCertificateIdsConfirmation(
    /**
     * Required
     *
     * Required. Charge Point indicates if it can process the request.
     */
    val status: GetInstalledCertificateStatusEnumType,

    /**
     * Optional.
     *
     * The Charge Point includes the Certificate information for each available certificate.
     */
    val certificateHashData: List<CertificateHashDataType>? = null
) : OcppConfirmation
