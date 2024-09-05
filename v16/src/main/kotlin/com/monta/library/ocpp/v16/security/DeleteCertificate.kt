package com.monta.library.ocpp.v16.security

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest

object DeleteCertificateFeature : Feature {
    override val name: String = "DeleteCertificate"
    override val requestType: Class<out OcppRequest> = DeleteCertificateRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = DeleteCertificateConfirmation::class.java
}

data class DeleteCertificateRequest(
    /**
     * Required
     *
     * Indicates the certificate of which deletion is requested.
     */
    val certificateHashData: CertificateHashDataType
) : OcppRequest

enum class DeleteCertificateStatusEnumType {
    /**
     * Normal successful completion (no errors).
     */
    Accepted,

    /**
     * Processing failure.
     */
    Failed,

    /**
     * Requested resource not found.
     */
    NotFound
}

data class DeleteCertificateConfirmation(
    /**
     * Required
     *
     * Charge Point indicates if it can process the request.
     */
    val status: DeleteCertificateStatusEnumType
) : OcppConfirmation
