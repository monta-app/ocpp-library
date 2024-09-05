package com.monta.library.ocpp.v16.firmware

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import java.time.ZonedDateTime

object GetDiagnosticsFeature : Feature {
    override val name: String = "GetDiagnostics"
    override val requestType: Class<out OcppRequest> = GetDiagnosticsRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = GetDiagnosticsConfirmation::class.java
}

data class GetDiagnosticsRequest(
    /**
     * Required
     *
     * This contains the location (directory) where the diagnostics file shall be uploaded to.
     */
    val location: String,
    /**
     * Optional
     *
     * This specifies how many times Charge Point must try to upload the diagnostics before giving up. If this field is not present, it is left to Charge Point to decide how many times it wants to retry
     */
    val retries: Int? = null,
    /**
     * Optional
     *
     * The interval in seconds after which a retry may be attempted. If this field is not present, it is left to Charge Point to decide how long to wait between attempts.
     */
    val retryInterval: Int? = null,
    /**
     * Optional
     *
     * This contains the date and time of the oldest logging information to include in the diagnostics.
     */
    val startTime: ZonedDateTime? = null,
    /**
     * Optional
     *
     * This contains the date and time of the latest logging information to include in the diagnostics
     */
    val stopTime: ZonedDateTime? = null
) : OcppRequest

data class GetDiagnosticsConfirmation(
    /**
     * String[255]
     *
     * Optional
     *
     * This contains the name of the file with diagnostic information that will be uploaded. This field is not present when no diagnostic information is available.
     */
    val fileName: String? = null
) : OcppConfirmation
