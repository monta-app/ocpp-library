package com.monta.library.ocpp.v201.blocks.displaymessage

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.StatusInfo

object ClearDisplayMessageFeature : Feature {
    override val name: String = "ClearDisplayMessage"
    override val requestType: Class<out OcppRequest> = ClearDisplayMessageRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = ClearDisplayMessageResponse::class.java
}

data class ClearDisplayMessageRequest(
    /** Id of the message that SHALL be removed from the Charging Station. */
    val id: Long,
    val customData: CustomData? = null
) : OcppRequest

data class ClearDisplayMessageResponse(
    val status: Status,
    val statusInfo: StatusInfo? = null,
    val customData: CustomData? = null
) : OcppConfirmation {

    enum class Status {
        Accepted,
        Unknown
    }
}
