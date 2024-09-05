package com.monta.library.ocpp.v16.core

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v16.IdTagInfo

object AuthorizeFeature : Feature {
    override val name: String = "Authorize"
    override val requestType: Class<out OcppRequest> = AuthorizeRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = AuthorizeConfirmation::class.java
}

/**
 * This contains the field definition of the [AuthorizeRequest] PDU sent by the Charge Point to the Central System.
 */
data class AuthorizeRequest(
    /**
     * String[20]
     *
     * Required
     *
     * This contains the identifier that needs to be authorized.
     */
    val idTag: String
) : OcppRequest

/**
 * This contains the field definition of the [AuthorizeConfirmation] PDU sent by the
 * Central System to the Charge Point in response to an [AuthorizeRequest] PDU.
 */
data class AuthorizeConfirmation(
    /**
     * Required
     *
     * This contains information about authorization status, expiry and parent id.
     */
    val idTagInfo: IdTagInfo
) : OcppConfirmation
