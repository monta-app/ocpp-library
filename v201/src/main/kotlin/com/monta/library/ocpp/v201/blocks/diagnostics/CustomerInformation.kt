package com.monta.library.ocpp.v201.blocks.diagnostics

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.blocks.certificatemanagement.common.CertificateHashData
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.IdToken
import com.monta.library.ocpp.v201.common.StatusInfo

object CustomerInformationFeature : Feature {
    override val name: String = "CustomerInformation"
    override val requestType: Class<out OcppRequest> = CustomerInformationRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = CustomerInformationResponse::class.java
}

data class CustomerInformationRequest(
    val customerCertificate: CertificateHashData? = null,
    val idToken: IdToken? = null,
    /** The Id of the request. */
    val requestId: Long,
    /** Flag indicating whether the Charging Station should return NotifyCustomerInformationRequest messages containing information about the customer referred to. */
    val report: Boolean,
    /** Flag indicating whether the Charging Station should clear all information about the customer referred to. */
    val clear: Boolean,
    /** A (e.g. vendor specific) identifier of the customer this request refers to. This field contains a custom identifier other than IdToken and Certificate.
     One of the possible identifiers (customerIdentifier, customerIdToken or customerCertificate) should be in the request message. */
    val customerIdentifier: String? = null,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        if (customerIdentifier != null) {
            require(customerIdentifier.length <= 64) {
                "customerIdentifier length > maximum 64 - ${customerIdentifier.length}"
            }
        }
    }
}

data class CustomerInformationResponse(
    val status: Status,
    val statusInfo: StatusInfo? = null,
    val customData: CustomData? = null
) : OcppConfirmation {

    enum class Status {
        Accepted,
        Rejected,
        Invalid
    }
}
