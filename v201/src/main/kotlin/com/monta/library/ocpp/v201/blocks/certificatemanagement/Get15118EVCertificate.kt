package com.monta.library.ocpp.v201.blocks.certificatemanagement

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.StatusInfo

object Get15118EVCertificateFeature : Feature {
    override val name: String = "Get15118EVCertificate"
    override val requestType: Class<out OcppRequest> = Get15118EVCertificateRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = Get15118EVCertificateResponse::class.java
}

data class Get15118EVCertificateRequest(
    /** Schema version currently used for the 15118 session between EV and Charging Station. Needed for parsing of the EXI stream by the CSMS. */
    val iso15118SchemaVersion: String,
    val action: Action,
    /** Raw CertificateInstallationReq request from EV, Base64 encoded. */
    val exiRequest: String,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        require(iso15118SchemaVersion.length <= 50) {
            "iso15118SchemaVersion length > maximum 50 - ${iso15118SchemaVersion.length}"
        }
        require(exiRequest.length <= 7500) {
            "exiRequest length > maximum 7500 - ${exiRequest.length}"
        }
    }

    enum class Action {
        Install,
        Update
    }
}

data class Get15118EVCertificateResponse(
    val status: Status,
    val statusInfo: StatusInfo? = null,
    /** Raw CertificateInstallationRes response for the EV, Base64 encoded. */
    val exiResponse: String,
    val customData: CustomData? = null
) : OcppConfirmation {

    init {
        require(exiResponse.length <= 7500) {
            "exiResponse length > maximum 7500 - ${exiResponse.length}"
        }
    }

    enum class Status {
        Accepted,
        Failed
    }
}
