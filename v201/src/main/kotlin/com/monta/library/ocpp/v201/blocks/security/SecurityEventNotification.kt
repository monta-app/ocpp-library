package com.monta.library.ocpp.v201.blocks.security

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import java.time.ZonedDateTime

object SecurityEventNotificationFeature : Feature {
    override val name = "SecurityEventNotification"
    override val requestType: Class<out OcppRequest> = SecurityEventNotificationRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = SecurityEventNotificationResponse::class.java
}

data class SecurityEventNotificationRequest(
    /**
     * Type of the security event. This value should be taken from the Security events list.
     **/
    val type: String,
    /**
     * Date and time at which the event occurred.
     **/
    val timestamp: ZonedDateTime,
    /**
     * Additional information about the occurred security event.
     **/
    val techInfo: String? = null,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        require(type.length <= 50) {
            "type length > maximum 50 - ${type.length}"
        }
        if (techInfo != null) {
            require(techInfo.length <= 255) {
                "techInfo length > maximum 255 - ${techInfo.length}"
            }
        }
    }
}

data class SecurityEventNotificationResponse(
    val customData: CustomData? = null
) : OcppConfirmation
