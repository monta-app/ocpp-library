package com.monta.library.ocpp.v201.blocks.certificatemanagement

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.blocks.certificatemanagement.common.CertificateHashData
import com.monta.library.ocpp.v201.blocks.certificatemanagement.common.GetCertificateIdUse
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.StatusInfo

object GetInstalledCertificateIdsFeature : Feature {
    override val name: String = "GetInstalledCertificateIds"
    override val requestType: Class<out OcppRequest> = GetInstalledCertificateIdsRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = GetInstalledCertificateIdsResponse::class.java
}

data class GetInstalledCertificateIdsRequest(
    /**
     * Indicates the type of certificates requested.
     * When omitted, all certificate types are requested.
     **/
    val certificateType: List<GetCertificateIdUse>? = null,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        if (certificateType != null) {
            require(certificateType.isNotEmpty()) {
                "certificateType length < minimum 1 - ${certificateType.size}"
            }
        }
    }
}

data class GetInstalledCertificateIdsResponse(
    val status: Status,
    val statusInfo: StatusInfo? = null,
    val certificateHashDataChain: List<CertificateHashDataChain>? = null,
    val customData: CustomData? = null
) : OcppConfirmation {

    init {
        if (certificateHashDataChain != null) {
            require(certificateHashDataChain.isNotEmpty()) {
                "certificateHashDataChain length < minimum 1 - ${certificateHashDataChain.size}"
            }
        }
    }

    enum class Status {
        Accepted,
        NotFound
    }

    data class CertificateHashDataChain(
        val customData: CustomData? = null,
        val certificateHashData: CertificateHashData,
        val certificateType: GetCertificateIdUse,
        val childCertificateHashData: List<CertificateHashData>? = null
    ) {

        init {
            if (childCertificateHashData != null) {
                require(childCertificateHashData.size in 1..4) {
                    "childCertificateHashData length not in range 1..4 - ${childCertificateHashData.size}"
                }
            }
        }
    }
}
