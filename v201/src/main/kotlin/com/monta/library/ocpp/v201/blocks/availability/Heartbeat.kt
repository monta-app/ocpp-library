package com.monta.library.ocpp.v201.blocks.availability

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import java.time.ZonedDateTime

object HeartbeatFeature : Feature {
    override val name: String = "Heartbeat"
    override val requestType: Class<out OcppRequest> = HeartbeatRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = HeartbeatResponse::class.java
}

data class HeartbeatRequest(
    val customData: CustomData? = null
) : OcppRequest

data class HeartbeatResponse(
    /**
     * Contains the current time of the CSMS.
     **/
    val currentTime: ZonedDateTime,
    val customData: CustomData? = null
) : OcppConfirmation
