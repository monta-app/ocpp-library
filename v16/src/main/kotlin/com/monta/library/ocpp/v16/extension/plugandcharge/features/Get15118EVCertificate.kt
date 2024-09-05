package com.monta.library.ocpp.v16.extension.plugandcharge.features

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest

object Get15118EVCertificateFeature : Feature {
    override val name: String = "Get15118EVCertificate"
    override val requestType: Class<out OcppRequest> = Get15118EVCertificateRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = Get15118EVCertificateConfirmation::class.java
}

data class Get15118EVCertificateRequest(
    // string[0..50] Required
    // Schema version currently used for the 15118 session between EV and Charge Point.
    // Needed for parsing of the EXI stream by the Central System.
    val iso15118SchemaVersion: String,
    // Required.
    // Defines whether certificate needs to be installed or updated.
    val action: Action,
    // string[0..5600] Required
    // Raw CertificateInstallationReq request from EV, Base64 encoded.
    val exiRequest: String
) : OcppRequest {
    enum class Action {
        // Install the provided certificate.
        Install,

        // Update the provided certificate.
        Update
    }
}

data class Get15118EVCertificateConfirmation(
    // Required
    // Indicates whether the message was processed properly.
    val status: Status,
    // string[0..5600] Required
    // Raw CertificateInstallationRes response for the EV, Base64 encoded.
    val exiResponse: String
) : OcppConfirmation {
    enum class Status {
        // exiResponse included. This is no indication whether the update was successful, just that the message was processed properly.
        Accepted,

        // Processing of the message was not successful, no exiResponse included.
        Failed
    }
}
