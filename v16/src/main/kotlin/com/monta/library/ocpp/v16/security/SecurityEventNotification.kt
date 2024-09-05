package com.monta.library.ocpp.v16.security

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import java.time.ZonedDateTime

object SecurityEventNotificationFeature : Feature {
    override val name: String = "SecurityEventNotification"
    override val requestType: Class<out OcppRequest> = SecurityEventNotificationRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = SecurityEventNotificationConfirmation::class.java
}

data class SecurityEventNotificationRequest(

    /**
     * String[50]
     *
     * Required.
     *
     * Type of the security event (See list of currently known security events)
     */
    val type: String,

    /**
     * Required.
     *
     * Date and time at which the event occurred.
     */
    val timestamp: ZonedDateTime,

    /**
     * String[256]
     *
     * Optional.
     *
     * Additional information about the occurred security event.
     */
    val techInfo: String? = null
) : OcppRequest

object SecurityEventNotificationConfirmation : OcppConfirmation
