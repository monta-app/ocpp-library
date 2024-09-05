package com.monta.library.ocpp.v201.blocks.certificatemanagement

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.OCSPRequestData
import com.monta.library.ocpp.v201.common.StatusInfo

object GetCertificateStatusFeature : Feature {
    override val name: String = "GetCertificateStatus"
    override val requestType: Class<out OcppRequest> = GetCertificateStatusRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = GetCertificateStatusResponse::class.java
}

data class GetCertificateStatusRequest(
    val ocspRequestData: OCSPRequestData,
    val customData: CustomData? = null
) : OcppRequest

data class GetCertificateStatusResponse(
    val status: Status,
    val statusInfo: StatusInfo? = null,
    /**
     * OCSPResponse class as defined in "ref-ocpp_security_24, IETF RFC 6960".
     * DER encoded (as defined in "ref-ocpp_security_24, IETF RFC 6960"),
     * and then base64 encoded. MAY only be omitted when status is not Accepted.
     **/
    val ocspResult: String? = null,
    val customData: CustomData? = null
) : OcppConfirmation {

    init {
        if (ocspResult != null) {
            require(ocspResult.length <= 5500) {
                "ocspResult length > maximum 5500 - ${ocspResult.length}"
            }
        }
    }

    enum class Status {
        Accepted,
        Failed
    }
}
