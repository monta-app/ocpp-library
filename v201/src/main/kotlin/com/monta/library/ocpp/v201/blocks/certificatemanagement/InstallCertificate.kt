package com.monta.library.ocpp.v201.blocks.certificatemanagement

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.StatusInfo

object InstallCertificateFeature : Feature {
    override val name: String = "InstallCertificate"
    override val requestType: Class<out OcppRequest> = InstallCertificateRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = InstallCertificateResponse::class.java
}

data class InstallCertificateRequest(
    val certificateType: InstallCertificateUse,
    /** A PEM encoded X.509 certificate. */
    val certificate: String,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        require(certificate.length <= 5500) {
            "certificate length > maximum 5500 - ${certificate.length}"
        }
    }

    enum class InstallCertificateUse {
        V2GRootCertificate,
        MORootCertificate,
        CSMSRootCertificate,
        ManufacturerRootCertificate
    }
}

data class InstallCertificateResponse(
    val status: Status,
    val statusInfo: StatusInfo? = null,
    val customData: CustomData? = null
) : OcppConfirmation {

    enum class Status {
        Accepted,
        Rejected,
        Failed
    }
}
