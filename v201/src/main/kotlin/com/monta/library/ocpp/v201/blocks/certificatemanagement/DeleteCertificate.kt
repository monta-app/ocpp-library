package com.monta.library.ocpp.v201.blocks.certificatemanagement

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.blocks.certificatemanagement.common.CertificateHashData
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.StatusInfo

object DeleteCertificateFeature : Feature {
    override val name: String = "DeleteCertificate"
    override val requestType: Class<out OcppRequest> = DeleteCertificateRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = DeleteCertificateResponse::class.java
}

data class DeleteCertificateRequest(
    val certificateHashData: CertificateHashData,
    val customData: CustomData? = null
) : OcppRequest

data class DeleteCertificateResponse(
    val status: Status,
    val statusInfo: StatusInfo? = null,
    val customData: CustomData? = null
) : OcppConfirmation {
    enum class Status {
        Accepted,
        Failed,
        NotFound
    }
}
